DESCRIPTION = "Refeyn image for doing Refyened things (lightweight version)"
LICENSE = "CLOSED"

inherit core-image
inherit populate_sdk_qt6

CONFLICT_DISTRO_FEATURES = "directfb"

IMAGE_INSTALL += " \
    curl \
    tzdata \
    i2c-tools \
    libgpiod-tools \
    spidev-test \
    can-utils \
    lmsensors \
    devmem2 \
    psplash \
    python3-refeyn-scripts \
"

## Select Image Features
IMAGE_FEATURES += " \
    debug-tweaks \
    package-management \
    splash \
    hwcodecs \
    weston \
    splash \
    ssh-server-dropbear \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    firmwared \
    nano \
    lrzsz \
    htop \
"
