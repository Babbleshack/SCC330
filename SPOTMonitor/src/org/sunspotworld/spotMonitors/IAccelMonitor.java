/*
 * Accelerometer monitor Interface
 */

package org.sunspotworld.spotMonitors;

import org.sunspotworld.spotRadios.SunspotPort;

public interface IAccelMonitor extends IMonitor
{
    public static final int PORT = SunspotPort.ACCEL_PORT;
    /**
     * Returns port number for this sensor type
     */
    SunspotPort getPort();
}