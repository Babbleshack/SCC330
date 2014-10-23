package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.Patterns.Observable;
/**
 * Define a Light Monitor
 * @author Dominic Lindsay
 */
public class LightMonitor extends Observable implements ILightMonitor
{
    private SunspotPort port;
    private static final int portNum = 120;
    private ILightSensor lightSensor; //Light Sensor
    public LightMonitor()
    {
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    }
    public SunspotPort getPort() {
        return this.port;
    }
    public static int getStaticPort() {
        return portNum;
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
