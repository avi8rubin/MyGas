package callback;

import common.MessageType;

public class callbackCampaignPattern extends CallBack {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Global variables for every pattern
	private int CampaignPatternsID;
	private String CampaignDescription;
	private int CampaignTypeIdServer;
	private int CampaignTypeIdClient;
	private float DiscountPercentage;
	 // Fuel Amount
	private int FuelAmount;
	// Fuel Type
	private int FuelId;
	//Gas Station
	private int GasStationId;
	//Hours
	String StartHour;
	String EndHour;
	//Rate
	private int CustomerRate;
	
	public callbackCampaignPattern() {}

	public callbackCampaignPattern(MessageType WhatToDo) {
		super(WhatToDo);}

	public int getCampaignPatternsID() {
		return CampaignPatternsID;
	}

	public void setCampaignPatternsID(int campaignPatternsID) {
		CampaignPatternsID = campaignPatternsID;
	}

	public String getCampaignDescription() {
		return CampaignDescription;
	}

	public void setCampaignDescription(String campaignDescription) {
		CampaignDescription = campaignDescription;
	}

	public int getCampaignTypeIdServer() {
		return CampaignTypeIdServer;
	}

	public void setCampaignTypeIdServer(int campaignTypeIdServer) {
		CampaignTypeIdServer = campaignTypeIdServer;
		CampaignTypeIdClient = convertToCampaignTypeIdServerToClient(campaignTypeIdServer);
	}

	public int getCampaignTypeIdClient() {
		return CampaignTypeIdClient;
	}

	public void setCampaignTypeIdClient(int campaignTypeIdClient) {
		CampaignTypeIdClient = campaignTypeIdClient;
	}

	public float getDiscountPercentage() {
		return DiscountPercentage;
	}

	public void setDiscountPercentage(float discountPercentage) {
		DiscountPercentage = discountPercentage;
	}

	public int getFuelAmount() {
		return FuelAmount;
	}

	public void setFuelAmount(int fuelAmount) {
		FuelAmount = fuelAmount;
	}

	public int getFuelId() {
		return FuelId;
	}

	public void setFuelId(int fuelId) {
		FuelId = fuelId;
	}

	public int getGasStationId() {
		return GasStationId;
	}

	public void setGasStationId(int gasStationId) {
		GasStationId = gasStationId;
	}

	public String getStartHour() {
		return StartHour;
	}

	public void setStartHour(String startHour) {
		StartHour = startHour;
	}

	public String getEndHour() {
		return EndHour;
	}

	public void setEndHour(String endHour) {
		EndHour = endHour;
	}

	public int getCustomerRate() {
		return CustomerRate;
	}

	public void setCustomerRate(int customerRate) {
		CustomerRate = customerRate;
	}

	public int convertToCampaignTypeIdServerToClient(int CampaignTypeIdServer) {
		
		switch	(CampaignTypeIdServer){
		case 4: // Fuel Amount
			return 1;
		case 3: // Fuel Type
			return 2;
		case 5: //Gas Station
			return 3;
		case 1: //Hours
			return 4;
		case 6: //Rate
			return 5;
	default:
		return 0;
	}	
	}
	
	
}
