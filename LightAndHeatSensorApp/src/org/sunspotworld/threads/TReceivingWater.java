/**
 * Simple wrapper for receiving water level data
 * and depositing into database.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.IQueryManager;
import org.sunspotworld.database.QueryManager;


public class TReceivingWater implements Runnable
{
    private IReceivingRadio rRadio;
    private IQueryManager qManage;
    private final ConcurrentHashMap<String, String> _addressMap;
    
    public TReceivingWater(ConcurrentHashMap addressMap)
    {
        try {
            rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.WATER_PORT)
            );
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
        qManage = new QueryManager();
        _addressMap = addressMap;
    }

    public void run() {
        while(true)
        {
            try {
                /**
                 * Receive data and put into db.
                 */
                int waterLevel = rRadio.receiveWater();
                String address = rRadio.getReceivedAddress();
                if(!_addressMap.contains(address)) continue;
                qManage.createWaterRecord(waterLevel, rRadio.getReceivedAddress(),
                        System.currentTimeMillis(), SunspotPort.WATER_PORT);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
