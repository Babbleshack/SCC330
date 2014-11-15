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
import com.sun.spot.util.Utils;




public class ThermoMonitor extends Observable implements IThermoMonitor
{
    private SunspotPort port;
    private ITemperatureInput thermo;

    private final int THRESHOLD; 

    //define condition and callback
    private IConditionListener callback;
    private Condition hasGoneAboveThreshold;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;

    public ThermoMonitor(int threshold)
    {
        this.thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
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
        callback = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                ThermoMonitor.this.hasChanged();
                ThermoMonitor.this.notifyObservers((Object)new Double(ThermoMonitor.this.getCelsiusTemp()));
            }
        };
        //innitialise the checking condition
        hasGoneAboveThreshold = new Condition(thermo, callback, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(ThermoMonitor.this.getCelsiusTemp() >= THRESHOLD) {
                // Utils.sleep(SECOND*5);
                return true;
            }
            return false;
          }  
        };
        hasGoneAboveThreshold.start();    
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return SunspotPort.THERMO_PORT;
    }

    public double getCelsiusTemp() {
        try
        {
            return thermo.getCelsius();
        } catch (IOException ex) {
            System.err.println("Error getting Celcius " + ex);
        }
        return -9999;
    }

    public double getFahrenheitTemp()
    {
        try
        {
            return thermo.getFahrenheit();
        } catch (IOException ex) {
            System.err.println("Error getting Fahrenheit " + ex);
        }
        return -9999;
    }

    public String getDataAsString() {
        String data = "";
        try {
            data = String.valueOf(thermo.getCelsius());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public double getDataAsDouble() {
        double data = 0;        
        try {
            data = thermo.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public int getDataAsInt() {
        int data = 0;
        try {
            data = (int) thermo.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public long getDataAsLong() {
        long data = 0;
         try {
            data = (long) thermo.getCelsius();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return data;
    }

}
