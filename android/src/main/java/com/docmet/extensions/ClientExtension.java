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
package com.docmet.extensions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

import android.util.Log;

/**
 * Initialization and finalization class of native extension.
 */
public class ClientExtension implements FREExtension {
    /*
     * @private
     */ 
    private static final String TAG = "[ClientExtension]";
    
    /*
     * @private
     */ 
    private ClientExtensionContext context;
    
    /*
     * @inheritDoc
     */
    public FREContext createContext(String extId) {  
        Log.d(TAG, "createContext: " + extId);  
        if(context == null) {
            context = new ClientExtensionContext();
        }
        return context;
    }

    /*
     * @inheritDoc
     */  
    public void initialize( ) {
        Log.d(TAG, "initialize");
    }
    
    /*
     * @inheritDoc
     */
    public void dispose() {
        Log.d(TAG, "dispose");
        if(context != null) {
            context.dispose();
        }
        context = null;
    }
}
