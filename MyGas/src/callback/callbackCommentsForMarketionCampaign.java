package callback;

public class callbackCommentsForMarketionCampaign extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int CampaignID;
	private String StartCampaign;
	private String EndCampaign;
	private String CampaignDescription;
	private int NumberOfCoustomer;
	private float TotalProfit;
	private float TotalFuelAmount;
	
	
	public callbackCommentsForMarketionCampaign(){
		
	}
	
	public int getCampaignID(){
		return CampaignID;
	}
	public void setCampaignID(int CampaignID){
		this.CampaignID = CampaignID;
	}
	public String getStartCampaign(){
		return StartCampaign;
	}
	public void setStartCampaign(String StartCampaign){
		this.StartCampaign = StartCampaign;
	}
	public String getEndCampaign(){
		return EndCampaign;
	}
	public void setEndCampaign(String EndCampaign){
		this.EndCampaign = EndCampaign;
	}
	public String getCampaignDescription(){
		return CampaignDescription;
	}
	public void setCampaignDescription(String CampaignDescription){
		this.CampaignDescription = CampaignDescription;
	}
	public int getNumberOfCoustomer(){
		return NumberOfCoustomer;
	}
	public void setNumberOfCoustomer(int NumberOfCoustomer){
		this.NumberOfCoustomer = NumberOfCoustomer;
	}
	public float getTotalProfit(){
		return TotalProfit;
	}
	public void setTotalProfit(float TotalProfit){
		this.TotalProfit = TotalProfit;
	}
	public float getTotalFuelAmount(){
		return TotalFuelAmount;
	}
	public void setTotalFuelAmount(float TotalFuelAmount){
		this.TotalFuelAmount = TotalFuelAmount;
	}
}
