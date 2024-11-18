include refeyn-image.bb

SUMMARY = "Refeyn Embedded Linux Debugging Image"
DESCRIPTION = "Refeyn image for doing Refyened things (heavyweight version)"
LICENSE = "CLOSED"

export IMAGE_BASENAME = "Refeyn-Fat-Image"

IMAGE_FEATURES += " \
    tools-profile \
    tools-sdk \
    tools-debug \
    dbg-pkgs \
"
