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
import android.hardware.Sensor;
import android.os.Vibrator;
import android.provider.Settings.Secure;

/**
 * Global API wrapper.
 *
 * @see http://help.adobe.com/en_US/air/extensions/WS24fe7069b89c5bc5-6148f737132a836f332-8000.html
 */
public class CommandCallNative implements FREFunction  {
    
    /*
     * @private
     */ 
    private static final String TAG = "[CommandCallNative]";
        
    private static final int EXT_GET_DEVICE_ID = 1;
    
    private static final int EXT_VIBRATE = 2;
    
    private static final int EXT_NOTIFY = 3;
    
    private static final int EXT_START_BAROMETER_LISTENER = 100;
    private static final int EXT_STOP_BAROMETER_LISTENER = 101;
    
    private static final int EXT_START_BATTERY_LISTENER = 200;
    private static final int EXT_STOP_BATTERY_LISTENER = 201;
        
    private static final int EXT_START_GRAVITY_LISTENER = 300;
    private static final int EXT_STOP_GRAVITY_LISTENER = 301;
            
    private static final int EXT_START_GYROSCOPE_LISTENER = 400;
    private static final int EXT_STOP_GYROSCOPE_LISTENER = 401;
    
    private static final int EXT_START_MAGNETOMETER_LISTENER = 500;
    private static final int EXT_STOP_MAGNETOMETER_LISTENER = 501;
        
    private static final int EXT_START_ORIENTATION_LISTENER = 600;
    private static final int EXT_STOP_ORIENTATION_LISTENER = 601;
    
    private static final int EXT_START_PROXIMITY_LISTENER = 700;
    private static final int EXT_STOP_PROXIMITY_LISTENER = 701;
    
    /*
     * Command entry point
     */ 
    public FREObject call(FREContext ctx, FREObject[] passedArgs) {
        //ClientExtensionContext clientExtensionContext = (ClientExtensionContext) ctx;
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
                case EXT_NOTIFY:
                        result = FREObject.newObject(0);
                        break;
                case EXT_START_BAROMETER_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_BAROMETER_LISTENER: 
                        result = FREObject.newObject(0);
                        break;   
                case EXT_START_BATTERY_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_BATTERY_LISTENER: 
                        result = FREObject.newObject(0); 
                        break;      
                case EXT_START_GRAVITY_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_GRAVITY_LISTENER:
                        result = FREObject.newObject(0); 
                        break;           
                case EXT_START_GYROSCOPE_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_GYROSCOPE_LISTENER: 
                        result = FREObject.newObject(0);
                        break;   
                case EXT_START_MAGNETOMETER_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_MAGNETOMETER_LISTENER:
                        result = FREObject.newObject(0);
                        break;     
                case EXT_START_ORIENTATION_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_ORIENTATION_LISTENER: 
                        result = FREObject.newObject(0);   
                        break;
                case EXT_START_PROXIMITY_LISTENER:
                        result = FREObject.newObject(0);
                        break;
                case EXT_STOP_PROXIMITY_LISTENER:
                        result = FREObject.newObject(0);
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
    
    // TODO: move extMethods to separate class which is testable and not dependent on the AIR runtime.
    
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
        vibrator.vibrate(500);
        return 1;
    }
}
