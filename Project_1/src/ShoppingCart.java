import java.util.ArrayList;

/**
	ShoppingCart class
*/

public class ShoppingCart
{
	private ArrayList<Item> cart = new ArrayList<Item>();
	private int numItems;
	private final double SALES_TAX = 0.0825; // 8.25% sales tax
	
	public ShoppingCart()
	{
		numItems = 0;
	}
	
	public void addItem(Item itm)
	{
		if(itm.getItemQuantity() != 0)
		{
			itm.decrementItemQuantity();
			cart.add(itm);
			numItems++;
			System.out.println(itm.getItemName() + " has been added to your cart!");
		}
		else
			System.out.println(itm.getItemName() + " is now sold out and could not be added to your cart!");
	}
	
	public void removeItem(Item itm)
	{
		if(this.numItems != 0)
		{
			if(cart.contains(itm))
			{
				itm.incrementItemQuantity();
				cart.remove(itm);
				numItems--;
				System.out.println(itm.getItemName() + " has been removed from your cart!");
			}
			else
				System.out.println(itm.getItemName() + " is not in your cart!");
		}
		else
			System.out.println("Your cart is currently empty!");
	}
	
	public void clearCart()
	{
		this.cart = new ArrayList<Item>();
		this.numItems = 0;
	}
	
	public int getNumItems()
	{
		return numItems;
	}
	
	public double getTotal()
	{
		double total = 0.0;
		
		for (Item i : cart)
			total += i.getItemPrice();
		
		return total;
	}
	
	public Item getItemAt(int i)
	{

		return cart.get(i);

	}
	
	@Override
	public String toString()
	{
		int itemNum = 1;
		for(Item item : cart)
		{
			System.out.println("Item Number: " + itemNum);
			System.out.println(item.displayItem());
			System.out.println("-----------------------------------------");
			itemNum++;
		}
		
		return null;
	}
}