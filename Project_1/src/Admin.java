
public class Admin extends User
{

	public Admin(String firstName, char middleInitial, String lastName, String email, String password) 
	{
		super(firstName, middleInitial, lastName, email, password, "ADMIN");
	}
	
	@Override
	public String toString()
	{
		return "";
	}

	@Override
	public double getReward() {return -1;}
	@Override
	public void addItemToCart(Item item) {}
	@Override
	public void showCart() {}
	@Override
	public void removeItemFromCart(int choice) {}
	@Override
	public boolean cartIsEmpty() {return false;}

}
