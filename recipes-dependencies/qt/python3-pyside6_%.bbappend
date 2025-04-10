FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-WIP-Fix-corruption-when-connecting-to-temporary-obje.patch \
"

BBCLASSEXTEND = "native nativesdk"

PYSIDE_QT_MODULES:class-native = "\
    qtbase \
    qtdeclarative \
    qtdeclarative-native \
"
DEPENDS += "python3-numpy"
