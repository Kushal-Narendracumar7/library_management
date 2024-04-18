package library_management;

import java.util.*;
import java.sql.*;

public class Registration {
	public static void addUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Name: ");
		String name = sc.nextLine();
	
		
		System.out.println("Enter your email: ");
		String email = sc.nextLine();

		System.out.println("Set password: ");
		String password = sc.nextLine();
	
		
		
		String sql = "insert into users(name,email,password) values(?,?,?)";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3,password);
			
			int rows = st.executeUpdate();
			
			if(rows != 0) {
				System.out.println("You have been registered as a new user.");
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while registration.Please try again.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
