/**
 * Monitor Factory
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;

//import com.sun.spot.resources.Resources;

public class MonitorFactory 
{
    public static ILightMonitor createLightMonitor(int threshold)
    {
        return new LightMonitor(threshold);
    }
    public static IThermoMonitor createThermoMonitor(int threshold)
    {
        return new ThermoMonitor(threshold);
    }
    public static IAccelMonitor createAccelMonitor()
    {
    	return new AccelMonitor();
    }
    public static IMotionMonitor createMotionMonitor()
    {
        return new MotionMonitor();
    }
}
