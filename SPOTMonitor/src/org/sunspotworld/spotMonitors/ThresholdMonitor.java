/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import monitorStates.IThresholdMonitorState;
import org.sunspotword.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.sensors.ISensor;


public class ThresholdMonitor extends TaskObservable implements IThresholdMonitor
{
    private static long SAMPLE_RATE = 1000;
    
    private double threshold = 0;
    ISensor sensor;
    IThresholdMonitorState monitorState;
    public ThresholdMonitor(double threshold, IThresholdMonitorState state) {
        super(SAMPLE_RATE);
        this.monitorState = state;
    }
    public void doTask() throws Exception {
        this.checkSensorReading();
    }
    public double getThreshold() {
        return this.threshold;
    }
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
    public SensorData getSensorReading() {
        return this.sensor.getData();
    }
    public void checkSensorReading() {
        monitorState.checkThresholdCondition(this);
    }
}
