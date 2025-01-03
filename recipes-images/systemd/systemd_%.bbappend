FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-systemd-Disable-tty1-console-if-splash-is-present.patch \
    file://rndis.network \
    file://persistent-journal.conf \
"

PACKAGECONFIG:append = " networkd"

PACKAGECONFIG[acl] = "-Dacl=true,-Dacl=false,acl"

do_install:append() {
    # The network files need to be in /usr/lib/systemd, not ${systemd_unitdir}...
    install -d ${D}${prefix}/lib/systemd/network/
    install -m 0644 ${WORKDIR}/rndis.network ${D}${prefix}/lib/systemd/network/

    # Persistent journal
    install -d ${D}${prefix}/lib/systemd/journald.conf.d/
    install -m 0644 ${WORKDIR}/persistent-journal.conf ${D}${prefix}/lib/systemd/journald.conf.d/
}

FILES:${PN} += " \
    ${nonarch_base_libdir}/systemd \
"
