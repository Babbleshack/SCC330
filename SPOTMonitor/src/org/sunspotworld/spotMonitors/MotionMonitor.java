/**
 * Motion Moniter
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

//resources
import com.sun.spot.resources.Resources;

//These two packages need to be imported to allow you to create an instance
//of EDemoBoard and to access the I/O pins on the board
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.io.IScalarInput;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.IToneGenerator;

import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.SensorEvent;


import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;

import org.sunspotworld.homePatterns.Observable;
import java.io.IOException;

public class MotionMonitor extends Observable implements IMotionMonitor
{
    private long lastMotion = 0;
    private EDemoBoard demo = EDemoBoard.getInstance(); //returns an instance of EDemoBoard through which the I/O pins can be accessed
    private ITriColorLEDArray leds;
    private IToneGenerator toneGen;
    private IScalarInput irSensor = irSensor = demo.getScalarInputs()[EDemoBoard.A0];
    private ITriColorLED led = leds.getLED(0);
    private IConditionListener thesholdCondition;
    private IConditionListener resetConditionListener;
    private Condition conditionHasBeenMet;
    private Condition waitForReset;
    private SunspotPort port;

    private static final double FREQ = 50.0;
    private static final int FREQ_DUR = 100;
    private static final int INTENSITY = 100;
    private static final int HIGH = 1023;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;

    public MotionMonitor()  
    {
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        toneGen = (IToneGenerator) Resources.lookup(IToneGenerator.class);
        led.setRGB(250, 0, 0);
		led.setOn();
		demo = EDemoBoard.getInstance();
		this.irSensor = irSensor = demo.getScalarInputs()[EDemoBoard.A0];
        try {
            this.port = new SunspotPort(SunspotPort.MOTION_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        lastMotion = 0;
        this.prepareConditions();
    }
 
    /**
     * innitializes conditions and starts them
     */
    private void prepareConditions()
    {
        /**
         * Called when reading goes above threshold
         * Calls observers notify method.
         * Stop thresholdCondition and starts waitForResetCondition
         */
        thesholdCondition = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                MotionMonitor.this.hasChanged();
                MotionMonitor.this.notifyObservers();
                MotionMonitor.this.conditionHasBeenMet.stop();
                MotionMonitor.this.waitForReset.start();
                
            }
        };
         /**
         * resets Threshold Condition Listener
         */
        resetConditionListener = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
              MotionMonitor.this.waitForReset.stop();
              MotionMonitor.this.conditionHasBeenMet.start();
            }
        };
        /**
         * Sampling Condition used to monitor instrument data
         */
        conditionHasBeenMet = new Condition(irSensor, thesholdCondition, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
              return MotionMonitor.this.getDataAsInt() == HIGH;
          }  
        };
        /**
         * reset callback, used to restart sampling.
         */
        waitForReset = new Condition(irSensor, resetConditionListener, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
              return MotionMonitor.this.getDataAsInt() != HIGH;
          }
        };    
    }

    /**
     * IMPORTED COMMENT FROM GERALD
     * returns motion sensor value. When the sensors detects a motion, its
     * output pin goes high (decimal equivalent of 1023). When there is no motion
     * the output reverts to zero. When motion is detected, LED (0) lights up
     * green else red
     */
    public int getSensorValue()
    {
       try
       {
            int value = irSensor.getValue();
            if(value == HIGH)
            {
               //motion detected
            	lastMotion = System.currentTimeMillis(); 
                led.setRGB(0, INTENSITY, 0);
         		toneGen.startTone(FREQ, FREQ_DUR);
            }
            else
            {
               //idle state (no motion detected)
                led.setRGB(INTENSITY,0,0);
            }
           
            return (value);
       }
       catch (IOException ex)
       {
            return -1;
       }   
    }

    public long getMotionTime()
    {
    	return this.lastMotion; 
    }

    public SunspotPort getPort()
    {
        return this.port;
    }

    public String getDataAsString() {
        return String.valueOf(lastMotion);
    }

    public double getDataAsDouble() {
        return (double) lastMotion;
    }

    public int getDataAsInt() {
        return (int) lastMotion ;
    }

    public long getDataAsLong() {
        return lastMotion;
    }

}