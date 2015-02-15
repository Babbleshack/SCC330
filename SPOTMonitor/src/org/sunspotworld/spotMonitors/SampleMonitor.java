/**
 * Simple Sample Monitor, takes a sample rate, notifies observer with ref 
 * to itself every time 'sampleRate'.
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;
public class SampleMonitor extends TaskObservable implements IMonitor {
    private long sampleRate = 0;
    private final ISensor sensor;
    private static final String _TYPE = "SAMPLE";
    private static final int MULTI = 1000;
    public static final long DEFAULT_SAMPLE_RATE = 1000;
    public SampleMonitor(ISensor sensor) {
        super(DEFAULT_SAMPLE_RATE);
        this.sampleRate = sampleRate;
        this.sensor = sensor;
    }
    public void doTask() throws Exception {
        this.notifyObservers(this.getSensorReading());
        System.out.println("doing task");
    }
    public long getSampleRate() {
        return this.sampleRate;
    }
    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
        this.setScheduledTime(sampleRate);
    }
    public SensorData getSensorReading() {
        return this.sensor.getData();
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
    public String getType() {
        return this._TYPE;
    }

    public void setVariable(int data) {
        this.setSampleRate(data * 1000);
    }

    public void addMonitorObserver(TaskObserver to) {
        this.addObserver(to);
    }
}
