//
// =BEGIN MIT LICENSE
// 
// The MIT License (MIT)
//
// Copyright (c) 2014 Andras Csizmadia
// http://www.vpmedia.hu
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
// 
// =END MIT LICENSE
//
 
#import "ClientExtension.h"

//------------------------------------
//
// Native Commands.
//
//------------------------------------

FREObject callNative(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[] )
{
    FREObject retVal = NULL;
    FRENewObjectFromBool((uint32_t)NO, &retVal);    
    return retVal;
}

//------------------------------------
//
// Required Methods.
//
//------------------------------------

void ContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx,
                        uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet) 
{    
    *numFunctionsToTest = 1;    
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * 6);
    func[0].name = (const uint8_t*) "callNative";
    func[0].functionData = NULL;
    func[0].function = &callNative;    
    *functionsToSet = func;
}

void ContextFinalizer(FREContext ctx) {
    
    NSLog(@"Entering ContextFinalizer()");    
    // TODO    
    NSLog(@"Exiting ContextFinalizer()");    
    return;
}

void ClientExtensionInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet,
                    FREContextFinalizer* ctxFinalizerToSet) {    
    NSLog(@"Entering ClientExtensionInitializer()");    
    *extDataToSet = NULL;
    *ctxInitializerToSet = &ContextInitializer;
    *ctxFinalizerToSet = &ContextFinalizer;    
    NSLog(@"Exiting ClientExtensionInitializer()");
}

void ClientExtensionFinalizer(void* extData) {
    
    NSLog(@"ClientExtensionFinalizer()");    
    // Nothing to clean up.    
    NSLog(@"Exiting ClientExtensionFinalizer()");
    return;
}