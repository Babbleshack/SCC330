public class TActuatorJobFinder extends Thread
{
	ActuatorController actuatorController;
	private static final long TIME = 10000;

	public TActuatorJobFinder(ActuatorController actuatorController)
	{
		this.actuatorController = actuatorController;
	}

	public void run(){
		while(true)
		{
			actuatorController.getJobsOfActuators();
			try {
				sleep(TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}