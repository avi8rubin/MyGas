package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;
/**
 * Create the GUI screens for CEO 
 * @author Ohad
 *
 */
public class CEOGUI extends abstractPanel_GUI{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gui variables
	 */
	private JButton TariffApprovalButton;
	private JLayeredPane TariffApprovalLayer;
	private JCheckBox checkBox = new JCheckBox("");
	// Create JTable - that the last column is editable.
	private JTable TariffApprovalTable = new JTable(){
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
	private JButton TariffSaveButton;
	
	/**
	 * Display CEO screen with all the function this screen contains.
	 * @param EnteredUser The user entered to the system.
	 * @param Server The client connection to server.
	 * @param CommonBuffer The callback buffer, where the query answer returns.
	 * @param LoginScreen The login screen, when the user logout, he return to the login screen.
	 */
	public CEOGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/* ------- Adding new button to Top Panel -------- */
		TariffApprovalButton = new JButton("Tariff Approval");									//Global variable
		TariffApprovalButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TariffApprovalButton.setBounds(12, 99, 185, 38);		
		TopPanel.add(TariffApprovalButton);
		
	
		
		/*------- Create new center layer and add it to container --------*/
		TariffApprovalLayer = new JLayeredPane();												//Global variable
		TariffApprovalLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(TariffApprovalLayer, "Tariff Approval");
		//TariffApprovalLayer.setVisible(false);
		TariffApprovalLayer.setOpaque(true);
		TariffApprovalLayer.setName("Tariff Approval");
		
		
		/*------- Create JTable surround with scroll pane and add it to TariffApprovalPanel --------*/
		JScrollPane TariffApprovalScroll = new JScrollPane();
		TariffApprovalScroll.setBounds(43, 58, 911, 438);
		TariffApprovalLayer.add(TariffApprovalScroll);		
		
		TariffApprovalScroll.setViewportView(TariffApprovalTable);		
		TariffApprovalTable.setRowHeight(23);
		TariffApprovalTable.setFillsViewportHeight(true);
		TariffApprovalTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffApprovalTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		TariffApprovalTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/*------- Create new label on the new layer --------*/
		JLabel TariffApprovalLabel = new JLabel("Tariffs Waiting For Approval");				
		TariffApprovalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TariffApprovalLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		TariffApprovalLabel.setBounds(177, 13, 608, 42);
		TariffApprovalLayer.add(TariffApprovalLabel);
		
		/* ------- Adding new button to Tariff layer -------- */
		TariffSaveButton = new JButton("Save");													//Global variable
		TariffSaveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TariffSaveButton.setBounds(840, 500, 112, 38); 
		TariffApprovalLayer.add(TariffSaveButton);
		
		
		checkBox.setBounds(957, 531, 21, 23);
		TariffApprovalLayer.add(checkBox);
	}
	
	public JCheckBox getCheckBox(){
		return checkBox;
	}
	public JButton getTariffApprovalButton(){
		return TariffApprovalButton;
	}
	public JLayeredPane getTariffApprovalLayer(){
		return TariffApprovalLayer;
	}
	public JButton getTariffSaveButton(){
		return TariffSaveButton;
	}
	public JTable getTariffApprovalTable(){
		return TariffApprovalTable;
	}
	/**
	 * Insert to JTable the new table from DB with combo box.
	 * @param NewTableModel
	 */
	public void setTariffApprovalTable(DefaultTableModel NewTableModel){
		//Create combo box
		Object[] value = { "Waiting", "Yes", "No"};
		TariffApprovalTable.setModel(NewTableModel);
		DefaultComboBoxModel<?> comboModel = new DefaultComboBoxModel( value );
		JComboBox combo = new JComboBox();
		combo.setModel( comboModel );
		TableColumn col = TariffApprovalTable.getColumnModel().getColumn( 6 );
		col.setCellEditor( new DefaultCellEditor( combo ) );
		//Hide columns
		TariffApprovalTable.removeColumn(TariffApprovalTable.getColumnModel().getColumn( 0 ));
		TariffApprovalTable.removeColumn(TariffApprovalTable.getColumnModel().getColumn( 1 ));
		//All values are in the center of the cell		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TariffApprovalTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	
}
