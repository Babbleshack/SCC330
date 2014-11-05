/**
 * Switch Monitor
 * Adam Cornforth
 */

package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import java.io.IOException;
import com.sun.spot.util.Utils;

import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.SwitchEvent;

public class SwitchMonitor implements ISwitchMonitor
{
    private SunspotPort port;

    public SwitchMonitor() {
        try {
            this.port = new SunspotPort(SunspotPort.SWITCH_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
    }

    /**
     * Adds a switch to this monitor that we might want to get the ID of later
     * @param sw  switch to add to monitor
     * @param id  ID that we might want to return later
     */
    public void addSwitch(ISwitch sw, String id) {
        sw.addTag("id="+id);
    }
    
    /**
     * returns ID of currently depressed button found from switch event
     * @param  evt Switch Event
     * @return String button ID
     */
    public String getPressedId(SwitchEvent evt) {
        return evt.getSwitch().getTagValue("id"); 
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return SunspotPort.SWITCH_PORT;
    }

}
