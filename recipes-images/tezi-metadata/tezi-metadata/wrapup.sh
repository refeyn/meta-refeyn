#!/bin/sh

# Mount rootfs
cd /tmp
mkdir m
mount /dev/mmcblk0p2 m

# Source version information
set -o allexport
source m/etc/os-release
set +o allexport

# Prepare chroot
cd m
mount --rbind /dev dev/

# Relabel rootfs
chroot . /sbin/sgdisk -c "1:boot" -t "1:bc13c2ff-59e6-4262-a352-b275fd6f7172" /dev/mmcblk0
chroot . /sbin/sgdisk -c "2:rootfs_$IMAGE_VERSION" -t "2:b921b045-1df0-41c3-af44-4c6f280d3fae" /dev/mmcblk0
chroot . /sbin/sgdisk -c "3:_empty" -t "3:b921b045-1df0-41c3-af44-4c6f280d3fae" /dev/mmcblk0
chroot . /sbin/sgdisk -c "4:data" -t "4:0fc63daf-8483-4772-8e79-3d69d8477de4" /dev/mmcblk0

# Mount bootfs
cd /tmp
mkdir b
mount /dev/mmcblk0p1 b
cd b
mv fitImage "fitImage_$IMAGE_VERSION"

exit 0
