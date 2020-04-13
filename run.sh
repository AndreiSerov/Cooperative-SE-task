#!/bin/bash

if [[ "$OSTYPE" == "msys" ]]; then
  params="./lib/kotlinx-cli-0.2.1.jar;bin/app.jar"
else
  params="./lib/kotlinx-cli-0.2.1.jar:"
  params+="./lib/log4j-core-2.13.1.jar:"
  params+="./lib/log4j-api-2.13.1.jar:"
  params+="./lib/log4j-api-kotlin-1.0.0.jar:"
  params+="./lib/h2-1.4.200.jar:"
  params+="bin/app.jar"
fi

# shellcheck disable=SC2068
java -classpath $params com.dinosaur.app.MainKt $@
