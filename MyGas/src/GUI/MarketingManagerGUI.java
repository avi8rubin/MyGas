package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

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
	private JLayeredPane TariffDisplayLayer= new JLayeredPane();
	private JScrollPane TariffScrollPane = new JScrollPane();
	private JTable TariffUpdateTable = new JTable();
	private JButton UpdateButton;
	//ReportButton
	private JButton CustomerCharacterizationReportButton;
	private JButton CommentsForMarketingCampaignButton;
	private JLayeredPane CreateReportsLeftLayer= new JLayeredPane();
	//Comments For Marketing Campaign Report
	private JLayeredPane CreateCommentsReportsCenterLayer= new JLayeredPane();
	private JScrollPane CommentsReportScrollPane = new JScrollPane();
	private JTable CommentsReportTable = new JTable();
	private JLabel ChooseCampaignLabel;
	private JComboBox CampaignComboBox ;
	private JButton ExportButton;
	//Comments For Marketing Campaign Report
	private JLayeredPane CreateCustomerReportsCenterLayer= new JLayeredPane(); ;
	private JScrollPane CustomerCharacterizationReportScrollPane = new JScrollPane();
	private JTable CustomerCharacterizationReportTable= new JTable();
	private JButton ExportButton2;
	private JDateChooser StartDateChooser;
	private JDateChooser EndDateChooser;
	private JLabel StartDateLabel;
	private JLabel EndDateLabel;
	//Activate Sale
	private JLayeredPane ActivateSaleLeftLayer= new JLayeredPane();
	private JLayeredPane ActivateSaleCenterLayer= new JLayeredPane();
	private JDateChooser ActivateSale_StartDateChooser;
	private JDateChooser ActivateSale_EndDateChooser;
	private JLabel ActivateSale_StartDateLabel;
	private JLabel ActivateSale_EndDateLabel;
	private JLabel AllActiveCampaignLabel;
	private JLabel SelectPatternLabel;
	private JComboBox PatternsComboBox;
	private JScrollPane ActiveSalesScrollPane = new JScrollPane();
	private JTable ActiveSalesTable= new JTable();
	private JButton StartSaleButton;

	
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
		
////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/* ------- Adding new layer to Tariff Panel -------- */
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
////////////////////////////////////////////////////////////////////////////////////////////////////////

		/* ------- Adding Left layer to Reports Panel -------- */
		CreateReportsLeftLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateReportsLeftLayer.setBackground(new Color(169, 169, 169));

		//add to left container
		LeftCardContainer.add(CreateReportsLeftLayer, "ReportsLeft");
		CreateReportsLeftLayer.setOpaque(true);
		CreateReportsLeftLayer.setName("ReportsLeft");

		//buttons left layer
		CommentsForMarketingCampaignButton = 
				new JButton("<html>Comments for marketing campaign report</html>");
		CommentsForMarketingCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsForMarketingCampaignButton.setBounds(27, 33, 212, 100);
		
		CustomerCharacterizationReportButton = 
				new JButton("<html>Customer haracterization by period report</html>");

		CustomerCharacterizationReportButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CustomerCharacterizationReportButton.setBounds(27, 161, 212, 100);
		
		CreateReportsLeftLayer.add(CommentsForMarketingCampaignButton);
		CreateReportsLeftLayer.add(CustomerCharacterizationReportButton);
		/* ------- Adding CommentsForMarketingCampaignButton layer -------- */
		CreateCommentsReportsCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateCommentsReportsCenterLayer,"CommentsForMarketingCampaignReport");
		CreateCommentsReportsCenterLayer.setOpaque(true);
		CreateCommentsReportsCenterLayer.setName("CommentsForMarketingCampaignReport");

		
		/*------- add labels, comboBox and buttons 
		 * 						to CreateCommentsReportsCenterLayer --------*/
		ChooseCampaignLabel = new JLabel("Choose Campaign ");
		ChooseCampaignLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ChooseCampaignLabel.setEnabled(false);
		ChooseCampaignLabel.setBounds(27, 22, 184, 34);
		ContactFrame.getContentPane().add(ChooseCampaignLabel);
		CreateCommentsReportsCenterLayer.add(ChooseCampaignLabel);
		
		CampaignComboBox = new JComboBox();
		CampaignComboBox.setBounds(27, 71, 217, 34);
		ContactFrame.getContentPane().add(CampaignComboBox);
		CreateCommentsReportsCenterLayer.add(CampaignComboBox);

		ExportButton = new JButton("EXPORT");
		ExportButton.setBounds(278, 71, 126, 34);
		ContactFrame.getContentPane().add(ExportButton);
		ExportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCommentsReportsCenterLayer.add(ExportButton);
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CommentsForMarketingCampaignReport --------*/
		
		CommentsReportScrollPane.setBounds(38, 133, 600, 200);
		CreateCommentsReportsCenterLayer.add(CommentsReportScrollPane);
		
		CommentsReportScrollPane.setViewportView(CommentsReportTable);
		CommentsReportTable.setRowHeight(23);
		CommentsReportTable.setFillsViewportHeight(true);
		CommentsReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/* ------- Adding CreateCustomerReportsCenterLayer -------- */
		CreateCustomerReportsCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateCustomerReportsCenterLayer,"CustomerCharacterizationReport");
		CreateCustomerReportsCenterLayer.setOpaque(true);
		CreateCustomerReportsCenterLayer.setName("CustomerCharacterizationReport");
		
		/*------- add dated and buttons to CreateCustomerReportsCenterLayer--------*/
		
		StartDateChooser = new JDateChooser();
		StartDateChooser.setBounds(107, 35, 140, 36);
		CreateCustomerReportsCenterLayer.add(StartDateChooser);
		
		EndDateChooser = new JDateChooser();
		EndDateChooser.setBounds(362, 35, 145, 34);
		CreateCustomerReportsCenterLayer.add(EndDateChooser);

		StartDateLabel = new JLabel("Start Date");
		StartDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartDateLabel.setBounds(20, 35, 98, 34);
		CreateCustomerReportsCenterLayer.add(StartDateLabel);
		
		EndDateLabel = new JLabel("End Date");
		EndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		EndDateLabel.setBounds(268, 40, 84, 26);
		CreateCustomerReportsCenterLayer.add(EndDateLabel);
		
		ExportButton2 = new JButton("EXPORT");
		ExportButton2.setBounds(584, 35, 126, 34);
		ContactFrame.getContentPane().add(ExportButton2);
		ExportButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCustomerReportsCenterLayer.add(ExportButton2);
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CustomerCharacterizationReportButton --------*/
		CustomerCharacterizationReportScrollPane.setBounds(38, 133, 600, 200);
		CreateCustomerReportsCenterLayer.add(CustomerCharacterizationReportScrollPane);
		
		CustomerCharacterizationReportScrollPane.setViewportView(CustomerCharacterizationReportTable);
		CustomerCharacterizationReportTable.setRowHeight(23);
		CustomerCharacterizationReportTable.setFillsViewportHeight(true);
		CustomerCharacterizationReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CustomerCharacterizationReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CustomerCharacterizationReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ------- Adding ActivateSale center layer -------- */
		ActivateSaleCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
	    AllActiveCampaignLabel = new JLabel("All Active Campaign");
		AllActiveCampaignLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AllActiveCampaignLabel.setBounds(38, 33, 312, 49);
		
		ActivateSaleCenterLayer.add(AllActiveCampaignLabel);
//		/*------- Create JTable surround with scroll pane and add
//		 * 				 it to CommentsForMarketingCampaignReport --------*/
//		
		ActiveSalesScrollPane.setBounds(38, 133, 600, 200);
		ActivateSaleCenterLayer.add(ActiveSalesScrollPane);
		
		ActiveSalesScrollPane.setViewportView(ActiveSalesTable);
		ActiveSalesTable.setRowHeight(23);
		ActiveSalesTable.setFillsViewportHeight(true);
		ActiveSalesTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ActiveSalesTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		ActiveSalesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		CenterCardContainer.add(ActivateSaleCenterLayer,"ActivateSaleCenterLayer");
		ActivateSaleCenterLayer.setOpaque(true);
		ActivateSaleCenterLayer.setName("ActivateSaleCenterLayer");

		
		/* ------- Adding ActivateSale left layer -------- */
		ActivateSaleLeftLayer.setBackground(new Color(169, 169, 169));
		ActivateSaleLeftLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		ActivateSale_StartDateChooser = new JDateChooser();
		ActivateSale_StartDateChooser.setBounds(134, 175, 140, 36);
		ActivateSaleLeftLayer.add(ActivateSale_StartDateChooser);
		
		ActivateSale_EndDateChooser = new JDateChooser();
		ActivateSale_EndDateChooser.setBounds(134, 231, 140, 34);
		ActivateSaleLeftLayer.add(ActivateSale_EndDateChooser);

		ActivateSale_StartDateLabel = new JLabel("Start Date");
		ActivateSale_StartDateLabel.setBounds(26, 175, 98, 34);
		ActivateSale_StartDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ActivateSaleLeftLayer.add(ActivateSale_StartDateLabel);
		
		ActivateSale_EndDateLabel = new JLabel("End Date");
		ActivateSale_EndDateLabel.setBounds(26, 231, 84, 26);
		ActivateSale_EndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ActivateSaleLeftLayer.add(ActivateSale_EndDateLabel);

		SelectPatternLabel = new JLabel("Select Pattern");
		SelectPatternLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SelectPatternLabel.setBounds(69, 33, 151, 26);
		ActivateSaleLeftLayer.add(SelectPatternLabel);

	    PatternsComboBox = new JComboBox();
		PatternsComboBox.setBounds(43, 70, 194, 36);
		ActivateSaleLeftLayer.add(PatternsComboBox);

	    StartSaleButton = new JButton("Start");
		StartSaleButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartSaleButton.setBounds(87, 291, 98, 36);
		ActivateSaleLeftLayer.add(StartSaleButton);

		LeftCardContainer.add(ActivateSaleLeftLayer,"ActivateSaleLeftLayer");
		ActivateSaleLeftLayer.setOpaque(true);
		ActivateSaleLeftLayer.setName("ActivateSaleLeftLayer");
		
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
////CommentsForMarketingCampaign
	public JButton getCommentsForMarketingCampaignButton(){
		return CommentsForMarketingCampaignButton;
	}
	public JButton getExportButton(){
		return ExportButton;
	}
	public String getComboBoxSelection(){
		return (String)CampaignComboBox.getSelectedItem();
	}
	
	public void setCommentsForMarketingCampaignTable(TableModel NewTable){
		CommentsReportTable.setModel(NewTable);	
	}
///CustomerCharacterization
	public JButton getCustomerCharacterizationReportButton(){
		return CustomerCharacterizationReportButton;
	}
	public JButton getExport2Button(){
		return ExportButton2;
	}
	public String getStartDate(){
		return StartDateChooser.getDateFormatString();// (new SimpleDateFormat("yyyy-mm-dd"));
	}
	public String getEndDate(){
		return EndDateChooser.getDateFormatString();// (new SimpleDateFormat("yyyy-mm-dd"));
	}
	public void setCustomerCharacterizationReportTable(TableModel NewTable){
		CustomerCharacterizationReportTable.setModel(NewTable);	
	}
	//campaign sale
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}



}


