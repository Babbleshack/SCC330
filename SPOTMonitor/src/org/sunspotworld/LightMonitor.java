/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;
/**
 * Define a Light Monitor 
 * @author babbleshack
 */
public class LightMonitor implements ILightMonitor
{
 
    private ISPOTMediator mediator; // Mediator
    private ILightSensor lightSensor; //Light Sensor 
    private ILightMonitor Outerself = this;
    /**
     * Define Condition Listener Callback.
     * Uses SPOTMediator to call send method on the radio.
     */
    IConditionListener postData = new IConditionListener() {
        ISPOTMediator med = mediator; // get referance to outer mediator
        ILightMonitor self = Outerself; //get ref to outer 'ThermoMonitor' inst
        public void conditionMet(SensorEvent evt, Condition condition){
            med.postLightData(Outerself);
        }
    };
    
    /**
     * Define Conditon Listener
     * triggers callback every 60 seconds
     */
    Condition checkTemp = new Condition(lightSensor, postData, 60 * 1000) {
        public boolean isMet(){
            //we can add a switch here to deativate reporter
            return true;
        }
    };
    
    public LightMonitor(ISPOTMediator mediator)
    {
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
        this.mediator = mediator;
    }
    public int getLightIntensity() 
    {
        try 
        {
           return lightSensor.getAverageValue();
        } catch (IOException ex) {
           System.err.println("Failed to get light sensor: " + ex);
        } 
        return -9999;
    }
    
}
