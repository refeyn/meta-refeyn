DESCRIPTION = "Backport of PEP 654 (exception groups)"
HOMEPAGE = "https://github.com/agronholm/exceptiongroup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5caa317463c433575efff1d2fe206d7"

SRC_URI[sha256sum] = "47c2edf7c6738fafb49fd34290706d1a1a2f4d1c6df275526b62cbb4aa5393cc"

inherit pypi python_flit_core

DEPENDS += "python3-flit-scm-native"

BBCLASSEXTEND = "native nativesdk"
