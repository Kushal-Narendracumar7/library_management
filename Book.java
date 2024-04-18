package library_management;
import java.sql.*;

public class Book{
    public static void borrowBook(String name, String title) {
        String insertBorrow = "INSERT INTO borrow_book (user_id, book_id, borrow_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String fetchUser = "SELECT id FROM users WHERE name = ?";
        String fetchBook = "SELECT id, available FROM books WHERE title = ?";
        String updateAvailable = "UPDATE books SET available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtility.getConnection()) {
            conn.setAutoCommit(false);
            
            int user_id = 0;
            int book_id = 0;
            int available = 0;

            
            // Fetch user ID
            try (PreparedStatement userStmt = conn.prepareStatement(fetchUser)) {
                userStmt.setString(1, name);
                try (ResultSet rsUser = userStmt.executeQuery()) {
                    if (rsUser.next()) {
                        user_id = rsUser.getInt("id");
                    } else {
                        System.out.println("User not found!!");
                        return;
                    }
                }
            }
            
            // Fetch book details
            try (PreparedStatement bookStmt = conn.prepareStatement(fetchBook)) {
                bookStmt.setString(1, title);
                try (ResultSet rsBook = bookStmt.executeQuery()) {
                    if (rsBook.next()) {
                        book_id = rsBook.getInt("id");
                        available = rsBook.getInt("available");
                    } else {
                        System.out.println("Book not found!!");
                        return;
                    }
                }
            }
            
            // Process of borrowing
            if (available <= 0) {
                System.out.println("Book is out of stock!!");
                return;
            } else {
                try (PreparedStatement borrowStmt = conn.prepareStatement(insertBorrow)) {
                    borrowStmt.setInt(1, user_id);
                    borrowStmt.setInt(2, book_id);
                    
                    int rowsInserted = borrowStmt.executeUpdate();
                    if (rowsInserted != 0) {
                        System.out.println("Congratulations! The book has been borrowed.");
                     // Update availability
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateAvailable)) {
                            updateStmt.setInt(1, available - 1);
                            updateStmt.setInt(2, book_id);
                            
                            int rowsUpdated = updateStmt.executeUpdate();
                            if (rowsUpdated != 0) {
                                System.out.println("Availability updated successfully.");
                            } else {
                                System.out.println("Failed to update availability.");
                            }
                        }
                    } else {
                        System.out.println("Problem occurred while borrowing the book.");
                        return;
                    }
                }
                
                
            }
            
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void returnBook(String name,String title) {
    	String fetch_book = "select id,available from books where title = ?";
    	String fetch_user = "select id from users where name = ?";
    	String fetch_borrow = " select id from borrow_book where user_id = ? and book_id = ?";
    	String update_return = "update borrow_book set return_date = CURRENT_TIMESTAMP where user_id=? and book_id=?";
    	String update_availability = "update books set available = ? where id = ?";
    	
    	int book_id=0;
    	int user_id = 0;
    	int borrow_id =0;
    	int available = 0;
    	
    	try(Connection conn = DatabaseUtility.getConnection()){
    		conn.setAutoCommit(false);
    		
    		//fetching book id
    		try(PreparedStatement bookstmt = conn.prepareStatement(fetch_book)){
    			bookstmt.setString(1,title);
    			
    			ResultSet rsBook = bookstmt.executeQuery();
    			while(rsBook.next()) {
    				book_id =rsBook.getInt("id");
    				available = rsBook.getInt("available");
    			}
    			
    			if(book_id == 0) {
    				System.out.println("Book not found.");
    				return;
    			}
    		}
    		
    		//fetching user id
    		try(PreparedStatement userstmt = conn.prepareStatement(fetch_user)){
    			userstmt.setString(1, name);
    			ResultSet rsUser = userstmt.executeQuery();
    			while(rsUser.next()) {
    				user_id =rsUser.getInt("id");
    			}
    			
    			if(user_id == 0) {
    				System.out.println("User not found.");
    				return;
    			}
    		}
    		
			/*
			 * //fetching borrow id try(PreparedStatement borrowstmt =
			 * conn.prepareStatement(fetch_borrow)){ borrowstmt.setInt(1,user_id);
			 * borrowstmt.setInt(2,book_id); ResultSet rsBorrow = borrowstmt.executeQuery();
			 * 
			 * while(rsBorrow.next()) { borrow_id = rsBorrow.getInt("id"); }
			 * 
			 * if(borrow_id == 0) { System.out.println("Book not borrowed"); return; } }
			 */
    		
    		
    		//updating the return date 
    		try(PreparedStatement returnstmt = conn.prepareStatement(update_return)){
    			returnstmt .setInt(1,user_id);
    			returnstmt.setInt(2, book_id);
    			//returnstmt.setInt(3, borrow_id);
    			
    			int rows = returnstmt.executeUpdate();
    			if(rows != 0) {
    				System.out.println("Thanks for returning the book...");
    				
    				//updating the availability
    				try(PreparedStatement updatestmt = conn.prepareStatement(update_availability)){
    					updatestmt.setInt(1,available + 1);
    					updatestmt.setInt(2, book_id);
    					
    					int row = updatestmt.executeUpdate();
    					if(row != 0) {
    						System.out.println("Book availability has been incresed by 1...");
    					}
    				}
    			}else {
    				System.out.println("Problem occurred while returning the book..");
    				return;
    			}
    		}
    		conn.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    public static void viewBorrowBooks(String name) {
        String select_book  = "select * from borrow_book where user_id = ?";
        String fetch_name = "select id from users where name = ?";
        String fetch_book = "select title from books where id = ?";
        int user_id=0;
        int book_id = 0;
        String book_name = "";
        Timestamp borrow_date, return_date;

        System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+");
        System.out.println("|             Book Name                    |             Borrow Date                  |             Return Date                  |");
        System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+");

        try (Connection conn = DatabaseUtility.getConnection()){
            conn.setAutoCommit(false);

            // Fetch user ID
            try (PreparedStatement userstmt = conn.prepareStatement(fetch_name)) {
                userstmt.setString(1, name);
                ResultSet rs2 = userstmt.executeQuery();
                if (rs2.next()) {
                    user_id = rs2.getInt("id");
                } else {
                    System.out.println("User not found!!");
                    return;
                }
            }
            
            PreparedStatement borrowstmt = conn.prepareStatement(select_book);
            borrowstmt.setInt(1,user_id);
            ResultSet rs1 = borrowstmt.executeQuery();
            while(rs1.next()) {
                book_id = rs1.getInt("book_id");
                borrow_date = rs1.getTimestamp("borrow_date");
                return_date = rs1.getTimestamp("return_date");

                PreparedStatement bookstmt = conn.prepareStatement(fetch_book);
                bookstmt.setInt(1, book_id);
                ResultSet rs3 = bookstmt.executeQuery();
                while(rs3.next()) {
                    book_name = rs3.getString("title");
                }

                System.out.printf("| %-40s | %-40s | %-40s |\n", truncate(book_name, 40), borrow_date, return_date==null?"Not Returned" : return_date);
                System.out.println("+------------------------------------------+------------------------------------------+------------------------------------------+");
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    static String truncate(String str, int maxLength) {
	    if (str.length() > maxLength) {
	        return str.substring(0, maxLength - 3) + "...";
	    } else {
	        return str;
	    }
	}
    
    
    public static void main(String[] args) {
		viewBorrowBooks("Aatish");
	}
}
