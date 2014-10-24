/**
 * Motion Detector
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
import org.sunspotworld.spotRadios.SunspotPort;

public interface IMotionMonitor {
	public long getMotionTime();
	public int getSensorValue();
	public SunspotPort getPort();
}