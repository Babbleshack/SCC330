/**
 * Monitor Factory
 * Dominic Lindsay
 */
package org.sunspotworld.spotMonitors;
import org.sunspotworld.monitorStates.IThresholdMonitorState;
import org.sunspotworld.sensors.ISensor;
public class MonitorFactory 
{
    public static IMonitor createSampleMonitor(long sampleRate, ISensor sensor)
    {
        return new SampleMonitor(sensor);
    }
    public static IMonitor createThresholdMonitor(ISensor sensor, 
            IThresholdMonitorState state)
    {
        return new ThresholdMonitor(sensor, state);
    }
    public static ISwitchMonitor createSwitchMonitor()
    {
        return new SwitchMonitor();
    }
}
