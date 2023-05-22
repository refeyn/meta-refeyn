DESCRIPTION = "Implementation of the PEP 3156 Event-Loop with Qt."
HOMEPAGE = "https://github.com/CabbageDevelopment/qasync"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69e205dd99649bd02cfdd312d8161144"

SRC_URI[sha256sum] = "e583d1c3ae20fd12e908dee358c527709b480e78d57fb72ee57a94097307d959"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-asyncio \
"
