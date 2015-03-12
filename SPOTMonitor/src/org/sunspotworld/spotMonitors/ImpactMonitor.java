/**
 * Impace Sensor Monitr, Can be configured to count N impacts during an 
 * arbitery ammount of time, counted in seconds.
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import operators.IOperator;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;
public class ImpactMonitor extends TaskObservable implements IMonitor {
    private long sampleRate = 0;
    private final ISensor sensor;
    private static final String _TYPE = "SAMPLE";
    private static final int MULTI = 1000;
    public static final long DEFAULT_SAMPLE_RATE = 1000;
    private IOperator _direction;
    public ImpactMonitor(ISensor sensor) {
        super(DEFAULT_SAMPLE_RATE);
        this.sampleRate = sampleRate;
        this.sensor = sensor;
    }
    public void doTask() throws Exception {
	   /*
	     * Sample impact sensor
	     * when first impact occurs,
	     * if timer thread has been started,
	     * 	increment impacts.
	     * else
	     * 	start timer thread.
	     *
	     * if number of impacts is > thresh
	     *  call update
	     *  set impacts to 0, and stop thread.
	     * else
	     * 	continue
	     *
	     * start timer thread, with impacts = 1
	    */
    }
    public long getSampleRate() {
        return this.sampleRate;
    }
    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
        System.out.println("Setting Sample rate " + sampleRate);
        this.setPeriod(sampleRate);
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
        public void setDirection(IOperator direction) {
        _direction = direction;
    }
    public IOperator getDirection(){
        return _direction;
    }
}
