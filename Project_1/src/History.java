import java.util.ArrayList;
import java.util.Date;

public class History 
{
	private String email;
	private String firstName;
	private String lastName;
	private String date;
	private int itemCount;
	private double price;
	private ArrayList<String> itemNames;
	
	public History(String email, String firstName, String lastName, String date, int itemCount, double price, ArrayList<String> items)
	{
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDate(date);
		this.setItemCount(itemCount);
		this.setPrice(price);
		this.setItems(items);
		
	}
	
// Getters
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public int getItemCount()
	{
		return this.itemCount;
	}
	
	public double getPrice()
	{
		return this.price;
	}
	
	public ArrayList<String> getItems()
	{
		return this.itemNames;
	}
	
// Setters
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setItemCount(int itemCount)
	{
		this.itemCount = itemCount;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public void setItems(ArrayList<String> items)
	{
		this.itemNames = items;
	}
	
	@Override
	public String toString()
	{
		String str = "---------------------------------\n" + 
				"Name: " + this.firstName + " " + this.lastName +
				"\nEmail: " + this.email +
				"\nDate of Purchase: " + this.date +
				"\nItems: " + this.itemCount;
		
		for(int i = 0; i < this.itemCount; i++)
			str += "\n\t" + this.itemNames.get(i);
		
		str += "\nTotal Price: " + this.price +
				"\n---------------------------------";
		
		return str;
	}
}
