SUMMARY = "Refeyn Embedded Linux Minimal Image"
DESCRIPTION = "Refeyn image for doing Refyened things (lightweight version)"
LICENSE = "CLOSED"

inherit populate_sdk_qt6
require recipes-images/images/torizon-base.inc

IMAGE_VARIANT = "Lite"

#Prefix to the resulting deployable tarball name
export IMAGE_BASENAME = "Refeyn-Lite-Image"
MACHINE_NAME ?= "${MACHINE}"
IMAGE_NAME = "${MACHINE_NAME}_${IMAGE_BASENAME}"
TEZI_IMAGE_NAME = "${IMAGE_NAME}"

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

UBOOT_BINARY_OTA:aquila-am69-refeyn = "u-boot.img"

CORE_IMAGE_BASE_INSTALL:remove = "fluentbit"
CORE_IMAGE_BASE_INSTALL:remove = "rac"
CORE_IMAGE_BASE_INSTALL:remove = "auto-provisioning"
CORE_IMAGE_BASE_INSTALL:remove = "tzn-mqtt"
CORE_IMAGE_BASE_INSTALL:remove = "torizon-users"
CORE_IMAGE_BASE_INSTALL:remove = "networkmanager"
IMAGE_INSTALL:remove = "aktualizr-info"
IMAGE_INSTALL:remove = "aktualizr"
IMAGE_INSTALL:remove = "aktualizr-shared-prov"

PACKAGE_EXCLUDE += "fluentbit rac auto-provisioning tzn-mqtt aktualizr-polling-interval aktualizr-info aktualizr-torizon aktualizr aktualizr-shared-prov aktualizr-reboot aktualizr-default-sec"

IMAGE_INSTALL += " \
    python3-refeyn-scripts \
    spidev-test \
    i2c-tools \
    edid-override \
    libgpiod-tools \
    can-utils \
    lmsensors \
    htop \
    greenboot \
    refeyn-users \
    zstd \
"
#     curl \
#     tzdata \
#     devmem2 \
#     psplash \
#     packagegroup-tdx-cli \
#     packagegroup-fsl-isp \
#     packagegroup-boot \
#     packagegroup-basic \
#     packagegroup-base-tdx-cli \
#     packagegroup-machine-tdx-cli \
#     packagegroup-wifi-tdx-cli \
#     packagegroup-wifi-fw-tdx-cli \
#     udev-extraconf \
#     ${CONMANPKGS} \
#     systemd-analyze \
#
## Select Image Features
IMAGE_FEATURES += " \
    debug-tweaks \
    hwcodecs \
"
#
# CORE_IMAGE_EXTRA_INSTALL += " \
#     packagegroup-core-full-cmdline \
#     firmwared \
#     nano \
#     lrzsz \
#     htop \
# "

nss_altfiles_set_users_groups () {
	# Make a temporary directory to be used by pseudo to find the real /etc/passwd,/etc/group
	pseudo_dir=${WORKDIR}/pseudo-rootfs${sysconfdir}
	override_dir=${IMAGE_ROOTFS}${sysconfdir}
	nsswitch_conf=${IMAGE_ROOTFS}${sysconfdir}/nsswitch.conf

	sed -i -e '/^passwd/s/$/ altfiles/' -e '/^group/s/$/ altfiles/' -e '/^shadow/s/$/ altfiles/' ${nsswitch_conf}

	install -d ${pseudo_dir}
	install -m 644 ${override_dir}/passwd ${pseudo_dir}
	install -m 644 ${override_dir}/group ${pseudo_dir}
	install -m 400 ${override_dir}/shadow ${pseudo_dir}
	cp -a ${pseudo_dir}/* ${IMAGE_ROOTFS}${libdir}

	for file in passwd group shadow; do
		cat > ${override_dir}/${file} <<- EOF
			# NSS altfiles module is installed. Default user, group and shadow files are in
			# /usr/lib/
		EOF
		grep -r refeyn ${IMAGE_ROOTFS}${libdir}/${file} >> ${override_dir}/${file}
	done
}
