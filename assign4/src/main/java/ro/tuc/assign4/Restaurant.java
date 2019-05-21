package ro.tuc.assign4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Restaurant extends Observable implements RestaurantProcessing, Serializable{
	
	HashMap<Order, ArrayList<MenuItem>> orders;
	ArrayList<MenuItem> menu;
	ArrayList<Observer> observers;
	
	public Restaurant()
	{
		orders = new HashMap<Order, ArrayList<MenuItem>>();
		menu = new ArrayList<MenuItem>();
		observers = new ArrayList<Observer>();
		deserialize();
	}
	
	public void createNewMenuItem(String name, float price, ArrayList<MenuItem> items) {
		
		MenuItem item;
		if (items.size() == 0)
		{
			assert (!name.isBlank());
			assert(price >= 1);
			item = new BaseProduct(name, price);
			menu.add(item);
			assert (item.computePrice() == price);
			assert menu.contains(item);
		}
		else
		{
			for (MenuItem i : items)
				assert menu.contains(i);
			item = new CompositeProduct(items);
			menu.add(item);
			assert menu.contains(item);
		}
		serialize();
	}

	public void deleteMenuItem(MenuItem item) {
		
		assert menu.contains(item);
		menu.remove(item);
		assert (!menu.contains(item));
		serialize();
	}

	public void editMenuItem(MenuItem item, String newName, float newPrice) {
		
		assert menu.contains(item);
		assert (item instanceof BaseProduct);
		assert (item.computePrice() >= 1);
		assert (!((BaseProduct)item).getName().isBlank());
		((BaseProduct)item).setNewName(newName);
		((BaseProduct)item).setNewPrice(newPrice);
		assert ((BaseProduct)item).getName().equals(newName);
		assert (((BaseProduct)item).computePrice() == newPrice);
		serialize();
	}

	public void createNewOrder(int orderID, String date, int table, ArrayList<MenuItem> items) {
		
		assert (orderID > 0);
		assert (table > 0);
		Order order = new Order(orderID, date, table);
		assert (!orders.containsKey(order));
		for (MenuItem i : items)
			assert (menu.contains(i));
		orders.put(order, items);
		assert (orders.containsKey(order));
		assert (orders.containsValue(items));
		
		
	}

	public float computePriceForOrder(Order order) {
		
		float price = 0;
		assert (orders.containsKey(order));
		ArrayList<MenuItem> orderedItems = orders.get(order);
		assert(orders.containsValue(orderedItems));
		for (MenuItem i : orderedItems)
		{
			price += i.computePrice();
		}
		assert(orders.containsKey(order));
		assert(orders.containsValue(orderedItems));
		
		return price;
	}

	public void generateBill(Order order) {
		
		String s = new String("Bon_"+order.getOrderID() + ".txt");
		try {
			PrintWriter writer = new PrintWriter(s, "UTF-8");
			writer.println("Bon fiscal");
			writer.println("Data " + order.getDate());
			ArrayList<MenuItem> orderedItems = orders.get(order);
			for (MenuItem i : orderedItems)
			{
				writer.println(i.getName());
			}
			writer.println("Total: " + computePriceForOrder(order));
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<MenuItem> getMenu()
	{
		return menu;
	}
	
	public HashMap<Order, ArrayList<MenuItem>> getOrders()
	{
		return orders;
	}

	public void printMenu()
	{
		for (MenuItem i : menu)
		{
			System.out.println(i.getName());
		}
	}
	
	public MenuItem getMenuItem(String name)
	{
		int index = 0;
		for (MenuItem item : menu)
		{
			if (item.getName().equals(name))
				break;
			index++;		
		}
		return menu.get(index);
	}
	
	public Order getOrder(int id, String date)
	{
		Order order = new Order();
		for (Order o : orders.keySet())
		{
			order = o;
			if ((o.getOrderID() == id) && (o.getDate().equals(date)))
				break;
		}
		return order;
	}
	
	public void serialize()
	{
		try
		{
			FileOutputStream file = new FileOutputStream("menu.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(menu);
			
			out.close();
			file.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void deserialize()
	{
		ArrayList<MenuItem> obj = new ArrayList<MenuItem>();
		try
		{
			FileInputStream file = new FileInputStream("menu.ser");
			ObjectInputStream in = new ObjectInputStream(file);
			
			obj = (ArrayList<MenuItem>)in.readObject();
			
			menu = obj;
			
			in.close();
			file.close();
		
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}
	
	 public void notifyAllObservers(){
	      for (Observer observer : observers) 
	      {
	         observer.update();
	      }
	 }
}
