DESCRIPTION = "Scientific Graphics and GUI Library for Python"
HOMEPAGE = "http://www.pyqtgraph.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f3e5f0e3bfada0ee09266e5402b0a690"

SRC_URI += "file://0001-Remove-pyproject-because-it-trips-up-picoinstall-no-.patch"
SRC_URI[sha256sum] = "58108d8411c7054e0841d8b791ee85e101fc296b9b359c0e01dde38a98ff2ace"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-pyside6 \
    ${PYTHON_PN}-numpy \
"
