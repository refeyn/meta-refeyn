SUMMARY = "AcquireMP - Control and Analysis Software for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/lw8"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/lw8.git;protocol=ssh;branch=master;destsuffix=lw8;name=lw8 \
           git://git@github.com/refeyn/iscat.git;protocol=ssh;branch=master;destsuffix=lw8/internal_dependencies/iscat;name=iscat \
           file://eglfs.json \
           file://lw8.service \
           "
require python3-lw8-crates.inc

PV = "v2023.2.0dev2+git${SRCPV}"
SRCREV_lw8 = "${AUTOREV}"
SRCREV_iscat = "${AUTOREV}"
SRCREV_FORMAT = "lw8_iscat"

S = "${WORKDIR}/lw8"

# HACK lfs aint working
# Also using git xiapi path
do_configure[network] = "1"
do_unpack[network] = "1"
do_compile[network] = "1"

DEPENDS += "ximea-xiapi pkgconfig-native"

inherit python_setuptools3_rust
require python3-dev-utils-build.inc

unset do_configure[postfuncs]

do_compile:prepend() {
    export BINDGEN_EXTRA_CLANG_ARGS="-I ${STAGING_DIR_TARGET}/usr/include"
    export LW8_RUST_LIBRARY_PATH="${STAGING_DIR_TARGET}/usr/lib"
}

FILES:${PN} += "${systemd_unitdir}/system/lw8.service"

inherit systemd

SYSTEMD_SERVICE:${PN} = "lw8.service"

do_install:append() {
    install -d ${D}${sysconfdir}/Refeyn/AcquireMP/
    cp ${WORKDIR}/eglfs.json ${D}${sysconfdir}/Refeyn/AcquireMP/

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lw8.service ${D}/${systemd_unitdir}/system

    find ${D}/${PYTHON_SITEPACKAGES_DIR}/lw8 -name "*.dll" -type f -delete
    find ${D}/${PYTHON_SITEPACKAGES_DIR}/lw8 -name "*.lib" -type f -delete
}

RDEPENDS:${PN} += " \
    python3-iscat python3-iscat-utils python3-more-itertools python3-pint \
    python3-tabulate python3-aiohttp ximea-xiapi python3-spinnaker \
    python3-fluigent python3-periphery python3-pyserial python3-prctl \
    python3-quickgraphlib python3-statistics python3-pyudev ${PN}-data \
"

PACKAGES =+ "${PN}-data"
FILES:${PN}-data = "${PYTHON_SITEPACKAGES_DIR}/lw8/data"
