/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.sensorboard.peripheral.LightSensor;
import java.io.IOException;
/**
 *
 * @author babbleshack
 */
public class LightMonitor implements ILightMonitor
{
    LightSensor lightSensor;
    public LightMonitor()
    {
        lightSensor = (LightSensor) Resources.lookup(ILightMonitor.class);
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
