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
public class BotanCryptoExtension extends EventDispatcher {
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
    private static var instance:BotanCryptoExtension;

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
     * TBD
     */
    public static const EXT_SHA_512:uint = 1;

    //----------------------------------
    //  Constructor
    //----------------------------------

    /**
     * Create a new ClientExtension.
     */
    public function BotanCryptoExtension() {
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
        loggingHandler("ClientExtension::onStatus: " + event.toString());
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
            context = ExtensionContext.createExtensionContext("com.docmet.extensions.BotanCryptoExtension", "");
            context.addEventListener(StatusEvent.STATUS, instance.onStatus, false, 0, true);
            isInstantiated = true;
        }
        catch (error:Error) {
            trace(error.toString());
        }
    }


}
}
