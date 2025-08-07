DESCRIPTION = "Python wrapper around NXP LIBUSBSIO library"
HOMEPAGE = "https://www.nxp.com/design/software/development-software/library-for-windows-macos-and-ubuntu-linux:LIBUSBSIO"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license/BSD-3-clause.txt;md5=1076c1c40acc679330f3d60bfea1c23b"

SRC_URI[sha256sum] = "df1b9d4b2a9f5eadf0b0574e8017862b59d26343598f1f548664ea6d01975b25"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-ctypes libudev \
"

do_install:append() {
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/linux_i686
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/linux_x86_64
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/x64
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/linux_armv7l
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/Win32
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/osx_arm64
    rm -r ${D}/${PYTHON_SITEPACKAGES_DIR}/libusbsio/bin/osx_x86_64
}
