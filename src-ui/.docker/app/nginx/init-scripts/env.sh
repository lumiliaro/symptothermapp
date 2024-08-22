#!/usr/bin/env sh

set -ex

# Find the file where environment variables need to be replaced.
envVars=$(ls -t /usr/share/nginx/html/assets/envVars*.js | head -n1)

# Replace environment variables
envsubst < "$envVars" > ./envVars_temp
cp ./envVars_temp "$envVars"
rm ./envVars_temp