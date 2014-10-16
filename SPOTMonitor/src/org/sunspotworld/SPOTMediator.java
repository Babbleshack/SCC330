/*
 * SPOTMediator
 * 'Mediates' Communication between Spot and Monitor Objects
 * Dominic Lindsay
 */
package org.sunspotworld;

import java.io.IOException;


public class SPOTMediator implements ISPOTMediator 
{
    private ISendingRadio sendingRadio;
    public SPOTMediator(ISendingRadio sendingRadio)
    {
        sendingRadio = sendingRadio;
    }
    /**
     * call light monitor 
     * @param lightMonitor 
     */
    public void postLightData(ILightMonitor lightMonitor) {
        sendingRadio.sendLight(lightMonitor.getLightIntensity());
    }
    public void postCelciusData(IThermoMonitor thermoMonitor) {
        sendingRadio.sendHeat(thermoMonitor.getCelsiusTemp());
    }
    public void postFahrenheitData(IThermoMonitor thermoMonitor) {
        sendingRadio.sendHeat(thermoMonitor.getFahrenheitTemp());
    }
    
}
