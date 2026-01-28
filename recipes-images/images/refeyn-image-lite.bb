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
    printf "IMAGE=\"${IMAGE_BASENAME}\"\n" >> ${IMAGE_ROOTFS}/etc/os-release
}

add_home_root_symlink () {
    ln -sf ${ROOT_HOME} ${IMAGE_ROOTFS}/home/root
}

# add the rootfs version to the welcome banner
ROOTFS_POSTPROCESS_COMMAND += " add_rootfs_version; add_home_root_symlink;"

IMAGE_LINGUAS = "en-us"

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
    systemd-analyze \
    edid-override \
    gptfdisk \
    systemd-boot-assessment \
"

## Select Image Features
IMAGE_FEATURES += " \
    debug-tweaks \
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

TEZI_DATA_ENABLED = "1"
TEZI_ROOT_PART_SIZE = "4096"

python rootfs_tezi_edit_json() {
    import json, os, copy
    json_file = os.path.join(d.getVar('IMGDEPLOYDIR'), "image-%s.json" % d.getVar('IMAGE_BASENAME'))
    with open(json_file) as outfile:
        data = json.load(outfile)

    data["blockdevs"][0]["table_type"] = "gpt"
    data["blockdevs"][0]["partitions"][1]["want_maximised"] = False
    data["blockdevs"][0]["partitions"].insert(2, copy.deepcopy(data["blockdevs"][0]["partitions"][1]))
    del data["blockdevs"][0]["partitions"][2]["content"]["filename"]
    del data["blockdevs"][0]["partitions"][2]["content"]["uncompressed_size"]
    del data["blockdevs"][0]["partitions"][3]["partition_type"]

    with open(json_file, 'w') as outfile:
        json.dump(data, outfile, indent=4)
}

TEZI_IMAGE_TEZIIMG_PREFUNCS:append = " rootfs_tezi_edit_json"
