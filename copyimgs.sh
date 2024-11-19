#!/bin/bash
rm /home/refeyn/Apalis-iMX8-refeyn_Refeyn-Lite-Image-Tezi.tar
cp tmp/deploy/images/apalis-imx8-refeyn/Apalis-iMX8-refeyn_Refeyn-Lite-Image-Tezi.tar /home/refeyn
rm /home/refeyn/Apalis-iMX8-refeyn_Refeyn-Image-Tezi.tar
cp tmp/deploy/images/apalis-imx8-refeyn/Apalis-iMX8-refeyn_Refeyn-Image-Tezi.tar /home/refeyn
rm /home/refeyn/Apalis-iMX8-refeyn_Refeyn-Fat-Image-Tezi.tar
cp tmp/deploy/images/apalis-imx8-refeyn/Apalis-iMX8-refeyn_Refeyn-Fat-Image-Tezi.tar /home/refeyn
echo "Copied images to /home/refeyn"
