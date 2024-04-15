# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022 NXP

DESCRIPTION = "i.MX System Controller Firmware with modifications for Refeyn boards"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"
SECTION = "BSP"

inherit deploy

SRC_URI = "git://git@github.com/refeyn/i.MX-System-Controller-Firmware.git;branch=master;protocol=ssh;fsl-eula=true"

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

do_compile[noexec] = "1"

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
