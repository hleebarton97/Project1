/**
	UserAccount
*/

public class UserAccount
{
	private String firstName;
	private char middleInitial;
	private String lastName;
	private String email;
	private String password;
	private boolean isMember;
	
	/**
	 * Constructor
	 * @param firstName
	 * @param middleInitial
	 * @param lastName
	 * @param email
	 * @param password
	 * @param isMember
	 */
	public UserAccount(String firstName, char middleInitial, String lastName,
			String email, String password, boolean isMember)
	{
		setFirstName(firstName);
		setMiddleInitial(middleInitial);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setMembership(isMember);
	}
	
	/**
	 * Copy constructor
	 * @param account The UserAccount object to be copied
	 */
	public UserAccount(UserAccount account)
	{
		setFirstName(account.getFirstName());
		setMiddleInitial(account.getMiddleInitial());
		setLastName(account.getLastName());
		setEmail(account.getEmail());
		setPassword(account.getPassword());
		setMembership(account.getMembership());
	}
	
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
	
	public void setMembership(boolean isMember)
	{
		this.isMember = isMember;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public char getMiddleInitial()
	{
		return middleInitial;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getMembership()
	{
		return isMember;
	}
	
	public String toString()
	{
		String str = "Name: " + firstName + " " + Character.toUpperCase(middleInitial)
				+ ". " + lastName
				+ "\nEmail: " + email
				+ "\nUClub Membership: " + isMember;
		
		return str;
	}
}