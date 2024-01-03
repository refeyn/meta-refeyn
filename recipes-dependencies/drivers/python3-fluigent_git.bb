LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=21b75caf4e262c22ec436f397612256d"

SRC_URI = " \
    git://github.com/Fluigent/fgt-SDK.git;protocol=https;branch=master \
    file://99-fluigent.rules \
"

PV = "1.0+git${SRCPV}"
SRCREV = "e158ea873f0ed7ac7bbbcc607496cfe19f8448aa"
RDEPENDS:${PN} = "python3-core libudev"
FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/Fluigent"
INSANE_SKIP:${PN} += "already-stripped"

S = "${WORKDIR}/git"

inherit python3-dir

COMPATIBLE_HOST = "(aarch64|armv7a|x64).*-linux"
FGT_ARCH ?= "${TARGET_ARCH}"
FGT_ARCH:armv7a = "arm"
FGT_ARCH:aarch64 = "arm64"
FGT_ARCH:x64 = "x64"

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/Fluigent/SDK/shared/linux/${FGT_ARCH}
    install Python/Fluigent/*.py ${D}${PYTHON_SITEPACKAGES_DIR}/Fluigent
    install Python/Fluigent/SDK/*.py ${D}${PYTHON_SITEPACKAGES_DIR}/Fluigent/SDK
    install Python/Fluigent/SDK/shared/linux/${FGT_ARCH}/*.so ${D}${PYTHON_SITEPACKAGES_DIR}/Fluigent/SDK/shared/linux/${FGT_ARCH}

    install -d ${D}${sysconfdir}/udev/rules.d/
    install ../99-fluigent.rules ${D}${sysconfdir}/udev/rules.d/
}
