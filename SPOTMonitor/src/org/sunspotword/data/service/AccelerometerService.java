/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;


public class AccelerometerService implements IService, TaskObserver {
    IMonitor monitor;
    public AccelerometerService(IMonitor monitor) {
        this.monitor = monitor;
    }
    public void startService() {
        this.monitor.startMonitor();
    }
    public void stopService() {
        this.monitor.stopMonitor();
    }

    public void update(TaskObservable o, Object arg) {
        //send data on radio
    }

    public void update(TaskObservable o) {
        //sends data on radio
    }
}
