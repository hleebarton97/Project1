/**
	ShoppingCart class
*/

public class ShoppingCart
{
	private Item[] cart;
	private int numItems;
	private final double SALES_TAX = 0.0825; // 8.25% sales tax
	
	public ShoppingCart()
	{
		cart = new Item[25];
		numItems = 0;
	}
	
	public void addItem(Item itm)
	{
		int i = 0;
		while (cart[i] != null)
			i++;
		
		cart[i] = itm;
		numItems++;
	}
	
	public void removeItem(Item itm)
	{
		int j = 0;
		for (int i = 0; i < cart.length; i++)
		{
			if (itm.getItemName() == cart[i].getItemName())
			{
				
			}
		}
		
		numItems--;
	}
	
	public int getNumItems()
	{
		return numItems;
	}
	
	public double getTotal()
	{
		double total = 0.0;
		
		for (Item i : cart)
		{
			total += i.getItemPrice();
		}
		
		return total;
	}
	
	/*public void checkOut(char isMember)
	{
		chargeUser();
		printReceipt();
		updateInventory();
	}*/
}