#!/bin/bash
set -e
devtool build-image imx-image-refeyn
../meta-refeyn/copyimgs.sh
