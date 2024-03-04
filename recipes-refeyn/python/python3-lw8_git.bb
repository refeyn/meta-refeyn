SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/lw8.git;protocol=ssh;branch=feature/CMS-20-do-not-build-mcs2-on-non-windows \
           file://eglfs.json \
           file://lw8.service \
           file://factory_settings.json \
           "

PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV = "5e67439b065b5c794584ec0de098ef06df204fed"

S = "${WORKDIR}/git"

inherit setuptools3

do_compile_ui() {
    cd ${S}
    rm -f lw8/.ui_cache
    echo '#!/usr/bin/env bash
${STAGING_DIR_NATIVE}/usr/libexec/uic -g python "$@"' > ${STAGING_BINDIR_NATIVE}/pyside6-uic
    chmod +x ${STAGING_BINDIR_NATIVE}/pyside6-uic
    dev_utils compile-ui lw8
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
