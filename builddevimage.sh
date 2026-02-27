#!/bin/bash
set -e
set -o allexport
source <(python ../meta-refeyn/resolve_commits.py $GH_TOKEN release/26.1.0)
set +o allexport
# export REFEYN_NO_CYTHONIZE=0
env | grep REFEYN_
devtool build-image refeyn-image-fat
../meta-refeyn/copyimgs.sh
