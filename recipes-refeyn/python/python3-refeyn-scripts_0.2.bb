SUMMARY = "Helper scripts for dealing with some embedded problems"

LICENSE = "CLOSED"

SRC_URI = "file://aux-board.py \
           "

FILES:${PN} += "${bindir}"

do_install:append() {
    install -d ${D}${bindir}
    cp ${WORKDIR}/aux-board.py ${D}${bindir}
    chmod +x ${D}${bindir}/aux-board.py
}

RDEPENDS:${PN} += "python3-core python3-io python3-datetime python3-docopt python3-profile python3-periphery"
