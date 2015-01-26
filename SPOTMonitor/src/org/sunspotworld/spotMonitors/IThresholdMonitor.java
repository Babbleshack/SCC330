package org.sunspotworld.spotMonitors;

import org.sunspotworld.data.SensorData;
public interface IThresholdMonitor {
    double getThreshold();
    void setThreshold(double threshold);
}
