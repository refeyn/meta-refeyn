DESCRIPTION = "A scientific graphing library for QtQuick"
HOMEPAGE = "https://github.com/refeyn/QuickGraphLib"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE;md5=292bbc415362ce0cd996cedf5dcf7235"

inherit qt6-cmake python_setuptools_build_meta

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.com/refeyn/QuickGraphLib.git;protocol=ssh;tag=v0.1.0a6;branch=master"

PV = "v0.1.0+git${SRCPV}"
# SRCREV = "${AUTOREV}"
DEPENDS += "qtbase qtbase-native qtdeclarative qtsvg qtdeclarative-native cmake python3-setuptools-git-versioning-native"
RDEPENDS:${PN} += "qtbase qtdeclarative qtsvg"

do_compile:prepend() {
    export CMAKE_BUILD_FLAGS=" \
        ${OECMAKE_GENERATOR_ARGS} \
        -DCMAKE_INSTALL_PREFIX:PATH=${prefix} \
        -DCMAKE_INSTALL_BINDIR:PATH=${@os.path.relpath(d.getVar('bindir'), d.getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_SBINDIR:PATH=${@os.path.relpath(d.getVar('sbindir'), d.getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_LIBEXECDIR:PATH=${@os.path.relpath(d.getVar('libexecdir'), d.getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_SYSCONFDIR:PATH=${sysconfdir} \
        -DCMAKE_INSTALL_SHAREDSTATEDIR:PATH=${@os.path.relpath(d.getVar('sharedstatedir'), d.  getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_LOCALSTATEDIR:PATH=${localstatedir} \
        -DCMAKE_INSTALL_LIBDIR:PATH=${@os.path.relpath(d.getVar('libdir'), d.getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_INCLUDEDIR:PATH=${@os.path.relpath(d.getVar('includedir'), d.getVar('prefix') + '/')} \
        -DCMAKE_INSTALL_DATAROOTDIR:PATH=${@os.path.relpath(d.getVar('datadir'), d.getVar('prefix') + '/')} \
        -DPYTHON_EXECUTABLE:PATH=${PYTHON} \
        -DPython_EXECUTABLE:PATH=${PYTHON} \
        -DPython3_EXECUTABLE:PATH=${PYTHON} \
        -DLIB_SUFFIX=${@d.getVar('baselib').replace('lib', '')} \
        -DCMAKE_INSTALL_SO_NO_EXE=0 \
        -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
        -DCMAKE_NO_SYSTEM_FROM_IMPORTED=1 \
        -DCMAKE_EXPORT_NO_PACKAGE_REGISTRY=ON \
        -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
        ${EXTRA_OECMAKE} \
        -Wno-dev"
}
