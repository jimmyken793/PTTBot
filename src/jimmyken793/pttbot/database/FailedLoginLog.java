package jimmyken793.pttbot.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FailedLoginLog extends Database {

	public FailedLoginLog() throws SQLException, ClassNotFoundException {
		super("database");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS FailedLoginLog (id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT)");
	}

	public void addLog(String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO FailedLoginLog(content) VALUES (?);");
			prep.setString(1, content);
			prep.addBatch();
			connection.setAutoCommit(false);
			prep.executeBatch();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
