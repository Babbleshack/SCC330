/*
 * Wrapper class for Accelerometer instantiation
 */
package org.sunspotworld.sensors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import java.io.IOException;
import org.sunspotword.data.AxisData;

public class AxisSensor
{
    private final IAccelerometer3D accel;
    public AxisSensor()
    {
        accel = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    }
    public AxisData getAxisData()
    {
        AxisData axisData;
        try {
            axisData = new AxisData(accel.getTiltX(), accel.getTiltY(),
                    accel.getTiltZ());
        } catch (IOException ex) {
            System.err.println("ERROR GETTING TILT");
            ex.printStackTrace();
            return new AxisData(0,0,0);
        }
        return axisData;
    }
    public AxisData getAccelData()
    {
        AxisData axisData;
        try {
            axisData = new AxisData(accel.getAccelX(), accel.getAccelY(),
                    accel.getAccelZ());
        } catch (IOException ex) {
            System.err.println("ERROR GETTING TILT");
            ex.printStackTrace();
            return new AxisData(0,0,0);
        }
        return axisData;
    }
    public double getTotalAccel()
    {
        try {
            return this.accel.getAccel();
        } catch (IOException ex) {
            System.err.println("ERROR GETTING TOTAL ACCELERATION");
            ex.printStackTrace();
        }
        return 0;
    }
}
