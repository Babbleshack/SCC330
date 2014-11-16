package org.sunspotworld.spotMonitors;

import com.sun.spot.peripheral.IPowerController;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.Utils;

/**
 *
 * @author babbleshack
 */
public class BatteryMonitor implements IBatteryMonitor 
{
    ITriColorLEDArray leds;
    IPowerController pwerControl;
    
    public BatteryMonitor()
    {
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        pwerControl = Spot.getInstance().getPowerController();
    }

    public String getDataAsString() {
        return String.valueOf(getSPOTVoltage());
    }

    public double getDataAsDouble() {
        return getSPOTVoltage();
    }

    public int getDataAsInt() {
        return (int) getSPOTVoltage();
    }

    public long getDataAsLong() {
        return (long) getSPOTVoltage();
    }
    private double getSPOTVoltage()
    {
        //spots voltage in millivolts
        double millivolts = pwerControl.getVbatt();
        double volts = millivolts/1000.0;
        return volts;
    }
    public void setPowerIndicator(double level)
    { 
        int count = 0;
        if(level<=3.0)
          leds.setRGB(90,0,0);
        if((level>3)&(level<=4))
          leds.setRGB(0,0,90);
        if(level>4)
          leds.setRGB(0,90,0);
        leds.setOn();
        Utils.sleep(2 * SECOND);
        leds.setOff();
    }
}
