package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
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
	
	protected JLayeredPane CenterPanel = new JLayeredPane();
	protected JLayeredPane LeftPanel = new JLayeredPane();

	//top buttons
	private JButton TariffButton;
	private JButton ReportButton;
	private JButton ActivateSaleCampaignButton;
	//TariffButton
	private JLayeredPane TariffDisplayLayer;
	private JScrollPane TariffScrollPane = new JScrollPane();
	private JTable TariffUpdateTable = new JTable();
	private JButton UpdateButton;
	//ReportButton
	private JButton CustomerCharacterizationReportButton;
	private JButton CommentsForMarketingCampaignButton;
	private JLayeredPane CreateReportsLeftLayer;
	private JLayeredPane CreateReportsCenterLayer ;
	//Comments For Marketing Campaign Report
	private JScrollPane CommentsReportScrollPane = new JScrollPane();
	private JTable CommentsReportTable = new JTable();
	private JLabel ChooseCampaignLabel;
	private JComboBox CampaignComboBox ;
	private JButton ExportButton;
	//private JScrollBar scrollBar;
	
	
	
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
		TariffDisplayLayer = new JLayeredPane();
		TariffDisplayLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		//TariffDisplayLayer.setBounds(0, 0, 968, 512);
		CenterCardContainer.add(TariffDisplayLayer, "Tariff");
		TariffDisplayLayer.setOpaque(true);
		TariffDisplayLayer.setName("Tariff");
		
		/*------- Create JTable surround with scroll pane and add
		 * 					 it to TariffApprovalPanel --------*/
		TariffScrollPane.setBounds(43, 58, 600, 200);
		TariffDisplayLayer.add(TariffScrollPane);
		
		TariffScrollPane.setViewportView(TariffUpdateTable);
		TariffUpdateTable.setRowHeight(23);
		TariffUpdateTable.setFillsViewportHeight(true);
		TariffUpdateTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		/* ------- Adding button and label to Tariff Panel -------- */
		JLabel FuelsTariffLabel = new JLabel("Fuels Tariff");
		FuelsTariffLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelsTariffLabel.setBounds(48, 31, 147, 33);
		TariffDisplayLayer.add(FuelsTariffLabel);
		
		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateButton.setBounds(787, 416, 125, 33);
		TariffDisplayLayer.add(UpdateButton);	

		/* ------- Adding new layer to Reports Panel -------- */

		/* ------- Adding Left layer to Reports Panel -------- */
		CreateReportsLeftLayer= new JLayeredPane();
		CreateReportsLeftLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateReportsLeftLayer.setBackground(new Color(169, 169, 169));

		//add to left container
		LeftCardContainer.add(CreateReportsLeftLayer, "ReportsLeft");
		CreateReportsLeftLayer.setOpaque(true);
		CreateReportsLeftLayer.setName("ReportsLeft");

		//buttons left layer
		CommentsForMarketingCampaignButton = 
				new JButton("<html>Comments for marketing campaign report</html>");
		CommentsForMarketingCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CommentsForMarketingCampaignButton.setBounds(27, 33, 212, 100);
		
		CustomerCharacterizationReportButton = 
				new JButton("<html>Customer haracterization by period report</html>");

		CustomerCharacterizationReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerCharacterizationReportButton.setBounds(27, 161, 212, 100);
		
		CreateReportsLeftLayer.add(CommentsForMarketingCampaignButton);
		CreateReportsLeftLayer.add(CustomerCharacterizationReportButton);
		/* ------- Adding CommentsForMarketingCampaignButton layer -------- */
		CreateReportsCenterLayer= new JLayeredPane();
		CreateReportsCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateReportsCenterLayer,"CommentsForMarketingCampaignReport");
		CreateReportsCenterLayer.setOpaque(true);
		CreateReportsCenterLayer.setName("CommentsForMarketingCampaignReport");
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CommentsForMarketingCampaignReport --------*/
		ChooseCampaignLabel = new JLabel("Choose Campaign ");
		ChooseCampaignLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ChooseCampaignLabel.setEnabled(false);
		ChooseCampaignLabel.setBounds(27, 22, 184, 34);
		ContactFrame.getContentPane().add(ChooseCampaignLabel);
		CreateReportsCenterLayer.add(ChooseCampaignLabel);
		
		CampaignComboBox = new JComboBox();
		CampaignComboBox.setBounds(27, 71, 217, 34);
		ContactFrame.getContentPane().add(CampaignComboBox);
		CreateReportsCenterLayer.add(CampaignComboBox);

		ExportButton = new JButton("EXPORT");
		ExportButton.setBounds(278, 71, 126, 34);
		ContactFrame.getContentPane().add(ExportButton);
		ExportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateReportsCenterLayer.add(ExportButton);

		CommentsReportScrollPane.setBounds(38, 133, 600, 200);
		CreateReportsCenterLayer.add(CommentsReportScrollPane);
		
		CommentsReportScrollPane.setViewportView(CommentsReportTable);
		CommentsReportTable.setRowHeight(23);
		CommentsReportTable.setFillsViewportHeight(true);
		CommentsReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CustomerCharacterizationReportButton --------*/
		
		
		
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
	public void setCommentsForMarketingCampaignTable(TableModel NewTable){
		CommentsReportTable.setModel(NewTable);	
	}
	public JButton getCustomerCharacterizationReportButton(){
		return CustomerCharacterizationReportButton;
	}

	//campaign sale
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}


}


