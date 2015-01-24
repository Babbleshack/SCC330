/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import org.sunspotworld.spotMonitors.IMonitor;

public class ServiceFactory {
    public static IService createThermoService(IMonitor monitor) {
        return new ThermoService(monitor);
    }
    public static IService createLightService(IMonitor monitor) {
        return new LightService(monitor);
    }
    public static IService createAccellerometerService(IMonitor monitor) {
        return new AccelerometerService(monitor);
    }
}
