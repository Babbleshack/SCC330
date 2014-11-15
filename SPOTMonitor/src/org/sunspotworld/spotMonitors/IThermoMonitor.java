/*
 * Thermomonitor Interface
 * Dominic Lindsay 
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.spotRadios.SunspotPort;

/**
 *
 * @author babbleshack
 */
public interface IThermoMonitor extends IMonitor
{
    public static final int PORT = SunspotPort.THERMO_PORT;
    /** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 

    /**
     * return temperature in Degrees Celsius
     * @return double temp
     */
    double getCelsiusTemp();
}
