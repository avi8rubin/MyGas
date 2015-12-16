package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import GUI.Login_GUI;
import GUI.StationsGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class StationsController extends Controller implements MouseListener {

		private callbackBuffer CommonBuffer = null;
		private static callbackUser EnteredUser;
		
		private JButton LoginButton;
		private JButton UserLogoutButton;
		private JButton MainLogoutButton;
		
		private JLabel BlueHand;
		private JLabel GreenHand;
		private JLabel RedHand;
		private StationsGUI StationUserLoginGui;
		
		private CardLayout ContainerCardLeft;
		private CardLayout ContainerCardCenter;
		
		private boolean ConnectionFlag = false;
		private boolean logoutflag;
		
		public StationsController(Client Server, callbackBuffer CommonBuffer, StationsGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.StationUserLoginGui = GuiScreen;
		StationUserLoginGui.setVisible(true);
		
		/*----- Create gui button handlers -----*/
		MainLogoutButton=new JButton();
		MainLogoutButton=GuiScreen.getMainLogoutButton();
		
		/*-----Hand gui Icons-------*/
		BlueHand=GuiScreen.getBlueHand();
		GreenHand=GuiScreen.getGreenHand();
		RedHand=GuiScreen.getRedHand();
		/*
 		JLabel BlueHand = new JLabel("");
		BlueHand.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				BlueHand.setBounds(getMousePosition().x-350, getMousePosition().y-300,90, 138);
			}
		});
		 */

		
		
		UserLogoutButton=GuiScreen.getUserLogoutButton();
		UserLogoutButton.addActionListener(this);
		
		
		LoginButton = GuiScreen.getLoginButton(); 					//Login Button on the main GUI
		LoginButton.addActionListener(this);							//Add action listener
		
		ContainerCardLeft=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCardCenter=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft.show(LeftCardContainer,"EmptyLeftPanel");
		ContainerCardCenter.show(CenterCardContainer,"StationUserLoginLayer");
		
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
			LocalUserCallBack = getCallBackFromBuffer();					//Get from the common buffer new callback
				
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
					}
				}
				else StationUserLoginGui.IllegalPassword();							//Display password error message
			}
	}
	/**
	 * This function switch left window
	 */
	private void GasStationSwitchLeftPanel(){
	ContainerCardLeft.show(LeftCardContainer, "left_panel");
	ContainerCardCenter.show(CenterCardContainer,"GasFuelingCenterPanel");
	}
	/**
	 * This function handel logout window
	 */
	private void LogoutButtonHandler(){
		if(logoutflag){
			logoutflag=false;
    		//EnteredUser.setWhatToDo(MessageType.updateUserLogout);
    		//Server.handleMessageFromClient(EnteredUser);
			ContainerCardLeft.show(LeftCardContainer,"EmptyLeftPanel");
			ContainerCardCenter.show(CenterCardContainer,"StationUserLoginLayer");
			StationUserLoginGui.setlogoutvisable(false);
			MainLogoutButton.setEnabled(true);
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

