/**
 * receives bearing readings from spots.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.QueryManager;


public class TReceivingCompassSample implements Runnable {
    private final QueryManager _qm;
    private IReceivingRadio _rRadio;
    private final int _port;
    public TReceivingCompassSample() {
        _port = SunspotPort.COMPASS_SAMPLE;
        try {
            _rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(_port)
            );
            //need radio
        } catch (PortOutOfRangeException ex) {
            System.err.println("error with radio in Barometer thread");
        }
        _qm = new QueryManager();
    }

    public void run() {
        double angle;
        while(true) {
            try {
                angle = _rRadio.receiveBarometer();
                _qm.createBarometerRecord(angle, _rRadio.getReceivedAddress(),
                        System.currentTimeMillis(), _port);
            } catch (IOException ex) {
                System.err.println("Error storing barometer reading");
            }
        }
    }
}
