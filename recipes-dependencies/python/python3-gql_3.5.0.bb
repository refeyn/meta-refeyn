DESCRIPTION = "GraphQL client for Python"
HOMEPAGE = "https://github.com/graphql-python/gql"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f38bd223275f617b47d97eacc029647f"

SRC_URI[sha256sum] = "ccb9c5db543682b28f577069950488218ed65d4ac70bb03b6929aaadaf636de9"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN}:class-target += " \
    ${PYTHON_PN}-graphql-core \
    ${PYTHON_PN}-yarl \
    ${PYTHON_PN}-anyio \
    ${PYTHON_PN}-backoff \
    ${PYTHON_PN}-requests \
    ${PYTHON_PN}-requests-toolbelt \
    ${PYTHON_PN}-urllib3 \
"
