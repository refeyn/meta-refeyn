LICENSE = "Ximea-Licence"
LIC_FILES_CHKSUM = "file://License.txt;md5=5c5ee3c662eb48159d834b005f362839"

NO_GENERIC_LICENSE[Ximea-Licence] = "License.txt"

SRC_URI = "https://www.ximea.com/downloads/recent/XIMEA_Linux_ARM_SP.tgz;subdir=src"
SRC_URI[sha256sum] = "8230435dafdaae1f3e6929a9f8d12393547702d57bd713909da8221955a7a08e"

S = "${WORKDIR}/src/package"

inherit python3-dir

DEBIAN_NOAUTONAME:${PN} = "1"
DEBIAN_NOAUTONAME:${PN}-dev = "1"
DEBIAN_NOAUTONAME:${PN}-dbg = "1"

DEPENDS += "libusb1 tiff"
RDEPENDS:${PN} += "lshw libusb1 libcurl bash tiff zlib kernel-usb-memory-limit"
PREFERRED_VERSION_tiff = "4.4.0"

COMPATIBLE_HOST = "(aarch64|armv7a).*-linux"
XIMEA_ARCH ?= "${TARGET_ARCH}"
XIMEA_ARCH:armv7a = "arm32"
XIMEA_ARCH:aarch64 = "arm64"

FILES:${PN} += "/opt/XIMEA ${PYTHON_SITEPACKAGES_DIR}/ximea"
INSANE_SKIP:${PN} += "already-stripped ldflags"
SYSROOT_DIRS += "/opt"

do_install() {
    optpath=${D}/opt/XIMEA
    install -d $optpath
    install -d $optpath/bin
    install -d $optpath/lib
    install -d $optpath/include
    install -d $optpath/data

    install -d ${D}${sysconfdir}/udev/rules.d/
    install libs/libusb/99-ximea.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}${libdir}
    install api/X${XIMEA_ARCH}/libm3api.so.2 ${D}${libdir}/libm3api.so.2.0.0
    ln -s -r ${D}${libdir}/libm3api.so.2.0.0 ${D}${libdir}/libm3api.so.2
    ln -s -r ${D}${libdir}/libm3api.so.2.0.0 ${D}${libdir}/libm3api.so

    cp -R include $optpath/

    install -d ${D}${includedir}
    ln -s -r $optpath/include ${D}${includedir}/m3api

    cp libs/gentl/X${XIMEA_ARCH}/*.cti $optpath/lib/

    cp libs/xiapi_dng_store/X${XIMEA_ARCH}/libxiapi_dng_store.so $optpath/lib/

    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/ximea/libs/x${XIMEA_ARCH}
    install api/Python/v3/ximea/*.py ${D}${PYTHON_SITEPACKAGES_DIR}/ximea
    cp -R api/Python/v3/ximea/libs/x${XIMEA_ARCH} ${D}${PYTHON_SITEPACKAGES_DIR}/ximea/libs

    cp bin/xiSample.${XIMEA_ARCH} $optpath/bin/xiSample

    cp bin/xiCOP.${XIMEA_ARCH} $optpath/bin/xiCOP
    cp data/fw_update_tools_map.xml $optpath/data/

    # Disable as it depends on X11
    # cp bin/xiCamTool $optpath/bin/
    # install -d $optpath/CamTool
    # cp -R CamTool.${XIMEA_ARCH}/* $optpath/CamTool
    # Licences are (add "& FreeImage"):
    # file://CamTool.64/license-fi.txt;md5=7d2690b4d6d7dd53d69a773664bc4850 \
    # file://CamTool.arm64/license-fi.txt;md5=7d2690b4d6d7dd53d69a773664bc4850 \
}
