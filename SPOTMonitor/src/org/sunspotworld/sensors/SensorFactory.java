/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.sensors;


public class SensorFactory 
{
    public static AxisSensor createAxisSensor()
    {
        return new AxisSensor();
    }
    public static LightSensor createLightSensor()
    {
        return new LightSensor();
    }
    public static ThermoSensor createThermoSensor()
    {
        return new ThermoSensor();
    }
}
