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

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;

/**
 * This class specifies the mapping between the AS3 functions and the Java native classes.
 *
 * @see http://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be-fd70de2132a8f3874e-8000.html
 * @see https://developer.android.com/reference/android/hardware/Sensor.html
 */
public class ClientExtensionContext extends FREContext implements SensorEventListener  {

    /*
     * @private
     */ 
    private static final String TAG = "[ClientExtensionContext]";
        
    /*
     * Initializer method
     */
    public void initialize() {
        Log.d(TAG, "initialize");
           
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
    public void dispose() {
        Log.d(TAG, "dispose");
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
    
    /*
     * Attach a sensor listener
     */
    public void addSensorListener(int sensorType) {        
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE); 
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_GAME);               
    }
    
    /*
     * Detach a sensor listener
     */
    public void removeSensorListener(int sensorType) {
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);   
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(sensorType));           
    }
    
    /*
     * Implements SensorEventListener.onAccuracyChanged
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {   
        try {    
            String sensorEventType = "ACCURACY_CHANGE_" + Integer.toString(sensor.getType());          
            dispatchStatusEventAsync(sensorEventType, Integer.toString(accuracy));                        
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }
    }

    /*
     * Implements SensorEventListener.onSensorChanged
     */
    public void onSensorChanged(SensorEvent event) {     
        try {                            
            Sensor sensor = event.sensor;       
            String sensorEventType = "VALUE_CHANGE_" + Integer.toString(sensor.getType()); 
            float sensorLevel = event.values[0];
            dispatchStatusEventAsync(sensorEventType, Float.toString(sensorLevel)); 
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }               
    } 
}
