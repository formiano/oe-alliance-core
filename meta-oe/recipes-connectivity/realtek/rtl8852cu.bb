SUMMARY = "Realtek rtl8852cu"
HOMEPAGE = "https://github.com/formiano/rtl8852cu-20251113"
SECTION = "kernel/modules"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b7e6779b3b112ee657a10f5a3e1a4beb"

inherit module

SRC_URI = "git://github.com/formiano/rtl8852cu-20251113.git;protocol=https;branch=main;destsuffix=s \
           file://0001-update-makefile.patch \
"

SRCREV = "23c77a55a6e4c75f899034507fff6979d2c5103b"

S = "${UNPACKDIR}/s"

EXTRA_OEMAKE = " \
    KSRC=${STAGING_KERNEL_DIR} \
    KERNEL_SRC=${STAGING_KERNEL_DIR} \
    KERNEL_SOURCE=${STAGING_KERNEL_DIR} \
    KDIR=${STAGING_KERNEL_DIR} \
    KERNDIR=${STAGING_KERNEL_DIR} \
    KVER=${KERNEL_VERSION} \
"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP

    SHORTSRC="/tmp/r52-${MACHINE}-$$"

    rm -f "${SHORTSRC}"
    ln -s "${S}" "${SHORTSRC}"

    oe_runmake -C "${SHORTSRC}" \
        M="${SHORTSRC}" \
        'KSRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_SRC=${STAGING_KERNEL_DIR}' \
        'KERNEL_SOURCE=${STAGING_KERNEL_DIR}' \
        'KDIR=${STAGING_KERNEL_DIR}' \
        'KERNDIR=${STAGING_KERNEL_DIR}' \
        'KVER=${KERNEL_VERSION}' \
        'KERNEL_VERSION=${KERNEL_VERSION}' \
        'CC=${KERNEL_CC}' \
        'AR=${KERNEL_AR}' \
        'LD=${KERNEL_LD}'

    rm -f "${SHORTSRC}"
}

require kcflags.inc

do_install() {
    install -d \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless

    install -m 0644 \
        ${S}/8852cu.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8852cu.ko
}

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}"
