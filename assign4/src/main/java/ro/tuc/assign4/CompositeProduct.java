package ro.tuc.assign4;

import java.util.*;

public class CompositeProduct extends MenuItem {
	List<MenuItem> items;
	
	public CompositeProduct()
	{
		items = new ArrayList<MenuItem>();
	}
	
	public CompositeProduct(ArrayList<MenuItem> items)
	{
		this.items = items;
	}
	
	public void addItem(MenuItem item)
	{
		items.add(item);
	}
	
	public float computePrice()
	{
		float price = 0;
		for (MenuItem item : items)
		{
			price += ((BaseProduct)item).computePrice();
		}
		return price;
	}
	
	public String getName()
	{
		String s = new String();
		for (MenuItem item : items)
		{
			s = s + ", " + ((BaseProduct)item).getName(); 
		}
		s = s.substring(2, s.length());
		return s;
	}
}
