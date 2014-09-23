#import "ClientExtension.h"
#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

#define UIColorFromRGB(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

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