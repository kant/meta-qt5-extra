SUMMARY = "Wolf Shaper is a waveshaper plugin with a graph editor"
HOMEPAGE = "https://pdesaulniers.github.io/wolf-shaper/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fe869ee987a340198fb0d54c55c47f1"

DEPENDS = " \
    jack \
    dssi \
    lv2 \
"

inherit autotools-brokensep pkgconfig qemu-ext

SRC_URI = "gitsm://github.com/pdesaulniers/wolf-shaper.git"
SRCREV = "adc67b3f923917b6b42f545451e6d4932f721e97"
S = "${WORKDIR}/git"

export PREFIX="${prefix}"

do_compile() {
    rm -f ${WORKDIR}/lv2_ttl_generator-data
    # manipulate scripts to keep lv2_ttl_generator-calls in script for qemu
    sed -i 's|"$GEN" "./$FILE"|echo `pwd`/$FILE >> ${WORKDIR}/lv2_ttl_generator-data|g' ${S}/dpf/utils/generate-ttl.sh

    NOOPT=true \
    SKIP_STRIPPING=true \
    BUILD_VST2=true \
    BUILD_LV2=true \
    BUILD_DSSI=true \
    BUILD_JACK=true \
    oe_runmake

    # build ttl-files must be done in quemu
    for sofile in `cat ${WORKDIR}/lv2_ttl_generator-data`; do
        cd `dirname ${sofile}`
        echo "QEMU lv2_ttl_generator for ${sofile}..."
        ${@qemu_run_binary_local(d, '${STAGING_DIR_TARGET}', '${S}/dpf/utils/lv2_ttl_generator')} ${sofile} || echo "ERROR: for QEMU lv2_ttl_generator for ${sofile}!"
    done
}

FILES_${PN} += " \
    ${libdir}/dssi \
    ${libdir}/lv2 \
    ${libdir}/vst \
"
