# Add zlib support to HDF5 (needed for DEFLATE filter)

EXTRA_OECMAKE += "-DHDF5_ENABLE_Z_LIB_SUPPORT=ON"
DEPENDS += "zlib"
