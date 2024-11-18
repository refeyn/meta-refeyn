FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

BASEFILESISSUEINSTALL = "do_install_basefilesissue_simple"
do_install_basefilesissue_simple() {
	install -m 644 ${WORKDIR}/issue*  ${D}${sysconfdir}
}
