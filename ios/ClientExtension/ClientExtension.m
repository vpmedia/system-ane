/*
 * =BEGIN MIT LICENSE
 * 
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Andras Csizmadia
 * http://www.vpmedia.hu
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * =END MIT LICENSE
 *
 */
 
#import "ClientExtension.h"

//----------------------------------
// Constants
//----------------------------------

// Common
#define EXT_LOG 1
#define EXT_DEVICE_ID 2
#define EXT_VIBRATE 3
#define EXT_NOTIFY 4
#define EXT_START_SENSOR 5
#define EXT_STOP_SENSOR 6
#define EXT_HAS_SENSOR 7
#define EXT_LIST_SENSOR 8

//----------------------------------
// Helpers
//----------------------------------

BOOL ExtLog() {
    NSLog(@"ExtLog()");
    return YES;
}

BOOL ExtDeviceId() {
    NSLog(@"ExtDeviceId()");
    return YES;
}

BOOL ExtVibrate() {
    NSLog(@"ExtVibrate()");
    return YES;
}

BOOL ExtNotify() {
    NSLog(@"ExtNotify()");
    return YES;
}

BOOL ExtStartSensor(int32_t sensorType) {
    NSLog(@"ExtStartSensor()");
    return YES;
}

BOOL ExtStopSensor(int32_t sensorType) {
    NSLog(@"ExtStopSensor()");
    return YES;
}


BOOL ExtHasSensor(int32_t sensorType) {
    NSLog(@"ExtHasSensor()");
    return YES;
}


BOOL ExtListSensor() {
    NSLog(@"ExtListSensor()");
    return YES;
}

//----------------------------------
// API
//----------------------------------

FREObject callNative(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    NSLog(@"callNative()");
    FREObject retVal = NULL;
    int32_t type = 0;
    BOOL isSuccess = NO;
    if(argc > 0) {
        FREGetObjectAsInt32(argv[0], &type);
        switch (type) {
            case EXT_LOG:
                isSuccess = ExtLog();
                break;
            case EXT_DEVICE_ID:
                isSuccess = ExtDeviceId();
                break;
            case EXT_VIBRATE:
                isSuccess = ExtVibrate();
                break;
            case EXT_NOTIFY:
                isSuccess = ExtNotify();
                break;
            case EXT_START_SENSOR:
                isSuccess = ExtStartSensor(0);
                break;
            case EXT_STOP_SENSOR:
                isSuccess = ExtStopSensor(0);
                break;
            case EXT_HAS_SENSOR:
                isSuccess = ExtHasSensor(0);
                break;
            case EXT_LIST_SENSOR:
                isSuccess = ExtListSensor();
                break;
            default:
                isSuccess = NO;
                break;
        }
    }
    FRENewObjectFromBool((uint32_t)isSuccess, &retVal);
    return retVal;
}

//----------------------------------
// FREExtension
//----------------------------------

void ContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx,
                        uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet) 
{    
    NSLog(@"ContextInitializer()");
    *numFunctionsToTest = 1;
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * 6);
    func[0].name = (const uint8_t*) "callNative";
    func[0].functionData = NULL;
    func[0].function = &callNative;    
    *functionsToSet = func;
}

void ContextFinalizer(FREContext ctx) {
    NSLog(@"ContextFinalizer()");
    return;
}

void ClientExtensionInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet,
                    FREContextFinalizer* ctxFinalizerToSet) {    
    NSLog(@"ClientExtensionInitializer()");
    *extDataToSet = NULL;
    *ctxInitializerToSet = &ContextInitializer;
    *ctxFinalizerToSet = &ContextFinalizer;
}

void ClientExtensionFinalizer(void* extData) {
    
    NSLog(@"ClientExtensionFinalizer()");
    return;
}