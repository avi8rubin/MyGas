package callback;

import common.MessageType;

public class callbackWaitingTariff extends CallBack {
	
	/**
	 * Create for CEO GUI
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * CallBack Variables
	 */
	private int TariffUpdateID;
	private String TariffUpdateDate;
	private int FuelID;
	private String FuelDescription;
	private float WantedPrice;
	private float CurrentPrice;
	private String CEOConfirmation;

	public callbackWaitingTariff(MessageType WhatToDo){
		super(WhatToDo);
	}
	
	public int getTariffUpdateID(){
		return TariffUpdateID;
	}
	public void setTariffUpdateID(int TariffUpdateID){
		this.TariffUpdateID = TariffUpdateID;
	}
	public String getTariffUpdateDate(){
		return TariffUpdateDate;
	}
	public void setTariffUpdateDate(String TariffUpdateDate){
		this.TariffUpdateDate = TariffUpdateDate;
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
	public float getWantedPrice(){
		return WantedPrice;
	}
	public void setWantedPrice(float WantedPrice){
		this.WantedPrice = WantedPrice;
	}
	public float getCurrentPrice(){
		return CurrentPrice;
	}
	public void setCurrentPrice(float CurrentPrice){
		this.CurrentPrice = CurrentPrice;
	}
	public String getCEOConfirmation(){
		return CEOConfirmation;
	}
	public void setCEOConfirmation(String CEOConfirmation){
		this.CEOConfirmation = CEOConfirmation;
	}
}
