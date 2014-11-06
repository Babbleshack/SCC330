package org.sunspotword.data;
/**
 * @author Dominic Lindsay
 * Wrapper for Tower address and power level data
 */
public class TowerSpot implements ITowerSpot 
{
    private String address;
    private int powerLevel;
    public TowerSpot(String address, int powerLevel)
    {
        this.address = address;
        this.powerLevel = powerLevel;
    }
    /**
     * overides equals method.
     * @param o TowerSpot
     * @return boolean
     */
    public boolean equals(Object o)
    {
        return address.equals(((TowerSpot)o).getAddress());
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the powerLevel
     */
    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * @param powerLevel the powerLevel to set
     */
    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
    
}
