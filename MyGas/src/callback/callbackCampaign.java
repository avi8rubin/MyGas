package callback;


import java.sql.Date;

import common.MessageType;

public class callbackCampaign extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int CampaignID;
	private int CampaignPatternsID;
	private Date StartCampaignDate;
	private Date EndCampaignDate;
	private String ISActive;
	
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
	public Date getStartCampaignDate() {
		return StartCampaignDate;
	}
	public void setStartCampaignDate(Date startCampaignDate) {
		StartCampaignDate = startCampaignDate;
	}
/*
	public void setStartCampaignDate(int year, int month, int day) {
		StartCampaignDate = new Date() ;
	}*/
	public Date getEndCampaignDate() {
		return EndCampaignDate;
	}
	public void setEndCampaignDate(Date endCampaignDate) {
		EndCampaignDate = endCampaignDate;
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
	
}
