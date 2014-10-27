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
    private final ILightSensor lightSensor; //Light Sensor
    //threshold stuff
    private final int threshold;
    private IConditionListener lightCheck;
    private Condition conditionMet;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;
    public LightMonitor(int threshold)
    {
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        this.prepareConditions();
        this.threshold = threshold;
    }
    /**
     * innitializes conditions and starts them
     */
    private void prepareConditions()
    {
        lightCheck = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                LightMonitor.this.hasChanged();
                LightMonitor.this.notifyObservers((Object)new Double(LightMonitor.this.getLightIntensity()));
            }
        };
        //innitialise the checking condition
        conditionMet = new Condition(lightSensor, lightCheck, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            if(LightMonitor.this.getLightIntensity() >= threshold) {
                Utils.sleep(SECOND*5);
                return true;
            }
            return false;
          }  
        };
        conditionMet.start();    
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
