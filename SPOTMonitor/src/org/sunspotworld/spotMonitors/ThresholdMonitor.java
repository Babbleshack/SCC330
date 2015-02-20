/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

import operators.IOperator;
import org.sunspotworld.monitorStates.IThresholdMonitorState;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;


public class ThresholdMonitor extends TaskObservable implements
        IThresholdMonitor, IMonitor
{
    public static final long SAMPLE_RATE = 2 * 1000;
    private double _threshold;
    private final ISensor _sensor;
    private final IThresholdMonitorState _monitorState;
    private static final String _TYPE = "THRESHOLD";
    private boolean _hasBeenMet = false;
    private IOperator _direction;
    public ThresholdMonitor(ISensor sensor, 
            IThresholdMonitorState state) {
        super(SAMPLE_RATE);
        this._threshold = 0;
        this._monitorState = state;
        this._sensor = sensor;
    }
    public void doTask() throws Exception {
        this._monitorState.checkThresholdCondition(this);
    }
    public double getThreshold() {
        return this._threshold;
    }
    public void setThreshold(double threshold) {
        this._threshold = threshold;
    }
    public SensorData getSensorReading() {
        return this._sensor.getData();
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
        this._threshold = (double)data;
    }

    public void addMonitorObserver(TaskObserver to) {
        this.addObserver(to);
    }

    public boolean getHasBeenMet() {
        return _hasBeenMet;
    }

    public void setHasBeenMet(boolean hasBeenMet) {
        _hasBeenMet = hasBeenMet;
    }
    public void setDirection(IOperator direction) {
        _direction = direction;
    }
    public IOperator getDirection(){
        return _direction;
    }

    public SensorData getThresholdAsSensorReading() {
        System.out.println("THRESHOLD IS " + this._threshold);
        return new SensorData(this._threshold);
    }
}
