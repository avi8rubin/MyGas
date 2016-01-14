package callback;

import common.MessageType;
/**
 * This callback represent Tariff update 
 * Contain
 * Global :FuelID,FuelDescription,CurrentPrice,MaxPrice, NewPrice,WantedPrice;
 *
 */
public class callbackTariffUpdate extends CallBack {

	private static final long serialVersionUID = 1L;
	private int FuelID;
	private String FuelDescription;
	private float CurrentPrice;
	private float MaxPrice;
	private float NewPrice;
	private float WantedPrice;
	
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
	public float getWantedPrice(){
		return WantedPrice;
	}
	public void setWantedPrice(float WantedPrice){
		this.WantedPrice = WantedPrice;
	}
}
