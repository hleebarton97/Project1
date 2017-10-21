/**
	Item class
*/

public class Item
{
	private String itemType;
	private String itemName;
	private double itemPrice;
	private int itemQuantity;
	
	/**
	 * Constructor
	 */
	public Item(String itemType, String itemName, double itemPrice, int itemQuantity)
	{
		setItemType(itemType);
		setItemName(itemName);
		setItemPrice(itemPrice);
		setItemQuantity(itemQuantity);
	}
	
	/**
	 * Copy constructor
	 * @param itm The Item object to be copied.
	 */
	public Item(Item itm)
	{
		setItemType(itm.getItemType());
		setItemName(itm.getItemName());
		setItemPrice(itm.getItemPrice());
		setItemQuantity(itm.getItemQuantity());
	}
	
	public void setItemType(String itemType)
	{
		this.itemType = itemType;
	}
	
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	
	public void setItemPrice(double itemPrice)
	{
		this.itemPrice = itemPrice;
	}
	
	public void setItemQuantity(int itemQuantity)
	{
		this.itemQuantity = itemQuantity;
	}
	
	public void decreaseItemQuantity(int num)
	{
		itemQuantity -= num;
	}
	
	public void increaseItemQuantity(int num)
	{
		itemQuantity += num;
	}
	
	public void decrementItemQuantity()
	{
		itemQuantity--;
	}
	
	public void incrementItemQuantity()
	{
		itemQuantity++;
	}
	
	public String getItemType()
	{
		return itemType;
	}
	
	public String getItemName()
	{
		return itemName;
	}
	
	public double getItemPrice()
	{
		return itemPrice;
	}
	
	public int getItemQuantity()
	{
		return itemQuantity;
	}
	
	public String toString()
	{
		String str = "Item: " + itemName
				+ "\nPrice: $" + itemPrice;
		
		if(itemQuantity != 0)
			str += "\nQuantity: " + itemQuantity;
		else
			str += "\nQuantity: SOLD OUT!";
		
		return str;
	}
	
	public String displayItem()
	{
		String str = "Item: " + itemName
				+ "\nPrice: $" + itemPrice;
		
		return str;
	}
}