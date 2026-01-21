SYSUPDATE_ROOT_SUFFIX = "ext4.xz"
IMAGE_TYPEDEP:sysupdate += "${SYSUPDATE_ROOT_SUFFIX}"
IMAGE_CMD:sysupdate () {
	${IMAGE_CMD_TAR} \
		--transform='s/.*\///' \
		--transform='s/fitImage/fitImage_${IMAGE_VERSION}/' \
		--transform='s/${IMAGE_LINK_NAME}/rootfs_${IMAGE_VERSION}/' \
		-chf "${IMGDEPLOYDIR}/${IMAGE_NAME}-sysupdate.tar" \
		${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.${SYSUPDATE_ROOT_SUFFIX} fitImage
}
do_image_sysupdate[dirs] += "${DEPLOY_DIR_IMAGE}"
do_image_sysupdate[recrdeptask] += "do_deploy"
