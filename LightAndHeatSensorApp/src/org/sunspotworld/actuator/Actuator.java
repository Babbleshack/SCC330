/**
 * encapsulates actuator logic.
 * @author Dominic Lindsay
 */
package org.sunspotworld.actuator;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YRelay;
public class Actuator {

    YRelay actuator;
    private String actuatorAddress;
    private ActuatorJob job;
    
    public Actuator(String actuatorAddress, ActuatorJob job) {
        this.actuatorAddress = actuatorAddress;
        this.job = job;
    }
    
    public Actuator(String actuatorAddress, YRelay actuator) {
        this.actuator = actuator;
        this.job = job;
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
}
