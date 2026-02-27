SUMMARY = "ISCAT - Analysis Package for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/iscat"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/iscat.git;protocol=ssh;nobranch=1;destsuffix=iscat"
require python3-iscat-crates.inc

PV = "1.0+git${SRCPV}"
SRCREV = "${REFEYN_ISCAT_COMMIT}"

S = "${WORKDIR}/iscat"

inherit python_setuptools3_rust cargo-update-recipe-crates
require python3-dev-utils-build.inc

# HACK until ndarray_ndimage has a new release
do_configure[network] = "1"
do_unpack[network] = "1"
do_compile[network] = "1"

RDEPENDS:${PN} += " \
    hdf5plugin-zstandard python3-asyncio python3-core python3-datetime \
    python3-h5py python3-io python3-json python3-logging \
    python3-more-itertools python3-multiprocessing python3-numpy python3-pickle \
    python3-profile python3-psutil python3-pydantic python3-pytest \
    python3-scikit-learn python3-scipy python3-threading python3-typing-extensions \
    python3-unittest python3-pyyaml python3-refeyn-io \
"
