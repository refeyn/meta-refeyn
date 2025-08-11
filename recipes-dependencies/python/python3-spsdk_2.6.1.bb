SUMMARY = "Secure Provisioning SDK (SPSDK) is unified, reliable and easy to use SW library working across NXP MCU portfolio providing strong foundation from quick customer prototyping up to production deployment. "
HOMEPAGE = "https://github.com/refeyn/spsdk"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=863e3c0c79e2589ac9d16c3918e115d1"

SRC_URI = "git://git@github.com/refeyn/spsdk.git;protocol=ssh;branch=master \
           file://0001-Relax-requirements-and-remove-custom-version-scheme.patch \
           "

PV = "2.6.1+git${SRCPV}"
SRCREV = "145a0e202805def975ca183ac3653f19b09d2a66"

S = "${WORKDIR}/git"

inherit python_setuptools_build_meta

RDEPENDS:${PN} += " \
    python3-core python3-cryptography python3-crcmod python3-filelock \
    python3-libusbsio python3-packaging python3-platformdirs python3-pyserial \
    python3-typing-extensions \
"
DEPENDS += "python3-setuptools-scm-native python3-platformdirs-native"
