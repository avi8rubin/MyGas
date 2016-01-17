/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/

package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import callback.callbackStringArray;
import callback.callbackUser;
import client.Client;
import common.JTableToExcel;

/**
 * Marketing Manager GUI
 * @author Litaf
 */
public class MarketingManagerGUI extends abstractPanel_GUI{

	/**
	 * Marketing Manager GUI Components  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * top buttons
	 */
	private JButton TariffButton;
	private JButton ReportButton;
	private JButton ActivateSaleCampaignButton;
	private JLabel HeadlineLabel=new JLabel("");
	/**
	 * TariffDisplayLayer- create Tariff layer components
	 */
	private JLayeredPane TariffDisplayLayer= new JLayeredPane();
	private JScrollPane TariffScrollPane = new JScrollPane();
	private JTable TariffUpdateTable = new JTable(){
		private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) { 
        	switch(column){
        	default:
                return false;
        	}};};
	private JButton UpdateButton;
	private JLabel ChooseNewTariffLabel;
	private JComboBox FuelscomboBox;
	private JTextArea NewFuelTariffTextArea;
	private JLabel TariffErrorLabel;
	/**
	 * CreateCommentsReportsCenterLayer- create center layer components
	 * CreateReportsLeftLayer- create report and center layer components
	 */
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
	/**
	 * CreateCustomerReportsCenterLayer-create the Customer Characterization Report 
	 * layer components 
	 */
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
	private JButton produceButton;
	private JDateChooser StartDateChooser;
	private JDateChooser EndDateChooser;
	private JLabel StartDateLabel;
	private JLabel EndDateLabel;
	/**
	 * ActivateSaleLeftLayer- create  Activate Sale center layer components
	 * ActivateSaleLeftLayer- create  Activate Sale left layer components
	 *
	 */
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
/**
 * export to excel classes
 */
	private JTableToExcel CustomerReportToExcel;
	private JTableToExcel CommentsReportToExcel;

	
	public MarketingManagerGUI(callbackUser EnteredUser, Client Server, 
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, LoginScreen);

		/**
		 *  Adding TariffButton to Top Panel
		 */
		TariffButton= new JButton("Tariff Update");
		TariffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TariffButton.setBounds(12, 99, 185, 38);
		TopPanel.add(TariffButton);
		
		/**
		 *  Adding ReportButton to Top Panel
		 */		ReportButton = new JButton("Create Reports");
		ReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportButton.setBounds(217, 99, 195, 38);
		TopPanel.add(ReportButton);
		
		/**
		 *  Adding ActivateSaleCampaignButton to Top Panel
		 */
		ActivateSaleCampaignButton = new JButton("Activate Sale Campaign");
		ActivateSaleCampaignButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ActivateSaleCampaignButton.setBounds(434, 99,252, 38);
		TopPanel.add(ActivateSaleCampaignButton);
		

		/**
		 * Adding new layer -TariffDisplayLayer to Tariff Panel 
		 */
		TariffDisplayLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(TariffDisplayLayer, "Tariff");
		TariffDisplayLayer.setOpaque(true);
		TariffDisplayLayer.setName("Tariff");
		
		/**
		 * Create JTable-TariffUpdateTable surround with TariffScrollPane and add
		 * it to TariffApprovalPanel
		 */
		TariffScrollPane.setBounds(43, 58,  900, 300);
		TariffDisplayLayer.add(TariffScrollPane);
		
		TariffScrollPane.setViewportView(TariffUpdateTable);
		TariffUpdateTable.setRowHeight(23);
		TariffUpdateTable.setFillsViewportHeight(true);
		TariffUpdateTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffUpdateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TariffUpdateTable.getTableHeader().setReorderingAllowed(false);

		/**
		 * Adding components to Tariff Panel:
		 * ChooseNewTariffLabel
		 * NewFuelTariffTextArea
		 * FuelscomboBox
		 * UpdateButton
		 * HeadlineLabel
		 * TariffErrorLabel
		 */	
		ChooseNewTariffLabel = new JLabel("Choose a new tariff:");
		ChooseNewTariffLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ChooseNewTariffLabel.setBounds(43, 394, 204, 28);
		TariffDisplayLayer.add(ChooseNewTariffLabel);
		
		FuelscomboBox = new JComboBox();
		FuelscomboBox.setToolTipText("Choose a fuel type");
		FuelscomboBox.setBounds(43, 432, 193, 28);
		TariffDisplayLayer.add(FuelscomboBox);
		
		NewFuelTariffTextArea = new JTextArea();
		NewFuelTariffTextArea.setToolTipText("Choose a new price");
		NewFuelTariffTextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		NewFuelTariffTextArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		NewFuelTariffTextArea.setBounds(266, 432, 75, 28);
		TariffDisplayLayer.add(NewFuelTariffTextArea);

		UpdateButton = new JButton("Update");
		UpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateButton.setBounds(818, 483, 125, 33);
		TariffDisplayLayer.add(UpdateButton);	
		
		HeadlineLabel.setText("Fuels Tariff");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		TariffDisplayLayer.add(HeadlineLabel);
		
		TariffErrorLabel = new JLabel("");
		TariffErrorLabel.setBounds(351, 431, 209, 66);
		TariffErrorLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		TariffErrorLabel.setText("");
		TariffErrorLabel.setForeground(Color.RED);
		TariffDisplayLayer.add(TariffErrorLabel);


		/**
		 * Adding new left layer -CreateReportsLeftLayer to reports Panel 
		 */		
		CreateReportsLeftLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateReportsLeftLayer.setBackground(new Color(169, 169, 169));
		
		//add to left container
		LeftCardContainer.add(CreateReportsLeftLayer, "ReportsLeft");
		CreateReportsLeftLayer.setOpaque(true);
		CreateReportsLeftLayer.setName("ReportsLeft");

		/**
		 * buttons left layer: CommentsForMarketingCampaignButton, CustomerCharacterizationReportButton
		 */
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
		
		/**
		 * adding the logo to the left layer
		 */
        JLabel LogoImageCreateReports = new JLabel("");
        LogoImageCreateReports.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
        LogoImageCreateReports.setBounds(38, 313, 239, 242);
        CreateReportsLeftLayer.add(LogoImageCreateReports);

        
        /**
         * Adding CreateCommentsReportsCenterLayer layer
         */
		CreateCommentsReportsCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateCommentsReportsCenterLayer,"CommentsForMarketingCampaignReport");
		CreateCommentsReportsCenterLayer.setOpaque(true);
		CreateCommentsReportsCenterLayer.setName("CommentsForMarketingCampaignReport");

		/**
		 * Adding components to CreateCommentsReportsCenterLayer the components:
		 * ChooseCampaignLabel
		 * CampaignComboBox
		 * ExportButton
		 * HeadlineLabel
		 * CommentsReportTable
		 */	
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
		
		HeadlineLabel = new JLabel("Comments For Marketing Campaign Report");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		CreateCommentsReportsCenterLayer.add(HeadlineLabel);

		CommentsReportScrollPane.setBounds(38, 125,  900, 400);
		CreateCommentsReportsCenterLayer.add(CommentsReportScrollPane);
		
		CommentsReportScrollPane.setViewportView(CommentsReportTable);
		CommentsReportTable.setRowHeight(23);
		CommentsReportTable.setFillsViewportHeight(true);
		CommentsReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CommentsReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		CommentsReportTable.getTableHeader().setReorderingAllowed(false);

		
		/**
		 * Adding CreateCustomerReportsCenterLayer
		 */
		CreateCustomerReportsCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateCustomerReportsCenterLayer,"CustomerCharacterizationReport");
		CreateCustomerReportsCenterLayer.setOpaque(true);
		CreateCustomerReportsCenterLayer.setName("CustomerCharacterizationReport");
	
		
		/**
		 * Adding components to Customer Reports Center Layer:
		 * HeadlineLabel
		 * StartDateChooser
		 * EndDateChooser
		 * StartDateLabel
		 * EndDateLabel
		 * ExportButton2
		 * produceButton
		 * Datelabel
		 * CustomerCharacterizationReportTable
		 */			
		HeadlineLabel = new JLabel("Customer Characterization By Period Report");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);
		CreateCustomerReportsCenterLayer.add(HeadlineLabel);
		
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
		
		produceButton = new JButton("Produce ");
		produceButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		produceButton.setBounds(676, 80, 126, 34);
		CreateCustomerReportsCenterLayer.add(produceButton);

		//incorrect date
		Datelabel.setForeground(Color.RED);
		Datelabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Datelabel.setBounds(534, 76, 145, 38);
		CreateCustomerReportsCenterLayer.add(Datelabel);
 
		CustomerCharacterizationReportScrollPane.setBounds(38, 125,  900, 400);
		CreateCustomerReportsCenterLayer.add(CustomerCharacterizationReportScrollPane);
	
		CustomerCharacterizationReportScrollPane.setViewportView(CustomerCharacterizationReportTable);
		CustomerCharacterizationReportTable.setRowHeight(23);
		CustomerCharacterizationReportTable.setFillsViewportHeight(true);
		CustomerCharacterizationReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CustomerCharacterizationReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CustomerCharacterizationReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		CustomerCharacterizationReportTable.getTableHeader().setReorderingAllowed(false);

		/**
		 * Adding ActivateSaleCenterLayer layer
		 */
		ActivateSaleCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		/**
		 * Adding components to Activate Sale Center Layer:
		 * HeadlineLabel
		 * ActiveSalesTable
		 */	
		HeadlineLabel = new JLabel("All Active Campaigns");				
		HeadlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		HeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		HeadlineLabel.setBounds(177, 13, 608, 42);		
		ActivateSaleCenterLayer.add(HeadlineLabel);
 
		ActiveSalesScrollPane.setBounds(43, 58,  900, 400);
		ActivateSaleCenterLayer.add(ActiveSalesScrollPane);
		
		ActiveSalesScrollPane.setViewportView(ActiveSalesTable);
		ActiveSalesTable.setRowHeight(23);
		ActiveSalesTable.setFillsViewportHeight(true);
		ActiveSalesTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ActiveSalesTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		ActiveSalesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		ActiveSalesTable.getTableHeader().setReorderingAllowed(false);

		
		CenterCardContainer.add(ActivateSaleCenterLayer,"ActivateSaleCenterLayer");
		ActivateSaleCenterLayer.setOpaque(true);
		ActivateSaleCenterLayer.setName("ActivateSaleCenterLayer");

		/**
		 * Adding components to Activate Sale Left Layer
		 * ActivateSale_StartDateChooser
		 * ActivateSale_EndDateChooser
		 * ActivateSale_StartDateLabel
		 * ActivateSale_EndDateLabel
		 * SelectPatternLabel
		 * PatternsComboBox
		 * StartSaleButton
		 * Datelabel2
		 */		
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
		
		LeftCardContainer.add(ActivateSaleLeftLayer,"ActivateSaleLeftLayer");
		ActivateSaleLeftLayer.setOpaque(true);
		ActivateSaleLeftLayer.setName("ActivateSaleLeftLayer");
		
		/**
		 * CustomerReportToExcel and CommentsReportToExcel are the two new objects from 
		 * JTableToExcel class there are responsible to export jtable to excel
		 */
		CustomerReportToExcel=new JTableToExcel(ExportButton2, CustomerCharacterizationReportTable);
		CommentsReportToExcel=new JTableToExcel(ExportButton, CommentsReportTable);
		
}

	//Tariff	
	public JButton getTariffButton(){
		return TariffButton;
	}
	public JButton getUpdateButton(){
		return UpdateButton;
	}
	public void setTariffErrorLabel(String str){
		TariffErrorLabel.setText(str);
	}
	public JLayeredPane getTariffDisplayLayer(){
		return TariffDisplayLayer;
	}
	
	public JTable getTariffUpdateTable(){
		return TariffUpdateTable;
	}
	/**
	 * setTariffUpdateTable:
	 * 1) add the table model that was received from the DB to the TariffUpdateTable
	 * 2) update the FuelscomboBox with the fuel types
	 * @param NewTableModel - Table came from server
	 */
	public void setTariffUpdateTable(DefaultTableModel NewTableModel)
	{		
		TariffUpdateTable.setModel(NewTableModel);
	    
		//center the table
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TariffUpdateTable.setDefaultRenderer(Object.class, CenterRenderer);
		
		
		Object[]fuels=new Object[TariffUpdateTable.getRowCount()];
		String FuelName;
		for(int i=0; i<TariffUpdateTable.getRowCount();i++){
			FuelName = TariffUpdateTable.getValueAt(i, 1).toString();
			fuels[i]=FuelName;
		}
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(fuels);
		FuelscomboBox.setModel(combopatternModel);
	}
	
	public String getFuelComboBoxSelection(){
		return (String)FuelscomboBox.getSelectedItem();
	}
	public String getFuelUpdateFromTextArea(){
		return NewFuelTariffTextArea.getText();
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
	/**
	 * SetComboBoxSelection- set the CampaignComboBox with the patterns that received from the DB
	 * @param CampaignPatterns - Create ComboBox and set default choice
	 */
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
	/**
	 * getPatternInt- return the row number of the pattern that was 
	 * chosen in the CampaignComboBox for the filter of the CampaignTable
	 * @param pattern - Pattern description
	 * @return row number
	 */
	public int getPatternInt(String pattern)
	{
		int row=0;
		for(int i=0;i<CampaignComboBox.getItemCount();i++){
			if(CampaignComboBox.getItemAt(i).toString().equals(pattern))
				row=i;
		}
		return row;
	}
	/**
	 * setCommentsForMarketingCampaignTable- add the table model 
	 * that was received from the DB to the CommentsReportTable
	 * @param NewTable - Table back from server
	 */
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
	public JButton getproduceButton() {
		return produceButton;	
	}
	/**
	 * setCustomerCharacterizationReportTable- add the table model 
	 * that was received from the DB to the CustomerCharacterizationReportTable
	 * @param NewTable - Table back from server
	 */
	public void setCustomerCharacterizationReportTable(DefaultTableModel NewTable){
		CustomerCharacterizationReportTable.setModel(NewTable);	
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		CustomerCharacterizationReportTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	public JTable getCustomerCharacterizationReportTable(){
		return CustomerCharacterizationReportTable;
	}
	//activate campaign sale
	public JLabel getErrorDateLabel2(){
		return Datelabel2;
	}
	public JButton getActivateSaleCampaignButton(){
		return ActivateSaleCampaignButton;
	}
	
	/**
	 * setActiveSalesTable- add the table model 
	 * that was received from the DB to the ActiveSalesTable
	 * @param NewTable - Table back from server
	 */
	public void setActiveSalesTable(DefaultTableModel NewTable){
		ActiveSalesTable.setModel(NewTable);	
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ActiveSalesTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
/**
 * SetComboBoxPattern- set the PatternsComboBox with the patterns that was received from the DB
 * @param CampaignPatterns - Create comboBox of all patterns
 */
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
	/**
	 * getSalePatternId- compare the patternChoosen string to the PatternWithID table and return 
	 * the chosen pattern id in order to activate a new sale in the DB
	 * @param patternChoosen - pattern that Chosen
	 * @param PatternWithID - pattern id
	 * @return Pattern With ID
	 */
	public Object getSalePatternId(String patternChoosen,Object[][] PatternWithID){
	int RowNum=0;
	for(int i=0; i<PatternWithID.length;i++){
		if(PatternWithID[i][1].equals(patternChoosen))
			RowNum=i;
		}	
	return PatternWithID[RowNum][0];
	}
	
	
}//class
