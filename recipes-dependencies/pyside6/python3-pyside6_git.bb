require python3-pyside6.inc

DEPENDS += "python3-shiboken6-native python3-shiboken6"

OECMAKE_SOURCEPATH = "${S}/sources/pyside6"

SRC_URI += " file://0001-Work-around-native-target-include-path-confusion.patch"
EXTRA_OECMAKE += " \
    -DBUILD_TESTS=FALSE \
    -DQFP_QT_HOST_PATH=${WORKDIR}/recipe-sysroot-native/usr \
    -DQFP_QT_TARGET_PATH=${WORKDIR}/recipe-sysroot/usr \
    -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
    -DQFP_SHIBOKEN_HOST_PATH=${STAGING_BINDIR_NATIVE}/shiboken6 \
    -DQFP_PYTHON_HOST_PATH=${PYTHON} \
"

INSANE_SKIP:${PN} += "already-stripped"
FILES:${PN} += "${datadir}/PySide6/*"

BBCLASSEXTEND = "native nativesdk"
RDEPENDS:${PN} += "python3-numpy ${PYSIDE_QTMODULES}"
