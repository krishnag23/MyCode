#!/bin/sh
set -e

envsubst '${elbdns}' < /etc/nginx/conf.d/server.conf.template > /etc/nginx/conf.d/server.conf

exec "$@"
