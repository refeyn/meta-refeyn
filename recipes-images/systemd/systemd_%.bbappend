FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-systemd-Disable-tty1-console-if-splash-is-present.patch \
"
