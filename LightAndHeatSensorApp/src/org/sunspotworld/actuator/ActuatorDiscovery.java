/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.actuator;

import com.sun.spot.util.Utils;
import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.YoctoAPI.YRelay;
import java.util.ArrayList;

public class ActuatorDiscovery implements Runnable
{

    private static final String HUB_ADDRESS = "127.0.0.1:4444";
    public ActuatorDiscovery() {
       try {
            YAPI.RegisterHub(HUB_ADDRESS);
            //YAPI.RegisterHub(HUB_ADDRESS);
        } catch (YAPI_Exception ex) {
            System.err.println("Error instantiating hub.");
            ex.printStackTrace();
        } 
    }
    
  /*  public void doTask() throws Exception {
        
           // System.out.println(relay.describe());
        
        /**
         * get list of active of active relays
         * 
         * pass list to QM method
         * 
         * returns list with ActuatorJob, field assigned
         * 
         * check thresholds and change actuator status
         */
   // }
    public void run() {
        ArrayList<Actuator> actuators;
        while(true)
        {
           actuators = getActuators();
           System.out.println("Actuator list size: " + actuators.size());
           for(Actuator a: actuators)
               System.out.println(a.getActuatorAddress());
           
           
        }
    }
    private ArrayList<Actuator> getActuators()
    {
        System.out.println("================");
        /**
         * TO FIND RELAYS
         * FIRST FIND THE MODULE NAME AND ADD .relay1
         * 
         */
        YModule module = YModule.FirstModule(); //get first module
        if(module == null) //catch non existent modules
        {
            System.err.println("NO MODULES FOUND");
        }
        ArrayList<Actuator> actuators = new ArrayList<Actuator>(); 
        YRelay relay;
        String relayAddress;
        System.out.println("MODULE LIST");
        while(module != null)
        {
            try {
                if(module.get_productName().toLowerCase().contains("hub"))
                {
                    System.out.println("Found Hub " + module.describe());
                    module = module.nextModule();
                    continue;
                }
                relayAddress = module.getSerialNumber() + ".relay1"; //get relay1
                relay = YRelay.FindRelay(relayAddress);
                System.out.println("ADDING: " + relayAddress + "to array list");
                actuators.add(new Actuator(relayAddress,relay));

            } catch (YAPI_Exception ex) {
                System.out.println("MODULE ERROR");
                ex.printStackTrace();
            }
            module = module.nextModule();
        }
        System.out.println("================");
        System.out.println("NO MORE RELAYS returning: " + actuators.size());
        return actuators;
    }

    /**
     * Tower Handler
     * Spot Handler
     */

}
