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

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import android.app.Activity;
import android.app.NotificationManager;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;
import android.util.Log;

/**
 * This class specifies the mapping between the AS3 functions and the Java native classes.
 *
 * @see http://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be-fd70de2132a8f3874e-8000.html
 */
public class ClientExtensionContext extends FREContext {

    /*
     * @private
     */ 
    private static final String TAG = "[ClientExtensionContext]";
    
    public NotificationManager notificationManager;
    public SensorManager sensorManager;
    public Sensor ambientLightSensor;
    public Sensor barometerSensor;
    public Sensor gravitySensor;
    public Sensor gyroscopeSensor;
    public Sensor magnetometerSensor;
    public Sensor orientationSensor;
    public Sensor proximitySensor;
    
    /*
     * @inheritDoc
     */
    @Override
    public void dispose() {
        Log.d(TAG, "dispose");
        sensorManager = null;
    }
    
    /*
     * Initializer method
     */
    public void initialize() {
        Log.d(TAG, "initialize");
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE); 
        
        /*IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){
                int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = i.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float battPct = level/(float)scale;       
            }
        };*/
    }

    /*
     * @inheritDoc
     */
    @Override
    public Map<String, FREFunction> getFunctions() {
        // create wrapper map
        Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
        // define global wrapper
        functionMap.put("callNative", new CommandCallNative() );
        // return map
        return functionMap;
    }
}
