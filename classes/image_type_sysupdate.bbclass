SYSUPDATE_ROOT_SUFFIX = "ext4.xz"
SYSUPDATE_BUILD_TIME = "${DATETIME}"
SYSUPDATE_BUILD_TIME[vardepsexclude] = "DATETIME"
IMAGE_TYPEDEP:sysupdate += "${SYSUPDATE_ROOT_SUFFIX}"
IMAGE_CMD:sysupdate () {
	local rootfspath="${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.${SYSUPDATE_ROOT_SUFFIX}"
	local rootfshash=$(sha256sum $rootfspath | cut -d" " -f 1)
	local fitimagehash=$(sha256sum fitImage | cut -d" " -f 1)
	echo "{
	\"version\": \"${DISTRO_VERSION}\",
	\"datetime\": \"${SYSUPDATE_BUILD_TIME}\",
	\"machine\": \"${MACHINE}\",
	\"image\": \"${IMAGE_BASENAME}\",
	\"devicetrees\" : \"${KERNEL_DEVICETREE}\",
	\"rootfshash\": \"$rootfshash\",
	\"fitimagehash\": \"$fitimagehash\"
}" > ${WORKDIR}/metadata.json
	${IMAGE_CMD_TAR} \
		--transform='s/.*\///' \
		--transform='s/fitImage/fitImage_${DISTRO_VERSION}/' \
		--transform='s/${IMAGE_LINK_NAME}/rootfs_${DISTRO_VERSION}/' \
		-chf "${IMGDEPLOYDIR}/${IMAGE_NAME}-${DISTRO_VERSION}-sysupdate.tar" \
		$rootfspath fitImage ${WORKDIR}/metadata.json
	ln -s "${IMAGE_NAME}-${DISTRO_VERSION}-sysupdate.tar" "${IMGDEPLOYDIR}/${IMAGE_NAME}-sysupdate.tar"
}
do_image_sysupdate[dirs] += "${DEPLOY_DIR_IMAGE}"
do_image_sysupdate[recrdeptask] += "do_deploy"
