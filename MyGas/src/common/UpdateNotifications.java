/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package common;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import GUI.abstractPanel_GUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStringArray;
import client.Client;
/**
 * Thread that get the notification for users screen
 * @author Ohad
 */
public class UpdateNotifications implements Runnable,Observer {
	private boolean NotificationFlag = true, ButtonBackground=true;
	private boolean NewNotificationFlag = false;
	private JTable NotificationsTable;
	private Client Server;
	private JButton NotificationsButton;
	private callbackStringArray Notification = new callbackStringArray(MessageType.getNotifications);
	
	public UpdateNotifications(abstractPanel_GUI GUIScreen ,Client Server, int UserID){
		this.Server = Server;
		Server.addObserver(this);
		NotificationsTable = GUIScreen.getNotificationsTable();
		NotificationsButton = GUIScreen.getNotificationsButton();
		Object[] User = new Object[1];
		User[0] = UserID;
		Notification.setVariance(User);
	}
	
	public void run() {
		while(NotificationFlag){
			Server.handleMessageFromClient(Notification);				
			CheckNewNotification();
			while(NewNotificationFlag){
				ChangeNotificationBackground();
				Sleep(1000);
			}
			Sleep(30000);
		}
		
	}
	public void StopNewNotification(){
		if(ButtonBackground)
			ChangeNotificationBackground();
			NewNotificationFlag=false;

	}
	public void StartThread(){
		new Thread(this);
	}
	public boolean isNotificationFlag() {
		return NotificationFlag;
	}
	/**
	 * User logout, stop thread and delete observer
	 * @param notificationFlag
	 */
	public void setNotificationFlag(boolean notificationFlag) {
		NotificationFlag = notificationFlag;
		Server.deleteObserver(this);
	}
	/**
	 * Update the notification table
	 * @param NewTableModel
	 */
	private void setNotificationsTable(DefaultTableModel NewTableModel){
		NotificationsTable.setModel(NewTableModel);
		NotificationsTable.removeColumn(NotificationsTable.getColumnModel().getColumn( 2 ));

	}
	/**
	 * Check if the there ant new notification.
	 */
	private void CheckNewNotification(){
		String[][] Table = (String[][]) Notification.getData();
		for(int i=0 ; i<Notification.getRowCount() ; i++){
			if(Table[i][2].equals("No")) NewNotificationFlag = true;
		}
	}
	/**
	 * When new notification create the notification button will change colors
	 */
	private void ChangeNotificationBackground(){
		ButtonBackground =! ButtonBackground;
		if(ButtonBackground)
			NotificationsButton.setBackground(new Color(220, 20, 60));
		else NotificationsButton.setBackground(new JButton().getBackground());
		
	}
	private void Sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){
			switch(((CallBack) arg).getWhatToDo()){			
				case getNotifications:
					Notification = (callbackStringArray) arg;
					setNotificationsTable(Notification.getDefaultTableModel());
				break;
				case updateUserLogout:
					Server.deleteObserver(this);
					break;
			}
		}
	}
}
