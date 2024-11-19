#!/bin/bash
set -e
devtool build-image refeyn-image-fat
../meta-refeyn/copyimgs.sh
