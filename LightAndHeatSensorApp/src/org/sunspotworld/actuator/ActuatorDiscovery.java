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
    private static final long SAMPLE_RATE = SECOND/2;
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
           actuators = getActuators(); //get a list of active actuators
           if(actuators == null)
           {
               break; // nevermind.. no actuators
           }
           if(actuators.isEmpty())
               continue;
           for(Actuator a: actuators)
           {
               if(qm.isActuatorExists(a.getActuatorAddress()) == 0) //if the actuator does not exist add it
               {
                   qm.createActuatorRecord(a.getActuatorAddress(), System.currentTimeMillis());
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
                        a.turnRelayOn();
                        continue;
                    } else {
                        a.turnRelayOff();
                    }
               } else {
                    //get the job data
                    a.setJob(qm.getActuatorJob(a.getActuatorAddress()));
                    //if no job go to next actuator
                    if(a.getJob() == null)
                    {
                        continue;
                    }
                        /**
                     * Check against threshold
                     * if the reading is meets threshold turn actuator on
                     * else turn it off because threshold is not met
                     */
                    if(a.getJob().getDirection().compareTo("ABOVE") == 0) //reading is above
                    {
                        //check if reading is greater than threshold
                        if(a.getJob().getThreshold() <= 
                                qm.getLatestReadingFromJobId(a.getJob().getId()))
                        {
                            a.turnRelayOn();
                            continue;
                        }
                        a.turnRelayOff();
                    } else {
                        //check if reading is greater than threshold
                        if(a.getJob().getThreshold() >= 
                                qm.getLatestReadingFromJobId(a.getJob().getId()))
                        {
                            a.turnRelayOn();
                            continue;
                        }
                        a.turnRelayOff();
                    }
               }
               
               
           }//end of for actutators
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
