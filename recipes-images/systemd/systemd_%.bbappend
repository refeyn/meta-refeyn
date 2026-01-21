FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Network settings

SRC_URI += " \
    file://0001-systemd-Disable-tty1-console-if-splash-is-present.patch \
    file://rndis.network \
    file://persistent-journal.conf \
    file://50-refeyn.preset \
"

PACKAGECONFIG:append = " networkd importd xz zlib bzip2 gcrypt"

PACKAGECONFIG[acl] = "-Dacl=true,-Dacl=false,acl"

do_install:append() {
    # The network files need to be in /usr/lib/systemd, not ${systemd_unitdir}...
    install -d ${D}${prefix}/lib/systemd/network/
    install -m 0644 ${WORKDIR}/rndis.network ${D}${prefix}/lib/systemd/network/

    # Persistent journal
    install -d ${D}${prefix}/lib/systemd/journald.conf.d/
    install -m 0644 ${WORKDIR}/persistent-journal.conf ${D}${prefix}/lib/systemd/journald.conf.d/

    # Disable network wait
    install -m 0644 ${WORKDIR}/50-refeyn.preset ${D}${prefix}/lib/systemd/system-preset
}

FILES:${PN} += " \
    ${nonarch_base_libdir}/systemd \
"

# Sysupdate

EXTRA_OEMESON:append = " \
    -Dfdisk=enabled \
    -Dsysupdate=enabled \
    -Dopenssl=enabled \
    -Dlibcurl=enabled \
"
DEPENDS += "curl"
SRC_URI += " \
    file://60-rootfs.conf \
    file://70-kernel.conf \
"
do_install:append() {
    install -d ${D}${base_libdir}/sysupdate.d
    # Note: In more recent versions of systemd, these should end in .transfer
    install -m 0644 ${WORKDIR}/60-rootfs.conf ${D}${base_libdir}/sysupdate.d/
    install -m 0644 ${WORKDIR}/70-kernel.conf ${D}${base_libdir}/sysupdate.d/
}


FILES:${PN} += " \
    ${nonarch_base_libdir}/sysupdate.d \
"
