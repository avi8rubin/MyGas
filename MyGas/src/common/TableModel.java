package common;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel created to allow us replace tables in GUI screens in a simple and fast way.
 * @author zino ohad
 *
 */
public class TableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	ArrayList<Object[]> Table;
	String[] header;											// the headers
/**
 * Constructor gets a table data and headers	
 * The current table insert to an ArrayList object for simple use later.	
 * @param Date
 * @param header
 */
	
	public TableModel(){
	}
	
	// constructor 
	public TableModel(Object[][] Data, String[] header) {	
		super(Data,header);
		setTableModel(Data,header);
	}
	
	public void setTableModel(Object[][] Data, String[] header){
		this.header = header;									// save the header
		Table = new ArrayList<Object[]>(); 						// and the rows
		for(int i = 0; i < Data.length; ++i) 					// copy the rows into the ArrayList
		Table.add(Data[i]);
		fireTableDataChanged();
	}
	
	// method that needs to be overload. The row count is the size of the ArrayList
	public int getRowCount() {
		return Table.size();
	}

	// method that needs to be overload. The column count is the size of our header
	public int getColumnCount() {
		return header.length;
	}

	// method that needs to be overload. The set object in the arrayList at rowIndex
	public void setValueAt(int rowIndex, int columnIndex, Object obj) {
		Object[] Selected = Table.get(rowIndex);
		Selected[6] = obj;
		Table.add(Selected);
		fireTableDataChanged();
	}
	
	// method that needs to be overload. The object is in the arrayList at rowIndex
	public Object getValueAt(int rowIndex, int columnIndex) {
		return Table.get(rowIndex)[columnIndex];
	}
			
	// a method to return the column name 
	public String getColumnName(int index) {
		return header[index];
	}
	
    public boolean isCellEditable(int row, int column){  
        return false;  
    }
	
	// a method to add a new line to the table, 4 columns
	public void add(Object obj0, Object obj1, Object obj2, Object obj3) {
		Object[] obj = new Object[4];
		obj[0] = obj0;
		obj[1] = obj1;
		obj[2] = obj2;
		obj[3] = obj3;
		Table.add(obj);
		// inform the GUI that I have change
		fireTableDataChanged();
	}
	// a method to add a new line to the table, 5 columns
	public void add(Object obj0, Object obj1, Object obj2, Object obj3, Object obj4) {
		Object[] obj = new Object[5];
		obj[0] = obj0;
		obj[1] = obj1;
		obj[2] = obj2;
		obj[3] = obj3;
		obj[4] = obj4;
		Table.add(obj);
		// inform the GUI that I have change
		fireTableDataChanged();
	}
	// a method to add a new line to the table, 6 columns
	public void add(Object obj0, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5) {
		Object[] obj = new Object[6];
		obj[0] = obj0;
		obj[1] = obj1;
		obj[2] = obj2;
		obj[3] = obj3;
		obj[4] = obj4;
		obj[5] = obj5;
		Table.add(obj);
		// inform the GUI that I have change
		fireTableDataChanged();
	}
	// a method to add a new line to the table, 7 columns
	public void add(Object obj0, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
		Object[] obj = new Object[7];
		obj[0] = obj0;
		obj[1] = obj1;
		obj[2] = obj2;
		obj[3] = obj3;
		obj[4] = obj4;
		obj[5] = obj5;
		obj[6] = obj6;
		Table.add(obj);
		// inform the GUI that I have change
		fireTableDataChanged();
	}
	// a method to add a new line to the table, 8 columns
	public void add(Object obj0, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
		Object[] obj = new Object[8];
		obj[0] = obj0;
		obj[1] = obj1;
		obj[2] = obj2;
		obj[3] = obj3;
		obj[4] = obj4;
		obj[5] = obj5;
		obj[6] = obj6;
		obj[7] = obj7;
		Table.add(obj);
		// inform the GUI that I have change
		fireTableDataChanged();
	}





}
