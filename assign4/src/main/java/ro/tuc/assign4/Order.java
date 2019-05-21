package ro.tuc.assign4;

public class Order {
	int orderID;
	String date;
	int table;
	
	public Order()
	{
		orderID = 0;
		date = new String("01.01.2000");
		table = 0;
	}
	
	public Order(int id, String date, int table)
	{
		this.orderID = id;
		this.date = date;
		this.table = table;
	}
	
	public int getOrderID()
	{
		return this.orderID;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public int getTable()
	{
		return this.table;
	}
	
	
	@Override
	public int hashCode()
	{
		return (3*orderID*orderID + 6* table) % date.hashCode();
	}
}
