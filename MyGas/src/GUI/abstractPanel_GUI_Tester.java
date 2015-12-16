package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;

public class abstractPanel_GUI_Tester extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane TopPanel = new JLayeredPane();
	private JLabel RoleLabel = new JLabel("<Role> Panel");
	private JLabel WelcomLabel = new JLabel("Welcom First + Last Name");
	private JLayeredPane LeftPanel = new JLayeredPane();
	private JLayeredPane CenterPanel = new JLayeredPane();
	private final JTable ContactTable = new JTable();
	private boolean ShowContacts = false;
	private JInternalFrame ContactFrame = new JInternalFrame("Contact List");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					abstractPanel_GUI_Tester frame = new abstractPanel_GUI_Tester();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public abstractPanel_GUI_Tester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setVisible(true);
		
		TopPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TopPanel.setBackground(SystemColor.activeCaption);
		TopPanel.setOpaque(true);
		TopPanel.setPreferredSize(new Dimension(1295,150));
		contentPane.add(TopPanel, BorderLayout.NORTH);
		
		
		RoleLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		RoleLabel.setBounds(12, 0, 453, 47);
		TopPanel.add(RoleLabel);
		
		
		WelcomLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		WelcomLabel.setBounds(12, 40, 358, 24);
		TopPanel.add(WelcomLabel);
		
		JButton LogoutButton = new JButton("Logout");
		LogoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutButton.setBounds(1148, 13, 112, 38);
		TopPanel.add(LogoutButton);
		LogoutButton.addActionListener(new ActionListener() { 				//Add action listener
			public void actionPerformed(ActionEvent e) {
		        System.exit(0);
			}
		});
		
		
		JButton ContactsButton = new JButton("Contacts");
		ContactsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowContacts = !ShowContacts;
				ContactFrame.setVisible(ShowContacts);	
			}
		});
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		TopPanel.add(ContactsButton);
		
		
		LeftPanel.setBackground(new Color(169, 169, 169));
		LeftPanel.setOpaque(true);
		LeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		LeftPanel.setPreferredSize(new Dimension(300,200));
		contentPane.add(LeftPanel, BorderLayout.WEST);
		
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 313, 239, 242);
		LeftPanel.add(LogoImage);
		
		
		CenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(CenterPanel, BorderLayout.CENTER);
		
		JLabel lblDisplaySubPanelLabel = new JLabel("Display Sub Panel Label");
		lblDisplaySubPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplaySubPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDisplaySubPanelLabel.setBounds(164, 13, 608, 42);
		CenterPanel.add(lblDisplaySubPanelLabel);
		
		// Contact list frame
		
		ContactFrame.setBounds(0, 0, 984, 555);
		ContactFrame.setVisible(false);
		CenterPanel.add(ContactFrame);
		ContactFrame.getContentPane().setLayout(null);
		ContactTable.setBounds(12, 13, 944, 593);
		
		ContactFrame.getContentPane().add(ContactTable);
		JScrollPane scrollPane = new JScrollPane(ContactTable);
		scrollPane.setBounds(0, 612, 962, -612);
		ContactFrame.getContentPane().add(scrollPane);
		
	}
	public void setWelcomLabel(String FirstName, String LastName){
		WelcomLabel.setText("Welcom "+FirstName+" "+LastName);
	}
	
	public void setRoleLabel(String Role){
		RoleLabel.setText(Role+" Panel");
	}
}
