package com.finalProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

public class Dashboard extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblMainPage;
	private JComboBox comboBox;
	private JLabel userLabel;
	private String loggedInUser; // Store the currently logged-in user
	private JLabel logoutLabel;
	private JLabel lblIconLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblICartLabel;
	private JLabel lblNewLabel;
	private JButton btnPurchaseButton;
	private JLabel lblBGLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Dashboard() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(0, 0, 541, 50);
		contentPane.add(panel);
		panel.setLayout(null);

		lblMainPage = new JLabel("MAIN PAGE");
		lblMainPage.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblMainPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainPage.setForeground(Color.WHITE);
		lblMainPage.setBounds(107, 11, 293, 39);
		panel.add(lblMainPage);

		logoutLabel = new JLabel("");
		logoutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int response = JOptionPane.showConfirmDialog(Dashboard.this, "Are you sure you want to logout?",
						"Confirm Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					Login frame = new Login();
					frame.setVisible(true);
				} else {
					// do nothing
				}
			}
		});

		Image img3 = new ImageIcon(this.getClass().getResource("/pic/logout.png")).getImage();
		Image newImg3 = img3.getScaledInstance(46, 39, Image.SCALE_DEFAULT);
		logoutLabel.setIcon(new ImageIcon(newImg3));
		logoutLabel.setBounds(485, 11, 46, 39);
		panel.add(logoutLabel);

		userLabel = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/pic/user.png")).getImage();
		Image newImg2 = img2.getScaledInstance(46, 39, Image.SCALE_DEFAULT);
		userLabel.setIcon(new ImageIcon(newImg2));
		userLabel.setBounds(437, 9, 46, 41);
		panel.add(userLabel);
		userLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Handle user label click event
				showUserAccountDetails();
			}
		});

		comboBox = new JComboBox();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				String selectedValue = (String) combo.getSelectedItem();

				if (selectedValue.equals("Mobile")) {
					// Redirect to the next frame for Mobile selection
					MobileFrame mobileFrame = new MobileFrame();
					mobileFrame.setVisible(true);
					dispose(); // Close the current frame if needed
				} else if (selectedValue.equals("Tab")) {
					// Redirect to the next frame for Tab selection
					TabFrame tabFrame = new TabFrame();
					tabFrame.setVisible(true);
					dispose(); // Close the current frame if needed
				} else if (selectedValue.equals("Laptop")) {
					// Redirect to the next frame for Laptop selection
					LaptopFrame laptopFrame = new LaptopFrame();
					laptopFrame.setVisible(true);
					dispose(); // Close the current frame if needed
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Mobile", "Tab", "Laptop" }));
		comboBox.setBounds(166, 218, 199, 22);
		contentPane.add(comboBox);

		lblIconLabel = new JLabel("Image Here");
		Image img4 = new ImageIcon(this.getClass().getResource("/pic/info.png")).getImage();
		Image newImg4 = img4.getScaledInstance(68, 53, Image.SCALE_DEFAULT);
		lblIconLabel.setIcon(new ImageIcon(newImg4));
		lblIconLabel.setBounds(231, 129, 68, 53);
		contentPane.add(lblIconLabel);

		lblNewLabel_1 = new JLabel("For product informations, choose here.");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel_1.setBounds(119, 193, 298, 14);
		contentPane.add(lblNewLabel_1);

		lblICartLabel = new JLabel("Image Here");
		Image img6 = new ImageIcon(this.getClass().getResource("/pic/cart.png")).getImage();
		Image newImg6 = img6.getScaledInstance(68, 53, Image.SCALE_DEFAULT);
		lblICartLabel.setIcon(new ImageIcon(newImg6));
		lblICartLabel.setBounds(231, 259, 68, 53);
		contentPane.add(lblICartLabel);

		lblNewLabel = new JLabel("To make purchases, click here.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel.setBounds(119, 323, 298, 14);
		contentPane.add(lblNewLabel);

		btnPurchaseButton = new JButton("Purchase");
		btnPurchaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CartFrame frame = new CartFrame();
				frame.setVisible(true);
				dispose();

			}
		});
		btnPurchaseButton.setForeground(new Color(255, 255, 255));
		btnPurchaseButton.setBackground(new Color(0, 0, 64));
		btnPurchaseButton.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnPurchaseButton.setBounds(223, 348, 89, 23);
		contentPane.add(btnPurchaseButton);

		lblBGLabel = new JLabel("");
		Image img5 = new ImageIcon(this.getClass().getResource("/pic/logoBG.png")).getImage();
		Image newImg5 = img5.getScaledInstance(541, 366, Image.SCALE_DEFAULT);
		lblBGLabel.setIcon(new ImageIcon(newImg5));
		lblBGLabel.setBounds(0, 50, 541, 366);
		contentPane.add(lblBGLabel);

	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	private void showUserAccountDetails() {
		UserDetails userDetails = UserDetails.getInstance();
		String loggedInUser = userDetails.getLoggedInUser();

		// Read the user account details for the currently logged-in user
		Map<String, String[]> accountDetails = getUserAccountDetails();

		// Get the details for the logged-in user
		String[] accountInfo = accountDetails.get(loggedInUser);
		if (accountInfo == null) {
			JOptionPane.showMessageDialog(this, "User account details not found", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Format the user account details into a string message
		StringBuilder message = new StringBuilder();
		String fullName = accountInfo[0];
		String username = loggedInUser;

		message.append("Current User:").append("\n\n");
		message.append("Name: ").append(fullName).append("\n");
		message.append("Username: ").append(username).append("\n\n");

		// Show the user account details in a dialog box
		JOptionPane.showMessageDialog(this, message.toString(), "User Account Details",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private Map<String, String[]> getUserAccountDetails() {
		Map<String, String[]> userDetails = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			String[] userInfo = new String[3];
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] tokens = line.split(":");
					if (tokens.length == 2) {
						String key = tokens[0];
						String value = tokens[1];
						userInfo[count++] = value;
					}
				} else {
					if (count == 3) {
						userDetails.put(userInfo[1], userInfo);
					}
					userInfo = new String[3];
					count = 0;
				}
			}
			if (count == 3) {
				userDetails.put(userInfo[1], userInfo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userDetails;
	}
}
