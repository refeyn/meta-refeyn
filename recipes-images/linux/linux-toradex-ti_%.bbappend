SRC_URI:remove = "git://git.toradex.com/linux-toradex.git;protocol=https;branch=${SRCBRANCH};name=machine"
SRC_URI:remove = "${KCONFIG_REPO};protocol=https;type=kmeta;name=meta-toradex-bsp;branch=main;destsuffix=${KMETA}"
SRC_URI += " \
    ${KERNEL_SRC};protocol=ssh;branch=${SRCBRANCH};name=machine \
    ${KCONFIG_REPO};protocol=ssh;type=kmeta;name=meta-toradex-bsp;branch=master;destsuffix=${KMETA} \
"

SRCBRANCH = "refeyn_ti-linux-6.6.y"
KERNEL_SRC = "git://git@github.com/refeyn/linux.git"
KCONFIG_REPO = "git://git@github.com/refeyn/linux-toradex-kconfig.git"

SRCREV_meta-toradex-bsp = "17deac27b9765b05bd12a75741d3c7e0080deebd"
SRCREV_machine = "3fc7678a1283915033ee188053d19b0e896512ef"

# Remove Torizon features
KERNEL_FEATURES:remove = "bsp/${MACHINE}-${LINUX_KERNEL_TYPE}-torizon.scc"
KERNEL_FEATURES += "bsp/aquila-am69-${LINUX_KERNEL_TYPE}-torizon.scc"
