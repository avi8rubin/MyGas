package callback;


import common.MessageType;
/**
 * Return all the campaigns on system
 * @author Ohad
 *
 */
public class callbackCampaign extends CallBack{

	private static final long serialVersionUID = 1L;
	private int CampaignID;
	private int CampaignPatternsID;
	private String StartCampaignDate;
	private String EndCampaignDate;
	private String ISActive;
	private String CampaignDescription;
	private String CalculationMethod;
	private Float DiscountPercentage;
	private Float PriceAfterDiscount;
	
	
	
	public callbackCampaign(){};
	public callbackCampaign(MessageType WhatToDo){
		super(WhatToDo);
	}
	public int getCampaignID() {
		return CampaignID;
	}
	public void setCampaignID(int campaignID) {
		CampaignID = campaignID;
	}
	public int getCampaignPatternsID() {
		return CampaignPatternsID;
	}
	public void setCampaignPatternsID(int campaignPatternsID) {
		CampaignPatternsID = campaignPatternsID;
	}
	public String getStartCampaignDate() {
		return StartCampaignDate;
	}
	public void setStartCampaignDate(String startCampaignDate) {
		StartCampaignDate = startCampaignDate;
	}
	/**
	 * Create the string on the date special form to mysql
	 * @param year - years in 4 digits
	 * @param month - month in 2 digits
	 * @param day - day in 2 digits
	 */
	public void setStartCampaignDate(int year, int month, int day) {
		StartCampaignDate = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day);
	}
	public String getEndCampaignDate() {
		return EndCampaignDate;
	}
	public void setEndCampaignDate(String endCampaignDate) {
		EndCampaignDate = endCampaignDate;
	}
	/**
	 * Create the string on the date special form to mysql
	 * @param year - years in 4 digits
	 * @param month - month in 2 digits
	 * @param day - day in 2 digits
	 */
	public void setEndCampaignDate(int year, int month, int day) {
		EndCampaignDate = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day);
	}
	public boolean getISActive() {
		if(ISActive.equals("Yes")) return true;
		return false;
	}
	public void setISActive(boolean iSActive) {
		if(iSActive == true)
			ISActive = "Yes";
		else ISActive = "No";
	}
	public String getCampaignDescription() {
		return CampaignDescription;
	}
	public void setCampaignDescription(String campaignDescription) {
		CampaignDescription = campaignDescription;
	}
	public String getCalculationMethod() {
		return CalculationMethod;
	}
	public void setCalculationMethod(String calculationMethod) {
		CalculationMethod = calculationMethod;
	}
	public Float getDiscountPercentage() {
		return DiscountPercentage;
	}
	public void setDiscountPercentage(Float discountPercentage) {
		DiscountPercentage = discountPercentage;
	}
	public Float getPriceAfterDiscount() {
		return PriceAfterDiscount;
	}
	public void setPriceAfterDiscount(Float priceAfterDiscount) {
		PriceAfterDiscount = priceAfterDiscount;
	}
	
}
