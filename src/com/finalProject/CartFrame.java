package com.finalProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CartFrame extends JFrame {
	private JTable cartTable;
	private JButton btnViewCartButton;
	private JButton btnBackButton;
	private List<Product> cartItems = new ArrayList<>();
	private JButton btnAddToCartButton;
	private int selectedRowIndex = -1;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new CartFrame();
		});
	}

	public CartFrame() {

		// Create the JTable instance
		cartTable = createCartTable();

		// Create a scroll pane and add the JTable to it
		JScrollPane scrollPane = new JScrollPane(cartTable);

		// Create a panel to hold the scroll pane and buttons
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Create a panel for the new panel above the table
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(0, 0, 64));
		northPanel.setBounds(0, 0, 664, 50);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lblCartLabel = new JLabel("CART");
		lblCartLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblCartLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCartLabel.setForeground(new Color(255, 255, 255));
		lblCartLabel.setBounds(165, 11, 293, 39);
		northPanel.add(lblCartLabel);

		// Add the new panel to the content pane in the north position
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Set the content pane of the frame
		setContentPane(contentPane);

		// Set frame properties
		setTitle("Cart");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		loadData();

		// Create the "View Cart" and "Back" buttons
		btnBackButton = new JButton("Back");
		btnBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Dashboard frame = new Dashboard();
				frame.setVisible(true);
				dispose();
			}
		});
		btnViewCartButton = new JButton("View Cart");
		btnViewCartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new frame for displaying the cart items
				JFrame cartItemsFrame = new JFrame("Cart Items");
				cartItemsFrame.setSize(600, 400);
				cartItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

				// Create a panel to hold the cart items table and total price label
				JPanel contentPane = new JPanel();
				contentPane.setLayout(new BorderLayout());
				cartItemsFrame.setContentPane(contentPane);

				// Create the JTable instance for displaying the cart items
				String[] columnNames = { "Brand", "Model", "Category", "Features", "Total Price" };
				DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
					@Override
					public boolean isCellEditable(int row, int column) { // make the table uneditable
						return false;
					}
				};
				// Add the cart items to the table model
				for (Product item : cartItems) {
					String features = item.getSelectedFeature1() + "\n " + item.getSelectedFeature2() + "\n "
							+ item.getSelectedFeature3();
					Object[] rowData = { item.getBrand(), item.getModel(), item.getCategory(), features,
							item.getPrice() };
					model.addRow(rowData);
				}

				JTable cartItemsTable = new JTable(model);

				cartItemsTable.getSelectionModel().addListSelectionListener(ex -> {
					if (!ex.getValueIsAdjusting()) {
						selectedRowIndex = cartItemsTable.getSelectedRow();
					}
				});

				// Set the preferred width for the "Features" column
				int featuresColumnIndex = model.findColumn("Features");
				cartItemsTable.getColumnModel().getColumn(featuresColumnIndex).setPreferredWidth(200); // Set your
																										// desired width

				// Set the custom cell renderer for the "Features" column
				cartItemsTable.getColumnModel().getColumn(featuresColumnIndex)
						.setCellRenderer(new DefaultTableCellRenderer() {
							@Override
							public Component getTableCellRendererComponent(JTable table, Object value,
									boolean isSelected, boolean hasFocus, int row, int column) {
								JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
										hasFocus, row, column);
								label.setText((String) value);
								label.setToolTipText((String) value); // Set the full text as the tooltip
								return label;
							}
						});

				// Create a scroll pane and add the cart items table to it
				JScrollPane scrollPane = new JScrollPane(cartItemsTable);
				contentPane.add(scrollPane, BorderLayout.CENTER);

				// Calculate the total price of all items
				double totalPrice = 0.0;
				int totalRows = model.getRowCount();
				int priceColumnIndex = model.findColumn("Total Price");
				for (int row = 0; row < totalRows; row++) {
					double price = Double.parseDouble(model.getValueAt(row, priceColumnIndex).toString());
					totalPrice += price;
				}

				// Create a container panel for the total price label
				JPanel totalPanel = new JPanel();
				totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JLabel totalPriceLabel = new JLabel("Overall Price: " + totalPrice);
				totalPanel.add(totalPriceLabel);
				contentPane.add(totalPanel, BorderLayout.NORTH);

				// Create buttons for Pay and Back
				JButton btnPayButton = new JButton("Pay");
				btnPayButton.addActionListener((new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (cartItems.isEmpty()) {
							JOptionPane.showMessageDialog(cartItemsFrame, "Cart cannot be empty!", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(CartFrame.this,
									"Before paying, please insert these informations for shipping purpose!");
							DeliveryInfo frame = new DeliveryInfo(CartFrame.this, cartItemsFrame);
							frame.setVisible(true);
						}
					}
				}));

				JButton btnBackButton = new JButton("Back");
				btnBackButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						cartItemsFrame.dispose();
					}
				});
				JButton btnRemoveButton = new JButton("Remove");
				btnRemoveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (selectedRowIndex != -1) {
							// Remove the selected row from the table model
							DefaultTableModel model = (DefaultTableModel) cartItemsTable.getModel();
							double removedPrice = Double
									.parseDouble(model.getValueAt(selectedRowIndex, priceColumnIndex).toString());
//				            model.removeRow(selectedRowIndex);
							cartItems.remove(selectedRowIndex);
							model.removeRow(selectedRowIndex);
							selectedRowIndex = -1; // Reset the selected row index
							JOptionPane.showMessageDialog(CartFrame.this, "Item removed from cart!");

							// Recalculate the total price
							double totalPrice = 0.0;
							int totalRows = model.getRowCount();
							for (int row = 0; row < totalRows; row++) {
								double price = Double.parseDouble(model.getValueAt(row, priceColumnIndex).toString());
								totalPrice += price;
							}
							// Update the overall price label
							totalPriceLabel.setText("Overall Price: " + totalPrice);
						}

					}
				});

				// Create a panel to hold the buttons
				JPanel buttonPanel = new JPanel();
				buttonPanel.add(btnPayButton);
				buttonPanel.add(btnBackButton);
				buttonPanel.add(btnRemoveButton);
				contentPane.add(buttonPanel, BorderLayout.SOUTH);

				// Set frame properties
				cartItemsFrame.setVisible(true);

			}
		}); // end viewCart button

		// Create a panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnViewCartButton);
		buttonPanel.add(btnBackButton);

		// Add the panel to the content pane at the bottom (South)
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}

	private JTable createCartTable() {
		// Define column names
		String[] columnNames = { "Brand", "Model", "Price (in $)", "Category" };

		// Create a DefaultTableModel with no data
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make all cells uneditable
			}
		};

//		// Create the JTable using the DefaultTableModel
		JTable table = new JTable(model);
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					showProductDetails((String) table.getValueAt(selectedRow, 0),
							(String) table.getValueAt(selectedRow, 1), (Double) table.getValueAt(selectedRow, 2),
							(String) table.getValueAt(selectedRow, 3));
				}
			}
		});

		return table;
	}

	private void loadData() {
		ProductModel productModel = new ProductModel();
		DefaultTableModel defaultTableModel = (DefaultTableModel) cartTable.getModel();
		defaultTableModel.setRowCount(0); // Clear existing data
		for (Product product : productModel.findAll()) {
			defaultTableModel.addRow(
					new Object[] { product.getBrand(), product.getModel(), product.getPrice(), product.getCategory() });
		}
		for (Product product : productModel.findAll2()) {
			defaultTableModel.addRow(
					new Object[] { product.getBrand(), product.getModel(), product.getPrice(), product.getCategory() });
		}
		for (Product product : productModel.findAll3()) {
			defaultTableModel.addRow(
					new Object[] { product.getBrand(), product.getModel(), product.getPrice(), product.getCategory() });
		}
		cartTable.setRowHeight(50);
		cartTable.getTableHeader().setReorderingAllowed(false);
	}

	private void showProductDetails(String brand, String model, double price, String category) {
		JFrame detailsFrame = new JFrame("Product Details");
		detailsFrame.setBounds(100, 100, 551, 560);
		detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		detailsFrame.getContentPane().add(contentPane);
		contentPane.setLayout(null);

		JLabel lblTopicLabel = new JLabel("Features");
		lblTopicLabel.setForeground(new Color(255, 255, 255));
		lblTopicLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblTopicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopicLabel.setBounds(126, 0, 242, 47);
		contentPane.add(lblTopicLabel);

		JLabel lblFeature1Label = new JLabel("Feature 1:");
		lblFeature1Label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblFeature1Label.setBounds(10, 113, 119, 14);
		contentPane.add(lblFeature1Label);

		JRadioButton rdbtnFeature1ARadioButton = new JRadioButton("New radio button");
		rdbtnFeature1ARadioButton.setBounds(10, 144, 165, 23);
		contentPane.add(rdbtnFeature1ARadioButton);

		JRadioButton rdbtnFeature1BRadioButton = new JRadioButton("New radio button");
		rdbtnFeature1BRadioButton.setBounds(181, 144, 165, 23);
		contentPane.add(rdbtnFeature1BRadioButton);

		JRadioButton rdbtnFeature1CRadioButton = new JRadioButton("New radio button");
		rdbtnFeature1CRadioButton.setBounds(348, 144, 165, 23);
		contentPane.add(rdbtnFeature1CRadioButton);

		JLabel lblFeature2Label = new JLabel("Feature 2:");
		lblFeature2Label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblFeature2Label.setBounds(10, 201, 129, 14);
		contentPane.add(lblFeature2Label);

		JRadioButton rdbtnFeature2ARadioButton = new JRadioButton("New radio button");
		rdbtnFeature2ARadioButton.setBounds(20, 234, 165, 23);
		contentPane.add(rdbtnFeature2ARadioButton);

		JRadioButton rdbtnFeature2BRadioButton = new JRadioButton("New radio button");
		rdbtnFeature2BRadioButton.setBounds(181, 234, 165, 23);
		contentPane.add(rdbtnFeature2BRadioButton);

		JRadioButton rdbtnFeature2CRadioButton = new JRadioButton("New radio button");
		rdbtnFeature2CRadioButton.setBounds(348, 234, 165, 23);
		contentPane.add(rdbtnFeature2CRadioButton);

		JLabel lblFeature3Label = new JLabel("Feature 3:");
		lblFeature3Label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblFeature3Label.setBounds(10, 276, 129, 14);
		contentPane.add(lblFeature3Label);

		JRadioButton rdbtnFeature3ARadioButton = new JRadioButton("New radio button");
		rdbtnFeature3ARadioButton.setBounds(20, 311, 165, 23);
		contentPane.add(rdbtnFeature3ARadioButton);

		JRadioButton rdbtnFeature3BRadioButton = new JRadioButton("New radio button");
		rdbtnFeature3BRadioButton.setBounds(181, 311, 165, 23);
		contentPane.add(rdbtnFeature3BRadioButton);

		JRadioButton rdbtnFeature3CRadioButton = new JRadioButton("New radio button");
		rdbtnFeature3CRadioButton.setBounds(348, 311, 165, 23);
		contentPane.add(rdbtnFeature3CRadioButton);

		// Group the radio buttons for color
		ButtonGroup feature1ButtonGroup = new ButtonGroup();
		feature1ButtonGroup.add(rdbtnFeature1ARadioButton);
		feature1ButtonGroup.add(rdbtnFeature1BRadioButton);
		feature1ButtonGroup.add(rdbtnFeature1CRadioButton);

		// Group the radio buttons for memory
		ButtonGroup feature2ButtonGroup = new ButtonGroup();
		feature2ButtonGroup.add(rdbtnFeature2ARadioButton);
		feature2ButtonGroup.add(rdbtnFeature2BRadioButton);
		feature2ButtonGroup.add(rdbtnFeature2CRadioButton);

		// Group the radio buttons for size
		ButtonGroup feature3ButtonGroup = new ButtonGroup();
		feature3ButtonGroup.add(rdbtnFeature3ARadioButton);
		feature3ButtonGroup.add(rdbtnFeature3BRadioButton);
		feature3ButtonGroup.add(rdbtnFeature3CRadioButton);

		if (category.equals("Mobile")) {
			lblFeature1Label.setText("Color: ");
			lblFeature2Label.setText("Memory:");
			lblFeature3Label.setText("Multi-Mode Charging:");
			
			if (brand.equals("Sony")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black (Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Stellar Blue  +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Crimson Red  +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("64GB(Default) +$0");
				rdbtnFeature2ARadioButton.setActionCommand("0");
				rdbtnFeature2BRadioButton.setText("128GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("256GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$30");
				rdbtnFeature3BRadioButton.setActionCommand("30");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$40");
				rdbtnFeature3CRadioButton.setActionCommand("40");
			} else if (brand.equals("Apple")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Space Gray (Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Pacific Blue +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Rose Gold +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("64GB(Default) +$0");
				rdbtnFeature2ARadioButton.setActionCommand("0");
				rdbtnFeature2BRadioButton.setText("128GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("256GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$30");
				rdbtnFeature3BRadioButton.setActionCommand("30");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$40");
				rdbtnFeature3CRadioButton.setActionCommand("40");
			} else if (brand.equals("Samsung")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Mystic Silver +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Violet Aura +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$200");
				rdbtnFeature2ARadioButton.setActionCommand("200");
				rdbtnFeature2BRadioButton.setText("256GB +$300");
				rdbtnFeature2BRadioButton.setActionCommand("300");
				rdbtnFeature2CRadioButton.setText("512GB +$400");
				rdbtnFeature2CRadioButton.setActionCommand("400");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$50");
				rdbtnFeature3BRadioButton.setActionCommand("50");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$100");
				rdbtnFeature3CRadioButton.setActionCommand("100");
			} else if (brand.equals("Google")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Just Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Clearly White +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Oh So Orange +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("64GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("128GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("256GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$50");
				rdbtnFeature3BRadioButton.setActionCommand("50");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$100");
				rdbtnFeature3CRadioButton.setActionCommand("100");
			} else if (brand.equals("OnePlus")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Green(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Ultramarine Blue +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Onyx Black +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$200");
				rdbtnFeature2ARadioButton.setActionCommand("200");
				rdbtnFeature2BRadioButton.setText("256GB +$300");
				rdbtnFeature2BRadioButton.setActionCommand("300");
				rdbtnFeature2CRadioButton.setText("512GB +$400");
				rdbtnFeature2CRadioButton.setActionCommand("400");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$50");
				rdbtnFeature3BRadioButton.setActionCommand("50");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$100");
				rdbtnFeature3CRadioButton.setActionCommand("100");
			} else if (brand.equals("Xiaomi")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Horizon Blue +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Lunar Silver +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("512GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$50");
				rdbtnFeature3BRadioButton.setActionCommand("50");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$100");
				rdbtnFeature3CRadioButton.setActionCommand("100");
			} else if (brand.equals("Oppo")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Nebula Blue +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Sunset Orange +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$300");
				rdbtnFeature2BRadioButton.setActionCommand("300");
				rdbtnFeature2CRadioButton.setText("512GB +$400");
				rdbtnFeature2CRadioButton.setActionCommand("400");

				// Set values for the charging mode radio buttons
				rdbtnFeature3ARadioButton.setText("Fast-Wired +$20");
				rdbtnFeature3ARadioButton.setActionCommand("20");
				rdbtnFeature3BRadioButton.setText("Wireless +$50");
				rdbtnFeature3BRadioButton.setActionCommand("50");
				rdbtnFeature3CRadioButton.setText("Reverse Wireless +$100");
				rdbtnFeature3CRadioButton.setActionCommand("100");
			}
		} //ends Mobile category 
		
		//Tab category
		else if(category.equals("Tab")) {
			lblFeature1Label.setText("Color: ");
			lblFeature2Label.setText("Memory:");
			lblFeature3Label.setText("Connectivity:");
			
			if(brand.equals("Apple")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Space Gray(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Silver +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Gold +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("512GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$150");
				rdbtnFeature3BRadioButton.setActionCommand("150");
				rdbtnFeature3CRadioButton.setVisible(false);
				
			} else if(brand.equals("Samsung")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Mystic Bronze +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Mystic Silver +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("512GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setVisible(false);
				
			} if(brand.equals("Microsoft")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Matte Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Platinum +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Cobalt Blue +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("512GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$150");
				rdbtnFeature3BRadioButton.setActionCommand("150");
				rdbtnFeature3CRadioButton.setVisible(false);
				
			} if(brand.equals("Lenovo")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Slate Grey(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Platinum Grey +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Modernist Teal +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("64GB(Default) +$50");
				rdbtnFeature2ARadioButton.setActionCommand("50");
				rdbtnFeature2BRadioButton.setText("128GB +$100");
				rdbtnFeature2BRadioButton.setActionCommand("100");
				rdbtnFeature2CRadioButton.setText("256GB +$150");
				rdbtnFeature2CRadioButton.setActionCommand("150");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$80");
				rdbtnFeature3BRadioButton.setActionCommand("80");
				rdbtnFeature3CRadioButton.setVisible(false);
				
			}if(brand.equals("Huawei")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Gray(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Pearl White +$50");
				rdbtnFeature1BRadioButton.setActionCommand("50");
				rdbtnFeature1CRadioButton.setText("Forest Green +$50");
				rdbtnFeature1CRadioButton.setActionCommand("50");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("128GB(Default) +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("256GB +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("512GB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$120");
				rdbtnFeature3BRadioButton.setActionCommand("120");
				rdbtnFeature3CRadioButton.setVisible(false);
				
			} if(brand.equals("Amazon")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Plum +$20");
				rdbtnFeature1BRadioButton.setActionCommand("20");
				rdbtnFeature1CRadioButton.setText("Twilight Blue +$20");
				rdbtnFeature1CRadioButton.setActionCommand("20");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("32GB(Default) +$0");
				rdbtnFeature2ARadioButton.setActionCommand("0");
				rdbtnFeature2BRadioButton.setText("64GB +$30");
				rdbtnFeature2BRadioButton.setActionCommand("30");
				rdbtnFeature2CRadioButton.setText("128GB +$60");
				rdbtnFeature2CRadioButton.setActionCommand("60");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setVisible(false);
				rdbtnFeature3CRadioButton.setVisible(false);
				
			} if(brand.equals("Google")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Blue(Default) +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setVisible(false);
				rdbtnFeature1CRadioButton.setVisible(false);

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("64GB(Default) +$0");
				rdbtnFeature2ARadioButton.setActionCommand("0");
				rdbtnFeature2BRadioButton.setText("128GB +$100");
				rdbtnFeature2BRadioButton.setActionCommand("100");
				rdbtnFeature2CRadioButton.setText("256GB +$200");
				rdbtnFeature2CRadioButton.setActionCommand("200");

				// Set values for the connectivity radio buttons
				rdbtnFeature3ARadioButton.setText("Wi-Fi(included) +$0");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("LTE +$150");
				rdbtnFeature3BRadioButton.setActionCommand("150");
				rdbtnFeature3CRadioButton.setVisible(false);
			}
		} //ends Tab category
		else if(category.equals("Laptop")) {
			lblFeature1Label.setText("Color: ");
			lblFeature2Label.setText("Memory:");
			lblFeature3Label.setText("RAM configurations:");
			
			if(brand.equals("Dell")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Platinum Silver +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Rose Gold +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Arctic White +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("1TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
			} else if(brand.equals("HP")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Nightfall Black +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Poseidon Blue +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Natural Silver +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("1TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
			} else if(brand.equals("Lenovo")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Black +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Silver +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Iron Grey +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("1TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
				
			} else if(brand.equals("Apple")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Space Gray +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Silver +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Gold +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$200");
				rdbtnFeature2ARadioButton.setActionCommand("200");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$400");
				rdbtnFeature2BRadioButton.setActionCommand("400");
				rdbtnFeature2CRadioButton.setText("1TB +$600");
				rdbtnFeature2CRadioButton.setActionCommand("600");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$200");
				rdbtnFeature3BRadioButton.setActionCommand("200");
				rdbtnFeature3CRadioButton.setText("32GB +$400");
				rdbtnFeature3CRadioButton.setActionCommand("400");
			} else if(brand.equals("Asus")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Eclipse Grey +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Moonlight White +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Mirage Blue +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("512GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("1TB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("2TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
			} else if(brand.equals("Acer")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Silver +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Blue +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Pink +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("1TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
			} else if(brand.equals("Microsoft")) {
				// Set values for the color radio buttons
				rdbtnFeature1ARadioButton.setText("Matte Black +$0");
				rdbtnFeature1ARadioButton.setActionCommand("0");
				rdbtnFeature1BRadioButton.setText("Platinum +$0");
				rdbtnFeature1BRadioButton.setActionCommand("0");
				rdbtnFeature1CRadioButton.setText("Sandstone +$0");
				rdbtnFeature1CRadioButton.setActionCommand("0");

				// Set values for the memory radio buttons
				rdbtnFeature2ARadioButton.setText("256GB SSD +$100");
				rdbtnFeature2ARadioButton.setActionCommand("100");
				rdbtnFeature2BRadioButton.setText("512GB SSD +$200");
				rdbtnFeature2BRadioButton.setActionCommand("200");
				rdbtnFeature2CRadioButton.setText("1TB +$300");
				rdbtnFeature2CRadioButton.setActionCommand("300");

				// Set values for the RAM radio buttons
				rdbtnFeature3ARadioButton.setText("8GB RAM (included)");
				rdbtnFeature3ARadioButton.setActionCommand("0");
				rdbtnFeature3BRadioButton.setText("16GB +$100");
				rdbtnFeature3BRadioButton.setActionCommand("100");
				rdbtnFeature3CRadioButton.setText("32GB +$200");
				rdbtnFeature3CRadioButton.setActionCommand("200");
			}
		}

		btnAddToCartButton = new JButton("Add To Cart");
		btnAddToCartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateFeaturesSelected(feature1ButtonGroup, feature2ButtonGroup, feature3ButtonGroup)) {
					double totalPrice = calculateTotalPrice(price, feature1ButtonGroup, feature2ButtonGroup,
							feature3ButtonGroup);
					addToCart(brand, model, totalPrice, category, feature1ButtonGroup, feature2ButtonGroup,
							feature3ButtonGroup);
					JOptionPane.showMessageDialog(CartFrame.this, "Item added to cart!");
					detailsFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(CartFrame.this, "Please select all features!");
				}
			}
		});
		btnAddToCartButton.setBounds(126, 401, 129, 23);
		contentPane.add(btnAddToCartButton);

		JButton btnBackButton = new JButton("Back");
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detailsFrame.dispose();
			}
		});
		btnBackButton.setBounds(280, 401, 129, 23);
		contentPane.add(btnBackButton);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(0, 0, 535, 47);
		contentPane.add(panel);

		detailsFrame.setVisible(true);
	}

	private double calculateTotalPrice(double basePrice, ButtonGroup colorButtonGroup, ButtonGroup memoryButtonGroup,
			ButtonGroup sizeButtonGroup) {
		// Get the selected values of the features
		String selectedColor = colorButtonGroup.getSelection().getActionCommand();
		String selectedMemory = memoryButtonGroup.getSelection().getActionCommand();
		String selectedSize = sizeButtonGroup.getSelection().getActionCommand();

		// Convert the selected values to double
		double colorPrice = Double.parseDouble(selectedColor);
		double memoryPrice = Double.parseDouble(selectedMemory);
		double sizePrice = Double.parseDouble(selectedSize);

		// Calculate the total price
		double totalPrice = basePrice + colorPrice + memoryPrice + sizePrice;

		return totalPrice;
	}

	private void addToCart(String brand, String model, double price, String category, ButtonGroup feature1ButtonGroup,
			ButtonGroup feature2ButtonGroup, ButtonGroup feature3ButtonGroup) {
		// Check if all radio buttons are selected
		if (isButtonGroupSelected(feature1ButtonGroup) && isButtonGroupSelected(feature2ButtonGroup)
				&& isButtonGroupSelected(feature3ButtonGroup)) {
			// All radio buttons are selected, add the item to the cart
			Product item = new Product(brand, model, price, category);
			// Set the selected values for the radio buttons
			item.setSelectedColor(getSelectedButtonText(feature1ButtonGroup));
			item.setSelectedMemory(getSelectedButtonText(feature2ButtonGroup));
			item.setSelectedSize(getSelectedButtonText(feature3ButtonGroup));
			cartItems.add(item); // add the AddToCart items in cartItems list
		} else {
			// Display an error message if any radio button is not selected
			JOptionPane.showMessageDialog(CartFrame.this, "Please select all features before adding to cart!");
		}
	}

	private boolean isButtonGroupSelected(ButtonGroup buttonGroup) {
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return true;
			}
		}
		return false;
	}

	private String getSelectedButtonText(ButtonGroup buttonGroup) {
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}

	private boolean validateFeaturesSelected(ButtonGroup feature1ButtonGroup, ButtonGroup feature2ButtonGroup,
			ButtonGroup feature3ButtonGroup) {
		return feature1ButtonGroup.getSelection() != null && feature2ButtonGroup.getSelection() != null
				&& feature3ButtonGroup.getSelection() != null;
	}
	
	 public void clearCartItems() {
	        cartItems.clear();
	    }


} // end class
