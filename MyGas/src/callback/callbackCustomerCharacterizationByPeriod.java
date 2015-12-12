package callback;

import java.sql.Date;

import common.MessageType;

public class callbackCustomerCharacterizationByPeriod extends CallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date StartDate;
	private Date EndDate;
	private String GasCompany;
	private int CustomersID;
	private String CustomerFirstName;
	private String CustomerLastName;
	private int CustomerRate;
	
	public callbackCustomerCharacterizationByPeriod(MessageType WhatToDo){
		super(WhatToDo);
	}
	
	public Date getStartDate(){
		return StartDate;
	}
	public void setStartDate(Date StartDate){
		this.StartDate = StartDate;
	}
	
	public Date getEndDate(){
		return EndDate;
	}
	public void setEndDate(Date EndDate){
		this.EndDate = EndDate;
	}
	
	public String getGasCompany(){
		return GasCompany;
	}
	public void setGasCompany(String GasCompany){
		this.GasCompany = GasCompany;
	}
	
	public int getCustomersID(){
		return CustomersID;
	}
	public void setCustomersID(int CustomersID){
		this.CustomersID = CustomersID;
	}
	public String getCustomerFirstName(){
		return CustomerFirstName;
	}
	public void setCustomerFirstName(String CustomerFirstName){
		this.CustomerFirstName = CustomerFirstName;
	}	
	
	public String getCustomerLastName(){
		return CustomerLastName;
	}
	public void setCustomerLastName(String CustomerLastName){
		this.CustomerLastName = CustomerLastName;
	}	
	
	
	public int getCustomerRate(){
		return CustomerRate;
	}
	public void setCustomerRate(int CustomerRate){
		this.CustomerRate = CustomerRate;
	}
			
}
