# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022 NXP

DESCRIPTION = "i.MX System Controller Firmware with modifications for Refeyn boards"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"
SECTION = "BSP"

inherit deploy

SRC_URI = " \
    git://git@github.com/refeyn/i.MX-System-Controller-Firmware.git;protocol=ssh;branch=master \
    https://developer.arm.com/-/media/Files/downloads/gnu-rm/6-2017q2/gcc-arm-none-eabi-6-2017-q2-update-linux.tar.bz;downloadfilename=gcc-toolchain.tar.bz \
"
SRC_URI[sha256sum] = "e68e4b2fe348ecb567c27985355dff75b65319a0f6595d44a18a8c5e05887cc3"

PV = "1+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

PROVIDES = "imx-sc-firmware"
RREPLACES:${PN} = "imx-sc-firmware"
RPROVIDES:${PN} = "imx-sc-firmware"
RCONFLICTS:${PN} = "imx-sc-firmware"

BOARD_TYPE ?= "unknown"
SC_FIRMWARE_NAME ?= "INVALID"
SC_FIRMWARE_NAME:mx8qm-nxp-bsp = "mx8qm-${BOARD_TYPE}-scfw-tcm.bin"

symlink_name = "scfw_tcm.bin"

BOOT_TOOLS = "imx-boot-tools"

SCFW_DEBUG_FLAGS = "D=0 M=0"
# SCFW_DEBUG_FLAGS = " D=1 DL=3 M=1"

do_compile() {
    if [ ! -d ${WORKDIR}/gcc-toolchain ]; then
        mkdir ${WORKDIR}/gcc-toolchain
        tar xf ${WORKDIR}/gcc-toolchain.tar.bz -C ${WORKDIR}/gcc-toolchain
    fi
    export TOOLS=${WORKDIR}/gcc-toolchain
    unset LDFLAGS
    unset CFLAGS
    unset CC

    cd ${S}/scfw_export_mx8qm_b0
    make clean
    make qm R=B0 ${SCFW_DEBUG_FLAGS} B=ec_asm_00014_01 U=1
    cp build_mx8qm_b0/scfw_tcm.bin ${S}/${SC_FIRMWARE_NAME}
}

do_install[noexec] = "1"

do_deploy() {
    install -Dm 0644 ${S}/${SC_FIRMWARE_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}/${SC_FIRMWARE_NAME}
    ln -sf ${SC_FIRMWARE_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}/${symlink_name}
}
addtask deploy after do_install

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(mx8qm-ec-asm-00014-01)"
