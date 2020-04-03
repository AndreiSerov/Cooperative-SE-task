#!/bin/bash

mkdir -p bin

libs="./lib/kotlinx-cli-0.2.1.jar:./lib/log4j-api-2.11.0.jar:./lib/log4j-api-kotlin-1.0.0.jar"
kotlinc \
  -cp ${libs}\
  ./src -include-runtime -d bin/app.jar