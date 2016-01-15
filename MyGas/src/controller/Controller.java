package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTable;

import GUI.abstractPanel_GUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackLostConnection;
import callback.callbackStringArray;
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
	
	
	public abstract void actionPerformed(ActionEvent e);				//Buttons handlers

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof callbackLostConnection){ 	//Connection was lost
			GUIScreen.ShowLostConnection();			//Show on GUI connection lost message
			Server.deleteObserver(this);			//Delete controller Observer
			Server.deleteObserver(GUIScreen.getNotificationThread());	//Delete Notification Observer
			GUIScreen.getNotificationThread().setNotificationFlag(false); // Stop Notification Thread
		}
		
		if(arg instanceof CallBack){	
			switch(((CallBack) arg).getWhatToDo()){
				case getContacts:
					callbackStringArray ContactList = (callbackStringArray) arg;
					ContactTable.setModel(ContactList.getDefaultTableModel());			
					break;
				case updateUserLogout:
					Server.deleteObserver(this);
					break;
					
			default:
				GUIScreen.getNotificationThread().update(o, arg);
				break;
			}		
		}	
	}
}
