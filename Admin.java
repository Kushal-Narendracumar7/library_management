package library_management;

import java.sql.*;


public class Admin {
	
	public static void addBook(String title,String author,String edition,int price, int available) {
		String sql = "insert into books (title,author,edition,price,available) values(?,?,?,?,?)";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn = DatabaseUtility.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,title);
			statement.setString(2,author);
			statement.setString(3,edition);
			statement.setInt(4,price);
			statement.setInt(5, available);
			
			int rows =statement.executeUpdate();
			if(rows!=0) {
				System.out.println("A new book has been added!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem occurred while adding the book.Please try again.");
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void removeBook(String title) {
		String sql = "delete from books where title = ? ";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,title);
			int rows = st.executeUpdate();
			if(rows!=0) {
				System.out.println("Book has been removed from library..");
			}else {
				System.out.println("Book not found!! Please try again");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while adding the book.Please try again.");
			e.printStackTrace();
		}
		
	}
	
	public static void updateBookName(String oldTitle,String newTitle) {
		String sql = "update books set title = ? where title = ?";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,newTitle);
			st.setString(2, oldTitle);
			int rows = st.executeUpdate();
			if(rows!=0) {
				System.out.println("Book title has been updated from library..");
			} 
			else {
				System.out.println("Book not found!! Please try again");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating the title.");
			e.printStackTrace();
		}
	}
	
	public static void updateBookAuthor(String title,String newAuthor) {
		String sql = "update books set author = ? where title = ?";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,newAuthor);
			st.setString(2, title);
			int rows = st.executeUpdate();
			if(rows!=0) {
				System.out.println("Book Author has been updated from library..");
			} else {
				System.out.println("Book not found!! Please try again");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating the price.");
			e.printStackTrace();
		}
	}
	
	
	public static void updateBookPrice(String title,int newPrice) {
		String sql = "update books set price = ? where title = ?";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,newPrice);
			st.setString(2, title);
			int rows = st.executeUpdate();
			if(rows!=0) {
				System.out.println("Book price has been updated from library..");
			} 
			else {
				System.out.println("Book not found!! Please try again");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating the price.");
			e.printStackTrace();
		}
	}
	
	
	public static void updateBookAvailability(String title,int available) {
		String sql = "update books set available = ? where title = ?";
		
		try {
			Connection conn = DatabaseUtility.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,available);
			st.setString(2, title);
			int rows = st.executeUpdate();
			if(rows!=0) {
				System.out.println("Book availability has been updated from library..");
			}
			else {
				System.out.println("Book not found!! Please try again");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Problem occurred while updating the availability.");
			e.printStackTrace();
		}
	}
	
	
	public static void viewBooks() {
	    String sql = "select title,author,edition,price,available from books";
	    System.out.println("+------------------------------------------+------------------------------------------+------------+------------+----------+");
	    System.out.println("|                   Title                  |                  Author                  |  Edition   | Available  |  Price   |");
	    System.out.println("+------------------------------------------+------------------------------------------+------------+------------+----------+");

	    try {
	        Connection conn = DatabaseUtility.getConnection();

	        Statement st = conn.createStatement();
	        ResultSet res = st.executeQuery(sql);
	        while (res.next()) {
	            String title = res.getString("title");
	            String author = res.getString("author");
	            String edition = res.getString("edition");
	            int price = res.getInt("price");
	            int available = res.getInt("available");

	            System.out.printf("| %-40s | %-40s | %-10s | %10s | %8s |%n", Book.truncate(title, 40), Book.truncate(author, 40), Book.truncate(edition, 10), available, price);
	        }
	        System.out.println("+------------------------------------------+------------------------------------------+------------+------------+----------+");
	    } catch (SQLException e) {
	        System.out.println("No books found..");
	        e.printStackTrace();
	    }
	}
		
	public static void viewBorrowBooks() {
	    String select_book  = "select * from borrow_book";
	    String fetch_name = "select name from users where id = ?";
	    String fetch_book = "select title from books where id = ?";
	    int user_id=0;
	    int book_id = 0;
	    String username = "";
	    String book_name = "";
	    Timestamp borrow_date, return_date;

	    System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+------------------------------------------+");
	    System.out.println("|             Book Name                    |             Borrower                     |             Borrow Date                  |             Return Date                  |");
	    System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+------------------------------------------+");

	    try (Connection conn = DatabaseUtility.getConnection()){
	        conn.setAutoCommit(false);
	        PreparedStatement borrowstmt = conn.prepareStatement(select_book);
	        ResultSet rs1 = borrowstmt.executeQuery();
	        while(rs1.next()) {
	            user_id = rs1.getInt("user_id");
	            book_id = rs1.getInt("book_id");
	            borrow_date = rs1.getTimestamp("borrow_date");
	            return_date = rs1.getTimestamp("return_date");

	            
	            
	            PreparedStatement userstmt = conn.prepareStatement(fetch_name);
	            userstmt.setInt(1, user_id);
	            ResultSet rs2 = userstmt.executeQuery();
	            while(rs2.next()) {
	                username = rs2.getString("name");
	            }

	            PreparedStatement bookstmt = conn.prepareStatement(fetch_book);
	            bookstmt.setInt(1, book_id);
	            ResultSet rs3 = bookstmt.executeQuery();
	            while(rs3.next()) {
	                book_name = rs3.getString("title");
	            }

	            System.out.printf("| %-40s | %-40s | %-40s | %-40s |\n", Book.truncate(book_name, 40), Book.truncate(username, 40), borrow_date, return_date==null?"Not Returned" : return_date);
	            System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+------------------------------------------+");
	            conn.commit();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	

	
	
}
