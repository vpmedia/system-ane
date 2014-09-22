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
 *  ClientExtensionTest
 *  ClientExtensionTest.h
 *  Purpose: DLL Import list
 *
 *  @author Andras Csizmadia (andras at vpmedia.hu)
 */

#ifndef EXTERNS_H
#define EXTERNS_H

//----------------------------------
// Import Standard Headers
//----------------------------------

// Input/output
#include <stdio.h>
// General utilities: memory management, program utilities, string conversions, random numbers
#include <stdlib.h>
// Fixed-width integer types
#include <stdint.h>
// String handling
#include <string.h>
// Macros supporting type boolean
#include <stdbool.h>
// Common mathematics functions
#include <math.h>
// Sizes of basic types
#include <limits.h>
// Vector type
#include <vector>
// IOStream
#include <iostream> 

//----------------------------------
//  CPP
//----------------------------------

#ifdef __cplusplus
extern "C" {
#endif

//----------------------------------
//  Program
//----------------------------------

//----------------------------------
//  End
//----------------------------------

#ifdef __cplusplus
}
#endif

#endif /* EXTERNS_H */
	