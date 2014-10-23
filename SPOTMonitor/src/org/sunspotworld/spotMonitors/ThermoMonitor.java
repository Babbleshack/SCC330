/**
 * Thermonitor Monitor
 * Dominic Lindsay
 */

package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;

public class ThermoMonitor implements IThermoMonitor
{
    private SunspotPort port;
    private static final int portNum = 110;
    private ITemperatureInput thermo;

    public ThermoMonitor()
    {
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }

        this.thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
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
