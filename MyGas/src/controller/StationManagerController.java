package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import GUI.CEOGUI;
import GUI.StationManagerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStationOrders;
import callback.callbackStringArray;
import callback.callbackUser;
import callback.callbackVector;
import callback.callbackWaitingTariff;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class StationManagerController extends Controller{

	private static callbackUser EnteredUser=null;
	private static CallBack Temp;
	private StationManagerGUI GuiScreen;
	private int GasStationID; // what gas staion this maganger is manage
	//Top screen
	private final JButton CreateReportButton;
	private final JButton ApproveSupplyButton;
	private final JButton UpdateLimitButton;
	//Approve
	private final JButton UpdateApproveButton;
	/**
	 * Constracor
	 * @param Server
	 * @param CommonBuffer
	 * @param GuiScreen
	 */
	public StationManagerController(Client Server, callbackBuffer CommonBuffer, StationManagerGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		/*-----------------get Table from DB By user ID----------------------*/
		EnteredUser = new callbackUser();
		EnteredUser.setUserID(GuiScreen.getUserID());
		
		/*------------------Initialized Buttons Actions ----------- */
		CreateReportButton = GuiScreen.getCreateReportButton();
		CreateReportButton.addActionListener(this);
		CreateReportButton.setActionCommand("CreateReportButton");
		
		ApproveSupplyButton = GuiScreen.getApproveSupplyButton();
		ApproveSupplyButton.addActionListener(this);
		ApproveSupplyButton.setActionCommand("ApproveSupplyButton");
		
		UpdateLimitButton = GuiScreen.getUpdateLimitButton();
		UpdateLimitButton.addActionListener(this);
		UpdateLimitButton.setActionCommand("UpdateLimitButton");
	
		/*--------------------"Update"----------------------*/
		UpdateApproveButton = GuiScreen.getUpdateApproveButton();
		UpdateApproveButton.addActionListener(this);
		UpdateApproveButton.setActionCommand("UpdateApproveButton");
		
		
		StationManagerFirstLoginScreen();
	}
	/**
	 * What to do if press Button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ApproveSupplyButton"))
			handleApproveSupplyButtonButton();	
		if(e.getActionCommand().equals("UpdateApproveButton"))
			handleUpdateOnApproveScreen();
		if(e.getActionCommand().equals("UpdateLimitButton"))
			handleUpdateLimitLevelScreen();
	}
	/**
	 * This function reset the main Manger window
	 */
	private void StationManagerFirstLoginScreen(){
		
		// Display Empty Station panel
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"EmptyCenterPanel");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
		GuiScreen.ResetErrorLabel();
		GuiScreen.ResetUpdateMessage();
		
		
	}
	/**
	 * Show Approve screen and get table
	 */
	private void handleApproveSupplyButtonButton()
	{
		/*----------------get table info and display it-----------*/
		if(setTableInApproveSuppliesOrders())
		{
		/*----------------Show Approve Supplies screen------------*/
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"ApproveSupplyLayer");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
		}
		else
			GuiScreen.ErrorEnterApproval();

	}
	/**
	 * This function update supplies in DB & Update Table
	 */
	private void handleUpdateOnApproveScreen()
	{
		/*-------------Read from Values from Table----*/
		int i,RowNum;
		JTable ApproveTable;
		callbackVector UpdateApproveTable = new callbackVector(MessageType.setStationSuppliesOrder);
		ApproveTable = GuiScreen.getApproveTable();
		DefaultTableModel CurrentTableModel = (DefaultTableModel) ApproveTable.getModel();
		RowNum = CurrentTableModel.getRowCount();
		for(i=0 ; i < RowNum ; i++){
			if(!CurrentTableModel.getValueAt(i, 7).equals("Waiting")){
				
				/*--------------------Create new order & enter to callback vector-----------------------*/
				callbackStationOrders NewOrder = new callbackStationOrders();
				NewOrder.setOrderID(Integer.valueOf((String)CurrentTableModel.getValueAt(i, 0))); 		//OrderID
				NewOrder.setGasStationID(Integer.valueOf((String)CurrentTableModel.getValueAt(i, 1)));  //GasStationID
				NewOrder.setFuelID(Integer.valueOf((String)CurrentTableModel.getValueAt(i, 2)));
				NewOrder.setAmount(Float.valueOf((String)CurrentTableModel.getValueAt(i, 4)));
				NewOrder.setConfirmation((String)CurrentTableModel.getValueAt(i,7));			
				UpdateApproveTable.add(NewOrder);
			
			}
		}
		/*Just is there was a change update DB---*/
		if(UpdateApproveTable.size()>0){
			Server.handleMessageFromClient(UpdateApproveTable);
			getCallBackFromBuffer();
			handleApproveSupplyButtonButton();
		}
		else {
			UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));
				JOptionPane.showMessageDialog(GuiScreen,	
					"There are no changes in the existing records.",
						"Error Message",
			    			JOptionPane.ERROR_MESSAGE);		
		}
	}
	
	/**
	 * This function add value to table
	 */
	private boolean setTableInApproveSuppliesOrders()
	{

		callbackStringArray LocalUserID=new callbackStringArray(MessageType.getStationSuppliesOrder);
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		LocalUserID.setVariance(data);
		LocalUserID.setWhatToDo(MessageType.getStationSuppliesOrder);
		Server.handleMessageFromClient(LocalUserID);
		Temp=(CallBack) getCallBackFromBuffer();
		if(Temp instanceof callback_Error)
			GuiScreen.ErrorEnterApproval();
		/*-----------------Set Table values in Approval Table------------------*/
		if(Temp instanceof callbackStringArray)
			{		
		GuiScreen.setApprovalTable(((callbackStringArray) Temp).getDefaultTableModel());
		return true;
			}
		return false;
	}
	/**
	 * This function show screen when "Update limit pressed"
	 */
	private void handleUpdateLimitLevelScreen(){
		if(setTableInUpdateLimitFuel())
		{
		/*----------------Show 'Update Limit Level for fuel' screen------------*/
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"UpdateLimitLevelFuelLayer");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
		}
		else
			GuiScreen.ErrorEnterUpdateLimitLevel();
		
	}
	/**
	 * This function set value in table by DB
	 * @return if successes true
	 */
	private boolean setTableInUpdateLimitFuel()
	{
		callbackStringArray LocalUserID=new callbackStringArray(MessageType.getCurrentThresholdLimit);
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		LocalUserID.setVariance(data);
		LocalUserID.setWhatToDo(MessageType.getCurrentThresholdLimit);
		Server.handleMessageFromClient(LocalUserID);
		Temp=(CallBack) getCallBackFromBuffer();
		if(Temp instanceof callback_Error)
			GuiScreen.ErrorEnterUpdateLimitLevel();
		/*-----------------Set Table values in Approval Table------------------*/
		if(Temp instanceof callbackStringArray)
			{		
		GuiScreen.setUpdatelimitLevelTable(((callbackStringArray) Temp).getDefaultTableModel());
		return true;
			}
		return false;
	}
}
