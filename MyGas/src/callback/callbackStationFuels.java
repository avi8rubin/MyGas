package callback;

import common.MessageType;

public class callbackStationFuels extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int GasStationID;
	private int FuelID;
	private int ThresholdLimit;
	private float CurrentAmount;
	private int Capacity;
	private String FuelDescription;
	private float MaxPrice;
	private float CurrentPrice;
	
	public callbackStationFuels(){}
	public callbackStationFuels(MessageType WhatToDo){
		super(WhatToDo);
	}
	public int getGasStationID() {
		return GasStationID;
	}
	public void setGasStationID(int gasStationID) {
		GasStationID = gasStationID;
	}
	public int getFuelID() {
		return FuelID;
	}
	public void setFuelID(int fuelID) {
		FuelID = fuelID;
	}
	public int getThresholdLimit() {
		return ThresholdLimit;
	}
	public void setThresholdLimit(int thresholdLimit) {
		ThresholdLimit = thresholdLimit;
	}
	public float getCurrentAmount() {
		return CurrentAmount;
	}
	public void setCurrentAmount(float currentAmount) {
		CurrentAmount = currentAmount;
	}
	public int getCapacity() {
		return Capacity;
	}
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	public String getFuelDescription() {
		return FuelDescription;
	}
	public void setFuelDescription(String fuelDescription) {
		FuelDescription = fuelDescription;
	}
	public float getMaxPrice() {
		return MaxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		MaxPrice = maxPrice;
	}
	public float getCurrentPrice() {
		return CurrentPrice;
	}
	public void setCurrentPrice(float currentPrice) {
		CurrentPrice = currentPrice;
	}
	
}
