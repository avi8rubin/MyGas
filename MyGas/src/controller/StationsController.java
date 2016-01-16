/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import javax.security.auth.callback.Callback;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import GUI.StationsGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCampaign;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackStationFuels;
import callback.callbackSuccess;
import callback.callbackUser;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;

public class StationsController extends Controller implements MouseListener,Runnable{
		private callbackBuffer CommonBuffer = null;
		private callbackStationFuels CurrentStation=null;
		
		
		private float Liter=0,Price=0,Fuel95Price,FuelScoterPrice,FuelDieselPrice,Fuel95CurrentAmount,Fuel95ThresholdLimit,FuelScoterCurrentAmount,FuelScoterThresholdLimit,
				FuelDieselCurrentAmount,FuelDieselThresholdLimit; 
		private int GasStationID,NFCnumber;
		private ArrayList UserCarsNumbers;
		private JButton LoginButton;
		private JButton UserLogoutButton;
		private JButton MainLogoutButton;
		private JButton StartFuelingButton;
		private JButton Paybutton;
		
		private JFormattedTextField NFCTextField;
		
		private static callbackUser EnteredUser=null;
		private static callbackUser StationCurrentUser;				// Current user details
		private static callbackStationFuels StationCurrentFuels;
		private static callbackVector FuelsInStation;
		private static callbackCar UserCarNFC;  // need to change back to customer
		private static callbackCar CustomerCars;
		private static CallBack Temp;
		private static callbackCustomer Customer;
		private static CallBack LocalUserCallBack = null;
		private static callbackSale CurrentSale;
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		
		private Thread LiterCounter;
		
		private JLabel BlueHand;
		private JLabel GreenHand;
		private JLabel RedHand;
		private JLabel BlueHandFlip;
		private JLabel RedHandFlip;
		private JLabel GreenHandFlip;
		private JLabel DiscountTextBox;
		private StationsGUI StationUserLoginGui;
		
		//private CardLayout ContainerCard2=(CardLayout)(CenterCardContainer.getLayout()),ContainerCard1;
		private JTextField LiterLabel;
		private JTextField PriceLabel;
		
		private boolean logoutflag;
		private boolean UserIsFueling =false;  //if hand in the car
		private boolean UserNeedToPay=false;   //if was start pumping
		private boolean PressStartStopButtonFlag=true;      //   true = start        false=stop
		private boolean Fuel95=false;  //RED
		private boolean FuelScoter=false; //Green
		private boolean FuelDiesel=false; //Blue
		private boolean Fuel95IsExist,FuelScoterIsExist,FuelDieselIsExist;
		private boolean NFCIsExist;
		private boolean ThreadRunFlag=true;
		/**
		 * Constratcor
		 * @param Server - main server to send DB
		 * @param CommonBuffer - get all the callbacks
		 * @param GuiScreen - gas Station enter
		 */
	public StationsController(Client Server, callbackBuffer CommonBuffer, StationsGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.StationUserLoginGui = GuiScreen;
		StationUserLoginGui.setVisible(true);
		
		/*----- Create gui button handlers -----*/
		MainLogoutButton=new JButton();
		MainLogoutButton=GuiScreen.getMainLogoutButton();
		StartFuelingButton=GuiScreen.getStartFuelingButton();
		StartFuelingButton.setEnabled(false);
		StartFuelingButton.addActionListener(this);
		Paybutton=GuiScreen.getPayButton();
		Paybutton.setEnabled(false);
		Paybutton.addActionListener(this);
		DiscountTextBox=GuiScreen.getDiscountLabelBox();
		LiterCounter = new Thread(this);
		LiterLabel=GuiScreen.getLiterLabel();
		PriceLabel=GuiScreen.getPriceLabel();
		StationCurrentUser=GuiScreen.getStationUser();
		NFCTextField=GuiScreen.getNFCTextField();
		StationUserLoginGui.setCashCheacked();

		/*-----Hand gui Icons-------*/
		BlueHand=GuiScreen.getBlueHand();
		BlueHandFlip=GuiScreen.getBlueHandFlip();
		GreenHand=GuiScreen.getGreenHand();
		GreenHandFlip=GuiScreen.getGreenHandFlip();
		RedHand=GuiScreen.getRedHand();
		RedHandFlip=GuiScreen.getRedHandFlip();
		
		
		BlueHand.addMouseListener(this);
		BlueHandFlip.addMouseListener(this);
		
		RedHand.addMouseListener(this);
		RedHandFlip.addMouseListener(this);
		
		GreenHand.addMouseListener(this);
		GreenHandFlip.addMouseListener(this);
		
		UserLogoutButton=GuiScreen.getUserLogoutButton();
		UserLogoutButton.addActionListener(this);
		
		
		LoginButton = GuiScreen.getLoginButton(); 	//Login Button on the main GUI
		LoginButton.addActionListener(this);		//Add action listener

			
		Customer=new callbackCustomer();
		EnteredUser = new callbackUser();
		this.Liter=0;
		this.Price=0;
		
		//Send To DB StationID To get Fuel Type
		SendUserIDToGetFuelPerStation();
		
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"StationUserLoginLayer");
		
		}
	/**
	 * This function set action listener to login & logout buttons
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==LoginButton){
			LoginButtonHandler();
		}
		if(e.getSource()==UserLogoutButton){
			LogoutButtonHandler();
		}
		if(e.getSource()==StartFuelingButton){
			FuelingProccess();
		}
		if(e.getSource()==Paybutton){
			PayProccess();
		}
		
	}

	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------Global Function--------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/
	/**
	 * Send To DB Request To Get All Fuel In Station
	 */
	private void SendUserIDToGetFuelPerStation(){

		/*-----send Station ID-------*/
		StationCurrentFuels = new callbackStationFuels(MessageType.getFuelPerStation); //create new CallbackStationFuels
		StationCurrentFuels.setGasStationID(StationCurrentUser.getUserID()); //Enter Station UserID
		Server.handleMessageFromClient(StationCurrentFuels);	//send to DB

	}
	/**
	 * This Function update fuel for this station
	 * @param e callbackVector with fuels
	 */
	private void UpdateFuelPerStation(callbackVector e ){
		/*------get callback Fuels ------*/
		FuelsInStation=e;					//Get from the common buffer new callback
		int NumbersFuelsInCurrentGasStaion=FuelsInStation.size();
		int index=0;
		/*
		 * 1=95
		 * 2=Scooter Fuel
		 * 4=Diesel
		 */
		while(index<NumbersFuelsInCurrentGasStaion){
			StationCurrentFuels=(callbackStationFuels)FuelsInStation.get(index);
			GasStationID=StationCurrentFuels.getGasStationID();
			
			if(StationCurrentFuels.getFuelID()==1) // there is 95 fuel
			{
				Fuel95IsExist=true;
				Fuel95CurrentAmount=StationCurrentFuels.getCurrentAmount();
				Fuel95ThresholdLimit=StationCurrentFuels.getThresholdLimit();
				Fuel95Price=StationCurrentFuels.getCurrentPrice();
				
			}
			if(StationCurrentFuels.getFuelID()==2) // there is scooter fuel
			{
				FuelScoterIsExist=true;
				FuelScoterCurrentAmount=StationCurrentFuels.getCurrentAmount();
				FuelScoterThresholdLimit=StationCurrentFuels.getThresholdLimit();
				FuelScoterPrice=StationCurrentFuels.getCurrentPrice();
				
			}
			if(StationCurrentFuels.getFuelID()==4) // there is diesel fuel
			{
				FuelDieselIsExist=true;
				FuelDieselCurrentAmount=StationCurrentFuels.getCurrentAmount();
				FuelDieselThresholdLimit=StationCurrentFuels.getThresholdLimit();
				FuelDieselPrice=StationCurrentFuels.getCurrentPrice();
			}
			index++;
		}
		

		
		
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------Login Function---------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/

	/**
	 * This function handle User login from gas station
	 */
	private void LoginButtonHandler()
	{
		
		
	/*--Check if NFC is OK--*/
	CheckNFC();
	
	/*--Check if User is OK--*/
	CheckUser();
	}

	/**
	 * This function switch left window
	 */
	private void GasStationSwitchScreen(){
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer, "left_panel");
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"GasFuelingCenterPanel");
	}

	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------NFC Function-----------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/
	
	/**
	 * Check NFC Input & Send To DB Car Number
	 */
	private void CheckNFC(){
		
		if(!NFCTextField.getText().equals("__-___-__") && !Checks.isNumeric(NFCTextField.getText())) //if nfc is not empty and enter illegal input
		{
			StationUserLoginGui.IllegalNFC();
		}
		
		if( !NFCTextField.getText().equals("__-___-__") && Checks.isNumeric(NFCTextField.getText()) ) //if nfc is not empty
		{ 
				/*-----send NFC to DB----*/
				UserCarNFC=new callbackCar(MessageType.getCarWithNFC);
				UserCarNFC.setCarNumber(NFCTextField.getText() );
				Server.handleMessageFromClient(UserCarNFC);

		}
	}
	/**
	 * Check Car have NFC & Send To DB Customer Info
	 * @param e Car Callback
	 */
	private void ValidNFCNumber(callbackCar e)
	{
	UserCarNFC=e;
	if(UserCarNFC.getYesNoNFC().equals("Yes"))
	{
	NFCIsExist=true;
	Customer = new callbackCustomer();
	Customer.setCustomersID(UserCarNFC.getCustomerID()); // NFC car callback -> Customer ID
	/*------send to DB User ID and get Customer id*/
	Customer.setWhatToDo(MessageType.getCustomer);
	Customer.setCustomersID(UserCarNFC.getCustomerID()); //Connect Customer.userID-> userID
	Server.handleMessageFromClient(Customer);
	}
	else 
	StationUserLoginGui.IllegalNFC();
	}
	/**
	 * Check if Costing Model allow Customer To Fuel In Gas Station 
	 * @param e - Customer Callback
	 */
	private void CheakModelCostingNFC(callbackCustomer e){
		Customer=e;
		if(Customer.isAllowToEnterGasStation(this.GasStationID)) // if Costing model is oK!
		{
	/*------ Move to the next screen ------*/		
			StationUserLoginGui.ClearErrorMessage(); 		//Clear the error message if exists
			GasStationSwitchScreen();					// go to the next gui screen by user role
			logoutflag=true;								//logout will return to this screen
			MainLogoutButton.setEnabled(false);
			StationUserLoginGui.setlogoutvisable(true);
			StationUserLoginGui.setPassword("");
			SendUserIDToGetFuelPerStation();
			ResetPumpSatation();
			StationUserLoginGui.setCombobox(NFCTextField.getText());
			StationUserLoginGui.setNFCTextField("");
			StationUserLoginGui.setComboboxedit(false);
			UserCarsNumbers= new ArrayList<String>();
			callbackCar onecar=new callbackCar();
			onecar.setCarNumber(StationUserLoginGui.getComboboxCarSelect());
			onecar.setCarID(UserCarNFC.getCarID());
			UserCarsNumbers.add(onecar);
			DiscountTextBox.setText("<html> Wellcome "+Customer.getCustomerFirstName()+" "+Customer.getCustomerLastName()+"</html>");
		}	
		else // Not Costing model
		{
			StationUserLoginGui.IllegalCostingmodel();
		}
		
	}//End of funtion
	
	
	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------NON-NFC Function-------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/
	
	/**
	 * Read Information form Gui And Send To DB
	 */
	private void CheckUser(){
	//EnteredUser = new callbackUser(MessageType.getCheckExistsUserPassForStation,StationUserLoginGui.getUserName(),StationUserLoginGui.getPassword());
	EnteredUser.setWhatToDo(MessageType.getCheckExistsUserPassForStation);
	EnteredUser.setUserName(StationUserLoginGui.getUserName());
	EnteredUser.setPassword(StationUserLoginGui.getPassword());
	
	
		if(!StationUserLoginGui.getPassword().equals(""))
		{
			Server.handleMessageFromClient(EnteredUser);		
		}
		
	}
	/**
	 * This Function Check If User PassWord Is ok
	 * @param e - User CallBack
	 */
	private void CheckUserInfo(callbackUser e)
	{
		EnteredUser = e;				//Casting to callbackUser
		String Password = StationUserLoginGui.getPassword();
		
		/*--------Try To Enter With Station User-------*/
		if (EnteredUser.getLoggedIn().equals("Station"))
		{			
		StationUserLoginGui.IllegalUserName();
		}
		if(! Password.equals(EnteredUser.getPassword()) )
		{
			StationUserLoginGui.IllegalPassword();
		}
		else if (Password.equals(EnteredUser.getPassword()))
		{				
		NFCIsExist=false;
		/*------send to DB User ID and get Customer id*/
		Customer = new callbackCustomer();
		Customer.setWhatToDo(MessageType.getCustomer);
		Customer.setUserID(EnteredUser.getUserID()); //Connect Customer.userID-> userID
		Server.handleMessageFromClient(Customer);
		}
	}//End of function
	/**
	 * Check if User Can Enter To Gas Station With The Costing Model
	 * @param e - Customer Callback
	 */
	private void CheckUserCostingModelNonNFC(callbackCustomer e){
		Customer=e;
		
		if(Customer.isAllowToEnterGasStation(this.GasStationID)) // if Costing model is oK!
		{
		getAllUserCar(Customer.getCustomersID());
		}
		else // Not Costing model
		{
			StationUserLoginGui.IllegalCostingmodel();
		}
	}
	
	/**
	 * This function get userID and return array of car number strings
	 * Build callback Car with Customer ID
	 * return Vector with cars
	 * @return 
	 */
	private void getAllUserCar(int CurrentCustomerID){
		UserCarsNumbers=new ArrayList<>();
		callbackVector LocalVector = new callbackVector();
		CustomerCars=new callbackCar();
		callbackVector TempVector = new callbackVector();
		//callbackVector LocalVector = new callbackVector();
		/*--- send to DB----*/
		CustomerCars.setWhatToDo(MessageType.getCarDetailes);
		CustomerCars.setCustomerID(CurrentCustomerID);
		Server.handleMessageFromClient(CustomerCars);
		

	}

	/**
	 * This Funtion Move To Gas Station If all The Input Test Successes
	 * @param e -Callback vector of Customer Cars
	 */
	private void EnterUserToGasStation(callbackVector e){
	
		UserCarsNumbers = new ArrayList<>();	
	for(int i=0;i<e.size();i++)
	{
		UserCarsNumbers.add(e.get(i));
	}
	//EnteredUser.setWhatToDo(MessageType.updateUserLogin);
	//Server.handleMessageFromClient(EnteredUser);	//Update user is logged in, in the DB
	
	StationUserLoginGui.ClearErrorMessage(); 		//Clear the error message if exists
	GasStationSwitchScreen();					// go to the next gui screen by user role
	logoutflag=true;								//logout will return to this screen
	MainLogoutButton.setEnabled(false);
	StationUserLoginGui.setlogoutvisable(true);
	StationUserLoginGui.setPassword("");
	StationUserLoginGui.setNFCTextField("");
	SendUserIDToGetFuelPerStation();
	ResetPumpSatation();
	StationUserLoginGui.setCombobox(UserCarsNumbers);
	DiscountTextBox.setText("<html> Wellcome "+Customer.getCustomerFirstName()+" "+Customer.getCustomerLastName()+"</html>");
	}

	
	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------Logout Function--------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/
		

	/**
	 * This function handle logout window for User
	 */
	private void LogoutButtonHandler(){
		if(logoutflag){ // if User can logout
			logoutflag=false;
			/* restore to Enter to Station Login*/
			ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
			ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
			ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
			ContainerCard.show(CenterCardContainer,"StationUserLoginLayer");
			/*reset all buttons*/
			StationUserLoginGui.setlogoutvisable(false);
			MainLogoutButton.setEnabled(true); // Enable to logout from station
			//EnteredUser.setWhatToDo(MessageType.updateUserLogout);
			//EnteredUser.setUserID(Customer.getUserID());
			//Server.handleMessageFromClient(EnteredUser);
			StationUserLoginGui.setUserName();
			NFCTextField.setValue("__-___-__");
			NFCIsExist=false;
			StationUserLoginGui.setComboboxedit(true);
			StationUserLoginGui.ClearErrorMessage();
			ResetPumpSatation();
		}
	}
	
	
	/*--------------------------------------------------------------------------------------------------*/
	/*---------------------------------Gas Station Function---------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------*/
	
	
	/**
	 * This function handle mouse click on Pump Hands
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		
		/*user enter in NFC - check if he can click right pump*/
		boolean SelectCar=false;
		if(StationUserLoginGui.getComboboxCarSelect().equals("Select Car")) SelectCar=true;
		
		 if((e.getComponent()==BlueHand || e.getComponent()==GreenHand || e.getComponent()==RedHand ) && SelectCar ) // select pump with no car number
		 {
			 DiscountTextBox.setText("*Select Car Number First To Begin.");
		 }
		 else
		 { 
				if(NFCIsExist)
				{
					int UserFuel=UserCarNFC.getFuelID();
					/*
					 * 1=95
					 * 2=Scooter Fuel
					 * 4=Diesel
					 */
					if(e.getComponent()==BlueHand && FuelDieselIsExist ){
						if(!UserIsFueling){  //if user is not Fueling
							DiscountTextBox.setText("");
							if(UserFuel!=4)
							{
								DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
							}
							if(FuelDieselCurrentAmount<0.5)
							{
								DiscountTextBox.setText("Out Of Fuel");
							}
							if(UserFuel==4 && FuelDieselCurrentAmount>0.7)
							{
								UserIsFueling=true;
								FuelDiesel=true;
								this.Liter=0;
								DiscountTextBox.setText("The Price Per Liter For Diesel is: "+FuelDieselPrice);
								StartFuelingButton.setEnabled(true);
								StationUserLoginGui.BluePumpShow();
							}
						}
					}
					if(e.getComponent()==BlueHandFlip)
					{
						if(!UserNeedToPay)
						{
							StationUserLoginGui.BluePumpNotShow(); //return the hand to the right palce
							StartFuelingButton.setEnabled(false);
							UserIsFueling=false;
							DiscountTextBox.setText("");
						}
					}
					if(e.getComponent()==RedHand && Fuel95IsExist){
						if(!UserIsFueling){  //if user is not fuling
							DiscountTextBox.setText("");
							if(UserFuel!=1)
							{
								DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
							}
							if(Fuel95CurrentAmount<0.5)
								DiscountTextBox.setText("Out Of Fuel");
							if(UserFuel==1 && Fuel95CurrentAmount > 0.7)
							{
								UserIsFueling=true;
								Fuel95=true;
								this.Liter=0;
								DiscountTextBox.setText("The Price Per Liter For 95 is: "+Fuel95Price);
								StartFuelingButton.setEnabled(true);
								StationUserLoginGui.RedPumpShow();
							}
						}
					}
					if(e.getComponent()==RedHandFlip){
						if(!UserNeedToPay){
							StationUserLoginGui.RedPumpNotShow();//return the hand to the right palce
							StartFuelingButton.setEnabled(false);
							UserIsFueling=false;
							DiscountTextBox.setText("");
						}
					}
					if(e.getComponent()==GreenHand && FuelScoterIsExist)
						{
						DiscountTextBox.setText("");
							if(!UserIsFueling)
							{  //if user is not fuling
								if(UserFuel!=2)
								{
									DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
								}
								if(FuelScoterCurrentAmount < 0.5)
									DiscountTextBox.setText("Out Of Fuel");
								if(UserFuel==2 && FuelScoterCurrentAmount > 0.7)
								{
									UserIsFueling=true;
									FuelScoter=true;
									this.Liter=0;
									DiscountTextBox.setText("The Price Per Liter For Scooter Fuel is: "+FuelScoterPrice);
									StartFuelingButton.setEnabled(true);
									StationUserLoginGui.GreenPumpShow();
								}
							}
						}
					if(e.getComponent()==GreenHandFlip)
						{
							if(!UserNeedToPay)
							{
								StationUserLoginGui.GreenPumpNotShow();//return the hand to the right palce
								StartFuelingButton.setEnabled(false);
								UserIsFueling=false;
								DiscountTextBox.setText("");
							}
						}
				
				}
				else
				/* ---------Regular Fueling --------*/
				{	
					// Get Combobox Selcted Item
					String currentcar=StationUserLoginGui.getComboboxCarSelect();
					int fueltypecurrentcar=0;
					fueltypecurrentcar=FindCarTypeByCarNumber(currentcar);   //Check car Fuel Type
					/*
					 * 1=95
					 * 2=Scooter Fuel
					 * 4=Diesel
					 */
					if(e.getComponent()==BlueHand && FuelDieselIsExist){
						DiscountTextBox.setText("");
						if(!UserIsFueling){  //if user is not fuling
							if(fueltypecurrentcar!=4)
							{
								DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
							}
							if(FuelDieselCurrentAmount < 0.5)
								DiscountTextBox.setText("Out Of Fuel");
							if(fueltypecurrentcar==4 && FuelDieselCurrentAmount > 0.7)
							{
								UserIsFueling=true;
								FuelDiesel=true;
								this.Liter=0;
								DiscountTextBox.setText("The Price Per Liter For Diesel is: "+FuelDieselPrice);
								StartFuelingButton.setEnabled(true);
								StationUserLoginGui.BluePumpShow();
							}
						}
					}
					if(e.getComponent()==BlueHandFlip){
						if(!UserNeedToPay){
							StationUserLoginGui.BluePumpNotShow(); //return the hand to the right palce
							StartFuelingButton.setEnabled(false);
							UserIsFueling=false;
							DiscountTextBox.setText("");
						}
					}
					if(e.getComponent()==RedHand && Fuel95IsExist){
						DiscountTextBox.setText("");
						if(!UserIsFueling){  //if user is not fuling
							if(fueltypecurrentcar!=1)
							{
								DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
							}
							if(Fuel95CurrentAmount<0.5)
								DiscountTextBox.setText("Out Of Fuel");
							if(fueltypecurrentcar==1 && Fuel95CurrentAmount>0.7)
							{
								UserIsFueling=true;
								Fuel95=true;
								this.Liter=0;
								DiscountTextBox.setText("The Price Per Liter For 95 is: "+Fuel95Price);
								StartFuelingButton.setEnabled(true);
								StationUserLoginGui.RedPumpShow();
							}
						}
					}
					if(e.getComponent()==RedHandFlip){
						if(!UserNeedToPay){
							StationUserLoginGui.RedPumpNotShow();//return the hand to the right palce
							StartFuelingButton.setEnabled(false);
							UserIsFueling=false;
							DiscountTextBox.setText("");
						}
					}
					if(e.getComponent()==GreenHand && FuelScoterIsExist){
						DiscountTextBox.setText("");
						if(!UserIsFueling){  //if user is not fuling
							if(fueltypecurrentcar!=2)
							{
								DiscountTextBox.setText("Pump Does Not Match The Type Of Vehicle Fuel");
							}
							if(FuelScoterCurrentAmount < 0.5)
								DiscountTextBox.setText("Out Of Fuel");
							if(fueltypecurrentcar==2 && FuelScoterCurrentAmount > 0.7)
							{
								UserIsFueling=true;
								FuelScoter=true;
								this.Liter=0;
								DiscountTextBox.setText("The Price Per Liter For Scooter Fuel is: "+FuelScoterPrice);
								StartFuelingButton.setEnabled(true);
								StationUserLoginGui.GreenPumpShow();
							}
						}
					}
					if(e.getComponent()==GreenHandFlip){
						if(!UserNeedToPay){
							StationUserLoginGui.GreenPumpNotShow();//return the hand to the right palce
							StartFuelingButton.setEnabled(false);
							UserIsFueling=false;
							DiscountTextBox.setText("");
						}
					}
				}
		 }
	}
/**
 * This function handle the Start/Stop Button
 */
	
	public void FuelingProccess(){		
		
		//Send To DB StationID To get Fuel Type
		SendUserIDToGetFuelPerStation();
		
		/*------ start ------*/
		if(PressStartStopButtonFlag==true && UserIsFueling)
		{
			StationUserLoginGui.getUserLogoutButton().setEnabled(false);
			PressStartStopButtonFlag=false;
			StartFuelingButton.setText("Stop");
			StationUserLoginGui.setComboboxedit(false);
			DiscountTextBox.setText("");
			Paybutton.setEnabled(false);
			UserNeedToPay=true;
			LiterCounter=new Thread(this);
			LiterCounter.start();
		}
		/*------ Stop ------*/
		else
		{ 
			LiterCounter.interrupt();
			LiterCounter.stop();
			/*
			 * 1=95
			 * 2=Scooter Fuel
			 * 4=Diesel
			 */
			int FuelID=0;
			if(FuelDiesel) FuelID=4;
			if(Fuel95) FuelID=1;
			if(FuelScoter) FuelID=2;
			String CurrentCarNumber;
			CurrentCarNumber=StationUserLoginGui.getComboboxCarSelect();
			CurrentCarNumber=CurrentCarNumber.replace("-","");

			if(Customer.getCreditCard().equals(""))
			{
				StationUserLoginGui.setCreditRadioButtonEdit(false); 
				StationUserLoginGui.setCashCheacked();
			}
			else
			{
				StationUserLoginGui.setCreditRadioButtonEdit(true); 
			}
			
			PressStartStopButtonFlag=true;
			Paybutton.setEnabled(true);
			StartFuelingButton.setText("Start");
			DiscountTextBox.setVisible(true);

			DiscountCalulation(Float.valueOf(myFormatter.format(Price)), this.Liter, FuelID, Customer.getCustomersID(),CurrentCarNumber);
		}
		
		
	}
	/**
	 * This function handle the payment - send data to DB and reset the station
	 */
	public void PayProccess()
	{
		SendUserIDToGetFuelPerStation();
		if(UserNeedToPay==true)
		{
			/*---Pushed Pay button---*/
			/*---set CurrentSale Details---*/
			CurrentSale.setWhatToDo(MessageType.setNewGasStationSale);
			CurrentSale.setUserID(Customer.getUserID());
			CurrentSale.setCarID( getCarIDByCarNumberr( StationUserLoginGui.getComboboxCarSelect() ) );
			if(Customer.getCustomerType().equals("Commercial"))
			{
				String[] options = {"OK"};
				JPanel panel = new JPanel();
				JLabel lbl = new JLabel("Enter Driver name: ");
				lbl.setFont((new Font("Tahoma", Font.PLAIN, 15)));
				JTextField txt = new JTextField(10);

				panel.add(lbl);
				panel.add(txt);
				
				txt.setText("");

				int selectedOption = JOptionPane.showOptionDialog(null, panel, "Driver Input", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
					while(txt.getText().equals(""))
					{
						lbl.setText("Wrong Driver Input, Try Again");
						selectedOption = JOptionPane.showOptionDialog(null, panel, "Driver Input", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
					}
				  String text = txt.getText();
				
				CurrentSale.setDriverName(text);
			}
			else
			{
				CurrentSale.setDriverName(Customer.getCustomerFirstName());
			}
			Server.handleMessageFromClient(CurrentSale);
			
		}
	}//End of funtion
	/**
	 * Main Thread count liter per 80 millisecond 
	 */
	public void run(){
		
		boolean havefuel=true;
		while(havefuel){
			try{
				this.Liter+=0.13;
				if(FuelDiesel)
				{
					if(FuelDieselCurrentAmount-this.Liter<=0.2) 
					{
						havefuel=false;
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("Out of fuel");
					}
					Price=Liter*FuelDieselPrice;
				}
				else if(Fuel95)
				{
					if(Fuel95CurrentAmount-this.Liter<=0.2) 
					{
						havefuel=false;
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("*Out of fuel");
					}
					Price=Liter*Fuel95Price;
				}
				else if(FuelScoter)
				{
					if(FuelScoterCurrentAmount-this.Liter<=0.2) 
					{
						havefuel=false;
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("Out of fuel");
					}
					Price=Liter*FuelScoterPrice;
				}
				
				Liter = Float.valueOf(myFormatter.format(Liter));
				StationUserLoginGui.setPriceLabel(String.valueOf(myFormatter.format(Price)));
				LiterLabel.setText(String.valueOf(Liter));
				Thread.sleep(80);
            } catch (InterruptedException e1) {
  
            	e1.printStackTrace();
               
            }
		}
		
	}
	/**
	 * Reset all station back to original 
	 */
	private void ResetPumpSatation(){		
		UserNeedToPay=false;
		Paybutton.setEnabled(false);
		UserIsFueling=false;
		Liter=0;
		Price=0;
		LiterLabel.setText(String.valueOf(Liter));
		StationUserLoginGui.setPriceLabel("0");
		StationUserLoginGui.getUserLogoutButton().setEnabled(true);
		StartFuelingButton.setEnabled(false);
		DiscountTextBox.setText("");
		DiscountTextBox.setVisible(true);
		StationUserLoginGui.getRedHand().setVisible(false);
		StationUserLoginGui.getGreenHand().setVisible(false);
		StationUserLoginGui.getBlueHand().setVisible(false);
		//reset all Hand back to the station
		if(Fuel95IsExist){
			StationUserLoginGui.RedPumpNotShow();//return the hand to the right palce
			UserIsFueling=false;
			StationUserLoginGui.getRedHand().setVisible(true);
		}
		if( FuelScoterIsExist){
			StationUserLoginGui.GreenPumpNotShow();//return the hand to the right palce
			UserIsFueling=false;
			StationUserLoginGui.getGreenHand().setVisible(true);
		}
		if(FuelDieselIsExist){
			StationUserLoginGui.BluePumpNotShow(); //return the hand to the right palce
			UserIsFueling=false;
			StationUserLoginGui.getBlueHand().setVisible(true);
		}
		/*----Reset Combobox -----*/
		StationUserLoginGui.setComboboxedit(true);
		
	}

	/**
	 * This function find the right car to number
	 * @param CarToFind - input car number " XX-XXX-XX"
	 * @return
	 */
	private int FindCarTypeByCarNumber(String CarToFind)
	{
		int fuel = 0,i;
		for(i=0;i<UserCarsNumbers.size();i++)
		{
			if(((callbackCar)UserCarsNumbers.get(i)).getCarNumber().equals(CarToFind))
			{
				fuel=((callbackCar)UserCarsNumbers.get(i)).getFuelID();
			}
		}
		return fuel;
	}
	
	private int getCarIDByCarNumberr(String CarNUM){
		for(int i=0;i<UserCarsNumbers.size();i++){
			String s= String.valueOf(((callbackCar)UserCarsNumbers.get(i)).getCarNumber());
			if(		s.equals(CarNUM) )	
				{
					return ((callbackCar)UserCarsNumbers.get(i)).getCarID();
				}
			
		}
		return 0;
	}
	
/**
 * This function check & update discount for user
 * @param Payment - Price
 * @param FueAmount - Liters
 * @param GasStationID - GasID
 * @param FuelID - 1=95, 2=Scooter Fuel, 4=Diesel
 * @param CustomerID - CusotmerID
 */
	private void DiscountCalulation(float Payment,float FueAmount,int FuelID,int CustomerID,String CarNumber){
		CurrentSale=new callbackSale();
		CurrentSale.setCarNumber(CarNumber);
		CurrentSale.setPayment(Payment);
		CurrentSale.setFuelAmount(FueAmount);
		CurrentSale.setGasStationID(GasStationID);
		CurrentSale.setFuelID(FuelID);
		if(NFCIsExist)
		{
			CustomerID=UserCarNFC.getCustomerID();
		}
		CurrentSale.setCustomersID(CustomerID);
		CurrentSale.setWhatToDo(MessageType.getSaleDiscount);
		Server.handleMessageFromClient(CurrentSale);
		 
	}
	private void UpdateSalePrice(CallBack e){
		if(e instanceof callbackSuccess) // No Discount
		{
			DiscountTextBox.setText(" You Need To Pay: "+myFormatter.format(Price)+" Shekel *No Discount");
		}
		if(e instanceof callbackCampaign) // Was Discount
		{
			Temp=(callbackCampaign)e;
			Price=((callbackCampaign) Temp).getPriceAfterDiscount();
			DiscountTextBox.setText("<html>You Need To Pay: "+myFormatter.format(Price)+" Shekel"+"<br/>"
			+" Discount = "+((callbackCampaign) Temp).getDiscountPercentage()+"<br/>"+
			" By "+((callbackCampaign) Temp).getCampaignDescription()+"<br/>"+
			"Calculation:"+((callbackCampaign) Temp).getCalculationMethod()
			+"</html>");
		/*----Send To DB Update*/
		CurrentSale.setPayment(Price);
		CurrentSale.setCampaignID(((callbackCampaign) Temp).getCampaignID());
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){	
			switch(((CallBack) arg).getWhatToDo()){
				case getCheckExistsUserPassForStation:
						if(arg instanceof callback_Error)
						{
							StationUserLoginGui.IllegalUserName();
						}
						if(arg instanceof callbackUser)
						{
							CheckUserInfo((callbackUser) arg);
						}
						
					break;
				case setNewGasStationSale:
					if(arg instanceof callback_Error)
					{
						DiscountTextBox.setText("Error With Payment");
					}
					if (arg instanceof callbackSuccess)
					{
						ResetPumpSatation();
					}
					break;

				case getCarWithNFC:
					if(arg instanceof callback_Error)
					{
						StationUserLoginGui.NFCNotExist();
					}
					if(arg instanceof callbackCar)
					{
						ValidNFCNumber((callbackCar) arg);
					}
					break;
				case getCustomer:
					if(NFCIsExist)
					{
						if(arg instanceof callback_Error)
						{
							StationUserLoginGui.IllegalCustomerID();
						}
						if(arg instanceof callbackCustomer)
						{
							CheakModelCostingNFC((callbackCustomer) arg);
						}
					}
					if(!NFCIsExist)
					{
						if(arg instanceof callback_Error)
						{
							StationUserLoginGui.IllegalCustomerID();
						}
						if(arg instanceof callbackCustomer)
						{
							CheckUserCostingModelNonNFC((callbackCustomer) arg);
						}
					}
						
					break;
				case getSaleDiscount:
					UpdateSalePrice((CallBack) arg);
					break;
			/*Don't change!*/
			default:
				super.update(o, arg);
				break;
			/*-------------*/
			}
		}
		else if(arg instanceof callbackVector){
				switch(((callbackVector) arg).getWhatToDo()){
				case getFuelPerStation:
					UpdateFuelPerStation((callbackVector) arg);
					break;
				case getCarDetailes:
					/*Error user have no cars or Customer error*/
					if(((callbackVector) arg).get(0) instanceof callback_Error)
					{
						StationUserLoginGui.IllegalCustomerHasNoCars();
					}
					if(((callbackVector) arg).get(0) instanceof callbackCar)
					{
						EnterUserToGasStation((callbackVector) arg);
					}
					break;
				default:
					break;
				
				}
		}		
	}
	
	}
//	8565232
// 2123650
