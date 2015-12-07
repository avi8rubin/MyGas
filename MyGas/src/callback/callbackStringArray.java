package callback;

public class callbackStringArray extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int colCount;
	private int rowCount;
	private Object[][] Data;
	private String[] colHeaders;
	
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
}
