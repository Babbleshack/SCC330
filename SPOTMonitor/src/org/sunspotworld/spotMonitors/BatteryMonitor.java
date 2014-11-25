package org.sunspotworld.spotMonitors;

import com.sun.spot.peripheral.IBattery;
import com.sun.spot.peripheral.IPowerController;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.service.Task;
import com.sun.spot.util.Utils;
import java.io.IOException;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;
/**
 * @author babbleshack
 */
public class BatteryMonitor extends Task implements IBatteryMonitor 
{
    ITriColorLEDArray leds;
    IPowerController pwerControl;
    IBattery battery;
    ISendingRadio sRadio;
    private static final int MAX_VOLTAGE = 5;
    private static final int MAX_PERCENTAGE = 5;
    
    public BatteryMonitor()
    {
        super(1000);
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        pwerControl = Spot.getInstance().getPowerController();
       
        try {
            sRadio = RadiosFactory.createSendingRadio(new SunspotPort(SunspotPort.BATTERY_PORT));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
        System.out.println(this.getSPOTVoltage());
        System.out.println("Battery Level " + pwerControl.getBattery().getBatteryLevel() + "%");
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

    public void doTask() throws Exception {        
        System.out.println("Battery Voltage Percentage: " + 
                Math.floor(Math.abs(((
                        this.getSPOTVoltage()/MAX_VOLTAGE)*MAX_PERCENTAGE)
                )));
        sRadio.sendBatteryPower((int)Math.floor(Math.abs(
                ((this.getSPOTVoltage()/MAX_VOLTAGE)*MAX_PERCENTAGE))));
    }
    
}
