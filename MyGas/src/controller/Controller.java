package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import GUI.Login_GUI;
import GUI.abstractPanel_GUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callback_Error;
import client.Client;
import common.Checks;

public abstract class Controller implements ActionListener{
	/**
	 * The server connection to send queries and receive callback's
	 */
	protected static Client Server;
	/**
	 * This buffer will allows the transfer of the callback, back to the application
	 */
	private callbackBuffer CommonBuffer = null;
	/**
	 * GUI Panels
	 */
	protected JPanel CenterCardContainer;
	protected JPanel LeftCardContainer;
	protected CardLayout ContainerCard;
	/**
	 * All the required checks we need to do in the controllers
	 */
	protected Checks checks = new Checks();
	/**
	 * The abstract controller constructor.
	 * @param Server - The client connection to server.
	 * @param CommonBuffer -  The callback buffer, where the query answer returns.
	 */
	
	public Controller(Client Server, callbackBuffer CommonBuffer){
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
	}
	public Controller(Client Server, callbackBuffer CommonBuffer,abstractPanel_GUI GUIScreen){
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
		CenterCardContainer = GUIScreen.getCenterCardContainer();
		LeftCardContainer = GUIScreen.getLeftCardContainer();
	}
	
	
	/**
	 * @return The callback from the buffer
	 */
	protected Object getCallBackFromBuffer(){
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
