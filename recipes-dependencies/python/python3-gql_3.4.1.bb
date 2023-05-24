DESCRIPTION = "GraphQL client for Python"
HOMEPAGE = "https://github.com/graphql-python/gql"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f38bd223275f617b47d97eacc029647f"

SRC_URI[sha256sum] = "11dc5d8715a827f2c2899593439a4f36449db4f0eafa5b1ea63948f8a2f8c545"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-graphql-core \
    ${PYTHON_PN}-yarl \
    ${PYTHON_PN}-backoff \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-requests-toolbelt \
    ${PYTHON_PN}-urllib3 \
"
