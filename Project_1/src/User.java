/**
 * 
 */

public abstract class User 
{
	protected String firstName, lastName, email, password;
	protected char middleInitial;
	
	String type;
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param middleInitial
	 * @param type
	 */
	public User(String firstName, char middleInitial, String lastName, String email, 
			String password, String type)
	{
		this.setFirstName(firstName);
		this.setMiddleInitial(middleInitial);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setType(type);
	}
	
	/**
	 * Copy constructor
	 * @param account The UserAccount object to be copied
	 */
	public User(User account)
	{
		setFirstName(account.getFirstName());
		setMiddleInitial(account.getMiddleInitial());
		setLastName(account.getLastName());
		setEmail(account.getEmail());
		setPassword(account.getPassword());
		setType(account.getType());
	}
	
// Getters.
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public char getMiddleInitial()
	{
		return this.middleInitial;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	
// Setters.
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setMiddleInitial(char middleInitial)
	{
		this.middleInitial = middleInitial;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setType(String type)
	{
		String temp = type.toUpperCase();
		
		if(temp == "MEMBER")
			this.type = type;
		else if(temp == "STANDARD")
			this.type = type;
		else if(temp == "ADMIN")
			this.type = type;
		else
			System.out.println("ERROR: User type invalid.");
	}
	
	public abstract void showCart();
	public abstract double getReward();
	public abstract void setReward(double reward);
	public abstract void addItemToCart(Item item);
	public abstract void removeItemFromCart(int choice);
	public abstract boolean cartIsEmpty();
	public abstract ShoppingCart getCart();
	
	public abstract String toString();
}
