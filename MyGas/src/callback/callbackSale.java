package callback;

import common.MessageType;

public class callbackSale extends CallBack {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SaleID;
	private int FuelID;
	private String SaleDate;
	private int FuelAmount;
	private float Payment;
	private int CustomersID;
//For home fuel sale
	private String DeliveryDateAndTime;
	private String DeliveryDate;
	private String DeliveryTime;
	private String Address;
	private String OrderStatus;
	
	public callbackSale(){};
	public callbackSale(MessageType WhatToDo){
		super(WhatToDo);
	}
	public int getSaleID() {
		return SaleID;
	}
	public void setSaleID(int saleID) {
		SaleID = saleID;
	}
	public int getFuelID() {
		return FuelID;
	}
	public void setFuelID(int fuelID) {
		FuelID = fuelID;
	}
	public String getSaleDate() {
		return SaleDate;
	}
	public void setSaleDate(String saleDate) {
		SaleDate = saleDate;
	}
	public int getFuelAmount() {
		return FuelAmount;
	}
	public void setFuelAmount(int fuelAmount) {
		FuelAmount = fuelAmount;
	}
	public float getPayment() {
		return Payment;
	}
	public void setPayment(float payment) {
		Payment = payment;
	}
	public int getCustomersID() {
		return CustomersID;
	}
	public void setCustomersID(int customersID) {
		CustomersID = customersID;
	}
	public String getDeliveryDateAndTime() {
		return DeliveryDateAndTime;
	}
	public void setDeliveryDateAndTime(String deliveryDate) {
		DeliveryDateAndTime = deliveryDate;
	}
	public void setDeliveryDateAndTime(int year, int month, int day, int hour, int minute, int second) {
		DeliveryDateAndTime = String.valueOf(year)+"-"+String.valueOf(month)+"-"+
								String.valueOf(day)+" "+String.valueOf(hour)+":"+
									String.valueOf(minute)+":"+String.valueOf(second);
	}
	public void setDeliveryDateAndTime(int year, int month, int day, int hour, int minute) {
		DeliveryDateAndTime = String.valueOf(year)+"-"+String.valueOf(month)+"-"+
								String.valueOf(day)+" "+String.valueOf(hour)+":"+
									String.valueOf(minute)+":00";
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getDeliveryDate() {
		return DeliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	public String getDeliveryTime() {
		return DeliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		DeliveryTime = deliveryTime;
	}
	
	
	
	
}
