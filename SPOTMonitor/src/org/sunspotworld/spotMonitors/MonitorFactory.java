/**
 * Monitor Factory
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
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

    public static ISwitchMonitor createSwitchMonitor()
    {
        return new SwitchMonitor();
    }
    
    public static IBatteryMonitor createBatteryMonitor()
    {
        return new BatteryMonitor();
    }
    
    public static WaterMonitor createWaterMonitor()
    {
        return new WaterMonitor();
    }
}
