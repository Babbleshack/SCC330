/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.service.Task;
import java.io.IOException;
import java.util.Random;
import org.sunspotworld.spotMonitors.IMonitor;
import org.sunspotworld.spotRadios.ISendingRadio;
import org.sunspotworld.spotRadios.PortOutOfRangeException;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.SunspotPort;


public class TowerService extends Task implements IService {

    private ISendingRadio radio;
    private ITriColorLEDArray leds;
    private ITriColorLED led;
    private Random gen;
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = SECOND;
    private int _serviceId;
    public TowerService(int serviceID) 
    {
        this._serviceId = serviceID;
         try
        {
            radio = RadiosFactory.createSendingRadio(
                    new SunspotPort(SunspotPort.PING_PORT));	
        } catch (PortOutOfRangeException e) {
            System.err.println("error creating PING radio: " + e);
        } catch (IOException e) {
            System.err.println("error creating PING radio: " + e);
        }
         gen = new Random();
         leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class );
         leds.setRGB(0, 0, 255);
         leds.setOn();
         led = leds.getLED(7);
         led.setRGB(255, 0, 0);
         System.out.println("STARTING TOWER");
    }
    public void doTask() {
        led.setOn();
        radio.ping();
        led.setOff();
        long wait = (SECOND / (gen.nextInt(10) + 1));
        this.setScheduledTime(wait);
    }
    public void startService() {
        this.start();
    }

    public void stopService() {
        this.stop();
    }

    public boolean isScheduled() {
        return this.isActive();
    }

    public int getServiceId() {
        return this._serviceId;
    }

    public IMonitor getMonitor() {
        return null;
    }
    public void setData(int data) {
        return;
    }
    

}
