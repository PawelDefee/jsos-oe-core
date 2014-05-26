# Edited from https://github.com/alext-mkrs/meta-alext-galileo/tree/master/recipes-addfeatures/nodejs-latest

DESCRIPTION = "nodeJS Evented I/O for V8 JavaScript"
HOMEPAGE = "http://nodejs.org"
LICENSE = "MIT & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c61ec54d119e218dbe45d9f69a421b5f"

DEPENDS = "curl python-shell python-datetime python-subprocess python-crypt python-textutils python-netclient bash"

SRC_URI = "http://nodejs.org/dist/v${PV}/node-v${PV}.tar.gz \
"
SRC_URI[md5sum] = "9426f4ba942e12e345bdc161bde846cb"
SRC_URI[sha256sum] = "7098763353011a92bca25192c0ed4a7cae5a115805223bcc6d5a81e4d20dc87a"

S = "${WORKDIR}/node-v${PV}"

# v8 errors out if you have set CCACHE
CCACHE = ""

ARCHFLAGS_arm = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--with-arm-float-abi=hard', '--with-arm-float-abi=softfp', d)}"
ARCHFLAGS ?= ""

# Node is way too cool to use proper autotools, so we install two wrappers to forcefully inject proper arch cflags to workaround gypi
do_configure () {
    export LD="${CXX}"

    ./configure --prefix=${prefix} --without-snapshot --without-dtrace ${ARCHFLAGS}
}

do_compile () {
    export LD="${CXX}"
    make BUILDTYPE=Release
}

do_install () {
    oe_runmake install DESTDIR=${D}
}

RDEPENDS_${PN} = "openssl"
RDEPENDS_${PN}_class-native = ""

FILES_${PN} += "${libdir}/node/wafadmin ${libdir}/node_modules ${libdir}/dtrace ${datadir}/systemtap/tapset/node.stp"
BBCLASSEXTEND = "native"
