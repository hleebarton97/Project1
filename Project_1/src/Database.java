/**
	Database
*/

import java.util.Scanner;
import java.io.*;

public class Database
{
	private Item[] inventory;
	private UserAccount[] accounts;

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public Database() throws FileNotFoundException
	{
		setInventory();
		setUserAccounts();
	}
	
	/**
	 * setInventory method
	 * @throws FileNotFoundException 
	 */
	public void setInventory() throws FileNotFoundException
	{
		// initialize an empty array of 25 Items
		inventory = new Item[25];
		
		// open the file to read Items into the array
		String fileName;
		File file;
		Scanner inputFile;
		fileName = "C:\\Users\\Ryan\\Desktop\\Database\\Inventory.txt";
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
			tokens = input.split(" ");
			itemType = tokens[0];
			itemName = tokens[1];
			price = Double.parseDouble(tokens[2]);
			quantity = Integer.parseInt(tokens[3]);
			
			inventory[i] = new Item(itemType, itemName, price, quantity);
			i++;
		}
		
		// close the file
		inputFile.close();
	}
	
	public Item[] copyInventory()
	{
		Item[] copy = new Item[25];
		
		for (int i = 0; i < inventory.length; i++)
		{
			copy[i] = inventory[i];
		}
		
		return copy;
	}
	
	public void setUserAccounts() throws FileNotFoundException
	{
		// initialize an empty array of 10 UserAccounts
		accounts = new UserAccount[10];
		
		// open the file to read UserAccounts into the array
		String fileName;
		File file;
		Scanner inputFile;
		fileName = "C:\\Users\\Ryan\\Desktop\\Database\\UserAccounts.txt";
		file = new File(fileName);
		inputFile = new Scanner(file);

		// variables needed to read in UserAccounts into the array
		int i = 0;
		String input, firstName, lastName, email, password;
		char middleInitial, isMember;
		
		while(inputFile.hasNext())
		{
			// tokenize the lines from the file and
			// create an array of UserAccounts
			String[] tokens;
			input = inputFile.nextLine();
			tokens = input.split(" ");
			firstName = tokens[0];
			middleInitial = tokens[1].charAt(0);
			lastName = tokens[2];
			email = tokens[3];
			password = tokens[4];
			isMember = tokens[5].charAt(0);
			
			accounts[i] = new UserAccount(firstName, middleInitial, lastName,
					email, password, isMember);
			i++;
		}
		
		// close the file
		inputFile.close();
	}
	
	public int accountExistsAt(String email, String password)
	{
		int i = 0;
		while (accounts[i] != null)
		{
			if (email.equalsIgnoreCase(accounts[i].getEmail())
					&& password.equals(accounts[i].getPassword()))
				return i;
			i++;
		}
		
		return -1;
	}
	
	public boolean emailExists(String email)
	{
		boolean status = false;
		int i = 0;
		
		while(accounts[i] != null)
		{
			if (email.equalsIgnoreCase(accounts[i].getEmail()))
				status = true;
			i++;
		}

		return status;
	}
	
	public UserAccount getUserAccountAt(int index)
	{
		UserAccount myAccount = new UserAccount(accounts[index]);
		
		return myAccount;
	}
	
	public void displayInventory()
	{
		int i = 0;
		for (i = 0; i < inventory.length; i++)
		{
			if (inventory[i] != null)
			{
				System.out.println(inventory[i]);
				System.out.println();
			}
		}
	}
	
	public void displayAccounts()
	{
		int i = 0;
		for (i = 0; i < accounts.length; i++)
		{
			if (accounts[i] != null)
			{
				System.out.println(accounts[i]);
				System.out.println();
			}
		}
	}
}