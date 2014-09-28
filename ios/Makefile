XBUILD=/usr/bin/xcodebuild
TARGET=ClientExtension
PROJECT_ROOT=.
PROJECT=$(PROJECT_ROOT)/$(TARGET).xcodeproj

all: libClientExtension.a

libi386.a:
	$(XBUILD) -project $(PROJECT) -target $(TARGET) -sdk iphonesimulator -configuration Release clean build
	-mv $(PROJECT_ROOT)/build/Release-iphonesimulator/lib$(TARGET).a $@

libArmv7.a:
	$(XBUILD) -project $(PROJECT) -target $(TARGET) -sdk iphoneos -arch armv7 -configuration Release clean build
	-mv $(PROJECT_ROOT)/build/Release-iphoneos/lib$(TARGET).a $@

libArmv7s.a:
	$(XBUILD) -project $(PROJECT) -target $(TARGET) -sdk iphoneos -arch armv7s -configuration Release clean build
	-mv $(PROJECT_ROOT)/build/Release-iphoneos/lib$(TARGET).a $@

libArm64.a:
	$(XBUILD) -project $(PROJECT) -target $(TARGET) -sdk iphoneos -arch arm64 -configuration Release clean build
	-mv $(PROJECT_ROOT)/build/Release-iphoneos/lib$(TARGET).a $@

libClientExtension.a: libi386.a libArmv7.a libArmv7s.a libArm64.a
	lipo -create -output lib$(TARGET)Universal.a $^

clean:
	-rm -f *.a
	-rm -rf build