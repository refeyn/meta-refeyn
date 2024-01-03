SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "gitsm://git@github.com/refeyn/lw8.git;protocol=ssh;branch=feature/CM-522-embedded-compatibility;lfs=0 \
           file://0001-Remove-internal-dependencies-and-C-extension-from-se.patch \
           file://eglfs.json \
           file://lw8.service \
           file://factory_settings.json \
           "

PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV = "5dfd144ca0fd6357a35197c9e6776226efbcf170"

S = "${WORKDIR}/git"

inherit setuptools3

do_write_version() {
    cd ${S}
    echo "VERSION_SHA = '$(git rev-parse HEAD)'" > lw8/_versionSha.py
}
addtask do_write_version before do_compile after do_configure

do_compile_ui() {
    cd ${S}
    rm -f lw8/.ui_cache
    echo '#!/usr/bin/env bash
${STAGING_DIR_NATIVE}/usr/libexec/uic -g python "$@"' > ${STAGING_BINDIR_NATIVE}/pyside6-uic
    chmod +x ${STAGING_BINDIR_NATIVE}/pyside6-uic
    compile_ui lw8
}
addtask do_compile_ui before do_compile after do_configure

FILES:${PN} += "${systemd_unitdir}/system/lw8.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "lw8.service"


do_install:append() {
    install -d ${D}${sysconfdir}/Refeyn/AcquireMP/
    cp ${WORKDIR}/eglfs.json ${D}${sysconfdir}/Refeyn/AcquireMP/
    cp ${WORKDIR}/factory_settings.json ${D}${sysconfdir}/Refeyn/AcquireMP/

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lw8.service ${D}/${systemd_unitdir}/system
}


DEPENDS += "python3-dev-utils-native"

RDEPENDS:${PN} += "python3-iscat python3-iscat-utils python3-more-itertools python3-pint python3-tabulate python3-aiohttp ximea-xiapi python3-spinnaker python3-fluigent python-periphery python3-pyserial python3-prctl"
