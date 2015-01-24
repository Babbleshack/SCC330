/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;


public class ThermoService implements TaskObserver, IService {
    private IMonitor monitor;
    private int _serviceId;
    //add radio and methods
    public ThermoService(IMonitor monitor, int serviceId) {
        this.monitor = monitor;
        this._serviceId = serviceId;
    }
    public void startService() {
        monitor.startMonitor();
    }
    public void stopService() {
        monitor.stopMonitor();
    }
    public boolean isScheduled() {
        return this.isScheduled();
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
    }
    public void update(TaskObservable o) {
        //send data across radio connection.
    }
}
