FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Add-Refeyn-bg-colors.patch"
SPLASH_IMAGES = "file://refeyn.png;outsuffix=default"
EXTRA_OEMAKE = "CFLAGS=-DPSPLASH_DISABLE_PROGRESS_BAR"
