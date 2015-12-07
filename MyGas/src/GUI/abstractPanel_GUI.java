package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callbackStringArray;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.MessageType;
import controller.LoginController;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

public class abstractPanel_GUI extends JFrame {

	
	//Instance variables ********************************************** 

	private static final long serialVersionUID = 1L;

	
	/**
	 * The login user
	 */
	private static callbackUser User;
	private Login_GUI LoginScreen;
	/**
	 * Server
	 */
	private Client Server;
	private callbackBuffer CommonBuffer;
	
	//GUI variables ***************************************************
	private JPanel contentPane;
	private JLayeredPane TopPanel = new JLayeredPane();
	private JLabel RoleLabel = new JLabel("<Role> Panel");
	private JLabel WelcomLabel = new JLabel("Welcome First + Last Name");
	private JLayeredPane LeftPanel = new JLayeredPane();
	private JLayeredPane CenterPanel = new JLayeredPane();
	private final JInternalFrame ContactFrame = new JInternalFrame("Contact List");
	private JLabel SubPanelLabel = new JLabel("");
	private JButton LogoutButton = new JButton("Logout");
	private JButton ContactsButton = new JButton("Contacts");
	private callbackStringArray ContactList;
	private boolean ShowContacts = false;
	private final JTable ContactTable;

	/**
	 * Create the abstract GUI panel.
	 */
	public abstractPanel_GUI(callbackUser EnteredUser, Client Server,callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 900);
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
		setWelcomeLabel(User.getFirstName(),User.getLastName()); 		// Set welcome label
		setRoleLabel(User.getUserPrivilege());							// Set user role
		LoginScreen.GoToLoginWindow();
		
	
	/**
	 * Create Top Panel
	 */
		TopPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TopPanel.setBackground(SystemColor.activeCaption);
		TopPanel.setOpaque(true);
		TopPanel.setPreferredSize(new Dimension(1295,200));
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
				User.setWhatToDo(MessageType.updateUserLogout);
				Server.handleMessageFromClient(User);
			}
		});

		// Get Contact List - Send query and create the JTable
		ContactList = new callbackStringArray();							//-----------------------------------
		ContactList.setWhatToDo(MessageType.getContacts);					//		get contact list
		Server.handleMessageFromClient(ContactList);						//----------------------------------- 
		ContactList = (callbackStringArray) getCallBackFromBuffer();
		ContactTable = new JTable(ContactList.getData(),ContactList.getColHeaders()); //create jtable with all the data of the workers
		
		
		// Contact button
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		TopPanel.add(ContactsButton);
		ContactsButton.addActionListener(new ActionListener() {				//Add action listener
			public void actionPerformed(ActionEvent e) {
				ShowContacts = !ShowContacts;
				ContactFrame.setVisible(ShowContacts);						//Display and hide the contact list
			}
		});
		
		
	/**
	 * Create Left Panel
	 */
		LeftPanel.setBackground(new Color(169, 169, 169));
		LeftPanel.setOpaque(true);
		LeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		LeftPanel.setPreferredSize(new Dimension(300,200));
		contentPane.add(LeftPanel, BorderLayout.WEST);
		
		// Logo on left panel
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 400, 239, 242);
		LeftPanel.add(LogoImage);
		
	/**
	 * Create Set Panel
	 */		
		CenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(CenterPanel, BorderLayout.CENTER);
		
		// Center panel label
		SubPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SubPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		SubPanelLabel.setBounds(170, 13, 608, 42);
		CenterPanel.add(SubPanelLabel);
		
		// Contact list frame
		ContactFrame.setBounds(0, 0, 984, 655);
		ContactFrame.setVisible(false);
		CenterPanel.add(ContactFrame);
		ContactFrame.getContentPane().setLayout(null);
		ContactTable.setBounds(12, 13, 944, 593);		
		ContactFrame.getContentPane().add(ContactTable);
		ContactFrame.add(new JScrollPane(ContactTable));
		
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
	/**
	 * @return The callback from the buffer
	 */
	private CallBack getCallBackFromBuffer(){
		CallBack ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBack();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){				//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		if (ReturnCallback instanceof callbackLostConnection){
			Login_GUI frame = new Login_GUI();
			frame.GoToLoginWindow();
			frame.NoConnectionToServer();
			frame.setVisible(true);
			new LoginController(frame,CommonBuffer);
		}
		return ReturnCallback; 	
	}
}
