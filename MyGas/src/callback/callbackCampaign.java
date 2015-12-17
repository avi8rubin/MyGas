package callback;


import java.sql.Date;
import java.text.SimpleDateFormat;

import common.MessageType;

public class callbackCampaign extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int CampaignID;
	private int CampaignPatternsID;
	private String StartCampaignDate;
	private String EndCampaignDate;
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

	public String getStartCampaignDate() {
		return StartCampaignDate;
	}
	public void setStartCampaignDate(String startCampaignDate) {
		StartCampaignDate = startCampaignDate;
	}
	public void setStartCampaignDate(int year, int month, int day) {
		StartCampaignDate = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day);
	}
	public String getEndCampaignDate() {
		return EndCampaignDate;
	}
	public void setEndCampaignDate(String endCampaignDate) {
		EndCampaignDate = endCampaignDate;
	}
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
	
}
