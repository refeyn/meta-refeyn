SUMMARY = "Lite prototype UI"

LICENSE = "CLOSED"

SRC_URI = "file://doit.py \
           file://liteController.py \
           file://Main.qml \
           file://MainInABox.qml \
           file://eglfs.json \
           file://liteproto.service \
           "

RDEPENDS:${PN} += "python3-lw8 python3-prctl"


FILES:${PN} += "/usr/bin/liteproto ${systemd_unitdir}/system/liteproto.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "liteproto.service"


do_install() {
    install -d ${D}${bindir}
    install -d ${D}${bindir}/liteproto
    cp ${WORKDIR}/*.py ${D}${bindir}/liteproto
    cp ${WORKDIR}/*.qml ${D}${bindir}/liteproto
    cp ${WORKDIR}/*.json ${D}${bindir}/liteproto

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/liteproto.service ${D}/${systemd_unitdir}/system
}
