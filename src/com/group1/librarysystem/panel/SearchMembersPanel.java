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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.group1.librarysystem.resources.ThemeColor;

import business.CheckoutRecordDTO;
import business.ControllerInterface;
import business.SystemController;

public class SearchMembersPanel extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	ControllerInterface ci = new SystemController();
	public static final SearchMembersPanel INSTANCE = new SearchMembersPanel();

	private JPanel mainPanel;
	private JTextField txtMemberId;
	private JTable tblRecord;
	private DefaultTableModel model;
	private JFrame parentFrame;

	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}

	SearchMembersPanel() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel AllIDsLabel = new JLabel("Checkout Record List");
		AllIDsLabel.setFont(ThemeColor.titleText);
		topPanel.add(AllIDsLabel);

		mainPanel.add(topPanel, BorderLayout.NORTH);

		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));

		JPanel middleNorth = new JPanel();
		middlePanel.add(middleNorth, BorderLayout.NORTH);
		middleNorth.setLayout(new BorderLayout(0, 50));

		createMiddleNorth(middleNorth);

		JPanel middleWest = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) middleWest.getLayout();
		flowLayout_1.setHgap(50);
		middlePanel.add(middleWest, BorderLayout.WEST);

		JPanel eastWest = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) eastWest.getLayout();
		flowLayout_2.setHgap(50);
		middlePanel.add(eastWest, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 154, 582, 287);
		middlePanel.add(scrollPane, BorderLayout.CENTER);

		tblRecord = new JTable();
		model = new DefaultTableModel();
		String[] column = { "ISBN", "Book Name", "Copy Number", "Checkout Date", "Due Date" };

		model.setColumnIdentifiers(column);
		tblRecord.setModel(model);
		scrollPane.setViewportView(tblRecord);
	}

	public void init() {

	}

	private void createMiddleNorth(JPanel parentPanel) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 50));
		parentPanel.add(panel);

		JPanel lblPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) lblPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(lblPanel);
		JLabel label = new JLabel("Member Id");
		label.setFont(ThemeColor.formLabel);
		lblPanel.add(label);

		JPanel txtPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) txtPanel.getLayout();
		flowLayout_1.setHgap(5);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(txtPanel);
		txtMemberId = new JTextField();
		txtMemberId.setMargin(new Insets(5, 10, 5, 10));
		txtMemberId.setBounds(12, 30, 220, 39);
		txtPanel.add(txtMemberId);
		txtMemberId.setColumns(10);

		JButton btnSearchButton = new JButton("Search");
		btnSearchButton.setFont(new Font("Roboto Slab", Font.BOLD, 13));
		btnSearchButton.setForeground(Color.WHITE);
		btnSearchButton.setBackground(SystemColor.desktop);
		btnSearchButton.setBounds(345, 40, 90, 36);

		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtMemberId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(parentFrame, "Please input Member Id");
					return;
				}

				List<CheckoutRecordDTO> list = null;
				try {
					list = ci.getCheckoutRecordByMemberId(txtMemberId.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(parentFrame, ex.getMessage());
					return;
				}

				int iRowCount = model.getRowCount();
				for (int i = 0; i < iRowCount; i++)
					model.removeRow(0);

				if (list == null)
					return;

				printDataInConsole();

				for (CheckoutRecordDTO entry : list) {
					String[] row = new String[5];
					row[0] = entry.getBookCopy().getBook().getIsbn();
					row[1] = entry.getBookCopy().getBook().getTitle();
					row[2] = " " + entry.getBookCopy().getCopyNum();
					row[3] = " " + entry.getCheckoutDate();
					row[4] = " " + entry.getDueDate();
					model.addRow(row);
					System.out.printf("| %-10s | %-20s | %11s | %13s | %13s |%n", row[0], row[1], row[2], row[3],
							row[4]);

				}

			}

			private void printDataInConsole() {
				System.out.println("");
				System.out
						.println("-----------------------------------------------------------------------------------");
				System.out
						.println("-----------------------------------------------------------------------------------");
				System.out
						.println("                         Library Management System                                 ");
				System.out
						.println("                            Checkout Record List                                   ");
				System.out
						.println("-----------------------------------------------------------------------------------");
				System.out
						.println("-----------------------------------------------------------------------------------");

				System.out.println("");
				System.out.println("Library Member ID: " + txtMemberId.getText());
				System.out.println("");

				System.out.printf(
						"-----------------------------------------------------------------------------------%n");
				System.out.printf("| %-10s | %-20s | %11s | %13s | %13s |%n", "ISBN", "BOOK NAME", "COPY NUMBER",
						"CHECKOUT DATE", "DUE DATE");
				System.out.printf(
						"-----------------------------------------------------------------------------------%n");

			}
		});
		txtPanel.add(btnSearchButton);

	}
}
