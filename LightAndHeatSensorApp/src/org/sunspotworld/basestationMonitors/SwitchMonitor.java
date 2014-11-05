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

public class SwitchMonitor implements ISwitchMonitor 
{
    private SunspotPort port;

    public SwitchMonitor()
    {
        try {
            this.port = new SunspotPort(SunspotPort.SWITCH_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
    }

    public SunspotPort getPort() {
        return this.port; 
    }

}
