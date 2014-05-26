SUMMARY = "node.js init"
DESCRIPTION = "Node.js based init system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r2"
DEPENDS = "nodejs"
SRC_URI = "file://init"

do_configure() {
        :
}

do_compile() {
        :
}

do_install() {
  install -d  ${D}/sbin
  install -m 0755 ${WORKDIR}/init ${D}/sbin/init
}

FILES_${PN} = "/sbin/init"
