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
        long timeout = System.currentTimeMillis() + _time;
        while(_counter <= _threshold || System.currentTimeMillis() <= timeout);
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
        System.out.println("Increment Counter");
        _counter++;
    }
    public void setCounter(int value) {
        _threshold = value;
        System.out.println("Threshold is: \t" + _threshold + " Counter is:\t" + _counter + " timer is:\t" + _time);
    }
    public int getCounter() { 
        return _counter;
    }
    public void resetCounter() {
        System.out.println("Resetting Counter");
        _counter = 0;
    }
    public boolean isRunning(){
        return _running;
    }
}
