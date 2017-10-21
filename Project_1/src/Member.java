import java.io.IOException;

public class Member extends User
{
	private double reward = 0;
	
	ShoppingCart cart = new ShoppingCart();
	
	public Member(String firstName, char middleInitial, String lastName, String email, String password, double reward) 
	{
		super(firstName, middleInitial, lastName, email, password, "MEMBER");
		this.reward = reward;
	}
	
	public void setReward(double reward)
	{
		this.reward = reward;
	}
	
	public double getReward()
	{
		return this.reward;
	}

	@Override
	public String toString() 
	{
		return "";
	}

	@Override
	public void addItemToCart(Item item) 
	{
		cart.addItem(item);
		
		System.out.println("Press \"ENTER\" to continue...");
		
		try {
			System.in.read(); }
		catch(IOException e) {
			e.printStackTrace(); }
	}

	@Override
	public void showCart() 
	{
		System.out.println("Items currently in your Cart: ");
		System.out.println("-----------------------------------------");
		
		if(cart.getNumItems() != 0)
		{
			cart.toString();
			System.out.println("-----------------------------------------");
			System.out.println("Total Item Count: ............ " + cart.getNumItems());
			System.out.println("Total Price of Cart: ......... $" + cart.getTotal());
		}
		else
			System.out.println("Currently no Items in your Cart!");
		
		System.out.println("-----------------------------------------");

	}

	@Override
	public void removeItemFromCart(int choice) 
	{
		Item item = cart.getItemAt(choice - 1);
		
		cart.removeItem(item);
		
		System.out.println("Press \"ENTER\" to continue...");
		
		try {
			System.in.read(); }
		catch(IOException e) {
			e.printStackTrace(); }
	}

	@Override
	public boolean cartIsEmpty() 
	{
		if(cart.getNumItems() != 0)
			return false;
		else
			return true;
	}

}
