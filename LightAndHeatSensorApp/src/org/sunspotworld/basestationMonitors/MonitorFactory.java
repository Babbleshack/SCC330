/**
 * Monitor factory designed to encapsulate 
 * instantiation of sensor monitors
 */
package org.sunspotworld.basestationMonitors;

import com.sun.spot.resources.Resources;

public class MonitorFactory 
{
    public static ILightMonitor createLightMonitor()
    {
        return new LightMonitor();
    }
    
    public static IThermoMonitor createThermoMonitor()
    {
        return new ThermoMonitor();
    }
    
    public static IAccelMonitor createAccelMonitor()
    {
        return new AccelMonitor();
    }
    
    public static IMotionMonitor createMotionMonitor()
    {
        return new MotionMonitor();
    }

    public static ISwitchMonitor createSwitchMonitor()
    {
        return new SwitchMonitor();
    }
}
