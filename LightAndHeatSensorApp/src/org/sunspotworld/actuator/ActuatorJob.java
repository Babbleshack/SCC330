/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.actuator;


public class ActuatorJob 
{
    private int id;
    private String direction;
    private double threshold;
    
    public ActuatorJob(int id, String direction, double threshold)
    {
        this.id = id; 
        this.direction = direction;
        this.threshold = threshold;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

}
