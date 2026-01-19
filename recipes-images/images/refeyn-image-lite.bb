SUMMARY = "Refeyn Embedded Linux Minimal Image"
DESCRIPTION = "Refeyn image for doing Refyened things (lightweight version)"
LICENSE = "CLOSED"

inherit core-image
inherit populate_sdk_qt6

#Prefix to the resulting deployable tarball name
export IMAGE_BASENAME = "Refeyn-Lite-Image"
MACHINE_NAME ?= "${MACHINE}"
IMAGE_NAME = "${MACHINE_NAME}_${IMAGE_BASENAME}"

# Copy Licenses to image /usr/share/common-license
COPY_LIC_MANIFEST ?= "1"
COPY_LIC_DIRS ?= "1"

add_rootfs_version () {
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) \\\n \\\l\n" >> ${IMAGE_ROOTFS}/etc/issue
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) %%h\n" >> ${IMAGE_ROOTFS}/etc/issue.net
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue.net
}

add_home_root_symlink () {
    ln -sf ${ROOT_HOME} ${IMAGE_ROOTFS}/home/root
}

# add the rootfs version to the welcome banner
ROOTFS_POSTPROCESS_COMMAND += " add_rootfs_version; add_home_root_symlink;"

IMAGE_LINGUAS = "en-us"

CONFLICT_DISTRO_FEATURES = "directfb"

CONMANPKGS ?= "connman connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"
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
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    packagegroup-fsl-isp \
    packagegroup-boot \
    packagegroup-basic \
    packagegroup-base-tdx-cli \
    packagegroup-machine-tdx-cli \
    packagegroup-wifi-tdx-cli \
    packagegroup-wifi-fw-tdx-cli \
    udev-extraconf \
    ${CONMANPKGS} \
    systemd-analyze \
    edid-override \
    gptfdisk \
"

## Select Image Features
IMAGE_FEATURES += " \
    debug-tweaks \
    package-management \
    splash \
    hwcodecs \
    weston \
    ssh-server-dropbear \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    firmwared \
    nano \
    lrzsz \
    htop \
"


python rootfs_tezi_edit_json() {
    import json, os
    json_file = os.path.join(d.getVar('IMGDEPLOYDIR'), "image-%s.json" % d.getVar('IMAGE_BASENAME'))
    with open(json_file) as outfile:
        data = json.load(outfile)

    data["blockdevs"][0]["table_type"] = "gpt"

    with open(json_file, 'w') as outfile:
        json.dump(data, outfile, indent=4)
}

TEZI_IMAGE_TEZIIMG_PREFUNCS:append = " rootfs_tezi_edit_json"
