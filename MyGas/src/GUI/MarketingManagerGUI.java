package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;
import common.TableModel;

public class MarketingManagerGUI extends abstractPanel_GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//top buttons
	private JButton TariffButton;
	private JButton ReportButton;
	private JButton ActivateSaleCampaignButton;
	//TariffButton
	private JLayeredPane TariffDisplayLayer = new JLayeredPane();
	private JScrollPane TariffScrollPane = new JScrollPane();
	private JTable TariffUpdateTable = new JTable();
	private JButton UpdateButton;
	//ReportButton
	private JButton CustomerCharacterizationReportButton;
	private JButton CommentsForMarketingCampaignButton;
	private JLayeredPane CreateReportsLeftLayer= new JLayeredPane();
	private JLayeredPane CreateReortsCenterLayer= new JLayeredPane();
	//

	public MarketingManagerGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);
		/* ------- Adding new button to Top Panel -------- */
		TariffButton= new JButton("Tariff Update");
		TariffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TariffButton.setBounds(23, 133, 160, 38);
		TopPanel.add(TariffButton);
		
		/* ------- Adding new button to Top Panel -------- */
		ReportButton = new JButton("Create Reports");
		ReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportButton.setBounds(211, 133, 178, 38);
		TopPanel.add(ReportButton);
		
		/* ------- Adding new button to Top Panel -------- */
		ActivateSaleCampaignButton = new JButton("Activate Sale Campaign");
		ActivateSaleCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ActivateSaleCampaignButton.setBounds(416, 133, 274, 38);
		TopPanel.add(ActivateSaleCampaignButton);
		

		/* ------- Adding new layer to Tariff Panel -------- */
		
		TariffDisplayLayer.setBounds(0, 0, 968, 512);
		ContactFrame.getContentPane().add(TariffDisplayLayer);
		TariffScrollPane.setBounds(48, 92, 864, 295);
		TariffDisplayLayer.add(TariffScrollPane);
		TariffScrollPane.setViewportView(TariffUpdateTable);
		TariffUpdateTable.setRowHeight(23);
		TariffUpdateTable.setFillsViewportHeight(true);
		TariffUpdateTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JLabel FuelsTariffLabel = new JLabel("Fuels Tariff");
		FuelsTariffLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelsTariffLabel.setBounds(48, 31, 147, 33);
		TariffDisplayLayer.add(FuelsTariffLabel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.setBounds(787, 416, 125, 33);
		TariffDisplayLayer.add(btnUpdate);	
		
		CenterCardContainer.add(TariffDisplayLayer, "Tariff");
		TariffDisplayLayer.setOpaque(true);
		TariffDisplayLayer.setName("Tariff");
		
		
		/* ------- Adding new layer to reports Panel -------- */
		//left layer
		CreateReportsLeftLayer.setBounds(0, 0, 310, 544);
		ContactFrame.getContentPane().add(CreateReportsLeftLayer);

		//buttons left layer
		CommentsForMarketingCampaignButton = new JButton
				(String.format("%s\n%s", "Comments for ","marketing campaign"));
		CommentsForMarketingCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CommentsForMarketingCampaignButton.setBounds(27, 33, 212, 100);
		
		CustomerCharacterizationReportButton = new JButton
				(String.format("%s\n%s","Customer characterization ","by period"));
		CustomerCharacterizationReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerCharacterizationReportButton.setBounds(27, 161, 212, 100);
		//add to container
		LeftCardContainer.add(CreateReportsLeftLayer, "Reports Left");
		CreateReportsLeftLayer.setOpaque(true);
		CreateReportsLeftLayer.setName("Reports Left");
		//center layer
		CreateReortsCenterLayer.setBounds(0, 26, 974, 526);
		ContactFrame.getContentPane().add(CreateReortsCenterLayer);
		//add to container
		CenterCardContainer.add(CreateReortsCenterLayer, "Reports Center");
		CreateReortsCenterLayer.setOpaque(true);
		CreateReortsCenterLayer.setName("Reports Center");
		
		
		
}

	//Tariff	
	public JButton getTariffButton(){
		return TariffButton;
	}
	public JButton getUpdateButton(){
		return UpdateButton;
	}
	public JLayeredPane getTariffDisplayLayer(){
		return TariffDisplayLayer;
	}
	public void setTariffUpdateTable(TableModel NewTable){
		TariffUpdateTable.setModel(NewTable);	
	}
	//reports
	public JButton getReportButton(){
		return ReportButton;
	}
	public JButton getCommentsForMarketingCampaignButton(){
		return CommentsForMarketingCampaignButton;
	}

	public JButton getCustomerCharacterizationReportButton(){
		return CustomerCharacterizationReportButton;
	}
	
	//campaign sale
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}




}


