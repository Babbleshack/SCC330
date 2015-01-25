import java.sql.*;

public class DB {

	private Connection con = null;

	public void connect() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");

		String DB_server = "scc330-01.lancs.ac.uk";
		int DB_port = 33060;
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

	protected ResultSet execute(PreparedStatement preparedStatement) throws SQLException
	{
		return preparedStatement.executeQuery();
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
}
