import java.sql.*;
import java.util.HashMap;

public class ActuatorController {
	HashMap<Integer, Actuator> actuatorDevices = new HashMap<Integer, Actuator>();
	DB db = new DB();

	public ActuatorController() throws ClassNotFoundException, SQLException
	{
		db.connect();
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
}