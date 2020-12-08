package cp317;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database manager class
 * 
 * @author Paris Ngow
 * @version 1.0
 * @since 2020-12-05
 */
public class DBManager {
	
	/**
	 * Establish connection to the database and return it.
	 * 
	 * @return conn
	 */
	public Connection getConnection() {
		try {
			//set up connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Plannero", "root", "cp317");
			
			//return connection to database
			return conn;
		} catch (ClassNotFoundException e) { //class error
			return null;
		} catch (SQLException e) {	//sql query error
			return null;
		}
		
	}
}
