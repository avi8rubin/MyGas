package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import callback.callbackBuffer;
import callback.callbackStringArray;
import callback.callbackUser;
import client.Client;
import common.JTableToExcel;
import common.TableModel;

public class MarketingManagerGUI extends abstractPanel_GUI{

	/**
	 * Marketing Manager GUI Components  
	 */
	private static final long serialVersionUID = 1L;
	//top buttons
	private JButton TariffButton;
	private JButton ReportButton;
	private JButton ActivateSaleCampaignButton;
	private JLabel HeadlineLabel=new JLabel("");
	//TariffButton
	private JLayeredPane TariffDisplayLayer= new JLayeredPane();
	private JScrollPane TariffScrollPane = new JScrollPane();
	private JTable TariffUpdateTable = new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) { 
        	switch(column){
        	case 3:
                return true; 
        	default:
                return false;
        	}};};
	private JButton UpdateButton;
	//ReportButton
	private JButton CustomerCharacterizationReportButton;
	private JButton CommentsForMarketingCampaignButton;
	private JLayeredPane CreateReportsLeftLayer= new JLayeredPane();
	//Comments For Marketing Campaign Report
	private JLayeredPane CreateCommentsReportsCenterLayer= new JLayeredPane();
	private JScrollPane CommentsReportScrollPane = new JScrollPane();
	private JTable CommentsReportTable = new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) { 
        	switch(column){
        	default:
                return false;
        	}};};
	private JLabel ChooseCampaignLabel;
	private JComboBox CampaignComboBox ;
	private JButton ExportButton;
	//Customer Characterization Report
	private JLabel Datelabel=new JLabel("");
	private JLayeredPane CreateCustomerReportsCenterLayer= new JLayeredPane(); ;
	private JScrollPane CustomerCharacterizationReportScrollPane = new JScrollPane();
	private JTable CustomerCharacterizationReportTable= new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) { 
        	switch(column){
        	default:
                return false;
        	}};};
	private JButton ExportButton2;
	private JDateChooser StartDateChooser;
	private JDateChooser EndDateChooser;
	private JLabel StartDateLabel;
	private JLabel EndDateLabel;
	//Activate Sale
	private JLabel Datelabel2=new JLabel("");
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
	private JTable ActiveSalesTable= new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) { 
        	switch(column){
        	default:
                return false;
        	}};};
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
		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateButton.setBounds(818, 483, 125, 33);
		TariffDisplayLayer.add(UpdateButton);	
		
		HeadlineLabel.setText("Fuels Tariff");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		TariffDisplayLayer.add(HeadlineLabel);
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
				new JButton("<html>Comments For Marketing Campaign Report</html>");
		CommentsForMarketingCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CommentsForMarketingCampaignButton.setBounds(27, 33, 212, 76);

		
		CustomerCharacterizationReportButton = 
				new JButton("<html>Customer Characterization By Period Report</html>");
		CustomerCharacterizationReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerCharacterizationReportButton.setBounds(27, 164, 212, 76);
		
		CreateReportsLeftLayer.add(CommentsForMarketingCampaignButton);
		CreateReportsLeftLayer.add(CustomerCharacterizationReportButton);
		
        JLabel LogoImageCreateReports = new JLabel("");
        LogoImageCreateReports.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
        LogoImageCreateReports.setBounds(38, 313, 239, 242);
        CreateReportsLeftLayer.add(LogoImageCreateReports);
 ///////////////////////////////////////////////////////////////////////////////////////////
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
		ChooseCampaignLabel.setBounds(38, 70, 160, 34);
		ContactFrame.getContentPane().add(ChooseCampaignLabel);
		CreateCommentsReportsCenterLayer.add(ChooseCampaignLabel);
		
		CampaignComboBox = new JComboBox();
		CampaignComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CampaignComboBox.setBounds(187,72, 410, 34);
		ContactFrame.getContentPane().add(CampaignComboBox);
		CreateCommentsReportsCenterLayer.add(CampaignComboBox);

		ExportButton = new JButton("Export");
		ExportButton.setBounds(647, 69, 126, 34);
		ContactFrame.getContentPane().add(ExportButton);
		ExportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCommentsReportsCenterLayer.add(ExportButton);
		
		/* ------- Adding label to comments report Panel -------- */		

		HeadlineLabel = new JLabel("Comments For Marketing Campaign Report");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		CreateCommentsReportsCenterLayer.add(HeadlineLabel);
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CommentsForMarketingCampaignReport --------*/

		CommentsReportScrollPane.setBounds(38, 125,  900, 400);
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
		
		/* ------- Adding label to customer report Panel -------- */		

		HeadlineLabel = new JLabel("Customer Characterization By Period Report");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		CreateCustomerReportsCenterLayer.add(HeadlineLabel);
		
		/*------- add dated and buttons to CreateCustomerReportsCenterLayer--------*/
		
		StartDateChooser = new JDateChooser();
		StartDateChooser.setBounds(131, 78, 140, 36);
		StartDateChooser.setDateFormatString("dd/MM/yy");
		StartDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CreateCustomerReportsCenterLayer.add(StartDateChooser);
		
		EndDateChooser = new JDateChooser();
		EndDateChooser.setDateFormatString("dd/MM/yy");
		EndDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		EndDateChooser.setBounds(389, 80, 145, 34);
		CreateCustomerReportsCenterLayer.add(EndDateChooser);

		StartDateLabel = new JLabel("Start Date:");
		StartDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartDateLabel.setBounds(38, 80, 98, 34);
		CreateCustomerReportsCenterLayer.add(StartDateLabel);
		
		EndDateLabel = new JLabel("End Date:");
		EndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		EndDateLabel.setBounds(305, 84, 84, 26);
		CreateCustomerReportsCenterLayer.add(EndDateLabel);
		
		ExportButton2 = new JButton("Export");
		ExportButton2.setBounds(812, 80, 126, 34);
		ContactFrame.getContentPane().add(ExportButton2);
		ExportButton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateCustomerReportsCenterLayer.add(ExportButton2);
		
		//incorrect date
		Datelabel.setForeground(Color.RED);
		Datelabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Datelabel.setBounds(558, 76, 145, 38);	
		CreateCustomerReportsCenterLayer.add(Datelabel);

		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CustomerCharacterizationReportButton --------*/
		CustomerCharacterizationReportScrollPane.setBounds(38, 125,  900, 400);
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

		/* ------- Adding label to customer report Panel -------- */		

		HeadlineLabel = new JLabel("All Active Campaigns");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);		
		ActivateSaleCenterLayer.add(HeadlineLabel);
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
		ActivateSale_StartDateChooser.setDateFormatString("dd/MM/yy");
		ActivateSale_StartDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ActivateSale_StartDateChooser.setBounds(130, 131, 140, 36);
		ActivateSaleLeftLayer.add(ActivateSale_StartDateChooser);
		
		ActivateSale_EndDateChooser = new JDateChooser();
		ActivateSale_EndDateChooser.setDateFormatString("dd/MM/yy");
		ActivateSale_EndDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		PatternsComboBox.setBounds(27, 67, 246, 36);
		ActivateSaleLeftLayer.add(PatternsComboBox);

	    StartSaleButton = new JButton("Start");
		StartSaleButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StartSaleButton.setBounds(88, 279, 98, 36);
		ActivateSaleLeftLayer.add(StartSaleButton);
		
		//incorrect date
		Datelabel2.setForeground(Color.RED);
		Datelabel2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Datelabel2.setBounds(130, 216, 146, 26);
		ActivateSaleLeftLayer.add(Datelabel2);


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
		//change last column to editable
		TableColumn col = TariffUpdateTable.getColumnModel().getColumn(3);
		//col.setCellEditor( new DefaultCellEditor( combo ) );

		//center the table
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TariffUpdateTable.setDefaultRenderer(Object.class, CenterRenderer);

	}
	//reports
	public JButton getReportButton(){
		return ReportButton;
	}
	//CommentsForMarketingCampaign
	public JTable getCommentsTable(){
		return CommentsReportTable;
	}
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
		CampaignComboBox.setSelectedItem("All Campaigns");						//Set that value as default
	}
	public JComboBox<?> getComboBox(){
		return CampaignComboBox;
	}
	
	public int getPatternInt(String pattern)
	{
		int row=0;
		for(int i=0;i<CampaignComboBox.getItemCount();i++){
			if(CampaignComboBox.getItemAt(i).toString().equals(pattern))
				row=i;
		}
		return row;
	}
	public void setCommentsForMarketingCampaignTable(DefaultTableModel NewTable){
		 CommentsReportTable.setModel(NewTable);
			DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
			CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			CommentsReportTable.setDefaultRenderer(Object.class, CenterRenderer);

	}
	//CustomerCharacterization
	public JLabel getErrorDateLabel(){
		return Datelabel;
	}
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
	public void setDates(){
		StartDateChooser.setCalendar(null);
		EndDateChooser.setCalendar(null);
	}
	
	public void setCustomerCharacterizationReportTable(DefaultTableModel NewTable){
		CustomerCharacterizationReportTable.setModel(NewTable);	
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		CustomerCharacterizationReportTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	//activate campaign sale
	public JLabel getErrorDateLabel2(){
		return Datelabel2;
	}
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}
	
	public void setActiveSalesTable(DefaultTableModel NewTable){
		ActiveSalesTable.setModel(NewTable);	
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ActiveSalesTable.setDefaultRenderer(Object.class, CenterRenderer);
	}

	public void SetComboBoxPattern(callbackStringArray CampaignPatterns){
		Object[]pattern=CampaignPatterns.getComboBoxStringArray();
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel<>(pattern);
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
	public void setActivateDates(){
		ActivateSale_StartDateChooser.setCalendar(null);
		ActivateSale_EndDateChooser.setCalendar(null);
	}
	
	public Object getSalePatternId(String patternChoosen,Object[][] PatternWithID){
	int RowNum=0;
	for(int i=0; i<PatternWithID.length;i++){
		if(PatternWithID[i][1].equals(patternChoosen))
			RowNum=i;
		}	
	return PatternWithID[RowNum][0];
	}
	
	
}//class
