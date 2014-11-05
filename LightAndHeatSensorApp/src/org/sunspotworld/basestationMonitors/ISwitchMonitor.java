package org.sunspotworld.basestationMonitors;

import org.sunspotworld.basestationRadios.SunspotPort;

/**
 *
 * @author adamcornforth
 */
public interface ISwitchMonitor
{
    /** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 
}
