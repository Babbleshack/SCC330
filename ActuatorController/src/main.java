import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ActuatorController actuatorController = new ActuatorController();
		ScheduledJob job = new ScheduledJob(actuatorController);
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(job, 1, 3, TimeUnit.SECONDS);
	}
}
