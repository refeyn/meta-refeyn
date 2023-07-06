SUMMARY = "A pure Python 2/3 library for peripheral I/O (GPIO, LED, PWM, SPI, I2C, MMIO, Serial) in Linux."
HOMEPAGE = "https://github.com/vsergeev/python-periphery"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=30fe6f023a80fb33989fb3b9d773fea0"

SRC_URI = "git://github.com/vsergeev/python-periphery.git;protocol=https;branch=master"

PV = "2.4.1+git${SRCPV}"
SRCREV = "7d46dfdbbc367dfaaff1e699062a5e29a0c300f0"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} += "python3-core python3-ctypes python3-io python3-mmap"
