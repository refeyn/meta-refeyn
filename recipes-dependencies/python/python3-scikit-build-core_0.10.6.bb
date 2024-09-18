DESCRIPTION = "Build backend for CMake based projects"
HOMEPAGE = "https://github.com/scikit-build/scikit-build-core"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b4e748e5f102e31c9390dcd6fa66f09"

SRC_URI[sha256sum] = "5397db8f09ee050d145406c11deed06538bb0b261df95f8d2d6aaf8699f0126d"

inherit pypi python_hatchling

DEPENDS += "python3-hatchling-native python3-hatch-vcs-native"
RDEPENDS:${PN} += "python3-exceptiongroup"

BBCLASSEXTEND = "native nativesdk"
PYPI_PACKAGE = "scikit_build_core"
