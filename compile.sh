#!/bin/bash

mkdir -p bin

lib="./lib/log4j-core-2.13.1.jar:"
lib+="./lib/kotlinx-cli-0.2.1.jar:"
lib+="./lib/log4j-api-2.13.1.jar:"
lib+="./lib/log4j-api-kotlin-1.0.0.jar:"
lib+="./lib/h2-1.4.200.jar:"
lib+="./lib/flyway-core-6.3.3.jar"



kotlinc\
  -cp ${lib}\
  ./src -include-runtime -d bin/app.jar

jar uf bin/app.jar ./log4j2.xml