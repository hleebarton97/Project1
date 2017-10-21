import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Console.
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
		User myAccount;
		
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
					displayFurniture(currentInventory, myAccount, keyboard);
					break;
					
				case 2:
					displayRugs(currentInventory, myAccount, keyboard);
					break;
				
				case 3:
					displayDecor(currentInventory, myAccount, keyboard);
					break;
					
				case 4:
					displayBedAndBath(currentInventory, myAccount, keyboard);
					break;
					
				case 5:
					displayHomeImprovement(currentInventory, myAccount, keyboard);
					break;
					
				case 6:
					displayKitchen(currentInventory, myAccount, keyboard);
					break;
					
				case 7:
					displayOutdoor(currentInventory, myAccount, keyboard);
					break;
					
				case 8:
					displayCart(currentInventory, myAccount, keyboard);
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
		int choice = 0; // to hold the users choice
		boolean error; // error checking.

		// get the users choice
		do
		{
			error = false;
			
			System.out.println("Welcome to Overstock.com");
			System.out.println();
			System.out.println("1: Login");
			System.out.println("2: Register");
			System.out.println();
			
			System.out.print(":: ");
			
			try {
				choice = keyboard.nextInt(); }
			catch(Exception e) {
				System.out.println("ERROR: Invalid Choice. Please try again.\n\n");
				error = true;
			}
			keyboard.nextLine(); // discard newline character in string buffer
			System.out.println();
			
			if (choice < 1 || choice > 2)
			{
				if(!error)
				{
					System.out.println("ERROR: Invalid Choice. Please try again.\n\n");
					error = true;
				}
			}
			
		} while (error);

		return choice;
	}
	
	public static User login(Database myDatabase, Scanner keyboard)
	{
		// variables needed to login
		String email, password;
		
		// to hold a UserAccount
		User myAccount;
		
		// get the users email and password
		System.out.print("Enter the Email Address for your account: ");
		email = keyboard.nextLine();
		
		System.out.print("Enter the password for your account: ");
		password = keyboard.nextLine();
		System.out.println();
		
		// search for the account in the database
		int index = myDatabase.accountExistsAt(email, password);
		
		if (index == -1)
		{
			System.out.println("Your credentials do not match our database.\nPlease try again.\n\n");
			myAccount = null;
		}
		else
			myAccount = myDatabase.getUserAccountAt(index);
		
		return myAccount;
	}
	
	public static User register(Database myDatabase, Scanner keyboard)
	{
		// variables needed to register a new user
		String input, firstName, lastName, email, password;
		char middleInitial;
		boolean error = false;
		
		// to hold a UserAccount
		User myAccount = null;
		
		// get the account information
		System.out.print("Enter your email address: ");
		email = keyboard.nextLine();
		System.out.println();
		
		// check if the email address already exists
		if (myDatabase.emailExists(email))
		{
			System.out.println("ERROR: The email address already exists.\n"
					+ "Try registering with a different email or try logging in.\n");
			myAccount = null;
			return myAccount;
		}
		
		System.out.print("Enter your First Name: ");
		firstName = keyboard.nextLine();
		System.out.println();
		
		System.out.print("Enter your Middle Initial: ");
		input = keyboard.nextLine();
		middleInitial = input.charAt(0);
		System.out.println();
		
		System.out.print("Enter your Last Name: ");
		lastName = keyboard.nextLine();
		System.out.println();
		
		System.out.print("Enter the desired account Password: ");
		password = keyboard.nextLine();
		System.out.println();
		
		do
		{
			error = false;
			
			System.out.print("Would you like to become a UClub Member for $19.95 annually? (Y/N): ");
			input = keyboard.nextLine(); // Receive input.
			System.out.println();
			
			// Check answer and create a appropriate account.
			String tempInput = input.toUpperCase();
			
			if(tempInput.equals("Y"))
				myAccount = new Member(firstName, middleInitial, lastName, email, password, 0.0);
			else if(tempInput.equals("N"))
				myAccount = new Standard(firstName, middleInitial, lastName, email, password);
			else
			{
				error = true;
				System.out.println("ERROR: Member input selection invalid.");
			}
		}
		while(error);
			
		
		// create the account
		//myAccount = new UserAccount(firstName, middleInitial, lastName, email, password, isMember);
		
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
			
			System.out.print(":: ");
			choice = keyboard.nextInt();
			keyboard.nextLine(); // remove the newline character from the keyboard buffer
			System.out.println();
			if (choice < 1 || choice > 9)
				System.out.println("ERROR: Please select a valid menu choice.\n");
			
		} while (choice < 1 || choice > 9);
		
		return choice;
	}
	
	public static int getCategoryItemChoice(Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		do
		{
			error = false;
			System.out.print("Enter item's number: ");
			
			try {
			choice = keyboard.nextInt(); }
			catch(Exception e) {
				System.out.println("ERROR: Invalid Choice. ");
				System.out.println();
				error = true; }
			
			keyboard.nextLine();
			System.out.println();
			
			if(choice < 0 || choice > 35)
				error = true;
		}
		while(error);
		
		return choice;
	}
	
	public static int getCartOptionChoice(Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		do
		{
			error = false;
			
			try {
				System.out.print(":: ");
				choice = keyboard.nextInt(); }
			catch(Exception e) {
				System.out.println("ERROR: Invalid Choice. ");
				System.out.println();
				error = true; }
			
			keyboard.nextLine();
			System.out.println();
		}
		while(error);
		
		return choice;
	}
	
	public static void displayCart(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		
		myAccount.showCart();
		
		if(!myAccount.cartIsEmpty())
		{
			System.out.println("Please enter 0 to continue");
			System.out.println("Or enter the item number to remove it from the cart.");
			System.out.println("-----------------------------------------");
			
			choice = getCartOptionChoice(keyboard);
			
			if(choice != 0)
				myAccount.removeItemFromCart(choice);
		}
		else
		{
			System.out.println("Press \"ENTER\" to continue...");
			
			try {
				System.in.read(); }
			catch(IOException e) {
				e.printStackTrace(); }
		}
		
		System.out.println();
	}
	
// ------------- DISPLAY CATEGORIES ---------------- //	
	
	public static void displayFurniture(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		
		System.out.println("Select a Furniture Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("furniture"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 0 && choice < 6)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
				}
		}
		while(choice < 0 || choice > 6);
		
		
		System.out.println();
	}
	
	
	private static void displayRugs(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard) 
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Rug Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("rugs"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 5 && choice < 11)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
	private static void displayDecor(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Decor Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("decor"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 10 && choice < 16)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
	private static void displayBedAndBath(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Bed and Bath Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("bed_bath"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 15 && choice < 21)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
	private static void displayHomeImprovement(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Home Improvement Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("home_improvement"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 20 && choice < 26)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
	private static void displayKitchen(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Kitchen Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("kitchen"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 25 && choice < 31)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
	private static void displayOutdoor(ArrayList<Item> currentInventory, User myAccount, Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Select a Furniture Item to add to your shopping cart");
		System.out.println("---------------");
		for (int i = 0; i < currentInventory.size(); i++)
			if (currentInventory.get(i).getItemType().equals("outdoor"))
			{
				System.out.println(i + 1 + ": " + currentInventory.get(i));
				System.out.println("---------------");
			}
		
		System.out.println("0 takes you back to the menu.");
		System.out.println("---------------");
		
		do
		{
			error = false;
			choice = getCategoryItemChoice(keyboard);
			
			if(choice != 0)
				if(choice > 30 && choice < 36)
					myAccount.addItemToCart(currentInventory.get(choice - 1));
				else
				{
					System.out.println("ERROR: Choice is out of range of items.");
					System.out.println();
					error = true;
				}
		}
		while(error);
		
		System.out.println();
	}
	
}
