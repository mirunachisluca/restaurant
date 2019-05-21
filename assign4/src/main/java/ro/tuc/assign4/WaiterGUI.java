package ro.tuc.assign4;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import ro.tuc.assign4.MenuItem;

public class WaiterGUI {
	RestaurantProcessing restaurant;
	JFrame frame = new JFrame("Waiter");
	JPanel panel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JButton createOrder = new JButton("Create new order");
	JButton computePrice = new JButton("Compute price for order");
	JButton generateBill = new JButton("Generate bill");
	
	String[] columnNames = {"Order ID", "Items", "Price"};
	String[] columnNames2 = {"Menu item", "Price"};
	
	int orderID;
	String orderDate;
	int orderTable;
	
	ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	
	public WaiterGUI(RestaurantProcessing rest)
	{
		restaurant = rest;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,600));
		buttonsPanel.add(createOrder);
		buttonsPanel.add(computePrice);
		buttonsPanel.add(generateBill);
		
		panel.add(buttonsPanel);
		updateOrderList();
		
		createOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				final JFrame frame = new JFrame("Create new order");
				JPanel panel = new JPanel();
				JPanel p1 = new JPanel();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setPreferredSize(new Dimension(500, 200));
				JLabel id = new JLabel("ID");
				JLabel date = new JLabel("Date");
				JLabel table = new JLabel("Table");
				final JTextField idField = new JTextField(3);
				final JTextField dateField = new JTextField(10);
				final JTextField tableField = new JTextField(3);
				JButton ok = new JButton("OK");
				p1.add(id); p1.add(idField);
				p1.add(date); p1.add(dateField);
				p1.add(table); p1.add(tableField);
				p1.add(ok);
				panel.add(p1);
				//JTable menu = createTable();
				//JScrollPane scrollPane = new JScrollPane(menu);
				//panel.add(scrollPane);
				
				idField.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						orderID = Integer.parseInt(idField.getText());
					}
				});
				
				dateField.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						orderDate = dateField.getText();
					}
				});
				
				tableField.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						orderTable = Integer.parseInt(tableField.getText());
					}
				});
				
				ok.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						frame.dispose();
						final JFrame newFrame = new JFrame();
						newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						newFrame.setPreferredSize(new Dimension(535, 480));
						JPanel panel = new JPanel();
						JButton ok = new JButton("OK");
						final JTable table = createTable();
						JScrollPane scrollPane = new JScrollPane(table);
						panel.add(scrollPane);
						panel.add(ok);
						
						ok.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								int[] v = table.getSelectedRows();
								items = new ArrayList<MenuItem>();
								for (int i=0; i < v.length; i++)
								{
									String name = table.getModel().getValueAt(v[i], 0).toString();
									MenuItem item = restaurant.getMenuItem(name);
									items.add(item);
								}
								restaurant.createNewOrder(orderID, orderDate, orderTable, items);
								updateOrderList();
								newFrame.dispose();
							}
						});
						
						newFrame.add(panel);
						newFrame.pack();
						newFrame.setVisible(true);
						
					}
				});
				
				
				frame.add(panel);
				frame.pack();
				frame.setVisible(true);
				
			}
		});
		
		generateBill.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				final JFrame frame = new JFrame("Generate bill for order");
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setPreferredSize(new Dimension(535, 480));
				JPanel panel = new JPanel();
				JButton ok = new JButton("OK");
				final JTable table = createOrdersTable();
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
				panel.add(ok);
				
				ok.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int index = table.getSelectedRow();
						int id = Integer.parseInt(table.getModel().getValueAt(index, 0).toString());
						String date = table.getModel().getValueAt(index, 1).toString();
						//int tableNo = Integer.parseInt(table.getModel().getValueAt(index, 2).toString());
						
						Order order = restaurant.getOrder(id, date);
						restaurant.generateBill(order);	
						frame.dispose();
							
						
					}
				});
				
				frame.add(panel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void updateOrderList()
	{
		HashMap<Order, ArrayList<MenuItem>> orders = restaurant.getOrders();
		Object[][] data = new Object[orders.size()][3];
		int i = 0;
		for (Order o : orders.keySet())
		{
			data[i][0] = o.getOrderID();
			ArrayList<MenuItem> orderedItems = orders.get(o);
			String s = new String();
			for (MenuItem item: orderedItems)
			{
				s = s + item.getName() + ";";
			}
			s = s.substring(0, s.length()-1);
			data[i][1] = s;
			data[i][2] = restaurant.computePriceForOrder(o);
			i++;
		}
		
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panel.setVisible(false);
		panel.removeAll();
		panel.add(buttonsPanel);
		panel.add(scrollPane);
		panel.setVisible(true);
	}
	
	public JTable createOrdersTable()
	{
		HashMap<Order, ArrayList<MenuItem>> orders = restaurant.getOrders();
		Object[][] data = new Object[orders.size()][3];
		int i = 0;
		for (Order o : orders.keySet())
		{
			data[i][0] = o.getOrderID();
			ArrayList<MenuItem> orderedItems = orders.get(o);
			String s = new String();
			for (MenuItem item: orderedItems)
			{
				s = s + item.getName() + ";";
			}
			s = s.substring(0, s.length()-1);
			data[i][1] = s;
			data[i][2] = restaurant.computePriceForOrder(o);
			i++;
		}
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		
		return table;
	}
	
	public JTable createTable()
	{
		ArrayList<MenuItem> menu = restaurant.getMenu();
		Object[][] data = new Object[menu.size()][2];
		int i = 0;
		for (MenuItem item : menu)
		{
			data[i][0] = item.getName();
			data[i][1] = item.computePrice();
			i++;
		}
		
		JTable table = new JTable(data, columnNames2);
		table.setFillsViewportHeight(true);
		
		return table;
	}

}
