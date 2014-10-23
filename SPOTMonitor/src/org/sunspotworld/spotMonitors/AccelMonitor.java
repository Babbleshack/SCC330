package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import java.io.IOException;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;

public class AccelMonitor implements IAccelMonitor
{
    private SunspotPort port;
    private static final int portNum = 130;
    private IAccelerometer3D accelSensor; //Accelerometer Sensor

    public AccelMonitor()
    {
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }

        this.accelSensor = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    }

    public SunspotPort getPort() {
        return this.port;
    }

    public static int getStaticPort() {
        return portNum;
    }

    public double getAccel()
    {
        try
        {
           return accelSensor.getAccel();
        } catch (IOException ex) {
           System.err.println("Failed to get accel sensor: " + ex);
        }
        return -9999;
    }

}