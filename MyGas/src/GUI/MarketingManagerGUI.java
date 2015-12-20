package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import callback.callbackBuffer;
import callback.callbackStringArray;
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
		TariffButton.setBounds(12, 99, 185, 38);
		TopPanel.add(TariffButton);
		
		/* ------- Adding new button to Top Panel -------- */
		ReportButton = new JButton("Create Reports");
		ReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportButton.setBounds(217, 99, 195, 38);
		TopPanel.add(ReportButton);
		
		/* ------- Adding new button to Top Panel -------- */
		ActivateSaleCampaignButton = new JButton("Activate Sale Campaign");
		ActivateSaleCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ActivateSaleCampaignButton.setBounds(434, 99,252, 38);
		TopPanel.add(ActivateSaleCampaignButton);
		


////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/* ------- Adding new layer to Tariff Panel -------- */
		TariffDisplayLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(TariffDisplayLayer, "Tariff");
		TariffDisplayLayer.setOpaque(true);
		TariffDisplayLayer.setName("Tariff");
		
		/*------- Create JTable surround with scroll pane and add
		 * 					 it to TariffApprovalPanel --------*/
		TariffScrollPane.setBounds(43, 58,  900, 400);
		TariffDisplayLayer.add(TariffScrollPane);
		
		TariffScrollPane.setViewportView(TariffUpdateTable);
		TariffUpdateTable.setRowHeight(23);
		TariffUpdateTable.setFillsViewportHeight(true);
		TariffUpdateTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		/* ------- Adding button and label to Tariff Panel -------- */
		JLabel FuelsTariffLabel = new JLabel("Fuels Tariff");
		FuelsTariffLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		FuelsTariffLabel.setBounds(400, 25, 132, 19);
		TariffDisplayLayer.add(FuelsTariffLabel);
		
		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateButton.setBounds(818, 483, 125, 33);
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
		CommentsForMarketingCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CommentsForMarketingCampaignButton.setBounds(27, 33, 212, 76);

		
		CustomerCharacterizationReportButton = 
				new JButton("<html>Customer characterization by period report</html>");
		CustomerCharacterizationReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerCharacterizationReportButton.setBounds(27, 164, 212, 76);
		
		CreateReportsLeftLayer.add(CommentsForMarketingCampaignButton);
		CreateReportsLeftLayer.add(CustomerCharacterizationReportButton);
		
        JLabel LogoImageCreateReports = new JLabel("");
        LogoImageCreateReports.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
        LogoImageCreateReports.setBounds(38, 313, 239, 242);
        CreateReportsLeftLayer.add(LogoImageCreateReports);
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
		ChooseCampaignLabel.setBounds(38, 35, 160, 34);
		ContactFrame.getContentPane().add(ChooseCampaignLabel);
		CreateCommentsReportsCenterLayer.add(ChooseCampaignLabel);
		
		CampaignComboBox = new JComboBox();
		CampaignComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CampaignComboBox.setBounds(180,35, 410, 34);
		ContactFrame.getContentPane().add(CampaignComboBox);
		CreateCommentsReportsCenterLayer.add(CampaignComboBox);

		ExportButton = new JButton("Export");
		ExportButton.setBounds(595, 35, 126, 34);
		ContactFrame.getContentPane().add(ExportButton);
		ExportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCommentsReportsCenterLayer.add(ExportButton);
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CommentsForMarketingCampaignReport --------*/

		CommentsReportScrollPane.setBounds(38, 110,  900, 400);
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
		StartDateChooser.setBounds(130, 35, 140, 36);
		CreateCustomerReportsCenterLayer.add(StartDateChooser);
		
		EndDateChooser = new JDateChooser();
		EndDateChooser.setBounds(372, 35, 145, 34);
		CreateCustomerReportsCenterLayer.add(EndDateChooser);

		StartDateLabel = new JLabel("Start Date:");
		StartDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartDateLabel.setBounds(38, 35, 98, 34);
		CreateCustomerReportsCenterLayer.add(StartDateLabel);
		
		EndDateLabel = new JLabel("End Date:");
		EndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		EndDateLabel.setBounds(283, 40, 84, 26);
		CreateCustomerReportsCenterLayer.add(EndDateLabel);
		
		ExportButton2 = new JButton("Export");
		ExportButton2.setBounds(595, 35, 126, 34);
		ContactFrame.getContentPane().add(ExportButton2);
		ExportButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCustomerReportsCenterLayer.add(ExportButton2);
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CustomerCharacterizationReportButton --------*/
		CustomerCharacterizationReportScrollPane.setBounds(38, 110, 900, 400);
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
		
	    AllActiveCampaignLabel = new JLabel("All Active Campaigns");
		AllActiveCampaignLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AllActiveCampaignLabel.setBounds(400, 25, 250, 25);
		
		ActivateSaleCenterLayer.add(AllActiveCampaignLabel);
//		/*------- Create JTable surround with scroll pane and add
//		 * 				 it to CommentsForMarketingCampaignReport --------*/
//		
		ActiveSalesScrollPane.setBounds(43, 58,  900, 400);
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
		ActivateSale_StartDateChooser.setBounds(130, 131, 140, 36);
		ActivateSaleLeftLayer.add(ActivateSale_StartDateChooser);
		
		ActivateSale_EndDateChooser = new JDateChooser();
		ActivateSale_EndDateChooser.setBounds(130, 178, 140, 34);
		ActivateSaleLeftLayer.add(ActivateSale_EndDateChooser);

		ActivateSale_StartDateLabel = new JLabel("Start Date:");
		ActivateSale_StartDateLabel.setBounds(22, 133, 98, 34);
		ActivateSale_StartDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ActivateSaleLeftLayer.add(ActivateSale_StartDateLabel);
		
		ActivateSale_EndDateLabel = new JLabel("End Date:");
		ActivateSale_EndDateLabel.setBounds(22, 178, 84, 26);
		ActivateSale_EndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ActivateSaleLeftLayer.add(ActivateSale_EndDateLabel);

		SelectPatternLabel = new JLabel("Select Pattern:");
		SelectPatternLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SelectPatternLabel.setBounds(69, 33, 151, 26);
		ActivateSaleLeftLayer.add(SelectPatternLabel);

	    PatternsComboBox = new JComboBox();
		PatternsComboBox.setBounds(44, 67, 194, 36);
		ActivateSaleLeftLayer.add(PatternsComboBox);

	    StartSaleButton = new JButton("Start");
		StartSaleButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartSaleButton.setBounds(88, 279, 98, 36);
		ActivateSaleLeftLayer.add(StartSaleButton);

		//////////////logo////////////////////////

        JLabel LogoImageActivateSale = new JLabel("");
        LogoImageActivateSale.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
        LogoImageActivateSale.setBounds(38, 313, 239, 242);
        ActivateSaleLeftLayer.add(LogoImageActivateSale);
		
		//ActivateSaleLeftLayer.add(LogoImage);

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
	public void setTariffUpdateTable(DefaultTableModel NewTableModel)
	{
		TariffUpdateTable.setModel(NewTableModel);

	}
	//reports
	public JButton getReportButton(){
		return ReportButton;
	}
	//CommentsForMarketingCampaign
	public JButton getCommentsForMarketingCampaignButton(){
		return CommentsForMarketingCampaignButton;
	}
	public JButton getExportButton(){
		return ExportButton;
	}
	public String getComboBoxSelection(){
		return (String)CampaignComboBox.getSelectedItem();
	}
	
	public void SetComboBoxSelection(callbackStringArray CampaignPatterns){
		Object[]pattern=CampaignPatterns.getComboBoxStringArray();
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		CampaignComboBox.setModel(combopatternModel);
		CampaignComboBox.addItem("All Campaigns");		
	}
	public JComboBox<?> getComboBox(){
		return CampaignComboBox;
	}
	
	public void setCommentsForMarketingCampaignTable(DefaultTableModel NewTable, String RowDetails){
		
		Object selectedObject;
		int rowToShow=0, numberOfRows=NewTable.getRowCount();
		CommentsReportTable.setModel(NewTable);
		
		if(!RowDetails.equals("All Campaigns")){	
			for(int i=0; i<numberOfRows;i++){
				selectedObject = (Object)NewTable.getValueAt(i, 3);
				if(RowDetails.equals(selectedObject))
					rowToShow=i;
				}
			for(int i=0;i<NewTable.getRowCount();i++){
				if(i!=rowToShow)
				NewTable.removeRow(i);
				}
			}
		 CommentsReportTable.setModel(NewTable);

	}
	//CustomerCharacterization
	public JButton getCustomerCharacterizationReportButton(){
		return CustomerCharacterizationReportButton;
	}
	public JButton getExport2Button(){
		return ExportButton2;
	}
	public String getStartDate(){
		return ((JTextField)StartDateChooser.getDateEditor().getUiComponent()).getText();
	}
	public String getEndDate(){
		return  ((JTextField)EndDateChooser.getDateEditor().getUiComponent()).getText();
	}
	public void setCustomerCharacterizationReportTable(DefaultTableModel NewTable){
		CustomerCharacterizationReportTable.setModel(NewTable);	
	}
	//campaign sale
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}

	public void SetComboBoxPattern(callbackStringArray CampaignPatterns){
		Object[]pattern=CampaignPatterns.getComboBoxStringArray();
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		PatternsComboBox.setModel(combopatternModel);

	}
	public JButton getStartSaleButton(){
		return StartSaleButton;
	}
	public String getComboBoxPattern(){
		return (String)PatternsComboBox.getSelectedItem();
	}
	public String getStartSaleDate(){
		return ((JTextField)ActivateSale_StartDateChooser.getDateEditor().getUiComponent()).getText();
	}
	public String getEndSaleDate(){
		return  ((JTextField)ActivateSale_EndDateChooser.getDateEditor().getUiComponent()).getText();
	}
}
