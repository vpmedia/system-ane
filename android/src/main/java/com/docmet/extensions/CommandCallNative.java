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
    
    // Commands
    private static final int EXT_VIBRATE = 1;
    private static final int EXT_GET_DEVICE_ID = 2;
    
    /*
     * Command entry point
     */ 
    public FREObject call(FREContext ctx, FREObject[] passedArgs) {
        FREObject result = null;
        String commandResult = null;        
        try {
            Activity activity = ctx.getActivity();
            FREObject typeObj = passedArgs[0];
            int type = typeObj.getAsInt();            
            switch (type) {
                case EXT_VIBRATE:
                        result = FREObject.newObject(extVibrate(activity));
                        break;
                case EXT_GET_DEVICE_ID:  
                        result = FREObject.newObject(extGetSecureId(activity));
                        break;
                default: 
                        result = FREObject.newObject(0);
                        break;
            }
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }
        return result;
    } 
    
    /*
     * @private
     */ 
    private String extGetSecureId(Activity activity) {
        String secureId = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
        return secureId;
    }

    /*
     * @private
     */ 
    private int extVibrate(Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1);
        return 1;
    }
}
