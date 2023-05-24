DESCRIPTION = "The ultimate Python library in building OAuth and OpenID Connect servers and clients."
HOMEPAGE = "https://authlib.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ec053ca028e37ec79d3da3af34976d48"
PYPI_PACKAGE = "Authlib"

SRC_URI[sha256sum] = "4fa3e80883a5915ef9f5bc28630564bc4ed5b5af39812a3ff130ec76bd631e9d"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-cryptography \
"
