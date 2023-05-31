LICENSE = "FLIR-Licence & LGPL-2.1-or-later & FreeImage"
# FLIR also provides a PDF full of licences, but I'm not sure the software is included in the packages
LIC_FILES_CHKSUM = " \
    file://spinnaker-3.1.0.79-arm64/README_ARM;endline=16;md5=528e7cd095cc84850d1b14f9ff138115 \
    file://LICENCE.txt;md5=e48e6c2aa0be8b393f34d934b222db92 \
"

NO_GENERIC_LICENSE[FLIR-Licence] = "LICENCE.txt"

SRC_URI = " \
    https://flir.netx.net/file/asset/54399/original/attachment;downloadfilename=spinnaker-3.1.0.79-arm64-pkg.tar.gz \
    file://40-flir-spinnaker.rules \
    file://LICENCE.txt \
"
SRC_URI[sha256sum] = "9b215b3f1fdc895ace374cebd834b83369054073fd765e37d8a191f4e23642ba"

S = "${WORKDIR}"

COMPATIBLE_HOST = "aarch64.*-linux"
RDEPENDS:${PN} += "libusb1 zlib bash kernel-usb-memory-limit"
DEPENDS = "tar-native zstd-native opkg-utils-native"
FILES:${PN} += "/opt/spinnaker"
INSANE_SKIP:${PN} += "already-stripped dev-so"

do_install() {
    cd spinnaker-3.1.0.79-arm64
    for f in \
        libgentl_*.deb \
        libspinnaker_*.deb \
        libspinnaker-c_*.deb \
        libspinvideo_*.deb \
        libspinvideo-c_*.deb \
        spinupdate_*.deb \
        spinnaker_*.deb
    do
        rm -rf extracttmp
        mkdir extracttmp
        ar -x $f --output extracttmp
        tar -xf extracttmp/data.* -C ${D}
    done

    install -d ${D}${sysconfdir}/udev/rules.d/
    install ../40-flir-spinnaker.rules ${D}${sysconfdir}/udev/rules.d/
}
