package com.group1.librarysystem.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;

public class AddLibraryMemberPanel {

	private AddLibraryMemberPanel() {
	}
	private static JPanel panel;
	private static JTextField id;
	private static JTextField firstName;
	private static JTextField lastName;
	private static JTextField street;
	private static JTextField city;
	private static JTextField state;
	private static JTextField zip;
	private static JTextField telephoneNo;

	private static ControllerInterface controllerInterface = new SystemController();

	public static Component getNewMemberPanel(JFrame frame) {
		return getPanel(frame);
	}

	private static JPanel getPanel(JFrame frame) {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		panel.setBounds(100, 100, 870, 468);
		panel.setLayout(null);

		JLabel lblAddNewMember = new JLabel("Add New Member");
		lblAddNewMember.setFont(new Font("Fira Code", Font.BOLD, 18));
		lblAddNewMember.setForeground(Color.DARK_GRAY);
		lblAddNewMember.setBounds(51, 29, 270, 30);
		panel.add(lblAddNewMember);
		
		id = new JTextField();
		id.setBounds(62, 127, 148, 39);
		id.setMargin(new Insets(5, 10, 5, 10));
		panel.add(id);
		id.setColumns(10);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId.setBounds(65, 109, 70, 15);
		panel.add(lblId);

		firstName = new JTextField();
		firstName.setColumns(10);
		firstName.setBounds(241, 127, 282, 39);
		firstName.setMargin(new Insets(5, 10, 5, 10));
		panel.add(firstName);

		JLabel lblId_1 = new JLabel("First Name");
		lblId_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1.setBounds(244, 109, 254, 15);
		panel.add(lblId_1);

		lastName = new JTextField();
		lastName.setColumns(10);
		lastName.setBounds(560, 127, 282, 39);
		lastName.setMargin(new Insets(5, 10, 5, 10));
		panel.add(lastName);

		JLabel lblId_1_1 = new JLabel("Last Name");
		lblId_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1.setBounds(563, 109, 143, 15);
		panel.add(lblId_1_1);

		street = new JTextField();
		street.setColumns(10);
		street.setBounds(62, 221, 211, 39);
		street.setMargin(new Insets(5, 10, 5, 10));
		panel.add(street);

		city = new JTextField();
		city.setColumns(10);
		city.setBounds(288, 221, 200, 39);
		city.setMargin(new Insets(5, 10, 5, 10));
		panel.add(city);

		JLabel lblId_1_2 = new JLabel("City");
		lblId_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2.setBounds(291, 203, 197, 15);
		panel.add(lblId_1_2);

		state = new JTextField();
		state.setColumns(10);
		state.setBounds(503, 221, 174, 39);
		state.setMargin(new Insets(5, 10, 5, 10));
		panel.add(state);

		JLabel lblId_1_1_1 = new JLabel("State");
		lblId_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1.setBounds(506, 203, 171, 15);
		panel.add(lblId_1_1_1);

		zip = new JTextField();
		zip.setColumns(10);
		zip.setBounds(706, 221, 135, 39);
		zip.setMargin(new Insets(5, 10, 5, 10));
		panel.add(zip);

		JLabel lblId_1_2_1 = new JLabel("Zip");
		lblId_1_2_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_1.setBounds(709, 203, 254, 15);
		panel.add(lblId_1_2_1);

		telephoneNo = new JTextField();
		telephoneNo.setColumns(10);
		telephoneNo.setBounds(62, 301, 232, 39);
		telephoneNo.setMargin(new Insets(5, 10, 5, 10));
		panel.add(telephoneNo);

		JLabel lblId_1_1_1_1 = new JLabel("Telephone Number");
		lblId_1_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1_1.setBounds(65, 283, 229, 15);
		panel.add(lblId_1_1_1_1);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblStreet.setBounds(65, 203, 208, 15);
		panel.add(lblStreet);

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBounds(51, 94, 807, 266);
		panel.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> {
			String fN = firstName.getText();
			String lN = lastName.getText();
			String zipCode = zip.getText();
			String phone = telephoneNo.getText();
			if (id.getText().equals("") || fN.equals("") || lN.equals("") || street.getText().equals("")
					|| city.getText().equals("") || state.getText().equals("") || zipCode.equals("")
					|| telephoneNo.getText().equals("")) {
				JOptionPane.showMessageDialog(frame, "Please fill all the fields");
			} else if (!fN.matches("[a-zA-Z]*") || !lN.matches("[a-zA-Z]*")) {
				JOptionPane.showMessageDialog(frame, "Names cannot have numbers");
			} else if (!zipCode.matches("\\d{5}")) {
				JOptionPane.showMessageDialog(frame, "Zip Code should be of 5 digits.");
			} else if (!phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")) {
				JOptionPane.showMessageDialog(frame, "Phone Number is invalid");
			} else {
				Address address = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
				LibraryMember member = new LibraryMember(id.getText(), firstName.getText(), lastName.getText(),
						telephoneNo.getText(), address);
				try {
					controllerInterface.addMember(member);
				} catch (LibrarySystemException err) {
					JOptionPane.showMessageDialog(frame, err.getMessage());
					return;
				}
				clearInputField();
				JOptionPane.showMessageDialog(frame, "Member Successfully Added");
			}

		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(SystemColor.desktop);
		btnAdd.setBounds(756, 378, 102, 41);
		panel.add(btnAdd);

		JButton btnCancel = new JButton("Reset");
		btnCancel.addActionListener(e -> {
			clearInputField();
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(642, 378, 102, 41);
		panel.add(btnCancel);

		return panel;

	}

	private static void clearInputField() {
		id.setText("");	
		firstName.setText("");
		lastName.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		telephoneNo.setText("");

	}
}
