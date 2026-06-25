#!/bin/bash
set -e
set -o allexport
source <(python ../meta-refeyn/resolve_commits.py $GH_TOKEN release/2026.7.0)
set +o allexport
# export REFEYN_NO_CYTHONIZE=0
env | grep REFEYN_
devtool build-image refeyn-image
../meta-refeyn/copyimgs.sh
