package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import GUI.MarketingManagerGUI;
import callback.callbackBuffer;
import callback.callbackStringArray;
import client.Client;
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
		CustomerCharacterizationReportButton.setActionCommand("Customer Characterization Report");
		
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

	}

	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff")){
			ContainerCardLeft.show(LeftCardContainer, "EmptyLeftPanel");
			ContainerCardCenter.show(CenterCardContainer, "Tariff");				//The TariffApproval layer will be display
			HandleTariffUpdatePressed();											
		}
		else if(e.getActionCommand().equals("CreateReports")){
			ContainerCardCenter.show(CenterCardContainer,"EmptyCenterPanel");
			ContainerCardLeft.show(LeftCardContainer, "ReportsLeft");
			HandleCreateReportsPressed();											
		}
		else if(e.getActionCommand().equals("Activate Sale Campaign")){
			HandleActivateSaleCampaignPressed();											
		}
		
		else if(e.getActionCommand().equals("Customer Characterization Report")){
			ContainerCardCenter.show(CenterCardContainer, "CustomerCharacterizationReport");
			HandleCustomerCharacterizationReport();											
		}
		else if(e.getActionCommand().equals("Export CustomerCharacterization Report")){
			HandleCustomerCharacterizationExportReport();											
		}
		else if(e.getActionCommand().equals("Export CommentsForMarketingCampaign Report")){
			HandleCommentsForMarketingCampaignExportReport();											
		}
		
		else if(e.getActionCommand().equals("Comments For Marketing Campaign")){
			ContainerCardCenter.show(CenterCardContainer, "CommentsForMarketingCampaignReport");			
			HandleCommentsForMarketingCampaign();											
		}
	}

	private void HandleTariffUpdatePressed(){
//		Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
//		callbackStringArray TariffUpdateTable = (callbackStringArray) getCallBackFromBuffer();		
//		GuiScreen.setTariffUpdateTable(new TableModel(TariffUpdateTable.getData(), TariffUpdateTable.getColHeaders()));
	}

	private void HandleCreateReportsPressed(){
	
	}
	private void HandleActivateSaleCampaignPressed(){}
	
	private void HandleCustomerCharacterizationReport(){
	}
	
	private void HandleCommentsForMarketingCampaign(){
//		Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
//		callbackStringArray CommentsReportTable = (callbackStringArray) getCallBackFromBuffer();		
//		GuiScreen.setCommentsForMarketingCampaignTable(new TableModel(CommentsReportTable.getData(), CommentsReportTable.getColHeaders()));
	}
	
	private void HandleCommentsForMarketingCampaignExportReport()
	{
		String CampaignToChooseFrom=GuiScreen.getComboBoxSelection();
	}
	private void HandleCustomerCharacterizationExportReport(){}
}

