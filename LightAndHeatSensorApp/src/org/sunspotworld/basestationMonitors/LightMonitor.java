/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.basestationMonitors;

import com.sun.spot.resources.Resources;
import java.io.IOException;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.SunspotPort;

/**
 * Define a Light Monitor 
 * @author babbleshack
 */
public class LightMonitor implements ILightMonitor
{
    private SunspotPort port; 
    
    public LightMonitor()
    {
        try {
            this.port = new SunspotPort(SunspotPort.LIGHT_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
    }
    
    public SunspotPort getPort() {
        return this.port; 
    }    
}
