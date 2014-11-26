/**
 * Receives battery data.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

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
        while(true)
        {
            rRadio.receiveBatteryLevel();//<--- goes straght into DB

            //NEED RECEIVING METHODS
            //DB METHOD
        }
    }
}
