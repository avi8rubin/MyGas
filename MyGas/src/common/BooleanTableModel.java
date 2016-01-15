/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package common;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
/**
 * BooleanTableModel create for JTable that have CheckBox in them.
 * That class extends AbstractTableModel and implements TableModelListener
 * @author Ohad
 *
 */
public class BooleanTableModel extends AbstractTableModel implements TableModelListener{
	private static final long serialVersionUID = 1L;
	String[] columns ;
    Object[][] data;

    public BooleanTableModel(String[] columns, Object[][] data){
    	this.columns=columns;
    	this.data=data;
    	//this.addTableModelListener(this);
    }
    public BooleanTableModel() {
    	//this.addTableModelListener(this);
		// TODO Auto-generated constructor stub
	}
    @Override
	public int getRowCount() {
        return data.length;
    }
	@Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	//return getValueAt(rowIndex, columnIndex);
        return data[rowIndex][columnIndex];
    }  
    @Override
    public void setValueAt(Object Value,int rowIndex, int columnIndex){
    	data[rowIndex][columnIndex] = Value;
    	fireTableCellUpdated(rowIndex, columnIndex);
    	fireTableDataChanged();
    }
    public void setData(Object Data[][]){
    	data=Data;
    }
    public void setHeaders(String Headers[]){
    	columns=Headers;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    /**
     * This method is used by the JTable to define the default
     * renderer or editor for each cell. For example if you have
     * a boolean data it will be rendered as a check box. A
     * number value is right aligned.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	//if(columnIndex==4) return (new JComboBox<String>()).getClass();
    	if(columnIndex==3 || columnIndex==5) return Boolean.class;
    	else return super.getColumnClass(columnIndex);
    }
	@Override
	public void tableChanged(TableModelEvent e) {
		 int row = e.getFirstRow();
	        int column = e.getColumn();
	        BooleanTableModel model = (BooleanTableModel)e.getSource();
	       // String columnName = model.getColumnName(column);
	        Object data = model.getValueAt(row, column);
	        model.setValueAt(data, row, column);
		
	}
}

