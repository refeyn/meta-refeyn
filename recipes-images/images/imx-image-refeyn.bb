include imx-image-refeyn-lite.bb

DESCRIPTION = "Refeyn image for doing Refyened things"
LICENSE = "CLOSED"

IMAGE_INSTALL += " \
    python3-lw8 \
"

IMAGE_INSTALL_OPENCV              = ""
IMAGE_INSTALL_OPENCV:imxgpu       = "${IMAGE_INSTALL_OPENCV_PKGS}"
IMAGE_INSTALL_OPENCV:mx93-nxp-bsp = "${IMAGE_INSTALL_OPENCV_PKGS}"
IMAGE_INSTALL_OPENCV_PKGS = " \
    opencv-apps \
    python3-opencv"

IMAGE_INSTALL_PARSEC = ""
IMAGE_INSTALL_PARSEC:aarch64 = " \
    packagegroup-security-tpm2 \
    packagegroup-security-parsec \
    swtpm \
    softhsm \
    os-release \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'optee-client optee-os', '', d)}"

# IMAGE_INSTALL += " \
#     python3-lw8 \
#     packagegroup-imx-ml \
#     packagegroup-qt6-imx \
#     ${IMAGE_INSTALL_OPENCV} \
#     ${IMAGE_INSTALL_PARSEC} \
# "

# IMAGE_FEATURES += " \
#     tools-profile \
#     tools-sdk \
#     tools-debug \
#     nfs-server \
# "

V2X_PKGS = ""
V2X_PKGS:mx8dxl-nxp-bsp = "packagegroup-imx-v2x"

DOCKER ?= ""
DOCKER:mx8-nxp-bsp = "docker"

G2D_SAMPLES              = ""
G2D_SAMPLES:imxgpu2d     = "imx-g2d-samples"
G2D_SAMPLES:mx93-nxp-bsp = "imx-g2d-samples"

# CORE_IMAGE_EXTRA_INSTALL += " \
#     packagegroup-fsl-tools-audio \
#     packagegroup-fsl-tools-gpu \
#     packagegroup-fsl-tools-gpu-external \
#     packagegroup-imx-isp \
#     packagegroup-imx-security \
#     packagegroup-fsl-gstreamer1.0 \
#     packagegroup-fsl-gstreamer1.0-full \
#     ${DOCKER} \
# "
