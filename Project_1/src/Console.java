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
		
		if(myAccount instanceof Admin)
		{
			do
			{
				choice = getAdminMenuChoice(keyboard);
				
				switch(choice)
				{
					case 1:
						displayUserHistory(myDatabase, keyboard);
						break;
						
				}
			}
			while(choice != 2);
		}
		else
		{
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
						
					case 9:
						checkout(currentInventory, myAccount, myDatabase, keyboard);
						break;
				}
			} while (choice != 10);
		}
		
		// update the UserAccount file
		myDatabase.writeCurrentUserAccounts();
		
		// Update the Inventory file
		myDatabase.writeCurrentInventory(currentInventory);
		
		//TODO: Some method to save the state of a user's shopping cart
		
		System.exit(0);
	}
	
	public static void checkout(ArrayList<Item> currentInventory, User myAccount, Database myDatabase, Scanner keyboard)
	{
		// create checkout system
		Checkout myCheckout = new Checkout(myAccount.getCart(), myAccount);
		
		String input;
		boolean error = false;
		
		if(!myAccount.cartIsEmpty())
		{
			myAccount.showCart();
			do
			{
				error = false;
				
				System.out.print("Are you sure you want to checkout? (Y/N): ");
				input = keyboard.nextLine(); // Receive input.
				System.out.println();
				
				// Check answer and create a appropriate account.
				String tempInput = input.toUpperCase();
				
				if(tempInput.equals("Y"))
				{
					myCheckout.printReceipt();
					myCheckout.updateDatabase(myDatabase, currentInventory);
				}
				else if(tempInput.equals("N"))
				{
					return;
				}
				else
				{
					error = true;
					System.out.println("ERROR: Invalid Input.");
				}
			}
			while(error);
		}
		else
			System.out.println("Your cart is empty! Please shop to checkout!\n");
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
		boolean error = false;
		String input;
		
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
		
		if(myAccount instanceof Standard)
		{ 	
			do
			{
				error = false;
				
				System.out.print("Would you like to become a UClub Member for $19.95 annually? (Y/N): ");
				input = keyboard.nextLine(); // Receive input.
				System.out.println();
				
				// Check answer and create a appropriate account.
				String tempInput = input.toUpperCase();
				
				if(tempInput.equals("Y"))
				{
					myAccount = new Member(myAccount.getFirstName(), myAccount.getMiddleInitial(), myAccount.getLastName(), myAccount.getEmail(), myAccount.getPassword(), 0.0);
					System.out.println("Your account has now been upgraded! You have been charged the $19.95 annual fee!\n");
					try {
						myDatabase.upgradeUser(myAccount); }
					catch(Exception e) {
						System.out.println("Problem upgrading the account."); }
				}
				else if(tempInput.equals("N"))
				{
					return myAccount;
				}
				else
				{
					error = true;
					System.out.println("ERROR: Member input selection invalid.");
				}
			}
			while(error);
		}
		
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
	
	public static int getAdminMenuChoice(Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
		System.out.println("Admin Options: ");
		System.out.println("-------------------------------");
		System.out.println("1: View User Purchase History");
		System.out.println("2: Log Out");
		System.out.println("-------------------------------");
		
		do
		{
			System.out.print(":: ");
			
			try {
				choice = keyboard.nextInt(); }
			catch(Exception e) {
				System.out.println("ERROR: Invalid choice.\n");
				error = true; }
			
			keyboard.nextLine(); // remove the newline character from the keyboard buffer
			System.out.println();
			
			if (choice < 1 || choice > 2)
			{
				if(!error)
				{
					System.out.println("ERROR: Please select a valid menu choice.\n");
					error = true;
				}
			}
		}
		while(error);
		
		return choice;
	}

	public static int getItemMenuChoice(Scanner keyboard)
	{
		int choice = 0;
		boolean error = false;
		
			
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
			System.out.println("9: Checkout");
			System.out.println("10: Log Out");
			
		do
		{
			System.out.print(":: ");
			
			try {
				choice = keyboard.nextInt(); }
			catch(Exception e) {
				System.out.println("ERROR: Invalid choice.\n");
				error = true; }
			
			keyboard.nextLine(); // remove the newline character from the keyboard buffer
			System.out.println();
			
			if (choice < 1 || choice > 10)
			{	
				if(!error)
				{
					System.out.println("ERROR: Please select a valid menu choice.\n");
					error = true;
				}
			}
			
		} while (error);
		
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
		boolean error = false;
		
		myAccount.showCart();
		
		if(!myAccount.cartIsEmpty())
		{
			do
			{
				error = false;
				System.out.println("Please enter 0 to continue");
				System.out.println("Or enter the item number to remove it from the cart.");
				System.out.println("-----------------------------------------");
				
				try {
					choice = getCartOptionChoice(keyboard); }
				catch(Exception e) {
					System.out.println("Invalid Item Number. Please try again.\n\n");
					error = true; }
				
				if(choice > myAccount.getCart().getNumItems())
				{
					System.out.println("That item number does not exist.");
					System.out.println();
					error = true;
				}
				
				if(choice != 0 && !error)
					myAccount.removeItemFromCart(choice);
			}
			while(error);
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
	
	public static void displayUserHistory(Database myDatabase, Scanner keyboard)
	{
		String email = "";
		boolean error = false;
		
		do
		{
			error = false;
			
			System.out.print("Enter the email address of the user: ");
			email = keyboard.nextLine();
			
			// check that this is a valid email address in our database
			boolean exists = myDatabase.emailExists(email);
			
			if (!exists)
			{
				System.out.println("That email does not exist.\nPlease try again.\n");
				error = true;
			}
		}
		while(error);
		
		myDatabase.displayHistory(email);
		
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
