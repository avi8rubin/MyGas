package controller;


import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import GUI.MarketingManagerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCampaign;
import callback.callbackStringArray;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.JTableToExcel;
import common.MessageType;

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
	private JButton ExportButton2;
	private JButton CommentsForMarketingCampaignButton;
	private JButton ExportButton;

	private JComboBox<?> ComboBoxSelection;
//
	private JButton StartSale;
	private JComboBox<?> ComboBoxSalePattern;
	private Object[][] PatterWithID;
	
	
	private JLabel Datelabel;
	private JLabel Datelabel2;

	private boolean CommentsForMarketionFlag=false;

	public MarketingManagerController(Client Server, callbackBuffer CommonBuffer, MarketingManagerGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		
		/* ------ Add action listener to buttons ------ */
		//Tariff update Button
		TariffButton = GuiScreen.getTariffButton();
		TariffButton.addActionListener(this);
		TariffButton.setActionCommand("Tariff");								
		
//		Tariff Update Button
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
		
		ExportButton2= GuiScreen.getExport2Button();
		ExportButton2.addActionListener(this);
		ExportButton2.setActionCommand("Export CustomerCharacterization Report");		

		Datelabel=GuiScreen.getErrorDateLabel();
				
		//Comments For Marketing Campaign
		CommentsForMarketingCampaignButton=GuiScreen.getCommentsForMarketingCampaignButton();
		CommentsForMarketingCampaignButton.addActionListener(this);
		CommentsForMarketingCampaignButton.setActionCommand("Comments For Marketing Campaign");	
		
		ExportButton= GuiScreen.getExportButton();
		ExportButton.addActionListener(this);
		ExportButton.setActionCommand("Export CommentsForMarketingCampaign Report");		
		//
		ComboBoxSelection=GuiScreen.getComboBox();
		ComboBoxSelection.addActionListener(this);
		ComboBoxSelection.setActionCommand("Change ComboBox selection");	
		
		//activate sale
		StartSale=GuiScreen.getStartSaleButton();
		StartSale.addActionListener(this);
		StartSale.setActionCommand("start sale");
		
		Datelabel2=GuiScreen.getErrorDateLabel2();
	}

	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff")){
			ContainerCardLeft.show(LeftCardContainer, "EmptyLeftPanel");
			ContainerCardCenter.show(CenterCardContainer, "Tariff");				//The TariffApproval layer will be display
			HandleTariffPressed();											
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
		else if(e.getActionCommand().equals("Export CustomerCharacterization Report")){
			HandleCustomerCharacterizationReport();											
		}
		//
		else if(e.getActionCommand().equals("Change ComboBox selection")){
			HandleChangeCampaignFromComboBox();

		}
		else if(e.getActionCommand().equals("Comments For Marketing Campaign")){
			ContainerCardCenter.show(CenterCardContainer, "CommentsForMarketingCampaignReport");
			HandleCommentsForMarketingCampaignCombo();
		}

		else if(e.getActionCommand().equals("Export CommentsForMarketingCampaign Report")){
			new JTableToExcel(GuiScreen.getExportButton(), GuiScreen.getCommentsTable());
			
			//HandleExportReport();
		}
		//
		else if(e.getActionCommand().equals("start sale")){	
			HandleActivateSaleCampaignButtonPressed();
		}
		
		
	}

	private void HandleExportReport() {
		
	}
	
	private void HandleTariffPressed(){
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
	}
	private void HandleChangeCampaignFromComboBox(){	
		CommentsForMarketionFlag=false; 
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
	}
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
	private void HandleCommentsForMarketingCampaignCombo(){
		CommentsForMarketionFlag=true;
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
	}
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
	private void HandleActivateSaleCampaignPressed() {
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCampaignPatternAndActiveCampaign));
	}
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
					CallBack temp =  (CallBack) arg;
					if(temp instanceof callback_Error){
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

