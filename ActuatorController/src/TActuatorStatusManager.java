import java.sql.SQLException;

public class TActuatorStatusManager extends Thread
{
	ActuatorController actuatorController;
	private static final long TIME = 2000;

	public TActuatorStatusManager(ActuatorController actuatorController)
	{
		this.actuatorController = actuatorController;
	}

	public void run(){
		while(true)
		{
			try {
				actuatorController.checkStatusOfActuators();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				sleep(TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}