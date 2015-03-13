package org.sunspotworld.timers;

import org.sunspotworld.homePatterns.*;

public class CounterTimer extends Observable implements ITimer {
    private long _time;
    private int _counter;
    private int _threshold;
    private boolean _running;
    public final static long DEF_TIME = 1000;
    public final static int DEF_THRESH = 1;
    public CounterTimer(long time, int threshold) {
        _time = time;
        _threshold = threshold;
    }
    public void run() {
        _running = true;
        long timout = System.currentTimeMillis() + _time;
        while(System.currentTimeMillis() <= _time && _counter < _threshold);
        if(_counter >= _threshold) {
            resetCounter();
            notifyObservers();
        } else {
            resetCounter();
        }
        _running = false;
    }
    public void setTimer(long time) { 
        _time = time;
    }
    public long getTimer() { 
        return _time;
    }
    public void incrementCounter() {
        _counter++;
    }
    public void setCounter(int value) {
        _counter = value;
    }
    public int getCounter() { 
        return _counter;
    }
    public void resetCounter() {
        _counter = 0;
    }
    public boolean isRunning(){
        return _running;
    }
}
