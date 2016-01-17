/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package GUI;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import callback.callbackStringArray;
import callback.callbackUser;
import client.Client;
import common.Checks;
import common.MessageType;
import common.UpdateNotifications;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import java.awt.CardLayout;
/**
 * The common GUI part.
 * All the GUI screens inherit that class and add the specific function and gui parts to the frame.
 * @author Ohad
 *
 */
public class abstractPanel_GUI extends JFrame{

	
	//Instance variables ********************************************** 

	private static final long serialVersionUID = 1L;

	
	/**
	 * The login user
	 */
	protected static callbackUser User;				// Current user details
	/**
	 * GUI screens
	 */
	private Login_GUI LoginScreen;					//The login screen to go back when user press logout
	private abstractPanel_GUI ThisScreen;			//The current gui screen
	
	/**
	 * Server
	 */
	private Client Server;
	protected Checks checks = new Checks();
	
	//GUI variables ***************************************************
	protected JPanel contentPane;
	protected JPanel CenterCardContainer = new JPanel(new CardLayout());
	protected JPanel LeftCardContainer = new JPanel(new CardLayout());
	protected JLayeredPane TopPanel = new JLayeredPane();
	protected JLayeredPane EmptyLeftPanel = new JLayeredPane();
	protected JLayeredPane EmptyCenterPanel = new JLayeredPane();
	protected JLayeredPane ContactsPanel = new JLayeredPane();
	private JLayeredPane NotificationsPanel = new JLayeredPane();
	private JLabel RoleLabel = new JLabel("<Role> Panel");
	private JLabel WelcomLabel = new JLabel("Welcome First + Last Name");
	protected final JInternalFrame ContactFrame = new JInternalFrame("Contact List");
	private final JScrollPane ContactsSrollPane = new JScrollPane();
	private JTable ContactTable;
	private JTable NotificationsTable = new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {                
                return false;               
        };
	};
	private JLabel LostConnection;
	private JButton LogoutButton = new JButton("Logout");
	private JButton ContactsButton = new JButton("Contacts");
	private JButton NotificationsButton = new JButton("Notifications");
	private JLabel SubPanelLabel = new JLabel("");
	protected CardLayout ContainerCard;
	
	//Other variables ***************************************************
	private callbackStringArray ContactList;
	private String LastCard,LastCardNot;
	private UpdateNotifications NotificationThrerad;

	/**
	 * Create the abstract GUI panel.
	 * @param EnteredUser - Current user
	 * @param Server - server connection
	 * @param CommonBuffer - not in use
	 * @param LoginScreen - The login screen to go back when user logout
	 */

	public abstractPanel_GUI(callbackUser EnteredUser, Client Server, Login_GUI LoginScreen) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-1300)/2,(screenSize.height-750)/2, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("My Gas - Created by: Avi Rubin, Litaf Kupfer, Adir Notes & Ohad Zino.");
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		setIconImage((new ImageIcon(abstractPanel_GUI.class.getResource("/images/Main_Login_empty.png"))).getImage());
		
		this.User = EnteredUser;
		this.Server = Server;
		this.LoginScreen = LoginScreen;
		ThisScreen = this;
		setWelcomeLabel(User.getFirstName(),User.getLastName()); 		// Set welcome label
		setRoleLabel(User.getUserPrivilege());							// Set user role
		//LoginScreen.GoToLoginWindow();
		
/**
 *  Attach the card containers to the frame
 */
		contentPane.add(CenterCardContainer, BorderLayout.CENTER);
		contentPane.add(LeftCardContainer, BorderLayout.WEST);
	
/**
 * Create Top Panel
 */
		TopPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TopPanel.setBackground(SystemColor.activeCaption);
		TopPanel.setOpaque(true);
		TopPanel.setPreferredSize(new Dimension(1295,150));
		contentPane.add(TopPanel, BorderLayout.NORTH);
		
		// Role label
		RoleLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		RoleLabel.setBounds(12, 0, 453, 47);
		TopPanel.add(RoleLabel);
		
		// Welcome label
		WelcomLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		WelcomLabel.setBounds(12, 40, 358, 24);
		TopPanel.add(WelcomLabel);

		
		// Logout button
		LogoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutButton.setBounds(1148, 13, 112, 38);
		TopPanel.add(LogoutButton);
		LogoutButton.addActionListener(new ActionListener() { 					//Add action listener
			public void actionPerformed(ActionEvent e) {			
				if(User.getUserTypeId()!=2){
					User.setWhatToDo(MessageType.updateUserLogout);
					Server.handleMessageFromClient(User);
				}
				
				NotificationThrerad.setNotificationFlag(false);					//Stop notification thread
				LoginScreen.setVisible(true);									//Go to login screen
				ThisScreen.setVisible(false);									//Set invisible current screen
				
			}
		});

		LostConnection = new JLabel("<html>Server connection was lost.<br>Save all the data and exit.</html>");
		LostConnection.setForeground(new Color(220, 20, 60));
		LostConnection.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		LostConnection.setBounds(531, 13, 263, 47);
		LostConnection.setVisible(false);
		TopPanel.add(LostConnection);
		
		
		// Contact button
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		ContactsButton.setActionCommand("Contacts"); 						//Button action command
		TopPanel.add(ContactsButton);
		ContactsButton.addActionListener(new ActionListener() {				//Add action listener
			public void actionPerformed(ActionEvent e) {
		/*----- Replace Card -------*/		
				// Get Contact List - Send query
				ContactList = new callbackStringArray(MessageType.getContacts);			
				Server.handleMessageFromClient(ContactList);
				
				ContainerCard = (CardLayout)(CenterCardContainer.getLayout());
				if(!getLastComponent(CenterCardContainer.getComponents()).equals("Contacts")){
					LastCard = getLastComponent(CenterCardContainer.getComponents());	//Set in global LastCard the current component
					ContainerCard.show(CenterCardContainer, "Contacts");	
				}			
				else ContainerCard.show(CenterCardContainer,LastCard);
		/*--------------------------*/
			}
		});
		
		//Notifications Button
		NotificationsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NotificationsButton.setBounds(986, 13, 150, 38);
		if(User.getUserTypeId()==2) NotificationsButton.setVisible(false);
		NotificationsButton.setActionCommand("Notifications");
		TopPanel.add(NotificationsButton);
		NotificationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent n) {
				/*----- Replace Card -------*/		
				ContainerCard = (CardLayout)(CenterCardContainer.getLayout());
				if(!getLastComponent(CenterCardContainer.getComponents()).equals("NotificationsPanel")){
					LastCardNot = getLastComponent(CenterCardContainer.getComponents());	//Set in global LastCard the current component
					ContainerCard.show(CenterCardContainer, "NotificationsPanel");	
					NotificationThrerad.StopNewNotification();
					User.setWhatToDo(MessageType.updateNotifications);
					Server.handleMessageFromClient(User);				
				}			
				else ContainerCard.show(CenterCardContainer,LastCardNot);				
				/*--------------------------*/
			}
		});
		
		
/**
 * Create Left Panel
 */
		EmptyLeftPanel.setBackground(new Color(169, 169, 169));
		EmptyLeftPanel.setOpaque(true);
		EmptyLeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		EmptyLeftPanel.setPreferredSize(new Dimension(300,200));
		EmptyLeftPanel.setName("EmptyLeftPanel");
		LeftCardContainer.add(EmptyLeftPanel, "EmptyLeftPanel");
		
		// Logo on left panel
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		EmptyLeftPanel.add(LogoImage);
		
/**
 * Create empty Center Panel
 */		
		EmptyCenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		EmptyCenterPanel.setOpaque(true);
		CenterCardContainer.add(EmptyCenterPanel, "EmptyCenterPanel");
		EmptyCenterPanel.setName("EmptyCenterPanel");
		EmptyCenterPanel.setLayout(null);
	
		
/**
 * Create Contacts Panel
 */		
		
		ContactsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		ContactsPanel.setOpaque(true);
		CenterCardContainer.add(ContactsPanel, "Contacts");
		ContactsPanel.setLayout(new CardLayout(0, 0));
		ContactsPanel.setName("Contacts");

		// Center panel label
		SubPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SubPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		ContactsPanel.add(SubPanelLabel, "name_2211558062915");
		ContactsPanel.add(ContactFrame, "name_2211600466443");
		ContactFrame.getContentPane().setLayout(null);
		ContactsSrollPane.setBounds(12, 13, 944, 495);		
		ContactFrame.getContentPane().add(ContactsSrollPane);
		ContactFrame.setVisible(true);
		
		//Contacts list table
		ContactTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		ContactTable.setRowHeight(30);
		ContactTable.setBackground(new Color(230, 230, 250));
		ContactTable.setFillsViewportHeight(true);
		ContactsSrollPane.setViewportView(ContactTable);
		ContactTable.setFont(new Font("Tahoma", Font.PLAIN, 18));

		//Change contacts table header background
		JTableHeader header = ContactTable.getTableHeader();
		header.setBackground(Color.lightGray);

		
/**
 * Notifications Panel
 */			

		NotificationsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		NotificationsPanel.setOpaque(true);
		NotificationsPanel.setName("NotificationsPanel");
		CenterCardContainer.add(NotificationsPanel, "NotificationsPanel");
		
		JLabel NotificationsLabel = new JLabel("Notifications");
		NotificationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NotificationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		NotificationsLabel.setBounds(164, 13, 608, 42);
		NotificationsPanel.add(NotificationsLabel);
		
		JScrollPane NotificationsScrollPane = new JScrollPane();
		NotificationsScrollPane.setBounds(58, 77, 878, 419);
		NotificationsPanel.add(NotificationsScrollPane);
				
		NotificationsScrollPane.setViewportView(NotificationsTable);
		/*Design table*/
		NotificationsTable.setRowHeight(23);
		NotificationsTable.setFillsViewportHeight(true);
		NotificationsTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		NotificationsTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		NotificationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/*Text in center*/
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		NotificationsTable.setDefaultRenderer(Object.class, CenterRenderer);
		
		/*Create notification thread*/
			NotificationThrerad = new UpdateNotifications(ThisScreen, Server, User.getUserID());
			new Thread(NotificationThrerad).start();
		

/**
 * Set the exit (X) button to performed a organized logout
 */
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {	    	
		    	if(User.getUserTypeId()!=2){
		    		NotificationThrerad.setNotificationFlag(false);	//Stop notification thread
		    		User.setWhatToDo(MessageType.updateUserLogout);		    		
		    		Server.handleMessageFromClient(User);
					LoginScreen.setVisible(true);
					ThisScreen.setVisible(false);
		    	}
		    }
		};
		this.addWindowListener(exitListener);
		
	}
	
	
	
	public void setWelcomeLabel(String FirstName, String LastName){
		WelcomLabel.setText("Welcome "+FirstName+" "+LastName);
	}	
	public void setRoleLabel(String Role){
		RoleLabel.setText(Role+" Panel");
	}
	public void ClearCenterPanelLabel(){
		SubPanelLabel.setText("");
	}
	public void setCenterPanelLabel(String NewString){
		SubPanelLabel.setText(NewString);
	}
	public JPanel getCenterCardContainer(){
		return CenterCardContainer;
	}
	public JPanel getLeftCardContainer(){
		return LeftCardContainer;
	}
	public JButton getLogoutButton(){
		return this.LogoutButton;
	}
	public JTable getNotificationsTable(){
		return NotificationsTable;
	}
	public JButton getNotificationsButton(){
		return this.NotificationsButton;
	}
	public JTable getContactTable(){
		return ContactTable;
	}
	public UpdateNotifications getNotificationThread(){
		return NotificationThrerad;
	}
	public void ShowLostConnection(){
		LostConnection.setVisible(true);
	}
	
	/**
	 * Set in global variable the current component displayed
	 * @param c - the components array
	 * @return the name of the last component
	 */
	private String getLastComponent(Component c[]){
		int i=0;
		for(i=0;i<c.length;i++)
			if(c[i].isVisible())
				break;
		return c[i].getName();
	}

}
