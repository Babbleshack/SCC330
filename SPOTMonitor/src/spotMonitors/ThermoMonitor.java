/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotMonitors;

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
    private ITemperatureInput thermo;

    public ThermoMonitor()
    {
        try {
            this.port = new SunspotPort(110);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }

        this.thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    }

    public SunspotPort getPort() {
        return this.port; 
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
