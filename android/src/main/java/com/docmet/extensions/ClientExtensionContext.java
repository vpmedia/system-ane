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
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class specifies the mapping between the AS3 functions and the Java native classes.
 * <p>http://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be-fd70de2132a8f3874e-8000.html
 * https://developer.android.com/reference/android/hardware/Sensor.html<p/>
 */
public class ClientExtensionContext extends FREContext implements SensorEventListener {

    //

     /*
     * @private
     */
    private static final String TAG = "[ClientExtensionContext]";

    /*
     * @private
     */
    private static final Map<Integer, String> sensorTypeNameMap = new HashMap<Integer, String>();

    /*
     * Initializer method
     */
    public void initialize() {
        Log.d(TAG, "initialize");
        sensorTypeNameMap.put(Sensor.TYPE_ACCELEROMETER, "ACCELEROMETER");
        sensorTypeNameMap.put(Sensor.TYPE_ACCELEROMETER, "ACCELEROMETER");
        sensorTypeNameMap.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "AMBIENT_TEMPERATURE");
        sensorTypeNameMap.put(Sensor.TYPE_GAME_ROTATION_VECTOR, "GAME_ROTATION_VECTOR");
        sensorTypeNameMap.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, "GEOMAGNETIC_ROTATION_VECTOR");
        sensorTypeNameMap.put(Sensor.TYPE_GRAVITY, "GRAVITY");
        sensorTypeNameMap.put(Sensor.TYPE_GYROSCOPE, "GYROSCOPE");
        sensorTypeNameMap.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, "GYROSCOPE_UNCALIBRATED");
        sensorTypeNameMap.put(Sensor.TYPE_HEART_RATE, "HEART_RATE");
        sensorTypeNameMap.put(Sensor.TYPE_LIGHT, "LIGHT");
        sensorTypeNameMap.put(Sensor.TYPE_LINEAR_ACCELERATION, "LINEAR_ACCELERATION");
        sensorTypeNameMap.put(Sensor.TYPE_MAGNETIC_FIELD, "MAGNETIC_FIELD");
        sensorTypeNameMap.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, "MAGNETIC_FIELD_UNCALIBRATED");
        sensorTypeNameMap.put(Sensor.TYPE_ORIENTATION, "ORIENTATION");
        sensorTypeNameMap.put(Sensor.TYPE_PRESSURE, "PRESSURE");
        sensorTypeNameMap.put(Sensor.TYPE_PROXIMITY, "PROXIMITY");
        sensorTypeNameMap.put(Sensor.TYPE_RELATIVE_HUMIDITY, "RELATIVE_HUMIDITY");
        sensorTypeNameMap.put(Sensor.TYPE_ROTATION_VECTOR, "ROTATION_VECTOR");
        sensorTypeNameMap.put(Sensor.TYPE_SIGNIFICANT_MOTION, "SIGNIFICANT_MOTION");
        sensorTypeNameMap.put(Sensor.TYPE_STEP_COUNTER, "STEP_COUNTER");
        sensorTypeNameMap.put(Sensor.TYPE_STEP_DETECTOR, "STEP_DETECTOR");
        sensorTypeNameMap.put(Sensor.TYPE_TEMPERATURE, "TEMPERATURE");
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
        Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
        functionMap.put("callNative", new CommandCallNative());
        return functionMap;
    }

    /*
     * Sends a Notification
     */
    public void sendNotify(int id, String message, String subject) {
        Activity activity = getActivity();
        Context context = activity.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setContentTitle(message)
                .setContentText(subject)
                .build();
        notificationManager.notify(id, notification);
    }

    /*
     * Returns a device UUID
     */
    public String getDeviceId() {
        Activity activity = getActivity();
        String result = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        return result;
    }

    /*
     * Vibrates the device
     */
    public boolean vibrate(long time) {
        boolean result = false;
        Activity activity = getActivity();
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(time);
            result = true;
        }
        return result;
    }

    /*
     * Attach a Intent listener
     */
    public void addIntentListener(int intentType) {
        /*IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(reciever, filter);
        BroadcastReceiver reciever = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){
                int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = i.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float battPct = level/(float)scale;
            }
        };*/
    }

    /*
     * Lists available Sensors
     */
    public List<Sensor> getSensorList() {
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> result = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : result) {
            Log.d(TAG, sensor.getName() + ", " + sensorTypeNameMap.get(sensor.getType()) + ", " + sensor.getVendor());
        }
        return result;
    }

    /*
     * Attach a Sensor listener
     */
    public void addSensorListener(int sensorType, int sensorDelay) {
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorType), sensorDelay);
    }

    /*
     * Detach a Sensor listener
     */
    public void removeSensorListener(int sensorType) {
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(sensorType));
    }

    /*
     * Implements SensorEventListener::onAccuracyChanged
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        try {
            String sensorType = sensorTypeNameMap.get(sensor.getType());
            String sensorEventType = sensorType + "_ACCURACY";
            dispatchStatusEventAsync(sensorEventType, Integer.toString(accuracy));
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }
    }

    /*
     * Implements SensorEventListener::onSensorChanged
     */
    public void onSensorChanged(SensorEvent event) {
        try {
            Sensor sensor = event.sensor;
            String sensorType = sensorTypeNameMap.get(sensor.getType());
            String sensorEventType = sensorType + "_VALUE";
            float sensorLevel = event.values[0];
            dispatchStatusEventAsync(sensorEventType, Float.toString(sensorLevel));
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
        }
    }
}
