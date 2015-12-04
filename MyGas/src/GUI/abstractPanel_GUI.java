package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import callback.callbackUser;
import client.QuerySender;

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
	 * The Query builder
	 */
	private static QuerySender SendQuery;
	
	/**
	 * The login user
	 */
	private static callbackUser User;
	
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


	/**
	 * Create the abstract GUI panel.
	 */
	public abstractPanel_GUI(QuerySender SendQuery, callbackUser EnteredUser) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setVisible(true);
		this.SendQuery = SendQuery;
		this.User = EnteredUser;
		setWelcomeLabel(User.getFirstName(),User.getLastName()); 		// Set welcome label
		setRoleLabel(User.getUserPrivilege());							// Set user role
		
		
	
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
				SendQuery.updateUserLogout(User.getUserID());
		        System.exit(0);
			}
		});

		
		// Contact button
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		TopPanel.add(ContactsButton);
		ContactsButton.addActionListener(new ActionListener() {				//Add action listener
			public void actionPerformed(ActionEvent e) {
				/*need to add query send to DB to get all the contact list*/
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
		ContactFrame.setVisible(true);
		CenterPanel.add(ContactFrame);
		ContactFrame.getContentPane().setLayout(null);
		
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
}
