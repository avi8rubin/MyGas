package controller;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import GUI.*;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class LoginController implements ActionListener,Observer{
	
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
	 * And next GUI screen for closing (gas station problem)
	 */
	private Login_GUI LoginScreen;
	/**
	 * Buttons from login Gui, use to set handler in controller
	 */
	private JButton LoginButton;
	private JButton ChangePasswordButton;
	/**
	 * The server connection to send queries and receive callback's
	 */
	private static Client Server;
		
	/**
	 * The login user
	 */
	private static callbackUser EnteredUser;
	/**
	 * Set connection Flag
	 */
	String Password;
	
	
//Constructors ****************************************************

	/**
	* Constructs an login controller.
	* @param LoginScreen The login Gui.
	* @param Server The server connection.
	* @param CommonBuffer The buffer to transfer callback's
	*/
	public LoginController(Login_GUI LoginScreen, callbackBuffer CommonBuffer){
		/*------- initialize common variables-------*/
		this.LoginScreen=LoginScreen; 									// Login GUI											// Server connection
		this.CommonBuffer=CommonBuffer;									// CallBack buffer

		
		/*----- Create gui button handlers -----*/
		LoginButton = LoginScreen.getLoginButton(); 					//Login Button on the main GUI
		LoginButton.addActionListener(this);							//Add action listener
		ChangePasswordButton = LoginScreen.getChangePasswordButton();
		ChangePasswordButton.addActionListener(this);					//Add action listener
		
	/**
	 * Set the exit (X) button to performed a organized logout
	 */
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {	  
		        System.exit(0);		        
		    }
		};
		LoginScreen.addWindowListener(exitListener);				
	}
	
/**
 * Login button handler
 */
	private void LoginButtonHandler(){		
		EnteredUser = null;
		if(Server!=null && Server.isConnected()){
			
			/*------ Read fields from gui ------*/
			EnteredUser = new callbackUser(MessageType.getCheckExistsUserPass,LoginScreen.getUserName(),LoginScreen.getPassword());
			Password = LoginScreen.getPassword();
			
			/*------Send user name and password query ------*/
			Server.handleMessageFromClient(EnteredUser);					//Send query to DB				
		}//END if
		else {
			setConnectionToServer();										//Start the connection to server
			LoginButtonHandler();
		}
	}
	
	private void CheckRegisteredUser(CallBack LocalUserCallBack){
			
		/*------ User not exists ------*/
		if (LocalUserCallBack instanceof callback_Error){				//If the query back empty or the entered values not illegal
			LoginScreen.IllegalUserName();	
		}
		/*------ User exists, check the reset parameters ------*/
		else if (LocalUserCallBack instanceof callbackUser){
			EnteredUser = (callbackUser) LocalUserCallBack;				//Casting to callbackUser
			if (Password.equals(EnteredUser.getPassword())){			//Check if Password	correct
				if (EnteredUser.getLoggedIn().equals("Yes")){			//Check if User Already Connected
					LoginScreen.AlreadyConnected();
				}
		/*------ Move to the next screen ------*/
				else{
					if(EnteredUser.getUserTypeId()!=2){
						EnteredUser.setWhatToDo(MessageType.updateUserLogin);
						Server.handleMessageFromClient(EnteredUser);
					}
					LoginScreen.ClearErrorMessage(); 					//Clear the error message if exists
					NextScreenByRole();									// go to the next gui screen by user role
				}
			}
			else LoginScreen.IllegalPassword();							//Display password error message
		}
	}

	/**
	 * Send query to DB that change the user password
	 */
	private void ChangeUserPasswordHandler(){
		if(Server.isConnected()){
			CallBack LocalUserCallBack = null;
			
			/*------Send query ------*/
			EnteredUser.setWhatToDo(MessageType.updateChangeUserPassword);
			EnteredUser.setPassword(LoginScreen.getNewPassword());
			Server.handleMessageFromClient(EnteredUser);
			
			/*------Waiting for callback ------*/
			if (LocalUserCallBack instanceof callback_Error)				//An error has occurred
				LoginScreen.ChangePasswordError();
			else {															//Password change successfully
				EnteredUser.setWhatToDo(MessageType.updateUserLogout);		
				Server.handleMessageFromClient(EnteredUser);				//Update user is logged out, in the DB	
				LoginScreen.ClearErrorMessage();
				LoginScreen.SwitchScreen();
			}
		}
		else {
			LoginScreen.GoToLoginWindow();
			LoginScreen.NoConnectionToServer();

		}
	}

	/**
	 * All buttons function handler
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LoginButton){
			LoginButtonHandler();	
		}	
		else if (e.getSource() == ChangePasswordButton){
			ChangeUserPasswordHandler();
		}
		
	}
	
	/**
	 * Go to the right screen base on the entered user
	 */
	private void NextScreenByRole(){
		
		LoginScreen.setVisible(false);
		
		switch(EnteredUser.getUserTypeId()){
		case 1: 
			new CustomerController(Server, CommonBuffer,
					new CustomerGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		case 2: 
			new StationsController(Server, CommonBuffer,
					new StationsGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		case 3: 
			new StationManagerController(Server, CommonBuffer,
					new StationManagerGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		case 4: 
			new CEOController(Server, CommonBuffer,
					new CEOGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		case 5: 
			new MarketingManagerController(Server, CommonBuffer,
					new MarketingManagerGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		case 6: 
			new MarketingRepresentativeController(Server, CommonBuffer,
					new MarketingRepresentativeGUI(EnteredUser, Server, CommonBuffer, LoginScreen));
			break;
		}
		
	}
		
	/**
	 * Initiate the connection to server 
	 */
	private void setConnectionToServer(){
		/*----- Create Server Connection -----*/
		try {
			Server = new Client (LoginScreen.getServerIP(),DEFAULT_PORT);
			Server.openConnection();	//Try open connection to server
			Server.addObserver(this);
		} catch (IOException e1) {
			LoginScreen.NoConnectionToServer(); 								//Set label on gui
			e1.printStackTrace();
		}													
	}

	@Override
	public void update(Observable o, Object arg) {
		 if (arg instanceof CallBack){
			if(((CallBack)arg).getWhatToDo() == MessageType.getCheckExistsUserPass)
					CheckRegisteredUser((CallBack)arg);	
			if(((CallBack)arg).getWhatToDo() == MessageType.updateUserLogout)
				RestartConnection();		
		 }
	}
	/**
	 * Close the connection and delete observer
	 */
	private void RestartConnection(){
		LoginScreen.ClearFields();
	}
	
}
