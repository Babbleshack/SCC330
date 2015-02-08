/**
 * A service for encapsulating the barometer
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;


public class BarometerService implements IService, TaskObserver {
    private final IMonitor _monitor;
    private final int _serviceId; 
    //RADIO
    public BarometerService(final IMonitor monitor, final int serviceId) {
        _monitor = monitor;
        _serviceId = serviceId;
    }

    public void startService() {
        if(_monitor.getStatus())
            return;
        _monitor.startMonitor();
    }

    public void stopService() {
        if(!_monitor.getStatus())
            return;
        _monitor.stopMonitor();
    }

    public boolean isScheduled() {
        return _monitor.getStatus();
    }

    public int getServiceId() {
        return _serviceId;
    }

    public IMonitor getMonitor() {
        return _monitor;
    }

    public void setData(int data) {
        _monitor.setVariable(data);
    }

    public void update(TaskObservable o, Object arg) {
        //pass data on radio
    }

    public void update(TaskObservable o) {
        //pass data on radio
    }
    
    
}
