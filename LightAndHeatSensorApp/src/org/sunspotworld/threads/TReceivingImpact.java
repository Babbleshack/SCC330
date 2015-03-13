/**
 * receives Impact flags from spots.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.QueryManager;


public class TReceivingImpact implements Runnable {
    private final QueryManager _qm;
    private IReceivingRadio _rRadio;
    private final int _port;
    private final ConcurrentHashMap<String, String> _addressMap;
    public TReceivingImpact(ConcurrentHashMap addressMap) {
        _port = SunspotPort.IMPACT_PORT;
        try {
            _rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(_port)
            );
            //need radio
        } catch (PortOutOfRangeException ex) {
            System.err.println("error with radio in Impact thread");
        }
        _addressMap = addressMap;
        _qm = new QueryManager();
    }

    public void run() {
        double flag;
        while(true) {
            try {
                flag = _rRadio.receiveBarometer();
                if(!_addressMap.contains(_rRadio.getReceivedAddress())) continue;    
                _qm.createImpactRecord(flag, _rRadio.getReceivedAddress(),
                        System.currentTimeMillis(), _port);
                System.out.println("Message from " + _rRadio.getReceivedAddress() + " \t\t " + "flag: \t\t " + flag);
            } catch (IOException ex) {
                System.err.println("Error storing Flag reading");
            }
        }
    }
}
