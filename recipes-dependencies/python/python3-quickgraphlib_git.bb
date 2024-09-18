DESCRIPTION = "A scientific graphing library for QtQuick"
HOMEPAGE = "https://github.com/refeyn/QuickGraphLib"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE;md5=292bbc415362ce0cd996cedf5dcf7235"

inherit qt6-cmake python_pep517

S = "${WORKDIR}/git"

SRC_URI = " \
    git://git@github.com/refeyn/QuickGraphLib.git;protocol=ssh;branch=master \
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
    python3-scikit-build-core-native \
"
RDEPENDS:${PN} += "qtbase qtdeclarative qtsvg"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}"
EXTRA_OECMAKE += " \
    -DBUILD_TESTS=FALSE \
    -DQFP_QT_HOST_PATH=${WORKDIR}/recipe-sysroot-native/usr \
    -DQFP_QT_TARGET_PATH=${WORKDIR}/recipe-sysroot/usr \
    -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
    -DQFP_SHIBOKEN_HOST_PATH=${STAGING_BINDIR_NATIVE}/shiboken6 \
    -DQFP_PYTHON_HOST_PATH=${PYTHON} \
"

do_compile:prepend() {
    export CMAKE_ARGS=" \
        ${OECMAKE_GENERATOR_ARGS} \
        $oecmake_sitefile \
        ${OECMAKE_SOURCEPATH} \
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
        -Wno-dev \
    "
    export SKBUILD_INSTALL_STRIP="false"
}

do_install:prepend() {
    export _PYTHON_SYSCONFIGDATA_NAME="_sysconfigdata"
}
