import java.net.SocketException;
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
}
