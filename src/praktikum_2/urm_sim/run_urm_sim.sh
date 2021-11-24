#!/bin/bash
#cd "${0%/*}"
cd "$(dirname "$0")"
java -jar ./urm-sim.jar
