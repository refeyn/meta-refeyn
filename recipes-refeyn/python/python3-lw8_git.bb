SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "gitsm://git@github.com/refeyn/lw8.git;protocol=ssh;branch=master;lfs=0 \
           file://0001-Remove-internal-dependencies-and-C-extension-from-se.patch \
           "

# Modify these as desired
PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV = "0bf9013e4ae58a2a14a193bd61b17abae5dbfaf1"

S = "${WORKDIR}/git"

inherit setuptools3

do_compile_ui() {
    cd ${S}
    rm -f lw8/.ui_cache
    echo '#!/usr/bin/env bash
${STAGING_DIR_NATIVE}/usr/libexec/uic -g python "$@"' > ${STAGING_BINDIR_NATIVE}/pyside6-uic
    chmod +x ${STAGING_BINDIR_NATIVE}/pyside6-uic
    compile_ui lw8
}
addtask do_compile_ui before do_compile after do_configure

DEPENDS += "python3-dev-utils-native"

RDEPENDS:${PN} += "python3-iscat python3-iscat-utils python3-more-itertools python3-pint python3-tabulate"
