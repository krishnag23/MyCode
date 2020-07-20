#!/bin/bash
set -e

printenv
java -jar smo-inspection-1.0.0.jar --spring.profiles.active=${profile}
