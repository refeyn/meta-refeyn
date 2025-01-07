do_install:append() {
    mv ${D}${sysconfdir}/udev/rules.d/automount.rules ${D}${sysconfdir}/udev/rules.d/automount.rules.default
}
