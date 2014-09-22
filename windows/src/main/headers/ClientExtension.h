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
 *  ClientExtension.h
 *  Purpose: Client AIR Native Extension header
 *
 *  @author Andras Csizmadia (andras at vpmedia.hu)
 *  @version 1.0 07/09/14
 */

#ifndef CLIENTEXTENSION_H
#define CLIENTEXTENSION_H

//----------------------------------
// Import Standard Headers
//----------------------------------

// Input/output
#include "stdio.h"
// General utilities: memory management, program utilities, string conversions, random numbers
#include "stdlib.h"
// Fixed-width integer types
#include "stdint.h"
// String handling
#include "string.h"
// Macros supporting type boolean
#include "stdbool.h"
// Common mathematics functions
#include "math.h"
// Sizes of basic types
#include "limits.h"
// Windows related helpers
#include "windows.h"

//----------------------------------
// Import Library Headers
//----------------------------------

//----------------------------------
//  CPP
//----------------------------------

#ifdef __cplusplus
extern "C" {
#endif

//----------------------------------
//  DLL Import/Export/Call Helper
//----------------------------------

#ifdef _WIN32
    #ifdef EXPORT
        #define DLLIMPORT __declspec (dllexport)
    #else
        #define DLLIMPORT __declspec (dllimport)
    #endif
    //#define DLLCALL __cdecl
#else 
  #define DLLIMPORT
  //#define DLLCALL
#endif

//----------------------------------
//  ANE API Wrapper Helper
//----------------------------------

#define EXPORT_FUNC( to, fname, ffunc ) \
    to.name = (const uint8_t*) fname; \
    to.functionData = NULL; \
    to.function = &ffunc; 

// Import Adobe Header
// Linked: $AIR_SDK\lib\win\FlashRuntimeExtensions.lib
#include "FlashRuntimeExtensions.h" 

/* Entry point of ANE */
DLLIMPORT void ClientExtensionInitializer(void** extData, FREContextInitializer* ctxInitializer, FREContextFinalizer* ctxFinalizer);
/* Exit point of ANE */
DLLIMPORT void ClientExtensionFinalizer(void* extData);

//----------------------------------
// Logging Helpers
//----------------------------------

#define LOG_ENABLED 1
#define LOG_FILE_ENABLED 1
#define LOG_CONSOLE_ENABLED 0
#define LOG_FILE "C:/Work/system-ane.log"

#ifdef LOG_ENABLED
    #define DEBUG_PRINT(msg) print(msg)
#else
    #define DEBUG_PRINT(msg)
#endif

/**
 * Debug method for strings
 */
void print(const char * msg);

//----------------------------------
//  End
//----------------------------------

#ifdef __cplusplus
}
#endif

#endif /* CLIENTEXTENSION_H */
	