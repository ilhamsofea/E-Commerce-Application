package com.finalProject;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class TabFrame extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable jTableProduct;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JButton btnBackButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabFrame frame = new TabFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 50, 664, 330);
		contentPane.add(scrollPane);

		jTableProduct = new JTable();
		jTableProduct.setShowVerticalLines(false);
		jTableProduct.setShowHorizontalLines(false);
		jTableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jTableProduct);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(0, 0, 664, 50);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("TAB PAGE");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(165, 11, 293, 39);
		panel.add(lblNewLabel);

		btnBackButton = new JButton("Back");
		btnBackButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnBackButton.setForeground(new Color(255, 255, 255));
		btnBackButton.setBackground(new Color(0, 0, 64));
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Dashboard frame = new Dashboard();
				frame.setVisible(true);
				dispose();
			}
		});
		btnBackButton.setBounds(293, 402, 89, 23);
		contentPane.add(btnBackButton);

		loadData();

		jTableProduct.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = jTableProduct.rowAtPoint(evt.getPoint());
				int col = jTableProduct.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					Product selectedProduct = getProductAtRow(row);
					showProductDetails(selectedProduct);
				}
			}
		});
	}

	private Product getProductAtRow(int row) {
		DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
		String brand = model.getValueAt(row, 0).toString();
		String modelValue = model.getValueAt(row, 1).toString();
		double price = (Double) model.getValueAt(row, 2);
		String photo = model.getValueAt(row, 3).toString();
		String description = null;
		String category = model.getValueAt(row, 4).toString();
		return new Product(brand, modelValue, price, photo, description, category);
	}

	private void showProductDetails(Product product) {
		JFrame frame = new JFrame();
		frame.setTitle("Product Information");
	    frame.setBounds(100, 100, 580, 400);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    JPanel panel = new JPanel();
	    panel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(panel);
	    panel.setLayout(null);
	    
	    JLabel lblCategory = new JLabel("Category:");
	    lblCategory.setForeground(new Color(0, 0, 64));
		lblCategory.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCategory.setBounds(10, 37, 111, 20);
		panel.add(lblCategory);
		
		JLabel lblCategoryValue = new JLabel(product.getCategory());
		lblCategoryValue.setForeground(new Color(0, 0, 64));
		lblCategoryValue.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblCategoryValue.setBounds(158, 37, 219, 20);
		panel.add(lblCategoryValue);

	    JLabel lblBrand = new JLabel("Brand:");
	    lblBrand.setForeground(new Color(0, 0, 64));
		lblBrand.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblBrand.setBounds(10, 80, 111, 20);
		panel.add(lblBrand);

	    JLabel lblBrandValue = new JLabel(product.getBrand());
	    lblBrandValue.setForeground(new Color(0, 0, 64));
		lblBrandValue.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblBrandValue.setBounds(158, 80, 219, 20);
		panel.add(lblBrandValue);

	    JLabel lblModel = new JLabel("Model:");
	    lblModel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblModel.setForeground(new Color(0, 0, 64));
		lblModel.setBounds(10, 121, 111, 20);
		panel.add(lblModel);

	    JLabel lblModelValue = new JLabel(product.getModel());
	    lblModelValue.setForeground(new Color(0, 0, 64));
		lblModelValue.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblModelValue.setBounds(158, 121, 200, 20);
		panel.add(lblModelValue);
	    
	    JLabel imgLabel = new JLabel("");
	    String image = ("src/pic/" + product.getPhoto());
		imgLabel.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(128, 212, Image.SCALE_DEFAULT)));
		imgLabel.setBounds(368, 9, 196, 361);
		panel.add(imgLabel);
		
		JLabel lblDescription = new JLabel("Product Description:");
		lblDescription.setForeground(new Color(0, 0, 64));
		lblDescription.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblDescription.setBounds(10, 232, 126, 14);
		panel.add(lblDescription);
		
		 // Fetch the description from the ProductModel
	    ProductModel productModel = new ProductModel();
	    String description = productModel.findDescription(product.getBrand(), product.getModel());
		JLabel lblDetailsValue = new JLabel("<html>" + description + "<html>");
		lblDetailsValue.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblDetailsValue.setForeground(new Color(0, 0, 64));
		lblDetailsValue.setBackground(new Color(0, 0, 0));
		lblDetailsValue.setBounds(158, 218, 200, 113);
		panel.add(lblDetailsValue);
		
	    JLabel lblPrice = new JLabel("Price(in $):");
	    lblPrice.setForeground(new Color(0, 0, 64));
		lblPrice.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPrice.setBounds(10, 166, 111, 14);
		panel.add(lblPrice);
		
	    JLabel lblPriceValue = new JLabel(String.valueOf(product.getPrice()));
	    lblPriceValue.setForeground(new Color(0, 0, 64));
		lblPriceValue.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblPriceValue.setBounds(158, 163, 200, 20);
		panel.add(lblPriceValue);

	    frame.setVisible(true);
	}

	private void loadData() {
		ProductModel productModel = new ProductModel();
		DefaultTableModel defaultTableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		defaultTableModel.addColumn("Brand Name");
		defaultTableModel.addColumn("Model");
		defaultTableModel.addColumn("Price");
		defaultTableModel.addColumn("Photo");
		defaultTableModel.addColumn("Category");
		for (Product product : productModel.findAll2()) {
			defaultTableModel.addRow(
					new Object[] { product.getBrand(), product.getModel(), product.getPrice(), product.getPhoto(), product.getCategory() });
		}
		jTableProduct.setModel(defaultTableModel);
		jTableProduct.setRowHeight(50);
		jTableProduct.getTableHeader().setReorderingAllowed(false);
		jTableProduct.getColumnModel().getColumn(3).setCellRenderer(new ImageRender());
	}

	private class ImageRender extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String photoName = value.toString();
			ImageIcon imageIcon = new ImageIcon(
					new ImageIcon("src/pic/" + photoName).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			return new JLabel(imageIcon);
		}

	}

}
