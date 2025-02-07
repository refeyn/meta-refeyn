FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-Add-Refeyn-bg-colors.patch \
    file://99-psplash.rules \
    "
SPLASH_IMAGES = "file://refeyn.png;outsuffix=default"
EXTRA_OEMAKE = "CFLAGS=-DPSPLASH_DISABLE_PROGRESS_BAR"

do_install:append() {
    install -d ${D}${prefix}/lib/udev/rules.d
    install -m 0644 ${WORKDIR}/99-psplash.rules ${D}${prefix}/lib/udev/rules.d
}

FILES:${PN} += " \
    ${prefix}/lib/udev/rules.d \
"
