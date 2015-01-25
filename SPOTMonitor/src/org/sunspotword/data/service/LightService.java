/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;
import java.io.IOException;
import org.sunspotworld.homePatterns.TaskObservable;
import org.sunspotworld.homePatterns.TaskObserver;
import org.sunspotworld.sensors.ISensor;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
public class LightService implements IService, TaskObserver {
    private IMonitor monitor;
    private final int _serviceId;
    private ISendingRadio sRadio;
    public LightService(IMonitor monitor, int serviceId) {
        try {
            sRadio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.LIGHT_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        sRadio.sendLight(((ISensor)o).getData().getDataAsInt());
    }

    public void update(TaskObservable o) {
        sRadio.sendLight(((ISensor)o).getData().getDataAsInt());
    }
    public int getServiceId() {
        return this._serviceId;
    }
    public IMonitor getMonitor(){
        return this.monitor;
    }
}
