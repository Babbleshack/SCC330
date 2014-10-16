/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;

public class ThermoMonitor implements IThermoMonitor 
{
    private ISPOTMediator mediator;
    private ITemperatureInput thermo;
    private ThermoMonitor Outerself = this;
    
    /**
     * Define Condition Listener Callback.
     * Uses SPOTMediator to call send method on the radio.
     */
    IConditionListener postData = new IConditionListener() {
        ISPOTMediator med = mediator; // get referance to outer mediator
        ThermoMonitor self = Outerself; //get ref to outer 'ThermoMonitor' inst
        public void conditionMet(SensorEvent evt, Condition condition){
            med.postCelciusData(self);
            med.postFahrenheitData(self);
        }
    };
    
    /**
     * Define Conditon Listener
     * triggers callback every 60 seconds
     */
    Condition checkTemp = new Condition(thermo, postData, 60 * 1000) {
        public boolean isMet(){
            //we can add a switch here to deativate reporter
            return true;
        }
    };
        
    
    
    
    
    
    //IConditionListener timeout = new ConditionListener
    public ThermoMonitor(ISPOTMediator mediator)
    {
        this.thermo = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
        this.mediator = mediator;
        
    }

    public double getCelsiusTemp() {
        try 
        {
            return thermo.getCelsius();
        } catch (IOException ex) {
            System.err.println("Error getting Celcius " + ex);
        }
        return -9999;
    }

    public double getFahrenheitTemp() 
    {
        try 
        {
            return thermo.getFahrenheit();
        } catch (IOException ex) {
            System.err.println("Error getting Fahrenheit " + ex);
        }
        return -9999;
    }
    
}
