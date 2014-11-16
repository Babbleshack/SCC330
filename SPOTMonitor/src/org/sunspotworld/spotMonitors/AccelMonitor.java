package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.SensorEvent;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.homePatterns.Observable;
import java.io.IOException;
import com.sun.spot.util.Utils;

public class AccelMonitor extends Observable implements IAccelMonitor
{
    private SunspotPort port;
    private static final int portNum = 130;
    private IAccelerometer3D accelSensor; //Accelerometer Sensor
    //threshold stuff
    private static final double LOWER_THRESHOLD = 0.8;
    private static final double HIGHER_THRESHOLD = 1.2;
    private IConditionListener accelCheck;
    private Condition conditionMet;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE =SECOND/3;
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
                AccelMonitor.this.notifyObservers();
            }
        };
        //innitialise the checking condition
        conditionMet = new Condition(accelSensor, accelCheck, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
              double reading  = AccelMonitor.this.getDataAsDouble();
            return reading < LOWER_THRESHOLD || reading > HIGHER_THRESHOLD;
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
    public String getDataAsString() {
        String reading = "";
        try {
            reading = String.valueOf(accelSensor.getAccel());
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

    public double getDataAsDouble() {
        double reading =0;
        try {
            reading = accelSensor.getAccel();
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading;
    }

    public int getDataAsInt() {
        Double reading = new Double(0.0);
        try {
            reading = Double.valueOf(accelSensor.getAccel());
            
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading.intValue();
    }

    public long getDataAsLong() {
         Double reading = new Double(0);
        try {
            reading = Double.valueOf(accelSensor.getAccel());
        } catch (IOException ex) {
            System.err.println("Error reading Accel Data");
            ex.printStackTrace();
        }
        return reading.longValue();
    }

}