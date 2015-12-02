package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Login2_GUI extends JFrame {
/**
 * Global Variables
 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField PasswordTextField;
	private JButton ChangePasswordButton = new JButton("Change Password");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login2_GUI frame = new Login2_GUI();
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
	public Login2_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds (100, 100, 454, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setVisible(true);
		
		JLayeredPane SecondLoginScreen = new JLayeredPane();
		SecondLoginScreen.setBackground(UIManager.getColor("CheckBox.light"));
		SecondLoginScreen.setOpaque(true);
		contentPane.add(SecondLoginScreen, BorderLayout.NORTH);
		contentPane.setPreferredSize(new Dimension(300,200));
		SecondLoginScreen.setPreferredSize(new Dimension(300, 557));
		
		//MyGas Icon
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Login2_GUI.class.getResource("/images/Main_Login_empty.jpg")));
		label.setBounds(63, 13, 294, 303);
		SecondLoginScreen.add(label);
		
		//User Name Label
		JLabel WelcomUserLabel = new JLabel("Welcom First+Last");
		WelcomUserLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		WelcomUserLabel.setBounds(63, 330, 305, 35);
		SecondLoginScreen.add(WelcomUserLabel);
		
		//Password Label
		JLabel PasswordLabel = new JLabel("New Password:");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordLabel.setBounds(63, 388, 136, 35);
		SecondLoginScreen.add(PasswordLabel);
		
		//Password Text Field
		PasswordTextField = new JPasswordField();
		PasswordTextField.setToolTipText("Enter Password");
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordTextField.setBounds(204, 388, 164, 39);
		SecondLoginScreen.add(PasswordTextField);
		
		// Change Password Button
		ChangePasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChangePasswordButton.setBounds(118, 462, 196, 46);
		SecondLoginScreen.add(ChangePasswordButton);		
	}
	
	public String getPassword(){
		return PasswordTextField.getPassword().toString();
	}
}
