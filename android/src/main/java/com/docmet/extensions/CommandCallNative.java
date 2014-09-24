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

import java.util.List;
import java.util.ArrayList;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import android.util.Log;

import android.app.Activity;
import android.content.Context;

import android.os.Vibrator;

import android.provider.Settings.Secure;

/**
 * Global API wrapper.
 */
public class CommandCallNative implements FREFunction  {
    
    /*
     * @private
     */ 
    private static final String TAG = "[CommandCallNative]";
    
    /*
     * Command entry point
     */ 
    public FREObject call(FREContext ctx, FREObject[] passedArgs) {
        FREObject result = null;
        String commandResult = null;
        try {
            FREObject typeObj = passedArgs[0];
            int type = typeObj.getAsInt();
            List<String> nativeList = new ArrayList<String>();
            int n = passedArgs.length;
            if(n > 0) {
                int i;
                for(i = 1; i < n; i++) {
                    nativeList.add(passedArgs[i].getAsString());                
                }            
            }
            String[] nativeArgs = nativeList.toArray(new String[nativeList.size()]);
            commandResult = callNative(type, nativeArgs.length, nativeArgs);
            Log.d(TAG, "call: " + Integer.toString(type) + " => " + commandResult);
            result = FREObject.newObject(commandResult);            
            // Vibrate test
            Activity activity = ctx.getActivity();
            Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1);
            // SecureId test
            String secureId = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }
        return result;
    }    
      
    /*
     * JNI Example
     */ 
    public native String callNative(int type, int argc, String[] argv);
    
    /*
     * @private
     */ 
    static {
        System.loadLibrary("MainJNI");
    }
}
