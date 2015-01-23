package org.sunspotworld.spotMonitors;

import org.sunspotword.data.SensorData;
public interface IThresholdMonitor {
    void checkSensorReading();
    double getThreshold();
    void setThreshold(double threshold);
    SensorData getSensorReading();
}
