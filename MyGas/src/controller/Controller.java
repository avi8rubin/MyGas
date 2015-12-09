package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Login_GUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callback_Error;
import client.Client;

public abstract class Controller implements ActionListener{
	/**
	 * The server connection to send queries and receive callback's
	 */
	protected static Client Server;
	/**
	 * This buffer will allows the transfer of the callback, back to the application
	 */
	private callbackBuffer CommonBuffer = null;
	
	public Controller(Client Server, callbackBuffer CommonBuffer){
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
	}
	
	
	
	/**
	 * @return The callback from the buffer
	 */
	protected CallBack getCallBackFromBuffer(){
		CallBack ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBack();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){				//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		if (ReturnCallback instanceof callbackLostConnection){
			Login_GUI frame = new Login_GUI();
			frame.GoToLoginWindow();
			frame.NoConnectionToServer();
			new LoginController(frame,CommonBuffer);
		}
		return ReturnCallback; 	
	}
	
	public abstract void actionPerformed(ActionEvent e);				//Buttons handlers
	
}
