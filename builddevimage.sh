#!/bin/bash
set -e
devtool build-image refeyn-image-lite
../meta-refeyn/copyimgs.sh
