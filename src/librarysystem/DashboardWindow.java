package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import com.group1.librarysystem.panel.NewMemberPanel;
import com.group1.librarysystem.panel.NewBookCopyPanel;
import com.group1.librarysystem.panel.NewBookPanel;
import com.group1.librarysystem.panel.CheckoutPanel;
import com.group1.librarysystem.panel.SearchBooksPanel;
import com.group1.librarysystem.panel.SearchMembersPanel;
import com.group1.librarysystem.resources.ThemeColor;

import dataaccess.Auth;


public class DashboardWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	private boolean isInitialized = false;
	private String userName = "";
	private Auth authorization = Auth.BOTH;
	private JLabel dashboardLabel;
	private JButton logoutButton;
	JList<String> menuItemList;
	JPanel cards;

	private boolean[] enabledItems;
	public static String[] menuItems = { "Dashboard", "Library Member", "  New Library Member",
			"   Book Checkouts", "   All Members",
			"Book", "   New Book", "   Overdue Books",
			"   New Book Copy","   Book IDs", "   Checkout Book" };

	private static int DASHBOARD = 0;
	private static int MEMBER = 1;
	private static int CREATE_NEW_MEM = 2;
	private static int SEARCH_MEM = 3;
	private static int ALL_MEM = 4;
	private static int BOOK = 5;
	private static int ADD_NEW_BOOK = 6;
	private static int SEARCH_BOOK = 7;
	private static int ADD_NEW_COPY = 8;
	private static int ALL_BOOK = 9;
	private static int CHECKOUT = 10;

	public static final DashboardWindow INSTANCE = new DashboardWindow();

	public DashboardWindow() {
		init(userName, authorization);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@Override
	public void init() {

	}

	public void setUser(String name, Auth au) {
		userName = name;
		authorization = au;
		dashboardLabel.setText("Welcome " + userName);

		for (int i = 0; i < enabledItems.length; i++)
			enabledItems[i] = false;

		enabledItems[DASHBOARD] = true;
		enabledItems[MEMBER] = true;
		enabledItems[ALL_MEM] = true;
		enabledItems[BOOK] = true;
		enabledItems[ALL_BOOK] = true;
		if (au == Auth.ADMIN || au == Auth.BOTH) {
			enabledItems[CREATE_NEW_MEM] = true;
			enabledItems[ADD_NEW_BOOK] = true;
			enabledItems[ADD_NEW_COPY] = true;
		}
		if (au == Auth.LIBRARIAN || au == Auth.BOTH) {
			enabledItems[BOOK] = true;
			enabledItems[CHECKOUT] = true;
			enabledItems[SEARCH_MEM] = true;
			enabledItems[SEARCH_BOOK] = true;
		}
		menuItemList.setSelectedIndex(DASHBOARD);
	}

	private void init(String name, Auth au) {
		userName = name;
		authorization = au;
		for (int i =0; i<menuItems.length; i++) {
			menuItems[i] = "      " + menuItems[i];
		}
		enabledItems = new boolean[menuItems.length];
		menuItemList = new JList<String>(menuItems);
		menuItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuItemList.setSelectionModel(new DisabledItemSelectionModel());
		menuItemList.setCellRenderer(new DisabledItemListCellRenderer());
		menuItemList.setFont(new Font("Roboto Slab", Font.PLAIN, 20));	
		menuItemList.setForeground(ThemeColor.black);	
		menuItemList.setBackground(ThemeColor.backgroundColor);	
		menuItemList.setFixedCellHeight(40);

		createPanels();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuItemList, cards);
		splitPane.setDividerLocation(380);
		add(splitPane, BorderLayout.CENTER);
		setSize(1500, 800);
		setUser(userName, au);
		isInitialized = true;
		
		addWindowListener(new WindowAdapter() {
	         @Override
	         public void windowClosing(WindowEvent e) {
	        	 LibrarySystem.hideAllWindows();
	        	 LoginWindow.INSTANCE.reset();
	        	 LibrarySystem.INSTANCE.setVisible(true);
	         }
	     });

	}

	private void createPanels() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		dashboardLabel = new JLabel("Welcome " + userName);
		dashboardLabel.setFont(new Font("Roboto Slab", Font.BOLD, 15));
		topPanel.add(dashboardLabel);

		logoutButton = new JButton("Logout");
		addLogoutButtonListener(logoutButton);
		topPanel.add(logoutButton);
		add(topPanel, BorderLayout.NORTH);

		cards = new JPanel(new CardLayout());
		cards.add(new JPanel(), menuItems[DASHBOARD]);
		cards.add(NewMemberPanel.getNewMemberPanel(this), menuItems[CREATE_NEW_MEM]);
		cards.add(NewBookCopyPanel.INSTANCE.getMainPanel(this),menuItems[ADD_NEW_COPY]);
		cards.add(SearchMembersPanel.INSTANCE.getMainPanel(this), menuItems[SEARCH_MEM]);
		cards.add(SearchBooksPanel.getNewSearchBookPanel(this), menuItems[SEARCH_BOOK]);
		cards.add(CheckoutPanel.INSTANCE.getMainPanel(this), menuItems[CHECKOUT]);
		cards.add(NewBookPanel.getNewBookPanel(this), menuItems[ADD_NEW_BOOK]);
		cards.add(AllMemberIdsWindow.INSTANCE.getMainPanel(), menuItems[ALL_MEM]);
		cards.add(AllBookIdsWindow.INSTANCE.getMainPanel(), menuItems[ALL_BOOK]);

		menuItemList.addListSelectionListener(event -> {
			String value = menuItemList.getSelectedValue().toString();
			if (value.compareTo(menuItems[ALL_MEM]) == 0)
				AllMemberIdsWindow.INSTANCE.setData();
			else if (value.compareTo(menuItems[ALL_BOOK]) == 0)
				AllBookIdsWindow.INSTANCE.setData();
			
			menuItemList.setSelectionBackground(ThemeColor.primaryColor);
			menuItemList.setSelectionForeground(ThemeColor.white);

			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, value);
		});
	}

	private void addLogoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.reset();
			LibrarySystem.INSTANCE.setVisible(true);

		});
	}

	private class DisabledItemListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected,boolean cellHasFocus) {
			if (enabledItems[index]) 
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
			Component comp = super.getListCellRendererComponent(list, value, index, false, false);
			comp.setEnabled(false);
			return comp;
		}
	}

	private class DisabledItemSelectionModel extends DefaultListSelectionModel {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (enabledItems[index0]) {
				super.setSelectionInterval(index0, index0);
			} else {
				if (getAnchorSelectionIndex() < index0) {
					for (int i = index0; i < enabledItems.length; i++) {
						if (enabledItems[i]) {
							super.setSelectionInterval(i, i);
							return;
						}
					}
				} else {
					for (int i = index0; i >= 0; i--) {
						if (enabledItems[i]) {
							super.setSelectionInterval(i, i);
							return;
						}
					}
				}
			}
		}
	}
}
