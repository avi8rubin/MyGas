package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

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
	private JButton StartFuelingButton;
	public JLabel car;
	private JLabel ErrorMassage=new JLabel();
	private	JLabel GreenHand;
	private	JLabel BlueHand;
	private	JLabel RedHand;
	private JLabel BlueHandFlip;
	private JLabel RedHandFlip;
	private JLabel GreenHandFlip;
	private JLabel GasStationHome;
	private JLabel DiscountTextBox;
	
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
		LogoutButton2.setBounds(978, 13, 158, 38);
		LogoutButton2.setVisible(false);
		TopPanel.add(LogoutButton2);
		
		StationUserLoginLayer=new JLayeredPane();
		StationUserLoginLayer.setOpaque(true);
		StationUserLoginLayer.setName("StationUserLoginLayer");
		CenterCardContainer.add(StationUserLoginLayer,"StationUserLoginLayer");
		//MyGas Icon
		JLabel MyGasIcon = new JLabel("CEO");
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
		
		// Gas station 1
		
		// left panel
		left_car.setBackground(new Color(169, 169, 169));
		left_car.setOpaque(true);
		left_car.setName("left_panel");
		LeftCardContainer.add(left_car, "left_panel");
		left_car.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		left_car.setPreferredSize(new Dimension(300,200));
		
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 313, 239, 242);
		left_car.add(LogoImage);
		
		CarNumberCombobox = new JComboBox();
		CarNumberCombobox.setBounds(25, 30, 251, 40);
		left_car.add(CarNumberCombobox);
		
		StartFuelingButton = new JButton("Start Fueling");
		StartFuelingButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		StartFuelingButton.setBounds(56, 90, 185, 40);
		left_car.add(StartFuelingButton);
		
		JLabel AmountTxt = new JLabel("Liter Amount :");
		AmountTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AmountTxt.setBounds(12, 217-70, 160, 40);
		left_car.add(AmountTxt);

		LiterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LiterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LiterLabel.setText("0");
		LiterLabel.setEditable(false);
		LiterLabel.setBounds(171, 218-70, 117, 40);
		left_car.add(LiterLabel);
		LiterLabel.setColumns(10);
		
		JLabel PriceTxt = new JLabel("Price :");
		PriceTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PriceTxt.setBounds(12, 270-70, 110, 40);
		left_car.add(PriceTxt);
		
		
		PriceLabel.setText("0");
		PriceLabel.setEditable(false);
		PriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PriceLabel.setColumns(10);
		PriceLabel.setBounds(171, 271-70, 117, 40);
		left_car.add(PriceLabel);
		
		PayButton = new JButton("Pay");
		PayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PayButton.setBounds(103, 121+150, 92, 38);
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
		GreenHand.setBounds(621, 324, 90, 119);
		GasFuelingCenterPanel.add(GreenHand);
		
		RedHand = new JLabel("");
		RedHand.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Red_hand.png")));
		RedHand.setBounds(508, 330, 90, 119);
		GasFuelingCenterPanel.add(RedHand);
		
		BlueHand = new JLabel("");
		BlueHand.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Blue_hand.png")));
		BlueHand.setBounds(720, 321, 90, 119);
		GasFuelingCenterPanel.add(BlueHand);
		
		BlueHandFlip = new JLabel("");
		BlueHandFlip.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Blue_hand_flip.png"))); 
		BlueHandFlip.setBounds(200, 305, 90, 119);
		BlueHandFlip.setVisible(false);
		GasFuelingCenterPanel.add(BlueHandFlip);
		
		RedHandFlip = new JLabel("");
		RedHandFlip.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Red_hand_flip.png"))); 
		RedHandFlip.setBounds(200, 305, 90, 119);
		RedHandFlip.setVisible(false);
		GasFuelingCenterPanel.add(RedHandFlip);
		
		GreenHandFlip= new JLabel("");
		GreenHandFlip.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Green_hand_flip.png"))); 
		GreenHandFlip.setBounds(200, 305, 90, 119);
		GreenHandFlip.setVisible(false);
		GasFuelingCenterPanel.add(GreenHandFlip);
		
		GasStationHome = new JLabel("");
		GasStationHome.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Gas_Station_Empty_scoter.png")));
		GasStationHome.setBounds(477, 0, 444, 508);
		GasFuelingCenterPanel.add(GasStationHome);
		
		car = new JLabel("");
		car.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Blackcarmid.png")));	
		car.setBounds(0, 207, 503, 259);
		GasFuelingCenterPanel.add(car);
		
		DiscountTextBox = new JLabel("");
		DiscountTextBox.setBounds(3, 13, 491, 33);
		GasFuelingCenterPanel.add(DiscountTextBox);
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
	public JLabel getBlueHand(){
		return this.BlueHand;
	}
	public JLabel getRedHand(){
		return this.RedHand;
	}
	public JLabel getBlueHandFlip(){
		return this.BlueHandFlip;
	}
	public JLabel getRedHandFlip(){
		return this.RedHandFlip;
	}
	public JLabel getGreenHandFlip(){
		return this.GreenHandFlip;
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
	public void ResetPumps(){
		BlueHand.setBounds(720, 321, 90, 119);
		GreenHand.setBounds(621, 324, 90, 119);
		RedHand.setBounds(508, 330, 90, 119);
	}
	public void setGasStation(){
		this.GasStationHome.setBounds(477, 0, 444, 508);
	}
	public void BluePumpShow(){
		BlueHand.setVisible(false);
		BlueHandFlip.setVisible(true);
	}
	public void BluePumpNotShow(){
		BlueHand.setVisible(true);
		BlueHandFlip.setVisible(false);	
	}
	public void RedPumpShow(){
		RedHand.setVisible(false);
		RedHandFlip.setVisible(true);
	}
	public void RedPumpNotShow(){
		RedHand.setVisible(true);
		RedHandFlip.setVisible(false);
	}
	public void GreenPumpShow(){
		GreenHand.setVisible(false);
		GreenHandFlip.setVisible(true);
	}
	public void GreenPumpNotShow(){
		GreenHand.setVisible(true);
		GreenHandFlip.setVisible(false);
	}
	public JButton getStartFuelingButton(){
		return this.StartFuelingButton;
	}
	public JButton getPayButton(){
		return this.PayButton;
	}
	public JLabel getDiscountLabelBox(){
		return this.DiscountTextBox;
	}
	public JTextField getLiterLabel(){
		return this.LiterLabel;
	}
	public JTextField getPriceLabel(){
		return this.PriceLabel;
	}
	public callbackUser getStationUser(){
		return this.User;
	}
}
