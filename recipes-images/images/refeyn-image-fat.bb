include refeyn-image.bb

SUMMARY = "Refeyn Embedded Linux Debugging Image"
DESCRIPTION = "Refeyn image for doing Refyened things (heavyweight version)"
LICENSE = "CLOSED"

export IMAGE_BASENAME = "Refeyn-Fat-Image"
IMAGE_VARIANT = "Fat"

IMAGE_FEATURES += " \
    tools-profile \
    tools-debug \
"
#     tools-sdk \
#     dbg-pkgs \
#     dev-pkgs \
#

IMAGE_INSTALL += " \
    cmake git cargo rust \
"
