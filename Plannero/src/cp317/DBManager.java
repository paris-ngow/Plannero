package cp317;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Plannero", "root", "cp317");
			
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
}
