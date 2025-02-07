SUMMARY = "EDID override for HDMI connector with broken DDC lines"

LICENSE = "CLOSED"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://hdmi-edid.bin \
"

do_install:append() {
    install -d ${D}${prefix}/lib/firmware/edid
    install -m 0644 ${WORKDIR}/hdmi-edid.bin ${D}${prefix}/lib/firmware/edid
}

FILES:${PN} += " \
    ${prefix}/lib/firmware/edid \
"

