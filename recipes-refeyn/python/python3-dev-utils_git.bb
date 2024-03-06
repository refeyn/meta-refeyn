SUMMARY = "DEV_UTILS - development utilities package for Refeyn Ltd"
HOMEPAGE = "https://github.com/refeyn/dev_utils"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://git@github.com/refeyn/dev_utils.git;protocol=ssh;branch=feature/CMS-28-option-to-ignore-cache-compile-ui"

PV = "1.41.0+git${SRCPV}"
SRCREV = "dc3401b85622743c19d2f612a9b703f834f7f319"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} += "python3-core python3-crypt python3-cython python3-datetime python3-io python3-json python3-logging python3-multiprocessing python3-packaging python3-profile python3-setuptools python3-typing-extensions python3-xml python3-pyside6 python3-click python3-jinja2 python3-pydantic python3-tomlkit"

BBCLASSEXTEND = "native nativesdk"
