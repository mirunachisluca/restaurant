package ro.tuc.assign4;

import java.util.ArrayList;
import java.util.HashMap;

public interface RestaurantProcessing {
	public void createNewMenuItem(String name, float price, ArrayList<MenuItem> items);
	public void deleteMenuItem(MenuItem item);
	public void editMenuItem(MenuItem item, String newName, float newPrice);
	public void createNewOrder(int orderID, String date, int table, ArrayList<MenuItem> items);
	public float computePriceForOrder(Order order);
	public void generateBill(Order order);
	public ArrayList<MenuItem> getMenu();
	public HashMap<Order, ArrayList<MenuItem>> getOrders();
	public void printMenu();
	public MenuItem getMenuItem(String name);
	public Order getOrder(int id, String date);
}
