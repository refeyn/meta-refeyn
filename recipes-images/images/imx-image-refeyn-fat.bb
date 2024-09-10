include imx-image-refeyn.bb

DESCRIPTION = "Refeyn image for doing Refyened things (heavyweight version)"
LICENSE = "CLOSED"

IMAGE_FEATURES += " \
    tools-profile \
    tools-sdk \
    tools-debug \
    dbg-pkgs \
"
