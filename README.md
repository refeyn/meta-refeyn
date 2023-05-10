# meta-refeyn
Yocto layer for Refeyn applications

# Initial build

You need to be on an Ubuntu VM with at least 400GB of disk space and 20GB of memory (RAM + swap). You also need to install the [yocto dependencies](https://docs.yoctoproject.org/ref-manual/system-requirements.html#ubuntu-and-debian) and kas ([via pip](https://kas.readthedocs.io/en/3.2.3/userguide.html#usage)). Then:

```bash
$ mkdir imx-refeyn
$ cd imx-refeyn
$ git clone git@github.com:refeyn/meta-refeyn.git
$ kas build meta-refeyn/kas-project.yml
```

This will take several hours.

# Layout

 - `kas-project.yml` defines which layers we depend on (a bit like git submodules), what our target machine distro and image is, and any modifications to the bitbake conf
 - `recipies-images` defines our images (a complete image with our applications and other tools bundled inside)
 - `recipies-refeyn` defines recipes for our apps and libraries
 - `recipies-python` defines recipes for third-party python dependencies
