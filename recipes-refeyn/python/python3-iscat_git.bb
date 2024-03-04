SUMMARY = "ISCAT - Analysis Package for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/iscat"

LICENSE = "CLOSED"

SRC_URI = "git://git@github.com/refeyn/iscat.git;protocol=ssh;branch=master \
           crate://crates.io/affinity/0.1.2 \
           crate://crates.io/anyhow/1.0.75 \
           crate://crates.io/approx/0.5.1 \
           crate://crates.io/argmin/0.8.1 \
           crate://crates.io/argmin-math/0.3.0 \
           crate://crates.io/autocfg/1.1.0 \
           crate://crates.io/bitflags/1.3.2 \
           crate://crates.io/cc/1.0.83 \
           crate://crates.io/cfg-if/1.0.0 \
           crate://crates.io/either/1.9.0 \
           crate://crates.io/errno/0.3.8 \
           crate://crates.io/finitediff/0.1.4 \
           crate://crates.io/getrandom/0.2.11 \
           crate://crates.io/hashbrown/0.12.3 \
           crate://crates.io/heck/0.4.1 \
           crate://crates.io/hermit-abi/0.3.3 \
           crate://crates.io/indexmap/1.9.3 \
           crate://crates.io/indoc/2.0.4 \
           crate://crates.io/indxvec/1.8.7 \
           crate://crates.io/instant/0.1.12 \
           crate://crates.io/itertools/0.10.5 \
           crate://crates.io/itertools/0.12.0 \
           crate://crates.io/libc/0.2.150 \
           crate://crates.io/libm/0.2.8 \
           crate://crates.io/libmimalloc-sys/0.1.35 \
           crate://crates.io/lock_api/0.4.11 \
           crate://crates.io/matrixmultiply/0.3.8 \
           crate://crates.io/medians/3.0.2 \
           crate://crates.io/memoffset/0.9.0 \
           crate://crates.io/mimalloc/0.1.39 \
           crate://crates.io/ndarray/0.15.6 \
           crate://crates.io/ndarray-rand/0.14.0 \
           crate://crates.io/ndarray-stats/0.5.1 \
           crate://crates.io/noisy_float/0.2.0 \
           crate://crates.io/num-complex/0.4.4 \
           crate://crates.io/num-integer/0.1.45 \
           crate://crates.io/num-traits/0.2.17 \
           crate://crates.io/num_cpus/1.16.0 \
           crate://crates.io/numpy/0.20.0 \
           crate://crates.io/once_cell/1.18.0 \
           crate://crates.io/parking_lot/0.12.1 \
           crate://crates.io/parking_lot_core/0.9.9 \
           crate://crates.io/paste/1.0.14 \
           crate://crates.io/ppv-lite86/0.2.17 \
           crate://crates.io/proc-macro2/1.0.70 \
           crate://crates.io/pyo3/0.20.0 \
           crate://crates.io/pyo3-build-config/0.20.0 \
           crate://crates.io/pyo3-ffi/0.20.0 \
           crate://crates.io/pyo3-macros/0.20.0 \
           crate://crates.io/pyo3-macros-backend/0.20.0 \
           crate://crates.io/quote/1.0.33 \
           crate://crates.io/ran/1.1.0 \
           crate://crates.io/rand/0.8.5 \
           crate://crates.io/rand_chacha/0.3.1 \
           crate://crates.io/rand_core/0.6.4 \
           crate://crates.io/rand_distr/0.4.3 \
           crate://crates.io/rand_xoshiro/0.6.0 \
           crate://crates.io/rawpointer/0.2.1 \
           crate://crates.io/redox_syscall/0.4.1 \
           crate://crates.io/rustc-hash/1.1.0 \
           crate://crates.io/scopeguard/1.2.0 \
           crate://crates.io/smallvec/1.11.2 \
           crate://crates.io/syn/2.0.39 \
           crate://crates.io/target-lexicon/0.12.12 \
           crate://crates.io/thiserror/1.0.50 \
           crate://crates.io/thiserror-impl/1.0.50 \
           crate://crates.io/unicode-ident/1.0.12 \
           crate://crates.io/unindent/0.2.3 \
           crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
           crate://crates.io/windows-sys/0.52.0 \
           crate://crates.io/windows-targets/0.48.5 \
           crate://crates.io/windows-targets/0.52.0 \
           crate://crates.io/windows_aarch64_gnullvm/0.48.5 \
           crate://crates.io/windows_aarch64_gnullvm/0.52.0 \
           crate://crates.io/windows_aarch64_msvc/0.48.5 \
           crate://crates.io/windows_aarch64_msvc/0.52.0 \
           crate://crates.io/windows_i686_gnu/0.48.5 \
           crate://crates.io/windows_i686_gnu/0.52.0 \
           crate://crates.io/windows_i686_msvc/0.48.5 \
           crate://crates.io/windows_i686_msvc/0.52.0 \
           crate://crates.io/windows_x86_64_gnu/0.48.5 \
           crate://crates.io/windows_x86_64_gnu/0.52.0 \
           crate://crates.io/windows_x86_64_gnullvm/0.48.5 \
           crate://crates.io/windows_x86_64_gnullvm/0.52.0 \
           crate://crates.io/windows_x86_64_msvc/0.48.5 \
           crate://crates.io/windows_x86_64_msvc/0.52.0 \
           "

PV = "1.0+git${SRCPV}"
SRCREV = "e0e92adc1d7c42163d72155552078d42c85757eb"

S = "${WORKDIR}/git"

inherit python_setuptools3_rust

# HACK until ndarray_ndimage has a new release
do_configure[network] = "1"
do_unpack[network] = "1"
do_compile[network] = "1"

DEPENDS += "python3-dev-utils-native"

RDEPENDS:${PN} += "hdf5plugin-zstandard python3-asyncio python3-core python3-datetime python3-distutils python3-h5py python3-io python3-json python3-logging python3-more-itertools python3-multiprocessing python3-numpy python3-pickle python3-profile python3-psutil python3-pydantic python3-pytest python3-scikit-learn python3-scipy python3-threading python3-typing-extensions python3-unittest"
PREFERRED_VERSION_python3-pydantic = "2.6.3"
