package controller;

import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import GUI.MarketingManagerGUI;
import callback.callbackBuffer;
import callback.callbackStringArray;
import client.Client;
import common.Checks;
import common.MessageType;
import common.TableModel;

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
	

	
	public MarketingManagerController(Client Server, callbackBuffer CommonBuffer, MarketingManagerGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		
		/* ------ Add action listener to buttons ------ */
		//Tariff update Button
		TariffButton = GuiScreen.getTariffButton();
		TariffButton.addActionListener(this);
		TariffButton.setActionCommand("Tariff");								//Add action command
		
//		Tariff Update Button
		UpdateButton = GuiScreen.getUpdateButton();
		UpdateButton.addActionListener(this);
		UpdateButton.setActionCommand("Update Button");								//Add action command

		//Report Button
		ReportButton = GuiScreen.getReportButton();
		ReportButton.addActionListener(this);
		ReportButton.setActionCommand("CreateReports");								//Add action command

		//Activate Sale Campaign Button
		ActivateSaleCampaignButton = GuiScreen.getActivateSaleCampaignButton();
		ActivateSaleCampaignButton.addActionListener(this);
		ActivateSaleCampaignButton.setActionCommand("Activate Sale Campaign");								//Add action command
		
		//Customer Characterization Report
		CustomerCharacterizationReportButton= GuiScreen.getCustomerCharacterizationReportButton();
		CustomerCharacterizationReportButton.addActionListener(this);
		CustomerCharacterizationReportButton.setActionCommand("Customer Characterization");
		
		ExportButton2= GuiScreen.getExport2Button();
		ExportButton2.addActionListener(this);
		ExportButton2.setActionCommand("Export CustomerCharacterization Report");		

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
			HandleActivateSaleCampaignPressed();											
		}
		
		else if(e.getActionCommand().equals("Customer Characterization")){
			ContainerCardCenter.show(CenterCardContainer, "CustomerCharacterizationReport");
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
			HandleCommentsForMarketingCampaign();
			//HandleActivateSaleCampaignCustomerCharacterizationReportButtonPressed();
		}

		else if(e.getActionCommand().equals("Export CommentsForMarketingCampaign Report")){
			//HandleChangeCampaignFromComboBox();
//			HandleCommentsForMarketingCampaignExportReport();											
		}
		
		
	}

	private void HandleTariffPressed(){
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
		callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();		
		GuiScreen.setTariffUpdateTable(TariffTable.getDefaultTableModel());
	}


	private void HandleChangeCampaignFromComboBox(){
		
		String patternSelected=GuiScreen.getComboBoxSelection();
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
		callbackStringArray CampaignTable = (callbackStringArray) getCallBackFromBuffer();		
		GuiScreen.setCommentsForMarketingCampaignTable(CampaignTable.getDefaultTableModel(),patternSelected);
	
	}

	private void HandleCommentsForMarketingCampaign(){
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
		callbackStringArray CampaignPatters = (callbackStringArray) getCallBackFromBuffer();	
		GuiScreen.SetComboBoxSelection(CampaignPatters);
	}
	private void HandleCustomerCharacterizationReport(){
		String startDate=GuiScreen.getStartDate(); 
		String endDate=GuiScreen.getEndDate();

		boolean flag=Checks.isDateValid(startDate,endDate);
		if(flag==false)
			JOptionPane.showMessageDialog(null, "Error, Illegal Date\nPlease insert a different date", "", JOptionPane.INFORMATION_MESSAGE);
		else{
		String startDateInFormat=String.format(startDate.substring(0,6)+startDate.substring(8,10));
		String endDateInFormat=String.format(endDate.substring(0,6)+endDate.substring(8,10));

		String[] dateArr={startDateInFormat,endDateInFormat};
		
		callbackStringArray createCustomerTable=new callbackStringArray(MessageType.getCustomerCharacterizationByPeriod);
		createCustomerTable.setColHeaders(dateArr);
		Server.handleMessageFromClient(createCustomerTable);
		callbackStringArray customerTable = (callbackStringArray) getCallBackFromBuffer();	
		GuiScreen.setCustomerCharacterizationReportTable(customerTable.getDefaultTableModel());
		}
	}
	
	
	private void HandleActivateSaleCampaignPressed() {
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCampaignPatternAndActiveCampaign));
		callbackStringArray Patterns = (callbackStringArray) getCallBackFromBuffer();	
		GuiScreen.SetComboBoxPattern(Patterns);
	}
	private void HandleActivateSaleCampaignCustomerCharacterizationReportButtonPressed(){	
	}


}

