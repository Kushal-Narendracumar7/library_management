package library_management;
import java.util.*;

public class Option {
    public static void selectRole(int role) {
        Scanner sc = new Scanner(System.in);
        if(role == 1) {
        	
        	String admin = "admin";
        	String admin_password = "admin@123";
        	
        	System.out.println("Enter username: ");
        	String username = sc.nextLine();
        	System.out.println("Enter Password: ");
        	String password = sc.nextLine();
        	
        	if(admin.equals(username) && admin_password.equals(password)) {
        	
        	
            int choice=0;
            while(choice!=9) {
                
                
                
                
                //Admin functionalities
                System.out.println("Operation: ");
                System.out.println("1. For Adding a Book ");
                System.out.println("2. For Removing a Book ");
                System.out.println("3. For Updating  Book Title ");
                System.out.println("4. For Updating  Book Author");
                System.out.println("5. For Updating  Book Price");
                System.out.println("6. For Updating  Book Availability");
                System.out.println("7. For Viewing Book Details");
                System.out.println("8. For Viewing Borrowed Books");
                System.out.println("9. For Exit");
                System.out.println("Enter your choice:");
                choice = sc.nextInt();
                sc.nextLine();
                switch(choice) {
                case 1:
                    System.out.println("Enter book title: ");
                    String book_name = sc.nextLine();
                    
                    System.out.println("Enter Author of the Book:");
                    String book_author = sc.nextLine();
                    System.out.println("Enter Edition of the Book:");
                    String book_edition = sc.nextLine();
                    System.out.println("Enter the price of Book: ");
                    int price = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter the availability of the Book: ");
                    int available = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    
                    Admin.addBook(book_name, book_author, book_edition, price, available);
                    break;
                case 2:
                    System.out.println("Enter the title of the book: ");
                    String book_name1 = sc.nextLine();
                    Admin.removeBook(book_name1);
                    break;
                case 3:
                    System.out.println("Enter the current title of the book");
                    String book_name2 = sc.nextLine();
                    System.out.println("Enter the new title of the book: ");
                    String book_new_title = sc.nextLine();
                    Admin.updateBookName(book_name2, book_new_title);
                    break;
                    
                case 4:
                    System.out.println("Enter the  title of the book");
                    String book_name3 = sc.nextLine();
                    System.out.println("Enter the new author of the book: ");
                    String book_new_author = sc.nextLine();
                    Admin.updateBookAuthor(book_name3, book_new_author);
                    break;
                    
                    
                case 5:
                    System.out.println("Enter the  title of the book");
                    String book_name4 = sc.nextLine();
                    System.out.println("Enter the new price of the book: ");
                    int book_new_price = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    Admin.updateBookPrice(book_name4, book_new_price);
                    break;
                    
                case 6:
                    System.out.println("Enter the  title of the book");
                    String book_name5 = sc.nextLine();
                    System.out.println("Enter the availability the book: ");
                    int book_new_availability = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    Admin.updateBookAvailability(book_name5, book_new_availability);
                    break;
                    
                    
                case 7:
                    Admin.viewBooks();
                    break;
                    
                case 8: Admin.viewBorrowBooks();
                	break;
                		
                    
                case 9:
                    System.out.println("Exiting the Program...");
                    break;
                    
                default:
                    System.out.println("Please make a valid choice");
                        
                }
            }
        	}else {
        		System.out.println("Invalid username or password!!!");
        		return;
        	}
        }
        else if(role == 2) {
            //Verify the login credentials and redirect to the the common customer page
        	System.out.println("Enter username: ");
        	String username = sc.nextLine();
        	System.out.println("Enter Password: ");
        	String password = sc.nextLine();
        	
        	if(Login.login(username, password)) {
        		int choice = 0;
        		while(choice!=5) {
        			 //User functionalities
                    System.out.println("Operation: ");
                    System.out.println("1. For Viewing Books ");
                    System.out.println("2. For Borrowing a Book ");
                    System.out.println("3. For Returning a Book  ");
                    System.out.println("4. For Viewing Borrowed Books ");
                    System.out.println("5. For Exit");
                    System.out.println("Enter your choice:");
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch(choice) {
                    case 1: Admin.viewBooks();
                    		break;
                    		
                    case 2: 
                    	System.out.println("Enter your Name: ");
                    	String name = sc.nextLine();
                    	System.out.println("Enter Book Name: ");
                    	String title = sc.nextLine();
                    	Book.borrowBook(name,title);
                    	break;
                    	
                    case 3:
                    	System.out.println("Enter your Name: ");
                    	String name1 = sc.nextLine();
                    	System.out.println("Enter Book Name: ");
                    	String title1 = sc.nextLine();
                    	Book.returnBook(name1,title1);
                    	break;   
                    	
                    case 4:
                    	System.out.println("Enter your name: ");
                    	String borrower = sc.nextLine();
                    	Book.viewBorrowBooks(borrower);
                    	break;
                    case 5: 
                    	System.out.println("Thanks for visiting the library..");
                    	break;
                    	
                    default:
                    	System.out.println("Invalid choice...");
                    	
                    }
                    
        		}
        	}
        	
        	
        }
        else if(role == 3) {
            Registration.addUser();
        }
        else {
            System.out.println("Not applicable role.. Please try again.");
        }
        
    }
}
