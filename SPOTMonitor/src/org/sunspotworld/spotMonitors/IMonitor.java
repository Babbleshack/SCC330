/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

import operators.IOperator;
import org.sunspotworld.data.SensorData;
import org.sunspotworld.homePatterns.TaskObserver;

/**
 *
 * @author babbleshack
 */
public interface IMonitor 
{/*
    public String getDataAsString();
    public double getDataAsDouble();
    public int getDataAsInt();
    public long getDataAsLong();*/
    //new stuff
    public SensorData getSensorReading();
    public void startMonitor();
    public void stopMonitor();
    public boolean getStatus();
    public String getType();
    /**
     * used for setting the variable of the monitor 
     * i.e. threshold or sample rate
     */
    public void setVariable(int data);
    public void addMonitorObserver(TaskObserver to);
    //Direction stuff
    public void setDirection(IOperator direction);
    public IOperator getDirection();
}
