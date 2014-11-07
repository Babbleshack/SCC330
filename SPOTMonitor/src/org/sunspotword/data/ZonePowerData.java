
package org.sunspotword.data;

import java.util.Vector;

/**
 * holds zone TowerSpot Obj,
 * handles operations on TowerSpots
 * @author Dominic Lindsay
 */
public class ZonePowerData extends Vector
{
    private Vector towerSpots;
    private static final int STANDARD_CONFIG = 3;
    private static final int FALSE_VALUE = -66;
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    private int hasChanged = 0;
    
    public ZonePowerData()
    {
        super(STANDARD_CONFIG);
    }
    /**
     * adds the TowerSpot if it does not exist 
     * or updates the TowerSpot if it does.
     * @param tSpot 
     */
    public void AddOrUpdate(TowerSpot tSpot)
    {
        System.out.println("Adding: " + tSpot.getAddress() + 
                " Power Level: " + tSpot.getPowerLevel());
        int index = indexOf(tSpot);
        if(index < 0)
        {
            this.addElement((Object)tSpot);
            return;
        }
        ((TowerSpot)this.elementAt(index)).setPowerLevel(tSpot.getPowerLevel());
    }
    /**
     * overides Index returns index of towerSpot or -1
     * @param tSpot
     * @return int
     */
    public int indexOf(TowerSpot tSpot)
    {
        for(int i=0; i<towerSpots.size(); i++)
        {
            if(((TowerSpot)this.elementAt(i)).equals(tSpot))
                return i;
        }
        return -1;
    }
    /**
     * returns address of closest tower
     * @return String
     */
    public String closestTowerAddress()
    {
        this.hasChanged = FALSE;
        TowerSpot tSpot = new TowerSpot("test", FALSE_VALUE);
        System.out.println("ZPD size: " + this.size());
        for(int i=0; i<this.size(); i++)
        {
            System.out.println("test" + ((TowerSpot)this.elementAt(i)).getAddress() + 
                    " : " +((TowerSpot)this.elementAt(i)).getPowerLevel());
           if(((TowerSpot)this.elementAt(i)).getPowerLevel() >
                   tSpot.getPowerLevel());
           {
               this.hasChanged = TRUE;
               tSpot = (TowerSpot)this.elementAt(i);
           }
        }
        return tSpot.getAddress();
    }
    public int hasChanged(){return this.hasChanged;}

}
