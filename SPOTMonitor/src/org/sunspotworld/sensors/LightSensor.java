/**
 *Wrapper class for Light Sensor
 * @author Dominic Lindsay
 */
package org.sunspotworld.sensors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import java.io.IOException;
import org.sunspotword.data.SensorData;


public class LightSensor implements ISensor
{
    private final ILightSensor lightSensor;
    public LightSensor()
    {
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    }
    public double getLightReading()
    {
        try {
            return this.lightSensor.getAverageValue();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public SensorData getData() {
        try {
            return new SensorData(this.lightSensor.getAverageValue());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
