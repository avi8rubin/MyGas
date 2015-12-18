package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

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
		//	HandleActivateSaleCampaignPressed();											
		}
		
		else if(e.getActionCommand().equals("Customer Characterization")){
			ContainerCardCenter.show(CenterCardContainer, "CustomerCharacterizationReport");
		}
		else if(e.getActionCommand().equals("Comments For Marketing Campaign")){
			ContainerCardCenter.show(CenterCardContainer, "CommentsForMarketingCampaignReport");			
		}
		else if(e.getActionCommand().equals("Export CustomerCharacterization Report")){
			HandleCustomerCharacterizationExportReport();											
		}
		else if(e.getActionCommand().equals("Export CommentsForMarketingCampaign Report")){
		//	HandleCommentsForMarketingCampaignExportReport();											
		}
		
		
	}

	private void HandleTariffPressed(){
//		Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
//		callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();		
//		GuiScreen.setTariffApprovalTable(TariffTable.getDefaultTableModel());
		
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
		callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();		
		GuiScreen.setTariffUpdateTable(TariffTable.getDefaultTableModel());
	}

	private void HandleActivateSaleCampaignPressed(){}
	
	
	private void HandleCommentsForMarketingCampaignExportReport()
	{
		String CampaignToChooseFrom=GuiScreen.getComboBoxSelection();
     //   JOptionPane.showMessageDialog(null,CampaignToChooseFrom.toString());
	}
	private void HandleCustomerCharacterizationExportReport(){
		String startDate=GuiScreen.getStartDate(); 
		String endDate=GuiScreen.getEndDate();

	}
}

