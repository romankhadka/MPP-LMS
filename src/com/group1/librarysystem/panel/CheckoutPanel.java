package com.group1.librarysystem.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.group1.librarysystem.resources.ThemeColor;

import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;


public class CheckoutPanel  extends JFrame implements LibWindow {
	
	private static final long serialVersionUID = 1L;
	private boolean isInitialized = false;
	private JTextField txtMemberId;
	private JTextField txtISBN;
	private JPanel mainPanel;
	private JFrame parentFrame;

	ControllerInterface ci = new SystemController();
	public static final CheckoutPanel INSTANCE = new CheckoutPanel();

	public CheckoutPanel() {
		init();
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
	}

	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}

	@Override
	public void init() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) northPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel northLabel = new JLabel("Checkout Book");
		northLabel.setFont(ThemeColor.titleText);
		northLabel.setBounds(42, 10, 319, 36);
		northPanel.add(northLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));

		JPanel listPanel = new JPanel();
		middlePanel.add(listPanel, BorderLayout.NORTH);
		listPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		txtMemberId = getLabelAndText(listPanel, "Member Id", true);
		txtMemberId.setFont(ThemeColor.formLabel);
		txtISBN = getLabelAndText(listPanel, "Book ISBN", true);
		txtISBN.setFont(ThemeColor.formLabel);
		JLabel label = new JLabel("");
		listPanel.add(label);

		JPanel checkoutPanel = new JPanel();
		listPanel.add(checkoutPanel);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setFont(new Font("Roboto Slab", Font.BOLD, 13));
		btnCheckout.setForeground(Color.WHITE);
		btnCheckout.setBackground(SystemColor.desktop);
		btnCheckout.setPreferredSize(new Dimension(110, 50));

		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookCopy bc = ci.checkoutBook(txtMemberId.getText(), txtISBN.getText());
					JOptionPane.showMessageDialog(parentFrame, bc.getBook().getTitle() +
							" [Copy number: " + bc.getCopyNum() + "] is checked out successfully");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame, e1.getMessage());
					return;
				}
			}
		});
		checkoutPanel.add(btnCheckout, BorderLayout.CENTER);

	}

	private JTextField getLabelAndText(JPanel parentPanel, String strLbl, boolean bEnable) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		parentPanel.add(panel);

		JPanel lblPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) lblPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(20);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(lblPanel);
		JLabel label = new JLabel(strLbl);
		lblPanel.add(label);
		label.setEnabled(bEnable);
		label.setFont(ThemeColor.formLabel);

		JPanel txtPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) txtPanel.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(txtPanel);
		JTextField txtField = new JTextField();
		txtField.setMargin(new Insets(5, 10, 5, 10));
		txtField.setBounds(12, 30, 220, 39);
		txtPanel.add(txtField);
		txtField.setColumns(20);
		txtField.setEnabled(bEnable);
		
		return txtField;
	}


}
