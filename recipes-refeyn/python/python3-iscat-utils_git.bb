SUMMARY = "ISCAT_UTILS - utilities package for mass photometry software"
HOMEPAGE = "https://github.com/refeyn/iscat_utils"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/iscat_utils.git;protocol=ssh;branch=master;destsuffix=iscat_utils"

PV = "1.44.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/iscat_utils"

inherit setuptools3
require python3-dev-utils-build.inc

RDEPENDS:${PN} += "python3-asyncio python3-core python3-crypt python3-ctypes python3-datetime python3-html python3-image python3-io python3-json python3-logging python3-misc python3-multiprocessing python3-netclient python3-netserver python3-numpy python3-packaging python3-pillow python3-profile python3-pyside6 python3-pytest python3-shell python3-threading python3-typing-extensions python3-unittest python3-dev-utils python3-matplotlib python3-qasync python3-pyqtgraph python3-licensing python3-gql python3-authlib python3-qdarkstyle python3-defusedxml python3-systemd python3-sqlite3 python3-iscat"
