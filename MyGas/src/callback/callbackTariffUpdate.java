package callback;

import common.MessageType;

public class callbackTariffUpdate extends CallBack {

	/**
	  * Create for Marketing Manager GUI
	 */
	private static final long serialVersionUID = 1L;
	private int FuelID;
	private String FuelDescription;
	private float CurrentPrice;
	private float MaxPrice;
	private float NewPrice;
	
	public callbackTariffUpdate(MessageType WhatToDo){
		super(WhatToDo);
	}
	
	public int getFuelID(){
		return FuelID;
	}
	public void setFuelID(int FuelID){
		this.FuelID = FuelID;
	}
	
	public String getFuelDescription(){
		return FuelDescription;
	}
	public void setFuelDescription(String FuelDescription){
		this.FuelDescription = FuelDescription;
	}
	
	
	public float getCurrentPrice(){
		return CurrentPrice;
	}
	public void setCurrentPrice(float CurrentPrice){
		this.CurrentPrice = CurrentPrice;
	}
	public float getMaxPrice(){
		return MaxPrice;
	}
	public void setMaxPrice(float MaxPrice){
		this.MaxPrice = MaxPrice;
	}
	
	public float getNewPrice(){
		return NewPrice;
	}
	public void setNewPrice(float NewPrice){
		this.NewPrice = NewPrice;
	}
	
}