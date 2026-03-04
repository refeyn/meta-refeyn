#!/bin/bash
set -e
set -o allexport
source <(python ../meta-refeyn/resolve_commits.py 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 release/26.1.0)
set +o allexport
# export REFEYN_NO_CYTHONIZE=0
env | grep REFEYN_
devtool build-image refeyn-image
../meta-refeyn/copyimgs.sh
