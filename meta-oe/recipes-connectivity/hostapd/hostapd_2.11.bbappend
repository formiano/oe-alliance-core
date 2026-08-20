FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-openssl-use-ASN1_STRING-accessors.patch \
"
