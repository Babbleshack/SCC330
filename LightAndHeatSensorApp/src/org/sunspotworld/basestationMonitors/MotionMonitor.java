package org.sunspotworld.basestationMonitors;

import com.sun.spot.resources.Resources;
import java.io.IOException;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.SunspotPort;

public class MotionMonitor implements IMotionMonitor 
{
    private SunspotPort port;

    public MotionMonitor()
    {
        try {
            this.port = new SunspotPort(SunspotPort.MOTION_PORT);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
    }

    public SunspotPort getPort() {
        return this.port; 
    }

}
