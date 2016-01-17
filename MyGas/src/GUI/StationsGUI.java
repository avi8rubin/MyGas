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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import callback.callbackCar;
import callback.callbackUser;
import client.Client;

public class StationsGUI extends abstractPanel_GUI{

	private static final long serialVersionUID = 1L;
	
	
	private JComboBox CarNumberCombobox;
	
	private JButton LoginButton;
	private JButton PayButton;
	private JButton LogoutButton2;
	private JButton StartFuelingButton;
	
	private JLabel car;
	private JLabel ErrorMassage=new JLabel();
	private	JLabel GreenHand;
	private	JLabel BlueHand;
	private	JLabel RedHand;
	private JLabel BlueHandFlip;
	private JLabel RedHandFlip;
	private JLabel GreenHandFlip;
	private JLabel GasStationHome;
	private JLabel DiscountTextBox;
	private JLabel NFCLabel;
	 
	private JRadioButton Cash;
	private JRadioButton Credit;
	
	private JFormattedTextField NFCTextField;
	private JTextField PasswordTextField = new JPasswordField();
	private JTextField LiterLabel = new JTextField();
	private JTextField PriceLabel = new JTextField();
	private JTextField UserNameTextField=new JTextField();
	
	private JLayeredPane left_car = new JLayeredPane();
	private JLayeredPane GasFuelingCenterPanel = new JLayeredPane();
	private JLayeredPane StationUserLoginLayer ;
	
	/**
	 * Stations GUI constructor
	 * @param EnteredUser The user entered to the system.
	 * @param Server The client connection to server.
	 * @param LoginScreen The login screen, when the user logout, he return to the login screen.
	 */
	public StationsGUI(callbackUser EnteredUser, Client Server, Login_GUI LoginScreen) {
		super(EnteredUser, Server, LoginScreen);

		/**
		 * Station Login Layer maker
		 */

		LogoutButton2 = new JButton("User Logout");
		LogoutButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutButton2.setBounds(986, 64, 150, 38);
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
		UserNameLabel.setBounds(343, 320, 123, 35);
		StationUserLoginLayer.add(UserNameLabel);
		
		//Password Label
		JLabel PasswordLabel = new JLabel("Password:");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordLabel.setBounds(343, 370, 123, 35);
		StationUserLoginLayer.add(PasswordLabel);
		UserNameTextField.setText("");
		
		//User Name Text Field
		UserNameTextField.setToolTipText("Enter User Name");
		UserNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UserNameTextField.setBounds(479, 320, 164, 39);
		StationUserLoginLayer.add(UserNameTextField);
		
		//Password Text Field
		LoginButton=new JButton();
		PasswordTextField.setToolTipText("Enter Password");
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PasswordTextField.setBounds(478, 370, 165, 38);
		StationUserLoginLayer.add(PasswordTextField);
		LoginButton.setText("Login");
		
		//NFC TextLabel
		NFCLabel=new JLabel("NFC:");
		NFCLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NFCLabel.setBounds(343,430,50,24);
		StationUserLoginLayer.add(NFCLabel);
		
		//NFC TextField
		MaskFormatter fmt = null;
		try {
			fmt = new MaskFormatter("**-***-**");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NFCTextField=new JFormattedTextField(fmt);
		
		NFCTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NFCTextField.setBounds(478, 430, 165, 38);
		NFCTextField.setHorizontalAlignment(SwingConstants.CENTER);
		NFCTextField.setValue("__-___-__");
		StationUserLoginLayer.add(NFCTextField);
		
		// Login Button
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LoginButton.setBounds(430, 503, 146, 46);
		StationUserLoginLayer.add(LoginButton);	
		
		// Error Massage
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(670, 430, 250, 38);
		StationUserLoginLayer.add(ErrorMassage);
		
		// Gas station 1
		
		// left panel
		left_car.setBackground(new Color(169, 169, 169));
		left_car.setOpaque(true);
		left_car.setName("left_panel");
		LeftCardContainer.add(left_car, "left_panel");
		left_car.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		left_car.setPreferredSize(new Dimension(300,200));
		
		//Logo
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 313, 239, 242);
		left_car.add(LogoImage);
		
		//"Car Number:"
		JLabel CarNumberTxt = new JLabel("Car Number : ");
		CarNumberTxt.setFont(new Font("Tahoma", Font.BOLD, 20));
		CarNumberTxt.setBounds(10, 0, 153, 40);
		left_car.add(CarNumberTxt);
		
		//Combobox
		CarNumberCombobox = new JComboBox();
		CarNumberCombobox.setAlignmentX(CENTER_ALIGNMENT);
		CarNumberCombobox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CarNumberCombobox.setBounds(15, 35, 120, 40);
		left_car.add(CarNumberCombobox);
		
		//Start/Stop Button
		StartFuelingButton = new JButton("Start");
		StartFuelingButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		StartFuelingButton.setBounds(170, 35, 117, 40);
		left_car.add(StartFuelingButton);
		
		//"Liter Amount:"
		JLabel AmountTxt = new JLabel("Liter Amount :");
		AmountTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AmountTxt.setBounds(12, 100, 160, 40);
		left_car.add(AmountTxt);
		
		// Liter Counter
		LiterLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LiterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LiterLabel.setText("0");
		LiterLabel.setEditable(false);
		LiterLabel.setBounds(171, 100, 117, 40);
		left_car.add(LiterLabel);
		LiterLabel.setColumns(10);
		
		//"Price:"
		JLabel PriceTxt = new JLabel("Price :");
		PriceTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PriceTxt.setBounds(12, 150, 110, 40);
		left_car.add(PriceTxt);
		
		//PriceCounter
		PriceLabel.setText("0");
		PriceLabel.setEditable(false);
		PriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PriceLabel.setColumns(10);
		PriceLabel.setBounds(171, 150, 117, 40);
		left_car.add(PriceLabel);
		
		
		//Cash Radio Button
		Cash = new JRadioButton("Cash");
		Cash.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Cash.setBackground(UIManager.getColor(left_car.getBackground()));
		Cash.setBounds(19, 230, 80, 25);
		left_car.add(Cash);
		
		//Credit Radio Button
		Credit = new JRadioButton("Credit");
		Credit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Credit.setBackground(UIManager.getColor(left_car.getBackground()));
		Credit.setBounds(171, 230, 120, 25);
		left_car.add(Credit);
		
		//Pay
		PayButton = new JButton("Pay");
		PayButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		PayButton.setBounds(103, 260, 92, 38);
		left_car.add(PayButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(Cash);
		group.add(Credit);
		
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
		DiscountTextBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DiscountTextBox.setBounds(10, 10, 491, 120);
		GasFuelingCenterPanel.add(DiscountTextBox);
	}
	
	public String getUserName(){
		return UserNameTextField.getText().trim();
	}
	public void setUserName(){
		this.UserNameTextField.setText("");
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
	public void IllegalNFC(){
		ErrorMassage.setText("*You Enter Illegal NFC input.");
	}
	public void IllegalCostingmodel(){
		ErrorMassage.setText("<html>*Cosing Model Not Compatible With This Station.<html/>");
	}
	public void NFCNotExist(){
		ErrorMassage.setText("*NFC Number is not exist in DB.");
	}
	public void IllegalCustomerID(){
		ErrorMassage.setText("*Error With CustomerID.");
	}
	public void IllegalCustomerHasNoCars(){
		ErrorMassage.setText("*No Records Of Cars For This Customer.");
	}
	public void ClearErrorMessage(){
		ErrorMassage.setText("");
	}
	public void AlreadyConnected(){
		ErrorMassage.setText("*User Already Connected.");
	}
	public void setCashDefult(){
		this.setCashCheacked();
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
	public void setCombobox(String str){
		Object[] CarNumber = new Object[1];
		CarNumber[0]=str;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel<Object>(CarNumber);
		this.CarNumberCombobox.setModel(combopatternModel);
		
	
	}
	public void setCombobox(ArrayList arr){
		Object[] CarNumber=new Object[arr.size()];
		for(int i=0;i<arr.size();i++)
		{
			CarNumber[i]=((callbackCar)arr.get(i)).getCarNumber();
		}
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel<Object>(CarNumber);
		CarNumberCombobox.setModel(combopatternModel);
		CarNumberCombobox.addItem("Select Car");
		CarNumberCombobox.setSelectedItem("Select Car");
	}
	
	public void setComboboxedit(boolean t){
		this.CarNumberCombobox.setEnabled(t);
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

	public JFormattedTextField getNFCTextField() {
		return this.NFCTextField;
	}

	public void setNFCTextField(String str) {
		NFCTextField.setText(str);;
	}
	public void setPassword(String str){
		this.PasswordTextField.setText(str);
	}
	public void setPriceLabel(String str){
		this.PriceLabel.setText(str);
	}
	public String getComboboxCarSelect(){
		return (String) this.CarNumberCombobox.getSelectedItem();
	}
	public void setCreditRadioButtonEdit(boolean t)
	{
		this.Credit.setEnabled(t);
	}
	public void setCashCheacked()
	{
		this.Cash.setSelected(true);
	}
}
