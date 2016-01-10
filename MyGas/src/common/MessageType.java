package common;
/**
 * All the common message to start query in the server
 * @author Ohad
 *
 */
public enum MessageType {
	No_Connection_To_Database,
	Connection_To_Database_Succeded,
	No_Driver,
	Driver_Succeded,
	statement_Succeded,
	Statement_Not_succeded,
	Connection_To_Server_Lost,
	Cant_Create_Connection_To_Server,
	Lost_Connection_With_Client,
	StartSender,
/*Global Queries*/
	updateChangeUserPassword,
	updateUserLogin,
	updateUserLogout,
	getCheckExistsUserPass,
	getContacts,
	getNotifications,
	updateNotifications,
/*Marketing Manager*/	
	getCommentsForMarketionCampaign,
	getFuelsDetailes,
	setUpdateFuelsTariffForCEO,
	getCustomerCharacterizationByPeriod,
	getCampaignPatternAndActiveCampaign,
	setNewCampaign,
/*CEO*/
	getWaitingTariff,
	setWaitingTariff,
/*Marketing Representative*/
	getCustomer,
	getIsUserNameExists,
	setCreateNewCustomer,
	setNewCar,
	getCarDetailes,
	getCustomerDetailes,
	getMarketingRepresentativeComboBox,
	getAnalyticSystemRatingCalculation,
	updateAnalyticSystemRatingCalculation,
	setUpdateCustomer,
	setNewCampignPattern,
	getExistCampaignPatterns,
	updateCustomerCar,
/*Customer*/
	setNewHomeFuelSale,
	getHomeFuelOrders,
/*Station*/
	getSaleDiscount,
	getCarWithNFC,
	setNewGasStationSale,
	getFuelPerStation,
/*Station Manager*/
	getStationSuppliesOrder,
	getCurrentThresholdLimit,
	setCurrentThresholdLimit,
	setStationSuppliesOrder,
	getQuarterIncomesReport,
	getQuarterPurchaseReport,
/*FitRunner*/
	ResetForCreateFuelOrder

}
