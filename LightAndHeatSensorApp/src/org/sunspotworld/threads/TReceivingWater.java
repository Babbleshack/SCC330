/**
 * Simple wrapper for receiving water level data
 * and depositing into database.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.IQueryManager;


public class TReceivingWater implements Runnable
{
    private IReceivingRadio rRadio;
    private IQueryManager qManage;
    
    public TReceivingWater()
    {
        try {
            rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.WATER_PORT)
            );
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            /**
             * Receive data and put into db.
             */
            int waterLevel = rRadio.receiveWater();
            qManage.createWaterRecord(waterLevel, rRadio.getReceivedAddress(),
                    System.currentTimeMillis());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
