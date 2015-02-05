public class TActuatorFinder extends Thread
{
	ActuatorController actuatorController;
	private static final long TIME = 10000;

	public TActuatorFinder(ActuatorController actuatorController)
	{
		this.actuatorController = actuatorController;
	}

	public void run(){
		while(true)
		{
			actuatorController.updateListOfActuators();
			try {
				sleep(TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}