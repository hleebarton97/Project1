import java.io.IOException;

public class Standard extends User
{
	ShoppingCart cart = new ShoppingCart();

	public Standard(String firstName, char middleInitial, String lastName, String email, 
			String password) 
	{
		super(firstName, middleInitial, lastName, email, password, "STANDARD");
	}

	@Override
	public String toString() 
	{
		return null;
	}

	@Override
	public double getReward() 
	{
		return -1;
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
		System.out.println("Press \"ENTER\" to continue...");
		
		try {
			System.in.read(); }
		catch(IOException e) {
			e.printStackTrace(); }
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
	public boolean cartIsEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
