DESCRIPTION = "Networkbrowser and Network-Mountmanager"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2"

RDEPENDS:${PN} = "autofs smbclient nfs-utils-client"

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "1567480c0765ccd6068e781b42fc4ac1f4f9094d"

PV = "1.0+git${SRCPV}"
PR = "r1"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/formiano/NetworkBrowser.git;protocol=https;branch=opendroid-network-discovery"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/SystemPlugins/NetworkBrowser"
