package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.security.auth.callback.Callback;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
		
		/*--------Need GAS STATION ID!!!-----*/
		//
		//GasStationID=StationCurrentUser.
		//	
		//		
				
		Customer=new callbackCustomer();
		this.Liter=0;
		this.Price=0;
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
		
		
		LoginButton = GuiScreen.getLoginButton(); 					//Login Button on the main GUI
		LoginButton.addActionListener(this);							//Add action listener
		
		
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"StationUserLoginLayer");
		
		}
	/**
	 * This function set action listenter to login & logout buttons
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
	/**
	 * This function handel User login from gas station
	 */
	private void LoginButtonHandler()
	{
			/*--Check if NFC is OK--*/
			CheckNFC();
			/*------ Read fields from gui ------*/
			EnteredUser = new callbackUser(MessageType.getCheckExistsUserPass,StationUserLoginGui.getUserName(),StationUserLoginGui.getPassword());
			/*--Check if User is OK--*/
			CheckUser();
	}
			
	/**
	 * This function switch left window
	 */
	private void GasStationSwitchLeftPanel(){
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer, "left_panel");
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"GasFuelingCenterPanel");
	}
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
			EnteredUser.setWhatToDo(MessageType.updateUserLogout);
			EnteredUser.setUserID(Customer.getUserID());
			Server.handleMessageFromClient(EnteredUser);
			getCallBackFromBuffer();//Clean buffer
			StationUserLoginGui.setUserName();
			NFCTextField.setValue("__-___-__");
			NFCIsExist=false;
			StationUserLoginGui.setComboboxedit(true);
			StationUserLoginGui.ClearErrorMessage();
			ResetPumpSatation();
		}
	}
	/**
	 * This function handle mouse click on Pump Hands
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		
		/*user enter in NFC - check if he can click right pump*/
		if(NFCIsExist)
		{
			int UserFuel=UserCarNFC.getFuelID();
			/*
			 * 1=95
			 * 2=Scooter Fuel
			 * 4=Diesel
			 */
			if(e.getComponent()==BlueHand && FuelDieselIsExist){
				if(!UserIsFueling){  //if user is not fuling
					DiscountTextBox.setText("");
					if(UserFuel!=4)
					{
						DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
					}
					else
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
						DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
					}
					else
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
							DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
						}
						else
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
						DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
					}
					else
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
						DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
					}
					else
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
						DiscountTextBox.setText("*Pump Does Not Match The Type Of Vehicle Fuel");
					}
					else
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
/**
 * This function handle the Start/Stop Button
 */
	public void FuelingProccess(){		
		/*------User Press start fueling------*/
		if(PressStartStopButtonFlag==true && UserIsFueling)
		{
			StationUserLoginGui.getUserLogoutButton().setEnabled(false);
			PressStartStopButtonFlag=false;
			StartFuelingButton.setText("Stop");
			StationUserLoginGui.setComboboxedit(false);
			DiscountTextBox.setText("");
			Paybutton.setEnabled(false);
			UserNeedToPay=true;
			// start counting liters thread
			LiterCounter.start();
		}
		//User Stop the Pumping
		else
		{ 
			LiterCounter.stop();
			LiterCounter=new Thread(this);
			/*
			 * 1=95
			 * 2=Scooter Fuel
			 * 4=Diesel
			 */
			int FuelID=0;
			if(FuelDiesel) FuelID=4;
			if(Fuel95) FuelID=1;
			if(FuelScoter) FuelID=2;
			DiscountCalulation(Float.valueOf(myFormatter.format(Price)), this.Liter, this.GasStationID, FuelID, Customer.getCustomersID());
			//if User Have'nt Credit Card
			if(Customer.getCreditCard().equals(""))
			{
				StationUserLoginGui.setCreditRadioButtonEdit(false); // Not Have NFC
				StationUserLoginGui.setCashCheacked();
			}
			else
				StationUserLoginGui.setCreditRadioButtonEdit(true); // have NFC
			
			PressStartStopButtonFlag=true;
			Paybutton.setEnabled(true);
			StartFuelingButton.setText("Start");
			DiscountTextBox.setVisible(true);
		}
		
		
	}
	/**
	 * This function handle the payment - send data to DB and reset the station
	 */
	public void PayProccess(){
		if(UserNeedToPay==true){
			/*---Pushed Pay button---*/
			ResetPumpSatation(); 
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
	/**
	 * Main Thread count liter per 80 millisecond 
	 */
	public void run(){
		while(true){
			try{
				
				this.Liter+=0.13;
				if(FuelDiesel)
				{
					if(FuelDieselCurrentAmount-this.Liter<=0) 
					{
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("*Out of fuel");
					}
					Price=Liter*FuelDieselPrice;
				}
				else if(Fuel95)
				{
					if(Fuel95CurrentAmount-this.Liter<=0) 
					{
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("*Out of fuel");
					}
					Price=Liter*Fuel95Price;
				}
				else if(FuelScoter)
				{
					if(FuelScoterCurrentAmount-this.Liter<=0) 
					{
						/*-------fuel tank is empty need to stop fueling*/
						this.Liter-=0.13;
						DiscountTextBox.setText("*Out of fuel");
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
	 * Update Station with the current fuels types, fuel price
	 * 
	 */
	private void UpdateStationInfo(){
		/*
		 * 1=95
		 * 2=Scooter Fuel
		 * 4=Diesel
		 */
		/*-----send Station ID-------*/
		StationCurrentFuels = new callbackStationFuels(MessageType.getFuelPerStation); //create new CallbackStationFuels
		StationCurrentFuels.setGasStationID(StationCurrentUser.getUserID()); //Enter Station UserID
		Server.handleMessageFromClient(StationCurrentFuels);	//send to DB		
		/*------get callback Fuels ------*/
		FuelsInStation=(callbackVector)getCallBackVectorFromBuffer();					//Get from the common buffer new callback
		int NumbersFuelsInCurrentGasStaion=FuelsInStation.size();
		int index=0;
		while(index<NumbersFuelsInCurrentGasStaion){
			StationCurrentFuels=(callbackStationFuels)FuelsInStation.get(index);
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
		
		/*------Empty buffer ------*/
		//getCallBackFromBuffer();//Emptying buffer
		
		
		
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
	 * Check if User have NFC by get his Car nubmer
	 */
	private void CheckNFC(){
		
		if( !NFCTextField.getText().equals("__-___-__") && Checks.isNumeric(NFCTextField.getText()) ) //if nfc is not empty
		{ 
			
				/*-----send NFC to DB----*/
				UserCarNFC=new callbackCar(MessageType.getCarWithNFC);
				UserCarNFC.setCarNumber(NFCTextField.getText() );
				Server.handleMessageFromClient(UserCarNFC);

				/*------Waiting for callback ------*/
				 Temp= (CallBack) getCallBackFromBuffer();
				/*-------if NFC Not Exist---*/ 
					if(Temp instanceof callback_Error)
					{
						StationUserLoginGui.NFCNotExist();
					}
				/*-------if NFC Exist---*/ 
				//			
					else  
					{
						UserCarNFC=(callbackCar) Temp;
						if(UserCarNFC.getYesNoNFC().equals("Yes"))
							{
							NFCIsExist=true;
							Customer.setCustomersID(UserCarNFC.getCustomerID()); // NFC car callback -> Customer ID
							/*------ Move to the next screen ------*/		
								StationUserLoginGui.ClearErrorMessage(); 		//Clear the error message if exists
								GasStationSwitchLeftPanel();					// go to the next gui screen by user role
								logoutflag=true;								//logout will return to this screen
								MainLogoutButton.setEnabled(false);
								StationUserLoginGui.setlogoutvisable(true);
								StationUserLoginGui.setPassword("");
								UpdateStationInfo();
								ResetPumpSatation();
								StationUserLoginGui.setCombobox(NFCTextField.getText());
								StationUserLoginGui.setNFCTextField("");
								StationUserLoginGui.setComboboxedit(false);
								
							}
					}
		}
		if(!NFCTextField.getText().equals("__-___-__") && !Checks.isNumeric(NFCTextField.getText())) //if nfc is not empty and enter illegal input
		{
			StationUserLoginGui.IllegalNFC();
		}
	}//End of funtion
	/**
	 * Check if Username & Password are correct
	 */
	private void CheckUser(){
		if(!StationUserLoginGui.getPassword().equals("")){
			/*------Send user name and password query ------*/
			Server.handleMessageFromClient(EnteredUser);					//Send query to DB			
	
			/*------Waiting for callback ------*/
			LocalUserCallBack = (CallBack) getCallBackFromBuffer();					//Get from the common buffer new callback
				
			/*------ User not exists ------*/
			if (LocalUserCallBack instanceof callback_Error){				//If the query back empty or the entered values not illegal
				StationUserLoginGui.IllegalUserName();	
			}
			/*------ User exists, check the reset parameters ------*/
			else if (LocalUserCallBack instanceof callbackUser){
				EnteredUser = (callbackUser) LocalUserCallBack;				//Casting to callbackUser
				String Password = StationUserLoginGui.getPassword();
				if (Password.equals(EnteredUser.getPassword())){			//Check if Password	correct
					if (EnteredUser.getLoggedIn().equals("Station")){			//Check if User Already Connected
						StationUserLoginGui.IllegalUserName();
					}
			/*------ Move to the next screen ------*/
					else{
						EnteredUser.setWhatToDo(MessageType.updateUserLogin);
						Server.handleMessageFromClient(EnteredUser);	//Update user is logged in, in the DB	
						getCallBackFromBuffer();						//Emptying buffer	
						NFCIsExist=false;
						
						/*------send to DB User ID and get Customer id*/
						Customer.setWhatToDo(MessageType.getCustomer);
						Customer.setUserID(EnteredUser.getUserID()); //Connect Customer.userID-> userID
						Server.handleMessageFromClient(Customer);
						
						/*-------get Cusomter ID---------------------*/
						LocalUserCallBack = (CallBack) getCallBackFromBuffer();
						if(LocalUserCallBack instanceof callback_Error)
						{
							StationUserLoginGui.IllegalCustomerID();
						}
						else if(LocalUserCallBack instanceof callbackCustomer)
						{
						Customer=(callbackCustomer) LocalUserCallBack;
						/* Get all Customer Cars*/
						UserCarsNumbers= getAllUserCar(Customer.getCustomersID());
						StationUserLoginGui.ClearErrorMessage(); 		//Clear the error message if exists
						GasStationSwitchLeftPanel();					// go to the next gui screen by user role
						logoutflag=true;								//logout will return to this screen
						MainLogoutButton.setEnabled(false);
						StationUserLoginGui.setlogoutvisable(true);
						StationUserLoginGui.setPassword("");
						StationUserLoginGui.setNFCTextField("");
						UpdateStationInfo();
						ResetPumpSatation();
						StationUserLoginGui.setCombobox(UserCarsNumbers);
						DiscountTextBox.setText("<html> Wellcome "+Customer.getCustomerFirstName()+" "+Customer.getCustomerLastName()+"</html>");
						}
					}
				}
				else StationUserLoginGui.IllegalPassword();							//Display password error message
			}
		}
	}
	/**
	 * This function get userID and return array of car number strings
	 * Build callback Car with Customer ID
	 * return Vector with cars
	 * @return 
	 */
	private ArrayList getAllUserCar(int CurrentCustomerID){
		UserCarsNumbers=new ArrayList<>();
		callbackVector LocalVector = new callbackVector();
		CustomerCars=new callbackCar();
		callbackVector TempVector = new callbackVector();
		//callbackVector LocalVector = new callbackVector();
		/*--- send to DB----*/
		CustomerCars.setWhatToDo(MessageType.getCarDetailes);
		CustomerCars.setCustomerID(CurrentCustomerID);
		Server.handleMessageFromClient(CustomerCars);
		/*----get buffer-----*/
		LocalVector = (callbackVector) getCallBackVectorFromBuffer();
		
		/*Error user have no cars or Customer error*/
		if(LocalVector.get(0) instanceof callback_Error)
		{
			if(((callback_Error) LocalUserCallBack).getErrorMassage().equals("No records of cars for this customer."))
			StationUserLoginGui.IllegalCustomerHasNoCars();
			else
				StationUserLoginGui.IllegalCustomerID();
		}
		/*Success User have cars*/
		if(LocalVector.get(0) instanceof callbackCar)
		{
			for(int i=0;i<LocalVector.size();i++)
			{
			
			UserCarsNumbers.add(LocalVector.get(i));
			}
			
		}
		return UserCarsNumbers;
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
	
	/**
	 * This function check if user can enter to statio by his station costing model
	 * @return true if is OK
	 */
	private boolean CheckIfUserCanEnterToGasStationByCostingModel(callbackCustomer currentCustomer)

	{
		//"Sonol"/"Paz".... 
		/*--------need Qurey---- */
		return true;
	}

/**
 * This funtion check & update discount for user
 * @param Payment - Price
 * @param FueAmount - Liters
 * @param GasStationID - GasID
 * @param FuelID - 1=95, 2=Scooter Fuel, 4=Diesel
 * @param CustomerID - CusotmerID
 */
	private void DiscountCalulation(float Payment,float FueAmount,int GasStationID,int FuelID,int CustomerID){
		callbackSale CurrentSale=new callbackSale();
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
		Temp=(CallBack) getCallBackFromBuffer();

		if(Temp instanceof callbackSuccess) // No Discount
		{
			DiscountTextBox.setText(" You Need To Pay: "+myFormatter.format(Price)+" Shekel *No Discount");
		}
		if(Temp instanceof callbackCampaign) // Was Discount
		{
			Temp=(callbackCampaign)Temp;
			Price=((callbackCampaign) Temp).getPriceAfterDiscount();
			DiscountTextBox.setText("<html>You Need To Pay: "+myFormatter.format(Price)+" The Calcultion By "+((callbackCampaign) Temp).getCalculationMethod()+
					" Discount = "+((callbackCampaign) Temp).getDiscountPercentage()+" By "+((callbackCampaign) Temp).getCampaignDescription()+"</html>");
		/*----Send To DB Update*/
		CurrentSale.setPayment(Price);
		CurrentSale.setWhatToDo(MessageType.setNewGasStationSale);
		Server.handleMessageFromClient(CurrentSale);
		Temp=(CallBack) getCallBackFromBuffer();
		if(Temp instanceof callback_Error)
		{
			/*----was Error in sending to DB*/
		}
		if(Temp instanceof callbackSuccess)
		{
			/*---Success transfer to DB*/
		}
		}
		
	
	}
	
	}
//	8565232
