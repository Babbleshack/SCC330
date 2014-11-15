/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

import org.sunspotworld.spotRadios.SunspotPort;

/**
 *
 * @author babbleshack
 */
public interface ILightMonitor extends IMonitor
{
    public static final int PORT = SunspotPort.LIGHT_PORT;
    /** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 
    
    /**
     * basically a wrapper
     * returns the average value of
     * 17 '1 msec' readings.
     * @return 
     */
    int getLightIntensity();
    
}
