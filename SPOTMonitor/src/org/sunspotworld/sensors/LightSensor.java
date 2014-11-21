/**
 *Wrapper class for Light Sensor
 * @author Dominic Lindsay
 */
package org.sunspotworld.sensors;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import java.io.IOException;


public class LightSensor 
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
}
