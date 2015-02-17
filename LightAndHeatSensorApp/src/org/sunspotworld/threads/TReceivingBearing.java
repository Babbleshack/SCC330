/**
 * receives bearing readings from spots.
 * @author Dominic Lindsay
 */
package org.sunspotworld.threads;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sunspotworld.basestationMonitors.IAccelMonitor;
import org.sunspotworld.basestationRadios.IReceivingRadio;
import org.sunspotworld.basestationRadios.PortOutOfRangeException;
import org.sunspotworld.basestationRadios.RadiosFactory;
import org.sunspotworld.basestationRadios.SunspotPort;
import org.sunspotworld.database.QueryManager;


public class TReceivingBearing implements Runnable {
    private QueryManager _qm;
    private IReceivingRadio _rRadio;
    public TReceivingBearing() {
        _qm = new QueryManager();
        try {
            _rRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.BAROMETER_PORT)
            );
            //need radio
        } catch (PortOutOfRangeException ex) {
            System.err.println("error with radio in Barometer thread");
        }
    }

    public void run() {
        double angle;
        while(true) {
            try {
                angle = _rRadio.receiveBarometer();
                _qm.createBarometerRecord(angle, _rRadio.getReceivedAddress(),
                        System.currentTimeMillis(), SunspotPort.BAROMETER_PORT);

            } catch (IOException ex) {
                System.err.println("Error storing barometer reading");
            }
        }
    }
}
