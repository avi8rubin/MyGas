package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import GUI.CEOGUI;
import GUI.StationManagerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStationFuels;
import callback.callbackStationOrders;
import callback.callbackStringArray;
import callback.callbackUser;
import callback.callbackVector;
import callback.callbackWaitingTariff;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class StationManagerController extends Controller{

	//Global values
	private static callbackUser EnteredUser=null;
	private static CallBack Temp;
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
	
	//Quarterly Report Screen
	callbackStringArray  QuartlyReport;
	//Purchase Report Screen
	callbackStringArray PurchaseReport;
	
	private final JButton GenerateInQuartlyReport;
	private final JButton GeneratetInPurchaseReport;
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
		if(e.getActionCommand().equals("CreateReportButton"))
			handleCreateReportButton();
		if(e.getActionCommand().equals("QuartelyRepotyButton"))
			handleQuartelyRepotyButton();
		if(e.getActionCommand().equals("PurchaseReportButton"))
			handlePurchaseReportButton();
		if(e.getActionCommand().equals("StockReportButton"))
			handleStockReportButton();
		if(e.getActionCommand().equals("UpdateLimitLevelButton"))
			handleUpdateLimitLevelButton();
		if(e.getActionCommand().equals("GenerateInQuartlyReport"))
			handleGenerateInQuartlyReport();
		if(e.getActionCommand().equals("GeneratetInPurchaseReport"))
			handleGeneratetInPurchaseReport();
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
	 * Show Approve screen and get table
	 */
	private void handleApproveSupplyButtonButton()
	{
		GuiScreen.ResetUpdateMessage();
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
			getCallBackFromBuffer();
			GuiScreen.UpdateSuccessMessage();
			handleApproveSupplyButtonButton();
		}
		else {
			GuiScreen.ErrorNoChangeUpdateApprove();
		}
	}
	/**
	 * Show Quarterly Report Screen
	 */
	private void handleQuartelyRepotyButton(){
		GuiScreen.ResetErrorLabel();
		//ResetQuarterlyReportScreen();
		if(getAllQuartelyRepot()){ // get the table data from DB
	
		GuiScreen.setYearSelectInQuarterlyReportComboBox(QuartlyReport.getComboBoxStringArray());	
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"QuartelyRepotyScreen");
		}
		else
			GuiScreen.ErrorEnterQuartReport();
	}
	
	private boolean getAllQuartelyRepot(){
		callbackStringArray Local = new callbackStringArray(MessageType.getQuarterIncomesReport);
		Object[] data=new Object[1];
		data[0]=EnteredUser.getUserID();
		Local.setVariance(data);
		Server.handleMessageFromClient(Local);
		Temp=(CallBack) getCallBackFromBuffer();
		if(Temp instanceof callbackStringArray)
		{
			QuartlyReport=(callbackStringArray) Temp;
			return true;
		}
		return false;
	
	}
	private boolean getAllPurchaseRepot(){
		callbackStringArray Local = new callbackStringArray(MessageType.getQuarterPurchaseReport);
		Object[] data=new Object[1];
		data[0]=EnteredUser.getUserID();
		Local.setVariance(data);
		Server.handleMessageFromClient(Local);
		Temp=(CallBack) getCallBackFromBuffer();
		if(Temp instanceof callbackStringArray)
		{
			PurchaseReport=(callbackStringArray) Temp;
			return true;
		}
		return false;
	}
	/**
	 * Show Purchase Report Screen
	 */
	private void handlePurchaseReportButton(){
		
		if(getAllPurchaseRepot()){ // get the table data from DB
	
	
		GuiScreen.setYearSelectInPurchasheReport(PurchaseReport.getComboBoxStringArray());
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"PurchaseReportScreen");
		}
		else
			GuiScreen.ErrorEnterPurchaseReportScreen();
	}
	/**
	 * Show Stock Report
	 */
	private void handleStockReportButton(){
		//handleUpdateOnApproveScreen();
		if(setFloatArray()){
		/*----------------Show 'Stock Report' Panel------------*/
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"StockReportLayer");
		}
		else
			GuiScreen.ErrorEnterStockReport();
	}
	/**
	 * Update Limit Level
	 */
	private void handleUpdateLimitLevelButton(){
		
		/*-------------Read from Values from Table----*/
		int i,RowNum,flagEdit=0;
		Object[][] FuelData=new Object[3][];
		Object[] tempVariance;
		JTable LimitTable;
		int NewLevel,what1,what2;
		
		
		
		if(UpdateGasStationFuelLevel())
		{
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
				getCallBackFromBuffer();
				handleUpdateLimitLevelScreen();
				GuiScreen.SuccessWasChangeUpdateLevel();
			}
			else 
			{
				GuiScreen.ErrorNoChangeInUpdateLevel();	
			}
		}
	}

	/**
	 * This function show screen when "Update limit pressed"
	 */
	private void handleUpdateLimitLevelScreen(){
		if(setTableInUpdateLimitFuel())
		{
		GuiScreen.ResetUpdateLimitTextError();
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

		/*-----------------Set Table values in Approval Table------------------*/
		if(UpdateGasStationFuelLevel())
			{		
		GuiScreen.setUpdatelimitLevelTable(((callbackStringArray) GasStationFuelLevel).getDefaultTableModel());
		return true;
			}
		return false;
	}
	/**
	 * This function Only Update Global Value GasStationFuelLevel
	 * @return true if Query was successful 
	 */
	private boolean UpdateGasStationFuelLevel(){
		callbackStringArray LocalUserID=new callbackStringArray(MessageType.getCurrentThresholdLimit);
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		LocalUserID.setVariance(data);
		Server.handleMessageFromClient(LocalUserID);
		GasStationFuelLevel=(CallBack) getCallBackFromBuffer();
		if(GasStationFuelLevel instanceof callback_Error)
			GuiScreen.ErrorEnterUpdateLimitLevel();
		/*-----------------Set Table values in Approval Table------------------*/
		if(GasStationFuelLevel instanceof callbackStringArray)
			{		
		return true;
			}
		return false;
	}
	
	/**
	 * Show CreateReport
	 */
	private void handleCreateReportButton(){
		
		ContainerCard=(CardLayout)(CenterCardContainer.getLayout());
		ContainerCard.show(CenterCardContainer,"EmptyCenterPanel");
		
		ContainerCard=(CardLayout)(LeftCardContainer.getLayout());
		ContainerCard.show(LeftCardContainer,"CreateReportLeftPanel");
	}
	/**
	 * get Float Array of fuel for Stock report
	 */
	private boolean setFloatArray(){
		FloatArray = new Float[6];

		/*-----send Station ID-------*/
		callbackStringArray StationCurrentFuels = new callbackStringArray(MessageType.getCurrentThresholdLimit); //create new CallbackStationFuels
		Object[] data = new Object[1];
		data[0]=EnteredUser.getUserID();
		StationCurrentFuels.setVariance(data); //Enter Station UserID
		
		Server.handleMessageFromClient(StationCurrentFuels);	//send to DB
		Temp = (CallBack) getCallBackFromBuffer();
		/*-----------------Set Table values in Approval Table------------------*/
		if(Temp instanceof callbackStringArray)
			{		

				
				/*------get callback Fuels ------*/
								//Get from the common buffer new callback
				int NumbersFuelsInCurrentGasStaion=((callbackStringArray) Temp).getRowCount();
				int index=0;
				String[][] data1=new String[3][];
				while(index<NumbersFuelsInCurrentGasStaion){
					data1=(String[][]) ((callbackStringArray) Temp).getData();
					if(data1[index][1].equals("95")) // there is 95 fuel
					{
						FloatArray[0]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[1]=Float.valueOf(data1[index][2]); //Capcity
					}
					if(data1[index][1].equals("Scooters Fuel")) // there is scooter fuel
					{
						FloatArray[2]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[3]=Float.valueOf(data1[index][2]); //Capcity
					}
					if(data1[index][1].equals("Diesel")) // there is diesel fuel
					{
						FloatArray[4]=Float.valueOf(data1[index][4]); //Current Amount
						FloatArray[5]=Float.valueOf(data1[index][2]); //Capcity
					}
					index++;
				}
				
			
	
			GuiScreen.setStockGraphFloatArray(FloatArray);

		return true;
			}
		return false;
		
		
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
	 * This function get quarter and Year and Show Report
	 */
	private void handleGenerateInQuartlyReport(){
		GuiScreen.ResetUpdateMessageQuarterlyReport();
		/*-----------------get quarter-----------------------*/
		int quarter  =1+new Integer(GuiScreen.getQuarterlycomboBoxInQuarterlyReport());
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
	private void handleGeneratetInPurchaseReport(){
		
		GuiScreen.ResetUpdateMessagePurchaseReport();
		/*-----------------get quarter-----------------------*/
		int quarter  =1+new Integer(GuiScreen.getQuarterlycomboBoxInPurchaseReport());	
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
	private int[] ArrayListToArray(ArrayList<Integer> e){
	
		int i;
		int[] returnArray=new int[e.size()];
		for (i=0;i<e.size();i++){
			returnArray[i]=e.get(i);
		}
		return returnArray;
	}
	/**
	 * Reset Combox & Table
	 */

	


}
