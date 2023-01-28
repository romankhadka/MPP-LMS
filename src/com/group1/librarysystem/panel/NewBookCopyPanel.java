package com.group1.librarysystem.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;

public class NewBookCopyPanel extends JFrame implements LibWindow {
	private static final long serialVersionUID = 5538744765326735797L;

	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JFrame parentFrame;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtCheckoutLeng;
	private JTextField txtCopies;

	ControllerInterface ci = new SystemController();
	public static final NewBookCopyPanel INSTANCE = new NewBookCopyPanel();

	public NewBookCopyPanel() {
		init();
	}

	public void init() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel northPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) northPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel northLabel = new JLabel("Add New Book Copy");
		northLabel.setFont(new Font("Fira Code Retina", Font.BOLD, 20));
		northPanel.add(northLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));

		JPanel listPanel = new JPanel();
		middlePanel.add(listPanel, BorderLayout.NORTH);
		listPanel.setLayout(new GridLayout(0, 1, 0, 0));

		txtISBN = getLabelAndText(listPanel, "Book ISBN", true);
		txtTitle = getLabelAndText(listPanel, "Title", false);
		txtAuthor = getLabelAndText(listPanel, "Authors", false);
		txtCheckoutLeng = getLabelAndText(listPanel, "Max Checkout Length", false);
		txtCopies = getLabelAndText(listPanel, "Current Copies", false);

		JLabel label = new JLabel("");
		listPanel.add(label);

		JPanel AddPanel = new JPanel();
		listPanel.add(AddPanel);

		JButton btnSearch = new JButton("Search Book");
		btnSearch.setForeground(SystemColor.desktop);
		btnSearch.setBackground(Color.WHITE);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Book b = ci.getBook(txtISBN.getText());
					updateBook(b);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame, e1.getMessage());
					return;
				}

			}
		});

		AddPanel.add(btnSearch);

		JButton btnAddCopy = new JButton("Add Copy");
		btnAddCopy.setForeground(Color.WHITE);
		btnAddCopy.setBackground(SystemColor.desktop);
		btnAddCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Book b = ci.addNewBookCopy(txtISBN.getText());
					JOptionPane.showMessageDialog(parentFrame, "New Copy has been sucessfully added");
					updateBook(b);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame, e1.getMessage());
					return;
				}
			}
		});

		AddPanel.add(btnAddCopy);

	}

	private void updateBook(Book book) {
		txtTitle.setText(" " + book.getTitle());
		txtCheckoutLeng.setText(" " + book.getMaxCheckoutLength());
		List<String> authList = new ArrayList<>();
		for (Author au : book.getAuthors()) {
			authList.add(au.getFirstName() + " " + au.getLastName());
		}
		txtAuthor.setText(" " + authList.toString());
		txtCopies.setText(" " + book.getNumCopies());
	}

	private JTextField getLabelAndText(JPanel parentPanel, String strLbl, boolean bEnable) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		parentPanel.add(panel);

		JPanel lblPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) lblPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(lblPanel);
		JLabel label = new JLabel(strLbl);
		lblPanel.add(label);
		label.setEnabled(bEnable);

		JPanel txtPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) txtPanel.getLayout();
		flowLayout_1.setHgap(5);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(txtPanel);
		JTextField txtField = new JTextField();
		txtPanel.add(txtField);
		txtField.setColumns(20);
		txtField.setMargin(new Insets(5, 10, 5, 10));
		txtField.setBounds(12, 30, 220, 39);
		txtField.setEnabled(bEnable);

		return txtField;
	}

	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

}
