package idv.jimmyken793.pttbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS EnterBoards (id INTEGER PRIMARY KEY AUTOINCREMENT,board TEXT,content TEXT)");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Board (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT UNIQUE,title TEXT)");
	}

	public void addFailedLoginLog(String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO FailedLoginLog(content) VALUES (?);");
			prep.setString(1, content);
			prep.addBatch();
			prep.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addAnnouncement(String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO Announcement(content) VALUES (?);");
			prep.setString(1, content);
			prep.addBatch();
			prep.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void addEnterBoard(String board,String content) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO EnterBoards(board,content) VALUES (?,?);");
			prep.setString(1, board);
			prep.setString(2, content);
			prep.addBatch();
			prep.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addBoard(String name, String title) {
		try {
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT COUNT(*) AS rowcount FROM Board WHERE name=\"" + name + "\";");
			if (rs.getInt("rowcount") == 0) {
				stat.execute("INSERT INTO Board(name,title) VALUES (\""+name.replaceAll("\"", "\\\"")+"\",\""+title.replaceAll("\"", "\\\"")+"\");");
			}else{
				//System.out.println("Duplicated board:"+name);
			}
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
