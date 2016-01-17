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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import GUI.StationManagerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStationFuels;
import callback.callbackStationOrders;
import callback.callbackStringArray;
import callback.callbackUser;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.MessageType;


public class StationManagerController extends Controller{

	//Global values
	private static callbackUser EnteredUser=null;
	private static CallBack GasStationFuelLevel;
	private StationManagerGUI GuiScreen;
	private int GasStation;
	
	//Top screen For Station Manager
	private final JButton CreateReportButton;
	private final JButton ApproveSupplyButton;
	private final JButton UpdateLimitButton;
	
	//Approve Supply Screen
	private final JButton UpdateApproveButton;
	
	//Update Level Limit Screen
	private final JButton UpdateLimitLevelButton;
	
	// Create Report Screen
	private final JButton QuartelyRepotyButton;
	private final JButton PurchaseReportButton;
	private final JButton StockReportButton;
	
	//Stock Report Screen
	Float [] FloatArray;
	private boolean StockFlag=false;
	
	//Quarterly Report Screen
	callbackStringArray  QuartlyReport;
	private final JButton GenerateInQuartlyReport;
	
	//Purchase Report Screen
	callbackStringArray PurchaseReport;
	private final JButton GeneratetInPurchaseReport;
	
	/*---------------------------------------------------------------------*/
	/*-----------------------Global Functions------------------------------*/
	/*---------------------------------------------------------------------*/
	/**
	 * Contractor
	 * @param Server - Connection to server
	 * @param CommonBuffer - Not in use
	 * @param GuiScreen - The gui screen use in some methods
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
		
		QuartelyRepotyButton= GuiScreen.getQuartelyRepotyButton();
		QuartelyRepotyButton.addActionListener(this);
		QuartelyRepotyButton.setActionCommand("QuartelyRepotyButton");
		
		PurchaseReportButton=GuiScreen.getPurchaseReportButton();
		PurchaseReportButton.addActionListener(this);
		PurchaseReportButton.setActionCommand("PurchaseReportButton");
		
		StockReportButton = GuiScreen.getStockReportButton();
		StockReportButton.addActionListener(this);
		StockReportButton.setActionCommand("StockReportButton");
		
		/*--------------------"Update Order"----------------------*/
		UpdateApproveButton = GuiScreen.getUpdateApproveButton();
		UpdateApproveButton.addActionListener(this);
		UpdateApproveButton.setActionCommand("UpdateApproveButton");
		
		/*--------------------"Update Limit-----------------*/
		UpdateLimitLevelButton = GuiScreen.getUpdateLimitLevelButton();
		UpdateLimitLevelButton.addActionListener(this);
		UpdateLimitLevelButton.setActionCommand("UpdateLimitLevelButton");
		
		/*-------------------"Generate in Quarterly Report---------- */
		GenerateInQuartlyReport=GuiScreen.getQuartelyGenrateButton();
		GenerateInQuartlyReport.addActionListener(this);
		GenerateInQuartlyReport.setActionCommand("GenerateInQuartlyReport");
		
		/*---------------------Generatet In Purchase Report---------*/
		GeneratetInPurchaseReport=GuiScreen.getPurchaseGenrateButton();
		GeneratetInPurchaseReport.addActionListener(this);
		GeneratetInPurchaseReport.setActionCommand("GeneratetInPurchaseReport");
		StationManagerFirstLoginScreen();
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
		 // Reset//
		GuiScreen.ResetErrorLabel();
		GuiScreen.ResetUpdateMessage();
		GuiScreen.ResetUpdateLimitTextError();
		
	}
	/**
	 *  * What to do if press Button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*---------------------Approve Supply----------------------*/
		if(e.getActionCommand().equals("ApproveSupplyButton"))
			handleApproveSupplyButtonButton();	
		if(e.getActionCommand().equals("UpdateApproveButton"))
			handleUpdateOnApproveScreen();
		/*---------------------Update Limit Level------------------*/
		if(e.getActionCommand().equals("UpdateLimitButton"))
			handleUpdateLimitLevelScreen();
		if(e.getActionCommand().equals("UpdateLimitLevelButton"))
			handleUpdateLimitLevelButton();
		/*---------------------Create Report-----------------------*/
		if(e.getActionCommand().equals("CreateReportButton"))
			handleCreateReportButton();
		/*---------------------Quarterly Report--------------------*/
		if(e.getActionCommand().equals("QuartelyRepotyButton"))
			handleQuartelyRepotyButton();
		if(e.getActionCommand().equals("GenerateInQuartlyReport"))
			handleGenerateInQuartlyReport();
		/*---------------------Purchase Report--------------------*/
		if(e.getActionCommand().equals("PurchaseReportButton"))
			handlePurchaseReportButton();
		if(e.getActionCommand().equals("GeneratetInPurchaseReport"))
			handleGeneratetInPurchaseReport();
		/*-------------------Stock Report------------------------*/
		if(e.getActionCommand().equals("StockReportButton"))
			handleStockReportButton();
	}
	
	
	/*---------------------------------------------------------------------*/
	/*-----------------------Approve Supply Functions----------------------*/
	/*---------------------------------------------------------------------*/
	/**
	 * Show Approve screen and get table
	 */
	private void handleApproveSupplyButtonButton()
	{
		GuiScreen.ResetUpdateMessage(); //Clear Text
		sendUserIDApproveSupply(); //Send Query To DB

	}
	/**
	 * This function send Manager ID to DB
	 */
	private void sendUserIDApproveSupply()
	{
		callbackStringArray LocalUserID=new callbackStringArray(MessageType.getStationSuppliesOrder);
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		LocalUserID.setVariance(data);
		LocalUserID.setWhatToDo(MessageType.getStationSuppliesOrder);
		Server.handleMessageFromClient(LocalUserID);

	}
	/**
	 * This Function Change Screen To Approve Supply orders
	 */
	private void ChangeScreenToApproval(){
		/*----------------Show Approve Supplies screen------------*/
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"ApproveSupplyLayer");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
	}
	/**
	 * This function update supplies in DB and Update Table
	 */
	private void handleUpdateOnApproveScreen()
	{
		/*-------------Read from Values from Table----*/
		int i,RowNum;
		String what;
		JTable ApproveTable;
		callbackVector UpdateApproveTable = new callbackVector(MessageType.setStationSuppliesOrder);
		ApproveTable = GuiScreen.getApproveTable();
		DefaultTableModel CurrentTableModel = (DefaultTableModel) ApproveTable.getModel();
		RowNum = CurrentTableModel.getRowCount();
		for(i=0 ; i < RowNum ; i++){
			what=(String) CurrentTableModel.getValueAt(i, 7);
			if(!what.equals("Waiting")){
				
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
			GuiScreen.UpdateSuccessMessage();
			handleApproveSupplyButtonButton();
		}
		else {
			GuiScreen.ErrorNoChangeUpdateApprove();
		}
	}
	
	
	/*---------------------------------------------------------------------*/
	/*-----------------------Update Limit Functions------------------------*/
	/*---------------------------------------------------------------------*/	
	/**
	 * This function show screen when "Update limit pressed"
	 */
	private void handleUpdateLimitLevelScreen(){
		GuiScreen.ResetUpdateLimitTextError();
		StockFlag=false;
		SendUserIDUpdateLimitLevel();	
	}
	/**
	 * This function Only Update Global Value GasStationFuelLevel
	 */
	private void SendUserIDUpdateLimitLevel(){
		callbackStringArray LocalUserID=new callbackStringArray(MessageType.getCurrentThresholdLimit);
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		LocalUserID.setVariance(data);
		Server.handleMessageFromClient(LocalUserID);
	}
	/**
	 * This function Change To Update Limit Level Screen
	 */
	private void ChangeScreenToUpdateLimitLevel(){
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"UpdateLimitLevelFuelLayer");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"EmptyLeftPanel");
	}
	/**
	 * handle 'Update' Button on Update Level Limit
	 */
	private void handleUpdateLimitLevelButton(){
		
		/*-------------Read from Values from Table----*/
		int i,RowNum,flagEdit=0;
		Object[][] FuelData=new Object[3][];
		Object[] tempVariance;
		JTable LimitTable;
		int NewLevel,what1,what2;
				FuelData=((callbackStringArray)GasStationFuelLevel).getData();
				tempVariance=((callbackStringArray)GasStationFuelLevel).getVariance();
				GasStation=(int) tempVariance[0];
				//callbackVector UpdateApproveTable = new callbackVector(MessageType.setCurrentThresholdLimit);
				callbackVector UpdateFuelLimit = new callbackVector(MessageType.setCurrentThresholdLimit);
				
				LimitTable = GuiScreen.getUpdatelimitLevelTable();
				DefaultTableModel CurrentTableModel = (DefaultTableModel) LimitTable.getModel();
				
				//what (int)CurrentTableModel.get
				RowNum = CurrentTableModel.getRowCount();
				for(i=0 ; i < RowNum ; i++){
					what1=Integer.valueOf((String) CurrentTableModel.getValueAt(i, 3));
					what2=Integer.valueOf((String) FuelData[i][3]);
					if(what1!=what2){
						NewLevel =Integer.valueOf((String) CurrentTableModel.getValueAt(i, 3));
						if(NewLevel>0)
						{
							callbackStationFuels UpdateFuel = new callbackStationFuels();
							flagEdit=1;
							UpdateFuel.setGasStationID(GasStation);
							UpdateFuel.setThresholdLimit(NewLevel);
							UpdateFuel.setFuelID( Integer.valueOf(   ((String) FuelData[i][0])  ));
							UpdateFuelLimit.add(UpdateFuel);
						}
						else
						{
							UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
							UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));
								JOptionPane.showMessageDialog(GuiScreen,	
									"Can't Enter Negative Level",
										"Error Message",
							    			JOptionPane.ERROR_MESSAGE);	
						}
					
					}
				}
				/*Just is there was a change update DB---*/
			if(flagEdit==1)
			{
				Server.handleMessageFromClient(UpdateFuelLimit);
				handleUpdateLimitLevelScreen();
				GuiScreen.SuccessWasChangeUpdateLevel();
			}
			else 
			{
				GuiScreen.ErrorNoChangeInUpdateLevel();	
			}
		
	}

	
	/*---------------------------------------------------------------------*/
	/*-----------------------Create Report Functions-----------------------*/
	/*---------------------------------------------------------------------*/
	/**
	 * Show CreateReport
	 */
	private void handleCreateReportButton(){
		
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"EmptyCenterPanel");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"CreateReportLeftPanel");
	}
	
	
	/*---------------------------------------------------------------------*/
	/*-----------------------Quarterly Report Functions--------------------*/
	/*---------------------------------------------------------------------*/	
	/**
	 * Show Quarterly Report Screen
	 */
	private void handleQuartelyRepotyButton(){
		GuiScreen.ResetErrorLabel();
		SendUserIDQuarterlyReport();	
	}
	/**
	 * Send To DB Manager ID for get Report
	 */
	private void SendUserIDQuarterlyReport(){
		callbackStringArray Local = new callbackStringArray(MessageType.getQuarterIncomesReport);
		Object[] data=new Object[1];
		data[0]=EnteredUser.getUserID();
		Local.setVariance(data);
		Server.handleMessageFromClient(Local);
	}
	/**
	 * Change Screen To Quarterly report screen
	 */
	private void ChangeQuarterlyReportScreen(){
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"QuartelyRepotyScreen");
	}
	/**
	 * This function get quarter and Year and Show Report
	 */
	private void handleGenerateInQuartlyReport(){
		GuiScreen.ResetUpdateMessageQuarterlyReport();
		/*-----------------get quarter-----------------------*/
		int quarter = 1+new Integer(GuiScreen.getQuarterlycomboBoxInQuarterlyReport());
		/*-----------------get Year--------------------------*/
		int Year = GuiScreen.getYearSelectInQuarterlyReport();
		/*-----------------Show Table------------------------*/
		int i;
		ArrayList<Integer> indexarry =new ArrayList<Integer>();
		Object[][] Data=QuartlyReport.getData();
		for(i=0;i<QuartlyReport.getData().length;i++)
		{
			if(quarter==Integer.parseInt(String.valueOf(Data[i][1])) && Year==Integer.parseInt( String.valueOf (Data[i][0]) ) )
			{
				indexarry.add(i);
			}
		}
		if(indexarry.size()==0)
		{
			GuiScreen.EmptyRecordInQuarterlyReport();
		}
		else{
		/*----------------Work with Global Table -QuartlyReport ---------------*/
		GuiScreen.setQuarterlyReportTable(
				new DefaultTableModel(QuartlyReport.getRowByIndex(ArrayListToArray(indexarry)), QuartlyReport.getColHeaders() ) );
		}
	}

	/*---------------------------------------------------------------------*/
	/*-----------------------Purchase Report Functions---------------------*/
	/*---------------------------------------------------------------------*/	
	/**
	 * Show Purchase Report Screen
	 */
	private void handlePurchaseReportButton(){
		GuiScreen.ResetErrorLabel();
		SendUserIdPurchaseReport();		
	}
	/**
	 * Change Screen To Purchase Report
	 */
	private void SendUserIdPurchaseReport(){
		callbackStringArray Local = new callbackStringArray(MessageType.getQuarterPurchaseReport);
		Object[] data=new Object[1];
		data[0]=EnteredUser.getUserID();
		Local.setVariance(data);
		Server.handleMessageFromClient(Local);
	}
	/**
	 * Change Screen To Purchase Report
	 */
	private void ChangeScreenToPurchaseReport(){
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"PurchaseReportScreen");
	}
	/**
	 * Show on Purchase Report table all the data by Quarter and Year
	 */
	private void handleGeneratetInPurchaseReport(){
		
		GuiScreen.ResetUpdateMessagePurchaseReport();
		/*-----------------get quarter-----------------------*/
		int quarter = 1+new Integer(GuiScreen.getQuarterlycomboBoxInPurchaseReport());	
		/*-----------------get Year--------------------------*/
		int Year = GuiScreen.getYearSelectInPurchaseReport();
		/*-----------------Show Table------------------------*/
		int i;
		ArrayList<Integer> indexarry =new ArrayList<Integer>();
		Object[][] Data=PurchaseReport.getData();
		for(i=0;i<PurchaseReport.getData().length;i++)
		{
			if(quarter==Integer.parseInt(String.valueOf(Data[i][1])) && Year==Integer.parseInt( String.valueOf (Data[i][0]) ) )
			{
				if(!indexarry.contains(i))
				{
					indexarry.add(i);
				}
			}
		}
		/* if there is no report for this quarter*/
		if(indexarry.size()==0)
		{
			GuiScreen.EmptyRecordInPurchase();
		}
		else
		{
		GuiScreen.setPurchaseReportTable(
				new DefaultTableModel(PurchaseReport.getRowByIndex(ArrayListToArray(indexarry)), PurchaseReport.getColHeaders() ) );
	
		}
	}
	
	/*---------------------------------------------------------------------*/
	/*-----------------------Stock Report Functions------------------------*/
	/*---------------------------------------------------------------------*/	

	/**
	 * Show Stock Report
	 */
	private void handleStockReportButton(){
		GuiScreen.ResetErrorLabel();
		StockFlag=true;
		SendUserIDStockReport();	
		ChangeScreenToStockReport();
	}
	/**
	 * Send ManagerID To DB with request to get Stock Report
	 */	
	private void SendUserIDStockReport(){
		
		/*-----send Station ID-------*/
		callbackStringArray StationCurrentFuels = new callbackStringArray(MessageType.getCurrentThresholdLimit); //create new CallbackStationFuels
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		StationCurrentFuels.setVariance(data); //Enter Station UserID
		Server.handleMessageFromClient(StationCurrentFuels);	//send to DB
	}
	private void PrintStockReport(callbackStringArray e)
	{	
		FloatArray = new Float[6];
				/*------get callback Fuels ------*/
		//Get from the common buffer new callback
				int NumbersFuelsInCurrentGasStaion=((callbackStringArray) e).getRowCount();
				int index=0;
				String[][] data1=new String[3][];
				while(index<NumbersFuelsInCurrentGasStaion){
					data1=(String[][]) ((callbackStringArray) e).getData();
					if(data1[index][1].equals("95")) // there is 95 fuel
					{
						FloatArray[0]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[1]=Float.valueOf(data1[index][2]); //Capacity
					}
					if(data1[index][1].equals("Scooters Fuel")) // there is scooter fuel
					{
						FloatArray[2]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[3]=Float.valueOf(data1[index][2]); //Capacity
					}
					if(data1[index][1].equals("Diesel")) // there is diesel fuel
					{
						FloatArray[4]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[5]=Float.valueOf(data1[index][2]); //Capacity
					}
					index++;
				}
			GuiScreen.setStockGraphFloatArray(FloatArray);	
	}

	/**
	 * Change Screen To Stock Reports
	 */
	private void ChangeScreenToStockReport(){
		/*----------------Show 'Stock Report' Panel------------*/
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"StockReportLayer");	
	}
	private int[] ArrayListToArray(ArrayList<Integer> e){
	
		int i;
		int[] returnArray=new int[e.size()];
		for (i=0;i<e.size();i++){
			returnArray[i]=e.get(i);
		}
		return returnArray;
	}


	
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){	
			switch(((CallBack) arg).getWhatToDo()){
				case getStationSuppliesOrder:
					if(arg instanceof callback_Error )
					{
						GuiScreen.ErrorEnterApproval();
					}
					if (arg instanceof callbackStringArray)
					{
						GuiScreen.setApprovalTable(((callbackStringArray) arg).getDefaultTableModel());	//Update Table
						ChangeScreenToApproval();//Change Screen
					}
					break;
				case getCurrentThresholdLimit:
					if(StockFlag)
					{
						if(arg instanceof callback_Error)
						{
							GuiScreen.ErrorEnterStockReport();
						}
						if(arg instanceof callbackStringArray)
						{
							PrintStockReport((callbackStringArray) arg);
							
						}
					}
					else
					{
						if(arg instanceof callback_Error)
						{
							GuiScreen.ErrorEnterUpdateLimitLevel();
						}
						if(arg instanceof callbackStringArray)
						{
							GasStationFuelLevel=(CallBack)arg;
							GuiScreen.setUpdatelimitLevelTable(((callbackStringArray) arg).getDefaultTableModel());
							ChangeScreenToUpdateLimitLevel();
						}
					}
					break;
				case getQuarterIncomesReport:
					if(arg instanceof callback_Error)
					{
						GuiScreen.ErrorEnterQuartReport();
					}
					if (arg instanceof callbackStringArray)
					{
						QuartlyReport=(callbackStringArray) arg;
						GuiScreen.setYearSelectInQuarterlyReportComboBox(((callbackStringArray) arg).getComboBoxStringArray());
						ChangeQuarterlyReportScreen();
					}
					break;
				case getQuarterPurchaseReport:
					if(arg instanceof callback_Error)
					{
						GuiScreen.ErrorEnterPurchaseReportScreen();
					}
					if(arg instanceof callbackStringArray)
					{
						PurchaseReport=(callbackStringArray) arg;
						GuiScreen.setYearSelectInPurchasheReport(((callbackStringArray) arg).getComboBoxStringArray());
						ChangeScreenToPurchaseReport();
					}
					break;
					
			/*Don't change!*/
			default:
				super.update(o, arg);
				break;
			/*-------------*/
			}
	}
	else if(arg instanceof callbackVector){
			switch(((callbackVector) arg).getWhatToDo()){
			case getWaitingTariff:
	
				break;
			case setWaitingTariff:
			
				break;
			default:
				break;
			
			}
	}
	else if(arg instanceof Vector){
	
	}
		
	}


}