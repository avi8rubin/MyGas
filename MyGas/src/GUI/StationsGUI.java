package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class StationsGUI extends abstractPanel_GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLayeredPane StationUserLoginLayer ;
	private JTextField UserNameTextField=new JTextField();
	private JTextField PasswordTextField = new JPasswordField();
	private JButton LoginButton=new JButton();
	private JLabel ErrorMassage=new JLabel();
	private JLayeredPane left_car = new JLayeredPane();
	private JTextField LiterLabel = new JTextField();
	private JTextField PriceLabel = new JTextField();
	public StationsGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);


		/**
		 * Station Login Layer maker
		 */

		//MyGas Icon
		JLabel MyGasIcon = new JLabel("");
		MyGasIcon.setIcon(new ImageIcon(Login_GUI.class.getResource("/images/Main_Login_empty.png")));
		MyGasIcon.setBounds(343, 13, 294, 303);
		EmptyCenterPanel.add(MyGasIcon);
		
		//User Name Label
		JLabel UserNameLabel = new JLabel("User Name:");
		UserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameLabel.setBounds(343, 353, 123, 35);
		EmptyCenterPanel.add(UserNameLabel);
		
		//Password Label
		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordLabel.setBounds(343, 412, 123, 35);
		EmptyCenterPanel.add(PasswordLabel);
		UserNameTextField.setText("CEO");
		
		//User Name Text Field
		UserNameTextField.setToolTipText("Enter User Name");
		UserNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameTextField.setBounds(478, 353, 165, 38);
		EmptyCenterPanel.add(UserNameTextField);
		
		//Password Text Field
		
		PasswordTextField.setToolTipText("Enter Password");
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordTextField.setBounds(479, 410, 164, 39);
		EmptyCenterPanel.add(PasswordTextField);
		LoginButton.setText("Login");
		
		// Login Button
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LoginButton.setBounds(430, 503, 146, 46);
		EmptyCenterPanel.add(LoginButton);	
		
		// Error Massage
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(375, 575, 242, 32);
		EmptyCenterPanel.add(ErrorMassage);
		
		// left panel
		

		left_car.setBackground(new Color(169, 169, 169));
		left_car.setOpaque(true);
		left_car.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		left_car.setPreferredSize(new Dimension(300,200));
		contentPane.add(left_car, BorderLayout.WEST);
		
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 400, 239, 242);
		left_car.add(LogoImage);
		
		JComboBox CarNumberCombobox = new JComboBox();
		CarNumberCombobox.setBounds(25, 47, 251, 40);
		left_car.add(CarNumberCombobox);
		
		JButton StartFuelingButton = new JButton("Start Fueling");
		StartFuelingButton.setHideActionText(true);
		StartFuelingButton.setVerticalTextPosition(SwingConstants.TOP);
		StartFuelingButton.setHorizontalAlignment(SwingConstants.LEFT);
		StartFuelingButton.setHorizontalTextPosition(SwingConstants.LEFT);
		StartFuelingButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		StartFuelingButton.setBounds(62, 110, 170, 56);
		left_car.add(StartFuelingButton);
		
		JLabel AmountTxt = new JLabel("Liter Amount :");
		AmountTxt.setFont(new Font("Tahoma", Font.BOLD, 20));
		AmountTxt.setBounds(12, 217, 147, 40);
		left_car.add(AmountTxt);
		/////////////////////
/////////////////////
/////////////////////
		
		LiterLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		LiterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LiterLabel.setText("0");
		LiterLabel.setBounds(171, 218, 117, 40);
		left_car.add(LiterLabel);
		LiterLabel.setColumns(10);
		
		JLabel PriceTxt = new JLabel("Price :");
		PriceTxt.setFont(new Font("Tahoma", Font.BOLD, 20));
		PriceTxt.setBounds(12, 270, 90, 40);
		left_car.add(PriceTxt);
		
		
		PriceLabel.setText("0");
		PriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PriceLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		PriceLabel.setColumns(10);
		PriceLabel.setBounds(171, 271, 117, 40);
		left_car.add(PriceLabel);
		
		JButton PayButton = new JButton("Pay");
		PayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PayButton.setBounds(69, 341, 153, 40);
		left_car.add(PayButton);
		
		JLabel CarNumberTxt = new JLabel("Car Number : ");
		CarNumberTxt.setFont(new Font("Tahoma", Font.BOLD, 20));
		CarNumberTxt.setBounds(69, 0, 153, 40);
		left_car.add(CarNumberTxt);
		
		
	}
	
	public String getUserName(){
		return UserNameTextField.getText().trim();
	}
	public String getPassword(){
		return String.valueOf(PasswordTextField.getText());
	}
	public void IllegalUserName(){
		ErrorMassage.setText("*You Enter Illegal User Name.");
	}
	public void IllegalPassword(){
		ErrorMassage.setText("*You Enter Illegal Password.");
		//PasswordTextField.setText("Illegal");
	}
	public void ClearErrorMessage(){
		ErrorMassage.setText("");
	}
	public void AlreadyConnected(){
		ErrorMassage.setText("*User Already Connected.");
	}

	/*
	public void ChangePasswordError(){
		SecondErrorMassage.setText("*An Error Has Occurred.");
	}*/
	public void NoConnectionToServer(){
		ErrorMassage.setText("*No Connection To The Server.");
	}
	public JButton getLoginButton(){
		return LoginButton;
	}
	/*
	public JButton getChangePasswordButton(){
		return ChangePasswordButton;
	}
	*/
	/*
	public void setWelcomUserLabel(String FirstName, String LastName){
		WelcomUserLabel.setText("Welcome "+FirstName+" "+LastName);
	}*/
	
	/*
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
	*/

}
