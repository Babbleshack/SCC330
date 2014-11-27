/**
 * Receives battery data.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.IQueryManager;
import org.sunspotworld.database.QueryManager;

public class TReceivingBattery implements Runnable {

    private IQueryManager qManage;
    private IReceivingRadio rRadio;
    
    public TReceivingBattery()
    {
        try {
            qManage = new QueryManager();
            rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.BATTERY_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
    }
    public void run() {
        int batteryLevel = 0;
        while(true)
        {
            try {
            batteryLevel =
                    rRadio.receiveBatteryLevel();//<--- goes straght into DB
                qManage.updateBatteryPower(rRadio.getReceivedAddress(), batteryLevel);
                //NEED RECEIVING METHODS
                //DB METHOD
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
