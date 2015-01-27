import java.sql.*;
import java.util.HashMap;

public class ActuatorController {
	HashMap<Integer, Actuator> actuatorDevices = new HashMap<Integer, Actuator>();
	DB db = new DB();

	//creates ActuatorTest class and calls test method
	public ActuatorController() throws ClassNotFoundException, SQLException
	{
		db.connect();
//		checkUpdatesForDevices();
	}

	public void checkUpdatesForDevices() throws SQLException, ClassNotFoundException
	{
			ResultSet result = db.getActuatorsWithStatus();

			while (result.next())
			{
				int id = result.getInt("id");
				boolean isOn = result.getBoolean("is_on");

				if(!actuatorDevices.containsKey(id))
				{
					actuatorDevices.put(id, new Actuator(result.getString("actuator_address")));
				}

				(actuatorDevices.get(id)).setStatus(isOn);
			}
			System.out.println(actuatorDevices.keySet());
	}
//	 private void checkIfStatusUpdated(int id, Boolean is_on)
	// {
	// 	Boolean is_on_local = actuatorDeviceStatus.get(id);
	// 	System.out.println(id + " db:" + is_on + " local:" + is_on_local);
	// 	if(is_on_local != is_on)
	// 	{
	// 		if(is_on)
	// 		{
	// 			turnON(id);
	// 		}
	// 		else
	// 		{
	// 			turnOFF(id);
	// 		}
	// 		actuatorDeviceStatus.put(id, is_on);
	// 	}
	// }
	//calls "turnLightON or turnLightOFF" method on actuator
	// public void turnON(int id)
	// {
	// 	System.out.println("TURNING ON!!! id: " + id);
	// 	actuator.setRelays(actuatorDevices.get(id));
	// 	actuator.turnLightON();
	// }
	// public void turnOFF(int id)
	// {
	// 	System.out.println("TURNING OFF!!! id: " + id);
	// 	actuator.setRelays(actuatorDevices.get(id));
	// 	actuator.turnLightOFF();
	// }
}