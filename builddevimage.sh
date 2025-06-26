#!/bin/bash
set -e
devtool build-image refeyn-image
../meta-refeyn/copyimgs.sh
