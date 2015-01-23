/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import org.sunspotword.data.SensorData;
import org.sunspotworld.spotMonitors.IMonitor;
public class LightService implements IService {
    IMonitor monitor;
    public LightService(IMonitor monitor) {
        this.monitor = monitor;
    }
    public void startService() {
        this.monitor.startMonitor();
    }
    public void stopService() {
        this.monitor.stopMonitor();
    }
}
