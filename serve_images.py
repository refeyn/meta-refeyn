import pathlib
import socket
import tarfile
from typing import Mapping

import cherrypy
import netifaces
import zeroconf

PORT = 8000
IMAGE_DIR = pathlib.Path(__file__).parent
NAME = "Refeyn local Tezi feed"


@cherrypy.popargs("image_name", "subfile")
class Images:
    def __init__(self, image_name_to_path: Mapping[str, pathlib.Path]) -> None:
        self._image_name_to_path = image_name_to_path

    @cherrypy.expose
    def default(self, image_name, subfile) -> None:
        path = self._image_name_to_path[image_name]
        tf = tarfile.open(path)
        infos = [mem for mem in tf.getmembers() if mem.name.endswith(f"/{subfile}")]
        if len(infos) != 1:
            raise cherrypy.HTTPError(404)
        info = infos[0]

        cherrypy.response.headers["Content-Type"] = "application/octet-stream"
        cherrypy.response.headers["Content-Disposition"] = (
            f'attachment; filename="{subfile}"'
        )
        cherrypy.response.headers["Content-Length"] = info.size

        def stream():
            with tf.extractfile(info) as f:
                while data := f.read(1024):
                    yield data
            tf.close()

        return stream()

    default._cp_config = {"response.stream": True}


class Index:
    def __init__(self) -> None:
        self._images = []
        image_name_to_path = {}
        for image in pathlib.Path(IMAGE_DIR).glob("*Tezi*.tar"):
            print("Found image at", image)
            self._images.append(f"images/{image.name}/image.json")
            image_name_to_path[image.name] = image

        self.images = Images(image_name_to_path)

    @cherrypy.expose
    @cherrypy.tools.json_out()
    def image_list_json(self):
        return {"config_format": 1, "images": self._images}


if __name__ == "__main__":
    addr = None
    for ifaceName in netifaces.interfaces():
        for i in netifaces.ifaddresses(ifaceName).get(netifaces.AF_INET, []):
            if i["addr"].startswith("192.168.11"):
                addr = i["addr"]
    if addr is None:
        print("Could not find IP addr of Tezi interface")
        exit()

    print("Addr on Tezi interface is", addr)

    info = zeroconf.ServiceInfo(
        "_tezi._tcp.local.",
        f"_{NAME}._tezi._tcp.local.",
        addresses=[socket.inet_aton(addr)],
        port=PORT,
        properties={
            "name": NAME,
            "path": "/image_list.json",
            "enabled": "1",
            "https": "0",
        },
        server=addr,
    )

    zc = zeroconf.Zeroconf(ip_version=zeroconf.IPVersion.V4Only)
    zc.register_service(info)
    try:
        cherrypy.config.update({"server.socket_port": PORT, "server.socket_host": addr})
        cherrypy.quickstart(Index())
    except KeyboardInterrupt:
        pass
    finally:
        print("Shutting down...")
        zc.unregister_service(info)
        zc.close()
