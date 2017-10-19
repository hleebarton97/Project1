/**
	Database
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Database
{
	private ArrayList<Item> inventory;
	private ArrayList<UserAccount> accounts;

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public Database() throws FileNotFoundException
	{
		inventory = new ArrayList<Item>();
		accounts = new ArrayList<UserAccount>();
		
		parseInventoryFromFile();
		parseUserAccountsFromFile();
	}
	
	/**
	 * The parseInventoryFromFile method opens up the Uberstock Inventory
	 * file and parses the items into an ArrayList of Items.
	 * @throws FileNotFoundException 
	 */
	public void parseInventoryFromFile() throws FileNotFoundException
	{
		
		// open the file to read Items into the array
		String fileName;
		File file;
		Scanner inputFile;
		fileName = "src/DB/Inventory.txt";
		file = new File(fileName);
		inputFile = new Scanner(file);
		
		// variables needed to read in Items from the file
		int i = 0; // incrementor
		String input, itemType, itemName;
		double price;
		int quantity;
		
		// read the items from the file into the inventory array
		while(inputFile.hasNext())
		{
			// tokenize the lines from the file and
			// create an array of Items
			String[] tokens;
			input = inputFile.nextLine();
			tokens = input.split(",");
			itemType = tokens[0];
			itemName = tokens[1];
			price = Double.parseDouble(tokens[2]);
			quantity = Integer.parseInt(tokens[3]);
			
			inventory.add(i, new Item(itemType, itemName, price, quantity));
			i++;
		}
		
		// close the file
		inputFile.close();
	}
	
	/**
	 * The copyInventory method copies an ArrayList of Items.
	 * @return A copy of an ArrayList of Items.
	 */
	public ArrayList copyInventory()
	{
		ArrayList<Item> copy = new ArrayList<Item>();
		
		for (int i = 0; i < inventory.size(); i++)
		{
			copy.add(inventory.get(i));
		}
		
		return copy;
	}
	
	/**
	 * The parseUserAccountsFromFile method opens up the Uberstock UserAccounts
	 * file and parses the accounts into an ArrayList of UserAccounts.
	 * @throws FileNotFoundException 
	 */
	public void parseUserAccountsFromFile() throws FileNotFoundException
	{
		// open the file to read UserAccounts into the array
		String fileName;
		File file;
		Scanner inputFile;
		fileName = "src/DB/UserAccounts.txt";
		file = new File(fileName);
		inputFile = new Scanner(file);

		// variables needed to read in UserAccounts into the array
		int i = 0;
		String input, firstName, lastName, email, password;
		char middleInitial;
		boolean isMember;
		
		while(inputFile.hasNext())
		{
			// tokenize the lines from the file and
			// create an array of UserAccounts
			String[] tokens;
			input = inputFile.nextLine();
			tokens = input.split(",");
			firstName = tokens[0];
			middleInitial = tokens[1].charAt(0);
			lastName = tokens[2];
			email = tokens[3];
			password = tokens[4];
			isMember = Boolean.valueOf(tokens[5]);
			
			accounts.add(i, new UserAccount(firstName, middleInitial, lastName,
					email, password, isMember));
			i++;
		}
		
		// close the file
		inputFile.close();
	}
	
	/**
	 * The accountExistsAt method checks the ArrayList of UserAccounts
	 * and determines if the email and password parameters match a
	 * UserAccount's email and password.
	 * @param email The email to be checked.
	 * @param password The password to be checked.
	 * @return The index of the account in the ArrayList.
	 */
	public int accountExistsAt(String email, String password)
	{
		for(int i = 0; i < accounts.size(); i++)
		{
			if(email.equalsIgnoreCase(accounts.get(i).getEmail())
					&& password.equals(accounts.get(i).getPassword()))
				return i;
		}
		return -1;
	}
	
	/**
	 * The emailExists method checks if the email parameter matches an
	 * email in the ArrayList of UserAccounts.
	 * @param email The email to be checked for its existence.
	 * @return True if found; false otherwise.
	 */
	public boolean emailExists(String email)
	{
		boolean status = false;
		
		if (accounts.contains(email))
			status = true;

		return status;
	}
	
	/**
	 * The getUserAccountAt accepts an index of the UserAccount
	 * to return from the ArrayList of UserAccounts.
	 * @param index The UserAccount location in the ArrayList of User Accounts.
	 * @return A reference to a UserAccount.
	 */
	public UserAccount getUserAccountAt(int index)
	{
		UserAccount myAccount = new UserAccount(accounts.get(index));
		
		return myAccount;
	}
	
	public void displayInventory()
	{
		int i = 0;
		for (i = 0; i < inventory.size(); i++)
		{
			if (inventory.get(i) != null)
			{
				System.out.println(inventory.get(i));
				System.out.println();
			}
		}
	}
	
	public void displayAccounts()
	{
		int i = 0;
		for (i = 0; i < accounts.size(); i++)
		{
			if (accounts.get(i) != null)
			{
				System.out.println(accounts.get(i));
				System.out.println();
			}
		}
	}
}
