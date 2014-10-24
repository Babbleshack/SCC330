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
        System.out.println("CALLING MOTION MONITOR");
        IMotionMonitor motion = new MotionMonitor();
        if(motion == null)
            System.out.println("Motion Moniter failed to innit");
        //return new MotionMonitor();
        System.out.println("SENSOR VALUE : "+ motion.getSensorValue());
        System.out.println("EXITING FACTORY");
        return motion;
    }
}
