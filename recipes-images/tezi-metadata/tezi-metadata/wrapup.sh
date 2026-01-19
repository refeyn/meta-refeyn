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
chroot . /sbin/sgdisk -c "2:rootfs_$IMAGE_VERSION" /dev/mmcblk0

# Mount bootfs
cd /tmp
mkdir b
mount /dev/mmcblk0p1 b
cd b
mv fitImage "fitImage_$IMAGE_VERSION"

exit 0
