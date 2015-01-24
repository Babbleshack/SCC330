/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.spotMonitors.IMonitor;
public class LightService implements IService, TaskObserver {
    private IMonitor monitor;
    private final int _serviceId;
    public LightService(IMonitor monitor, int serviceId) {
        this.monitor = monitor;
        this._serviceId = serviceId;
    }
    public void startService() {
        this.monitor.startMonitor();
    }
    public void stopService() {
        this.monitor.stopMonitor();
    }
    public boolean isScheduled() {
        return this.isScheduled();
    }

    public void update(TaskObservable o, Object arg) {
        //send data on radio
    }

    public void update(TaskObservable o) {
        //sends data on radio
    }
    public int getServiceId() {
        return this._serviceId;
    }
}
