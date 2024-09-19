SUMMARY = "Generate QML stub files (.qmltypes) from Python modules (which use PySide6)"
HOMEPAGE = "https://github.com/matsjoyce/pyside6-qml-stubgen"
LICENSE = "LGPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENCE.md;md5=7f848ce64a560ae1b89024d70ccf883e"

SRC_URI[sha256sum] = "876f2174899d3ad04d4b1ae993a8da640d416eb57877b739ba24122d06cdf938"

inherit pypi python_hatchling
PYPI_PACKAGE = "pyside6_qml_stubgen"
RDEPENDS:${PN} += "python3-pyside6 python3-pydantic python3-docopt"
DEPENDS += "python3-hatch-vcs-native"

BBCLASSEXTEND = "native nativesdk"
