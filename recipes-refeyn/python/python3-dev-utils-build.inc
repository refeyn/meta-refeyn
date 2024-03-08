inherit setuptools3

do_compile:prepend() {
    export NO_CYTHONIZE=${REFEYN_NO_CYTHONIZE}
    rm -rf ${S}/build
}

do_compile_ui() {
    cd ${S}
    echo '#!/usr/bin/env bash
${STAGING_DIR_NATIVE}/usr/libexec/uic -g python "$@"' > ${STAGING_BINDIR_NATIVE}/pyside6-uic
    chmod +x ${STAGING_BINDIR_NATIVE}/pyside6-uic
    dev_utils repo compile-ui --no-cache
}
addtask do_compile_ui before do_compile after do_configure

DEPENDS += "python3-dev-utils-native"
