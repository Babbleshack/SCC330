/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;


public class ThermoService implements TaskObserver, IService {
    IMonitor monitor;
    //add radio and methods
    public ThermoService(IMonitor monitor) {
        this.monitor = monitor;
    }
    public void startService() {
        monitor.startMonitor();
    }
    public void stopService() {
        monitor.stopMonitor();
    }
    public void update(TaskObservable o, Object arg) {
        //send data across radio connection.
    }
    public void update(TaskObservable o) {
        //send data across radio connection.
    }
}
