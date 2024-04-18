package library_management;
import java.sql.*;
public class DatabaseUtility {
		
	private static final String USERNAME = "root";
	private static final String PASSWORD = "12345";
	private static final String DATABASEURL="jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false";
	
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASEURL,USERNAME,PASSWORD);
	}
}
