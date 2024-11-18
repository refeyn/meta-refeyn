SUMMARY = "Packagegroups which provide graphical/display/multimedia releated packages"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES += " \
    packagegroup-drm-utils-tdx-graphical \
    packagegroup-gstreamer-tdx-graphical \
    packagegroup-gpu-tdx-graphical \
    packagegroup-vulkan-tools-tdx-graphical \
"

RRECOMMENDS:packagegroup-tdx-graphical = " \
    packagegroup-gstreamer-tdx-graphical \
    packagegroup-gpu-tdx-graphical \
    ${@bb.utils.contains('DISTRO_FEATURES', 'vulkan', \
                         'packagegroup-vulkan-tools-tdx-graphical', '', d)} \
"
RRECOMMENDS:packagegroup-tdx-graphical:append:k3 = " \
    packagegroup-drm-utils-tdx-graphical \
"
RRECOMMENDS:packagegroup-tdx-graphical:append:mx8-nxp-bsp = " \
    packagegroup-drm-utils-tdx-graphical \
"
RRECOMMENDS:packagegroup-tdx-graphical:append:upstream = " \
    packagegroup-drm-utils-tdx-graphical \
"

SUMMARY:packagegroup-drm-utils-tdx-graphical = "Utilities for DRM, Direct Rendering Manager"
RRECOMMENDS:packagegroup-drm-utils-tdx-graphical = " \
    libdrm-tests \
"

SUMMARY:packagegroup-gstreamer-tdx-graphical = "gstreamer packages"
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical = " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gst-examples \
"
GSTREAMER_MX6QDL = " \
    gstreamer1.0-plugins-base-ximagesink \
    gstreamer1.0-plugins-imx \
    imx-gst1.0-plugin \
    imx-gst1.0-plugin-gplay \
    imx-gst1.0-plugin-grecorder \
"
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:append:mx6dl-nxp-bsp = " ${GSTREAMER_MX6QDL}"
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:append:mx6q-nxp-bsp = " ${GSTREAMER_MX6QDL}"
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:colibri-imx6ull = ""
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:colibri-imx6ull-emmc = ""
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:append:mx7-nxp-bsp = " imx-gst1.0-plugin"
RRECOMMENDS:packagegroup-gstreamer-tdx-graphical:append:mx8-nxp-bsp = " \
    imx-gst1.0-plugin \
    imx-gst1.0-plugin-gplay \
    imx-gst1.0-plugin-grecorder \
    packagegroup-fsl-gstreamer1.0-full \
"

SUMMARY:packagegroup-gpu-utils-tdx-graphical = "Utilities for GPU (OpenGL...)"
IMAGE_INSTALL_OPENCL_IMX = " \
    libopencl-imx \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical = " \
    glmark2 \
"
IMAGE_INSTALL_GPU_MX6QDL = " \
    packagegroup-fsl-gpu-libs \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical:append:mx6dl-nxp-bsp = " \
    ${IMAGE_INSTALL_GPU_MX6QDL} \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical:append:mx6q-nxp-bsp = " \
    ${IMAGE_INSTALL_GPU_MX6QDL} \
    ${IMAGE_INSTALL_OPENCL_IMX} \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical:colibri-imx6ull = ""
RRECOMMENDS:packagegroup-gpu-tdx-graphical:colibri-imx6ull-emmc = ""
RRECOMMENDS:packagegroup-gpu-tdx-graphical:append:mx8-nxp-bsp = " \
    tinycompress \
    libvdk-imx \
    vulkan-headers \
    vulkan-loader \
    vulkan-tools \
    ${IMAGE_INSTALL_OPENCL_IMX} \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical:remove:mx8mm-nxp-bsp = " \
    vulkan-headers \
    vulkan-loader \
    vulkan-tools \
    ${IMAGE_INSTALL_OPENCL_IMX} \
"
RRECOMMENDS:packagegroup-gpu-tdx-graphical:append:mx8qm-nxp-bsp = " \
    libopenvx-imx \
"

SUMMARY:packagegroup-vulkan-tools-tdx-graphical = "Vulkan tools"
RRECOMMENDS:packagegroup-vulkan-tools-tdx-graphical = " \
    vulkan-tools \
"
