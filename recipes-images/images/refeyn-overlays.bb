DESCRIPTION = "Overlays for Refeyn image"
LICENSE = "MIT"

inherit overlayfs

OVERLAYFS_MOUNT_POINT[root-home] = "/data/root/"
OVERLAYFS_WRITABLE_PATHS[root-home] = "/root/"

OVERLAYFS_MOUNT_POINT[var-log] = "/data/log/"
OVERLAYFS_WRITABLE_PATHS[var-log] = "/var/log/"
