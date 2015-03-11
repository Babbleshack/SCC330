/**
 * receives bearing readings from spots.
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


public class TReceivingCompassThreshold implements Runnable {
    private final QueryManager _qm;
    private IReceivingRadio _rRadio;
    private final int _port;
    private final ConcurrentHashMap<String, String> _addressMap;
    public TReceivingCompassThreshold(ConcurrentHashMap addressMap) {
        _port = SunspotPort.COMPASS_THRESH;
        try {
            _rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.COMPASS_THRESH)
            );
            //need radio
        } catch (PortOutOfRangeException ex) {
            System.err.println("error with radio in Barometer thread");
        }
        _qm = new QueryManager();
        _addressMap = addressMap;
    }

    public void run() {
        double angle;
        while(true) {
            try {
                angle = _rRadio.receiveBarometer();
                String address = _rRadio.getReceivedAddress();
                if(!_addressMap.contains(address)) continue;    
                _qm.createBarometerRecord(angle, _rRadio.getReceivedAddress(),
                        System.currentTimeMillis(), _port);
                System.out.println("Message from " + _rRadio.getReceivedAddress() + " \t\t " + "Angle: \t\t " + angle);
            } catch (IOException ex) {
                System.err.println("Error storing barometer reading");
            }
        }
    }
}
