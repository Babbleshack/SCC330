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

/**
 *
 * @author babbleshack
 */
public class ThermoMonitor extends Observable implements IThermoMonitor
{
    private SunspotPort port;
    private ITemperatureInput thermoSensor;

    private final int THRESHOLD; 
    private final int SAMPLE_RATE;
    private final int NO_THRESHOLD = 0;
    private static final int SECOND = 1000; 
    private static final int FIVE_MINUTES = 5 * (60 * SECOND);

    //define condition and callback
    private IConditionListener thesholdCondition;
    private Condition conditionHasBeenMet;
    private Condition waitForReset;

    public ThermoMonitor(int threshold)
    {
        this.thermoSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
        try {
            this.port = new SunspotPort(SunspotPort.THERMO_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        /**
         * If no threshold has been set then
         * set threshold to 0, and sample every 5 
         * minutes,
         * otherwise sample every second and report when threshold has been
         * met.
         */
        if(threshold <= NO_THRESHOLD)
        {
            this.THRESHOLD = NO_THRESHOLD;
            this.SAMPLE_RATE = FIVE_MINUTES;
        } else{
            this.THRESHOLD = threshold;
            this.SAMPLE_RATE = SECOND;
        } 
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
                ThermoMonitor monitor = ThermoMonitor.this;
                monitor.hasChanged();
                monitor.notifyObservers();
                if(monitor.THRESHOLD == monitor.NO_THRESHOLD)
                {
                    return;
                }
                monitor.conditionHasBeenMet.stop();
                monitor.waitForReset.start();

            }
        };
        /**
         * Sampling Condition used to monitor instrument data
         */
        conditionHasBeenMet = new Condition(thermoSensor, thesholdCondition, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
              System.out.println("Thermo Reading: " + ThermoMonitor.this.getDataAsDouble());
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
