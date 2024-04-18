package library_management;

import java.util.*;


public class library {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Who are you?");
		System.out.println("1. Admin.");
		System.out.println("2. Already a Customer.");
		System.out.println("3. New Customer.");
		
		int role = sc.nextInt();
		
		Option.selectRole(role);
        	
	}

}
