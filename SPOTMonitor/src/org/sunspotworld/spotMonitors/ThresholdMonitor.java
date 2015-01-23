/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.monitorStates.IThresholdMonitorState;
import org.sunspotword.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.sensors.ISensor;


public class ThresholdMonitor extends TaskObservable implements IThresholdMonitor
{
    public static final long SAMPLE_RATE = 1000;
    private double threshold = 0;
    private final ISensor sensor;
    private final IThresholdMonitorState monitorState;
    public ThresholdMonitor(double threshold, ISensor sensor, 
            IThresholdMonitorState state) {
        super(SAMPLE_RATE);
        this.threshold = SAMPLE_RATE;
        this.monitorState = state;
        this.sensor = sensor;
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
