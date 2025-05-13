DESCRIPTION = "An interface to Linux's prctl() syscall written in pure Python using ctypes."
HOMEPAGE = "https://github.com/cptpcrd/pyprctl"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f74077c8075122645e5255bad7a1cf5"

SRC_URI[sha256sum] = "1fb54d3ab030ec02e4afc38fb9662d6634c12834e91ae7959de56a9c09f69c26"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-ctypes \
"
