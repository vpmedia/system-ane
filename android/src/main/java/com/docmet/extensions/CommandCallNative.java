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

import android.app.Activity;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * Global API wrapper
 * <p>http://help.adobe.com/en_US/air/extensions/WS24fe7069b89c5bc5-6148f737132a836f332-8000.html<p/>
 */
public class CommandCallNative implements FREFunction {
    /*
     * @private
     */
    private static final String TAG = "[CommandCallNative]";

    //----------------------------------
    // Constants
    //----------------------------------

    // List of available commands  
    private static final int EXT_LOG = 1;
    private static final int EXT_VIBRATE = 2;
    private static final int EXT_SPEECH = 3;

    private static final int EXT_DEVICE_ID = 100;
    private static final int EXT_DEVICE_IMEI = 101;
    private static final int EXT_DEVICE_PHONE = 102;
    private static final int EXT_DEVICE_PROP = 103;

    private static final int EXT_NOTIFY = 200;
    private static final int EXT_TOAST = 201;

    private static final int EXT_START_SENSOR = 300;
    private static final int EXT_STOP_SENSOR = 301;
    private static final int EXT_HAS_SENSOR = 302;
    private static final int EXT_LIST_SENSOR = 303;

    /*
     * Command entry point
     */
    public FREObject call(FREContext ctx, FREObject[] argv) {
        int argc = argv.length;
        FREObject result = null;
        try {
            ClientExtensionContext clientExtensionContext = (ClientExtensionContext) ctx;
            Activity activity = ctx.getActivity();
            int type = argv[0].getAsInt();
            //Log.d(TAG, "call: " + Integer.toString(type) + " (" + Integer.toString(argc) + ")");
            switch (type) {
                case EXT_LOG:
                    if (argc > 1) {
                        Log.d(TAG, "Client: " + argv[1].getAsString());
                        result = FREObject.newObject(1);
                    } else {
                        result = FREObject.newObject(0);
                    }
                    break;
                case EXT_DEVICE_ID:
                    result = FREObject.newObject(clientExtensionContext.getDeviceId());
                    break;
                case EXT_DEVICE_IMEI:
                    result = FREObject.newObject(clientExtensionContext.getDeviceIMEI());
                    break;
                case EXT_DEVICE_PHONE:
                    result = FREObject.newObject(clientExtensionContext.getDeviceNumber());
                    break;
                case EXT_DEVICE_PROP:
                    result = FREObject.newObject(clientExtensionContext.getSystemProperty("os.arch"));
                    break;
                case EXT_VIBRATE:
                    if (argc > 1) {
                        result = FREObject.newObject(clientExtensionContext.vibrate(argv[1].getAsInt()));
                    } else {
                        result = FREObject.newObject(clientExtensionContext.vibrate(1000));
                    }
                    break;
                case EXT_NOTIFY:
                    result = FREObject.newObject(clientExtensionContext.sendNotify(0, "Hello ANE", "Notification"));
                    break;
                case EXT_TOAST:
                    result = FREObject.newObject(clientExtensionContext.sendToast("Hello ANE"));
                    break;
                case EXT_START_SENSOR:
                    if (argc > 2) {
                        clientExtensionContext.addSensorListener(argv[1].getAsInt(), argv[2].getAsInt());
                        result = FREObject.newObject(1);
                    } else {
                        result = FREObject.newObject(0);
                    }
                    break;
                case EXT_STOP_SENSOR:
                    if (argc > 1) {
                        clientExtensionContext.removeSensorListener(argv[1].getAsInt());
                        result = FREObject.newObject(1);
                    } else {
                        result = FREObject.newObject(0);
                    }
                    break;
                case EXT_LIST_SENSOR:
                    result = FREObject.newObject(clientExtensionContext.getSensorList().toString());
                    break;
                case EXT_HAS_SENSOR:
                    result = FREObject.newObject(0);
                    break;
                case EXT_SPEECH:
                    result = FREObject.newObject(clientExtensionContext.speech("Hello ANE Text to Speech"));
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
}
