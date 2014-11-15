/**
 * Motion Detector
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
import org.sunspotworld.spotRadios.SunspotPort;

public interface IMotionMonitor extends IMonitor {
        public static final int PORT = SunspotPort.MOTION_PORT;

	public long getMotionTime();
	public int getSensorValue();
	public SunspotPort getPort();
}