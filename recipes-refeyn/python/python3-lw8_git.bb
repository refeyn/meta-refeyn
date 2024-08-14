SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/lw8.git;protocol=ssh;branch=master;destsuffix=lw8 \
           file://eglfs.json \
           file://lw8.service \
           "

PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/lw8"

# HACK lfs aint working

do_unpack[network] = "1"

require python3-dev-utils-build.inc

FILES:${PN} += "${systemd_unitdir}/system/lw8.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "lw8.service"

do_install:append() {
    install -d ${D}${sysconfdir}/Refeyn/AcquireMP/
    cp ${WORKDIR}/eglfs.json ${D}${sysconfdir}/Refeyn/AcquireMP/

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lw8.service ${D}/${systemd_unitdir}/system
}

RDEPENDS:${PN} += "python3-iscat python3-iscat-utils python3-more-itertools python3-pint python3-tabulate python3-aiohttp ximea-xiapi python3-spinnaker python3-fluigent python3-periphery python3-pyserial python3-prctl python3-quickgraphlib python3-statistics python3-betterproto python3-pyudev"
