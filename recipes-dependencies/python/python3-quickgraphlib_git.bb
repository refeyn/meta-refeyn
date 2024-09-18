DESCRIPTION = "A scientific graphing library for QtQuick"
HOMEPAGE = "https://github.com/refeyn/QuickGraphLib"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE;md5=292bbc415362ce0cd996cedf5dcf7235"

inherit qt6-cmake setuptools3-base

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/refeyn/QuickGraphLib.git;protocol=ssh;branch=master \
           file://0001-Fix-cross-comilation.patch \
           "

PV = "v0.1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"
DEPENDS += " \
    qtbase \
    qtbase-native \
    qtdeclarative \
    qtdeclarative-native \
    qtsvg \
    cmake \
    python3-shiboken6 \
    python3-shiboken6-native \
    python3-pyside6 \
    python3-pyside6-native \
"
RDEPENDS:${PN} += "qtbase qtdeclarative qtsvg"

EXTRA_OECMAKE += " \
    -DBUILD_TESTS=FALSE \
    -DQFP_QT_HOST_PATH=${WORKDIR}/recipe-sysroot-native/usr \
    -DQFP_QT_TARGET_PATH=${WORKDIR}/recipe-sysroot/usr \
    -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
    -DQFP_SHIBOKEN_HOST_PATH=${STAGING_BINDIR_NATIVE}/shiboken6 \
    -DQFP_PYTHON_HOST_PATH=${PYTHON} \
    -DINSTALL_SUBPATH=${PYTHON_SITEPACKAGES_DIR} \
"
