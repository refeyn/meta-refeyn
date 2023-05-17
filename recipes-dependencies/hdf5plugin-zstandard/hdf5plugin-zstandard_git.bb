LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "git://github.com/aparamon/HDF5Plugin-Zstandard.git;protocol=https;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "d5afdb5f04116d5c2d1a869dc9c7c0c72832b143"

S = "${WORKDIR}/git"

inherit cmake

DEPENDS = "hdf5 zstd"
FILES:${PN} += "${prefix}/local/hdf5/lib/plugin/libH5Zzstd.so"
