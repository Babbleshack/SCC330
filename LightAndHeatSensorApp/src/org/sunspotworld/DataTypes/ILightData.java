/*
 * encapsulate Light data extend ISpotData
 */
package org.sunspotworld.DataTypes;

/**
 *
 * @author Babblebase
 */
public interface ILightData extends ISunSpotData 
{
    void setLightData(double data);
    double getLightData();
}
