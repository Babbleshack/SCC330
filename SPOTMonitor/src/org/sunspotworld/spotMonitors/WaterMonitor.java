/**
 * SmartCup Application used to remotely measure the contents
 * of a cup.
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.SwitchEvent;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.states.FullState;
import org.sunspotworld.states.SmartCupState;
import sensors.AxisSensor;
public class WaterMonitor extends TaskObservable implements IMonitor,
        ISwitchListener
{
    private double fillLevelPercentage;
    private SmartCupState cupState;
    private final AxisSensor axisSensor;
    private ISwitch sw1 = (ISwitch) Resources.lookup(ISwitch.class,"SW1");
    private static final long SAMPLE_RATE = 500;
    private static final long SAMPLE_FREQ = 10;
    private static final double MAX_FILL_PERCENTAGE = 100;
    public WaterMonitor()
    {
        super(SAMPLE_RATE);
        axisSensor = new AxisSensor();
        cupState = new FullState(); //set state of cup to full
        fillLevelPercentage = MAX_FILL_PERCENTAGE;
        this.sw1.addISwitchListener(this);
    }
    /**
     * Repeatedly executed every sample rate.
     * Takes average over SAMPLE_FREQ
     * passing average to pour method.
     * @throws Exception 
     */
    public void doTask() throws Exception {
        double averageReading = 0;
        for(int i=0;i<SAMPLE_FREQ;i++)
        {
            averageReading += 
                    Math.abs(Math.floor(Math.toDegrees(
                            axisSensor.getAxisData().getY()
                    )));
        }
        //if water level changes then notify Observers 
        double currentWaterLevel = this.fillLevelPercentage;
        System.out.println("==================================================");
        averageReading = averageReading/SAMPLE_FREQ;
        System.out.println("ANGLE [" + averageReading + "]");
        System.out.println("BEFORE POURING [" + this.fillLevelPercentage + "]");
        this.pour(averageReading);
        if(currentWaterLevel != this.fillLevelPercentage)
        { 
            this.hasChanged();
            this.notifyObservers();
        }
        System.out.println("After POURING [" + this.fillLevelPercentage + "]");
    }
    /**
     * call locally to determine cup fill level.
     * defers execution to SmartCupState Object.
     * @param tiltAngle angle of cup.
     */
    private void pour(double tiltAngle)
    {
        this.fillLevelPercentage = 
                cupState.pour(this, tiltAngle, this.fillLevelPercentage);
    }
    /**
     * sets the fill level in percentage
     * @param fill 
     */
    public void setFillLevel(double fill)
    {
        this.fillLevelPercentage = fill;
    }
    /**
     * Called by associated classes to change cup state.
     * @param state 
     */
    public void setState(SmartCupState state)
    {
        this.cupState = state;
    }
    public String getDataAsString() {
        return String.valueOf(this.fillLevelPercentage);
    }
    public double getDataAsDouble() {
        return this.fillLevelPercentage;
    }
    public int getDataAsInt() {
        return (int) this.fillLevelPercentage;
    }
    public long getDataAsLong() {
        return (long) this.fillLevelPercentage;
    }

   /* public SunspotPort getPort() {
        throw new java.lang.UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    public void switchPressed(SwitchEvent evt) {
        if(evt.getSwitch() == sw1)
        {
            System.out.println("REFILL SWWITCH PRESSED");
            this.fillLevelPercentage = 100;
            this.hasChanged();
            this.notifyObservers();
        }
    }

    public void switchReleased(SwitchEvent evt) {
        System.out.println("Switch released");
    }

}
