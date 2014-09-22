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

/**
 *  ClientExtension
 *  ClientExtension.cpp
 *  Purpose: System Wrapper for Adobe AIR Native Extension
 *
 *  @author Andras Csizmadia (andras at vpmedia.hu)
 *  @version 1.0.0
 */

// Import ClientExtension Header
#include "ClientExtension.h"

//----------------------------------
// Constants
//----------------------------------

// Common
#define EXT_SHA_512 1

//----------------------------------
// Properties
//----------------------------------

/* The FREContext variable that represents this extension context. */
FREContext dllContext;

//----------------------------------
//  API
//----------------------------------


/**
 *  Global Wrapper
 *  
 *  @ctx The FREContext variable that represents this extension context.
 *  @funcData A void*. This pointer points to the data you associated with this FREFunction function in its FRENamedFunction structure.
 *  @argc The number of elements in the argv parameter.
 *  @argv An array of FREObject variables. 
 *  
 *  @return Int32 serialized as an FREObject variable.
 */
FREObject callNative(FREContext ctx, void* funcData, uint32_t argc, FREObject argv[]) {
    // result data
    FREObject result = NULL;  
    // get command type parameter
    int32_t type; 
    FREGetObjectAsInt32(argv[0], &type);
    // get result
    switch (type) {
         default:
            FRENewObjectFromInt32(0, &result); 
            break;
    }      
    // Return result
    return result;
}

//----------------------------------
//  ANE Initializer and Destroyer
//----------------------------------

/**
 *  Context entry point
 *  
 *  @extData The extension data that the extension initialization function had created. 
 *  @ctxType The context type. The ActionScript method ExtensionContext.createExtensionContext()is passed a parameter that specifies the context type. The runtime passes this string value to the context initialization function. 
 *  @ctx The FREContext variable that represents this extension context.
 *  @numFunctions A pointer to the number of functions to set.
 *  @functions A pointer of a pointer to the functions to set.
 */
void contextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctions, const FRENamedFunction** functions) {   
    // log message
    //DEBUG_PRINT("#################### contextInitializer ####################\n");
    // define API length
    *numFunctions = 1;
    // allocate API functions by length
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * (*numFunctions));
    // assign API functions
    EXPORT_FUNC(func[0], "callNative", callNative); 
    // save API
    *functions = func;
    // save context
    dllContext = ctx;
}

/**
 *  Context exit point
 *   
 *  @ctx The FREContext variable that represents this extension context.
 */
void contextFinalizer(FREContext ctx) {
    return;
}

//----------------------------------
//  ANE Entry and Exit points
//----------------------------------

/**
 *  Extension entry point
 *  
 *  @extData A pointer to a pointer to the extension data of the native extension. Create a data structure to hold extension-specific data.
 *  @ctxInitializer A pointer to the context initialization function.
 *  @ctxFinalizer A pointer to the context finalizer function. The runtime calls this function when the runtime disposes of the extension context. 
 */
void ClientExtensionInitializer(void** extData, FREContextInitializer* ctxInitializer, FREContextFinalizer* ctxFinalizer) {
    *ctxInitializer = &contextInitializer;
    *ctxFinalizer = &contextFinalizer;
}

/**
 *  Extension exit point
 *  
 *  @extData A pointer to the data that the runtime later passes to each new extension context.
 */
void ClientExtensionFinalizer(void* extData) {
    FREContext nullCTX;
    nullCTX = 0; //We want to point to the current context.
    contextFinalizer(nullCTX);
    return;
}

//----------------------------------
// Logging Helpers
//----------------------------------

/**
 *  Logger helper
 *  
 *  @msg The message to log.
 */
void print(const char * msg)
{
    if(LOG_FILE_ENABLED)
    {
        FILE *file;
        // get file name
        char fname[] = LOG_FILE;
        // open file as append
        file = fopen(fname, "a");
        // check for existing file
        if(file == NULL)
        {
            // file not exists, create it
            file = fopen(fname, "w");
            // check for read only drives
            if(file == NULL)
            {
                return;
            }
        }
        // write out content
        fprintf(file, "%s", msg);
        // close file
        fclose(file);
    }
    if(LOG_CONSOLE_ENABLED)
    {
        printf("%s: ", msg);
    }
}
