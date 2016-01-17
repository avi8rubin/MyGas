/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
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
	 */
	
	public Controller(Client Server){
		this.Server = Server;
		Server.addObserver(this);
	}
	public Controller(Client Server,abstractPanel_GUI GUIScreen){
		this.Server = Server;
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
