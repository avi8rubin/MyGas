package callback;

import common.MessageType;
/**
 * Callback of customer with all the needed details.
 * @author Ohad
 */
public class callbackCustomer extends CallBack{

	private static final long serialVersionUID = 1L;
	private int UserID =0;
	private int CustomersID =0;
	private String CustomerFirstName;
	private String CustomerLastName;
	private String CustomerType;
	private int PlanID;
	private String PlanName;
	private String PhoneNumber;
	private String CreditCard;
	private String Email;
	private int CarrentRate;
	private String ISActive;
	private String UserName;
	private String UserPassword;
	private int UserTypeID;
	private String UserPrivilege;
	private int CostingModelID;
	private String ModelTypeDescription;
	private int[] GasStationInPurchasePlan;
	
	public callbackCustomer(){};
	public callbackCustomer(MessageType WhatToDo){
		super(WhatToDo);
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getCustomersID() {
		return CustomersID;
	}
	public void setCustomersID(int customersID) {
		CustomersID = customersID;
	}
	public String getCustomerFirstName() {
		return CustomerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		CustomerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return CustomerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		CustomerLastName = customerLastName;
	}
	public String getCustomerType() {
		return CustomerType;
	}
	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}
	public String getPlanName() {
		return PlanName;
	}
	public void setPlanName(String planName) {
		PlanName = planName;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getCreditCard() {
		return CreditCard;
	}
	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getCarrentRate() {
		return CarrentRate;
	}
	public void setCarrentRate(int carrentRate) {
		CarrentRate = carrentRate;
	}
	public String getISActive() {
		return ISActive;
	}
	public void setISActive(String iSActive) {
		ISActive = iSActive;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	public int getUserTypeID() {
		return UserTypeID;
	}
	public void setUserTypeID(int userTypeID) {
		UserTypeID = userTypeID;
	}
	public String getUserPrivilege() {
		return UserPrivilege;
	}
	public void setUserPrivilege(String userPrivilege) {
		UserPrivilege = userPrivilege;
	}
	public int getPlanID() {
		return PlanID;
	}
	public void setPlanID(int planID) {
		PlanID = planID;
	}
	public int getCostingModelID() {
		return CostingModelID;
	}
	public void setCostingModelID(int costingModelID) {
		CostingModelID = costingModelID;
	}
	public String getModelTypeDescription() {
		return ModelTypeDescription;
	}
	public void setModelTypeDescription(String modelTypeDescription) {
		ModelTypeDescription = modelTypeDescription;
	}
	public int[] getGasStationInPurchasePlan() {
		return GasStationInPurchasePlan;
	}
	public void setGasStationInPurchasePlan(int[] gasStationInPurchasePlan) {
		GasStationInPurchasePlan = gasStationInPurchasePlan;
	}
	/**
	 * Check if the current station is allowed to this customer
	 * @param GasStationID - Current station id
	 * @return - true/false
	 */
	public boolean isAllowToEnterGasStation(int GasStationID) {
		for (int i=0; i<GasStationInPurchasePlan.length ; i++)
			if (GasStationInPurchasePlan[i] == GasStationID) return true;
		return false;
	}
}
