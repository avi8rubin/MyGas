package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
		/*----------------------------------TOP Panel---------------------------------*/
		
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
		
		/*---------------------------Center panel - Click "Approve Supplies Order"------------------------------------------*/
		
		/*--------Create Table--------------------------------------------*/
		ApprovalSuppliesTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	switch(column){
	        	case 4:
	                return true; 
	        	default:
	                return false;
	        	}
	        };
		};
		
		/*------- Create new center layer and add it to container --------*/
		ApproveSupplyLayer = new JLayeredPane();		//Global variable
		ApproveSupplyLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(ApproveSupplyLayer, "ApproveSupplyLayer");
		ApproveSupplyLayer.setOpaque(true);
		ApproveSupplyLayer.setName("ApproveSupplyLayer");
		
		/*------- Create JTable surround with scroll pane and add it to TariffApprovalPanel --------*/
		JScrollPane UpdateLevelScroll = new JScrollPane();
		UpdateLevelScroll.setBounds(43, 58, 911, 438);
		ApproveSupplyLayer.add(UpdateLevelScroll);		
		
		UpdateLevelScroll.setViewportView(ApprovalSuppliesTable);		
		ApprovalSuppliesTable.setRowHeight(23);
		ApprovalSuppliesTable.setFillsViewportHeight(true);
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
		UpdateMessage.setBounds(83, 480, 575, 25);
		ApproveSupplyLayer.add(UpdateMessage);
		
		/*--------------------Center panel - Click "Update Level For Fuel Type"---------------------------*/
		
		
		/*------- Create new center layer and add it to container --------*/
		UpdateLimitLevelFuelLayer = new JLayeredPane();		//Global variable
		UpdateLimitLevelFuelLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(UpdateLimitLevelFuelLayer, "UpdateLimitLevelFuelLayer");
		UpdateLimitLevelFuelLayer.setOpaque(true);
		UpdateLimitLevelFuelLayer.setName("UpdateLimitLevelFuelLayer");
		
		/*--------Create tabele---------------------------*/
		UpdateLimitTabel = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	        	switch(column){
	        	case 3:
	                return true; 
	        	default:
	                return false;
	        	}
	        };
		};
		
		/*------- Create JTable surround with scroll pane and add it to Update level Panel --------*/
		JScrollPane UpdateLimitTabelScroll = new JScrollPane();
		UpdateLimitTabelScroll.setBounds(0, 58, 911, 438);
		ApproveSupplyLayer.add(UpdateLimitTabelScroll);		
		
		UpdateLimitTabelScroll.setViewportView(UpdateLimitTabel);		
		UpdateLimitTabel.setRowHeight(23);
		UpdateLimitTabel.setFillsViewportHeight(true);
		UpdateLimitTabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		UpdateLimitTabel.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		UpdateLimitTabel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/*-----------------------------------------------Left Panel---------------------------------*/
		
		/*-------------Error Textbox------------------------*/
		Error_Label = new JLabel("");
		Error_Label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Error_Label.setBounds(12, 13, 264, 144);
		EmptyLeftPanel.add(Error_Label);
		
		
		
		
		
		
		
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
	public void ErrorEnterUpdateLimitLevel(){
		Error_Label.setText("<html>Error with Update Limit Table</html>");
	}
	public void UpdateSuccessMessage(){
		UpdateMessage.setText("*Success To Update Order.");
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
		//ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 0 ));
		//ApprovalSuppliesTable.removeColumn(ApprovalSuppliesTable.getColumnModel().getColumn( 1 ));
		
		//All values are in the center of the cell		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ApprovalSuppliesTable.setDefaultRenderer(Object.class, CenterRenderer);	
	}
}
