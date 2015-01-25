import java.sql.*;
import java.util.HashMap;

public class ActuatorController {
	HashMap<Integer, String> actuatorDevices = new HashMap<Integer, String>();
	HashMap<Integer, Boolean> actuatorDeviceStatus = new HashMap<Integer, Boolean>();
	Actuator actuator = new Actuator();
	DB db = new DB();

	//creates ActuatorTest class and calls test method
	public ActuatorController() throws ClassNotFoundException, SQLException
	{
		db.connect();
//		checkUpdatesForDevices();
	}

	public void checkUpdatesForDevices() throws SQLException
	{

		PreparedStatement preparedStatement = db.preStatementCreate("SELECT id, actuator_address, is_on FROM Actuator");
//		preparedStatement_checkBids.setInt(1, this.id);
		ResultSet result = db.execute(preparedStatement);

		while (result.next())
		{
			actuatorDevices.put(result.getInt("id"), result.getString("actuator_address"));
			checkIfStatusUpdated(result.getInt("id"), result.getBoolean("is_on"));
		}
		System.out.println(actuatorDevices);
	}

	private void checkIfStatusUpdated(int id, Boolean is_on)
	{
		Boolean is_on_local = actuatorDeviceStatus.get(id);
		System.out.println(id + " db:" + is_on + " local:" + is_on_local);
		if(is_on_local != is_on)
		{
			if(is_on)
			{
				turnON(id);
			}
			else
			{
				turnOFF(id);
			}
			actuatorDeviceStatus.put(id, is_on);			
		}
	}
	//calls "turnLightON or turnLightOFF" method on actuator
	public void turnON(int id)
	{
		System.out.println("TURNING ON!!! id: " + id);
		actuator.setRelays(actuatorDevices.get(id));
		actuator.turnLightON();
	}
	public void turnOFF(int id)
	{
		System.out.println("TURNING OFF!!! id: " + id);
		actuator.setRelays(actuatorDevices.get(id));
		actuator.turnLightOFF();
	}
}