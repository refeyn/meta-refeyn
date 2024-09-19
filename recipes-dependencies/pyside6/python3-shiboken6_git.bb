require python3-pyside6.inc

DEPENDS += "clang-native python3-shiboken6-native"

OECMAKE_SOURCEPATH = "${S}/sources/shiboken6"

NUMPY_DIR = "${PYTHON_SITEPACKAGES_DIR}/numpy/core/include/"
NUMPY_DIR:class-target = "${WORKDIR}/recipe-sysroot/${PYTHON_SITEPACKAGES_DIR}/numpy/core/include/"
EXTRA_OECMAKE += "-DSHIBOKEN_BUILD_LIBS=ON -DNUMPY_INCLUDE_DIR=${NUMPY_DIR}"
EXTRA_OECMAKE:append:class-target = " -DQFP_SHIBOKEN_HOST_PATH=${STAGING_BINDIR_NATIVE}/shiboken6 -DQFP_PYTHON_HOST_PATH=${PYTHON}"

INSANE_SKIP:${PN} += "already-stripped"

BBCLASSEXTEND = "native nativesdk"
