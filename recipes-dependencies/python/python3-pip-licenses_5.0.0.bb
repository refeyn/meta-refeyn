DESCRIPTION = "Dump the software license list of Python packages installed with pip."
HOMEPAGE = "https://github.com/raimon49/pip-licenses"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3f05e9cbc47d46ae75392de99f43c94a"

SRC_URI[sha256sum] = "0633a1f9aab58e5a6216931b0e1d5cdded8bcc2709ff563674eb0e2ff9e77e8e"

inherit pypi python_setuptools_build_meta

DEPENDS += "python3-setuptools-scm-native"
RDEPENDS:${PN} += "python3-prettytable python3-tomli"

BBCLASSEXTEND = "native nativesdk"
PYPI_PACKAGE = "pip_licenses"
