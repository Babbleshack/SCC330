/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.sensors;


public class SensorFactory 
{
    public static ISensor createAxisSensor()
    {
        return new AxisSensor();
    }
    public static ISensor createLightSensor()
    {
        return new LightSensor();
    }
    public static ISensor createThermoSensor()
    {
        return new ThermoSensor();
    }
    public static ISensor createBearingSensor()
    {
        return new BearingSensor();
    }
    public static ISensor createImpactSensor() {
        return new ImpactSensor();
    }
}
