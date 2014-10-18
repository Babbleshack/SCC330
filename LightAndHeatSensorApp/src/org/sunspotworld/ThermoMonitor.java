/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import java.io.IOException;

public class ThermoMonitor implements IThermoMonitor 
{
    private SunspotPort port;

    public ThermoMonitor()
    {
        try {
            this.port = new SunspotPort(110);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
    }

    public SunspotPort getPort() {
        return this.port; 
    }

}
