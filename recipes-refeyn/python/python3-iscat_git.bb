SUMMARY = "ISCAT - Analysis Package for Mass Photometry Measurements"
HOMEPAGE = "https://github.com/refeyn/iscat"

LICENSE = "CLOSED"

SRC_URI = "gitsm://git@github.com/refeyn/iscat.git;protocol=ssh;branch=feature/gotta-go-fast-sbc \
           crate://crates.io/anyhow/1.0.70 \
           crate://crates.io/approx/0.5.1 \
           crate://crates.io/argmin/0.8.1 \
           crate://crates.io/argmin-math/0.3.0 \
           crate://crates.io/atty/0.2.14 \
           crate://crates.io/autocfg/1.1.0 \
           crate://crates.io/bitflags/1.3.2 \
           crate://crates.io/cfg-if/1.0.0 \
           crate://crates.io/crossbeam-channel/0.5.7 \
           crate://crates.io/crossbeam-deque/0.8.3 \
           crate://crates.io/crossbeam-epoch/0.9.14 \
           crate://crates.io/crossbeam-utils/0.8.15 \
           crate://crates.io/dirs-next/2.0.0 \
           crate://crates.io/dirs-sys-next/0.1.2 \
           crate://crates.io/either/1.8.1 \
           crate://crates.io/finitediff/0.1.4 \
           crate://crates.io/getrandom/0.2.8 \
           crate://crates.io/hashbrown/0.12.3 \
           crate://crates.io/hermit-abi/0.1.19 \
           crate://crates.io/hermit-abi/0.2.6 \
           crate://crates.io/indexmap/1.9.3 \
           crate://crates.io/indoc/1.0.9 \
           crate://crates.io/indxvec/1.5.1 \
           crate://crates.io/instant/0.1.12 \
           crate://crates.io/itertools/0.10.5 \
           crate://crates.io/itoa/1.0.6 \
           crate://crates.io/libc/0.2.140 \
           crate://crates.io/libm/0.2.6 \
           crate://crates.io/lock_api/0.4.9 \
           crate://crates.io/matrixmultiply/0.3.2 \
           crate://crates.io/medians/2.2.2 \
           crate://crates.io/memoffset/0.8.0 \
           crate://crates.io/ndarray/0.15.6 \
           crate://crates.io/ndarray-conv/0.1.3 \
           crate://crates.io/ndarray-ndimage/0.3.0 \
           crate://crates.io/ndarray-rand/0.14.0 \
           crate://crates.io/ndarray-stats/0.5.1 \
           crate://crates.io/ndrustfft/0.3.0 \
           crate://crates.io/noisy_float/0.2.0 \
           crate://crates.io/num/0.4.0 \
           crate://crates.io/num-bigint/0.4.3 \
           crate://crates.io/num-complex/0.4.3 \
           crate://crates.io/num-integer/0.1.45 \
           crate://crates.io/num-iter/0.1.43 \
           crate://crates.io/num-rational/0.4.1 \
           crate://crates.io/num-traits/0.2.15 \
           crate://crates.io/num_cpus/1.15.0 \
           crate://crates.io/num_threads/0.1.6 \
           crate://crates.io/numpy/0.18.0 \
           crate://crates.io/once_cell/1.17.1 \
           crate://crates.io/parking_lot/0.11.2 \
           crate://crates.io/parking_lot_core/0.8.6 \
           crate://crates.io/paste/1.0.12 \
           crate://crates.io/ppv-lite86/0.2.17 \
           crate://crates.io/primal-check/0.3.3 \
           crate://crates.io/proc-macro2/1.0.54 \
           crate://crates.io/pyo3/0.18.2 \
           crate://crates.io/pyo3-build-config/0.18.2 \
           crate://crates.io/pyo3-ffi/0.18.2 \
           crate://crates.io/pyo3-macros/0.18.2 \
           crate://crates.io/pyo3-macros-backend/0.18.2 \
           crate://crates.io/quote/1.0.26 \
           crate://crates.io/ran/1.1.0 \
           crate://crates.io/rand/0.8.5 \
           crate://crates.io/rand_chacha/0.3.1 \
           crate://crates.io/rand_core/0.6.4 \
           crate://crates.io/rand_distr/0.4.3 \
           crate://crates.io/rand_xoshiro/0.6.0 \
           crate://crates.io/rawpointer/0.2.1 \
           crate://crates.io/rayon/1.7.0 \
           crate://crates.io/rayon-core/1.11.0 \
           crate://crates.io/realfft/3.2.0 \
           crate://crates.io/redox_syscall/0.2.16 \
           crate://crates.io/redox_users/0.4.3 \
           crate://crates.io/rustc-hash/1.1.0 \
           crate://crates.io/rustdct/0.7.1 \
           crate://crates.io/rustfft/6.1.0 \
           crate://crates.io/rustversion/1.0.12 \
           crate://crates.io/scopeguard/1.1.0 \
           crate://crates.io/serde/1.0.160 \
           crate://crates.io/slog/2.7.0 \
           crate://crates.io/slog-async/2.7.0 \
           crate://crates.io/slog-term/2.9.0 \
           crate://crates.io/smallvec/1.10.0 \
           crate://crates.io/strength_reduce/0.2.4 \
           crate://crates.io/syn/1.0.109 \
           crate://crates.io/syn/2.0.12 \
           crate://crates.io/take_mut/0.2.2 \
           crate://crates.io/target-lexicon/0.12.6 \
           crate://crates.io/term/0.7.0 \
           crate://crates.io/thiserror/1.0.40 \
           crate://crates.io/thiserror-impl/1.0.40 \
           crate://crates.io/thread_local/1.1.7 \
           crate://crates.io/time/0.3.20 \
           crate://crates.io/time-core/0.1.0 \
           crate://crates.io/time-macros/0.2.8 \
           crate://crates.io/transpose/0.2.2 \
           crate://crates.io/unicode-ident/1.0.8 \
           crate://crates.io/unindent/0.1.11 \
           crate://crates.io/version_check/0.9.4 \
           crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
           crate://crates.io/winapi/0.3.9 \
           crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
           crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "26cc4fe044499b7328a2a9c42ed1509e22516046"

S = "${WORKDIR}/git"

inherit python_setuptools3_rust

do_write_version() {
    echo "VERSION_SHA = '${SRCREV}'" > ${S}/iscat/_versionSha.py
}
addtask do_write_version before do_compile after do_configure

DEPENDS += "python3-dev-utils-native"

RDEPENDS:${PN} += "hdf5plugin-zstandard python3-asyncio python3-core python3-datetime python3-distutils python3-h5py python3-io python3-json python3-logging python3-more-itertools python3-multiprocessing python3-numpy python3-pickle python3-profile python3-psutil python3-pydantic python3-pytest python3-scikit-learn python3-scipy python3-threading python3-typing-extensions python3-unittest"
PREFERRED_VERSION_python3-pydantic = "1.10.7"
