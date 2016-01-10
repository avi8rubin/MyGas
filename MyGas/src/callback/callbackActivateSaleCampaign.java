package callback;

import java.sql.Date;

import common.MessageType;
/**
 * Return the active sale campaign
 * @author Ohad
 *
 */
public class callbackActivateSaleCampaign extends CallBack{

	private static final long serialVersionUID = 1L;
	private int CampaignID;
	private String CampaignDescription;
	private int CampaignPatternsID;
	private Date StartCampaign;
	private Date EndCampaign;
	private Boolean IsActive;	
	
	public callbackActivateSaleCampaign(MessageType WhatToDo){
		super(WhatToDo);
	}	
	public int getCampaignID(){
		return CampaignID;
	}
	public void setCampaignID(int CampaignID){
		this.CampaignID = CampaignID;
	}	
	public int getCampaignPatternsID(){
		return CampaignPatternsID;
	}
	public void setCampaignPatternsID(int CampaignPatternsID){
		this.CampaignPatternsID = CampaignPatternsID;
	}		
	public String getCampaignDescription(){
		return CampaignDescription;
	}
	public void setCampaignDescription(String CampaignDescription){
		this.CampaignDescription = CampaignDescription;
	}	
	public Date getStartCampaign(){
		return StartCampaign;
	}
	public void setStartCampaign(Date StartCampaign){
		this.StartCampaign = StartCampaign;
	}	
	public Date getEndCampaign(){
		return EndCampaign;
	}
	public void setEndCampaign(Date EndCampaign){
		this.EndCampaign = EndCampaign;
	}	
	public Boolean getIsActive(){
		return IsActive;
	}
	public void setIsActive(Boolean IsActive){
		this.IsActive = IsActive;
	}	
	
}


