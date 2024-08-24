#!/usr/bin/env sh

set -ex

# Execute all scripts in /docker-entrypoint.d/
for script in /docker-entrypoint.d/*.sh; do
    if [ -f "$script" ]; then
        echo "Executing $script"
        sh "$script"
    fi
done

# Start the main process (nginx in this case)
exec "$@"