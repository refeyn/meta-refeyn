SUMMARY = "refeyn_io - I/O utilities for Refeyn files"
HOMEPAGE = "https://github.com/refeyn/refeyn_io"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/refeyn_io.git;protocol=ssh;branch=${REFEYN_REFEYN_IO_BRANCH};destsuffix=refeyn_io"

PV = "1.44.0+git${SRCPV}"
SRCREV = "${REFEYN_REFEYN_IO_COMMIT}"

S = "${WORKDIR}/refeyn_io"

inherit setuptools3
require python3-dev-utils-build.inc

RDEPENDS:${PN} += " \
    python3-core python3-crypt python3-datetime python3-io \
    python3-json python3-logging python3-misc python3-numpy \
    python3-packaging python3-pytest python3-typing-extensions python3-dev-utils \
    python3-h5py python3-numpy \
"
