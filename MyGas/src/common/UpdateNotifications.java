package common;

import java.awt.Color;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import GUI.abstractPanel_GUI;
import callback.callbackStringArray;
import client.Client;
import common.Checks;

public class UpdateNotifications implements Runnable {
	private boolean NotificationFlag = true, ButtonBackground=true;
	private boolean NewNotificationFlag = false;
	private JTable NotificationsTable;
	private abstractPanel_GUI GUIScreen;			//To use the callback from buffer function
	private Client Server;
	private JButton NotificationsButton;
	private int UserID;
	private callbackStringArray Notification = new callbackStringArray(MessageType.getNotifications);
	
	public UpdateNotifications(abstractPanel_GUI GUIScreen ,Client Server, int UserID){
		this.GUIScreen = GUIScreen;
		this.Server = Server;
		NotificationsTable = GUIScreen.getNotificationsTable();
		NotificationsButton = GUIScreen.getNotificationsButton();
		this.UserID = UserID;
		Object[] User = new Object[1];
		User[0] = UserID;
		Notification.setVariance(User);
		//GUIScreen.getCallBackFromBuffer();
	}
	
	public void run() {
		while(NotificationFlag){
			Server.handleMessageFromClient(Notification);				
			Notification = (callbackStringArray) GUIScreen.getCallBackFromBuffer();
			setNotificationsTable(Notification.getDefaultTableModel());
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
	public void setNotificationFlag(boolean notificationFlag) {
		NotificationFlag = notificationFlag;
	}
	private void setNotificationsTable(DefaultTableModel NewTableModel){
		NotificationsTable.setModel(NewTableModel);
		//Hide columns
		NotificationsTable.removeColumn(NotificationsTable.getColumnModel().getColumn( 2 ));
		//Checks.resizeColumnWidth(NotificationsTable);
		//TableColumnModel tcm = NotificationsTable.getColumnModel();
		//tcm.getColumn(0).setPreferredWidth(40);
	}
	private void CheckNewNotification(){
		String[][] Table = (String[][]) Notification.getData();
		for(int i=0 ; i<Notification.getRowCount() ; i++){
			if(Table[i][2].equals("No")) NewNotificationFlag = true;
		}
	}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
