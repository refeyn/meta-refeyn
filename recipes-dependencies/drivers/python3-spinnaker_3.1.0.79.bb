LICENSE = "FLIR-Licence"
LIC_FILES_CHKSUM = " \
    file://src/README.txt;endline=16;md5=8f4a6483a6ec2534fc120d2658ecb1d3 \
    file://LICENCE.txt;md5=e48e6c2aa0be8b393f34d934b222db92 \
"

NO_GENERIC_LICENSE[FLIR-Licence] = "LICENCE.txt"

SRC_URI = " \
    https://flir.netx.net/file/asset/54402/original/attachment;downloadfilename=spinnaker_python-3.1.0.79-cp310-cp310-linux_aarch64.tar.gz;subdir=src \
    file://LICENCE.txt \
"
SRC_URI[sha256sum] = "38ac6a6a4aefe32c8bbee0b9d6f2991cb23936b5eb2bfcbde2edc6242c550d30"

S = "${WORKDIR}"

RDEPENDS:${PN} = "spinnaker python3-core"

inherit python_setuptools_build_meta

PEP517_WHEEL_PATH = "${WORKDIR}/src"

do_compile[noexec] = "1"
