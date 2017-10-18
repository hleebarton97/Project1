/**
	Database
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Database
{
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();

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
	
	public ArrayList copyInventory()
	{
		ArrayList<Item> copy = new ArrayList<Item>();
		
		for (int i = 0; i < inventory.size(); i++)
		{
			copy.add(inventory.get(i));
		}
		
		return copy;
	}
	
	public void setUserAccounts() throws FileNotFoundException
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
	
	public int accountExistsAt(String email, String password)
	{
		int i = 0;
		while (accounts.get(i) != null)
		{
			if (email.equalsIgnoreCase(accounts.get(i).getEmail())
					&& password.equals(accounts.get(i).getPassword()))
				return i;
			i++;
		}
		
		return -1;
	}
	
	public boolean emailExists(String email)
	{
		boolean status = false;
		
		if (accounts.contains(email))
			status = true;

		return status;
	}
	
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