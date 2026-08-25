DESCRIPTION = "Networkbrowser and Network-Mountmanager"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2"

RDEPENDS:${PN} = "autofs smbclient nfs-utils-client nmap"

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "88b57fb35ae50b2db815dc8e8e6ef4a798ea2e52"

PV = "1.0+git${SRCPV}"
PR = "r1"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/formiano/NetworkBrowser.git;protocol=https;branch=main"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/SystemPlugins/NetworkBrowser"
