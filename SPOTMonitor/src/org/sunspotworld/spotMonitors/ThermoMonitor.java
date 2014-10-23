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
import org.sunspotworld.Patterns.Observable;
import java.io.IOException;import com.sun.spot.resources.transducers.SensorEvent;



public class ThermoMonitor extends Observable implements IThermoMonitor
{
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;
    private SunspotPort port;
    private static final int portNum = 110;
    private ITemperatureInput thermo;
    private final int threshold; 
    //define condition and callback
    IConditionListener thermoCheck;
    Condition hitThreshold;
    public ThermoMonitor(int threshold)
    {
        this.thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        this.threshold = threshold;
        this.prepareConditions();
    }
    /**
     * innitializes conditions and starts them
     */
    private void prepareConditions()
    {
        thermoCheck = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                System.out.println("Thermo Condition Met");
                System.out.println("Temperature: " + ThermoMonitor.this.getCelsiusTemp());
                ThermoMonitor.this.notifyObservers((Object)new Double(ThermoMonitor.this.getCelsiusTemp()));
            }
        };
        //innitialise the checking condition
        hitThreshold = new Condition(thermo, thermoCheck, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(ThermoMonitor.this.getCelsiusTemp() >= threshold)
                return true;
            return false;
          }  
        };
        hitThreshold.start();    
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return portNum;
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

}
