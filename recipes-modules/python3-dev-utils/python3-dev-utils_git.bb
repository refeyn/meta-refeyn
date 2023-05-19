SUMMARY = "DEV_UTILS - development utilities package for Refeyn Ltd"
HOMEPAGE = "https://github.com/refeyn/dev_utils"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://git@github.com/refeyn/dev_utils.git;protocol=ssh;branch=master"

PV = "1.41.0+git${SRCPV}"
SRCREV = "1a4915f808c5b92f2e74602b42f43cd63f8e7bf8"

S = "${WORKDIR}/git"

inherit setuptools3

do_write_version() {
    echo "VERSION_SHA = '${SRCREV}'" > ${S}/dev_utils/_versionSha.py
}
addtask do_write_version before do_compile after do_configure

RDEPENDS:${PN} += "python3-core python3-crypt python3-cython python3-datetime python3-io python3-json python3-logging python3-multiprocessing python3-packaging python3-profile python3-setuptools python3-typing-extensions python3-xml"

BBCLASSEXTEND = "native nativesdk"
