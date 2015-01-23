package org.sunspotworld.spotMonitors;

import org.sunspotword.data.SensorData;
public interface IThresholdMonitor {
    double getThreshold();
    void setThreshold(double threshold);
}
