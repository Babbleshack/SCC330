/**
 * Receives battery data.
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

public class TReceivingBattery implements Runnable {

    private IQueryManager qManage;
    private IReceivingRadio rRadio;
    private final ConcurrentHashMap<String, String> _addressMap;
    public TReceivingBattery(ConcurrentHashMap addressMap)
    {
        try {
            qManage = new QueryManager();
            rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.BATTERY_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        }
        _addressMap = addressMap;
    }
    public void run() {
        int batteryLevel = 0;
        while(true)
        {
            try {
            batteryLevel =
                    rRadio.receiveBatteryLevel();//<--- goes straght into DB
            String address = rRadio.getReceivedAddress();
            if(!_addressMap.contains(address)) continue;    
            qManage.updateBatteryPower(rRadio.getReceivedAddress(), batteryLevel);
                System.out.println("Message from " + rRadio.getReceivedAddress() + " \t\t " + "Battery: \t\t " + batteryLevel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
