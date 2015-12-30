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
import callback.callbackUser;
import callback.callback_Error;
import client.Client;

public class UpdateNotifications implements Runnable,Observer {
	private boolean NotificationFlag = true, ButtonBackground=true;
	private boolean NewNotificationFlag = false;
	private JTable NotificationsTable;
	private Client Server;
	private callbackBuffer CommonBuffer;
	private JButton NotificationsButton;
	private callbackStringArray Notification = new callbackStringArray(MessageType.getNotifications);
	
	public UpdateNotifications(abstractPanel_GUI GUIScreen, callbackBuffer CommonBuffer ,Client Server, int UserID){
		this.Server = Server;
		this.CommonBuffer = CommonBuffer;
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
			//Notification = (callbackStringArray) getCallBackFromBuffer();
		//	setNotificationsTable(Notification.getDefaultTableModel());
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
			e.printStackTrace();
		}
	}
	/**
	 * @return The callback from the buffer
	 */
	private CallBack getCallBackFromBuffer(){
		CallBack ReturnCallback;
		while (CommonBuffer.getHaveNewCallBack() == false); 			//Waits for new callback		
		ReturnCallback = CommonBuffer.getBufferCallBack();				//Get the new callback	
		if (ReturnCallback instanceof callback_Error){					//If the query back empty or the entered values not illegal
			System.out.println(((callback_Error) ReturnCallback).getErrorMassage());	
		}	
		return ReturnCallback; 	
	}

	@Override
	public void update(Observable o, Object arg) {
		switch(((CallBack) arg).getWhatToDo()){
		
			case getNotifications:
				Notification = (callbackStringArray) arg;
				setNotificationsTable(Notification.getDefaultTableModel());
			break;
		}
	}
}
