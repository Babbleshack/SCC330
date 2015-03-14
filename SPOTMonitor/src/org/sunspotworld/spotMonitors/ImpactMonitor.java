/**
 * Impace Sensor Monitr, Can be configured to count N impacts during an 
 * arbitery ammount of time, counted in seconds.
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import operators.IOperator;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.*;
import org.sunspotworld.sensors.ISensor;
import org.sunspotworld.timers.*;
public class ImpactMonitor extends TaskObservable implements IMonitor, Observer{
    private long sampleRate = 0;
    private final ISensor sensor;
    private ITimer _counter;
    private boolean _running;
    private static final String _TYPE = "SAMPLE";
    private static final int MULTI = 1000;
    public static final long DEFAULT_SAMPLE_RATE = 1000;
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    private int _flag = FALSE;
    private boolean _wasHigh;

    private IOperator _direction;
    public ImpactMonitor(ISensor sensor) {
        super(DEFAULT_SAMPLE_RATE);
        this.sampleRate = sampleRate;
        this.sensor = sensor;
        _counter = new CounterTimer(CounterTimer.DEF_TIME, CounterTimer.DEF_THRESH);
        ((Observable)_counter).addObserver(this);
        _wasHigh = false;
    }
    public void doTask() throws Exception {
        while(_running){
            if(sensor.getData().getDataAsInt() == FALSE) {
                _wasHigh = false;
                continue;// no impact was detected
            }
            if(!_wasHigh){
                System.out.println("Impact");
                ((CounterTimer)_counter).incrementCounter();
                _wasHigh = true;
                System.out.println("Counter After Impact \t " + ((CounterTimer)_counter).getCounter());
            }
        }
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
        return new SensorData(this._flag);
    }

    public void startMonitor() {
        _running = true;
        this.start();
        _counter.run();
    }

    public void stopMonitor() {
        _running = false;
        this.stop();
    }
    public boolean getStatus() {
        return this.isActive();
    }
    public String getType() {
        return this._TYPE;
    }

    public void setVariable(int data) {
        ((CounterTimer)_counter).setTimer((long)1000*data);
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
    private int _flipSample() {
        System.out.println("Fliping.. " + _flag);
        return _flag ^= TRUE;
        //return _flag ^ TRUE; 
    }
    public void update(Observable o, Object arg) {
        _flipSample();
        notifyObservers();
        System.out.println("Impact Mon is Notifying its Observers");
        _counter.run();
    }
    public void update(Observable o) {
        notifyObservers(new SensorData(_flipSample()));
        System.out.println("Impact Mon is Notifying its Observers");
        _counter.run();
    }
    public void setCounter(int counter) {
        ((CounterTimer)_counter).setCounter(counter);
    }       
}
