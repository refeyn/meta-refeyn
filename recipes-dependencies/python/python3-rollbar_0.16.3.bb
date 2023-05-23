DESCRIPTION = "Easy and powerful exception tracking with Rollbar. Send messages and exceptions with arbitrary context, get back aggregates, and debug production issues quickly."
HOMEPAGE = "http://github.com/rollbar/pyrollbar"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7006739aeae0ff1a518ca79b1ba00b53"

SRC_URI[sha256sum] = "02313dfc60710ec736ab033d0f8c969d857a8b991cd67e0c1a91620e8a04ede2"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-six \
"
