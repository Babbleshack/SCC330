package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.homePatterns.Observable;
import com.sun.spot.util.Utils;


/**
 * Define a Light Monitor
 * @author Dominic Lindsay
 */
public class LightMonitor extends Observable implements ILightMonitor
{
    private SunspotPort port;
    private final ILightSensor lightSensor; //Light Sensor

    //threshold stuff
    private final int threshold;

    private IConditionListener thesholdCondition;
    private IConditionListener resetConditionListener;
    private Condition conditionHasBeenMet;
    private Condition waitForReset;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;

    public LightMonitor(int threshold)
    {
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
        try {
            this.port = new SunspotPort(SunspotPort.LIGHT_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        this.prepareConditions();
        this.threshold = threshold;
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
                LightMonitor.this.hasChanged();
                LightMonitor.this.notifyObservers();
                LightMonitor.this.conditionHasBeenMet.stop();
                LightMonitor.this.waitForReset.start();
                
            }
        };
        /**
         * resets Threshold Condition Listener
         */
        resetConditionListener = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
              LightMonitor.this.waitForReset.stop();
              LightMonitor.this.conditionHasBeenMet.start();
            }
        };
        /**
         * Sampling Condition used to monitor instrument data
         */
        conditionHasBeenMet = new Condition(lightSensor, thesholdCondition, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(LightMonitor.this.getLightIntensity() >= threshold) {
                return true;
            }
            return false;
          }  
        };
        /**
         * reset callback condition, used to restart sampling.
         */
        waitForReset = new Condition(lightSensor, resetConditionListener, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(LightMonitor.this.getLightIntensity() <= threshold) {
                return true;
            }
            return false;
          }  
        };
        conditionHasBeenMet.start();    
    }
    public void waitForThreshold(){
        this.conditionHasBeenMet.start();
    }
    
    public void waitForReset() {
        this.conditionHasBeenMet.stop();
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return SunspotPort.LIGHT_PORT;
    }

    public int getLightIntensity()
    {
        try
        {
           return lightSensor.getAverageValue();
        } catch (IOException ex) {
           System.err.println("Failed to get light sensor: " + ex);
        }
        return -9999;
    }
    
        public String getDataAsString() {
        String reading = "";
        try {
            reading = String.valueOf(lightSensor.getAverageValue());
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

    public double getDataAsDouble() {
        double reading =0;
        try {
            reading = lightSensor.getAverageValue();
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

    public int getDataAsInt() {
        int reading = 0;
        try {
            reading = lightSensor.getAverageValue();
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

    public long getDataAsLong() {
         long reading = 0;
        try {
            reading = lightSensor.getAverageValue();
            
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

}
