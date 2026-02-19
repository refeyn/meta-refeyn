include refeyn-image.bb

inherit populate_sdk_qt6

SUMMARY = "Refeyn Embedded Linux Debugging Image"
DESCRIPTION = "Refeyn image for doing Refyened things (heavyweight version)"
LICENSE = "CLOSED"

export IMAGE_BASENAME = "Refeyn-Fat-Image"

IMAGE_FEATURES += " \
    tools-profile \
    tools-debug \
    debug-tweaks \
    weston \
    ssh-server-dropbear \
"

IMAGE_INSTALL += " \
    cmake \
    git \
    cargo \
    rust \
    packagegroup-core-full-cmdline \
    firmwared \
    nano \
    lrzsz \
    htop \
    curl \
    i2c-tools \
    libgpiod-tools \
    spidev-test \
    can-utils \
    lmsensors \
    devmem2 \
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    packagegroup-fsl-isp \
    packagegroup-boot \
    packagegroup-basic \
    packagegroup-base-tdx-cli \
    packagegroup-machine-tdx-cli \
    packagegroup-wifi-tdx-cli \
    packagegroup-wifi-fw-tdx-cli \
    systemd-analyze \
"
