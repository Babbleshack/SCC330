import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.YoctoAPI.YRelay;

public class ActuatorController {
	HashMap<String, Actuator> actuators = new HashMap<String, Actuator>();
	DB db = new DB();

	public ActuatorController() throws ClassNotFoundException, SQLException
	{
		db.connect();
	}

	public void checkStatusOfActuators() throws SQLException, ClassNotFoundException
	{
		if(actuators != null)
			for(Entry<String, Actuator> a : actuators.entrySet())
			{
				/**
				* check if field is null
				* if null then auto mode, else check to see of actuator should
				* be on
				*/

				if(db.isActuatorNull(a.getKey()) == 0)
				{
					a.getValue().setStatus(db.isActuatorOn(a.getKey()));
				}
				else
				{
					// if no job is set then go to next actuator
					if(a.getValue().getJob() == null)
					{
						continue;
					}

					/**
					 * Check against threshold
					 * if the reading is meets threshold turn actuator on
					 * else turn it off because threshold is not met
					 */
					if(a.getValue().getJob().getDirection().compareTo("ABOVE") == 0) //reading is above
					{
						System.out.println("AUTO MODE: Checking against threshold");
						//check if reading is greater than threshold
						if(a.getValue().getJob().getThreshold() <=
								db.getLatestReadingFromJobId(a.getValue().getJob().getId()))
							a.getValue().setStatus(1);
						else
							a.getValue().setStatus(0);
					}
					else
					{
						//check if reading is greater than threshold
						System.out.println("AUTO MODE: Checking if reading is greater than threshold");
						if(a.getValue().getJob().getThreshold() >= db.getLatestReadingFromJobId(a.getValue().getJob().getId()))
							a.getValue().setStatus(1);
						else
							a.getValue().setStatus(0);
					}
				}
			}
	}

	public void getJobsOfActuators()
	{
		if(actuators != null)
			for(Entry<String, Actuator> a : actuators.entrySet())
				a.getValue().setJob(db.getActuatorJob(a.getKey()));
	}

	public void updateListOfActuators()
	{
		System.out.println("Detecting new actuators ...");

		HashMap<String, Actuator> actuator_temp = detectActuators();

		Iterator<Entry<String, Actuator>> iterator = actuators.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<String, Actuator> a = iterator.next();
			if(!actuator_temp.containsKey(a.getKey()))
			{
			  System.out.println("REMOVED: " + a.getKey());
			  actuators.remove(a.getKey());
			}
		}

		for(Entry<String, Actuator> a : actuator_temp.entrySet())
		{
			if(!actuators.containsKey(a.getKey()))
			{
				System.out.println("ADDED: " + a.getKey());
				actuators.put(a.getKey(), a.getValue());
			}
		}

		System.out.println(actuators.keySet());

		if(actuators != null)
			for(Entry<String, Actuator> a : actuators.entrySet())
				if(db.isActuatorExists(a.getValue().getActuatorAddress()) == 0) //if the actuator does not exist add it
					db.createActuatorRecord(a.getValue().getActuatorAddress(), System.currentTimeMillis());
	}

	private HashMap<String, Actuator> detectActuators()
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
		HashMap<String, Actuator> actuators = new HashMap<String, Actuator>();
		YRelay relay;
		String relayAddress;
		while(module != null)
		{
			try
			{
				if(module.get_productName().toLowerCase().contains("hub"))
				{
					module = module.nextModule();
					continue;
				}
				relayAddress = module.getSerialNumber() + ".relay1"; //get relay1
				relay = YRelay.FindRelay(relayAddress);
				actuators.put(relayAddress, new Actuator(relayAddress, relay));
			}
			catch (YAPI_Exception ex)
			{
				System.out.println("MODULE ERROR");
				ex.printStackTrace();
			}
			module = module.nextModule();
		}
		return actuators;
	}
}