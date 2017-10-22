import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Checkout 
{
	ShoppingCart cart = null;
	User user = null;
	History history;
	
	private final double SALES_TAX = 0.0825;
	private final double REWARDS = 0.05;
	
	private String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
	private ArrayList<String> items = new ArrayList<String>();

	public Checkout(ShoppingCart cart, User user)
	{
		this.cart = cart;
		this.user = user;
		this.history = new History(this.user.getEmail(), this.user.getFirstName(), this.user.getLastName(), timeStamp, this.cart.getNumItems(), (this.cart.getTotal() + this.cart.getTotal() * SALES_TAX), this.getItemHistoryNames());
	}
	
	public void printReceipt()
	{
		// Calculate tax, total, and rewards.
		double tax = this.cart.getTotal() * SALES_TAX;
		double total = this.cart.getTotal() + tax;
		double rewards = total * REWARDS;
		
		System.out.println("\n\n\n\nRECEIPT: ");
		System.out.println("-----------------------------------------");
		System.out.println("Items: ");
		
		for(int i = 0; i < this.cart.getNumItems(); i++)
			System.out.println(this.cart.getItemAt(i).getItemName() + "\t$" + this.cart.getItemAt(i).getItemPrice() + "\n");
		
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.println("Subtotal: \t\t\t$" + this.cart.getTotal());
		System.out.println("Tax (" + SALES_TAX * 100 + "%): \t\t\t$" + String.format("%.2f", tax));
		System.out.println();
		System.out.println("TOTAL: \t\t\t\t$" + String.format("%.2f", total));
		
		if(user instanceof Member)
		{
			System.out.println("UClub Rewards: \t\t\t$" + String.format("%.2f", rewards));
			user.setReward(user.getReward() + rewards);
		}
		
		System.out.println("-----------------------------------------");
		System.out.println("Press \"ENTER\" to continue...");
		
		try {
			System.in.read(); }
		catch(IOException e) {
			e.printStackTrace(); }
		
		
	}
	
	public ArrayList<String> getItemHistoryNames()
	{
		for(int i = 0; i < this.cart.getNumItems(); i++)
			this.items.add(this.cart.getItemAt(i).getItemName());
		
		return this.items;
	}
	
	public void updateDatabase(Database db, ArrayList<Item> currentInventory)
	{
		try {
			db.writeCurrentInventory(currentInventory); }
		catch(Exception e) {
			e.printStackTrace(); }
		
		try {
			db.updateUser(this.user); }
		catch(Exception e) {
			e.printStackTrace(); }
		
		try {
			db.addUserHistory(this.history); }
		catch(Exception e) {
			e.printStackTrace(); }
		
		this.cart.clearCart(); // Clear user's cart as we have checked out.
	}
	
}
