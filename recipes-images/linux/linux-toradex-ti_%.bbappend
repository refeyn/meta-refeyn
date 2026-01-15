FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:remove = "git://git.toradex.com/linux-toradex.git;protocol=https;branch=${SRCBRANCH};name=machine"
SRC_URI += " \
    ${KERNEL_SRC};protocol=ssh;branch=${SRCBRANCH};name=machine \
    file://config.cfg \
"

SRCBRANCH = "refeyn_ti-linux-6.6.y"
KERNEL_SRC = "git://git@github.com/refeyn/linux.git"
KMACHINE = "aquila-am69"

SRCREV_meta-toradex-bsp = "17deac27b9765b05bd12a75741d3c7e0080deebd"
SRCREV_machine = "3fc7678a1283915033ee188053d19b0e896512ef"
