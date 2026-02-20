#!/bin/bash
set -e
export REFEYN_DEV_UTILS_BRANCH=release/apps_2026.3.0
export REFEYN_REFEYN_IO_BRANCH=release/apps_2026.3.0
export REFEYN_ISCAT_BRANCH=release/apps_2026.3.0
export REFEYN_ISCAT_UTILS_BRANCH=release/apps_2026.3.0
export REFEYN_LW8_BRANCH=release/2026.3.0
export REFEYN_NO_CYTHONIZE=0
devtool build-image refeyn-image
../meta-refeyn/copyimgs.sh
