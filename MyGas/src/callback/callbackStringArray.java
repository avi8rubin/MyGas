package callback;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import common.MessageType;
import common.TableModel;

public class callbackStringArray extends CallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int colCount;
	private int rowCount;
	private Object[][] Data;
	private String[] colHeaders;
	private Object[] ComboBoxString;
	private Object[] Variance;
	
	public callbackStringArray(MessageType WhatToDo){
		super(WhatToDo);
	}
	public callbackStringArray(Object[][] Data, String[] Headers){
		setData(Data);
		setColHeaders(Headers);
	}
	public JTable getJTableObject(){
		return new JTable(Data,colHeaders);
	}
	public TableModel getTableModelObject(){
		return new TableModel(Data,colHeaders);
	}	
	public DefaultTableModel getDefaultTableModel(){
		return new DefaultTableModel(Data,colHeaders);
	}
	public void setComboBoxStringArray(Object[] StringArray){
		ComboBoxString = StringArray;
	}
	public Object[] getComboBoxStringArray(){
		return ComboBoxString;
	}
	public JComboBox getJComboBoxObject (){
		DefaultComboBoxModel<?> comboModel = new DefaultComboBoxModel( ComboBoxString );
		JComboBox combo = new JComboBox();
		combo.addItem((String)ComboBoxString[0]);
		combo.addItem((String)ComboBoxString[1]);
		combo.addItem((String)ComboBoxString[2]);
		combo.setModel( comboModel );
		return combo;
	}
	public int getColCount(){
		return colCount;
	}
	public void setColCount(int ColCount){
		this.colCount = ColCount;
	}
	public int getRowCount(){
		return rowCount;
	}
	public void setRowCount(int rowCount){
		this.rowCount = rowCount;
	}
	public Object[][] getData(){
		return Data;
	}
	public void setData(Object[][] Data){
		this.Data = Data;
	}
	public String[] getColHeaders(){
		return colHeaders;
	}
	public void setColHeaders(String[] colHeaders){
		this.colHeaders = colHeaders;
	}
	public Object[] getVariance(){
		return Variance;
	}
	public void setVariance(Object[] Variance){
		this.Variance = Variance;
	}
}
