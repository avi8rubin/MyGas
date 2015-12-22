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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callbackStringArray;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import java.awt.CardLayout;

public class abstractPanel_GUI extends JFrame {

	
	//Instance variables ********************************************** 

	private static final long serialVersionUID = 1L;

	
	/**
	 * The login user
	 */
	protected static callbackUser User;				// Current user details
	/**
	 * Gui screens
	 */
	private Login_GUI LoginScreen;					//The login screen to go back when user press logout
	private abstractPanel_GUI ThisScreen;			//The current gui screen
	
	/**
	 * Server
	 */
	private Client Server;
	private callbackBuffer CommonBuffer;
	protected Checks checks = new Checks();
	
	//GUI variables ***************************************************
	protected JPanel contentPane;
	protected JPanel CenterCardContainer = new JPanel(new CardLayout());
	protected JPanel LeftCardContainer = new JPanel(new CardLayout());
	protected JLayeredPane TopPanel = new JLayeredPane();
	protected JLayeredPane EmptyLeftPanel = new JLayeredPane();
	protected JLayeredPane EmptyCenterPanel = new JLayeredPane();
	protected JLayeredPane ContactsPanel = new JLayeredPane();
	private JLabel RoleLabel = new JLabel("<Role> Panel");
	private JLabel WelcomLabel = new JLabel("Welcome First + Last Name");
	protected final JInternalFrame ContactFrame = new JInternalFrame("Contact List");
	private final JScrollPane ContactsSrollPane = new JScrollPane();
	private JTable ContactTable;
	private JButton LogoutButton = new JButton("Logout");
	private JButton ContactsButton = new JButton("Contacts");
	private JLabel SubPanelLabel = new JLabel("");
	protected CardLayout ContainerCard;
	
	//Other variables ***************************************************
	private callbackStringArray ContactList;
	private String LastCard;
	/**
	 * Create the abstract GUI panel.
	 */

	public abstractPanel_GUI(callbackUser EnteredUser, Client Server,callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		setBounds(100, 100, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setVisible(true);
		this.User = EnteredUser;
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
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
		LogoutButton.addActionListener(new ActionListener() { 				//Add action listener
			public void actionPerformed(ActionEvent e) {
				if(User.getUserTypeId()!=2){
					User.setWhatToDo(MessageType.updateUserLogout);
					Server.handleMessageFromClient(User);
					getCallBackFromBuffer();									//Clean buffer
				}
				LoginScreen.setVisible(true);								//Go to login screen
				ThisScreen.setVisible(false);								//Set invisible current screen
			}
		});

		// Get Contact List - Send query and create the JTable
		ContactList = new callbackStringArray(MessageType.getContacts);		//--------- Get contact list --------
		Server.handleMessageFromClient(ContactList);						//----------------------------------- 
		ContactList = (callbackStringArray) getCallBackFromBuffer();
		
		
		// Contact button
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		ContactsButton.setActionCommand("Contacts"); 						//Button action command
		TopPanel.add(ContactsButton);
		ContactsButton.addActionListener(new ActionListener() {				//Add action listener
			public void actionPerformed(ActionEvent e) {
		/*----- Replace Card -------*/		
				ContainerCard = (CardLayout)(CenterCardContainer.getLayout());
				if(!getLastComponent(CenterCardContainer.getComponents()).equals("Contacts")){
					LastCard = getLastComponent(CenterCardContainer.getComponents());	//Set in global LastCard the current component
					ContainerCard.show(CenterCardContainer, "Contacts");	
				}			
				else ContainerCard.show(CenterCardContainer,LastCard);
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
		//EmptyCenterPanel.setLayout(new CardLayout(0, 0));
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
		ContactTable = new JTable(ContactList.getData(),ContactList.getColHeaders()){
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
 * Set the exit (X) button to performed a organized logout
 */
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {	    	
		    	
	    		User.setWhatToDo(MessageType.updateUserLogout);
	    		Server.handleMessageFromClient(User);
				getCallBackFromBuffer();
				LoginScreen.setVisible(true);
				ThisScreen.setVisible(false);		        
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
	/**
	 * @return The callback from the buffer
	 */
	private CallBack getCallBackFromBuffer(){
		CallBack ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBack();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){					//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		if (ReturnCallback instanceof callbackLostConnection){
			LoginScreen.NoConnectionToServer();
			LoginScreen.setVisible(true);
			this.setVisible(false);
		}
		return ReturnCallback; 	
	}
	
	/**
	 * Set in global variable the current component
	 * @param c - the components array
	 */
	private String getLastComponent(Component c[]){
		int i=0;
		for(i=0;i<c.length;i++)
			if(c[i].isVisible())
				break;
		return c[i].getName();
	}
}
