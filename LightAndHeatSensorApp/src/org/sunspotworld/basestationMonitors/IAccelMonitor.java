/**
 * Receive Accel monitor
 */ 
package org.sunspotworld.basestationMonitors;

import org.sunspotworld.basestationRadios.SunspotPort;

public interface IAccelMonitor
{
    /** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 
}