package org.sunspotworld.monitorStates;

import org.sunspotworld.spotMonitors.ThresholdMonitor;

/**
 * Monitor states define the methods for checking sensor readings against 
 * threshold values. 
 * Default monitor states include (axis, thermo and light)
 * @author babbleshack
 */
public interface IThresholdMonitorState {
    public void checkThresholdCondition(ThresholdMonitor context);
}
