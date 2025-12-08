LICENSE = "FLIR-Licence"
LIC_FILES_CHKSUM = " \
    file://README.txt;endline=16;md5=8f4a6483a6ec2534fc120d2658ecb1d3 \
    file://LICENCE.txt;md5=e48e6c2aa0be8b393f34d934b222db92 \
"

NO_GENERIC_LICENSE[FLIR-Licence] = "LICENCE.txt"

SRC_URI = " \
    https://flir.netx.net/file/asset/59631/original/attachment;downloadfilename=Spinnaker-3.1.0.79-Linux.zip \
    file://LICENCE.txt \
"
SRC_URI[sha256sum] = "4e93dd163fa1808d322face8a5cbe041c10db02102b806e55d177bc7070766fd"

S = "${WORKDIR}"

RDEPENDS:${PN} = "spinnaker python3-core"

inherit python_setuptools_build_meta

PEP517_WHEEL_PATH = "${WORKDIR}"

do_compile[noexec] = "1"

do_unpack2() {
    tar -xvf ${S}/Spinnaker-3.1.0.79-Linux/spinnaker_python-3.1.0.79-cp310-cp310-linux_aarch64.tar.gz -C ${S}
}

addtask do_unpack2 after do_unpack before do_patch
