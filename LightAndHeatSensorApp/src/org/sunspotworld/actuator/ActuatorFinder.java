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

public class ActuatorFinder implements Runnable
{

    private static final String HUB_ADDRESS = "127.0.0.1:4444";
    public ActuatorFinder() {
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
    private ArrayList<Actuator> getActiveRelays()
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
        ArrayList<Actuator> relays = new ArrayList<Actuator>(); 
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
                System.out.println(module.get_serialNumber() + " (" + module.get_productName() + ")");
                relayAddress = module.getSerialNumber() + ".relay1";
                relay = YRelay.FindRelay(relayAddress);
                relays.add(new Actuator(relay, relayAddress));

            } catch (YAPI_Exception ex) {
                System.out.println("MODULE ERROR");
                ex.printStackTrace();
            }
            module = module.nextModule();
        }
        System.out.println("================");
        System.out.println("NO MORE RELAYS");
        return null;
    }

    public void run() {
        while(true)
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
            ArrayList<Actuator> relays = new ArrayList<Actuator>(); 
            YRelay relay;
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
                    System.out.println(module.get_serialNumber() + " (" + module.get_productName() + ")");
                    relay = YRelay.FindRelay(module.getSerialNumber() + ".relay1");
                    System.out.println(module.getSerialNumber() + ".relay1");
                    relay.setState(1);
                    Utils.sleep(150);
                    relay.setState(0);

                } catch (YAPI_Exception ex) {
                    System.out.println("MODULE ERROR");
                    ex.printStackTrace();
                }
                module = module.nextModule();
            }
            System.out.println("================");
            System.out.println("NO MORE RELAYS");
        }
    }
    /**
     * Tower Handler
     * Spot Handler
     */

}
