# The MIPS cacheline fallback patch targets the newer host_cpu-based
# TORRENT_CHECK_CACHELINE implementation and does not apply to libtorrent 0.16.12.
SRC_URI:remove = "file://0001-configure-add-MIPS-to-the-Linux-cacheline-fallback.patch"
