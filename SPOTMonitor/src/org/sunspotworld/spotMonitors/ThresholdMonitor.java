/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.monitorStates.IThresholdMonitorState;
import org.sunspotword.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.sensors.ISensor;


public class ThresholdMonitor extends TaskObservable implements
        IThresholdMonitor, IMonitor
{
    public static final long SAMPLE_RATE = 1000;
    private double threshold = 0;
    private final ISensor sensor;
    private final IThresholdMonitorState monitorState;
    private static final String _TYPE = "THRESHOLD";
    public ThresholdMonitor(ISensor sensor, 
            IThresholdMonitorState state) {
        super(SAMPLE_RATE);
        this.threshold = SAMPLE_RATE;
        this.monitorState = state;
        this.sensor = sensor;
    }
    public void doTask() throws Exception {
        this.getSensorReading();
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
    public void startMonitor(){
        this.start();
    }
    public void stopMonitor() {
        this.stop();
    }
    public boolean getStatus() {
        return this.isActive();
    }
    public String getType() {
        return this._TYPE;
    }
    public void setVariable(int data) {
        this.threshold = data;
    }
}
