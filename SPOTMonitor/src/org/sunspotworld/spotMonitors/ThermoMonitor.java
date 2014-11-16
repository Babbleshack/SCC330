/**
 * Thermonitor Monitor
 * Dominic Lindsay
 */

package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ITemperatureInput;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.homePatterns.Observable;
import java.io.IOException;
import com.sun.spot.resources.transducers.SensorEvent;




public class ThermoMonitor extends Observable implements IThermoMonitor
{
    private SunspotPort port;
    private ITemperatureInput thermoSensor;

    private final int THRESHOLD; 

    //define condition and callback
    private IConditionListener thesholdCondition;
    private IConditionListener restartCondition;
    private Condition conditionHasBeenMet;
    private Condition waitForReset;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;

    public ThermoMonitor(int threshold)
    {
        this.thermoSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
        try {
            this.port = new SunspotPort(SunspotPort.THERMO_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        this.THRESHOLD = threshold;
        this.prepareConditions();
    }
    
    /**
     * innitializes conditions and starts them
     * ThermoCheck Condition Listener Fired by the "isMet"
     * Condition.
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
                ThermoMonitor.this.hasChanged();
                ThermoMonitor.this.notifyObservers();
                ThermoMonitor.this.conditionHasBeenMet.stop();
                ThermoMonitor.this.waitForReset.start();
                
            }
        };
        /**
         * restarts monitor condition
         */
        restartCondition = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                ThermoMonitor.this.waitForReset.stop();    
                ThermoMonitor.this.conditionHasBeenMet.start();
            }
        };
        /**
         * Sampling Condition used to monitor instrument data
         */
        conditionHasBeenMet = new Condition(thermoSensor, thesholdCondition, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(ThermoMonitor.this.getDataAsDouble() >= THRESHOLD) {
                return true;
            }
            return false;
          }  
        };
        /**
         * reset callback, used to restart sampling.
         */
        waitForReset = new Condition(thermoSensor, thesholdCondition, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(ThermoMonitor.this.getDataAsDouble() <= THRESHOLD) {
                return true;
            }
            return false;
          }  
        };
        conditionHasBeenMet.start();       
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return SunspotPort.THERMO_PORT;
    }
    public String getDataAsString() {
        String data = "";
        try {
            data = String.valueOf(thermoSensor.getCelsius());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public double getDataAsDouble() {
        double data = 0;        
        try {
            data = thermoSensor.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public int getDataAsInt() {
        int data = 0;
        try {
            data = (int) thermoSensor.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public long getDataAsLong() {
        long data = 0;
         try {
            data = (long) thermoSensor.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return data;
    }

}
