package controller;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import GUI.*;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class LoginController implements ActionListener{
	
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
	private boolean ConnectionFlag = false;
	
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
		    	if(ConnectionFlag) {
		    		EnteredUser.setWhatToDo(MessageType.updateUserLogout);
		    		Server.handleMessageFromClient(EnteredUser);
		    	}
		        System.exit(0);		        
		    }
		};
		LoginScreen.addWindowListener(exitListener);			
		
	}
	
/**
 * Login button handler
 */
	private void LoginButtonHandler(){
		CallBack LocalUserCallBack = null;
		EnteredUser = null;
		setConnectionToServer();										//Start the connection to server

		if(ConnectionFlag){
			
			/*------ Read fields from gui ------*/
			EnteredUser = new callbackUser(MessageType.getCheckExistsUserPass,LoginScreen.getUserName(),LoginScreen.getPassword());
			String Password = LoginScreen.getPassword();
			
			/*------Send user name and password query ------*/
			Server.handleMessageFromClient(EnteredUser);					//Send query to DB			
	
			/*------Waiting for callback ------*/
			LocalUserCallBack = getCallBackFromBuffer();					//Get from the common buffer new callback
				
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
						EnteredUser.setWhatToDo(MessageType.updateUserLogin);
						Server.handleMessageFromClient(EnteredUser);		//Update user is logged in, in the DB	
						getCallBackFromBuffer();							//Emptying buffer		
						LoginScreen.ClearErrorMessage(); 					//Clear the error message if exists
						NextScreenByRole();									// go to the next gui screen by user role
						
						
						//LoginScreen.setWelcomUserLabel(EnteredUser.getFirstName(), EnteredUser.getLastName());
						//LoginScreen.SwitchScreen();
					}
				}
				else LoginScreen.IllegalPassword();							//Display password error message
			}
		}//END if
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
			LocalUserCallBack = getCallBackFromBuffer();					//Get from the common buffer new callback
			if (LocalUserCallBack instanceof callback_Error)				//An error has occurred
				LoginScreen.ChangePasswordError();
			else {															//Password change successfully
				EnteredUser.setWhatToDo(MessageType.updateUserLogout);		
				Server.handleMessageFromClient(EnteredUser);				//Update user is logged out, in the DB	
				getCallBackFromBuffer();									//Emptying buffer
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
	 * @return The callback from the buffer
	 */
	private CallBack getCallBackFromBuffer(){
		CallBack ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBack();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){				//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		if (ReturnCallback instanceof callbackLostConnection){
			LoginScreen.GoToLoginWindow();
			LoginScreen.NoConnectionToServer();
		}
		return ReturnCallback; 						
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
		if (!ConnectionFlag){	
			/*----- Create Server Connection -----*/
			try {
				Server = new Client (LoginScreen.getServerIP(),DEFAULT_PORT,CommonBuffer);
				Server.openConnection();											//Try open connection to server
				ConnectionFlag = true;												//Connection already set
			} catch (IOException e1) {
				LoginScreen.NoConnectionToServer(); 								//Set label on gui
				ConnectionFlag = false;												//Can't succeed to make a connection
				e1.printStackTrace();
			}													
		}
	}
}
