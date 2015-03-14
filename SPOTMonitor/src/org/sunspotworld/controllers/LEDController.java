/**
 * specifies methods which should appear in any LEDController Implementation.
 * LEDS are first come first serve; e.g. if Service X1 has turned LED 
 * '1' on, and Service X2 attempts to power LED 1, X2's request will be dropped. 
 * @author Dominic Lindsay
 */
package org.sunspotworld.controllers;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.util.Utils;

/**
 * turnLEDOn
 * turnLEDOff
 * flashServiceLED
 * @author babbleshack
 */
public abstract class LEDController {  
    /**
     * LED INDEX FOR SERVICES
     */
    public static final int THERMAL_LED = 0;
    public static final int LIGHT_LED   = 1;
    public static final int ACCEL_LED   = 2;
    public static final int COMPASS_LED = 3;
    public static final int ROAMING_LED = 4;
    public static final int TOWER_LED   = 5;
    public static final int IMPACT_LED  = 3;    
    public static final int STATUS_LED  = 7; //SERVICE LED USED FOR PACKAGE FEEDBACK
    private static final int DIVISOR = 10;
    /**
     * First Come First Serve LED Power on method
     * @param led LED to power on
     * @param colour colour of LED
     */
    public static void turnLEDOn(final ITriColorLED led, final LEDColor colour){
        if(led.isOn())
            return;
        led.setColor(new LEDColor(
                (colour.red()/DIVISOR),
                (colour.green()/DIVISOR), (colour.blue()/DIVISOR))
        );
        led.setOn();
    };
    /**
     * Always powers LED off
     * @param led led to turn off
     */
    public static void turnLEDOff(final ITriColorLED led){
        led.setOff();
    };
    /**
     * flashes led, if led is already powered request is dropped
     * @param led led to flash
     * @param colour colour
     */
    public static void flashLED(final ITriColorLED led, final LEDColor colour){
        if(led.isOn())
            return;
        led.setColor(new LEDColor(
                (colour.red()/DIVISOR),
                (colour.green()/DIVISOR), (colour.blue()/DIVISOR))
        );
        led.setOn();
        Utils.sleep(500);
        led.setOff();
    };
    /**
     * helper method, returns an instance of the TriColourLEDArray
     * @return 
     */
    public static ITriColorLEDArray getLEDArrayInstance(){
        return (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    }
    /**
     * returns led at index value
     * @param ledIndex index of led to return
     * @return 
     */
    public static ITriColorLED getLED(int ledIndex) {
        return ((ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class)).
                getLED(ledIndex);
    }
}
