package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.SensorEvent;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.Patterns.Observable;
import java.io.IOException;

public class AccelMonitor extends Observable implements IAccelMonitor
{
    private SunspotPort port;
    private static final int portNum = 130;
    private IAccelerometer3D accelSensor; //Accelerometer Sensor
    //threshold stuff
    private static final double MIN_G = 0.9;
    private static final double MAX_G = 1.1;
    private IConditionListener accelCheck;
    private Condition conditionMet;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE =SECOND;
    public AccelMonitor()
    {
        this.accelSensor = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
        try {
            this.port = new SunspotPort(portNum);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        prepareConditions();
    }
    /**
     * innitializes conditions and starts them
     */
    private void prepareConditions()
    {
        accelCheck = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                AccelMonitor.this.hasChanged();
                AccelMonitor.this.notifyObservers((Object)new Double(AccelMonitor.this.getAccel()));
            }
        };
        //innitialise the checking condition
        conditionMet = new Condition(accelSensor, accelCheck, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            System.out.println("Accelerometer");
            if(AccelMonitor.this.getAccel() < MIN_G || AccelMonitor.this.getAccel() > MAX_G)
                return true;
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