#!/usr/bin/env bash

GRADLE_HOME=/usr/local/opt/gradle/libexec
export GRADLE_HOME

AIR_HOME=/usr/local/opt/adobe-air-sdk/libexec
export AIR_HOME

PATH=$AIR_HOME/bin:$GRADLE_HOME/bin:$PATH
export PATH

gradle