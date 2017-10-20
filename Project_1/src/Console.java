import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
	Project Driver
*/

import java.util.Scanner;

public class Console
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// load the current inventory and user accounts
		Database myDatabase = new Database();
		
		// copy the inventory from the database
		ArrayList<Item> currentInventory = new ArrayList<Item>(myDatabase.copyInventory());
		
		// Scanner object to read keyboard input
		Scanner keyboard = new Scanner(System.in);
		
		// to hold a user's menu selection choices
		int choice;
		
		// to hold a UserAccount
		UserAccount myAccount;
		
		// have the user login or register
		do
		{
			choice = getloginMenuChoice(keyboard);
		
			if (choice == 1)
				myAccount = login(myDatabase, keyboard);
			else
				myAccount = register(myDatabase, keyboard);
			
		} while (myAccount == null);
		
		// display welcome message
		System.out.println("Welcome " + myAccount.getFirstName() + "\n");
		
		// allow the user to browse the inventory
		do
		{
			// display the items main menu
			choice = getItemMenuChoice(keyboard);
		
			// display the items sub-menu
			switch(choice)
			{
				case 1:
					displayFurniture(currentInventory);
					break;
					
				case 2:
					//displayRugs(currentInventory);
					break;
				
				case 3:
					//displayDecor(currentInventory);
					break;
					
				case 4:
					//displayBedAndBath(currentInventory);
					break;
					
				case 5:
					//displayHomeImprovement(currentInventory);
					break;
					
				case 6:
					//displayKitchen(currentInventory);
					break;
					
				case 7:
					//displayOutdoor(currentInventory);
					break;
					
				case 8:
					// some method
					break;
			}
		} while (choice != 9);
		
		// update the UserAccount file
		myDatabase.writeCurrentUserAccounts();
		
		// Update the Inventory file
		myDatabase.writeCurrentInventory(currentInventory);
		
		//TODO: Some method to save the state of a user's shopping cart
		
		System.exit(0);
	}
	
	/**
	 * The loginMenu method prompts the user
	 * to login or register and returns the
	 * users choice.
	 * @return The users login or register choice
	 */
	public static int getloginMenuChoice(Scanner keyboard)
	{
		int choice; // to hold the users choice

		// get the users choice
		do
		{
			System.out.println("Welcome to Overstock.com");
			System.out.println();
			System.out.println("1: Login");
			System.out.println("2: Register");
			
			choice = keyboard.nextInt();
			keyboard.nextLine(); // discard newline character in string buffer
			
			if (choice < 1 || choice > 2)
				System.out.println("ERROR: Invalid Choice. Please try again.\n\n");
			
		} while (choice < 1 || choice > 2);

		return choice;
	}
	
	public static UserAccount login(Database myDatabase, Scanner keyboard)
	{
		// variables needed to login
		String email, password;
		
		// to hold a UserAccount
		UserAccount myAccount;
		
		// get the users email and password
		System.out.println("Enter the Email Address for your account: ");
		email = keyboard.nextLine();
		
		System.out.println("Enter the password for your account: ");
		password = keyboard.nextLine();
		
		// search for the account in the database
		int index = myDatabase.accountExistsAt(email, password);
		
		if (index == -1)
		{
			System.out.println("Your credentials do not match our database.\nPlease try again.\n\n");
			myAccount = null;
		}
		else
			myAccount = new UserAccount(myDatabase.getUserAccountAt(index));
		
		return myAccount;
	}
	
	public static UserAccount register(Database myDatabase, Scanner keyboard)
	{
		// variables needed to register a new user
		String input, firstName, lastName, email, password;
		char middleInitial;
		boolean isMember;
		
		// to hold a UserAccount
		UserAccount myAccount;
		
		// get the account information
		System.out.println("Enter your email address: ");
		email = keyboard.nextLine();
		
		// check if the email address already exists
		if (myDatabase.emailExists(email))
		{
			System.out.println("ERROR: The email address already exists.\n"
					+ "Try registering with a different email or try logging in.\n");
			myAccount = null;
			return myAccount;
		}
		
		System.out.println("Enter your First Name: ");
		firstName = keyboard.nextLine();
		
		System.out.println("Enter your Middle Initial: ");
		input = keyboard.nextLine();
		middleInitial = input.charAt(0);
		
		System.out.println("Enter your Last Name: ");
		lastName = keyboard.nextLine();
		
		System.out.println("Enter the desired account Password: ");
		password = keyboard.nextLine();
		
		System.out.println("Would you like to become a UClub Member for $19.95 annually? (Y/N): ");
		input = keyboard.nextLine();
		isMember = Boolean.valueOf(input);
		
		// create the account
		myAccount = new UserAccount(firstName, middleInitial, lastName, email, password, isMember);
		
		// add the account to the database
		myDatabase.addUserAccount(myAccount);
		
		return myAccount;
	}

	public static int getItemMenuChoice(Scanner keyboard)
	{
		int choice;
		
		do
		{
			System.out.println("Item Categories");
			System.out.println("------------------");
			System.out.println("1: Furniture");
			System.out.println("2: Rugs");
			System.out.println("3: Decor");
			System.out.println("4: Bed & Bath");
			System.out.println("5: Home Improvement");
			System.out.println("6: Kitchen");
			System.out.println("7: Outdoor");
			System.out.println();
			System.out.println("8: View Shopping Cart");
			System.out.println("9: Log Out");
			
			choice = keyboard.nextInt();
			keyboard.nextLine(); // remove the newline character from the keyboard buffer
			if (choice < 1 || choice > 9)
				System.out.println("ERROR: Please select a valid menu choice.\n");
			
		} while (choice < 1 || choice > 9);
		
		return choice;
	}
	
	public static void displayFurniture(ArrayList<Item> currentInventory)
	{
		
	}
}
