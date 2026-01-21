#!/usr/bin/python3

import re, pathlib

with open("/etc/os-release") as f:
    version = re.search("IMAGE_VERSION=\"(.*)\"", f.read()).group(1)

fit_images = list(pathlib.Path("/boot").glob(f"fitImage_{version}*", case_sensitive=False))
if len(fit_images) != 1:
    exit(1)

fit_image, = fit_images
fit_image.rename(fit_image.with_name(f"fitImage_{version}"))
