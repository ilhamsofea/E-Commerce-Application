package com.finalProject;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.Color;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JLabel registerLbl;
	private JLabel usernameLabel;
	private JLabel pwLabel;
	private JButton regBtn;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPw;
	private JLabel confirmPwLbl;
	private JPasswordField textFieldConfirmPw;
	private JCheckBox showPw;
	private JButton cancelBtn;
	private JLabel nameLabel;
	private JTextField textFieldName;
	private JPanel panel;
	private JLabel lblNewLabel;
	private ArrayList<String> errorMessages = new ArrayList<>();
	private JLabel lblLogoLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup frame = new Signup();
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
	public Signup() {

		setTitle("Sign Up Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		usernameLabel.setForeground(new Color(0, 0, 64));
		usernameLabel.setBounds(93, 209, 166, 14);
		contentPane.add(usernameLabel);

		pwLabel = new JLabel("Password:");
		pwLabel.setForeground(new Color(0, 0, 64));
		pwLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		pwLabel.setBounds(93, 256, 166, 14);
		contentPane.add(pwLabel);

		regBtn = new JButton("Register");
		regBtn.setBackground(new Color(0, 0, 64));
		regBtn.setForeground(new Color(255, 255, 255));
		regBtn.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textFieldName.getText();
				String username = textFieldUsername.getText();
				String password = textFieldPw.getText();

				errorMessages.clear();

				try {
					validateName(name);
				} catch (InvalidNameException ex) {
					errorMessages.add(ex.getMessage());
					textFieldName.setText("");
				}

				try {
					validateUsername(username);
				} catch (InvalidUsernameException ex) {
					errorMessages.add(ex.getMessage());
					textFieldUsername.setText("");
				}

				try {
					validatePassword(password);
				} catch (InvalidPasswordException ex) {
					errorMessages.add(ex.getMessage());
					textFieldPw.setText("");
					textFieldConfirmPw.setText("");
				}

				if (errorMessages.isEmpty()) {
					saveUserData(name, username, password);
					JOptionPane.showMessageDialog(Signup.this, "You are registered, thank you!", "Success Message",
							JOptionPane.INFORMATION_MESSAGE);
					resetForm();
					Login frame = new Login();
					frame.setVisible(true);
					dispose();
				} else {
					String errorMessage = "Please correct the following errors:\n";
					for (String message : errorMessages) {
						errorMessage += "- " + message + "\n";
					}
					JOptionPane.showMessageDialog(Signup.this, errorMessage, "Input Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		regBtn.setBounds(269, 379, 100, 23);
		contentPane.add(regBtn);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(269, 206, 234, 20);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);

		textFieldPw = new JPasswordField();
		textFieldPw.setBounds(269, 253, 234, 20);
		contentPane.add(textFieldPw);
		textFieldPw.setColumns(10);

		confirmPwLbl = new JLabel("Re-enter Password");
		confirmPwLbl.setForeground(new Color(0, 0, 64));
		confirmPwLbl.setFont(new Font("Times New Roman", Font.BOLD, 13));
		confirmPwLbl.setBounds(93, 305, 166, 14);
		contentPane.add(confirmPwLbl);

		textFieldConfirmPw = new JPasswordField();
		textFieldConfirmPw.setColumns(10);
		textFieldConfirmPw.setBounds(269, 302, 234, 20);
		contentPane.add(textFieldConfirmPw);

		showPw = new JCheckBox("Show password");
		showPw.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		showPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				togglePasswordVisibility();
			}
		});
		showPw.setBounds(269, 329, 158, 23);
		contentPane.add(showPw);

		cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(new Color(255, 255, 255));
		cancelBtn.setFont(new Font("Times New Roman", Font.BOLD, 13));
		cancelBtn.setBackground(new Color(0, 0, 64));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login();
				frame.setVisible(true);
//				int response = JOptionPane.showConfirmDialog(Signup.this, "Are you sure you want to cancel register?",
//						"Alert", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//				if (response == JOptionPane.YES_OPTION) {
				dispose(); // Close the signup window if cancelled
//				} else if (response == JOptionPane.NO_OPTION) {
//
//				}

			}
		});

		cancelBtn.setBounds(403, 379, 100, 23);
		contentPane.add(cancelBtn);

		nameLabel = new JLabel("Name:");
		nameLabel.setForeground(new Color(0, 0, 64));
		nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		nameLabel.setBounds(93, 163, 166, 14);
		contentPane.add(nameLabel);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(269, 160, 234, 20);
		contentPane.add(textFieldName);

		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(0, 0, 664, 61);
		contentPane.add(panel);
		panel.setLayout(null);

		registerLbl = new JLabel("Register");
		registerLbl.setForeground(new Color(255, 255, 255));
		registerLbl.setBounds(186, 11, 278, 46);
		panel.add(registerLbl);
		registerLbl.setFont(new Font("Times New Roman", Font.BOLD, 30));
		registerLbl.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel = new JLabel("COME AND JOIN US!");
		lblNewLabel.setForeground(new Color(0, 0, 64));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 71, 664, 54);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Password must be at least 6 characters long.");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		lblNewLabel_1.setBounds(269, 277, 234, 14);
		contentPane.add(lblNewLabel_1);

	} // end constructor

	// methods to validate inputs
	private void validateName(String name) throws InvalidNameException {
		if (name.isEmpty()) {
			throw new InvalidNameException("Name is required.");
		} 
		// to check if name contains only alphabetic characters and spaces
		else if (!name.matches("[a-zA-Z ]+")) {
			throw new InvalidNameException("Name is not valid.");
		}
	}

	private void validateUsername(String username) throws InvalidUsernameException {
		if (username.isEmpty()) {
			throw new InvalidUsernameException("Username is required.");
		} else if (isUsernameExists(username)) {
			throw new InvalidUsernameException("Username already exists! Please choose a different username.");
		}
	}

	private void validatePassword(String password) throws InvalidPasswordException {
		if (password.isEmpty()) {
			throw new InvalidPasswordException("Password is required.");
		} else if (password.length() < 6) {
			throw new InvalidPasswordException("Password must be at least 6 characters long.");
		} else if (!password.equals(textFieldConfirmPw.getText())) {
			throw new InvalidPasswordException("Password does not match!");
		}
	}

	private void saveUserData(String name, String username, String password) {
		File file = new File("users.txt");
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter buffer = new PrintWriter(fileWriter);

			buffer.println("Full Name:" + textFieldName.getText());
			buffer.println("Username:" + username);
			if (password.equals(textFieldConfirmPw.getText())) {
				buffer.println("Password:" + password);
			}
			buffer.println();
			buffer.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resetForm() {
		textFieldName.setText("");
		textFieldUsername.setText("");
		textFieldPw.setText("");
		textFieldConfirmPw.setText("");
	}

	private void togglePasswordVisibility() {
		if (showPw.isSelected()) {
			textFieldPw.setEchoChar((char) 0); // Show password as plain text
			textFieldConfirmPw.setEchoChar((char) 0); // Show password as plain text
		} else {
			textFieldPw.setEchoChar('*'); // Hide password with asterisks
			textFieldConfirmPw.setEchoChar('*'); // Hide password with asterisks
		}
	}

	private boolean isUsernameExists(String username) {
		try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(":");
				if (tokens.length == 2 && tokens[0].equals("Username") && tokens[1].equals(username)) {
					return true; // Username already exists
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // Username does not exist
	}

	// Custom Exception classes for each input validations that takes one argument,
	// that is message
	public class InvalidNameException extends Exception {
		public InvalidNameException(String message) {
			super(message);
		}
	}

	public class InvalidUsernameException extends Exception {
		public InvalidUsernameException(String message) {
			super(message);
		}
	}

	public class InvalidPasswordException extends Exception {
		public InvalidPasswordException(String message) {
			super(message);
		}
	}

} // end Signup
