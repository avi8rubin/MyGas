package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import GUI.StationsGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStationFuels;
import callback.callbackUser;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class StationsController extends Controller implements MouseListener,Runnable{
		private callbackBuffer CommonBuffer = null;
		private callbackStationFuels CurrentStation=null;
		
		private float Liter=0,Price=0,Fuel_95_Price,Fuel_Scoter_Price,Fuel_Diesel_Price;
		
		private JButton LoginButton;
		private JButton UserLogoutButton;
		private JButton MainLogoutButton;
		private JButton StartFuelingButton;
		private JButton Paybutton;
		
		private static callbackUser EnteredUser;
		private static callbackUser StationCurrentUser;				// Current user details
		private static callbackStationFuels StationCurrentFuels;
		private static callbackVector FuelsInStation;
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
		private boolean Fuel_95=false;  //RED
		private boolean Fuel_Scoter=false; //Green
		private boolean Fuel_Diesel=false; //Blue
		private boolean Fuel95IsExist,FuelScoterIsExist,FuelDieselIsExist;

		
		/**
		 * Constratcor
		 * @param Server
		 * @param CommonBuffer
		 * @param GuiScreen
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
		
		//ContainerCard2=(CardLayout)(CenterCardContainer.getLayout());
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
		//ContainerCard1.show(LeftCardContainer,"EmptyLeftPanel");
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
		CallBack LocalUserCallBack = null;
		EnteredUser = null;
		
			/*------ Read fields from gui ------*/
			EnteredUser = new callbackUser(MessageType.getCheckExistsUserPass,StationUserLoginGui.getUserName(),StationUserLoginGui.getPassword());
			String Password = StationUserLoginGui.getPassword();
			
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
				if (Password.equals(EnteredUser.getPassword())){			//Check if Password	correct
					if (EnteredUser.getLoggedIn().equals("Station")){			//Check if User Already Connected
						StationUserLoginGui.IllegalUserName();
					}
			/*------ Move to the next screen ------*/
					else{
						EnteredUser.setWhatToDo(MessageType.updateUserLogin);
						Server.handleMessageFromClient(EnteredUser);	//Update user is logged in, in the DB	
						getCallBackFromBuffer();						//Emptying buffer		
						StationUserLoginGui.ClearErrorMessage(); 		//Clear the error message if exists
						GasStationSwitchLeftPanel();					// go to the next gui screen by user role
						logoutflag=true;								//logout will return to this screen
						MainLogoutButton.setEnabled(false);
						StationUserLoginGui.setlogoutvisable(true);
						UpdateStationInfo();
					}
				}
				else StationUserLoginGui.IllegalPassword();							//Display password error message
			}
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
	 * This function handel logout window for User
	 */
	private void LogoutButtonHandler(){
		if(logoutflag){ // if User can logout
			logoutflag=false;
			/* restore to Enter to Station Login*/
			ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
			ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
			ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
			ContainerCard.show(CenterCardContainer,"StationUserLoginLayer");
			StationUserLoginGui.setlogoutvisable(false);
			MainLogoutButton.setEnabled(true); // Enable to logout from station
			EnteredUser.setWhatToDo(MessageType.updateUserLogout);
			Server.handleMessageFromClient(EnteredUser);
			getCallBackFromBuffer();									//Clean buffer
			
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent()==BlueHand){
			if(!UserIsFueling){  //if user is not fuling
				UserIsFueling=true;
				Fuel_Diesel=true;
				this.Liter=0;
				StartFuelingButton.setEnabled(true);
				StationUserLoginGui.BluePumpShow();
			}
		}
		if(e.getComponent()==BlueHandFlip){
			if(!UserNeedToPay){
				StationUserLoginGui.BluePumpNotShow(); //return the hand to the right palce
				StartFuelingButton.setEnabled(false);
				UserIsFueling=false;
			}
		}
		if(e.getComponent()==RedHand){
			if(!UserIsFueling){  //if user is not fuling
				UserIsFueling=true;
				Fuel_95=true;
				this.Liter=0;
				StartFuelingButton.setEnabled(true);
				StationUserLoginGui.RedPumpShow();
			}
		}
		if(e.getComponent()==RedHandFlip){
			if(!UserNeedToPay){
				StationUserLoginGui.RedPumpNotShow();//return the hand to the right palce
				StartFuelingButton.setEnabled(false);
				UserIsFueling=false;
			}
		}
		if(e.getComponent()==GreenHand){
			if(!UserIsFueling){  //if user is not fuling
				UserIsFueling=true;
				Fuel_Scoter=true;
				this.Liter=0;
				StartFuelingButton.setEnabled(true);
				StationUserLoginGui.GreenPumpShow();
			}
		}
		if(e.getComponent()==GreenHandFlip){
			if(!UserNeedToPay){
				StationUserLoginGui.GreenPumpNotShow();//return the hand to the right palce
				StartFuelingButton.setEnabled(false);
				UserIsFueling=false;
			}
		}
		
	}

	public void FuelingProccess(){
		//User starting to Pumping
		if(PressStartStopButtonFlag==true && UserIsFueling){ //start new fueling
			StationUserLoginGui.getUserLogoutButton().setEnabled(false);
			PressStartStopButtonFlag=false;
			StartFuelingButton.setText("Stop");
			Paybutton.setEnabled(false);
			UserNeedToPay=true;
			
			
			// start counting liters
			LiterCounter.start();
			
			
		}
		//User Stop the Pumping
		else{ 
				LiterCounter.stop();
				LiterCounter=new Thread(this);
			PressStartStopButtonFlag=true;
			Paybutton.setEnabled(true);
			StartFuelingButton.setText("Start Fueling");
			DiscountTextBox.setText(" You Need to Pay: "+Liter*10+" Shekel");
			DiscountTextBox.setVisible(true);
		}
		
		
	}
	public void PayProccess(){
		if(UserNeedToPay==true){
			UserNeedToPay=false;
			Paybutton.setEnabled(false);
			UserIsFueling=false;
			Liter=0;
			LiterLabel.setText(String.valueOf(Liter));
			StationUserLoginGui.getUserLogoutButton().setEnabled(true);
			StartFuelingButton.setEnabled(false);
			DiscountTextBox.setText("");
			DiscountTextBox.setVisible(true);
			//reset all Hand back to the station
			if(Fuel_95){
				StationUserLoginGui.RedPumpNotShow();//return the hand to the right palce
				UserIsFueling=false;
			}
			if(Fuel_Scoter){
				StationUserLoginGui.GreenPumpNotShow();//return the hand to the right palce
				UserIsFueling=false;
			}
			if(Fuel_Diesel){
				StationUserLoginGui.BluePumpNotShow(); //return the hand to the right palce
				UserIsFueling=false;
			}
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
	public void run(){
		while(true){
			try{
				this.Liter+=0.13;
				
				Liter = Float.valueOf(myFormatter.format(Liter));
				//System.out.println(this.Liter+=0.1);
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
		/*-----send Station ID-------*/

		StationCurrentFuels = new callbackStationFuels(MessageType.getFuelPerStation); //create new CallbackStationFuels
		StationCurrentFuels.setGasStationID(StationCurrentUser.getUserID()); //Enter Station UserID
		Server.handleMessageFromClient(StationCurrentFuels);	//send to DB
			
		/*------get callback Fuels ------*/
		FuelsInStation=(callbackVector)getCallBackVectorFromBuffer();					//Get from the common buffer new callback
		StationCurrentFuels=(callbackStationFuels)FuelsInStation.get(0);
		System.out.println(StationCurrentFuels.getFuelID());
		StationCurrentFuels=(callbackStationFuels)FuelsInStation.get(1);
		System.out.println(StationCurrentFuels.getFuelID());
		StationCurrentFuels=(callbackStationFuels)FuelsInStation.get(2);
		System.out.println(StationCurrentFuels.getFuelID());
		/*------Empty buffer ------*/
		//getCallBackFromBuffer();//Emptying buffer
		
		
		
	}
	
}

