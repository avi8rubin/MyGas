package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login_GUI extends JFrame {
/**
 * Global Variables
 */
	private static final long serialVersionUID = 1L;
	private JPanel contantPane;
	private JPasswordField PasswordTextField;
	private JFormattedTextField UserNameTextField = new JFormattedTextField();
	private JButton LoginButton = new JButton("Login");
	private JLayeredPane FirstLoginScreen = new JLayeredPane();
	private JLabel ErrorMassage = new JLabel("");
	private JLabel SecondErrorMassage = new JLabel("");
	private JLayeredPane SecondLoginScreen = new JLayeredPane();
	private JButton ChangePasswordButton = new JButton("Change Password");
	private JPasswordField NewPasswordTextField;
	private boolean DisplayScreen = true;
	private JLabel WelcomUserLabel = new JLabel("Welcom First+Last");
	private JTextField ServerIPField;
	/**
	 * Create the frame.
	 */
	public Login_GUI() {
		setBounds (100, 100, 454, 609);
		contantPane = new JPanel();
		contantPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contantPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contantPane);
		this.setResizable(false);
		this.setVisible(true);
		


		
		
	/**
	 * Create the First screen of login GUI
	 */
				
		FirstLoginScreen.setBackground(UIManager.getColor("CheckBox.light"));
		FirstLoginScreen.setOpaque(true);
		contantPane.add(FirstLoginScreen, BorderLayout.NORTH);
		contantPane.setPreferredSize(new Dimension(300,200));
		FirstLoginScreen.setPreferredSize(new Dimension(300, 565));
		
		//MyGas Icon
		JLabel MyGasIcon = new JLabel("");
		MyGasIcon.setIcon(new ImageIcon(Login_GUI.class.getResource("/images/Main_Login_empty.png")));
		MyGasIcon.setBounds(63, 13, 294, 303);
		FirstLoginScreen.add(MyGasIcon);
		
		//User Name Label
		JLabel UserNameLabel = new JLabel("User Name:");
		UserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameLabel.setBounds(63, 330, 123, 35);
		FirstLoginScreen.add(UserNameLabel);
		
		//Password Label
		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordLabel.setBounds(63, 388, 123, 35);
		FirstLoginScreen.add(PasswordLabel);
		UserNameTextField.setText("ohad");
		
		//User Name Text Field
		UserNameTextField.setToolTipText("Enter User Name");
		UserNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameTextField.setBounds(204, 329, 165, 38);
		FirstLoginScreen.add(UserNameTextField);
		
		//Password Text Field
		PasswordTextField = new JPasswordField();
		PasswordTextField.setToolTipText("Enter Password");
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordTextField.setBounds(204, 388, 164, 39);
		FirstLoginScreen.add(PasswordTextField);
		
		// Login Button
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LoginButton.setBounds(132, 461, 146, 46);
		FirstLoginScreen.add(LoginButton);	
		
		// Error Massage
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(92, 520, 242, 32);
		FirstLoginScreen.add(ErrorMassage);
		
		// Server IP
		ServerIPField = new JTextField("127.0.0.1");
		ServerIPField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ServerIPField.setHorizontalAlignment(SwingConstants.CENTER);
		ServerIPField.setBounds(322, 0, 116, 22);
		FirstLoginScreen.add(ServerIPField);
		ServerIPField.setColumns(10);
		
		FirstLoginScreen.setVisible(true);								//See the first login screen
			
		
		
	/**
	 * Create the Second screen of login GUI
	 */			
	
		SecondLoginScreen.setBackground(UIManager.getColor("CheckBox.light"));
		SecondLoginScreen.setOpaque(true);
		SecondLoginScreen.setPreferredSize(new Dimension(300, 565));
		contantPane.add(SecondLoginScreen, BorderLayout.NORTH);
		
		//MyGas Icon
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Login_GUI.class.getResource("/images/Main_Login_empty.png")));
		label.setBounds(63, 13, 294, 303);
		SecondLoginScreen.add(label);
		
		//User Name Label
		WelcomUserLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		WelcomUserLabel.setBounds(63, 330, 305, 35);
		SecondLoginScreen.add(WelcomUserLabel);
		
		//Password Label
		JLabel NewPasswordLabel = new JLabel("New Password:");
		NewPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NewPasswordLabel.setBounds(63, 388, 136, 35);
		SecondLoginScreen.add(NewPasswordLabel);
		
		//Password Text Field
		NewPasswordTextField = new JPasswordField();
		NewPasswordTextField.setToolTipText("Enter Password");
		NewPasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NewPasswordTextField.setBounds(204, 388, 164, 39);
		SecondLoginScreen.add(NewPasswordTextField);
		
		// Change Password Button
		ChangePasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChangePasswordButton.setBounds(118, 462, 196, 46);
		SecondLoginScreen.add(ChangePasswordButton);	
		
		// Password Screen error Massage
		SecondErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SecondErrorMassage.setForeground(Color.RED);
		SecondErrorMassage.setBounds(92, 520, 242, 32);
		SecondLoginScreen.add(SecondErrorMassage);	
		
		SecondLoginScreen.setVisible(false);								//See the second login screen	
	
	}
	
	
	public String getUserName(){
		return UserNameTextField.getText().trim();
	}
	public String getPassword(){
		return String.valueOf(PasswordTextField.getPassword());
	}
	public String getNewPassword(){
		return String.valueOf(NewPasswordTextField.getPassword());
	}
	public String getServerIP(){
		return String.valueOf(ServerIPField.getText().trim());
	}
	public JPanel getcontentPane(){
		return contantPane;
	}
	public void IllegalUserName(){
		ErrorMassage.setText("*You Enter Illegal User Name.");
	}
	public void IllegalPassword(){
		ErrorMassage.setText("*You Enter Illegal Password.");
	}
	public void ClearErrorMessage(){
		ErrorMassage.setText("");
	}
	public void AlreadyConnected(){
		ErrorMassage.setText("*User Already Connected.");
	}
	public void ChangePasswordError(){
		SecondErrorMassage.setText("*An Error Has Occurred.");
	}
	public void NoConnectionToServer(){
		ErrorMassage.setText("*No Connection To The Server.");
	}
	public JButton getLoginButton(){
		return LoginButton;
	}
	public JButton getChangePasswordButton(){
		return ChangePasswordButton;
	}
	public void setWelcomUserLabel(String FirstName, String LastName){
		WelcomUserLabel.setText("Welcome "+FirstName+" "+LastName);
	}
	
	public void SwitchScreen(){
		DisplayScreen=!DisplayScreen;				//Switch screen
		FirstLoginScreen.setVisible(DisplayScreen);
		SecondLoginScreen.setVisible(!DisplayScreen);	
	}
	public void GoToLoginWindow(){
		if (DisplayScreen == false){
			SwitchScreen();
		}
	}
}
