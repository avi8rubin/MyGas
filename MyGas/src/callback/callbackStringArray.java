package callback;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import common.MessageType;
import common.BooleanTableModel;
/**
 * That callback create to have an easy way to ask and receive from server objects for JTable.
 * 
 * @author Ohad
 *
 */
public class callbackStringArray extends CallBack {

	private static final long serialVersionUID = 1L;
	private int colCount;
	private int rowCount;
	private Object[][] Data;
	private String[] colHeaders;
	private Object[] ComboBoxString;
	private Object[] Variance;							//Free object
	private Object[][] VarianceMatrix;					//Free object
	
	public callbackStringArray(MessageType WhatToDo){
		super(WhatToDo);
	}
	public callbackStringArray(Object[][] Data, String[] Headers){
		setData(Data);
		setColHeaders(Headers);
	}
	public callbackStringArray(Object[] Variance){
		this.Variance = Variance;
	}
	/**
	 * Return JTable object
	 * @return - JTable
	 */
	public JTable getJTableObject(){
		return new JTable(Data,colHeaders);
	}
	
	/**
	 * Return DefaultTableModel object
	 * @return - DefaultTableModel
	 */
	public DefaultTableModel getDefaultTableModel(){
		return new DefaultTableModel(Data,colHeaders);
	}
	/**
	 * Return BooleanTableModel object
	 * @return - BooleanTableModel
	 */
	public BooleanTableModel getBooleanTableModel(){
		return new BooleanTableModel(colHeaders,Data);
	}
	/**
	 * Get array of strings that will be the ComboBox values
	 * @param StringArray
	 */
	public void setComboBoxStringArray(Object[] StringArray){
		ComboBoxString = StringArray;
	}
	/**
	 * Return array of strings for ComboBox 
	 * @param StringArray
	 */
	public Object[] getComboBoxStringArray(){
		return ComboBoxString;
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
	/**
	 * Get an array of integers and return the data matrix for JTable with the index's in the array.
	 * @param Index - Array of integers
	 * @return - Data matrix for JTable
	 */
	public Object[][] getRowByIndex (int[] Index){
		Object[][] obj = new Object[Index.length][];
		for(int i=0 ; i<Index.length ; i++)
			obj[i] = Data[Index[i]];
		return obj;
	}
	public Object[][] getVarianceMatrix() {
		return VarianceMatrix;
	}
	public void setVarianceMatrix(Object[][] varianceMatrix) {
		VarianceMatrix = varianceMatrix;
	}
}
