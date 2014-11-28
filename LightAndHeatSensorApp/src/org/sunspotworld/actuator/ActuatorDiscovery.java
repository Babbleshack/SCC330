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
import org.sunspotworld.database.QueryManager;

public class ActuatorDiscovery implements Runnable
{
    private static final long SECOND = 1000;
    private static final long SAMPLE_RATE = SECOND;
    private static final String HUB_ADDRESS = "127.0.0.1:4444";
    private QueryManager qm;
    public ActuatorDiscovery() {
       try {
            YAPI.RegisterHub(HUB_ADDRESS);//YAPI.RegisterHub(HUB_ADDRESS);
        } catch (YAPI_Exception ex) {
            System.err.println("Error instantiating hub.");
            ex.printStackTrace();
        } 
       qm = new QueryManager();
    }

    public void run() {
        ArrayList<Actuator> actuators;
        while(true)
        {
            System.out.println("========================================");
           actuators = getActuators(); //get a list of active actuators
           if(actuators == null)
           {
               continue; //skip no attached actuators.
           }
           System.out.println("Actuator list size: " + actuators.size());
           if(actuators.isEmpty())
               continue;
           for(Actuator a: actuators)
           {
               System.out.println(a.getActuatorAddress());
               if(qm.isActuatorExists(a.getActuatorAddress()) == 0) //if the actuator does not exist add it
               {
                   qm.createActuatorRecord(a.getActuatorAddress(), System.currentTimeMillis());
                   System.out.println("Added Actuator: " + a.getActuatorAddress());
               }
               
               /**
                * chekck if field is null
                * if null then auto mode, else check to see of acutator should
                * be on
                */
               if(qm.isActuatorNull(a.getActuatorAddress()) == 0)
               {
                    if(qm.isActuatorOn(a.getActuatorAddress()) == 1/*true*/ ) //if status is on and relay is on skip
                    {
                        System.out.println("Actuator is set to On");
                        if(a.isSwitchedOn() == 0) //if relay is off switch it on
                        {
                            a.turnRelayOn();
                        }
                        continue;
                    } else {
                        System.out.println("Actuator is set to OFF");
                        if(a.isSwitchedOn() == 1) //if relay is ON switch it OFF
                        {
                            a.turnRelayOff();
                        }
                    }
               } else {
                    //get the job data
                    a.setJob(qm.getActuatorJob(a.getActuatorAddress()));
                    //if no job go to next actuator
                    if(a.getJob() == null)
                    {
                        System.out.println("No actuator job skipping...");
                        continue;
                    }
                        /**
                     * Check against threshold
                     * if the reading is meets threshold turn actuator on
                     * else turn it off because threshold is not met
                     */
                        System.out.println(qm.getLatestReadingFromJobId(a.getJob().getId()));
                    if(a.getJob().getDirection().compareTo("ABOVE") == 0) //reading is above
                    {
                        System.out.println("Checking if reading is ABOVE thresh");
                        //check if reading is greater than threshold
                        if(a.getJob().getThreshold() <= 
                                qm.getLatestReadingFromJobId(a.getJob().getId()))
                        {
                            System.out.println("Actuator thresh met switching on...");
                            a.turnRelayOn();
                            continue;
                        }
                        a.turnRelayOff();
                    } else {
                        //check if reading is greater than threshold
                        System.out.println("Checking if reading is BELOW thresh");
                        if(a.getJob().getThreshold() >= 
                                qm.getLatestReadingFromJobId(a.getJob().getId()))
                        {
                            System.out.println("Actuator thresh met switching on...");
                            a.turnRelayOn();
                            continue;
                        }
                        a.turnRelayOff();
                    }
               }
               
               
           }//end of for actutators
           System.out.println("========================================");
           Utils.sleep(SAMPLE_RATE);
        }
    }
    private ArrayList<Actuator> getActuators()
    {
        /**
         * TO FIND RELAYS
         * FIRST FIND THE MODULE NAME AND ADD .relay1
         * 
         */
        YModule module = YModule.FirstModule(); //get first module
        if(module == null) //catch non existent modules
        {
            System.err.println("NO MODULES FOUND");
            return null; //no modules return null
        }
        ArrayList<Actuator> actuators = new ArrayList<Actuator>(); 
        YRelay relay;
        String relayAddress;
        while(module != null)
        {
            try {
                if(module.get_productName().toLowerCase().contains("hub"))
                {
                    module = module.nextModule();
                    continue;
                }
                relayAddress = module.getSerialNumber() + ".relay1"; //get relay1
                relay = YRelay.FindRelay(relayAddress);
                actuators.add(new Actuator(relayAddress,relay));
            } catch (YAPI_Exception ex) {
                System.out.println("MODULE ERROR");
                ex.printStackTrace();
            }
            module = module.nextModule();
        }
        return actuators;
    }
}
