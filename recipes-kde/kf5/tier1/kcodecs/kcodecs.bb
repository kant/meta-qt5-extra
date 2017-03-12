SUMMARY = "KDE Text encoding library"
LICENSE = "GPLv2 | LGPLv2.1"
LIC_FILES_CHKSUM = " \
	file://COPYING;md5=5c213a7de3f013310bd272cdb6eb7a24 \
	file://COPYING.LIB;md5=2d5025d4aa3495befef8f17206a5b0a1 \
"

inherit kde-kf5

PV = "${KF5_VERSION}"
SRC_URI[md5sum] = "ca577d2af0a48a14615e38414474773b"
SRC_URI[sha256sum] = "a3084f995808312be1cb8f63b91766e06c63b9c6d2f8a62426d5a228ca9dcb7b"
