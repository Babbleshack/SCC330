import java.sql.SQLException;


public class ScheduledJob implements Runnable{

	ActuatorController actuatorController;

	public ScheduledJob(ActuatorController actuatorController) {
		this.actuatorController = actuatorController;
	}

	@Override
	public void run() {
		try {
			actuatorController.checkUpdatesForDevices();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
