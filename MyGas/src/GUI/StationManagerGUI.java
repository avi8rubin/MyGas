package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;
import common.GasStationBarChart;

public class StationManagerGUI extends abstractPanel_GUI{
	//Global values
	private static callbackUser ManagerUser;
	
	//Top screen
	private final JButton CreateReportButton;
	private final JButton ApproveSupplyButton;
	private final JButton UpdateLimitButton;
	
	//Approve Supply screen
	private JLayeredPane ApproveSupplyLayer;
	private final JButton UpdateApproveButton;
	private final JLabel UpdateMessage;
	
	//Update Limit Level For Fuel Type
	private JTable UpdateLimitTabel;
	private JLayeredPane UpdateLimitLevelFuelLayer;
	private JButton UpdateLimitLevelButton;
	private JLabel UpdateLimitTextError;
	
	// Create Report
	private JLayeredPane CreateReportLeftPanel;
	private JLayeredPane QuartelyRepotyScreen;
	private JLayeredPane PurchaseReportScreen;
	private JLayeredPane StockReportScreen;

	private JButton QuartelyRepotyButton;
	private JButton PurchaseReportButton;
	private JButton StockReportButton;
	//Quarterly report
	private JComboBox YearSelectInQuarterlyReport;
	private JButton QuartelyGenrateButton;
	private JComboBox QuarterlycomboBoxInQuarterlyReport;
	private JTable QuarterlyReportTable;
	private JLabel UpdateMessageQuartelyRepot;
	//Purchase Report
	private JComboBox QuarterlycomboBoxInPurchaseReport;
	private JComboBox YearSelectInPurchaseReport;
	private JButton PurchaseGenrateButton;
	private JTable PurchaseReportTable;
	private JLabel UpdateMessagePurchaseReport;
	//Stock Report
	private  JLayeredPane StockReportLayer;
	//private Float[] StockGraphFloatArray={0f,0f,0f,0f,0f,0f};
	private JPanel Stockpanel;
	// Left screen
	private final JLabel Error_Label;
	
	private static final long serialVersionUID = 1L;

	//  JTable - that the last column is editable.
	private JTable ApprovalSuppliesTable;
	/**
	 * Constracor
	 * @param EnteredUser
	 * @param Server
	 * @param CommonBuffer
	 * @param LoginScreen
	 */
	public StationManagerGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);
		ManagerUser=EnteredUser;
		/*-------------------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------Top Panel Buttons----------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------*/
		
		// "Approve Supplies Orders"
		ApproveSupplyButton = new JButton("Approve Supplies Orders");
		ApproveSupplyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ApproveSupplyButton.setBounds(12, 98, 279, 38);
		this.TopPanel.add(ApproveSupplyButton);
		
		// "Create Report"
		CreateReportButton = new JButton("<html>Create Reports</html>");
		CreateReportButton.setMargin(new Insets(0, 14, 0, 14));
		CreateReportButton.setHorizontalTextPosition(SwingConstants.CENTER);
		CreateReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateReportButton.setAlignmentY(0.0f);
		CreateReportButton.setBounds(303, 98, 189, 38);
		this.TopPanel.add(CreateReportButton);
		
		// "Update Limit Level"
		UpdateLimitButton = new JButton("<html>Update Limit Level For Fuel Type</html>");
		UpdateLimitButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateLimitButton.setBounds(504, 98, 365, 38);
		this.TopPanel.add(UpdateLimitButton);
		
		/*-------------------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------Approve Supply ------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------*/
		
		/*--------Create Table--------------------------------------------*/
		ApprovalSuppliesTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	if(column==4) return true;
	        	return false;
	        };
		};
		
		/*------- Create new center layer and add it to container --------*/
		ApproveSupplyLayer = new JLayeredPane();		//Global variable
		ApproveSupplyLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		ApproveSupplyLayer.setOpaque(true);
		ApproveSupplyLayer.setLayout(null);
		CenterCardContainer.add(ApproveSupplyLayer, "ApproveSupplyLayer");
		ApproveSupplyLayer.setName("ApproveSupplyLayer");
		
		/*------- Create JTable surround with scroll pane and add it to TariffApprovalPanel --------*/
		JScrollPane approvetableScroll = new JScrollPane();
		approvetableScroll.setBounds(40, 58, 900, 438);
		ApproveSupplyLayer.add(approvetableScroll);		
		
		approvetableScroll.setViewportView(ApprovalSuppliesTable);	
		ApprovalSuppliesTable.setRowHeight(23);
		ApprovalSuppliesTable.setFillsViewportHeight(true);
		ApprovalSuppliesTable.getTableHeader().setReorderingAllowed(false);
		
		ApprovalSuppliesTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ApprovalSuppliesTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		ApprovalSuppliesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/*------- Create new label on the new layer --------*/
		JLabel TariffApprovalLabel = new JLabel("Supplies Waiting For Approval");				
		TariffApprovalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TariffApprovalLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		TariffApprovalLabel.setBounds(177, 13, 608, 42);
		ApproveSupplyLayer.add(TariffApprovalLabel);
		
		/*---------Create Update Button---------------------*/
		UpdateApproveButton = new JButton("Update");
		UpdateApproveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateApproveButton.setBounds(840, 500, 112, 38);
		ApproveSupplyLayer.add(UpdateApproveButton);
		

		
		/*----------Message Label---------------------------*/
		UpdateMessage = new JLabel("");
		UpdateMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateMessage.setBounds(300, 500, 400, 38);
		UpdateMessage.setForeground(Color.RED);
		ApproveSupplyLayer.add(UpdateMessage);
		
		/*--------------------Center panel - Click "Update Level For Fuel Type"---------------------------*/
		
		
		/*------- Create new center layer and add it to container --------*/
		UpdateLimitLevelFuelLayer = new JLayeredPane();		//Global variable
		UpdateLimitLevelFuelLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(UpdateLimitLevelFuelLayer, "UpdateLimitLevelFuelLayer");
		UpdateLimitLevelFuelLayer.setOpaque(true);
		UpdateLimitLevelFuelLayer.setName("UpdateLimitLevelFuelLayer");
		
		/*------- Create new label on the new layer --------*/
		JLabel UpdateLabel = new JLabel("Update Threshold Limit");				
		UpdateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UpdateLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		UpdateLabel.setBounds(177, 13, 608, 42);
		UpdateLimitLevelFuelLayer.add(UpdateLabel);
		
		/*--------Create tabele---------------------------*/
		UpdateLimitTabel = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	switch(column){
	        	case 2:
	                return true; 
	        	default:
	                return false;
	        	}
	        };
		};
		
		/*------- Create JTable surround with scroll pane and add it to Update level Panel --------*/
		JScrollPane UpdateLimitTabelScroll = new JScrollPane();
		UpdateLimitTabelScroll.setBounds(40, 58, 911, 438);
		UpdateLimitLevelFuelLayer.add(UpdateLimitTabelScroll);		
		
		UpdateLimitTabelScroll.setViewportView(UpdateLimitTabel);		
		UpdateLimitTabel.setRowHeight(23);
		UpdateLimitTabel.setFillsViewportHeight(true);
		UpdateLimitTabel.getTableHeader().setReorderingAllowed(false);
		UpdateLimitTabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		UpdateLimitTabel.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		UpdateLimitTabel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		
		/*-----------------------------Update Button------------*/
		
		
		/*---------Create Update Button---------------------*/
		UpdateLimitLevelButton = new JButton("Update");
		UpdateLimitLevelButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateLimitLevelButton.setBounds(840, 500, 112, 38);
		UpdateLimitLevelFuelLayer.add(UpdateLimitLevelButton);
		
		/*---------Update Error text box-------------------*/
		
		UpdateLimitTextError = new JLabel(); 
		UpdateLimitTextError.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateLimitTextError.setBounds(300, 500, 400, 38);
		UpdateLimitTextError.setForeground(Color.RED);
		UpdateLimitLevelFuelLayer.add(UpdateLimitTextError);
		
		/*-------------------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------Quarterly Report------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------*/
		
		
		/*------- Create new center layer and add it to container --------*/
		QuartelyRepotyScreen = new JLayeredPane();		//Global variable
		QuartelyRepotyScreen.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(QuartelyRepotyScreen, "QuartelyRepotyScreen");
		QuartelyRepotyScreen.setOpaque(true);
		QuartelyRepotyScreen.setName("QuartelyRepotyScreen");
		
		//"Select Quarter:"
		JLabel lblSelectQuarter = new JLabel("Select Quarter:");
		lblSelectQuarter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectQuarter.setBounds(12, 13, 148, 25);
		QuartelyRepotyScreen.add(lblSelectQuarter);
		
		//"Select Year:"
		JLabel lblSelectYear = new JLabel("Select Year:");
		lblSelectYear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectYear.setBounds(247, 13, 148, 25);
		QuartelyRepotyScreen.add(lblSelectYear);
		
		//CoboBox 1,2,3,4
		QuarterlycomboBoxInQuarterlyReport = new JComboBox();
		QuarterlycomboBoxInQuarterlyReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		QuarterlycomboBoxInQuarterlyReport.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		QuarterlycomboBoxInQuarterlyReport.setBounds(181, 13, 39, 25);
		QuartelyRepotyScreen.add(QuarterlycomboBoxInQuarterlyReport);
		
		//ComboBox 2015,2014....
		YearSelectInQuarterlyReport = new JComboBox();
		YearSelectInQuarterlyReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		YearSelectInQuarterlyReport.setBounds(378, 14, 85, 22);
		QuartelyRepotyScreen.add(YearSelectInQuarterlyReport);
		contentPane.add(ContactFrame, BorderLayout.SOUTH);
		ContactFrame.setVisible(false);
		ContactFrame.getContentPane().setLayout(null);
		QuartelyRepotyScreen.setBounds(12, 13, 944, 593);
		
		//Generate Button
		QuartelyGenrateButton = new JButton("Generate");
		QuartelyGenrateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		QuartelyGenrateButton.setBounds(490, 13, 125, 25);
		QuartelyRepotyScreen.add(QuartelyGenrateButton);
		
		//Scroll Pane & Table
		
		/*--------Create Table--------------------------------------------*/
		QuarterlyReportTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	return false;
	        };
		};
		
		
		/*------- Create JTable surround with scroll pane and add it to TariffApprovalPanel --------*/
		JScrollPane QuarterlyReport = new JScrollPane();
		QuarterlyReport.setBounds(40, 58, 900, 438);
		QuartelyRepotyScreen.add(QuarterlyReport);		
		
		QuarterlyReport.setViewportView(QuarterlyReportTable);	
		QuarterlyReportTable.setRowHeight(23);
		QuarterlyReportTable.setFillsViewportHeight(true);
		
		QuarterlyReportTable.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		QuarterlyReportTable.setDefaultRenderer(Object.class, CenterRenderer);
		
		QuarterlyReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		QuarterlyReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		QuarterlyReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		
		/*----------Message Label---------------------------*/
		UpdateMessageQuartelyRepot = new JLabel("");
		UpdateMessageQuartelyRepot.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateMessageQuartelyRepot.setBounds(300, 500, 400, 38);
		UpdateMessageQuartelyRepot.setForeground(Color.RED);
		QuartelyRepotyScreen.add(UpdateMessageQuartelyRepot);
		
		/*-------------------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------Purchase Report------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------*/
		
		
		/*------- Create new center layer and add it to container --------*/
		PurchaseReportScreen = new JLayeredPane();		//Global variable
		PurchaseReportScreen.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(PurchaseReportScreen, "PurchaseReportScreen");
		PurchaseReportScreen.setOpaque(true);
		PurchaseReportScreen.setName("PurchaseReportScreen");
		
		
		//"Select Quarter:"
		JLabel lblSelectQuarter1 = new JLabel("Select Quarter:");
		lblSelectQuarter1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectQuarter1.setBounds(12, 13, 148, 25);
		PurchaseReportScreen.add(lblSelectQuarter1);
		
		//"Select Year:"
		JLabel lblSelectYear1 = new JLabel("Select Year:");
		lblSelectYear1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectYear1.setBounds(247, 13, 148, 25);
		PurchaseReportScreen.add(lblSelectYear1);
		
		//CoboBox 1,2,3,4
		QuarterlycomboBoxInPurchaseReport = new JComboBox();
		QuarterlycomboBoxInPurchaseReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		QuarterlycomboBoxInPurchaseReport.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		QuarterlycomboBoxInPurchaseReport.setBounds(181, 13, 39, 25);
		PurchaseReportScreen.add(QuarterlycomboBoxInPurchaseReport);
		
		
		
		//ComboBox 2015,2014....
		YearSelectInPurchaseReport = new JComboBox();
		YearSelectInPurchaseReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		YearSelectInPurchaseReport.setBounds(378, 14, 85, 22);
		PurchaseReportScreen.add(YearSelectInPurchaseReport);
		contentPane.add(ContactFrame, BorderLayout.SOUTH);
		ContactFrame.setVisible(false);
		ContactFrame.getContentPane().setLayout(null);
		PurchaseReportScreen.setBounds(12, 13, 944, 593);
		
		//Generate Button
		PurchaseGenrateButton = new JButton("Generate");
		PurchaseGenrateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PurchaseGenrateButton.setBounds(490, 13, 125, 25);
		PurchaseReportScreen.add(PurchaseGenrateButton);
		
		//Scroll Pane & Table
		
		/*--------Create Table--------------------------------------------*/
		PurchaseReportTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	return false;
	        };
		};
		
		
		/*------- Create JTable surround with scroll pane and add it to TariffApprovalPanel --------*/
		JScrollPane PurchaseReportpane = new JScrollPane();
		PurchaseReportpane.setBounds(40, 58, 900, 438);
		PurchaseReportScreen.add(PurchaseReportpane);		
		
		PurchaseReportpane.setViewportView(PurchaseReportTable);	
		PurchaseReportTable.setRowHeight(23);
		PurchaseReportTable.setFillsViewportHeight(true);
		
		PurchaseReportTable.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer CenterRenderer1 = new DefaultTableCellRenderer();
		CenterRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
		PurchaseReportTable.setDefaultRenderer(Object.class, CenterRenderer1);
		
		PurchaseReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		PurchaseReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		PurchaseReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		
		/*----------Message Label---------------------------*/
		UpdateMessagePurchaseReport = new JLabel("");
		UpdateMessagePurchaseReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateMessagePurchaseReport.setBounds(300, 500, 400, 38);
		UpdateMessagePurchaseReport.setForeground(Color.RED);
		PurchaseReportScreen.add(UpdateMessagePurchaseReport);
		
		
		
		/*-------------------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------Stock Report---------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------*/
		
		
		
		
		
		/*------- Create new center layer and add it to container --------*/
		StockReportScreen = new JLayeredPane();		//Global variable
		StockReportScreen.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(StockReportScreen, "StockReportScreen");
		StockReportScreen.setOpaque(true);
		StockReportScreen.setName("StockReportScreen");
		
		
		
		
		
		
		
		/*-------------------------------------Left Panel Click -Create Report Screen---------------------------------*/
		

		CreateReportLeftPanel =new JLayeredPane();
		CreateReportLeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateReportLeftPanel.setBackground(new Color(169, 169, 169));
		CreateReportLeftPanel.setPreferredSize(new Dimension(300,200));
		LeftCardContainer.add(CreateReportLeftPanel,"CreateReportLeftPanel");
		CreateReportLeftPanel.setOpaque(true);
		
		// Logo on left panel
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		CreateReportLeftPanel.add(LogoImage);
		
		CreateReportLeftPanel.setName("CreateReportLeftPanel");
		
		// add 3 buttons
		QuartelyRepotyButton = new JButton("Quarterly Report");
		QuartelyRepotyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		QuartelyRepotyButton.setBounds(12, 26, 279, 38);
		CreateReportLeftPanel.add(QuartelyRepotyButton);
		
		PurchaseReportButton = new JButton("Purchase Report");
		PurchaseReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PurchaseReportButton.setBounds(12, 77, 279, 38);
		CreateReportLeftPanel.add(PurchaseReportButton);
		
		StockReportButton = new JButton("Stock Report");
		StockReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		StockReportButton.setBounds(12, 128, 279, 38);
		CreateReportLeftPanel.add(StockReportButton);
		
		
		/*-------------Error Textbox------------------------*/
		Error_Label = new JLabel("");
		Error_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Error_Label.setBounds(12, 13, 264, 144);
		EmptyCenterPanel.add(Error_Label);
		
		
		
		/*--------------------------------------Center For Stock Report------------------------------*/
		
		/*------- Create new center layer and add it to container --------*/
		StockReportLayer = new JLayeredPane();		//Global variable
		StockReportLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(StockReportLayer, "StockReportLayer");
		StockReportLayer.setOpaque(true);
		StockReportLayer.setName("StockReportLayer");
		
		/*-------------------New Chart--------------------------------------------*/

		
		
	}
	public JButton getApproveSupplyButton(){
		return this.ApproveSupplyButton;
	}
	public JButton getCreateReportButton(){
		return this.CreateReportButton;
	}
	public JButton getUpdateLimitButton(){
		return this.UpdateLimitButton;
	}
	public JButton getUpdateApproveButton(){
		return this.UpdateApproveButton;
	}
	/**
	 * Initialized values of table
	 * @param NewTableModel
	 */
	public void setApprovalTable(DefaultTableModel NewTableModel)
	{
		//Create combo box
		Object[] value = { "Waiting", "Yes", "No"};
		ApprovalSuppliesTable.setModel(NewTableModel);
		DefaultComboBoxModel<?> comboModel = new DefaultComboBoxModel( value );
		JComboBox combo = new JComboBox();
		combo.setModel( comboModel );
		TableColumn col = ApprovalSuppliesTable.getColumnModel().getColumn( 7 );
		col.setCellEditor( new DefaultCellEditor( combo ) );
		//Hide columns
		ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 0 ));
		ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 1 ));
		ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 0 ));
		ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 5 ));
		//All values are in the center of the cell		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ApprovalSuppliesTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	public int getUserID(){
		return this.ManagerUser.getUserID();
	}
	public void ErrorEnterApproval(){
		Error_Label.setText("<html>Error with Approval Table</html>");
	}
	public void ErrorEnterQuartReport(){
		Error_Label.setText("<html>Error with Quart Report Table</html>");
	}
	public void ErrorEnterStockReport(){
		Error_Label.setText("<html>Error with Stock Table</html>");
	}
	public void ErrorEnterPurchaseReportScreen(){
		Error_Label.setText("<html>Error with Purchase Table</html>");
	}
public void ErrorEnterUpdateLimitLevel(){
		Error_Label.setText("<html>Error with Update Limit Table</html>");
	}
	public void ErrorNoChangeInUpdateLevel(){
		this.UpdateLimitTextError.setText("*There Was No Changes In Records");
	}
	
public void SuccessWasChangeUpdateLevel(){
		this.UpdateLimitTextError.setText("*record inserted successfully");
	}
	public void ResetUpdateLimitTextError(){
		this.UpdateLimitTextError.setText("");
	}
	public void UpdateSuccessMessage(){
		UpdateMessage.setText("*Success To Update Order.");
	}
	public void ErrorNoChangeUpdateApprove(){
		UpdateMessage.setText("*There Was No Changes In Records");
	}
	public void ResetErrorLabel(){
		Error_Label.setText("");
	}
	public void ResetUpdateMessage(){
		UpdateMessage.setText("");
	}
	public JTable getApproveTable(){
		return this.ApprovalSuppliesTable;
	}
	public void setUpdatelimitLevelTable(DefaultTableModel NewTableModel){
		//Create combo box
		JTextField value=new JTextField("Enter new Level");	
		UpdateLimitTabel.setModel(NewTableModel);
		TableColumn col = UpdateLimitTabel.getColumnModel().getColumn( 4 );
		col.setCellEditor( new DefaultCellEditor( value ) );
		//Hide columns
		UpdateLimitTabel.removeColumn(UpdateLimitTabel.getColumnModel().getColumn( 0 ));
		//All values are in the center of the cell		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		UpdateLimitTabel.setDefaultRenderer(Object.class, CenterRenderer);	
	}
	public JTable getUpdatelimitLevelTable(){
		return this.UpdateLimitTabel;
	}



	/*--------------Stock Report Functions--------------*/
	public JButton getStockReportButton() {
		return StockReportButton;
	}
	public void setStockReportButton(JButton stockReportButton) {
		StockReportButton = stockReportButton;
	}
	public JButton getQuartelyGenrateButton() {
		return QuartelyGenrateButton;
	}

	public void setStockGraphFloatArray(Float[] StockGraphFloatArray){
		Stockpanel = (new GasStationBarChart(StockGraphFloatArray)).createBarChartPanel();
		Stockpanel.setBounds(67, 65, 859, 457);
		StockReportLayer.add(Stockpanel);

	}
	public JButton getUpdateLimitLevelButton(){
		return this.UpdateLimitLevelButton;
	}




	
	
	/*--------------Quarterly Report Functions-----------*/
	public void setQuarterlyReportTable(DefaultTableModel NewTableModel){
		/*---Need To change float display---*/
		Vector data=NewTableModel.getDataVector();
		NumberFormat nf = NumberFormat.getInstance(); // get instance
		nf.setMaximumFractionDigits(2); // set decimal places
		String s ;
		Vector<Object> cell;
		Object icell;
		for(int i=0;i<data.size();i++){
			cell= (Vector<Object>) data.get(i);
			icell= cell.elementAt(4);
			s = nf.format((Double.valueOf((String)icell)));
			cell.setElementAt(s, 4);
			
			icell= cell.elementAt(5);
			s = nf.format((Double.valueOf((String)icell)));
			cell.setElementAt(s, 5);

		}
		QuarterlyReportTable.setModel(NewTableModel);
	}
	public void ResetQuarterlycomboBoxInQuarterlyReport()
	{
		QuarterlycomboBoxInQuarterlyReport.setSelectedIndex(0);
	}
	public void setYearSelectInQuarterlyReportComboBox(Object[] data){
		// Insert values to CombBox
		int i;
		this.YearSelectInQuarterlyReport.removeAllItems();
		for (i=0;i<data.length;i++)
		{
			this.YearSelectInQuarterlyReport.addItem(data[i]);
		}
		//YearSelectInQuarterlyReport.
	}
	public void setQuartelyGenrateButton(JButton quartelyGenrateButton) {
		QuartelyGenrateButton = quartelyGenrateButton;
	}
	public void setQuartelyRepotyButton(JButton quartelyRepotyButton) {
		QuartelyRepotyButton = quartelyRepotyButton;
	}
	public void EmptyRecordInQuarterlyReport(){
		UpdateMessageQuartelyRepot.setText("*No Record In This Quarter");
	}
	public void ResetUpdateMessageQuarterlyReport(){
		this.UpdateMessageQuartelyRepot.setText("");
	}
	public JButton getQuartelyRepotyButton() {
		return QuartelyRepotyButton;
	}
	public int getQuarterlycomboBoxInQuarterlyReport(){
		return this.QuarterlycomboBoxInQuarterlyReport.getSelectedIndex();
	}
	public int getYearSelectInQuarterlyReport(){
		return  Integer.parseInt(String.valueOf(this.YearSelectInQuarterlyReport.getSelectedItem()));
	}
	/*-------------Purchase Report Functions-------------*/
	public void setPurchaseReportTable(DefaultTableModel NewTableModel){
		/*---Need To change float display---*/
		Vector data=NewTableModel.getDataVector();
		NumberFormat nf = NumberFormat.getInstance(); // get instance
		nf.setMaximumFractionDigits(2); // set decimal places
		String s ;
		Vector<Object> cell;
		Object icell;
		for(int i=0;i<data.size();i++){
			cell= (Vector<Object>) data.get(i);
			icell= cell.elementAt(3);
			s = nf.format((Double.valueOf((String)icell)));
			cell.setElementAt(s, 3);
			


		}
		PurchaseReportTable.setModel(NewTableModel);
	}
	public void setYearSelectInPurchasheReport(Object[] data){
		int i;
		this.YearSelectInPurchaseReport.removeAllItems();
		for (i=0;i<data.length;i++)
		{
			this.YearSelectInPurchaseReport.addItem(data[i]);
		}
	}
	public void setPurchaseReportButton(JButton purchaseReportButton) {
		PurchaseReportButton = purchaseReportButton;
	}
	public JButton getPurchaseReportButton() {
		return PurchaseReportButton;
	}
	public int getQuarterlycomboBoxInPurchaseReport(){
		return this.QuarterlycomboBoxInPurchaseReport.getSelectedIndex();
	}
	public int getYearSelectInPurchaseReport(){
		return Integer.parseInt(String.valueOf(this.YearSelectInPurchaseReport.getSelectedItem()));
	}
	public JButton getPurchaseGenrateButton(){
		return this.PurchaseGenrateButton;
	}
	public void EmptyRecordInPurchase(){
		this.UpdateMessagePurchaseReport.setText("*No Record In This Quarter");
	}
	public void ResetUpdateMessagePurchaseReport(){
		this.UpdateMessagePurchaseReport.setText("");
	}

}
