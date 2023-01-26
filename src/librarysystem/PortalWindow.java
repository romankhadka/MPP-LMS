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

import com.group1.librarysystem.panel.AddLibraryMemberPanel;
import com.group1.librarysystem.panel.AddNewBookCopyPanel;
import com.group1.librarysystem.panel.AddNewBookPanel;
import com.group1.librarysystem.panel.CheckoutPanel;
import com.group1.librarysystem.panel.SearchBookPanel;
import com.group1.librarysystem.panel.SearchMemberPanel;
import com.group1.librarysystem.resources.ThemeColor;

import dataaccess.Auth;


public class PortalWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 3842471112999905561L;

	private boolean isInitialized = false;
	private String username = "";
	private Auth auth = Auth.BOTH;
	private JLabel lblWecome;
	private JButton btnLogout;
	JList<String> funcList;
	JPanel cards;

	private boolean[] enabledFlags;

	
	public static String[] funcItems = { "Home", "Member", "   Add New Members",
			"   View Checkouts", "   All Member IDs",
			"Book", "   Add New Books", "   Search Overdue Books",
			"   Add New Copies","   All Book IDs", "   Check out" };

	private static int FUNC_HOME = 0;
	private static int FUNC_MEMBER = 1;
	private static int FUNC_CREATE_NEW_MEM = 2;
	private static int FUNC_SEARCH_MEM = 3;
	private static int FUNC_ALL_MEM = 4;
	private static int FUNC_BOOK = 5;
	private static int FUNC_ADD_NEW_BOOK = 6;
	private static int FUNC_SEARCH_BOOK = 7;
	private static int FUNC_ADD_NEW_COPY = 8;
	private static int FUNC_ALL_BOOK = 9;
	private static int FUNC_CHECKOUT = 10;

	public static final PortalWindow INSTANCE = new PortalWindow();

	public PortalWindow() {
		init(username, auth);
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
		username = name;
		auth = au;
		lblWecome.setText("Welcome " + username);

		for (int i = 0; i < enabledFlags.length; i++)
			enabledFlags[i] = false;

		enabledFlags[FUNC_HOME] = true;
		enabledFlags[FUNC_MEMBER] = true;
		enabledFlags[FUNC_ALL_MEM] = true;
		enabledFlags[FUNC_BOOK] = true;
		enabledFlags[FUNC_ALL_BOOK] = true;
		if (au == Auth.ADMIN || au == Auth.BOTH) {
			enabledFlags[FUNC_CREATE_NEW_MEM] = true;

			enabledFlags[FUNC_ADD_NEW_BOOK] = true;
			enabledFlags[FUNC_ADD_NEW_COPY] = true;
		}
		if (au == Auth.LIBRARIAN || au == Auth.BOTH) {
			enabledFlags[FUNC_BOOK] = true;
			enabledFlags[FUNC_CHECKOUT] = true;
			enabledFlags[FUNC_SEARCH_MEM] = true;
			enabledFlags[FUNC_SEARCH_BOOK] = true;
		}

		funcList.setSelectedIndex(FUNC_HOME);
	}

	private void init(String name, Auth au) {
		username = name;
		auth = au;
		for (int i =0; i<funcItems.length; i++) {
			funcItems[i] = "      " + funcItems[i];
		}
		enabledFlags = new boolean[funcItems.length];
		
		
		// [left] create List of functionalities
		funcList = new JList<String>(funcItems);
		funcList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		funcList.setSelectionModel(new DisabledItemSelectionModel());
		funcList.setCellRenderer(new DisabledItemListCellRenderer());
		funcList.setFont(new Font("Roboto Slab", Font.PLAIN, 20));	
		funcList.setForeground(ThemeColor.black);	
		funcList.setBackground(ThemeColor.backgroundColor);	
		funcList.setFixedCellHeight(40);

		// [right] create panels
		createPanels();
		// [middle] create split
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, funcList, cards);
		splitPane.setDividerLocation(380);
		add(splitPane, BorderLayout.CENTER);

		setSize(1500, 800);
		setUser(username, au);
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
		// -----top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		lblWecome = new JLabel("Welcome " + username);
		lblWecome.setFont(new Font("Roboto Slab", Font.BOLD, 15));
		topPanel.add(lblWecome);

		btnLogout = new JButton("Logout");
		addLogoutButtonListener(btnLogout);
		topPanel.add(btnLogout);
		add(topPanel, BorderLayout.NORTH);

		// ----middle Panel
		cards = new JPanel(new CardLayout());
		cards.add(new JPanel(), funcItems[FUNC_HOME]);
		cards.add(AddLibraryMemberPanel.getNewMemberPanel(this), funcItems[FUNC_CREATE_NEW_MEM]);
		cards.add(AddNewBookCopyPanel.INSTANCE.getMainPanel(this),funcItems[FUNC_ADD_NEW_COPY]);
		cards.add(SearchMemberPanel.INSTANCE.getMainPanel(this), funcItems[FUNC_SEARCH_MEM]);
		cards.add(SearchBookPanel.getNewSearchBookPanel(this), funcItems[FUNC_SEARCH_BOOK]);
		cards.add(CheckoutPanel.INSTANCE.getMainPanel(this), funcItems[FUNC_CHECKOUT]);
		cards.add(AddNewBookPanel.getNewBookPanel(this), funcItems[FUNC_ADD_NEW_BOOK]);
		cards.add(AllMemberIdsWindow.INSTANCE.getMainPanel(), funcItems[FUNC_ALL_MEM]);
		cards.add(AllBookIdsWindow.INSTANCE.getMainPanel(), funcItems[FUNC_ALL_BOOK]);

		// connect JList elements to CardLayout panels
		funcList.addListSelectionListener(event -> {
			String value = funcList.getSelectedValue().toString();
			
			if (value.compareTo(funcItems[FUNC_ALL_MEM]) == 0)
				AllMemberIdsWindow.INSTANCE.setData();
			else if (value.compareTo(funcItems[FUNC_ALL_BOOK]) == 0)
				AllBookIdsWindow.INSTANCE.setData();
			
			funcList.setSelectionBackground(ThemeColor.primaryColor);
			funcList.setSelectionForeground(ThemeColor.white);

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
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			if (enabledFlags[index]) {
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}

			Component comp = super.getListCellRendererComponent(list, value, index, false, false);
			comp.setEnabled(false);
			return comp;
		}
	}

	private class DisabledItemSelectionModel extends DefaultListSelectionModel {

		private static final long serialVersionUID = 1L;

		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (enabledFlags[index0]) {
				super.setSelectionInterval(index0, index0);
			} else {
				/*
				 * The previously selected index is before this one, so walk forward to find the
				 * next selectable item.
				 */
				if (getAnchorSelectionIndex() < index0) {
					for (int i = index0; i < enabledFlags.length; i++) {
						if (enabledFlags[i]) {
							super.setSelectionInterval(i, i);
							return;
						}
					}
				} /*
					 * Otherwise, walk backward to find the next selectable item.
					 */ else {
					for (int i = index0; i >= 0; i--) {
						if (enabledFlags[i]) {
							super.setSelectionInterval(i, i);
							return;
						}
					}
				}
			}
		}
	}
}
