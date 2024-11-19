SUMMARY = "Packagegroups which provide cmdline releated packages"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES += " \
    packagegroup-base-tdx-cli \
    packagegroup-benchmark-tdx-cli \
    packagegroup-devel-tdx-cli \
    packagegroup-machine-tdx-cli \
    packagegroup-networking-tdx-cli \
    packagegroup-utils-tdx-cli \
    packagegroup-utils-large-tdx-cli \
    packagegroup-tpm2-tdx-cli \
    packagegroup-wifi-tdx-cli \
    packagegroup-wifi-fw-tdx-cli \
    packagegroup-wifi-fw-large-tdx-cli \
"

RDEPENDS:packagegroup-tdx-cli = "\
    packagegroup-base-tdx-cli \
    packagegroup-benchmark-tdx-cli \
    packagegroup-devel-tdx-cli \
    packagegroup-machine-tdx-cli \
    packagegroup-networking-tdx-cli \
    packagegroup-utils-tdx-cli \
    packagegroup-utils-large-tdx-cli \
    packagegroup-wifi-tdx-cli \
    packagegroup-wifi-fw-tdx-cli \
    packagegroup-wifi-fw-large-tdx-cli \
"

USB_GADGET = " \
    libusbgx \
    libusbgx-config \
    libusbgx-examples \
"

SUMMARY:packagegroup-base-tdx-cli = "Recommended for any image"
RRECOMMENDS:packagegroup-base-tdx-cli = "\
    can-utils \
    can-utils-cantest \
    dosfstools \
    e2fsprogs-mke2fs \
    exfat-utils \
    iproute2 \
    libgomp \
    libgpiod-tools \
    mtd-utils \
    set-hostname \
    u-boot-fw-utils \
    udev-toradex-rules \
    uhubctl \
    util-linux-fstrim \
    ${USB_GADGET} \
    tdx-info \
"

SUMMARY:packagegroup-benchmark-tdx-cli = "Benchmarking tools"
RRECOMMENDS:packagegroup-benchmark-tdx-cli = "\
    hdparm \
    iperf2 \
    iperf3 \
    lmbench \
    memtester \
    nbench-byte \
    rt-tests \
    tinymembench \
"

SUMMARY:packagegroup-devel-tdx-cli = "Tools useful during development"
RRECOMMENDS:packagegroup-devel-tdx-cli = "\
    bonnie++ \
    devmem2 \
    evtest \
    fio \
    gdbserver \
    i2c-tools \
    jq \
    ldd \
    less \
    smem \
    spitools \
    spidev-test \
    strace \
"
RRECOMMENDS:packagegroup-devel-tdx-cli:append:k3 = "\
    k3conf \
"

SUMMARY:packagegroup-machine-tdx-cli = "Machine specific tools"
RRECOMMENDS:packagegroup-machine-tdx-cli = "\
"
RRECOMMENDS:packagegroup-machine-tdx-cli:apalis-imx6 = "\
    bmode-usb \
"
RRECOMMENDS:packagegroup-machine-tdx-cli:colibri-imx6 = "\
    bmode-usb \
"
RRECOMMENDS:packagegroup-machine-tdx-cli:colibri-imx6ull = "\
    bmode-usb \
    mtd-utils-ubifs \
"
RRECOMMENDS:packagegroup-machine-tdx-cli:colibri-imx6ull-emmc = "\
    bmode-usb \
"
RRECOMMENDS:packagegroup-machine-tdx-cli:colibri-imx7 = "\
    mtd-utils-ubifs \
"

SUMMARY:packagegroup-networking-tdx-cli = "Networking specific tools"
RRECOMMENDS:packagegroup-networking-tdx-cli = "\
    avahi-autoipd \
    curl \
    linuxptp \
    ppp \
    ptpd \
    tcpdump \
"

SUMMARY:packagegroup-utils-tdx-cli = "Useful utilities"

# Entropy source daemon
RANDOM_HELPER = "rng-tools"

RRECOMMENDS:packagegroup-utils-tdx-cli = "\
    alsa-utils \
    alsa-utils-aplay \
    alsa-utils-amixer \
    alsa-ucm-conf-tdx \
    bluealsa \
    bzip2 \
    e2fsprogs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    ethtool \
    gpsd \
    grep \
    lsof \
    minicom \
    pciutils \
    phytool \
    procps \
    ${RANDOM_HELPER} \
    stress-ng \
    sqlite3 \
    util-linux \
"

SUMMARY:packagegroup-utils-large-tdx-cli = "Useful utilities, but large footprint"
RRECOMMENDS:packagegroup-utils-large-tdx-cli = "\
    aspell \
    file \
    joe \
"

SUMMARY:packagegroup-tpm2-tdx-cli = "Basic tools to work with TPM 2.0"
RRECOMMENDS:packagegroup-tpm2-tdx-cli = " \
    tpm2-tools \
    libtss2-tcti-device \
"

SUMMARY:packagegroup-wifi-tdx-cli = "Useful Wi-Fi utilities and firmware"
BACKPORTS = ""
RRECOMMENDS:packagegroup-wifi-tdx-cli = "\
    ${BACKPORTS} \
    hostapd \
    wireless-regdb-static \
"

SUMMARY:packagegroup-wifi-fw-tdx-cli = "Wi-Fi firmware"
RRECOMMENDS:packagegroup-wifi-fw-tdx-cli = "\
    linux-firmware-ath10k    \
    linux-firmware-iw416     \
    linux-firmware-nxp89xx   \
    linux-firmware-sd8686    \
    linux-firmware-sd8688    \
    linux-firmware-sd8787    \
    linux-firmware-sd8797    \
    linux-firmware-sd8887    \
    linux-firmware-sd8997    \
    linux-firmware-ralink    \
    linux-firmware-rtl8192cu \
    linux-firmware-rtl8188   \
"

SUMMARY:packagegroup-wifi-fw-large-tdx-cli = "Wi-Fi firmware with large footprint"
RRECOMMENDS:packagegroup-wifi-fw-large-tdx-cli = "\
    linux-firmware-ath9k     \
    linux-firmware-iwlwifi   \
"
