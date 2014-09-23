#!/bin/bash
set -e
set -x
if [[ "$(uname -s)" == 'Darwin' ]]; then
	DARWIN=true
else
	DARWIN=false
fi

if [[ "$DARWIN" = true ]]; then
	# Update Brew silently
	brew update
	# Install Brew Packages silently
	brew install gradle
	brew install adobe-air-sdk
	brew install android-sdk
	brew install android-ndk
	# Adobe Flash Player for CLI
	brew install caskroom/cask/brew-cask
	brew cask install flash-player-debugger
	# Set env. variable used by FlexUnit
	export FLASHPLAYER_DEBUGGER="$HOME/Applications/Flash Player Debugger.app/Contents/MacOS/Flash Player Debugger"
	# Set env. variable used by Gradle
	export FLASH_PLAYER_EXE="$HOME/Applications/Flash Player Debugger.app/Contents/MacOS/Flash Player Debugger"
	# Apache Flex SDK
	wget -O flex_sdk.zip http://mirrors.gigenet.com/apache/flex/4.13.0/binaries/apache-flex-sdk-4.13.0-bin.zip
	unzip -q flex_sdk.zip -d flex_sdk
	# Init PlayerGlobals
	echo env.PLAYERGLOBAL_HOME=libs/player > flex_sdk/env.properties
	# Init Apache Flex SDK
	mkdir -p flex_sdk/frameworks/libs/player/11.1/
	mkdir -p flex_sdk/frameworks/libs/player/15.0/
	cp -f /usr/local/opt/adobe-air-sdk/libexec/frameworks/libs/player/15.0/playerglobal.swc flex_sdk/frameworks/libs/player/11.1/
	cp -f /usr/local/opt/adobe-air-sdk/libexec/frameworks/libs/player/15.0/playerglobal.swc flex_sdk/frameworks/libs/player/15.0/
	# Init Android SDK
	echo yes | android update sdk --filter platform-tools,android-20,build-tools-20.0.0 --force --no-ui > /dev/null
	# Set env. variable used by Gradle
	export FLEX_HOME="$TRAVIS_BUILD_DIR/flex_sdk"
	# Init Adobe AIR SDK
	mkdir -p /usr/local/opt/adobe-air-sdk/libexec/frameworks/libs/player/11.1/
	cp -f /usr/local/opt/adobe-air-sdk/libexec/frameworks/libs/player/15.0/playerglobal.swc /usr/local/opt/adobe-air-sdk/libexec/frameworks/libs/player/11.1/
fi
