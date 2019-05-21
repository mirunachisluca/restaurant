package ro.tuc.assign4;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class AdministratorGUI {
	RestaurantProcessing restaurant;
	JFrame frame = new JFrame("Administrator");
	JPanel panel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JButton createItemButton = new JButton("Create new menu item");
	JButton deleteItemButton = new JButton("Delete menu item");
	JButton editItemButton = new JButton("Edit menu item");
	
	String[] columnNames = {"Menu item", "Price"};
	
	String itemName;
	float itemPrice;
	ArrayList<MenuItem> items = new ArrayList<MenuItem>();

	
	public AdministratorGUI(RestaurantProcessing rest)
	{
		restaurant = rest;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,600));
		buttonsPanel.add(createItemButton);
		buttonsPanel.add(deleteItemButton);
		buttonsPanel.add(editItemButton);
		
		panel.add(buttonsPanel);
		updateMenuList();
		
		createItemButton.addActionListener(new ActionListener()
		{
				public void actionPerformed(ActionEvent e)
				{
					JFrame frame = new JFrame("Create new menu item");
					JPanel panel = new JPanel();
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setPreferredSize(new Dimension(400, 200));
					JButton b1 = new JButton("Base Poduct");
					JButton b2 = new JButton("Composite Product");
					panel.add(b1);
					panel.add(b2);
					
					b1.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JFrame frame = new JFrame("New base product");
							frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							frame.setPreferredSize(new Dimension(400,200));
							JPanel panel = new JPanel();
							JPanel p1 = new JPanel();
							JPanel p2 = new JPanel();
							JLabel l1 = new JLabel("Name");
							JLabel l2 = new JLabel("Price");
							final JTextField name = new JTextField(15);
							final JTextField price = new JTextField(4);
							p1.add(l1); p1.add(name);
							p2.add(l2); p2.add(price);
							panel.add(p1);
							panel.add(p2);

							name.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									itemName = name.getText();
								}
							});
							
							price.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									itemPrice = Float.parseFloat(price.getText());
								}
							});
							
							JButton ok = new JButton("OK");
							panel.add(ok);
							
							ok.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									items.clear();
									restaurant.createNewMenuItem(itemName, itemPrice, items);
									updateMenuList();
									//restaurant.printMenu();
								}
							});
							
							frame.add(panel);
							frame.pack();
							frame.setVisible(true);
							
							
						}
						
					});
					
					b2.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JFrame frame = new JFrame("New composite product");
							frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							frame.setSize(new Dimension(500,500));
							JPanel panel = new JPanel();
							JButton ok = new JButton("OK");
							final JTable table = createTableWithBaseProducts();
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
									restaurant.createNewMenuItem(itemName, itemPrice, items);
									updateMenuList();
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
					
				};
		});
		
		deleteItemButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				final JFrame frame = new JFrame("Delete menu item");
				JPanel panel = new JPanel();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setPreferredSize(new Dimension(535, 480));
				JButton ok = new JButton("OK");
				final JTable table = createTable();
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
				panel.add(ok);
				
				ok.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int index = table.getSelectedRow();
						String name = table.getModel().getValueAt(index, 0).toString();
						MenuItem item = restaurant.getMenuItem(name);
						restaurant.deleteMenuItem(item);
						updateMenuList();
						frame.dispose();
					}
				});
				
				frame.add(panel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		
		editItemButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				final JFrame frame = new JFrame("Edit menu item");
				JPanel panel = new JPanel();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setPreferredSize(new Dimension(535, 480));
				JButton ok = new JButton("OK");
				final JTable table = createTableWithBaseProducts();
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
				panel.add(ok);
				
				ok.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						frame.dispose();
						int index = table.getSelectedRow();
						String productName = table.getModel().getValueAt(index, 0).toString();
						final MenuItem item = restaurant.getMenuItem(productName);
						final JFrame newFrame = new JFrame();
						JPanel panel = new JPanel();
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setPreferredSize(new Dimension(400, 200));
						JPanel p1 = new JPanel();
						JPanel p2 = new JPanel();
						JLabel l1 = new JLabel("New name");
						JLabel l2 = new JLabel("New price");
						final JTextField name = new JTextField(15);
						final JTextField price = new JTextField(4);
						p1.add(l1); p1.add(name);
						p2.add(l2); p2.add(price);
						panel.add(p1);
						panel.add(p2);
						
						name.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								itemName = name.getText();
							}
						});
						
						price.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								itemPrice = Float.parseFloat(price.getText());
							}
						});
						
						JButton ok = new JButton("OK");
						panel.add(ok);
						
						ok.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								restaurant.editMenuItem(item, itemName, itemPrice);
								updateMenuList();
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
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	public void updateMenuList()
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
		
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panel.setVisible(false);
		panel.removeAll();
		panel.add(buttonsPanel);
		panel.add(scrollPane);
		panel.setVisible(true);
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
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		
		return table;
	}
	
	public JTable createTableWithBaseProducts()
	{
		ArrayList<MenuItem> menu = restaurant.getMenu();
		int size = 0;
		for (MenuItem item : menu)
		{
			if (item instanceof BaseProduct)
			{
				size++;
			}
		}
		Object[][] data = new Object[size][2];
		int i = 0;
		for (MenuItem item : menu)
		{
			if (item instanceof BaseProduct)
			{
				data[i][0] = item.getName();
				data[i][1] = item.computePrice();
				i++;
			}
		}
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		
		return table;
	}
}
