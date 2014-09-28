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

    // List of available commands  
    private static final int EXT_LOG = 1; // send message into system log
    private static final int EXT_DEVICE_ID = 2; // get device id (no param)
    private static final int EXT_VIBRATE = 3; // vibrate device (optional time param)
    private static final int EXT_NOTIFY = 4; // send notification (requires message param)
    private static final int EXT_START_SENSOR = 5; // start a sensor by type (requires type and delay param)
    private static final int EXT_STOP_SENSOR = 6; // stop a sensor by type (requires type param)
    private static final int EXT_HAS_SENSOR = 7; // check for sensor by type (requires type param)
    private static final int EXT_LIST_SENSOR = 8; // list available sensors (no param)

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
                case EXT_VIBRATE:
                    if (argc > 1) {
                        result = FREObject.newObject(clientExtensionContext.vibrate(argv[1].getAsInt()));
                    } else {
                        result = FREObject.newObject(clientExtensionContext.vibrate(1000));
                    }
                    break;
                case EXT_NOTIFY:
                    clientExtensionContext.sendNotify(0, "Hello ANE", "Notification");
                    result = FREObject.newObject(1);
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
