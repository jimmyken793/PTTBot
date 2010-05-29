package jimmyken793.pttbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	protected Connection connection;
	protected Statement statement;

	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS FailedLoginLog (id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Announcement (id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT)");
	}

	public void addFailedLoginLog(String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO FailedLoginLog(content) VALUES (?);");
			prep.setString(1, content);
			prep.addBatch();
			connection.setAutoCommit(false);
			prep.executeBatch();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void addAnnouncement(String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO Announcement(content) VALUES (?);");
			prep.setString(1, content);
			prep.addBatch();
			connection.setAutoCommit(false);
			prep.executeBatch();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Database(String dbname) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbname + ".db");
		statement = connection.createStatement();
	}
}
