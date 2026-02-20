DESCRIPTION = "The ultimate Python library in building OAuth and OpenID Connect servers and clients."
HOMEPAGE = "https://authlib.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ec053ca028e37ec79d3da3af34976d48"
PYPI_PACKAGE = "authlib"

SRC_URI[sha256sum] = "45770e8e056d0f283451d9996fbb59b70d45722b45d854d58f32878d0a40c38e"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-cryptography \
"
