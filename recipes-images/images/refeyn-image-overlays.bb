SUMMARY = "Overlay fs paths that need to be writable"

LICENSE = "CLOSED"

inherit overlayfs

OVERLAYFS_MOUNT_POINT[root] = "/data"
OVERLAYFS_WRITABLE_PATHS[root] = "/root"
OVERLAYFS_MOUNT_POINT[var] = "/data"
OVERLAYFS_WRITABLE_PATHS[var] = "/var"
