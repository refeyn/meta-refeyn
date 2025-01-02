SRC_URI:remove = "git://git.toradex.com/linux-toradex.git;protocol=https;branch=${SRCBRANCH};name=machine"
SRC_URI:remove = "${KCONFIG_REPO};protocol=https;type=kmeta;name=meta-toradex-bsp;branch=main;destsuffix=${KMETA}"
SRC_URI += " \
    ${KERNEL_SRC};protocol=ssh;branch=${SRCBRANCH};name=machine \
    ${KCONFIG_REPO};protocol=ssh;type=kmeta;name=meta-toradex-bsp;branch=master;destsuffix=${KMETA} \
"

SRCBRANCH = "refeyn_6.6-2.1.x-imx"
KERNEL_SRC = "git://git@github.com/refeyn/linux.git"
KCONFIG_REPO = "git://git@github.com/refeyn/linux-toradex-kconfig.git"

SRCREV_meta-toradex-bsp = "96cf3fa393b30b16adac4fd6be06f6d48d248d4b"
SRCREV_machine = "3fc7678a1283915033ee188053d19b0e896512ef"
