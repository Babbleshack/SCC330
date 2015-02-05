/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.spotMonitors;

/**
 *
 * @author babbleshack
 */
public interface IBatteryMonitor {
    long SECOND = 1000;

    double getDataAsDouble();

    int getDataAsInt();

    long getDataAsLong();

    String getDataAsString();

    void setPowerIndicator(double level);
    /**
     * //gets LED array
    private ITriColorLEDArray leds =
    (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private static final int HOST_PORT = 66;
    private static final int SAMPLE_PERIOD = 10 * 100;  // in milliseconds
    //returns an instance of the spot's powerController
    IPowerController pCtrller =  Spot.getInstance().getPowerController();
    protected void startApp() throws MIDletStateChangeException
    {
    BootloaderListenerService.getInstance().start();
    //invokes the thread to transmit the SPOT's current voltage
    sendBattVoltageThread();
    }
    public void sendBattVoltageThread()
    {
    new Thread()
    {
    public void run()
    {
    RadiogramConnection rConn=null;
    Datagram dg = null;
    try
    {
    rConn = (RadiogramConnection) Connector.open("radiogram://broadcast:"+HOST_PORT);
    // Then, we ask for a datagram with the maximum size allowed
    dg = rConn.newDatagram(50);
    }
    catch (IOException e)
    {
    System.out.println("Could not open radiogram receiver connection");
    }
    while(true)
    {
    try
    {
    long now = System.currentTimeMillis();
    //returns the remaining Spot voltage (in millvolts)
    double volts = getSPOTVoltage();
    dg.reset();
    dg.writeLong(now);
    dg.writeDouble(volts);
    rConn.send(dg);
    Utils.sleep(SAMPLE_PERIOD - (System.currentTimeMillis() - now));
    }
    catch (IOException e)
    {
    System.out.println("Nothing received");
    }
    }
    }
    }.start();
    }
    //returns the spot's voltage max voltage is 5v
    public double getSPOTVoltage()
    {
    //spots voltage in millivolts
    double millivolts = pCtrller.getVbatt();
    double volts = millivolts/1000.0;
    setPowerIndicator(volts);
    return volts;
    }
    public void setPowerIndicator(double level)
    {
    //sets red LEDs on one at a time starting with the left LED
    ITriColorLED led = leds.getLED(0);
    if(level<=3.0)
    led.setRGB(90,0,0);
    if((level>3)&(level<=4))
    led.setRGB(0,0,90);
    if(level>4)
    led.setRGB(0,90,0);
    led.setOn();
    }
    protected void pauseApp()
    {
    // This is not currently called by the Squawk VM
    }
     */
    
}
