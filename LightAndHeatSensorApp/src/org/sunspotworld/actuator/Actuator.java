/**
 * encapsulates actuator logic.
 * @author Dominic Lindsay
 */
package org.sunspotworld.actuator;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YRelay;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Actuator {

    YRelay actuator;
    private String actuatorAddress;
    private ActuatorJob job;
    private static final int ON = 1;
    private static final int OFF = 0;
    
    
    public Actuator(String actuatorAddress, ActuatorJob job) {
        this.actuatorAddress = actuatorAddress;
        this.job = job;
    }
    
    public Actuator(String actuatorAddress, YRelay actuator) {
        this.actuatorAddress = actuatorAddress;
        this.actuator = actuator;
    }

    /**
     * turn actuator on
     */
    public void turnRelayOn()
    {
        try {
            actuator.setState(1);
        } catch (YAPI_Exception ex) {
            System.err.println("Failed to turn actuator: " + this.actuatorAddress + " ON");
        }
    }
    /**
     * turn actuator off
     */
    public void turnRelayOff()
    {
        try {
            actuator.setState(0);
        } catch (YAPI_Exception ex) {
            System.err.println("Failed to turn actuator: " + this.actuatorAddress + " OFF");
        }
    }
    /**
     * @return the actuatorAddress
     */
    public String getActuatorAddress() {
        return actuatorAddress;
    }

    /**
     * @return the job
     */
    public ActuatorJob getJob() {
        return job;
    }

    /**
     * @param actuatorAddress the actuatorAddress to set
     */
    public void setActuatorAddress(String actuatorAddress) {
        this.actuatorAddress = actuatorAddress;
    }

    /**
     * @param job the job to set
     */
    public void setJob(ActuatorJob job) {
        this.job = job;
    }
    /**
     * set actuator
     * @param actuator the actuator to be set 
     */
    public void setActuator(YRelay actuator) {
        this.actuator = actuator;
    }
    
    /**
     * Switch Actuator on
     */
    public void actuatorOn()
    {
        try {
            this.actuator.setState(ON);
        } catch (YAPI_Exception ex) {
            System.err.println("Error turning Actuator On");
            ex.printStackTrace();
        }
    }
    /**
     * Switch Actuator off
     */
    public void actuatorOff()
    {
        try {
            this.actuator.setState(OFF);
        } catch (YAPI_Exception ex) {
            System.err.println("Error turning actuator off");
            ex.printStackTrace();
        }
    }
    
    /**
     * Returns current running status of actuator
     */
    public int isSwitchedOn()
    {
        try {
               return actuator.getState();
        } catch (YAPI_Exception ex) {
            System.err.println("Error getting actuator status");
            ex.printStackTrace();
        }
        return 0;
    }
}
