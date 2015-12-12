package callback;

import java.sql.Date;

import common.MessageType;

public class callbackBuyHomeFuel extends CallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int CustomerID;
	private int FuelAmount;
	private Date DeliveryDate;
	private int Price;
	private Boolean OrderStatus;
	
	public callbackBuyHomeFuel(MessageType WhatToDo){
		super(WhatToDo);
	}
	
	public int getCustomerID(){
		return CustomerID;
	}
	public void setCustomerID(int CustomerID){
		this.CustomerID = CustomerID;
	}
	
	public int getFuelAmount(){
		return FuelAmount;
	}
	public void setFuelAmount(int FuelAmount){
		this.FuelAmount = FuelAmount;
	}
	public Date getDeliveryDate(){
		return DeliveryDate;
	}
	public void setDeliveryDate(Date DeliveryDate){
		this.DeliveryDate = DeliveryDate;
	}
	public int getPrice(){
		return Price;
	}
	public void setPrice(int Price){
		this.Price = Price;
	}
	public Boolean getOrderStatus(){
		return OrderStatus;
	}
	public void set(Boolean OrderStatus){
		this.OrderStatus = OrderStatus;
	}
	
}
