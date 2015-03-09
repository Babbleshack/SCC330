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
import org.sunspotworld.cupStates.FullState;
import org.sunspotworld.cupStates.SmartCupState;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.sensors.AxisSensor;
import operators.IOperator;
import operators.NullOperator;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;

public class WaterMonitor extends TaskObservable implements 
        ISwitchListener, IMonitor
{
    private double fillLevelPercentage;
    private SmartCupState cupState;
    private final AxisSensor axisSensor;
    private ISwitch sw1 = (ISwitch) Resources.lookup(ISwitch.class,"SW1");
    private static final long SAMPLE_RATE = 500;
    private static final long SAMPLE_FREQ = 10;
    private static final double MAX_FILL_PERCENTAGE = 100;
    private final String _TYPE = "WATER_MONITOR";
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
	//get average cup tilt.
        double averageReading = 0;
        for(int i=0;i<SAMPLE_FREQ;i++)
        {
            averageReading += 
                    Math.abs(Math.floor(Math.toDegrees(
                            axisSensor.getAxisData().getY()
                    )));
        }
        averageReading = averageReading/SAMPLE_FREQ;
	//pour container
        double currentWaterLevel = this.fillLevelPercentage;
        this.pour(averageReading);
        //if water level changes then notify Observers 
        if(currentWaterLevel != this.fillLevelPercentage)
        { 
            this.hasChanged();
            this.notifyObservers();
        }
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

    public void switchPressed(SwitchEvent evt) {
	    /* check if monitor is running */
        if(evt.getSwitch() == sw1)
        {
            System.out.println("REFILL SWWITCH PRESSED");
            if(getTiltAngle() < 75)
                return; // do nothing if reading if tilt is below 75
            this.fillLevelPercentage = this.MAX_FILL_PERCENTAGE;
            this.setState(new FullState());
            this.hasChanged();
            this.notifyObservers();
        }
    }
    private int getTiltAngle()
    {
        return (int)Math.abs(Math.floor(Math.toDegrees(
                            axisSensor.getAxisData().getY()
                    )));
    }

    public void switchReleased(SwitchEvent evt) {
        System.out.println("Switch released");
    }

	public SensorData getSensorReading(){
		return new SensorData(fillLevelPercentage);
	}

	public void startMonitor() {
		this.start();
	}
	public void stopMonitor() {
		this.stop();
	}
	public boolean getStatus() {
		return this.isActive();
	}
	public String getType(){
		return _TYPE;
	}
	public void setVariable(int data) {
		return;
	}
	public void addMonitorObserver(TaskObserver to) {
		this.addObserver(to);
	}
	//Direction stuff
	public void setDirection(IOperator direction) {
		return;
	}
	public IOperator getDirection() {
		return new NullOperator();
	}
}
