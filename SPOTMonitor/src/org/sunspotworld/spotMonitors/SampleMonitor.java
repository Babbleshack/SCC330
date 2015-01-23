/**
 * Simple Sample Monitor, takes a sample rate, notifies observer with ref 
 * to itself every time 'sampleRate'.
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import org.sunspotword.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.sensors.ISensor;
public class SampleMonitor extends TaskObservable implements IMonitor {
    private long sampleRate = 0;
    private final ISensor sensor;
    public SampleMonitor(long sampleRate, ISensor sensor) {
        super(sampleRate);
        this.sampleRate = sampleRate;
        this.sensor = sensor;
    }
    public void doTask() throws Exception {
        this.notifyObservers(this.getSensorReading());
    }
    public long getSampleRate() {
        return this.sampleRate;
    }
    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
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
}
