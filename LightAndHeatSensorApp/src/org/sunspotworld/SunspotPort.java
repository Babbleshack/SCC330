package org.sunspotworld;

/**
 * Integer wrapper class for storing a port number for the SunSpots. 
 * Forces a value between 31 and 255 as the values 0-31 are reserved for system servicea
 * and 255 is the maximum port value for the SunSpots. 
 */
public class SunspotPort 
{
    public static final int MAX_VALUE = 255;
    public static final int MIN_VALUE = 31;

    private final int value;

    public SunspotPort(int value) throws PortOutOfRangeException
    {
        if(value < MAX_VALUE && value > MIN_VALUE) {
            this.value = value;
        } else {
            throw new PortOutOfRangeException("Supplied port number must be in the range 31-255");
        }
    }

    public int getPort() 
    {
        return this.value;
    }
}
