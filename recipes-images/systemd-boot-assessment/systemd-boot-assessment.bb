LICENSE = "CLOSED"

SRC_URI = " \
    file://systemd-bless-boot.service \
    file://systemd-bless-boot.py \
"

inherit systemd

SYSTEMD_SERVICE:${PN} = "systemd-bless-boot.service"

FILES:${PN} += "${systemd_unitdir}/"
RDEPENDS:${PN} += "python3-core"

do_install() {
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd-bless-boot.service ${D}/${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/systemd-bless-boot.py ${D}/${systemd_unitdir}
}
