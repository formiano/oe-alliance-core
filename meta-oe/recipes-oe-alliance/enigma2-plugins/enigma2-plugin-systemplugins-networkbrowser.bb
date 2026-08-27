DESCRIPTION = "Networkbrowser and Network-Mountmanager"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "enigma2"

RDEPENDS:${PN} = "autofs smbclient nfs-utils-client nmap"

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "ea99b0fc3604520a5faaa0d10a46deb9bc7e2268"

PV = "1.0+git${SRCPV}"
PR = "r1"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/formiano/NetworkBrowser.git;protocol=https;branch=main"

FILES:${PN} += "${libdir}/enigma2/python/Plugins/SystemPlugins/NetworkBrowser"
