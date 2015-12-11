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
	private JButton CommentsForMarketingCampaignButton;
	private JLayeredPane CreateReortsCenterPanel;
	private JLayeredPane CreateReportsLeftPanel;
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
		
		//Tariff Update Button
		UpdateButton = GuiScreen.getUpdateButton();
		UpdateButton.addActionListener(this);
		UpdateButton.setActionCommand("Update Button");								//Add action command

		//Report Button
		ReportButton = GuiScreen.getReportButton();
		ReportButton.addActionListener(this);
		ReportButton.setActionCommand("Reports");								//Add action command

		//Activate Sale Campaign Button
		ActivateSaleCampaignButton = GuiScreen.getActivateSaleCampaignButton();
		ActivateSaleCampaignButton.addActionListener(this);
		ActivateSaleCampaignButton.setActionCommand("Activate Sale Campaign");								//Add action command
		
		//Customer Characterization Report
		CustomerCharacterizationReportButton= GuiScreen.getCustomerCharacterizationReportButton();
		CustomerCharacterizationReportButton.addActionListener(this);
		CustomerCharacterizationReportButton.setActionCommand("Customer Characterization Report");	
		//Comments For Marketing Campaign
		CommentsForMarketingCampaignButton=GuiScreen.getCommentsForMarketingCampaignButton();
		CommentsForMarketingCampaignButton.addActionListener(this);
		CommentsForMarketingCampaignButton.setActionCommand("Comments For Marketing Campaign");	
		//

	}

	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff")){
			ContainerCardCenter.show(CenterCardContainer, "Tariff");				//The TariffApproval layer will be display
			HandleTariffUpdatePressed();											
		}
		if(e.getActionCommand().equals("Reports")){
			ContainerCardCenter.show(CenterCardContainer,"Reports Center");
			ContainerCardLeft.show(LeftCardContainer, "Reports Left");
			HandleCreateReportsPressed();											
		}
		if(e.getActionCommand().equals("Activate Sale Campaign")){
			HandleActivateSaleCampaignPressed();											
		}
		
		if(e.getActionCommand().equals("Customer Characterization Report")){
			HandleCustomerCharacterizationReport();											
		}
		if(e.getActionCommand().equals("Comments For Marketing Campaign")){
			HandleCommentsForMarketingCampaign();											
		}
	}

	private void HandleTariffUpdatePressed(){
	//	Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
	//	callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();		
	//	GuiScreen.setTariffUpdateTable(new TableModel(TariffTable.getData(), TariffTable.getColHeaders()));
	}

	private void HandleCreateReportsPressed(){
	
	}
	private void HandleActivateSaleCampaignPressed(){}
	
	private void HandleCustomerCharacterizationReport(){
	//Server.handleMessageFromClient(new callbackStringArray(MessageType.getCommentsForMarketionCampaign));
	//callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();		
	//GuiScreen.setTariffUpdateTable(new TableModel(TariffTable.getData(), TariffTable.getColHeaders()));
	}
	private void HandleCommentsForMarketingCampaign(){}
}

