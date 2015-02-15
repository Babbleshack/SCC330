/*
 * SunSpotApplication.java
 *
 * Created on 13-Oct-2014 23:18:39;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.ISleepManager;
import com.sun.spot.peripheral.Spot;
import org.sunspotworld.threads.TDiscoverMe;
import org.sunspotworld.threads.TDemandSwitch;
import org.sunspotworld.spotRadios.RadiosFactory;
import org.sunspotworld.spotRadios.IReceivingRadio;
import org.sunspotworld.spotRadios.SunspotPort;
import org.sunspotworld.spotRadios.PortOutOfRangeException;


import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.IEEEAddress;

import java.io.IOException;
import java.lang.SecurityException;

import java.lang.Thread;
import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.sunspotworld.service.IService;
import org.sunspotworld.service.ServiceController;
import org.sunspotworld.service.ServiceFactory;
import org.sunspotworld.monitorStates.AxisThresholdState;
import org.sunspotworld.monitorStates.BearingMonitorState;
import org.sunspotworld.monitorStates.LightThresholdState;
import org.sunspotworld.monitorStates.ThermoThresholdState;
import org.sunspotworld.sensors.SensorFactory;
import org.sunspotworld.spotMonitors.BatteryMonitor;
import org.sunspotworld.spotMonitors.MonitorFactory;
import org.sunspotworld.spotMonitors.SampleMonitor;
import org.sunspotworld.spotMonitors.ThresholdMonitor;


/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public final class SunSpotApplication extends MIDlet implements Runnable {

    /**
     * Threads for communicating with Basestation
     */
    private Thread switchThread;

    private Thread discoverMeThread;

    private ServiceController serviceController;
    private static final int MOCK_HEAT_THRESHOLD = 30;
    private IReceivingRadio discoverRequestRadio;

    
    public SunSpotApplication() {
        this.serviceController = new ServiceController(this.prepareServices());
        BatteryMonitor bm = new BatteryMonitor();
        bm.start();
        try {
            discoverRequestRadio = RadiosFactory.createReceivingRadio(
                    new SunspotPort(SunspotPort.DISCOVERY_PORT));
        } catch (PortOutOfRangeException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        discoverMeThread = new Thread(new TDiscoverMe(),"discoverMeService");
        discoverMeThread.start();
        
        switchThread = new Thread(new TDemandSwitch(MOCK_HEAT_THRESHOLD),"switchService");
        switchThread.start();
        //disable deep sleep.
        this._disableDeepSleep();
    }
    public Hashtable prepareServices() 
    {
        Hashtable services = new Hashtable();
        //add thermo services
        services.put(Integer.valueOf(IService.THERMO_SAMPLE), 
                ServiceFactory.createThermoService(
                new SampleMonitor(SensorFactory.createThermoSensor())) 
        );
        services.put(Integer.valueOf(IService.THERMO_THRESH),
                ServiceFactory.createThermoService(
                        MonitorFactory.createThresholdMonitor(
                                SensorFactory.createThermoSensor(), 
                                new ThermoThresholdState()))
        );
        System.out.println("Added Thermo Services");
        //add Light Services
        services.put(Integer.valueOf(IService.LIGHT_SAMPLE), 
                ServiceFactory.createLightService(
                new SampleMonitor(SensorFactory.createLightSensor())) 
        );
        services.put(Integer.valueOf(IService.LIGHT_THRESH),
                ServiceFactory.createLightService(
                        MonitorFactory.createThresholdMonitor(
                                SensorFactory.createLightSensor(), 
                                new LightThresholdState()))
        );
        System.out.println("Added Light Services");
        //add Accellerometer Services
        services.put(Integer.valueOf(IService.ACCEL_SAMPLE), 
                ServiceFactory.createAccellerometerService(
                new SampleMonitor(SensorFactory.createAxisSensor())) 
        );
        services.put(Integer.valueOf(IService.ACCEL_THRESH),
                ServiceFactory.createAccellerometerService(
                        MonitorFactory.createThresholdMonitor(
                                SensorFactory.createAxisSensor(), 
                                new AxisThresholdState()))
        );

        /**
         * This is commented out at the moment as the ZoneProcessorServer 
         * blocks this thread inside its run() call. Needs fixing
         */
         services.put(Integer.valueOf(IService.PING), 
                 ServiceFactory.createTowerService());
         System.out.println("added tower service");
         services.put(Integer.valueOf(IService.TOWER_RECIEVER),
                 ServiceFactory.createZoneProccessorService());
        System.out.println("added ZoneProcessor service");
        
        services.put(Integer.valueOf(IService.BAROMETER_SAMPLE), 
                ServiceFactory.createBarometerService(
                        new SampleMonitor(SensorFactory.createBearingSensor())
                )
        );
        services.put(Integer.valueOf(IService.BAROMETER_THRESH), 
                ServiceFactory.createBarometerService(
                        new ThresholdMonitor(
                                SensorFactory.createBearingSensor(),
                                new BearingMonitorState()
                        )
                )
        );
        System.out.println("Added Barometer stuff");        
        return services;
        
    }
    public void run()
    {
        int[] portsThresholds;
        int[] thresholdSamples;
        int[] ports;
        int i,y; 
        while(true) {
            portsThresholds = null;
            thresholdSamples = null;
            ports = null;
          try {
              portsThresholds = discoverRequestRadio.receiveDiscoverResponse(); 
          } catch (IOException io) {
              System.out.println("Exception while receiving discover response: " + io);
          }
          //build new array because some silly bugger put both thresholds 
          //and ports into the same array :p
          ports = new int[portsThresholds.length/2];
          thresholdSamples = new int[portsThresholds.length/2];
          for (i = 0, y = 0;i < portsThresholds.length; i += 2, y++) {
              ports[y] = portsThresholds[i];
              thresholdSamples[y] = portsThresholds[i+1];
          }
          System.out.println("ports length: " + ports.length);
          System.out.println("theshold sample: " + thresholdSamples.length);
          //pass the ports to autostoper 
          //then start all services in ports
          if(portsThresholds != null){
              serviceController.autoManageServices(ports, thresholdSamples);
          } else {
              System.out.println("No ports or thresholds");
          }
        }
    }
    /**
     * disable deep sleep functionality.
     */
    private void _disableDeepSleep()
    {
        ISleepManager sleepManager = Spot.getInstance().getSleepManager();
        sleepManager.disableDeepSleep();
        System.out.println("deep sleep in enabled?: " + sleepManager.isDeepSleepEnabled());
    }
    /**
     * Starts polling threads
     * @throws MIDletStateChangeException [description]
     */
    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        try {
           // SunSpotApplication spa = new SunSpotApplication();
           // spa.run();
           this.run(); //start the listener service
           System.out.println("Running loop");
        } catch(SecurityException se) {
            System.out.println("The current thread cannot create a thread in the specified thread group: " + se);
        }

        //notifyDestroyed();                      // cause the MIDlet to exit
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}

