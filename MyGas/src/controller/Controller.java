package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;

import GUI.Login_GUI;
import GUI.abstractPanel_GUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callbackStringArray;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.Checks;

public abstract class Controller implements ActionListener,Observer{
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
	abstractPanel_GUI GUIScreen;
	
	protected JPanel CenterCardContainer;
	protected JPanel LeftCardContainer;
	protected CardLayout ContainerCard;
	private JTable ContactTable;
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
		Server.addObserver(this);
	}
	public Controller(Client Server, callbackBuffer CommonBuffer,abstractPanel_GUI GUIScreen){
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
		this.GUIScreen = GUIScreen;
		Server.addObserver(this);
		CenterCardContainer = GUIScreen.getCenterCardContainer();
		LeftCardContainer = GUIScreen.getLeftCardContainer();
		ContactTable = GUIScreen.getContactTable();
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
	
	/**
	 * @return The vector from the buffer
	 */
	protected Object getCallBackVectorFromBuffer(){
		Object ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBackVector();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){				//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		if (ReturnCallback instanceof callbackLostConnection){
			Login_GUI frame = new Login_GUI();
			frame.GoToLoginWindow();
			frame.NoConnectionToServer();
			new LoginController(frame,CommonBuffer);
		}
		if (ReturnCallback instanceof callbackVector){
			return (callbackVector) ReturnCallback;
		}
		if (ReturnCallback instanceof Vector){
			return (Vector<?>) ReturnCallback;
		}
		return (callbackVector) ReturnCallback; 	
	}
	
	public abstract void actionPerformed(ActionEvent e);				//Buttons handlers

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof CallBack){	
			switch(((CallBack) arg).getWhatToDo()){
				case getContacts:
					callbackStringArray ContactList = (callbackStringArray) arg;
					ContactTable.setModel(ContactList.getDefaultTableModel());			
					break;
			default:
				GUIScreen.getNotificationThrerad().update(o, arg);
				break;
			}		
		}	
	}
}
