package com.finalProject;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Login extends JFrame {

	private JPanel contentPane;
	private JLabel unameLbl;
	private JLabel pwLbl;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPw;
	private JLabel loginLbl;
	private JButton loginBtn;
	private JLabel lblDontHaveAn;
	private JLabel lblNewLabel_4;
	private JPanel decoPanel;
	private JLabel lblLogoLabel;
	private JCheckBox showPw;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
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
	public Login() {
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		unameLbl = new JLabel("Username:");
		unameLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		unameLbl.setForeground(new Color(0, 0, 64));
		unameLbl.setBounds(363, 187, 86, 14);
		contentPane.add(unameLbl);

		pwLbl = new JLabel("Password:");
		pwLbl.setForeground(new Color(0, 0, 64));
		pwLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pwLbl.setBounds(363, 242, 86, 14);
		contentPane.add(pwLbl);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(459, 185, 175, 20);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);

		textFieldPw = new JPasswordField();
		textFieldPw.setBounds(459, 240, 175, 20);
		contentPane.add(textFieldPw);
		textFieldPw.setColumns(10);

		loginBtn = new JButton("Login");
		loginBtn.setBackground(new Color(0, 0, 64));
		loginBtn.setForeground(new Color(255, 255, 255));
		loginBtn.setFont(new Font("Times New Roman", Font.BOLD, 11));
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// read file
				try {
				    BufferedReader fileReader = new BufferedReader(new FileReader("users.txt"));
				    String line;
				    boolean foundUser = false;
				    while ((line = fileReader.readLine()) != null) {
				        if (line.isEmpty()) {
				            foundUser = false; // Reset foundUser flag for each user
				        } else {
				            String[] tokens = line.split(":");
				            if (tokens.length == 2) {
				                String key = tokens[0].trim();
				                String value = tokens[1].trim();
				                if (key.equals("Username")) {
				                    if (value.equals(textFieldUsername.getText())) {
				                        foundUser = true;
				                    }
				                } else if (key.equals("Password") && foundUser) {
				                    if (value.equals(textFieldPw.getText())) {
				                    	// If login is successful, set the logged-in user in UserDetails
				                        UserDetails userDetails = UserDetails.getInstance();
				                        userDetails.setLoggedInUser(textFieldUsername.getText());
				                        
				                        // Open the Dashboard frame
				                        Dashboard dashboard = new Dashboard();
				                        dashboard.setVisible(true);
				                        dispose();
				                        break; // Exit the loop once a matching username and password are found
				                    }
				                }
				            }
				        }
				    }
				    fileReader.close();

				    if (!foundUser) {
				        JOptionPane.showMessageDialog(Login.this, "Username or Password is wrong");
				    }
				} catch (FileNotFoundException e1) {
				    e1.printStackTrace();
				} catch (IOException e1) {
				    e1.printStackTrace();
				}

			}
		});
		
		loginBtn.setBounds(498, 308, 89, 23);
		contentPane.add(loginBtn);

		lblDontHaveAn = new JLabel("Don't have an account?");
		lblDontHaveAn.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		lblDontHaveAn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDontHaveAn.setBounds(363, 342, 166, 14);
		contentPane.add(lblDontHaveAn);

		lblNewLabel_4 = new JLabel("SIGN UP");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Signup sign = new Signup();
				sign.setVisible(true);
				dispose();
			}
		});
		lblNewLabel_4.setBounds(539, 342, 66, 14);
		contentPane.add(lblNewLabel_4);

		decoPanel = new JPanel();
		decoPanel.setBackground(new Color(0, 0, 64));
		decoPanel.setBounds(0, 0, 300, 501);
		contentPane.add(decoPanel);
		decoPanel.setLayout(null);

		lblLogoLabel = new JLabel("Image Here");
		Image img1 = new ImageIcon(this.getClass().getResource("/pic/logo.png")).getImage();
		Image newImg1 = img1.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		lblLogoLabel.setIcon(new ImageIcon(newImg1));
		lblLogoLabel.setBounds(41, 141, 200, 200);
		lblLogoLabel.setForeground(new Color(255, 255, 255));
		decoPanel.add(lblLogoLabel);
		
		showPw = new JCheckBox("Show password");
		showPw.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		showPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 togglePasswordVisibility();
			}
		});
		showPw.setBounds(459, 267, 175, 23);
		contentPane.add(showPw);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setForeground(new Color(0, 0, 64));
		panel.setBounds(298, 0, 366, 59);
		contentPane.add(panel);
				panel.setLayout(null);
		
				loginLbl = new JLabel("Login");
				loginLbl.setBounds(147, 11, 74, 35);
				panel.add(loginLbl);
				loginLbl.setForeground(new Color(255, 255, 255));
				loginLbl.setHorizontalAlignment(SwingConstants.CENTER);
				loginLbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
	}
	
	//other methods
	 private void togglePasswordVisibility() {
	        if (showPw.isSelected()) {
	            textFieldPw.setEchoChar((char) 0); // Show password as plain text
	        } else {
	        	textFieldPw.setEchoChar('*'); // Hide password with asterisks
	        }
	    }
}
