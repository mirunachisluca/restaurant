package ro.tuc.assign4;

public class BaseProduct extends MenuItem {
	String name;
	float price;
	
	public BaseProduct(String name, float price)
	{
		this.name = name;
		this.price = price;
	}
	
	public float computePrice()
	{
		return this.price;
	}
	
	public void setNewPrice(float price)
	{
		this.price = price;
	}
	
	public void setNewName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
