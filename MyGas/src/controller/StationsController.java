package controller;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;

import GUI.CEOGUI;
import GUI.Login_GUI;
import GUI.StationsGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class StationsController extends Controller {

	
	//Instance variables **********************************************
		/**
		 * The default port to connect on.
		 */
		 final public static int DEFAULT_PORT = 5555;
		/**
		 * This buffer will allows the transfer of the callback, back to the application
		 */
		private callbackBuffer CommonBuffer = null;
		/**
		 * The login GUI screen
		 */
		private Login_GUI LoginScreen;
		
		/**
		 * Buttons from login Gui, use to set handler in controller
		 */
		private JButton LoginButton;
		/**
		 * The server connection to send queries and receive callback's
		 */
			
		/**
		 * The login user
		 */
		private static callbackUser EnteredUser;
		/**
		 * Set connection Flag
		 */
		
		private StationsGUI StationUserLoginGui;
		
		private boolean ConnectionFlag = false;
		
		
	public StationsController(Client Server, callbackBuffer CommonBuffer, StationsGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.StationUserLoginGui = GuiScreen;
		StationUserLoginGui.setVisible(true);
		
		
		/*----- Create gui button handlers -----*/
		LoginButton = GuiScreen.getLoginButton(); 					//Login Button on the main GUI
		LoginButton.addActionListener(this);							//Add action listener
		
			
		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==LoginButton){
			LoginButtonHandler();
		}
	}
		/**
		 * Login button handler
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
						Server.handleMessageFromClient(EnteredUser);		//Update user is logged in, in the DB	
						getCallBackFromBuffer();							//Emptying buffer		
						StationUserLoginGui.ClearErrorMessage(); 					//Clear the error message if exists
						GasStationFueling();									// go to the next gui screen by user role
						
						
						//LoginScreen.setWelcomUserLabel(EnteredUser.getFirstName(), EnteredUser.getLastName());
						//LoginScreen.SwitchScreen();
					}
				}
				else StationUserLoginGui.IllegalPassword();							//Display password error message
			}
		}//END if
	/**
	 * This function replace gas station screen to Fueling screen
	 */
	private void GasStationFueling(){
	}
}

