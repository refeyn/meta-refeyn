#!/bin/bash
cp tmp/deploy/images/imx8qm-ec-asm-00014-01/imx-image-refeyn-imx8qm-ec-asm-00014-01.wic.* /home/refeyn
cp tmp/deploy/images/imx8qm-ec-asm-00014-01/imx-image-refeyn-lite-imx8qm-ec-asm-00014-01.wic.* /home/refeyn
cp tmp/deploy/images/imx8qm-ec-asm-00014-01/imx-image-refeyn-fat-imx8qm-ec-asm-00014-01.wic.* /home/refeyn
cp tmp/deploy/images/imx8qm-ec-asm-00014-01/imx-boot-imx8qm-ec-asm-00014-01-sd.bin-flash_spl /home/refeyn
cp tmp/deploy/images/imx8qm-ec-asm-00014-01/imx8qm-ec-asm-00014-01-with-ec-asm-{00025-02,00027-01}.dtb /home/refeyn
echo "Copied images to /home/refeyn"
