/**
 * Motion Detector
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
public interface IMotionMonitor {
	public long getMotionTime();
	public int getSensorValue();
}