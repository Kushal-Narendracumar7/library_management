package library_management;
import java.sql.*; 

public class Login {
	public static boolean login(String username,String password) {
		String valid_password;
		String sql = "select password from users where name = ?";
		try(Connection conn = DatabaseUtility.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				valid_password = rs.getString("password");
				
				if(valid_password.equals(password)) {
					return true;
				}
				else {
					System.out.println("Invalid password..");
					return false;
				}
			}else {
				System.out.println("Invalid Username");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
