package com.group1.librarysystem.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.group1.librarysystem.resources.ThemeColor;

import business.Author;
import business.ControllerInterface;
import business.SystemController;
import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

public class NewBookPanel {
	private NewBookPanel() {
	}

	private static JPanel panel;
	private static JTextField isbn;
	private static JTextField title;
	private static JTextField firstName;
	private static JTextField lastName;
	private static JTextField street;
	private static JTextField city;
	private static JTextField state;
	private static JTextField zip;
	private static JTextArea bio;
	private static JTextField telephoneNo;
	private static JComboBox<String> maxCheckoutLength;
	private static ControllerInterface controllerInterface = new SystemController();
	private static List<Author> authors = new ArrayList<>();

	public static Component getNewBookPanel(JFrame frame) {
		return getPanel(frame);
	}

	private static JPanel getPanel(JFrame frame) {
		panel = new JPanel();
		panel.setBackground(ThemeColor.backgroundColor);
		frame.setBackground(ThemeColor.backgroundColor);
		frame.setBounds(100, 100, 1163, 700);
		panel.setLayout(null);

		JLabel lblAddNewMember = new JLabel("Add New Book");
		lblAddNewMember.setFont(ThemeColor.titleText);
		lblAddNewMember.setForeground(Color.DARK_GRAY);
		lblAddNewMember.setBackground(ThemeColor.backgroundColor);
		lblAddNewMember.setBounds(42, 10, 319, 36);
		panel.add(lblAddNewMember);

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(51, 46, 807, 161);
		panel.add(bookPanel);
		bookPanel.setLayout(null);

		isbn = new JTextField();
		isbn.setMargin(new Insets(5, 10, 5, 10));
		isbn.setBounds(12, 30, 220, 39);
		bookPanel.add(isbn);
		isbn.setColumns(10);

		JLabel lblId_1 = new JLabel("ISBN");
		lblId_1.setBounds(15, 12, 138, 15);
		bookPanel.add(lblId_1);
		lblId_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));

		title = new JTextField();
		title.setBounds(252, 30, 220, 39);
		bookPanel.add(title);
		title.setColumns(10);

		JLabel lblId_1_1 = new JLabel("Title");
		lblId_1_1.setBounds(258, 12, 143, 15);
		bookPanel.add(lblId_1_1);
		lblId_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));

		String[] checkoutLengthOption = { "7", "21" };
		maxCheckoutLength = new JComboBox<String>(checkoutLengthOption);
		maxCheckoutLength.setBounds(492, 30, 220, 39);
		maxCheckoutLength.setSelectedIndex(0);
		bookPanel.add(maxCheckoutLength);

		JLabel lblId_1_2 = new JLabel("Max Checkout Length");
		lblId_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2.setBounds(498, 12, 217, 15);
		bookPanel.add(lblId_1_2);

		JLabel lblId_1_4 = new JLabel("Authors");
		lblId_1_4.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_4.setBounds(15, 82, 138, 15);
		bookPanel.add(lblId_1_4);

		JButton authorAdd = new JButton("Add ");

		authorAdd.setForeground(Color.WHITE);
		authorAdd.setBackground(new Color(75, 0, 130));
		authorAdd.setBounds(12, 99, 76, 25);
		bookPanel.add(authorAdd);

		JLabel authorList = new JLabel("");
		authorList.setBounds(15, 134, 500, 15);
		bookPanel.add(authorList);

		JButton bookAddBtn = new JButton("Add Book");

		bookAddBtn.addActionListener(e -> {
			String maxCheckoutLengthString = (String) maxCheckoutLength.getSelectedItem();
			if (isbn.getText().equals("") || title.getText().equals("") || maxCheckoutLengthString.equals("")) {
				JOptionPane.showMessageDialog(frame, "Please fill all the fields");
			} else if (!((String) maxCheckoutLength.getSelectedItem()).matches("\\d*")) {
				JOptionPane.showMessageDialog(frame, "Max Checkout Length cannot have letters.");
			} else if (authors.size() == 0) {
				JOptionPane.showMessageDialog(frame, "Please add atleast one author");
			} else {

				Book book = new Book(isbn.getText(), title.getText(), Integer.parseInt(maxCheckoutLengthString),
						authors);
				try {
					controllerInterface.addBook(book);
				} catch (LibrarySystemException e1) {
					e1.printStackTrace();
				}
				clearBookInputField();
				authorList.setText("");
				JOptionPane.showMessageDialog(frame, "Successful Addition of new Book");
			}
		});

		bookAddBtn.setForeground(Color.WHITE);
		bookAddBtn.setBackground(SystemColor.desktop);
		bookAddBtn.setBounds(755, 220, 100, 41);
		panel.add(bookAddBtn);

		JButton btnCancel = new JButton("Clear");

		btnCancel.addActionListener(e -> {
			clearBookInputField();
		});

		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(635, 220, 100, 41);
		panel.add(btnCancel);

		JPanel authorPanel = new JPanel();
		authorPanel.setBounds(51, 273, 807, 350);
		panel.add(authorPanel);
		authorPanel.setLayout(null);

		authorAdd.addActionListener(e -> {
			authorPanel.setVisible(true);

		});

		JButton btnCancelAuthor = new JButton("Cancel");
		btnCancelAuthor.setBounds(548, 280, 100, 41);
		authorPanel.add(btnCancelAuthor);
		btnCancelAuthor.setForeground(Color.WHITE);
		btnCancelAuthor.setBackground(Color.LIGHT_GRAY);

		JButton btnAddAuthor = new JButton("Add Author");
		btnAddAuthor.setBounds(668, 280, 135, 41);
		authorPanel.add(btnAddAuthor);
		btnAddAuthor.setForeground(Color.WHITE);
		btnAddAuthor.setBackground(SystemColor.desktop);

		JPanel addressPanel = new JPanel();
		addressPanel.setBounds(0, 20, 810, 276);

		authorPanel.add(addressPanel);
		addressPanel.setLayout(null);

		firstName = new JTextField();
		addressPanel.add(firstName);
		firstName.setColumns(10);
		firstName.setMargin(new Insets(5, 10, 5, 10));
		firstName.setBounds(23, 30, 211, 39);

		JLabel lblId_1_3 = new JLabel("First Name");
		addressPanel.add(lblId_1_3);
		lblId_1_3.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_3.setBounds(26, 12, 254, 15);

		lastName = new JTextField();
		addressPanel.add(lastName);
		lastName.setColumns(10);
		lastName.setMargin(new Insets(5, 10, 5, 10));
		lastName.setBounds(255, 30, 282, 39);

		JLabel lblId_1_1_2 = new JLabel("Last Name");
		addressPanel.add(lblId_1_1_2);
		lblId_1_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_2.setBounds(255, 12, 143, 15);

		bio = new JTextArea();
		addressPanel.add(bio);
		bio.setBounds(255, 166, 437, 72);
		bio.setMargin(new Insets(5, 10, 5, 10));

		street = new JTextField();
		addressPanel.add(street);
		street.setColumns(10);
		street.setBounds(23, 99, 211, 39);
		street.setMargin(new Insets(5, 10, 5, 10));

		JLabel lblStreet_2 = new JLabel("Street");
		addressPanel.add(lblStreet_2);
		lblStreet_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblStreet_2.setBounds(26, 81, 208, 15);

		city = new JTextField();
		addressPanel.add(city);
		city.setColumns(10);
		city.setBounds(255, 99, 200, 39);
		city.setMargin(new Insets(5, 10, 5, 10));

		JLabel lblId_1_2_2 = new JLabel("City");
		addressPanel.add(lblId_1_2_2);
		lblId_1_2_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_2.setBounds(255, 81, 197, 15);

		state = new JTextField();
		addressPanel.add(state);
		state.setColumns(10);
		state.setBounds(465, 99, 174, 39);
		state.setMargin(new Insets(5, 10, 5, 10));

		JLabel lblId_1_1_1_1 = new JLabel("State");
		addressPanel.add(lblId_1_1_1_1);
		lblId_1_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1_1.setBounds(468, 81, 171, 15);

		zip = new JTextField();
		addressPanel.add(zip);
		zip.setColumns(10);
		zip.setBounds(657, 99, 135, 39);
		zip.setMargin(new Insets(5, 10, 5, 10));

		JLabel lblId_1_2_1_1 = new JLabel("Zip");
		addressPanel.add(lblId_1_2_1_1);
		lblId_1_2_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_1_1.setBounds(660, 81, 254, 15);

		JLabel lblStreet_1_1 = new JLabel("Bio");
		addressPanel.add(lblStreet_1_1);
		lblStreet_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblStreet_1_1.setBounds(255, 150, 208, 15);

		telephoneNo = new JTextField();
		telephoneNo.setColumns(10);
		telephoneNo.setBounds(23, 168, 211, 39);
		telephoneNo.setMargin(new Insets(5, 10, 5, 10));

		addressPanel.add(telephoneNo);

		JLabel lblId_1_2_1_1_1 = new JLabel("Telephone Number");
		lblId_1_2_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_1_1_1.setBounds(26, 150, 254, 15);
		addressPanel.add(lblId_1_2_1_1_1);

		btnAddAuthor.addActionListener(e -> {
			String fN = firstName.getText();
			String lN = lastName.getText();
			String zipCode = zip.getText();
			String phone = telephoneNo.getText();
			if (fN.equals("") || lN.equals("") || street.getText().equals("") || city.getText().equals("")
					|| state.getText().equals("") || zipCode.equals("") || phone.equals("")
					|| bio.getText().equals("")) {
				JOptionPane.showMessageDialog(frame, "Please fill all the fields");
			} else if (!fN.matches("[a-zA-Z]*") || !lN.matches("[a-zA-Z]*")) {
				JOptionPane.showMessageDialog(frame, "Names cannot have numbers");
			} else if (!zipCode.matches("\\d{5}")) {
				JOptionPane.showMessageDialog(frame, "Zip Code should be of 5 digits.");
			} else if (!phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")) {
				JOptionPane.showMessageDialog(frame, "Phone Number is invalid");
			} else {
				Address address = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
				Author author = new Author(fN, lN, phone, address, bio.getText());
				authors.add(author);
				List<String> authorListValue = authors.stream().map(a -> a.getFirstName()).collect(Collectors.toList());
				authorList.setText(authorListValue.toString());
				clearAuthorInputField();
				authorPanel.setVisible(false);
			}
		});

		btnCancelAuthor.addActionListener(e -> {
			clearAuthorInputField();
			authorPanel.setVisible(false);
		});

		authorPanel.setVisible(false);
		return panel;

	}

	private static void clearBookInputField() {
		isbn.setText("");
		title.setText("");
		maxCheckoutLength.setSelectedIndex(0);
	}

	private static void clearAuthorInputField() {
		firstName.setText("");
		lastName.setText("");
		street.setText("");
		city.setText("");
		telephoneNo.setText("");
		state.setText("");
		zip.setText("");
		bio.setText("");
	}

}
