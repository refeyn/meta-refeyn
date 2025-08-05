# Since we use the native headers for shiboken, they must have the same config
PACKAGECONFIG:class-native:append = "accessibility gles2 eglfs"
PACKAGECONFIG:class-native:remove = "no-opengl"

EXTRA_OECMAKE:append:class-target = "-DQT_QPA_DEFAULT_PLATFORM=minimal"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-Add-environment-variable-for-rotating-libinput-mouse.patch"
