package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	
	private JComboBox CarNumberCombobox;
	
	private JButton LoginButton;
	private JButton PayButton;
	private JButton LogoutButton2;
	
	private JLabel ErrorMassage=new JLabel();
	private	JLabel GreenHand;
	
	private JTextField PasswordTextField = new JPasswordField();
	private JTextField LiterLabel = new JTextField();
	private JTextField PriceLabel = new JTextField();
	private JTextField UserNameTextField=new JTextField();
	
	private JLayeredPane left_car = new JLayeredPane();
	private JLayeredPane GasFuelingCenterPanel = new JLayeredPane();
	private JLayeredPane StationUserLoginLayer ;
	
	public StationsGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/**
		 * Station Login Layer maker
		 */
		
		LogoutButton2 = new JButton("User Logout");
		LogoutButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutButton2.setBounds(980, 13, 148, 34);
		LogoutButton2.setVisible(false);
		TopPanel.add(LogoutButton2);
		
		StationUserLoginLayer=new JLayeredPane();
		StationUserLoginLayer.setOpaque(true);
		StationUserLoginLayer.setName("StationUserLoginLayer");
		CenterCardContainer.add(StationUserLoginLayer,"StationUserLoginLayer");
		//MyGas Icon
		JLabel MyGasIcon = new JLabel("");
		MyGasIcon.setIcon(new ImageIcon(Login_GUI.class.getResource("/images/Main_Login_empty.png")));
		MyGasIcon.setBounds(343, 13, 294, 303);
		StationUserLoginLayer.add(MyGasIcon);
		
		//User Name Label
		JLabel UserNameLabel = new JLabel("User Name:");
		UserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameLabel.setBounds(343, 353, 123, 35);
		StationUserLoginLayer.add(UserNameLabel);
		
		//Password Label
		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordLabel.setBounds(343, 412, 123, 35);
		StationUserLoginLayer.add(PasswordLabel);
		UserNameTextField.setText("CEO");
		
		//User Name Text Field
		UserNameTextField.setToolTipText("Enter User Name");
		UserNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameTextField.setBounds(478, 353, 165, 38);
		StationUserLoginLayer.add(UserNameTextField);
		
		//Password Text Field
		LoginButton=new JButton();
		PasswordTextField.setToolTipText("Enter Password");
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordTextField.setBounds(479, 410, 164, 39);
		StationUserLoginLayer.add(PasswordTextField);
		LoginButton.setText("Login");
		
		// Login Button
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LoginButton.setBounds(430, 503, 146, 46);
		StationUserLoginLayer.add(LoginButton);	
		
		// Error Massage
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(375, 575, 242, 32);
		StationUserLoginLayer.add(ErrorMassage);
		
		// left panel
		left_car.setBackground(new Color(169, 169, 169));
		left_car.setOpaque(true);
		left_car.setName("left_panel");
		LeftCardContainer.add(left_car, "left_panel");
		left_car.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		left_car.setPreferredSize(new Dimension(300,200));
		
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 400, 239, 242);
		left_car.add(LogoImage);
		
		CarNumberCombobox = new JComboBox();
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
		
		PayButton = new JButton("Pay");
		PayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PayButton.setBounds(69, 341, 153, 40);
		left_car.add(PayButton);
		
		JLabel CarNumberTxt = new JLabel("Car Number : ");
		CarNumberTxt.setFont(new Font("Tahoma", Font.BOLD, 20));
		CarNumberTxt.setBounds(69, 0, 153, 40);
		left_car.add(CarNumberTxt);
		
		//-------Gas fueling panel----////
		
	
		
		GasFuelingCenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		//contentPane.add(GasFuelingCenterPanel, BorderLayout.CENTER);
		
		GasFuelingCenterPanel.setOpaque(true);
		GasFuelingCenterPanel.setName("GasFuelingCenterPanel");
		CenterCardContainer.add(GasFuelingCenterPanel, "GasFuelingCenterPanel");
		
		GreenHand = new JLabel("");
		GreenHand.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Green_hand.png")));
		GreenHand.setBounds(798, 249, 90, 138);
		GasFuelingCenterPanel.add(GreenHand);
		
		JLabel RedHand = new JLabel("");
		RedHand.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Red_hand.png")));
		RedHand.setBounds(651, 249, 90, 138);
		GasFuelingCenterPanel.add(RedHand);
		
		JLabel BlueHand = new JLabel("");
		BlueHand.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		BlueHand.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Blue_hand.png")));
		BlueHand.setBounds(497, 249, 90, 138);
		GasFuelingCenterPanel.add(BlueHand);
		
		JLabel CarIcon = new JLabel("");
		CarIcon.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Car.png")));
		CarIcon.setBounds(12, 328, 240, 316);
		GasFuelingCenterPanel.add(CarIcon);
		
		JLabel BlueHandHome = new JLabel("");
		BlueHandHome.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Blue_hand_Home.png")));
		BlueHandHome.setBounds(529, 209, 90, 138);
		GasFuelingCenterPanel.add(BlueHandHome);
		
		JLabel RedHandHome = new JLabel("");
		RedHandHome.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Red_hand_Home.png")));
		RedHandHome.setBounds(686, 209, 90, 138);
		GasFuelingCenterPanel.add(RedHandHome);
		
		JLabel GreenHandHome = new JLabel("");
		GreenHandHome.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Green_Hand_Home.png")));
		GreenHandHome.setBounds(839, 209, 90, 138);
		GasFuelingCenterPanel.add(GreenHandHome);
		
		JLabel Label95 = new JLabel("95");
		Label95.setHorizontalAlignment(SwingConstants.CENTER);
		Label95.setFont(new Font("Tahoma", Font.BOLD, 27));
		Label95.setBounds(542, 156, 58, 52);
		GasFuelingCenterPanel.add(Label95);
		
		JLabel Label98 = new JLabel("98");
		Label98.setHorizontalAlignment(SwingConstants.CENTER);
		Label98.setFont(new Font("Tahoma", Font.BOLD, 27));
		Label98.setBounds(700, 156, 58, 52);
		GasFuelingCenterPanel.add(Label98);
		
		JLabel LabelDiesel = new JLabel("Diesel");
		LabelDiesel.setHorizontalAlignment(SwingConstants.CENTER);
		LabelDiesel.setFont(new Font("Tahoma", Font.BOLD, 27));
		LabelDiesel.setBounds(839, 156, 90, 52);
		GasFuelingCenterPanel.add(LabelDiesel);
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

	public void NoConnectionToServer(){
		ErrorMassage.setText("*No Connection To The Server.");
	}
	public JButton getLoginButton(){
		return LoginButton;
	}
	public JLayeredPane getLeftPanel(){
		return this.left_car;
	}
	public JLabel getGreenHand(){
		return this.GreenHand;
	}
	public JComboBox getCarNumberComboBox(){
		return this.CarNumberCombobox;
	}

	public JButton getUserLogoutButton(){
		return this.LogoutButton2;
	}
	public JButton getMainLogoutButton(){
		return super.getLogoutButton();
	}
	public void setlogoutvisable(boolean x){
		this.LogoutButton2.setVisible(x);
	}

}
