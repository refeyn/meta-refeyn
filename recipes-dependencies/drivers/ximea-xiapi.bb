LICENSE = "Ximea-Licence"
LIC_FILES_CHKSUM = "file://License.txt;md5=b9215a55b9de0276a924742b3cc99060"

NO_GENERIC_LICENSE[Ximea-Licence] = "License.txt"

SRC_URI = "https://www.ximea.com/downloads/recent/XIMEA_Linux_SP.tgz;subdir=src"
SRC_URI[sha256sum] = "f1ac31de6beacfbae5eb1e29b8f32554ec52ed9e410346032813f1b9214c9686"

S = "${WORKDIR}/src/package"

inherit python3-dir

DEBIAN_NOAUTONAME:${PN} = "1"
DEBIAN_NOAUTONAME:${PN}-dev = "1"
DEBIAN_NOAUTONAME:${PN}-dbg = "1"

RDEPENDS:${PN} += "lshw libusb1 libcurl bash tiff zlib kernel-usb-memory-limit"

COMPATIBLE_HOST = "(aarch64|armv7a).*-linux"
XIMEA_ARCH ?= "${TARGET_ARCH}"
XIMEA_ARCH:armv7a = "arm32"
XIMEA_ARCH:aarch64 = "arm64"

FILES:${PN} += "/opt/XIMEA ${PYTHON_SITEPACKAGES_DIR}/ximea"
INSANE_SKIP:${PN} += "already-stripped ldflags"

do_install() {
    optpath=${D}/opt/XIMEA
    install -d $optpath
    install -d $optpath/bin
    install -d $optpath/lib
    install -d $optpath/include
    install -d $optpath/data

    install -d ${D}${sysconfdir}/udev/rules.d/
    install libs/libusb/99-ximea.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}${libdir}
    install api/X${XIMEA_ARCH}/libm3api.so.2 ${D}${libdir}/libm3api.so.2.0.0
    ln -s -r ${D}${libdir}/libm3api.so.2.0.0 ${D}${libdir}/libm3api.so

    cp -R include $optpath/

    install -d ${D}${includedir}
    ln -s -r $optpath/include ${D}${includedir}/m3api

    cp libs/gentl/X${XIMEA_ARCH}/*.cti $optpath/lib/

    cp libs/xiapi_dng_store/X${XIMEA_ARCH}/libxiapi_dng_store.so $optpath/lib/

    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/ximea/libs/x${XIMEA_ARCH}
    install api/Python/v3/ximea/*.py ${D}${PYTHON_SITEPACKAGES_DIR}/ximea
    cp -R api/Python/v3/ximea/libs/x${XIMEA_ARCH} ${D}${PYTHON_SITEPACKAGES_DIR}/ximea/libs

    cp bin/xiSample.${XIMEA_ARCH} $optpath/bin/xiSample

    cp bin/xiCOP.${XIMEA_ARCH} $optpath/bin/xiCOP
    cp data/fw_update_tools_map.xml $optpath/data/

    # Disable as it depends on X11
    # cp bin/xiCamTool $optpath/bin/
    # install -d $optpath/CamTool
    # cp -R CamTool.${XIMEA_ARCH}/* $optpath/CamTool
    # Licences are (add "& FreeImage"):
    # file://CamTool.64/license-fi.txt;md5=7d2690b4d6d7dd53d69a773664bc4850 \
    # file://CamTool.arm64/license-fi.txt;md5=7d2690b4d6d7dd53d69a773664bc4850 \
}
