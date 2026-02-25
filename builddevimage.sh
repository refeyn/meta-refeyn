#!/bin/bash
set -e
set -o allexport
source <(python ../meta-refeyn/resolve_commits.py $GH_TOKEN release/2026.3.0)
set +o allexport
export REFEYN_DEV_UTILS_BRANCH=release/apps_2026.3.0
export REFEYN_REFEYN_IO_BRANCH=release/apps_2026.3.0
export REFEYN_ISCAT_BRANCH=release/apps_2026.3.0
export REFEYN_ISCAT_UTILS_BRANCH=release/apps_2026.3.0
export REFEYN_LW8_BRANCH=release/2026.3.0
export REFEYN_NXP_SDK_BRANCH=master
# export REFEYN_NO_CYTHONIZE=0
env | grep REFEYN_
devtool build-image refeyn-image
../meta-refeyn/copyimgs.sh
