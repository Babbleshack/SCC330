/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotMonitors.WaterMonitor;
import org.sunspotworld.spotMonitors.ImpactMonitor;
import org.sunspotworld.sensors.ImpactSensor;

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
            return new CompassService(monitor, IService.BAROMETER_SAMPLE);
        else
            return new CompassService(monitor, IService.BAROMETER_THRESH);
    }
    
    public static IService createTowerService() {
        return new TowerService(IService.PING);
    }
    
    public static IService createZoneProccessorService() {
        return new ZoneProcessorService(IService.TOWER_RECIEVER);
    }

    public static IService createWaterService() {
    	return new WaterService(new WaterMonitor(), IService.WATER_SERVICE);
    }

    public static IService createImpactService() { 
        return new ImpactService(new ImpactMonitor(new ImpactSensor()), IService.IMPACT_SERVICE);
    }
    
}
