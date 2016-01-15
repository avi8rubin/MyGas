package controller;


import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.MarketingManagerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCampaign;
import callback.callbackStringArray;
import callback.callbackTariffUpdate;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;
/**
 * callbackTariffUpdate- control all the components of the Marketing manger Gui
 * handle the Creation new rate of the fuels
 * handle the Reports Export
 * handle the creation of new campaigns 
 * @param Server
 * @param CommonBuffer
 * @param GuiScreen
 * @author ליטף	
 */
public class MarketingManagerController extends Controller {
	/**
	 * Marketing Manager GUI components 
	 */
	private CardLayout ContainerCardCenter;
	private CardLayout ContainerCardLeft;
//
	private MarketingManagerGUI GuiScreen;
	private JButton TariffButton;
	private JButton ReportButton;
	private JButton ActivateSaleCampaignButton;
	private JButton UpdateButton;
//
	private JButton CustomerCharacterizationReportButton;
	//private JButton ExportButton2;
	private JButton CommentsForMarketingCampaignButton;
	//private JButton ExportButton;
	private JButton ProduceButton;

	private JComboBox<?> ComboBoxSelection;
	private callbackTariffUpdate tarrifUpdate=new callbackTariffUpdate(MessageType.setUpdateFuelsTariffForCEO);
	private JButton StartSale;
	private Object[][] PatterWithID;
	
	
	private JLabel Datelabel;
	private JLabel Datelabel2;

	private boolean CommentsForMarketionFlag=false;

	public MarketingManagerController(Client Server, callbackBuffer CommonBuffer, MarketingManagerGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		
		/**
		 * Add action listener to buttons
		 */
		//Tariff update Button
		TariffButton = GuiScreen.getTariffButton();
		TariffButton.addActionListener(this);
		TariffButton.setActionCommand("Tariff");								
		
		//Tariff Update Button
		UpdateButton = GuiScreen.getUpdateButton();
		UpdateButton.addActionListener(this);
		UpdateButton.setActionCommand("Update Button");								
		
		//Report Button
		ReportButton = GuiScreen.getReportButton();
		ReportButton.addActionListener(this);
		ReportButton.setActionCommand("CreateReports");				

		//Activate Sale Campaign Button
		ActivateSaleCampaignButton = GuiScreen.getActivateSaleCampaignButton();
		ActivateSaleCampaignButton.addActionListener(this);
		ActivateSaleCampaignButton.setActionCommand("Activate Sale Campaign");							
		
		//Customer Characterization Report
		CustomerCharacterizationReportButton= GuiScreen.getCustomerCharacterizationReportButton();
		CustomerCharacterizationReportButton.addActionListener(this);
		CustomerCharacterizationReportButton.setActionCommand("Customer Characterization");

		ProduceButton=GuiScreen.getproduceButton();
		ProduceButton.addActionListener(this);
		ProduceButton.setActionCommand("Produce CustomerCharacterization Report");	

		Datelabel=GuiScreen.getErrorDateLabel();
		
		//Comments For Marketing Campaign
		CommentsForMarketingCampaignButton=GuiScreen.getCommentsForMarketingCampaignButton();
		CommentsForMarketingCampaignButton.addActionListener(this);
		CommentsForMarketingCampaignButton.setActionCommand("Comments For Marketing Campaign");	

		ComboBoxSelection=GuiScreen.getComboBox();
		ComboBoxSelection.addActionListener(this);
		ComboBoxSelection.setActionCommand("Change ComboBox selection");	
		
		//activate sale
		StartSale=GuiScreen.getStartSaleButton();
		StartSale.addActionListener(this);
		StartSale.setActionCommand("start sale");
		
		Datelabel2=GuiScreen.getErrorDateLabel2();
		
	}
/**
 * actionPerformed- attach the buttons to their action event handlers and 
 * Adding the appropriate window layer according to the selected action
 * @param ActionEvent
 * 
 */
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff")){
			ContainerCardLeft.show(LeftCardContainer, "EmptyLeftPanel");
			ContainerCardCenter.show(CenterCardContainer, "Tariff");				//The TariffApproval layer will be display
			HandleTariffPressed();											
		}
		else if(e.getActionCommand().equals("Update Button")){
			GuiScreen.setTariffErrorLabel("");
			HandleUpdateFuelFromComboBox();
		}
		else if(e.getActionCommand().equals("CreateReports")){
			ContainerCardCenter.show(CenterCardContainer,"EmptyCenterPanel");
			ContainerCardLeft.show(LeftCardContainer, "ReportsLeft");
		}
		else if(e.getActionCommand().equals("Activate Sale Campaign")){
			ContainerCardLeft.show(LeftCardContainer,"ActivateSaleLeftLayer");
			ContainerCardCenter.show(CenterCardContainer,"ActivateSaleCenterLayer");
			GuiScreen.setActivateDates();
			Datelabel2.setText("");
			HandleActivateSaleCampaignPressed();											
		}
		
		else if(e.getActionCommand().equals("Customer Characterization")){
			ContainerCardCenter.show(CenterCardContainer, "CustomerCharacterizationReport");
			GuiScreen.setDates();
			Datelabel.setText("");
		}		
		else if(e.getActionCommand().equals("Produce Cu"
				+ "stomerCharacterization Report")){
			HandleCustomerCharacterizationReport();											
		}
		else if(e.getActionCommand().equals("Change ComboBox selection")){
			HandleChangeCampaignFromComboBox();
		}
		else if(e.getActionCommand().equals("Comments For Marketing Campaign")){
			ContainerCardCenter.show(CenterCardContainer, "CommentsForMarketingCampaignReport");
			HandleCommentsForMarketingCampaignCombo();
		}
		else if(e.getActionCommand().equals("start sale")){	
			HandleActivateSaleCampaignButtonPressed();
		}	
	}
	
	/**
	 * HandleUpdateFuelFromComboBox- handle the action of determining a new rate for the fuels
	 */
	private void HandleUpdateFuelFromComboBox(){
		GuiScreen.setTariffErrorLabel("");
		String fuelType=GuiScreen.getFuelComboBoxSelection();
		
		int fuelID=0;
		float currentTariff=0;
		float maxTariff=0;

		JTable TarrifTable=GuiScreen.getTariffUpdateTable();
		for(int i=0;i<TarrifTable.getRowCount();i++){
			
			if(TarrifTable.getValueAt(i, 1).equals(fuelType)){
				fuelID=Integer.parseInt(TarrifTable.getValueAt(i, 0).toString());
				currentTariff=Float.parseFloat(TarrifTable.getValueAt(i, 3).toString());
				maxTariff=Float.parseFloat(TarrifTable.getValueAt(i, 2).toString());
			}
		}
		//check new fuel tariff is valid
		if(Checks.isNumberInRange(maxTariff,GuiScreen.getFuelUpdateFromTextArea())){	
		tarrifUpdate.setFuelID(fuelID);
		tarrifUpdate.setWantedPrice(Float.parseFloat(GuiScreen.getFuelUpdateFromTextArea()));
		tarrifUpdate.setCurrentPrice(currentTariff);
		tarrifUpdate.setMaxPrice(maxTariff);
		callbackVector tariffVector= new callbackVector(MessageType.setUpdateFuelsTariffForCEO);
		tariffVector.add(tarrifUpdate);
		Server.handleMessageFromClient(tariffVector);

		}
		else{
			GuiScreen.setTariffErrorLabel("<html>*Incorrect Fuel Tariff, please insert a <u>number</u> "
										+ "between 0 and "+maxTariff+"</html>");	
		}
	}
	
	/**
	 * HandleTariffPressed- Create a query to get the fuels table information from the DB
	 */
	private void HandleTariffPressed(){
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
	}
	/**
	 * HandleChangeCampaignFromComboBox- Create a query to get the fuels for the
	 * Campaigns comboBox
	 * CommentsForMarketionFlag- Variable that distinguishes between two types 
	 * of queries with the same type of message
	 */
	private void HandleChangeCampaignFromComboBox(){	
		CommentsForMarketionFlag=false; 
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
	}
	/**
	 * Server_ChangeCampaignFromComboBox- after getting an answer from the server- add the 
	 * patterns to the comboBox 
	 * @param CampaignTable
	 */
	public void Server_ChangeCampaignFromComboBox(callbackStringArray CampaignTable){
		String patternSelected=GuiScreen.getComboBoxSelection();
		int PattentIndex[] = new int[1]; 
		PattentIndex[0]=GuiScreen.getPatternInt(patternSelected);
		DefaultTableModel table;

		if(patternSelected=="All Campaigns"){
			table=CampaignTable.getDefaultTableModel();
			GuiScreen.setCommentsForMarketingCampaignTable(table);
		}
		else{
			table=new DefaultTableModel(CampaignTable.getRowByIndex(PattentIndex),CampaignTable.getColHeaders());
			GuiScreen.setCommentsForMarketingCampaignTable(table);
		}
	}
	/**
	 * HandleCommentsForMarketingCampaignCombo- Create a query to get the fuels table 
	 * of the Comments For Marketion Campaign Report from the DB
	 * CommentsForMarketionFlag- Variable that distinguishes between two types 
	 * of queries with the same type of message
	 */
	private void HandleCommentsForMarketingCampaignCombo(){
		CommentsForMarketionFlag=true;
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
	}
	/**
	 * HandleCustomerCharacterizationReport- get the date of the wanted report from the user,
	 * check that it is valid and if it is- send a qeury to the server to ger the wanted report
	 */
	private void HandleCustomerCharacterizationReport(){
		String startDate=GuiScreen.getStartDate(); 
		String endDate=GuiScreen.getEndDate();
		Datelabel.setText("");

		boolean flag=Checks.isDateValid(startDate,endDate);
		if(flag==false){
			Datelabel.setText("*Error, Illegal Date");
		}
		else{
		String[] dateArr={startDate,endDate};
		callbackStringArray createCustomerTable=new callbackStringArray(MessageType.getCustomerCharacterizationByPeriod);
		createCustomerTable.setColHeaders(dateArr);
		Server.handleMessageFromClient(createCustomerTable);
		}
	}	
	/**
	 * HandleActivateSaleCampaignPressed- send a query to the server to get the patterns and 
	 * active campaign table
	 */
	private void HandleActivateSaleCampaignPressed() {
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCampaignPatternAndActiveCampaign));
	}
	/**
	 * HandleActivateSaleCampaignButtonPressed- after pressing the activate sale campaign, check
	 * that all fields are filled correctly and send a query to the server to add the new
	 * campaign sale
	 */
	private void HandleActivateSaleCampaignButtonPressed(){
		
		String startDate=GuiScreen.getStartSaleDate();
		String endDate=GuiScreen.getEndSaleDate();
		String patternChoosen=GuiScreen.getComboBoxPattern();
		Datelabel2.setText("");

		Object patternID=GuiScreen.getSalePatternId(patternChoosen,PatterWithID);
		Boolean flag=Checks.isDateValid(startDate, endDate);

		if(flag==false){
			Datelabel2.setText("*Error, Illegal Date");
		}		
		else{
		String startDateToDB= startDate.substring(6,8)+"/"+startDate.substring(3,5)+"/"+startDate.substring(0,2);
		String endDateToDB= endDate.substring(6,8)+"/"+endDate.substring(3,5)+"/"+endDate.substring(0,2);

		callbackCampaign addSale=new callbackCampaign(MessageType.setNewCampaign);
		addSale.setCampaignPatternsID(Integer.parseInt(patternID.toString()));
		addSale.setEndCampaignDate(endDateToDB);
		addSale.setStartCampaignDate(startDateToDB);
		
		Server.handleMessageFromClient(addSale);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){	
			switch(((CallBack) arg).getWhatToDo()){
				case getFuelsDetailes:
					callbackStringArray TariffTable = (callbackStringArray) arg;		
					GuiScreen.setTariffUpdateTable(TariffTable.getDefaultTableModel());
					break;
				case setUpdateFuelsTariffForCEO:
					CallBack temp =  (CallBack) arg;
					if(temp instanceof callback_Error){
						JOptionPane.showMessageDialog(null, "Error, Fuel Update Didn't Saved", 
								"", JOptionPane.INFORMATION_MESSAGE);
						}
					else {
						JOptionPane.showMessageDialog(null, "Success, Fuel Update Saved In The DB", 
								"", JOptionPane.INFORMATION_MESSAGE);
						}
					break;
				case getCommentsForMarketionCampaign:
					callbackStringArray CampaignTable = (callbackStringArray)arg;
					if(CommentsForMarketionFlag)
						GuiScreen.SetComboBoxSelection(CampaignTable);
					else
						Server_ChangeCampaignFromComboBox(CampaignTable);

					break;
				case getCustomerCharacterizationByPeriod:
					callbackStringArray customerTable = (callbackStringArray) arg;	
					GuiScreen.setCustomerCharacterizationReportTable(customerTable.getDefaultTableModel());
					
					break;
				case getCampaignPatternAndActiveCampaign:
						callbackStringArray activeSales = (callbackStringArray) arg;
						GuiScreen.setActiveSalesTable(activeSales.getDefaultTableModel());
						GuiScreen.SetComboBoxPattern(activeSales);
						PatterWithID=activeSales.getVarianceMatrix();
						GuiScreen.SetComboBoxPattern(activeSales);
					break;
				case setNewCampaign:
					CallBack temp1 =  (CallBack) arg;
					if(temp1 instanceof callback_Error){
						JOptionPane.showMessageDialog(null, "Error, Sale Didn't Saved", 
								"", JOptionPane.INFORMATION_MESSAGE);
						}
					else {
						HandleActivateSaleCampaignPressed();
						}
					break;
			/*Don't change!*/
			default:
				super.update(o, arg);
				break;
			/*-------------*/
			}
	}

	}
}

