/*
 * Thread for receiving acceleration data.
 * Dominic Lindsay
 */

package org.sunspotworld;

import java.text.SimpleDateFormat;
import org.sunspotworld.Collections.ArrayList;
import org.sunspotworld.DB.QueryManager;
import org.sunspotworld.DataTypes.LightData;
import org.sunspotworld.DataTypes.ThermoData;

public class TGeneral implements Runnable
{
    // creates an instance of SunSpotHostApplication class and initialises
    // instance variables
    public TGeneral()
    {
    }

    public void startPolling() throws Exception
    {
    }

    public void run()
    {
       try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("com.mysql.jdbc.Driver could not be instantiated");
        }
        QueryManager qm = new QueryManager();
        //ArrayList heatData = qm.getPastWeekThermo();
        ArrayList lightData = qm.getPastWeekLight();
        ArrayList heatData = qm.getPastWeekThermo();
        SimpleDateFormat dateFormatter =
        new SimpleDateFormat("E dd/MM/yyyy'@'hh:mm:ss a zzz");
        LightData light;
        System.out.println("----------------------------------");
        System.out.println("Last Light Readings");
        for(int i = 0; i<lightData.size();i++)
        {
            light = (LightData) lightData.get(i);
            System.out.println("----------------------------------");
            System.out.println("Heat Data: \n"
            + "\t" + "Address: " + light.getSpotAddress() + "\n"
            + "\t" + "Lumen Value: " + light.getLightData() + "\n"
            + "\t" + "Time: "
            + dateFormatter.format(light.getTime()) + "\n"
            + "\t" + "ZoneID: " + light.getZoneId() + "\n");
            System.out.println("----------------------------------");
        }
        ThermoData data;
        System.out.println("----------------------------------");
        System.out.println("Last Thermonitor Readings");
        for(int i = 0; i<heatData.size();i++)
        {
            data =(ThermoData)heatData.get(i);
            System.out.println("----------------------------------");
            System.out.println("Heat Data: \n"
            + "\t" + "Address: " + data.getSpotAddress() + "\n"
            + "\t" + "Celcius Value: " + data.getCelciusData() + "\n"
            + "\t" + "Time: "
            + dateFormatter.format(data.getTime()) + "\n"
            + "\t" + "ZoneID: " + data.getZoneId() + "\n");
            System.out.println("----------------------------------");
        }
    }
}