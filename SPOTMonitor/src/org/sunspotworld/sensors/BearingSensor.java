/**
 * bearing sensor, encapsulate bearing sensor readings.
 * @author Dominic Lindsay
 */
package org.sunspotworld.sensors;

import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.util.Utils;
import java.io.IOException;
import org.sunspotworld.data.SensorData;


public class BearingSensor implements ISensor {
    private final EDemoBoard _dBoard;
    private final IIOPin _d0;
    private final IIOPin _d1;
    private static final byte CMPS_COMMAND = (byte)0x13;
    
    public BearingSensor() {
        _dBoard = EDemoBoard.getInstance();
        _d0 = _dBoard.getIOPins()[EDemoBoard.D0];
        _d1 = _dBoard.getIOPins()[EDemoBoard.D1];
        _initCompass(); //get compass going.
    }
       
    public SensorData getData() {
        double angle = 0;
        int msb = 0; 
        int lsb = 0;
        //SEND BEARING COMMAND (0x13) 
        _dBoard.writeUART(CMPS_COMMAND);
        Utils.sleep(5); //latency???
        try {
            msb = _dBoard.readUART();
            lsb = _dBoard.readUART();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //do some bitshitfting to get value from two bytes returned.
            //get rid of the extra 0
            angle = ((msb<<8)+lsb)/10;
        }
        return new SensorData(angle);
    }
    private void _initCompass() {
        //SET RX/TX PINS
        _d0.setAsOutput(false);
        _d1.setAsOutput(true); //RX1 FOR INPUT TO CMPS MODULE??
        //SET SERIAL BAUD RATE TO 19200 
        //SET TO 8 BITS FOR DATA (SHOULDNT THIS BE 16 FOR 2 BYTES???)
        //NO PARIT, 2 stop bits.
        //SEE CMPS Datasheet for more info.
        _dBoard.initUART(
               EDemoBoard.SERIAL_SPEED_9600, 
               EDemoBoard.SERIAL_DATABITS_8, 
               EDemoBoard.SERIAL_PARITY_NONE,
               EDemoBoard.SERIAL_STOPBITS_2
        );
        Utils.sleep(10); //for latencies?
    }
}
