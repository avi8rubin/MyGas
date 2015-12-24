package callback;

import common.MessageType;

public class callbackSale extends CallBack {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int UserID;
	private int SaleID;
	private int FuelID;
	private String SaleDate;
	private float FuelAmount;
	private float Payment;
	private int CustomersID;
//For home fuel sale
	private String DeliveryDateAndTime;
	private String DeliveryDate;
	private String DeliveryTime;
	private String Address;
	private String OrderStatus;
//For gas station sale
	private String DriverName;
	private int CarID;
	private String CarNumber;
	private int GasStationID;
	private int CampaignID = 0;
	
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
	public float getFuelAmount() {
		return FuelAmount;
	}
	public void setFuelAmount(float fuelAmount) {
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
	public String getDriverName() {
		return DriverName;
	}
	public void setDriverName(String driverName) {
		DriverName = driverName;
	}
	public int getCarID() {
		return CarID;
	}
	public void setCarID(int carID) {
		CarID = carID;
	}
	public int getGasStationID() {
		return GasStationID;
	}
	public void setGasStationID(int gasStationID) {
		GasStationID = gasStationID;
	}
	public int getCampaignID() {
		return CampaignID;
	}
	public void setCampaignID(int campaignID) {
		CampaignID = campaignID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	
	
	
	
}
