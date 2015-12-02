package controller;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.*;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackUser;
import callback.callback_Error;
import client.Client;
import client.QuerySender;

public class LoginController implements ActionListener{
	
//Instance variables **********************************************

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
	private final Client Server;
	
	/**
	 * The Query builder
	 */
	private static QuerySender SendQuery = null;
	
	/**
	 * The login user
	 */
	private static callbackUser EnteredUser = null;
	
//Constructors ****************************************************

	/**
	* Constructs an login controller.
	* @param LoginScreen The login Gui.
	* @param Server The server connection.
	* @param CommonBuffer The buffer to transfer callback's
	*/
	public LoginController(Login_GUI LoginScreen, Client Server, callbackBuffer CommonBuffer){
		
		this.LoginScreen=LoginScreen; 						// Login GUI
		this.Server=Server;									// Server connection
		this.CommonBuffer=CommonBuffer;						// CallBack buffer
		SendQuery = new QuerySender(Server);				// Query sender
			
		LoginButton = LoginScreen.getLoginButton(); 		//Login Button on the main GUI
		LoginButton.addActionListener(this);				//Add action listener
		ChangePasswordButton = LoginScreen.getChangePasswordButton();
		ChangePasswordButton.addActionListener(this);		//Add action listener	
	}
	
/**
 * Login button handler
 */
	private void LoginButtonHandler(){
		String UserName = LoginScreen.getUserName();
		String Password = LoginScreen.getPassword();
		CallBack LocalUserCallBack = null;
		
		SendQuery.getCheckExistsUserPass(UserName, Password); 	//Send query to DB

		
		LocalUserCallBack = getCallBackFromBuffer();			//Get from the common buffer new callback
		EnteredUser = (callbackUser) LocalUserCallBack;	
		
		if (LocalUserCallBack instanceof callback_Error){		//If the query back empty or the entered values not illegal
			LoginScreen.IllegalUserName();	
		}
		else if (Password.equals(EnteredUser.getPassword())){	//Password & username correct	
			if (EnteredUser.getLoggedIn().equals("Yes")){		//User Already Connected
				LoginScreen.AlreadyConnected();
			}
			else{
				LoginScreen.setWelcomUserLabel(EnteredUser.getFirstName(), EnteredUser.getLastName());
				LoginScreen.SwitchScreen();
			}
		}
		else LoginScreen.IllegalPassword();	
	}
	
	private void ChangeUserPasswordHandler(){
		LoginScreen.SwitchScreen();
	}
	
	private CallBack getCallBackFromBuffer(){
		callbackUser ReturnCallBack;
		while (CommonBuffer.HasNewCallBackAndBufferFree() == false); 		//Waits for new callback
		ReturnCallBack = (callbackUser) CommonBuffer.getBufferCallBack();	//Get the new callback
		return (ReturnCallBack);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LoginButton){
			LoginButtonHandler();	
		}	
		else if (e.getSource() == ChangePasswordButton){
			ChangeUserPasswordHandler();
		}
		
	}
	
}
