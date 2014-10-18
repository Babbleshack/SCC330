/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
/**
 * Define a Light Monitor 
 * @author babbleshack
 */
public class LightMonitor implements ILightMonitor
{
    private SunspotPort port; 
    private ILightSensor lightSensor; //Light Sensor    
    
    public LightMonitor()
    {
        try {
            this.port = new SunspotPort(120);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    }
    
    public SunspotPort getPort() {
        return this.port; 
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
    
}
