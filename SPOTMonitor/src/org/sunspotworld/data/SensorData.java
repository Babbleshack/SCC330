/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.data;

import org.sunspotword.data.ISensorData;

public class SensorData implements ISensorData {
    private double data;
    private AxisData axisData;
    public SensorData(int data) {
        this.data = (double) data;
    }
    public SensorData(double data) {
        this.data = data;
    }
    public SensorData(long data) {
        this.data = (double) data;
    }
    public SensorData(String data) {
        this.data = Double.parseDouble(data);
    }
    public SensorData(AxisData axisData)
    {
        this.axisData = axisData;
    }
    public int getDataAsInt() {
        return (int) this.data;
    }
    public long getDataAsLong() {
        return (long) this.data;
    }
    public double getDataAsDouble() {
        return this.data;
    }
    public String getDataAsString() {
        return String.valueOf(this.data);
    }
    public AxisData getAxisData()
    {
        return this.axisData;
    }
}
