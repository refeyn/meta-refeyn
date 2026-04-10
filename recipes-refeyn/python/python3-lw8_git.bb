SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/lw8.git;protocol=ssh;destsuffix=lw8;nobranch=1;name=lw8 \
           git://git@github.com/refeyn/iscat.git;protocol=ssh;destsuffix=lw8/internal_dependencies/iscat;nobranch=1;name=iscat \
           git://git@github.com/refeyn/nxp_sdk_board_support.git;protocol=ssh;nobranch=1;destsuffix=lw8/firmware/photometer_controller/src/drivers/nxp/nxp_sdk;name=nxp-sdk \
           https://developer.arm.com/-/media/Files/downloads/gnu/14.3.rel1/binrel/arm-gnu-toolchain-14.3.rel1-x86_64-arm-none-eabi.tar.xz;name=firmware-toolchain \
           file://eglfs.json \
           file://lw8.service \
           file://lw8-stop.sh \
           "
SRC_URI[firmware-toolchain.sha256sum] = "8f6903f8ceb084d9227b9ef991490413014d991874a1e34074443c2a72b14dbd"

require python3-lw8-crates.inc

PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV_lw8 = "${REFEYN_LW8_COMMIT}"
SRCREV_iscat = "${REFEYN_ISCAT_COMMIT}"
SRCREV_nxp-sdk = "${REFEYN_NXP_SDK_COMMIT}"
SRCREV_FORMAT = "lw8_iscat_nxp-sdk"

S = "${WORKDIR}/lw8"

# HACK lfs aint working
# Also using git xiapi path
do_configure[network] = "1"
do_unpack[network] = "1"
do_compile[network] = "1"

DEPENDS += "ximea-xiapi spinnaker pkgconfig-native"

inherit python_setuptools3_rust cargo-update-recipe-crates
require python3-dev-utils-build.inc

DEPENDS += "python3-pyyaml-native cmake-native ninja-native"

do_compile_firmware() {
    cd ${S}
    python3 ./firmware/buildFirmware.py \
        --build_config refeyn_ec-pcb-00044 \
        --serial_driver UART \
        --build_type Debug \
        --build_target NXP.elf \
        --toolchain_path ../arm-gnu-toolchain-14.3.rel1-x86_64-arm-none-eabi
}
addtask do_compile_firmware before do_compile after do_configure

unset do_configure[postfuncs]

do_compile:prepend() {
    export BINDGEN_EXTRA_CLANG_ARGS="-I ${STAGING_DIR_TARGET}/usr/include"
    export LW8_RUST_LIBRARY_PATH="${STAGING_DIR_TARGET}/usr/lib"
    export LW8_SPINNAKER_RUST_LIBRARY_PATH="${STAGING_DIR_TARGET}/opt/spinnaker/lib"
}

FILES:${PN} += "${systemd_unitdir}/system/lw8.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "lw8.service"

do_install:append() {
    install -d ${D}${sysconfdir}/Refeyn/AcquireMP/
    cp ${WORKDIR}/eglfs.json ${D}${sysconfdir}/Refeyn/AcquireMP/

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lw8.service ${D}/${systemd_unitdir}/system

    install -d ${D}/${bindir}
    install -m 0644 ${WORKDIR}/lw8-stop.sh ${D}/${bindir}/lw8-stop.sh

    rm ${D}/${PYTHON_SITEPACKAGES_DIR}/lw8/data/dependencies/*
}

RDEPENDS:${PN} += " \
    python3-iscat python3-iscat-utils python3-more-itertools python3-pint \
    python3-tabulate ximea-xiapi python3-spinnaker python3-jinja2 \
    python3-fluigent python3-periphery python3-pyserial python3-pyprctl \
    python3-quickgraphlib python3-statistics python3-pyudev ${PN}-data \
    qtvirtualkeyboard qtimageformats gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad python3-spsdk util-linux-findmnt util-linux-hwclock smbclient \
"

PACKAGES =+ "${PN}-data"
FILES:${PN}-data = "${PYTHON_SITEPACKAGES_DIR}/lw8/data"
