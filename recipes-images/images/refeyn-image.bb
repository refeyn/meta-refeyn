include refeyn-image-lite.bb

SUMMARY = "Refeyn Embedded Linux AMP Image"
DESCRIPTION = "Refeyn image for doing Refyened things"
LICENSE = "CLOSED"

export IMAGE_BASENAME = "Refeyn-Image"

IMAGE_INSTALL += " \
    python3-lw8 \
"
