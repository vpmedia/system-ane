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
package com.docmet.extensions {
import flash.events.EventDispatcher;
import flash.events.StatusEvent;
import flash.external.ExtensionContext;

/**
 * The ClientExtension class is the interface for the application native extension (ANE).
 */
public class SystemExtension extends EventDispatcher {
    //----------------------------------
    //  Private static properties
    //----------------------------------

    /**
     * @private
     */
    private static var isInstantiated:Boolean;

    /**
     * @private
     */
    private static var context:ExtensionContext;

    /**
     * @private
     */
    private static var instance:SystemExtension;

    //----------------------------------
    //  Public variables
    //----------------------------------

    /**
     * @private
     */
    private var loggingHandler:Function;

    //----------------------------------
    //  Public static constants
    //----------------------------------

    /**
     * Keep in sync with CommandCallNative.java command types
     */
    public static const EXT_LOG:uint = 1; 
    public static const EXT_DEVICE_ID:uint = 2; 
    public static const EXT_VIBRATE:uint = 3;  
    public static const EXT_NOTIFY:uint = 4;
    public static const EXT_START_SENSOR:uint = 5;
    public static const EXT_STOP_SENSOR:uint = 6;
    public static const EXT_HAS_SENSOR:uint = 7;
    
    /**
     * @see https://developer.android.com/reference/android/hardware/Sensor.html
     */
    public static const EXT_SENSOR_TYPE_ALL:uint = 0xffffffff;
    public static const EXT_SENSOR_TYPE_ACCELEROMETER:uint = 0x00000001;
    public static const EXT_SENSOR_TYPE_AMBIENT_TEMPERATURE:uint = 0x0000000d;
    public static const EXT_SENSOR_TYPE_GAME_ROTATION_VECTOR:uint = 0x0000000f;
    public static const EXT_SENSOR_TYPE_GEOMAGNETIC_ROTATION_VECTOR:uint = 0x00000014;
    public static const EXT_SENSOR_TYPE_GRAVITY:uint = 0x00000009;
    public static const EXT_SENSOR_TYPE_GYROSCOPE:uint = 0x00000004;
    public static const EXT_SENSOR_TYPE_GYROSCOPE_UNCALIBRATED:uint = 0x00000010;
    public static const EXT_SENSOR_TYPE_HEART_RATE:uint = 0x00000015;
    public static const EXT_SENSOR_TYPE_LIGHT:uint = 0x00000005;
    public static const EXT_SENSOR_TYPE_LINEAR_ACCELERATION:uint = 0x0000000a;
    public static const EXT_SENSOR_TYPE_MAGNETIC_FIELD:uint = 0x00000002;
    public static const EXT_SENSOR_TYPE_MAGNETIC_FIELD_UNCALIBRATED:uint = 0x0000000e;
    public static const EXT_SENSOR_TYPE_ORIENTATION:uint = 0x00000003;
    public static const EXT_SENSOR_TYPE_PRESSURE:uint = 0x00000006;
    public static const EXT_SENSOR_TYPE_PROXIMITY:uint = 0x00000008;
    public static const EXT_SENSOR_TYPE_RELATIVE_HUMIDITY:uint = 0x0000000c;
    public static const EXT_SENSOR_TYPE_ROTATION_VECTOR:uint = 0x0000000b;
    public static const EXT_SENSOR_TYPE_SIGNIFICANT_MOTION:uint = 0x00000011;
    public static const EXT_SENSOR_TYPE_STEP_COUNTER:uint = 0x00000013;
    public static const EXT_SENSOR_TYPE_STEP_DETECTOR:uint = 0x00000012;
    public static const EXT_SENSOR_TYPE_STEP_TEMPERATURE:uint = 0x00000007;

    //----------------------------------
    //  Constructor
    //----------------------------------

    /**
     * Create a new ClientExtension.
     */
    public function SystemExtension() {
        super();
        if (isInstantiated)
            return;
        instance = this;
        loggingHandler = log;
        createInstance();
    }

    /**
     * @private
     */
    private function log(message:String):void {
        trace(this, message);
    }

    //----------------------------------
    //  Public methods
    //----------------------------------

    /**
     * Native wrapper
     */
    public function callNative(...args):Object {
        var result:Object;
        try {
            result = context.call.apply(null, ["callNative"].concat(args));
        }
        catch (error:Error) {
            loggingHandler(error.toString());
        }
        loggingHandler("callNative: " + args[0] + " | " + args + " => " + result);
        return result;
    }


    /**
     * Will destroy the ClientExtension object.
     */
    public function dispose():void {
        loggingHandler("dispose");
        if (context) {
            context.removeEventListener(StatusEvent.STATUS, onStatus);
            context.dispose();
            context = null;
        }
        loggingHandler = null;
        instance = null;
        isInstantiated = false;
    }
    
    /**
     * Will set the logging handler
     */
    public function setLogger(handler:Function):void {
        loggingHandler = handler;
    }

    //----------------------------------
    //  Common Getter/Setter methods
    //----------------------------------

    //----------------------------------
    //  Event handler
    //----------------------------------

    /**
     * @private
     */
    private function onStatus(event:StatusEvent):void {
        //loggingHandler("ClientExtension::onStatus: " + event.toString());
        // TODO: is event.clone() needed here?!
        // VPMedia: Since it's coming from the Native layer and we do not modify anything, I don't think so.
        dispatchEvent(event);
    }

    //----------------------------------
    //  Public static methods
    //----------------------------------

    /**
     * Will create a new singleton instance of the ClientExtension class.
     */
    public static function createInstance():void {
        trace("ClientExtension::createInstance");
        try {
            context = ExtensionContext.createExtensionContext("com.docmet.extensions.SystemExtension", "");
            context.addEventListener(StatusEvent.STATUS, instance.onStatus, false, 0, true);
            isInstantiated = true;
        }
        catch (error:Error) {
            trace(error.toString());
        }
    }


}
}
