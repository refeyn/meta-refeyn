LICENSE = "CLOSED"

SRC_URI = " \
    file://kernel_usb_memory_limit.service \
"

inherit systemd

SYSTEMD_SERVICE:${PN} = "kernel_usb_memory_limit.service"

FILES:${PN} += "${systemd_unitdir}/system/kernel_usb_memory_limit.service"

do_install() {
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/kernel_usb_memory_limit.service ${D}/${systemd_unitdir}/system
}
