import java.sql.*;

public class DB {

	private Connection con = null;

	public void connect() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");

		String DB_server = "10.42.72.24";
		int DB_port = 3306;
		String DB_name = "testing";
		String DB_login = "babbleshack";
		String DB_password = "testing";

		String DB_url = "jdbc:mysql://" + DB_server + ":" + DB_port + "/" + DB_name;

		con = DriverManager.getConnection(DB_url, DB_login, DB_password);
	}

	protected PreparedStatement preStatementCreate(String statement) throws SQLException
	{
		return con.prepareStatement(statement);
	}

	protected ResultSet execute(PreparedStatement preparedStatement) throws SQLException, ClassNotFoundException
	{
		ResultSet result = null;
		while(result == null)
		{
			try
			{
				return preparedStatement.executeQuery();
			}
			catch(Exception e)
			{
				System.out.println("SocketException ERROR DETECTED!");
				System.out.println("Trying to reconnect to the DB ...");
				connect();
			}
		}
		return result;
	}

	protected ResultSet execute(String query) throws SQLException
	{
		Statement statement = con.createStatement();
		return statement.executeQuery(query);
	}

	protected ResultSet upsert(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.executeUpdate();
		return preparedStatement.getGeneratedKeys();
	}

	protected int returnID(ResultSet result) throws SQLException
	{
		int id = 0;
		ResultSetMetaData rsmd = result.getMetaData();
		String column_name = rsmd.getColumnName(1);

		while (result.next()) {
			id = result.getInt(column_name);
		}

		return id;
	}

	protected ResultSet getActuatorsWithStatus() throws SQLException, ClassNotFoundException
	{
		// PreparedStatement preparedStatement = preStatementCreate("SELECT id, actuator_address, is_on FROM Actuator");
//		preparedStatement_checkBids.setInt(1, this.id);
		return execute(preStatementCreate("SELECT id, actuator_address, is_on FROM Actuator"));
	}

	/**
	 * Insert spot record into db
	 * @param actuator_address
	 * @param time long
	 */
	public void createActuatorRecord(String actuator_address, long time)
	{
		String insertActuatorRecord = "INSERT INTO Actuator"
				+ "(actuator_address, created_at, updated_at)"
				+ ("VALUES (?,?,?)");
		try
		{
			PreparedStatement insert = con.prepareStatement(insertActuatorRecord);
			insert.setString(1, actuator_address);
			insert.setTimestamp(2, new Timestamp(time));
			insert.setTimestamp(3, new Timestamp(time));
			insert.executeUpdate();
		}
		catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing"
				+ "insertActuatorRecord: " + e);
		}
	}

	/**
	 * returns true if actuator is null
	 * @param actuator_address
	 * @return
	 */
	public int isActuatorNull(String actuator_address)
	{
		try
		{
			String isActuatorOn = "SELECT * FROM Actuator WHERE actuator_address LIKE ?";
			int returnedInt;

			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(isActuatorOn);
			record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

			/**
			 * Access ResultSet for actuator_address
			 */
			ResultSet result = record.executeQuery();

			/**
			 * Return result
			 */
			result.next();
			returnedInt = result.getInt("is_on");
			if(result.wasNull())
				return 1;
			else
				return 0;
		}
		catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing "
				+ "isActuatorOn: " + e);
				return 0;
		}
	}

	public int isActuatorOn(String actuator_address)
	{
		String isActuatorOn = "SELECT * FROM Actuator WHERE actuator_address LIKE ?";

		try
		{
			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(isActuatorOn);
			record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

			/**
			 * Access ResultSet for actuator_address
			 */
			ResultSet result = record.executeQuery();

			/**
			 * Return result
			 */
			if(result.next())
			{
				if(result.getInt("is_on") == 1)
					return 1;
				else
					return 0;
			}
			else
			{
				return 0;
			}
		}
		catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing "
				+ "isActuatorOn: " + e);
				return 0;
		}
	}

	public ActuatorJob getActuatorJob(String actuator_address)
	{
		try
		{
			String getActuator = "SELECT * "
					+ " FROM actuator_job, Actuator"
					+ " WHERE actuator_job.actuator_id = Actuator.id "
					+ " AND Actuator.actuator_address LIKE ? "
					+ " LIMIT 1";

			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(getActuator);
			record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

			/**
			 * Access ResultSet for zone_id
			 */
			ResultSet result = record.executeQuery();
			if(result.next())
			{
				/**
				 * Return result
				 */
				return new ActuatorJob(result.getInt("actuator_job.job_id"), result.getString("actuator_job.direction"), result.getDouble("actuator_job.threshold"));
			}
			else
			{
				return null;
			}

		}
		catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing "
				+ "getActuator: " + e);
				return null;
		}
	}

	public double getLatestReadingFromJobId(int job_id)
	{
		String reading_table = this.getReadingTableFromJobId(job_id);
		String reading_field = this.getReadingFieldFromJobId(job_id);

		if(reading_table == null || reading_field == null) return -1;

		String getReading = "SELECT * "
				+ " FROM " + reading_table
				+ " WHERE job_id = ? "
				+ " ORDER BY id DESC "
				+ " LIMIT 1";

		try {
			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(getReading);
			record.setInt(1, job_id);

			/**
			 * Access ResultSet for zone_id
			 */
			ResultSet result = record.executeQuery();
			if(result.next())
			{
				/**
				 * Return result
				 */
				return result.getDouble(reading_table + "." + reading_field);
			}
			else
			{
				return -1;
			}

		} catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing "
				+ "getReading: " + e);
				return -1;
		}
	}

	public String getReadingTableFromJobId(int job_id) {
		String getReadingTableFromJobId = "SELECT * "
				+ " FROM Job, Sensor"
				+ " WHERE Sensor.id = Job.sensor_id "
				+ " AND Job.id = ? "
				+ " LIMIT 1";

		try {
			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(getReadingTableFromJobId);
			record.setInt(1, job_id);

			/**
			 * Access ResultSet for zone_id
			 */
			ResultSet result = record.executeQuery();
			if(result.next()) {
				/**
				 * Return result
				 */
				return result.getString("Sensor.table");
			} else {
				return null;
			}

		} catch (SQLException e) {
				System.err.println("SQL Exception while preparing/Executing "
				+ "getReadingTableFromJobId: " + e);
				return null;
		}
	}

	public String getReadingFieldFromJobId(int job_id) {
		String getReadingTableFromJobId = "SELECT * "
				+ " FROM Job, Sensor"
				+ " WHERE Sensor.id = Job.sensor_id "
				+ " AND Job.id = ? "
				+ " LIMIT 1";

		try {
			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(getReadingTableFromJobId);
			record.setInt(1, job_id);

			/**
			 * Access ResultSet for zone_id
			 */
			ResultSet result = record.executeQuery();
			if(result.next()) {
				/**
				 * Return result
				 */
				return result.getString("Sensor.field");
			} else {
				return null;
			}

		} catch (SQLException e) {
				System.err.println("SQL Exception while preparing/Executing "
				+ "getReadingTableFromJobId: " + e);
				return null;
		}
	}

	/**
	 *
	 * @param actuator_address
	 * @return
	 */
	public int isActuatorExists(String actuator_address)
	{
		try
		{
			String isActuatorExists = "SELECT * FROM Actuator WHERE Actuator.actuator_address LIKE ? ";

			/**
			 * Execute select query
			 */
			PreparedStatement record = con.prepareStatement(isActuatorExists);
			record.setString(1, "%" + actuator_address.replace(".relay1", "") + "%");

			/**
			 * Access ResultSet for actuator_address
			 */
			ResultSet result = record.executeQuery();

			/**
			 * Return result
			 */
			if(result.next())
				return 1;
			else
				return 0;

		}
		catch (SQLException e)
		{
				System.err.println("SQL Exception while preparing/Executing "
				+ "isActuatorExists: " + e);
				return 0;
		}
	}
}
