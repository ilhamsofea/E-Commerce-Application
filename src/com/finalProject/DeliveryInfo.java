package com.finalProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;

public class DeliveryInfo extends JFrame {

	private JPanel contentPane;
	private JLabel lblAddressLabel;
	private JLabel lblPhoneLabel;
	private JTextField addressTextField;
	private JTextField phoneTextField;
	private JLabel lblExampleLabel;
	private JButton btnEnterButton;
	private JButton btnCancel;
	private JLabel lblRecipientNameLabel;
	private JTextField nameTextField;
	private boolean paymentSuccessful;
	private CartFrame cartFrame;
	private JFrame cartItemsFrame;
	private JDialog cardPaymentDialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartFrame frame = new CartFrame();
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
	public DeliveryInfo(CartFrame cartFrame, JFrame cartItemsFrame) {
		this.cartFrame = cartFrame;
		this.cartItemsFrame = cartItemsFrame;
		setTitle("Delivery Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblAddressLabel = new JLabel("Address:");
		lblAddressLabel.setForeground(new Color(0, 0, 64));
		lblAddressLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAddressLabel.setBounds(20, 114, 111, 14);
		contentPane.add(lblAddressLabel);

		lblPhoneLabel = new JLabel("Phone Number:");
		lblPhoneLabel.setForeground(new Color(0, 0, 64));
		lblPhoneLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPhoneLabel.setBounds(20, 172, 111, 14);
		contentPane.add(lblPhoneLabel);

		addressTextField = new JTextField();
		addressTextField.setBounds(141, 111, 258, 20);
		contentPane.add(addressTextField);
		addressTextField.setColumns(10);

		phoneTextField = new JTextField();
		phoneTextField.setColumns(10);
		phoneTextField.setBounds(141, 169, 258, 20);
		contentPane.add(phoneTextField);

		lblExampleLabel = new JLabel("eg: 10, Eoulmandang-ro 2-gil, Mapo-gu, Seoul");
		lblExampleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		lblExampleLabel.setBounds(141, 130, 258, 14);
		contentPane.add(lblExampleLabel);

		btnEnterButton = new JButton("Enter");
		btnEnterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String address = addressTextField.getText();
				String phoneNum = phoneTextField.getText();

				// create an array list to hold error messages
				ArrayList<String> errorMessages = new ArrayList<>();
				// try-catch blocks for every input validation
				try {
					// validate name input
					checkName(name);
				} catch (Exception ex) {
					errorMessages.add(ex.getMessage());
					nameTextField.setText(""); // if wrong, text field is emptied
				}
				try {
					// validate address
					checkAddress(address);
				} catch (InvalidAddressException ex) {
					errorMessages.add(ex.getMessage());
					addressTextField.setText("");
				}

				try {
					// validate phone number
					checkPhoneNumber(phoneNum);
				} catch (InvalidPhoneNumberException ex) {
					errorMessages.add(ex.getMessage());
					phoneTextField.setText("");
				}

				// if there are no error messages, show success message and reset form
				if (errorMessages.isEmpty()) {
					JOptionPane.showMessageDialog(DeliveryInfo.this, "Success! Enter your card details for payment.",
							"Success Message", JOptionPane.INFORMATION_MESSAGE);
					resetForm(); // form reset to indicate form is submitted successfully
					dispose();
					processCardPayment();
				} else {
					// if there are error messages, concatenate them into a single string and
					// display them
					String errorMessage = "Please correct the following errors:\n";
					for (String message : errorMessages) {
						errorMessage += "- " + message + "\n";
					}
					JOptionPane.showMessageDialog(null, errorMessage, "Input Error", JOptionPane.ERROR_MESSAGE);
				}

			}

			// methods to validate the inputs that throws custom exceptions for each inputs
			// name validation method
			private void checkName(String name) throws InvalidFullNameException {
				// to check if name text field is empty
				if (name.trim().isEmpty()) {
					throw new InvalidFullNameException("You forgot to fill the name text field. Please fill it.");
				}
				// to check if name contains only alphabetic characters and spaces
				if (!name.matches("[a-zA-Z ]+")) {
					throw new InvalidFullNameException("Name is not valid.");
				}
			} // end name validation method
				// address validation method

			private void checkAddress(String address) throws InvalidAddressException {
				// check if address text field is empty
				if (address.isEmpty()) {
					throw new InvalidAddressException("You forgot to fill the address text field. Please fill it.");
				}
				// regular expression for a valid address format: number, street name, city,
				// state
				if (!address.matches(
						"\\d+,[a-zA-Z0-9\\p{L}\\s\\-.,'’]+,[a-zA-Z\\p{L}\\s\\-.,'’]+,[a-zA-Z\\p{L}\\s\\-.,'’]+")) {
					throw new InvalidAddressException(
							"Please enter a valid address in the format: 'number, street name, city, state'");
				}
			}// end address validation method
				// phone number validation method

			private void checkPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
				// check if phone number text field is empty
				if (phoneNumber.isEmpty()) {
					throw new InvalidPhoneNumberException(
							"You forgot to fill the phone number text field. Please fill it.");
				}
				// check if phone number in correct format
				if (!phoneNumber.matches("\\d{2} \\d{4}-\\d{4}")) {
					throw new InvalidPhoneNumberException("Proper format for a phone number is 'XX XXXX-XXXX'"); // X
																													// represents
																													// a
																													// digit
				}
			}// end phone number validation method

			// method to clear all text fields if the application form is correctly filled
			private void resetForm() {
				nameTextField.setText("");
				addressTextField.setText("");
				phoneTextField.setText("");

			} // end of method
		}); // end ok button

		btnEnterButton.setBounds(229, 227, 89, 23);
		contentPane.add(btnEnterButton);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(328, 227, 89, 23);
		contentPane.add(btnCancel);

		lblRecipientNameLabel = new JLabel("Recipient Name:");
		lblRecipientNameLabel.setForeground(new Color(0, 0, 64));
		lblRecipientNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblRecipientNameLabel.setBounds(20, 63, 111, 14);
		contentPane.add(lblRecipientNameLabel);

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(141, 60, 258, 20);
		contentPane.add(nameTextField);
	}

	public boolean isPaymentSuccessful() {
		return paymentSuccessful;

	}

	public void processCardPayment() {

		cardPaymentDialog = new JDialog();
		cardPaymentDialog.setTitle("Card Payment");
		cardPaymentDialog.setSize(338, 226);
		cardPaymentDialog.setModal(true);
		cardPaymentDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JTextField cardNumberField = new JTextField(16);
		JTextField expiryDateField = new JTextField(5);
		JTextField cvvField = new JTextField(3);

		JLabel cardNumberLabel = new JLabel("Card Number:");
		JLabel expiryDateLabel = new JLabel("Expiry Date:");
		JLabel cvvLabel = new JLabel("CVV:");

		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cardNumber = cardNumberField.getText();
				String expiryDate = expiryDateField.getText();
				String cvv = cvvField.getText();

				if (isValidCardDetails(cardNumber, expiryDate, cvv)) {
					// Disable the pay button to prevent multiple clicks
					payButton.setEnabled(false);

					// Create a progress bar to indicate the code sending process
					JProgressBar progressBar = new JProgressBar();
					progressBar.setIndeterminate(true);
					progressBar.setString("Sending Code...");
					progressBar.setStringPainted(true);

					// Create a panel to hold the progress bar
					JPanel panel = new JPanel();
					panel.setLayout(new GridLayout(1, 1));
					panel.add(progressBar);

					// Display the panel with the progress bar
					JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE,
							JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
					JDialog progressDialog = optionPane.createDialog(cardPaymentDialog, "Sending Code");
					progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

					// Schedule a timer to close the progress dialog after 3 seconds
					Timer timer = new Timer(3000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							progressDialog.dispose(); // Close the progress dialog
						}
					});
					timer.setRepeats(false); // Only fire the timer once
					timer.start(); // Start the timer

					// Show the progress dialog
					progressDialog.setVisible(true);

					// Perform the code sending process in the background
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							// Simulate the code sending process
							Thread.sleep(100);

							return null;
						}

						@Override
						protected void done() {
							generateCodeDialog(); // Display the code dialog
						}
					};

					// Start the worker thread
					worker.execute();
				} else {
					JOptionPane.showOptionDialog(cardPaymentDialog,
							"Payment failed! Please check your card details based on these format:\n\n 1) Card Numbers should be 10 digits\n2) Expiry date should be in MM/YY format\n3) CVV must be 3 digits!",
							"Payment Failed", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
				}
			}

			// Method to generate and display the code dialog
			private void generateCodeDialog() {
				// Generate random payment code of 5 digits
				Random random = new Random();
				int code1 = random.nextInt(100000);

				// Show a notification of the sent code
				JOptionPane.showOptionDialog(cardPaymentDialog, "Code sent: " + code1, "Code Sent",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				// Enable the text field and update the progress bar after a delay
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

						// Create a label for the code field
						JLabel codeLabel = new JLabel("Enter code:");
						codeLabel.setHorizontalAlignment(SwingConstants.CENTER);

						// Create a text field for entering the code
						JTextField codeField = new JTextField();
						codeField.setColumns(10);

						// Create a panel to hold the label and text field
						JPanel panel = new JPanel();
						panel.setLayout(new GridLayout(2, 1));
						panel.add(codeLabel);
						panel.add(codeField);

						// Display the panel with the text field
						int option = JOptionPane.showOptionDialog(cardPaymentDialog, panel, "Code Verification",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

						if (option == JOptionPane.OK_OPTION) {
							// Get the entered code
							String enteredCode = codeField.getText();

							// Compare the entered code with the generated code
							if (enteredCode.equals(Integer.toString(code1))) {
								// Display success message and perform necessary actions
								Random random = new Random();
								int shippingNumber1 = random.nextInt(1000);
								int shippingNumber2 = random.nextInt(1000);
								int shippingNumber3 = random.nextInt(1000);

								// Display the shipping numbers using JOptionPane
								String shippingNumbers = "Your shipping number:\n" + shippingNumber1 + " "
										+ shippingNumber2 + " " + shippingNumber3;
								JOptionPane.showOptionDialog(cardPaymentDialog,
										"Payment successful! Thank you for shopping with us!\n\n" + shippingNumbers,
										"Payment Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
										null, null, null);
								cardPaymentDialog.dispose();
								paymentSuccessful = true; // Payment is successful
								cartFrame.clearCartItems();
								cartItemsFrame.dispose(); // Dispose the cartItemsFrame
								// cartFrame.dispose(); // Dispose the cartFrame
							} else {
								// Display error message for incorrect code
								JOptionPane.showOptionDialog(cardPaymentDialog, "Invalid code entered. Payment failed!",
										"Payment Failed", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
										null, null);
								payButton.setEnabled(true); // Enable the pay button
							}
						} else {
							payButton.setEnabled(true); // Enable the pay button
						}
					}
				});
			}
		}); // end pay button

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(cardPaymentDialog,
						"Are you sure you want to cancel the payment?", "Cancel Payment", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					// Perform necessary actions for canceling the payment
					cardPaymentDialog.dispose();
				}
			}
		});

		JPanel contentPane = new JPanel();
		cardPaymentDialog.setContentPane(contentPane);

		JLabel selectBankLabel = new JLabel("Select Bank:");

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Woori Bank", "Hana Bank", "Kookmin Bank", "Kakao Bank", "Nonghyup Bank" }));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(selectBankLabel, GroupLayout.PREFERRED_SIZE, 82,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(cardNumberLabel)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(cardNumberField,
										GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(expiryDateLabel)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(expiryDateField,
										GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(cvvLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cvvField, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(payButton)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cancelButton)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(selectBankLabel, GroupLayout.PREFERRED_SIZE, 22,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cardNumberLabel, GroupLayout.PREFERRED_SIZE, 22,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cardNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(expiryDateLabel, GroupLayout.PREFERRED_SIZE, 22,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(expiryDateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cvvLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(cvvField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(payButton)
								.addComponent(cancelButton))
						.addContainerGap()));
		cardPaymentDialog.setVisible(true);
	}

	private boolean isValidCardDetails(String cardNumber, String expiryDate, String cvv) {
		// Check if card number, expiry date, and CVV are not empty
		if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
			return false;
		}

		// Check if card number has 10 digits
		if (cardNumber.length() != 10) {
			return false;
		}

		// Check if expiry date is in the valid format (MM/YY)
		if (!expiryDate.matches("\\d{2}/\\d{2}")) {
			return false;
		}

		// Check if CVV has 3 digits
		if (cvv.length() != 3) {
			return false;
		}

		return true;
	}

	// Custom Exception classes for each input validations that takes one argument,
	// that is message
	// name validation exception class
	class InvalidFullNameException extends Exception {
		public InvalidFullNameException(String message) {
			super(message);
		}
	}// end name validation exception class
		// address validation exception class

	class InvalidAddressException extends Exception {
		public InvalidAddressException(String message) {
			super(message);
		}
	}// end address validation exception class
		// phone number validation exception class

	class InvalidPhoneNumberException extends Exception {
		public InvalidPhoneNumberException(String message) {
			super(message);
		}
	}// end phone number validation exception class

}
