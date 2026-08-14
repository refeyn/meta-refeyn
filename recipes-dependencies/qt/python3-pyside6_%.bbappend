BBCLASSEXTEND = "native nativesdk"

PYSIDE_QT_MODULES:class-native = "\
    qtbase \
    qtdeclarative \
    qtdeclarative-native \
"
PYSIDE_QT_MODULES:remove = "qtpositioning qtlocation"
DEPENDS += "python3-numpy"
