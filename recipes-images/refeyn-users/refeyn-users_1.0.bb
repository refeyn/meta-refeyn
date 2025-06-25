SUMMARY = "Refeyn user accounts"
DESCRIPTION = "Set up user accounts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"

ALLOW_EMPTY:${PN} = "1"

inherit useradd

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM:${PN} = "refeyn"

# default password is 'refeyn', generated with the following command: 'openssl passwd refeyn'
USERADD_PARAM:${PN} = "-G adm,sudo,users,plugdev,audio,video,gpio,i2cdev,spidev,dialout,input,pwm -m -d /home/refeyn -p '\$1\$kVfHRee9\$rsCB3sXit.WZVY4QJ1B89/' refeyn"
