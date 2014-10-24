/**
 * Motion Detector
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
public interface IMovementMonitor {
	public long getMovementTime();
	public int getSensorValue();
}