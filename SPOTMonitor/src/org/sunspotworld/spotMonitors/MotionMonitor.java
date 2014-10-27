/**
 * Motion Moniter
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

//resources
import com.sun.spot.resources.Resources;
//These two packages need to be imported to allow you to create an instance
//of EDemoBoard and to access the I/O pins on the board
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.io.IScalarInput;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.SensorEvent;
import org.sunspotworld.spotMonitors.IMotionMonitor;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.Patterns.Observer;
import org.sunspotworld.Patterns.Observable;
import com.sun.spot.util.Utils;
import java.io.IOException;

public class MotionMonitor extends Observable implements IMotionMonitor
{
	private long lastMotion = 0;
    private EDemoBoard demo = EDemoBoard.getInstance(); //returns an instance of EDemoBoard through which the I/O pins can be accessed
    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private IToneGenerator toneGen = (IToneGenerator) Resources.lookup(IToneGenerator.class);
    private IScalarInput irSensor = irSensor = demo.getScalarInputs()[EDemoBoard.A0];
    private ITriColorLED led = leds.getLED(0);
   // private IToneGenerator toneGen;
    //private IScalarInput irSensor;
    private IConditionListener motionCheck;
    private Condition conditionMet;
    private SunspotPort port;


    private static final int PORTNUM = 140;
    private static final double FREQ = 50.0;
    private static final int FREQ_DUR = 100;
    private static final int INTENSITY = 100;
    private static final int HIGH = 1023;
    private static final int SECOND = 1000; 
    private static final int SAMPLE_RATE = SECOND;

    public MotionMonitor()  
    {
        System.out.println("ENTERED MOTION MONITOR CONSTRUCTOR");
        led.setRGB(250, 0, 0);
		led.setOn();
		demo = EDemoBoard.getInstance();
		this.irSensor = irSensor = demo.getScalarInputs()[EDemoBoard.A0];
        try {
            this.port = new SunspotPort(PORTNUM);
        } catch (PortOutOfRangeException pe) {
            System.out.println("Port number out of range: " + pe);
        }
        System.out.println("ABout to start preparing motion conditions");
        this.prepareConditions();
        try {
            System.out.println("Motion Monitor" + irSensor.getValue());
        } catch (Exception e) {
            System.err.println("IO EXCEPTION WHILE READING MOTION SENSORE " + e);
        }
    }

    
    /**
     * innitializes conditions and starts them
     */
    private void prepareConditions()
    {
        motionCheck = new IConditionListener()
        {
            public void conditionMet(SensorEvent evt, Condition condition)
            {
                MotionMonitor.this.hasChanged();
                MotionMonitor.this.notifyObservers((Object)new Long(System.currentTimeMillis()));
            }
        };
        //innitialise the checking condition
        conditionMet = new Condition(irSensor, motionCheck, SAMPLE_RATE)
        {
          public boolean isMet(SensorEvent evt)
          {
            System.out.println("CHECK MOTION");
            if(MotionMonitor.this.getSensorValue() == HIGH)
                return true;
            return false;
          }  
        };
        conditionMet.start();    
    }




    //IMPORTED COMMENT FROM GERALD
    //returns motion sensor value. When the sensors detects a motion, its
    //output pin goes high (decimal equivalent of 1023). When there is no motion
    //the output reverts to zero. When motion is detected, LED (0) lights up
    //green else red
    public int getSensorValue()
    {
       try
       {
            int value = irSensor.getValue();
            if(value == HIGH)
            {
               //motion detected
            	lastMotion = System.currentTimeMillis(); 
                led.setRGB(0, INTENSITY, 0);
         		// toneGen.startTone(FREQ, FREQ_DUR);
            }
            else
            {
               //idle state (no motion detected)
                led.setRGB(INTENSITY,0,0);
            }
           
            return (value);
       }
       catch (IOException ex)
       {
            return -1;
       }   
    }
    public long getMotionTime()
    {
    	return this.lastMotion; 
    }
    public SunspotPort getPort()
    {
        return this.port;
    }

}