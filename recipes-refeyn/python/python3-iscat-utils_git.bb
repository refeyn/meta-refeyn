SUMMARY = "ISCAT_UTILS - utilities package for mass photometry software"
HOMEPAGE = "https://github.com/refeyn/iscat_utils"

LICENSE = "CLOSED"

SRC_URI = "gitsm://git@github.com/refeyn/iscat_utils.git;protocol=ssh;branch=feature/posix-compatability"

# Modify these as desired
PV = "1.44.0+git${SRCPV}"
SRCREV = "6a4837743a991aae0375da0c87ec0c0d7748dc77"

S = "${WORKDIR}/git"

inherit setuptools3

do_write_version() {
    echo "VERSION_SHA = '${SRCREV}'" > ${S}/iscat_utils/_versionSha.py
}
addtask do_write_version before do_compile after do_configure

do_compile_ui() {
    cd ${S}
    rm -f iscat_utils/.ui_cache
    echo '#!/usr/bin/env bash
${STAGING_DIR_NATIVE}/usr/libexec/uic -g python "$@"' > ${STAGING_BINDIR_NATIVE}/pyside6-uic
    chmod +x ${STAGING_BINDIR_NATIVE}/pyside6-uic
    compile_ui iscat_utils
}
addtask do_compile_ui before do_compile after do_configure

DEPENDS += "python3-dev-utils-native"

RDEPENDS:${PN} += "python3-asyncio python3-core python3-crypt python3-ctypes python3-datetime python3-html python3-image python3-io python3-json python3-logging python3-misc python3-multiprocessing python3-netclient python3-netserver python3-numpy python3-packaging python3-pillow python3-profile python3-pyside6 python3-pytest python3-shell python3-threading python3-typing-extensions python3-unittest python3-dev-utils python3-matplotlib python3-qasync python3-pyqtgraph python3-rollbar python3-licensing python3-gql python3-authlib python3-qdarkstyle"
