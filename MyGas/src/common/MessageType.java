package common;

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
/*Global Queries*/
	updateChangeUserPassword,
	updateUserLogin,
	updateUserLogout,
	getCheckExistsUserPass,
	getContacts,
/*Marketing Manager*/	
	getCommentsForMarketionCampaign,
/*CEO*/
	getWaitingTariff,
	setWaitingTariff

}
