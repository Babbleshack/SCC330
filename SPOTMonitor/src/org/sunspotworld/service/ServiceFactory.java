/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import org.sunspotworld.spotMonitors.IMonitor;

public class ServiceFactory {
    public static IService createThermoService(IMonitor monitor) {
        if(monitor.getType().equals("SAMPLE"))
            return new ThermoService(monitor, IService.THERMO_SAMPLE);
        else
            return new ThermoService(monitor, IService.THERMO_THRESH);
    }
    public static IService createLightService(IMonitor monitor) {
        if(monitor.getType().equals("SAMPLE"))
            return new LightService(monitor, IService.LIGHT_SAMPLE);
        else
            return new LightService(monitor, IService.LIGHT_THRESH); 
    }
    
    public static IService createAccellerometerService(IMonitor monitor) {
        if(monitor.getType().equals("SAMPLE"))
            return new AccelerometerService(monitor, IService.ACCEL_SAMPLE);
        else
            return new AccelerometerService(monitor, IService.ACCEL_THRESH);
    }
    
    public static IService createBarometerService(IMonitor monitor) {
        if(monitor.getType().equals("SAMPLE"))
            return new BarometerService(monitor, IService.BAROMETER_SAMPLE);
        else
            return new BarometerService(monitor, IService.BAROMETER_THRESH);
    }
    
    public static IService createTowerService() {
        return new TowerService(IService.PING);
    }
    
    public static IService createZoneProccessorService() {
        return new ZoneProcessorService(IService.TOWER_RECIEVER);
    }
    
}
