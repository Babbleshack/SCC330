/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;
import java.io.IOException;

public class ThermoMonitor implements IThermoMonitor 
{
    ITemperatureInput thermo;
    public ThermoMonitor()
    {
        thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
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
