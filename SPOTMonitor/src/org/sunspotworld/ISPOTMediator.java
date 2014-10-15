/*
 * 'Mediates' Communications between spot controller object
 * and Spot monitor Objects, atm the moment allows for 
 * clean call backs from the spot monitors to the controllers
 * and keeps the opposite relationship tidy...
 * May also be expanded to radio controller into two seperate mediator 
 * classes 'MonitorMediator' and 'RadioMediator'
 * Dominic
 */
package org.sunspotworld;

/**
 *
 * @author Dominic Lindsay
 */
interface ISPOTMediator{
    public void postLightData(ILightMonitor lightMonitor);
    public void postCelciusData(IThermoMonitor thermoMonitor);
    public void postFahrenheitData(IThermoMonitor thermoMonitor);
}
