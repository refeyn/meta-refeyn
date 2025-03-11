SRC_URI = "git://git@github.com/refeyn/u-boot.git;protocol=ssh;branch=${SRCBRANCH}"
SRCREV = "0c671147947cbaf31e6c64c6815119ec4fceb374"
SRCBRANCH = "refeyn_ti-u-boot-2024.04"

do_compile:prepend:k3r5 () {
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            if [ -h ${B}/${config}/${UBOOT_BINARY} ]; then
                rm ${B}/${config}/${UBOOT_BINARY}
            fi
        done
    else
        if [ -h ${B}/${UBOOT_BINARY} ]; then
            rm ${B}/${UBOOT_BINARY}
        fi
    fi
}
