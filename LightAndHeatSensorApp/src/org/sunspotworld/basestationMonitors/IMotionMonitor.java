/**
 * Dominic Lindsay
 */
package org.sunspotworld.basestationMonitors;

import org.sunspotworld.basestationRadios.SunspotPort;

public interface IMotionMonitor
{
	/** 
     * Returns port number for this sensor type
     */
    SunspotPort getPort(); 
}
