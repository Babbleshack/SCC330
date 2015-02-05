package org.sunspotworld.data;

/**
 *
 * @author Dominic Lindsay
 */
public interface ITowerSpot
{

    /**
     * @return the address
     */
    String getAddress();

    /**
     * @return the powerLevel
     */
    int getPowerLevel();

    /**
     * @param address the address to set
     */
    void setAddress(String address);

    /**
     * @param powerLevel the powerLevel to set
     */
    void setPowerLevel(int powerLevel);
}
