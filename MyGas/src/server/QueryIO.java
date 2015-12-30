package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;

import callback.*;
import common.MessageType;

/**
 * In this class you can find all queries that communicate with the database.
 * All the requests from client goes into two methods, one handles object type CallBack
 * and the second one handles callbackVector type witch extends Vector class.
 * @author zinoohad
 *
 */
public class QueryIO implements Runnable {
	static Connection conn = null;
	static Statement st = null;
	static Calendar now = Calendar.getInstance();
	private static ResultSet AnswerResult;
	private static Object AnswerObject = null;
	
/**
 * Thread values
 */
	private callbackSale ThreadSale;
	private int ThreadMission;
	private static boolean SendBill = true;

	

	/**
	 * This function create Driver connection
	 * @return MessageType
	 */
	public String SetDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e1) {
			return MessageType.No_Driver.toString()+"|Class:QueryIO| Function:SetDriver()|";
		}
		return MessageType.Driver_Succeded.toString();
	}
	/**
	 * This function create the connection to Database
	 * @param Url
	 * @param Name
	 * @param Password
	 * @return Message if connection succeeded or not
	 */
	public String SetConnectionToDB(String Url,String Name,String Password){
		try {
			conn = DriverManager.getConnection(Url, Name, Password);
		} catch (SQLException e) {
		return MessageType.No_Connection_To_Database.toString()+"|Class:QueryIO| Function:SetConnectionToDB|";
		}	
		return MessageType.Connection_To_Database_Succeded.toString();
	}
	public String setStatement(){	
		try {
			st =  conn.createStatement();
		} catch (SQLException e) {
			return MessageType.Statement_Not_succeded.toString()+"|Class:QueryIO| Function:setStatement()|";
		}
		return MessageType.statement_Succeded.toString();
	}	
	public Object CallbackResolver(Object SwitchCallback){		
		
		switch(((CallBack) SwitchCallback).getWhatToDo()){
		
/*Global Queries*/
			case getCheckExistsUserPass:
				AnswerObject = getCheckExistsUserPass((callbackUser)SwitchCallback);				
				break;				
			case updateChangeUserPassword:
				AnswerObject = updateChangeUserPassword((callbackUser)SwitchCallback);			
				break;				
			case updateUserLogin:
				AnswerObject = updateUserLogin((callbackUser)SwitchCallback);				
				break;				
			case updateUserLogout:
				AnswerObject = updateUserLogout((callbackUser)SwitchCallback);				
				break;				
			case getContacts:
				if(SwitchCallback instanceof callbackWorkersDetailes)
					AnswerObject = getContacts((callbackWorkersDetailes)SwitchCallback);	
				else if (SwitchCallback instanceof callbackStringArray)
					AnswerObject = getContacts((callbackStringArray)SwitchCallback);
				break;				
			case getNotifications:
				AnswerObject = getNotifications((callbackStringArray)SwitchCallback);				
				break;				
			case updateNotifications:
				AnswerObject = updateNotifications((callbackUser)SwitchCallback);				
				break;
				
/*Marketing Manager*/
			case getCommentsForMarketionCampaign:
				if(SwitchCallback instanceof callbackCommentsForMarketionCampaign)
					AnswerObject = getCommentsForMarketionCampaign((callbackCommentsForMarketionCampaign)SwitchCallback);	
				else if (SwitchCallback instanceof callbackStringArray)
					AnswerObject = getCommentsForMarketionCampaign((callbackStringArray)SwitchCallback);
				break;					
			case getCustomerCharacterizationByPeriod:
				AnswerObject = getCustomerCharacterizationByPeriod((callbackStringArray)SwitchCallback);
				break;				
			case getFuelsDetailes:
				AnswerObject = getFuelsDetailes((callbackStringArray)SwitchCallback);
				break;				
			case getCampaignPatternAndActiveCampaign:
				AnswerObject = getCampaignPatternAndActiveCampaign((callbackStringArray)SwitchCallback);
				break;				
			case setNewCampaign:
				AnswerObject = setNewCampaign((callbackCampaign)SwitchCallback);
				break;
				
/*CEO*/
			case getWaitingTariff:
				AnswerObject = getWaitingTariff((callbackStringArray)SwitchCallback);				
				break;	
				
/*Marketing Representative*/				
			case getCustomer:
				AnswerObject = getCustomer((callbackCustomer)SwitchCallback);				
				break;					
			case getIsUserNameExists:
				AnswerObject = getIsUserNameExists((callbackUser)SwitchCallback);				
				break;				
			case setCreateNewCustomer:
				AnswerObject = setCreateNewCustomer((callbackCustomer)SwitchCallback);				
				break;					
			case setNewCar:
				AnswerObject = setNewCar((callbackCar)SwitchCallback);				
				break;				
			case getHomeFuelOrders:
				if(SwitchCallback instanceof callbackSale)
					AnswerObject = getHomeFuelOrders((callbackSale)SwitchCallback);	
				if(SwitchCallback instanceof callbackStringArray)
					AnswerObject = getHomeFuelOrders((callbackStringArray)SwitchCallback);	
				break;				
			case getCustomerDetailes:
				AnswerObject = getCustomerDetailes((callbackCustomer)SwitchCallback);				
				break;		
			case getAnalyticSystemRatingCalculation:
				AnswerObject = getAnalyticSystemRatingCalculation(1,(callbackStringArray)SwitchCallback);				
				break;
				
			case updateAnalyticSystemRatingCalculation:
				AnswerObject = getAnalyticSystemRatingCalculation(2,(callbackStringArray)SwitchCallback);				
				break;
			case setUpdateCustomer:
				AnswerObject = setUpdateCustomer((callbackCustomer)SwitchCallback);				
				break;
				
/*Customer*/
			case setNewHomeFuelSale:
				AnswerObject = setNewHomeFuelSale((callbackSale)SwitchCallback);				
				break;				
			case getCarDetailes:
				if(SwitchCallback instanceof callbackCar)
					AnswerObject = getCarDetailes((callbackCar)SwitchCallback);	
				if(SwitchCallback instanceof callbackStringArray)
					AnswerObject = getCarDetailes((callbackStringArray)SwitchCallback);	
				break;
/*Station*/		
			case getSaleDiscount:
				AnswerObject = getSaleDiscount((callbackSale)SwitchCallback);				
				break;				
			case getCarWithNFC:
				AnswerObject = getCarWithNFC((callbackCar)SwitchCallback);				
				break;				
			case setNewGasStationSale:
				AnswerObject = setNewGasStationSale((callbackSale)SwitchCallback);				
				break;				
			case getFuelPerStation:
				AnswerObject = getFuelPerStation((callbackStationFuels)SwitchCallback);				
				break;

/*Station Manager*/
			case getStationSuppliesOrder:
				AnswerObject = getStationSuppliesOrder((callbackStringArray)SwitchCallback);				
				break;	
			case getCurrentThresholdLimit:
				AnswerObject = getCurrentThresholdLimit((callbackStringArray)SwitchCallback);				
				break;	
			case getQuarterIncomesReport:
				AnswerObject = getQuarterIncomesReport((callbackStringArray)SwitchCallback);				
				break;	
			case getQuarterPurchaseReport:
				AnswerObject = getQuarterPurchaseReport((callbackStringArray)SwitchCallback);				
				break;	
				
		default:
			AnswerObject = new callback_Error("Not a callback object, send legal callback or you don't fill 'WhatToDo'.");
			break;
				
		} // END switch

		
		TimeToSendBill();									//Check if the first day in the month is the current date and send bills
		/*Copy What to do*/
		if (AnswerObject instanceof CallBack)
			((CallBack) AnswerObject).setWhatToDo(((CallBack) SwitchCallback).getWhatToDo());
		else if (AnswerObject instanceof callbackVector)
			((callbackVector) AnswerObject).setWhatToDo(((CallBack) SwitchCallback).getWhatToDo());
		/*---------------*/
		return AnswerObject;
	}

	public void TimeToSendBill(){
		if(now.get(Calendar.DAY_OF_MONTH) == 24 && SendBill){
			/*Initiate Thread*/
			ThreadMission=1;
			(new Thread(this)).start();
			SendBill = false;
			/*---------------*/
		}
		if(now.get(Calendar.DAY_OF_MONTH) != 1) SendBill = true;
	}
	
	public Object VectorResolver(Object SwitchCallback){
		switch(((callbackVector) SwitchCallback).getWhatToDo()){

/*Marketing Manager*/			
		case setUpdateFuelsTariffForCEO:
			AnswerObject = setUpdateFuelsTariffForCEO((callbackVector)SwitchCallback);
			break;		
/*CEO*/
			case setWaitingTariff:
				AnswerObject = setWaitingTariff((callbackVector)SwitchCallback);
				break;
			
/*Station Manager*/				
			case setCurrentThresholdLimit:
				AnswerObject = setCurrentThresholdLimit((callbackVector)SwitchCallback);				
				break;				

/*Marketing Representative*/					
			case getMarketingRepresentativeComboBox:
				AnswerObject = getMarketingRepresentativeComboBox((callbackVector)SwitchCallback);				
				break;
				
		default:
			AnswerObject = new callback_Error("Not a callbackVector object, send legal callbackVector or you don't fill 'WhatToDo'.");
			break;
		}
		
		/*Copy What to do*/	
		if (AnswerObject instanceof CallBack)
			((CallBack) AnswerObject).setWhatToDo(((callbackVector) SwitchCallback).getWhatToDo());
		else if (AnswerObject instanceof callbackVector)
			((callbackVector) AnswerObject).setWhatToDo(((callbackVector) SwitchCallback).getWhatToDo());
		/*---------------*/
		
		
		return AnswerObject;
		
	}
	
/**
 * Global queries 	
 */	
	private CallBack getCheckExistsUserPass(callbackUser User){
		// Set variables ---------------------------------------------------------
		callbackUser callback_Obj = new callbackUser();
		
		// Build query -----------------------------------------------------------
		String SqlQuery = "SELECT * FROM All_Users_Detailes "
						+ "WHERE User_Name= '"+User.getUserName().trim()+"' ";
		 	
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);	
		/**
		 * Create the callback structure for user 
		 */
				if (AnswerResult.next()) { 					
					callback_Obj.setUserName(AnswerResult.getString("User_Name"));	
					callback_Obj.setPassword(AnswerResult.getString("User_Password"));
					callback_Obj.setEmail(AnswerResult.getString("Email"));
					callback_Obj.setFirstName(AnswerResult.getString("Customer_First_Name"));
					callback_Obj.setLastName(AnswerResult.getString("Customer_Last_Name"));
					callback_Obj.setLoggedIn(AnswerResult.getString("Logged_In"));
					callback_Obj.setUserID(AnswerResult.getInt("User_ID"));
					callback_Obj.setUserTypeId(AnswerResult.getInt("User_Type_Id"));
					callback_Obj.setUserPrivilege(AnswerResult.getString("User_Privilege"));
				}
			
				} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(callback_Obj.getUserName() != null)
			return callback_Obj;
		else return (new callback_Error("User not exists in DB."));

		
	} // END getCheckExistsUserPass
	private CallBack updateChangeUserPassword(callbackUser User){
		// Set variables ---------------------------------------------------------
				
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Users SET User_Password=(?) WHERE User_ID=(?);");
			
		// Send query to DB  -----------------------------------------------------
			ps.setString(1, User.getPassword());
			ps.setInt(2, User.getUserID());			
			ps.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
		
	}
	private CallBack updateUserLogin(callbackUser User){
		// Set variables ---------------------------------------------------------
				
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Users SET Logged_In='Yes' WHERE User_ID=(?);");
			
		// Send query to DB  -----------------------------------------------------
			ps.setInt(1, User.getUserID());			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
	}
	private CallBack updateUserLogout(callbackUser User){
		// Set variables ---------------------------------------------------------
			//Vector <CallBack> LocalVector = new Vector <CallBack>();
				
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Users SET Logged_In='No' WHERE User_ID=(?);");
			
		// Send query to DB  -----------------------------------------------------
			ps.setInt(1, User.getUserID());			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
	}
	private Vector<?> getContacts(callbackWorkersDetailes WorkersDetailes){
		// Set variables ---------------------------------------------------------
		Vector <CallBack> LocalVector = new Vector <CallBack>();	
		
		// Build query -----------------------------------------------------------
		String SqlQuery = "SELECT * FROM Workers";
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);
			/**
			 * Create the report callback structure
			 */
					while (AnswerResult.next()) { 					
						callbackWorkersDetailes callback_Obj = new callbackWorkersDetailes();
						callback_Obj.setWorkerID(AnswerResult.getInt("Worker_ID"));
						callback_Obj.setWorkerFirstName(AnswerResult.getString("Worker_First_Name"));
						callback_Obj.setWorkerLastName(AnswerResult.getString("Worker_Last_Name"));
						callback_Obj.setEmail(AnswerResult.getString("Email"));
						callback_Obj.setOrganization(AnswerResult.getString("Organization"));
						callback_Obj.setUserId(AnswerResult.getInt("User_Id"));
						LocalVector.add(callback_Obj);
					}
				
					} catch (SQLException e) {
						LocalVector.add(new callback_Error("Problem has occurred, query not valid or not connection to DB."));					
				e.printStackTrace();
			}
		return LocalVector;
		
	}
	private CallBack getContacts(callbackStringArray WorkersDetailes){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		String SqlQuery = "SELECT Worker_First_Name, Worker_Last_Name, Email, Organization FROM Workers";
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);
			LocalResult = AnswerResult.getMetaData();
			WorkersDetailes.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			WorkersDetailes.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[WorkersDetailes.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			WorkersDetailes.setColHeaders(Headers);
			
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 
					
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			WorkersDetailes.setData(Data);
			} catch (SQLException e) {
				return new callback_Error("Problem has occurred, query not valid or not connection to DB.");
				}
		return WorkersDetailes;
		
	}
	private CallBack getNotifications(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		String SqlQuery1 = "DELETE FROM Notifications WHERE User_ID=(?) AND User_Saw = 'Yes' AND HOUR(TIMEDIFF(now(), Notification_Date))>48";
		String SqlQuery2 = "SELECT Notification_Date, NotificationsDescription, User_Saw FROM Notifications WHERE User_ID=(?)";
		// Send query to DB and get result ---------------------------------------
		try {
			PreparedStatement ps1=conn.prepareStatement(SqlQuery1);
			ps1.setInt(1,(int) Callback.getVariance()[0] );
			ps1.executeUpdate();		
			
			PreparedStatement ps2=conn.prepareStatement(SqlQuery2);
			ps2.setInt(1,(int) Callback.getVariance()[0] );
			AnswerResult = ps2.executeQuery();
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 
					
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);
			} catch (SQLException e) {
				return new callback_Error("Problem has occurred, query not valid or not connection to DB.");
				}
		return Callback;
		
	}
	private CallBack updateNotifications(callbackUser Callback){
		// Set variables ---------------------------------------------------------	

		// Build query -----------------------------------------------------------
		String SqlQuery1 = "UPDATE Notifications SET User_Saw='Yes' WHERE User_ID=(?)";
		// Send query to DB and get result ---------------------------------------
		try {
			PreparedStatement ps1=conn.prepareStatement(SqlQuery1);
			ps1.setInt(1,(int) Callback.getUserID() );
			ps1.executeUpdate();
			
			} catch (SQLException e) {
				return new callback_Error("Problem has occurred, query not valid or not connection to DB.");
				}
		return new callbackSuccess("Update Notifications Successfully."); 
		
	}
	private boolean setNotifications(int UserID, String Message){
		// Set variables ---------------------------------------------------------	

		// Build query -----------------------------------------------------------
		String SqlQuery = "INSERT INTO Notifications VALUES((?),(?),DEFAULT,DEFAULT)";
		// Send query to DB and get result ---------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement(SqlQuery);
			ps.setInt(1, UserID);
			ps.setString(2, Message);
			ps.executeUpdate();
			
			} catch (SQLException e) {
				return false;
				}
		return true; 
		
	}

/**
 * Marketing Manager 	
 */	
	private Vector<?> getCommentsForMarketionCampaign(callbackCommentsForMarketionCampaign Report){
		// Set variables ---------------------------------------------------------
		Vector <CallBack> LocalVector = new Vector <CallBack>();	
		
		// Build query -----------------------------------------------------------
		String SqlQuery = 
				"SELECT A.Campaign_ID "+
				",DATE_FORMAT(A.Start_Campaign,'%d/%m/%Y') AS Start_Campaign "+
				",DATE_FORMAT(A.End_Campaign,'%d/%m/%Y') AS End_Campaign "+
				",CONCAT(A.Campaign_Description,' (',CAST(DATE_FORMAT(A.Start_Campaign,'%d/%m/%Y') AS CHAR),' - ',CAST(DATE_FORMAT(A.End_Campaign,'%d/%m/%Y') AS CHAR),')') AS Campaign_Description "+ 
				",COUNT(*) AS NumberOfCoustomer "+
				",SUM(B.Payment) AS TotalProfit "+
				",SUM(B.Fuel_Amount) AS TotalFuelAmount "+
				"FROM All_Campaign_On_System A "+
				"LEFT OUTER JOIN All_Gas_Stations_Sales B ON A.Campaign_ID=B.Campaign_ID "+
				"WHERE IS_Active = 'Yes' "+
				"GROUP BY A.Campaign_ID "	;
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);
			/**
			 * Create the report callback structure
			 */
					while (AnswerResult.next()) { 					
						callbackCommentsForMarketionCampaign callback_Obj = new callbackCommentsForMarketionCampaign(null);
						callback_Obj.setCampaignID(AnswerResult.getInt("Campaign_ID"));
						callback_Obj.setStartCampaign(AnswerResult.getDate("Start_Campaign").toString());
						callback_Obj.setEndCampaign(AnswerResult.getDate("End_Campaign").toString());
						callback_Obj.setCampaignDescription(AnswerResult.getString("Campaign_Description"));
						callback_Obj.setNumberOfCoustomer(AnswerResult.getInt("NumberOfCoustomer"));
						callback_Obj.setTotalProfit(AnswerResult.getFloat("TotalProfit"));
						callback_Obj.setTotalFuelAmount(AnswerResult.getFloat("TotalFuelAmount"));
						LocalVector.add(callback_Obj);
					}
				
					} catch (SQLException e) {
						LocalVector.add(new callback_Error("Problem has occurred, query not valid or not connection to DB."));					
				e.printStackTrace();
			}
		return LocalVector;
		
	}
	private CallBack getCommentsForMarketionCampaign(callbackStringArray Report){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		String[] Combo;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		String SqlQuery = 
				"SELECT A.Campaign_ID "+
				",DATE_FORMAT(A.Start_Campaign,'%d/%m/%Y') AS Start_Campaign "+
				",DATE_FORMAT(A.End_Campaign,'%d/%m/%Y') AS End_Campaign "+
				",CONCAT(A.Campaign_Description,' (',CAST(DATE_FORMAT(A.Start_Campaign,'%d/%m/%Y') AS CHAR),' - ',CAST(DATE_FORMAT(A.End_Campaign,'%d/%m/%Y') AS CHAR),')') AS Campaign_Description "+ 
				",COUNT(*) AS NumberOfCoustomer "+
				",SUM(B.Payment) AS TotalProfit "+
				",SUM(B.Fuel_Amount) AS TotalFuelAmount "+
				"FROM All_Campaign_On_System A "+
				"LEFT OUTER JOIN All_Gas_Stations_Sales B ON A.Campaign_ID=B.Campaign_ID "+
				"WHERE IS_Active = 'Yes' "+
				"GROUP BY A.Campaign_ID "	;
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);
			LocalResult = AnswerResult.getMetaData();
			Report.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Report.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Report.getRowCount()][ColNum];
			Headers = new String[ColNum];
			Combo = new String[Report.getRowCount()];
			
			/*Get the table headers*/
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Report.setColHeaders(Headers);
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 			
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				Combo[RowNum] = AnswerResult.getString("Campaign_Description");
				RowNum++;
			}
			Report.setData(Data);
			Report.setComboBoxStringArray(Combo);	
				} catch (SQLException e) {
						return new callback_Error("Problem has occurred, query not valid or not connection to DB.");					
			}
		return Report;
		
	}
	private CallBack getFuelsDetailes(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		String SqlQuery = "SELECT * FROM Fuels";
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);					//Send query to DB
			LocalResult = AnswerResult.getMetaData();					//Get MetaData object
			Callback.setColCount(ColNum = LocalResult.getColumnCount());//How many columns in the table that back from DB
			
			AnswerResult.last();										//--------------------------
			Callback.setRowCount(AnswerResult.getRow());				//Get the number of rows   |
			AnswerResult.beforeFirst();									//--------------------------
			Data = new String[Callback.getRowCount()][ColNum];			//Create table in the result size
			Headers = new String[ColNum];
			//Combo = new String[Callback.getRowCount()];				//Set values for combobox object
			
			/*Get the table headers*/
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 			
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				//Combo[RowNum] = AnswerResult.getString("Campaign_Description");	//Object from ComboBox in the specific screen
				RowNum++;
			}
			Callback.setData(Data);
			//Callback.setComboBoxStringArray(Combo);
				} catch (SQLException e) {
						return new callback_Error("Problem has occurred, query not valid or not connection to DB.");					
			}
		return Callback;
		
	}
	private CallBack setUpdateFuelsTariffForCEO(callbackVector Callback){
		// Set variables ---------------------------------------------------------
		int i;
		// Build query -----------------------------------------------------------
		if(Callback.size()==0) return new callback_Error("The callbackVector was empty.");
		
		try {
			PreparedStatement ps=conn.prepareStatement(
					"INSERT INTO Tariff_Update (Tariff_Update_ID, Tariff_Update_Date, Fuel_ID, Wanted_Price, Current_Price, CEO_Confirmation) "+
					"VALUES(NULL,NOW(),(?),(?),(?),DEFAULT)");
			
		// Send query to DB  -----------------------------------------------------
			for(i=0;i<Callback.size();i++){				
				ps.setInt(1, ((callbackTariffUpdate)Callback.get(i)).getFuelID());
				ps.setFloat(2, ((callbackTariffUpdate)Callback.get(i)).getWantedPrice());
				ps.setFloat(3, ((callbackTariffUpdate)Callback.get(i)).getCurrentPrice());
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
		
	}
	private CallBack getCustomerCharacterizationByPeriod(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		String SqlQuery = 
				"SELECT  DISTINCT A.Customers_ID "+
				", A.Customer_First_Name "+
				", A.Customer_Last_Name "+
				", IFNULL(B.Paz,0) AS Paz "+
				", IFNULL(C.Sonol,0) AS Sonol "+
				", IFNULL(D.Yellow,0) AS Yellow "+
				", IFNULL(E.Ten,0) AS Ten "+
				", IFNULL(F.Total,0) AS Total "+
				"FROM All_Gas_Stations_Sales A "+
				"LEFT OUTER JOIN ( "+
				"	SELECT  Customers_ID "+
				"	,COUNT(*) AS Paz "+
				"	FROM All_Gas_Stations_Sales  "+
				"	WHERE Gas_Company_Name='Paz' "+
				"   AND Sale_Date BETWEEN (?) AND (?) "+
				"    GROUP BY Customers_ID "+
				") B ON A.Customers_ID=B.Customers_ID "+

				"LEFT OUTER JOIN ( "+
				"	SELECT  Customers_ID "+
				"	,COUNT(*) AS Sonol "+
				"	FROM All_Gas_Stations_Sales  "+
				"	WHERE Gas_Company_Name='Sonol' "+
				"   AND Sale_Date BETWEEN (?) AND (?) "+
				"   GROUP BY Customers_ID "+
				") C ON A.Customers_ID=C.Customers_ID "+

				"LEFT OUTER JOIN ( "+
				"	SELECT  Customers_ID "+
				"	,COUNT(*) AS Yellow "+
				"	FROM All_Gas_Stations_Sales "+
				"	WHERE Gas_Company_Name='Yellow' "+
				"   AND Sale_Date BETWEEN (?) AND (?) "+
				"   GROUP BY Customers_ID "+
				") D ON A.Customers_ID=D.Customers_ID "+

				"LEFT OUTER JOIN ( "+
				"	SELECT  Customers_ID "+
				"	,COUNT(*) AS Ten "+
				"	FROM All_Gas_Stations_Sales "+
				"	WHERE Gas_Company_Name='Ten' "+
				"   AND Sale_Date BETWEEN (?) AND (?) "+
				"   GROUP BY Customers_ID "+
				") E ON A.Customers_ID=E.Customers_ID "+

				"LEFT OUTER JOIN ( "+
				"	SELECT  Customers_ID "+
				"	,COUNT(*) AS Total "+
				"	FROM All_Gas_Stations_Sales "+
				"   WHERE Sale_Date BETWEEN (?) AND (?) "+
				"   GROUP BY Customers_ID "+
				") F ON A.Customers_ID=F.Customers_ID "+
				"ORDER BY F.Total DESC ";
		
		
		// Send query to DB and get result ---------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement(SqlQuery);
						
				ps.setString(1, Callback.getColHeaders()[0]);
				ps.setString(2, Callback.getColHeaders()[1]);
				ps.setString(3, Callback.getColHeaders()[0]);
				ps.setString(4, Callback.getColHeaders()[1]);
				ps.setString(5, Callback.getColHeaders()[0]);
				ps.setString(6, Callback.getColHeaders()[1]);
				ps.setString(7, Callback.getColHeaders()[0]);
				ps.setString(8, Callback.getColHeaders()[1]);
				ps.setString(9, Callback.getColHeaders()[0]);
				ps.setString(10, Callback.getColHeaders()[1]);
				AnswerResult = ps.executeQuery();
			
			
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			/*Get the table headers*/
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 			
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);
				} catch (SQLException e) {
						return new callback_Error("Problem has occurred, query not valid or not connection to DB.");					
			}
		return Callback;
		
	}
	private CallBack getCampaignPatternAndActiveCampaign(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		String[][] Data;
		String[] Headers;
		String[] Combo;
		String[][] ComboWithIndex;
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		//Campaign Patterns
		String SqlQuery1 = "SELECT Campaign_Patterns_ID, Campaign_Description FROM Campaign_Patterns";
		//Active Campaign
		String SqlQuery2 = 	"SELECT Campaign_ID "+
							",DATE_FORMAT(Start_Campaign,'%d/%m/%Y') AS Start_Campaign "+
							",DATE_FORMAT(End_Campaign,'%d/%m/%Y') AS End_Campaign "+
							",Campaign_Description "+
							"FROM All_Campaign_On_System "+
							"WHERE IS_Active = 'Yes'";
		
		
		
		// Send query to DB and get result ---------------------------------------
		try {
			
			//Campaign Patterns			
			AnswerResult = st.executeQuery(SqlQuery1);					//Send query to DB
			AnswerResult.last();										//--------------------------
			RowNum = AnswerResult.getRow();								//Get the number of rows   |
			AnswerResult.beforeFirst();									//--------------------------
			Combo = new String[RowNum];									//Set values for omboBox object
			ComboWithIndex = new String[RowNum][2];
			RowNum=0;
			while (AnswerResult.next()) { 	
				Combo[RowNum] = AnswerResult.getString("Campaign_Description");
				ComboWithIndex[RowNum][0] = AnswerResult.getString("Campaign_Patterns_ID");
				ComboWithIndex[RowNum][1] = AnswerResult.getString("Campaign_Description");
				RowNum++;
			}
			RowNum=0;
			Callback.setComboBoxStringArray(Combo);
			Callback.setVarianceMatrix(ComboWithIndex);
			
			//Active Campaign
			AnswerResult = st.executeQuery(SqlQuery2);					//Send query to DB
			LocalResult = AnswerResult.getMetaData();					//Get MetaData object
			Callback.setColCount(ColNum = LocalResult.getColumnCount());//How many columns in the table that back from DB
			
			AnswerResult.last();										//--------------------------
			Callback.setRowCount(AnswerResult.getRow());				//Get the number of rows   |
			AnswerResult.beforeFirst();									//--------------------------
			Data = new String[Callback.getRowCount()][ColNum];			//Create table in the result size
			Headers = new String[ColNum];
			
			/*Get the table headers*/
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 			
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);
				} catch (SQLException e) {
						return new callback_Error("Problem has occurred, query not valid or not connection to DB.");					
			}
		return Callback;
		
	}
	private CallBack setNewCampaign(callbackCampaign Callback){
		// Set variables ---------------------------------------------------------

		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("INSERT INTO Campaigns VALUES(null,(?),(?),(?),'Yes')");
			
		// Send query to DB  -----------------------------------------------------
						
				ps.setInt(1, Callback.getCampaignPatternsID());
				ps.setString(2, Callback.getStartCampaignDate());
				ps.setString(3, Callback.getEndCampaignDate());
				ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
		
	}
	
/**
 * CEO
 */
	private CallBack getWaitingTariff(callbackStringArray Tarrif){
		// Set variables ---------------------------------------------------------	
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		//	JComboBox Confirm = new JComboBox
		Object[] Confirm = {"Waiting","Yes","No"};
		// Build query -----------------------------------------------------------
		String SqlQuery = 
				"SELECT A.Tariff_Update_ID "+
				", DATE_FORMAT(A.Tariff_Update_Date,'%d/%m/%Y') AS Tariff_Update_Date "+
				", A.Fuel_ID "+
				", B.Fuel_Description "+
				", A.Wanted_Price "+
				", A.Current_Price "+
				", A.CEO_Confirmation "+
				"FROM Tariff_Update A "+
				"LEFT OUTER  JOIN Fuels B ON A.Fuel_ID=B.Fuel_ID "+
				"WHERE A.CEO_Confirmation = 'Waiting' ";
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);
			LocalResult = AnswerResult.getMetaData();
			Tarrif.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Tarrif.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Tarrif.getRowCount()][ColNum+1];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Tarrif.setColHeaders(Headers);
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 
				
				for (int i = 0; i < ColNum; i++) 
					if(i<ColNum) Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Tarrif.setComboBoxStringArray(Confirm);
			Tarrif.setData(Data);
			} catch (SQLException e) {
				return new callback_Error("Problem has occurred, query not valid or not connection to DB.");
				}
		return Tarrif;
		
	}
	private CallBack setWaitingTariff(callbackVector UpdateWaitingTariff){
		// Set variables ---------------------------------------------------------
		int i;
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps1=conn.prepareStatement("UPDATE Tariff_Update SET CEO_Confirmation=(?) WHERE Tariff_Update_ID=(?)");
			PreparedStatement ps2=conn.prepareStatement("UPDATE Fuels SET Current_Price=(?) WHERE Fuel_ID=(?)");
		// Send query to DB  -----------------------------------------------------
			for(i=0;i<UpdateWaitingTariff.size();i++){
				
				ps1.setString(1, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getCEOConfirmation().trim());
				ps1.setInt(2, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getTariffUpdateID());
				ps1.executeUpdate();
				if(((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getCEOConfirmation().trim().equals("Yes")){	
					ps2.setFloat(1, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getWantedPrice());
					ps2.setInt(2, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getFuelID());
					ps2.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
	}

/**
 * Marketing Representative	
 */
	private CallBack getCustomer(callbackCustomer Callback){
		// Set variables ---------------------------------------------------------
			int RowNum =0;
			int[] GasStationIDinPurchasePlan;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement("SELECT * FROM Customer_Detailes WHERE User_ID = (?) OR Customers_ID=(?)");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT A.User_ID ,A.Customers_ID ,B.Gas_Company_ID FROM customer_detailes A "+
					"LEFT OUTER JOIN gas_station_detailes B ON A.Plan_ID = B.Plan_ID "+
					"WHERE A.User_ID = (?) GROUP BY User_ID,Gas_Company_Name");
			
		// Send query to DB  -----------------------------------------------------
						
			ps1.setInt(1, Callback.getUserID());
			ps1.setInt(2, Callback.getCustomersID());
			AnswerResult = ps1.executeQuery();
			
			if (!AnswerResult.next()) return new callback_Error("Customer not exists in DB.");
			
			Callback.setUserID(AnswerResult.getInt("User_ID"));
			Callback.setCustomersID(AnswerResult.getInt("Customers_ID"));
			Callback.setCustomerFirstName(AnswerResult.getString("Customer_First_Name"));
			Callback.setCustomerLastName(AnswerResult.getString("Customer_Last_Name"));
			Callback.setCustomerType(AnswerResult.getString("Customer_Type"));
			Callback.setPlanName(AnswerResult.getString("Plan_Name"));
			Callback.setPhoneNumber(AnswerResult.getString("Phone_Number"));
			Callback.setCreditCard(AnswerResult.getString("Credit_Card"));
			Callback.setEmail(AnswerResult.getString("Email"));
			Callback.setCarrentRate(AnswerResult.getInt("Carrent_Rate"));
			Callback.setISActive(AnswerResult.getString("IS_Active"));
			Callback.setUserName(AnswerResult.getString("User_Name"));
			Callback.setUserPassword(AnswerResult.getString("User_Password"));
			Callback.setUserTypeID(AnswerResult.getInt("User_Type_ID"));
			Callback.setUserPrivilege(AnswerResult.getString("User_Privilege"));
			Callback.setCostingModelID(AnswerResult.getInt("Costing_Model_ID"));
			Callback.setModelTypeDescription(AnswerResult.getString("Model_Type_Description"));
			
			//Get Gas Station ID in Purchase Plan
			ps2.setInt(1, Callback.getUserID());
			AnswerResult = ps2.executeQuery();
			AnswerResult.last();			
			GasStationIDinPurchasePlan = new int[AnswerResult.getRow()];
			AnswerResult.beforeFirst();
			
			while(AnswerResult.next()){
				GasStationIDinPurchasePlan[RowNum] = AnswerResult.getInt("Gas_Company_ID");
				RowNum++;
			}
			Callback.setGasStationInPurchasePlan(GasStationIDinPurchasePlan);
		
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return Callback;					// 	Query not succeed
		
	}
	private CallBack getIsUserNameExists(callbackUser Callback){
		// Set variables ---------------------------------------------------------
		CallBack UserCheck;
		callbackCustomer NewCustomer = new callbackCustomer();
		// Build query -----------------------------------------------------------
		String SqlQuery1 = "INSERT INTO Users VALUES(NULL,(?),(?),1,DEFAULT)";
		String SqlQuery2 = "SELECT User_ID FROM Users WHERE User_Name=(?)";
		 	
		// Send query to DB and get result ---------------------------------------
		/*Check if the user name is already exists*/
		UserCheck = getCheckExistsUserPass(Callback);
		if(UserCheck instanceof callbackUser)
			return new callback_Error("User name already exists in DB."); 
		
		/*Create new customer user*/
		try {
			PreparedStatement ps=conn.prepareStatement(SqlQuery1);
			ps.setString(1, Callback.getUserName().trim());
			ps.setString(2, Callback.getPassword().trim());
			ps.executeUpdate();
		/**
		 * Create the callback structure for user 
		 */
			ps=conn.prepareStatement(SqlQuery2);
			ps.setString(1, Callback.getUserName().trim());
			AnswerResult = ps.executeQuery();
			AnswerResult.next();
			NewCustomer.setUserID(AnswerResult.getInt("User_ID"));
			NewCustomer.setUserName(Callback.getUserName().trim());
			NewCustomer.setUserPassword(Callback.getPassword().trim());
			NewCustomer.setUserPrivilege("Customer");
			NewCustomer.setUserTypeID(1);
			
				} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");				// If query not succeed 
		}		
		 return NewCustomer;	
	} // END getCheckExistsUserPass
	private CallBack setCreateNewCustomer(callbackCustomer Callback){
		// Set variables ---------------------------------------------------------
		CallBack Ucallback;			//Check User callback
		callbackUser NewUser = new callbackUser(Callback.getUserName(),Callback.getUserPassword());		
		callbackCustomer Fcallback;
		// Build query -----------------------------------------------------------
		String SqlQuery1 = "SELECT COUNT(*) FROM Customers WHERE Customers_ID=(?)";
		String SqlQuery2 = "SELECT COUNT(*) FROM Customers WHERE Email=(?)";
		String SqlQuery3 = "INSERT INTO Customers VALUES((?),(?),(?),(?),(?),(?),(?),(?),(?),0,1,(?))";
		try {
			PreparedStatement ps1 = conn.prepareStatement(SqlQuery1);
			PreparedStatement ps2 = conn.prepareStatement(SqlQuery2);
			PreparedStatement ps3 = conn.prepareStatement(SqlQuery3);
			
		// Send query to DB  -----------------------------------------------------
			
			//Check if customer already exists	
			ps1.setInt(1, Callback.getCustomersID());
			AnswerResult = ps1.executeQuery();
			AnswerResult.next();
			if (AnswerResult.getInt(1)>0) return new callback_Error("Customer already registered in system."); 
			
			//Check if email already exists	
			ps2.setString(1, Callback.getEmail().trim());
			AnswerResult = ps2.executeQuery();	
			AnswerResult.next();
			if (AnswerResult.getInt(1)>0) return new callback_Error("Email belong to another customer.");
			
			//Check if user exists, if not create new user
			Ucallback = getIsUserNameExists(NewUser);
			if(Ucallback instanceof callback_Error) return Ucallback;
			Fcallback = (callbackCustomer) Ucallback;
			
			//Add new customer to DB
			ps3.setInt(1, Callback.getCustomersID());
			ps3.setString(2, Callback.getCustomerFirstName().trim());
			ps3.setString(3, Callback.getCustomerLastName().trim());
			ps3.setString(4, Callback.getCustomerType().trim());
			ps3.setInt(5, Callback.getPlanID());
			ps3.setInt(6, Fcallback.getUserID());
			ps3.setString(7, Callback.getPhoneNumber().trim());
			ps3.setString(8, Callback.getCreditCard().trim());
			ps3.setString(9, Callback.getEmail().trim());		
			ps3.setInt(10, Callback.getCostingModelID());	
			ps3.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return new callbackSuccess("Create new customer successfully.");					// 	Query succeed
		
	}
	private CallBack setNewCar(callbackCar Callback){
		// Set variables ---------------------------------------------------------

		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("INSERT INTO Cars VALUES(null,(?),(?),(?),(?))");
			
		// Send query to DB  -----------------------------------------------------
			
				ps.setString(1, Callback.getCarNumber().trim());
				ps.setInt(2, Callback.getCustomerID());
				ps.setString(3, Callback.getYesNoNFC().trim());
				ps.setInt(4, Callback.getFuelID());
				ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
		
	}
	private Object getCarDetailes(callbackCar Callback){
		// Set variables ---------------------------------------------------------
		callbackVector LocalVector = new callbackVector();
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Cars WHERE Customers_ID=(?)");
			
		// Send query to DB  -----------------------------------------------------
			ps.setInt(1, Callback.getCustomerID());
			AnswerResult = ps.executeQuery();
			
			while (AnswerResult.next()) { 					
				callbackCar callback_Obj = new callbackCar();
				callback_Obj.setCarID(AnswerResult.getInt("Car_ID"));
				callback_Obj.setCarNumber(AnswerResult.getString("Car_Number"));
				callback_Obj.setCustomerID(AnswerResult.getInt("Customers_ID"));
				callback_Obj.setFuelID(AnswerResult.getInt("Fuel_ID"));
				callback_Obj.setYesNoNFC(AnswerResult.getString("NFC"));				
				LocalVector.add(callback_Obj);
			}
			if (LocalVector.size() == 0) 
				return LocalVector.add( new callback_Error("No records of cars for this customer."));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return LocalVector;					// 	Query not succeed
		
	}
	private CallBack getCarDetailes(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		int ColNum;
		int RowNum =0;
		String[] Combo;
		String[][] ComboWithIndex;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Cars WHERE Customers_ID=(?)");
			
		// Send query to DB  -----------------------------------------------------
			ps.setInt(1, (int) Callback.getVariance()[0]);
			AnswerResult = ps.executeQuery();
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum+1];
			Headers = new String[ColNum];
			Combo = new String[Callback.getRowCount()];
			ComboWithIndex = new String[Callback.getRowCount()][2];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 				
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				Combo[RowNum] = AnswerResult.getString("Car_Number");
				ComboWithIndex[RowNum][0] = AnswerResult.getString("Car_ID");
				ComboWithIndex[RowNum][1] = AnswerResult.getString("Car_Number");
				RowNum++;
			}

			Callback.setData(Data);
			Callback.setComboBoxStringArray(Combo);
			Callback.setVarianceMatrix(ComboWithIndex);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return Callback;					// 	Query not succeed
		
	}
	private Object getCustomerDetailes(callbackCustomer Callback){
		// Set variables ---------------------------------------------------------
			CallBack returnCallback;
			Object[] Var = new Object[1];
			Var[0] = (int)Callback.getCustomersID();
			callbackStringArray NSCB = new callbackStringArray(Var);
			callbackVector LocalVector = new callbackVector();
		// Build query -----------------------------------------------------------
			
		// Send query to DB  -----------------------------------------------------
			returnCallback = getCustomer(Callback);
			if (returnCallback instanceof callback_Error) return returnCallback;
			Callback = (callbackCustomer) getCustomer(Callback);
			LocalVector.add(Callback);
			
			returnCallback = getCarDetailes(NSCB);
			if (returnCallback instanceof callback_Error) return returnCallback;
			NSCB = (callbackStringArray) returnCallback;
			LocalVector.add(NSCB);
			return LocalVector;
	}
	private Vector<?> getMarketingRepresentativeComboBox(callbackVector Callback){
		// Set variables ---------------------------------------------------------
		int RowNum =0;
		Vector<String[]> ComboBoxVector = new Vector<String[]>();
		String[] PurchasePlan;
		String[] FuelType;
		String[] CostingModel;
		// Build query -----------------------------------------------------------
		
		try {

			
		// Send query to DB  -----------------------------------------------------
			//Set Purchase Plan
			AnswerResult = st.executeQuery("SELECT * FROM Purchase_Plan");						
			AnswerResult.last();
			RowNum = AnswerResult.getRow();
			AnswerResult.beforeFirst();
			PurchasePlan = new String[RowNum];
			RowNum=0;
			while (AnswerResult.next()) { 
				PurchasePlan[RowNum] = AnswerResult.getString("Plan_Name");
				RowNum++;
			}
			ComboBoxVector.add(PurchasePlan);
			
			//Set Fuels
			AnswerResult = st.executeQuery("SELECT * FROM Fuels");						
			AnswerResult.last();
			RowNum = AnswerResult.getRow();
			AnswerResult.beforeFirst();
			FuelType = new String[RowNum];
			RowNum=0;
			while (AnswerResult.next()) { 
				FuelType[RowNum] = AnswerResult.getString("Fuel_Description");
				RowNum++;
			}
			ComboBoxVector.add(FuelType);
			
			//Set Costing Model 
			AnswerResult = st.executeQuery("SELECT * FROM Costing_Model");						
			AnswerResult.last();
			RowNum = AnswerResult.getRow();
			AnswerResult.beforeFirst();
			CostingModel = new String[RowNum];
			RowNum=0;
			while (AnswerResult.next()) { 
				CostingModel[RowNum] = AnswerResult.getString("Model_Type_Description");
				RowNum++;
			}
			ComboBoxVector.add(CostingModel);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			Callback.add( new callback_Error("Problem has occurred, it's possible the connection to DB was lost or get error message from DB."));					// If query not succeed 
			return Callback;
		}
			return ComboBoxVector;					// 	Query not succeed
		
	}
	private CallBack getAnalyticSystemRatingCalculation(int SelectUpdate, callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		CallBack ReturnCallback;
		int ColNum;
		int RowNum =0;
		String[] Combo;
		String[][] ComboWithIndex;
		// Build query -----------------------------------------------------------
		
		String SqlQuery = 
				"SELECT Customers_ID "+
				", Customer_First_Name "+
				", Customer_Last_Name "+
				", Customer_Type "+
				", Carrent_Rate "+
				", ROUND(SUM(Costing_Model_Rate)) AS Costing_Model_Rate "+
				", ROUND(SUM(Sale_Hour_Rate)) AS Sale_Hour_Rate "+
				", ROUND(SUM(Price_Rate)) AS Price_Rate "+
				", ROUND(SUM(Fuel_Type_Rate)) AS Fuel_Type_Rate "+
				", ROUND(SUM(Costing_Model_Rate)) AS New_Rate "+
				"FROM ( "+
// Rate	|	Costing Model
//-------------------------
//	2.5	|	Casual Fueling-Max price		
//	5	|	Monthly Fueling 1 car-4% discount price	
//	7.5	|	Monthly Fueling cars-10% discount price	
//	10	|	Full Monthly Fueling-3% discount price	
				"	SELECT * "+
				"	, CASE Costing_Model_ID "+
				"		WHEN 1 THEN 2.5 "+
				"		WHEN 2 THEN 5 "+
				"		WHEN 3 THEN 7.5 "+
				"		WHEN 4 THEN 10 "+
				"	END*0.2 AS  Costing_Model_Rate "+
				"    ,0 AS Sale_Hour_Rate "+
				"    ,0 AS Price_Rate "+
				"    ,0 AS Fuel_Type_Rate "+
				"	FROM Customers "+

				"	UNION "+
//	Rate	|	Hour
//	------------------------
//	10		|	00:00:00-05:00:00
//	8		|	05:01:00-07:00:00
//	6		|	07:01:00-10:00:00
//	4		|	10:01:00-18:00:00
//	5		|	18:01:00-22:00:00
//	8		|	22:01:00-23:59:59
				"	SELECT A.*  "+
				"    ,0 AS Costing_Model_Rate "+
				"	, AVG(CASE  "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '00:00:00' AND '05:00:00' THEN 10 "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '05:01:00' AND '07:00:00' THEN 8 "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '07:01:00' AND '10:00:00' THEN 6 "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '10:01:00' AND '18:00:00' THEN 4 "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '18:01:00' AND '22:00:00' THEN 5 "+
				"		WHEN TIME(B.Sale_Date) BETWEEN '22:01:00' AND '23:59:59' THEN 8 "+
				"	END)*0.3 AS  Sale_Hour_Rate "+
				"   ,0 AS Price_Rate "+
				"    ,0 AS Fuel_Type_Rate "+
				"	FROM mygas.customers A "+
				"	LEFT OUTER JOIN sales B ON A.Customers_ID=B.Customers_ID "+
				"	WHERE DATEDIFF(DATE(NOW()),B.Sale_Date) BETWEEN 0 AND 7 "+
				"	GROUP BY A.Customers_ID "+

				"	UNION "+
//	Rate	|	Price
//	------------------------
//	1		|	0-100
//	2		|	101-200
//	.		|	.
//	.		|	.
//	9		|	801-900
//	10		|	>900
				"	SELECT A.*  "+
				"   ,0 AS Costing_Model_Rate "+
				"   ,0 AS Sale_Hour_Rate "+
				"	, CASE "+
				"		WHEN SUM(B.Payment) BETWEEN 0 AND 100 THEN 1 "+
				"		WHEN SUM(B.Payment) BETWEEN 101 AND 200 THEN 2 "+
				"		WHEN SUM(B.Payment) BETWEEN 201 AND 300 THEN 3 "+
				"		WHEN SUM(B.Payment) BETWEEN 301 AND 400 THEN 4 "+
				"		WHEN SUM(B.Payment) BETWEEN 401 AND 500 THEN 5 "+
				"		WHEN SUM(B.Payment) BETWEEN 501 AND 600 THEN 6 "+
				"		WHEN SUM(B.Payment) BETWEEN 601 AND 700 THEN 7 "+
				"		WHEN SUM(B.Payment) BETWEEN 701 AND 800 THEN 8 "+
				"		WHEN SUM(B.Payment) BETWEEN 801 AND 900 THEN 9 "+
				"		WHEN SUM(B.Payment) > 900 THEN 10 "+
				"	END*0.3 AS Price_Rate "+
				"   ,0 AS Fuel_Type_Rate "+
				"	FROM customers A "+
				"	LEFT OUTER JOIN sales B ON A.Customers_ID=B.Customers_ID "+
				"	WHERE DATEDIFF(DATE(NOW()),B.Sale_Date) BETWEEN 0 AND 7 "+
				"	GROUP BY A.Customers_ID "+

				"	UNION "+

//	Rate	|	Fuel Type
//----------------------------
//	2.5		|	Scooters Fuel
//	5		|	95
//	7.5		|	Diesel
//	10		|	Home Fuel
				"	SELECT A.*  "+
				"   ,0 AS Costing_Model_Rate "+
				"   ,0 AS Sale_Hour_Rate "+
				"	,0 AS Sale_Hour_Rate "+
				"	, AVG(CASE B.Fuel_ID "+
				"		WHEN 2 THEN 2.5 "+
				"		WHEN 1 THEN 5 "+
				"		WHEN 4 THEN 7.5 "+
				"		WHEN 3 THEN 10 "+
				"	END)*0.2 AS Fuel_Type_Rate "+
				"	FROM customers A "+
				"	LEFT OUTER JOIN sales B ON A.Customers_ID=B.Customers_ID "+
				"	WHERE DATEDIFF(DATE(NOW()),B.Sale_Date) BETWEEN 0 AND 7 "+
				"	GROUP BY A.Customers_ID) A "+
				"GROUP BY A.Customers_ID";

		try {
			PreparedStatement ps1=conn.prepareStatement("UPDATE Customers SET Carrent_Rate=(?) WHERE Customers_ID=(?)");			
		// Send query to DB  -----------------------------------------------------

			AnswerResult = st.executeQuery(SqlQuery);
			/*Create callbackStringArray and send back to client*/
			if(SelectUpdate == 1){
				
				LocalResult = AnswerResult.getMetaData();
				Callback.setColCount(ColNum = LocalResult.getColumnCount());
				
				AnswerResult.last();
				Callback.setRowCount(AnswerResult.getRow());
				AnswerResult.beforeFirst();
				Data = new String[Callback.getRowCount()][ColNum+1];
				Headers = new String[ColNum];
				
				for(int i=0;i<ColNum;i++)
					Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
				Callback.setColHeaders(Headers);
			/**
			 * Create the report callback structure
			 */
				while (AnswerResult.next()) { 				
					for (int i = 0; i < ColNum; i++) 
						Data[RowNum][i] = AnswerResult.getString(i + 1);
					RowNum++;
				}
				Callback.setData(Data);
				ReturnCallback = Callback;	
			}	
			/*Update new rating for all customers*/
			else /*if(SelectUpdate == 2)*/{
				while(AnswerResult.next()){
					ps1.setInt(1, AnswerResult.getInt("New_Rate"));
					ps1.setInt(2, AnswerResult.getInt("Customers_ID"));
					ps1.executeUpdate();
				}
				ReturnCallback =  new callbackSuccess();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
		return ReturnCallback;
	}
	private CallBack setUpdateCustomer(callbackCustomer Callback){
		// Set variables ---------------------------------------------------------
		int user_id;
		
		// Build query -----------------------------------------------------------
		String SqlQuery1 = "SELECT COUNT(*) FROM Users WHERE User_ID<>(?) AND User_Name=(?)";
		String SqlQuery2 = "SELECT COUNT(*) FROM Customers WHERE Email=(?) AND Customers_ID<>(?)";
		String SqlQuery3 = "UPDATE Customers SET Customer_First_Name=(?),Customer_Last_Name=(?),Customer_Type=(?),"
							+ "Plan_ID=(?),Phone_Number=(?),Credit_Card=(?),Email=(?),IS_Active=(?),Costing_Model_ID=(?) "
							+ "WHERE Customers_ID=(?)";	
		String SqlQuery4 = "UPDATE Users SET User_Name=(?),User_Password=(?) WHERE User_ID=(?)";
		String SqlQuery5 = "SELECT User_ID FROM Customers WHERE Customers_ID=(?)";
		
		try {
			PreparedStatement ps1 = conn.prepareStatement(SqlQuery1);
			PreparedStatement ps2 = conn.prepareStatement(SqlQuery2);
			PreparedStatement ps3 = conn.prepareStatement(SqlQuery3);
			PreparedStatement ps4 = conn.prepareStatement(SqlQuery4);
			PreparedStatement ps5 = conn.prepareStatement(SqlQuery5);


			
		// Send query to DB  -----------------------------------------------------

			//Get user_id from customers table
			ps5.setInt(1, Callback.getCustomersID());
			AnswerResult = ps5.executeQuery();	
			AnswerResult.next();
			user_id = AnswerResult.getInt(1);			
					
//			Check if user_name already exists in another user	
			ps1.setInt(1, user_id);
			ps1.setString(2, Callback.getUserName().trim());
			AnswerResult = ps1.executeQuery();
			AnswerResult.next();

			if (AnswerResult.getInt(1)>0) return new callback_Error("User name already exists in DB."); 
			
			//Check if email already exists	in another user
			ps2.setString(1, Callback.getEmail().trim());
			ps2.setInt(2, Callback.getCustomersID());
			AnswerResult = ps2.executeQuery();	
			AnswerResult.next();
			if (AnswerResult.getInt(1)>0) return new callback_Error("Email belong to another customer.");
				
			//Update user in DB	
			ps4.setString(1, Callback.getUserName().trim());
			ps4.setString(2, Callback.getUserPassword().trim());
			ps4.setInt(3, user_id);
			ps4.executeUpdate();
			
			//Update customer in DB	
			ps3.setString(1, Callback.getCustomerFirstName().trim());
			ps3.setString(2, Callback.getCustomerLastName().trim());
			ps3.setString(3, Callback.getCustomerType().trim());
			ps3.setInt(4, Callback.getPlanID());
			ps3.setString(5, Callback.getPhoneNumber().trim());
			ps3.setString(6, Callback.getCreditCard().trim());
			ps3.setString(7, Callback.getEmail().trim());
			ps3.setString(8, Callback.getISActive().trim());	
			ps3.setInt(9, Callback.getCostingModelID());
			ps3.setInt(10, Callback.getCustomersID());
			ps3.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return new callbackSuccess("Create new customer successfully.");					// 	Query succeed
		
	}
	
/**
 * Customer	
 */
	private CallBack setNewHomeFuelSale(callbackSale Callback){
		// Set variables ---------------------------------------------------------
			int SaleID;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"INSERT INTO Sales VALUES(null,(?),NOW(),(?),(?),(?))");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Sales_ID, MAX(Sale_Date) AS Sale_Date FROM Sales WHERE Customers_ID = (?)");										
			PreparedStatement ps3=conn.prepareStatement(
					"INSERT INTO Home_Fuel_Sales VALUES((?),(?),(?),'Not Delivered')");
		// Send query to DB  -----------------------------------------------------
			//Create new sale 			
			ps1.setInt(1, Callback.getFuelID());
			ps1.setFloat(2, Callback.getFuelAmount());
			ps1.setFloat(3, Callback.getPayment());
			ps1.setInt(4, Callback.getCustomersID());
			ps1.executeUpdate();
			//Get SaleID
			ps2.setInt(1, Callback.getCustomersID());
			AnswerResult = ps2.executeQuery();
			AnswerResult.next();
			SaleID = AnswerResult.getInt("Sales_ID");
			//Enter new home fuel sale
			ps3.setInt(1, SaleID);
			ps3.setString(2,Callback.getDeliveryDateAndTime());
			ps3.setString(3,Callback.getAddress());
			ps3.executeUpdate();
			
			//Set user notification
			setNotifications(Callback.getUserID(), "Purchase details: "+Callback.getFuelAmount()
			+" liters of home fuel, cost "+Callback.getPayment()+" shekels.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return Callback;					// 	Query not succeed
		
	}
	private Object getHomeFuelOrders(callbackSale Callback){
		// Set variables ---------------------------------------------------------
			int CustomerID = Callback.getCustomersID();
			Vector <CallBack> LocalVector = new Vector <CallBack>();
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"UPDATE Home_Fuel_Sales SET Order_Status = 'Delivered' "+
					"WHERE Sales_ID IN (SELECT Sales_ID FROM All_Home_Fuel_Sales "+
										"WHERE Customers_ID =(?)) "+
					"AND Delivery_Date < NOW()");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Sale_Date AS Sale_Date_And_Time "+
					", Address "+
					", Fuel_Amount "+
					", Payment AS Price "+
					", DATE_FORMAT(Delivery_Date,'%d/%m/%Y') AS Delivery_Date "+
					", TIME(Delivery_Date) AS Delivery_Time "+
					", Order_Status "+
					"FROM All_Home_Fuel_Sales "+
					"WHERE Customers_ID = (?) "+
					"ORDER BY Sale_Date DESC");										

		// Send query to DB  -----------------------------------------------------
			//Update customer orders status 			
			ps1.setInt(1, CustomerID);
			ps1.executeUpdate();
			//Get customer orders
			ps2.setInt(1, CustomerID);
			AnswerResult = ps2.executeQuery();		
			
			while (AnswerResult.next()) { 					
				callbackSale callback_Obj = new callbackSale();
				callback_Obj.setSaleDate(AnswerResult.getString("Sale_Date_And_Time"));
				callback_Obj.setAddress(AnswerResult.getString("Address"));
				callback_Obj.setFuelAmount(AnswerResult.getInt("Fuel_Amount"));
				callback_Obj.setPayment(AnswerResult.getFloat("Price"));
				callback_Obj.setDeliveryDate(AnswerResult.getString("Delivery_Date"));
				callback_Obj.setDeliveryTime(AnswerResult.getString("Delivery_Time"));
				callback_Obj.setOrderStatus(AnswerResult.getString("Order_Status"));
				LocalVector.add(callback_Obj);
			}
			
			if (LocalVector.size() == 0) 
				return new callbackSuccess("No records of orders.");
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return LocalVector;					// 	Query not succeed
		
	}
	private CallBack getHomeFuelOrders(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
			int CustomerID = (int) Callback.getVariance()[0];
			ResultSetMetaData LocalResult;
			String[][] Data;
			String[] Headers;
			int ColNum;
			int RowNum =0;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"UPDATE Home_Fuel_Sales SET Order_Status = 'Delivered' "+
					"WHERE Sales_ID IN (SELECT Sales_ID FROM All_Home_Fuel_Sales "+
										"WHERE Customers_ID =(?)) "+
					"AND Delivery_Date < NOW()");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Sale_Date AS Sale_Date_And_Time "+
					", Address "+
					", Fuel_Amount "+
					", Payment AS Price "+
					", DATE_FORMAT(Delivery_Date,'%d/%m/%Y') AS Delivery_Date "+
					", TIME(Delivery_Date) AS Delivery_Time "+
					", Order_Status "+
					"FROM All_Home_Fuel_Sales "+
					"WHERE Customers_ID = (?) "+
					"ORDER BY Sale_Date DESC");										

		// Send query to DB  -----------------------------------------------------
			//Update customer orders status 			
			ps1.setInt(1, CustomerID);
			ps1.executeUpdate();
			//Get customer orders
			ps2.setInt(1, CustomerID);
			AnswerResult = ps2.executeQuery();		
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();										//--------------------------
			RowNum = AnswerResult.getRow();								//Get the number of rows   |
			AnswerResult.beforeFirst();									//--------------------------
			
			if(RowNum == 0) return new callbackSuccess("No records of orders.");
						
			Data = new String[Callback.getRowCount()][ColNum];			//Create table in the result size
			Headers = new String[ColNum];
			
			/*Get the table headers*/
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 			
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return Callback;					// 	Query not succeed
		
	}	

/**
 * Station	
 */
	private CallBack getSaleDiscount(callbackSale Callback){
		// Set variables ---------------------------------------------------------
		int UserRate=0;
		callbackCampaign CampaignDiscount = new callbackCampaign();
		// Build query -----------------------------------------------------------
		
		
		String SqlQuery1 = "SELECT Carrent_Rate FROM Customers WHERE Customers_ID=(?)";	
		
		String SqlQuery2 = "SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", (?)*A.Discount_Percentage AS Discount_Percentage "+
			    ", MIN((?) - (?)*A.Discount_Percentage ) AS Price_After_Discount "+
			    "FROM ( "+
			"/*Amount_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage "+
			"FROM Campaigns_Amount B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND B.Amount < (?) "+
			
			"UNION  "+
			
			"/*Date_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage "+			
			"FROM Campaigns_Date B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND NOW() BETWEEN A.Start_Campaign AND A.End_Campaign "+
			
			"UNION "+
			
			"/*Gas_Station_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage "+
			"FROM Campaigns_Gas_Station B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND B.Gas_Station_ID = (?) "+
			
			"UNION "+
			
			"/*Gas_Type_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage "+
			"FROM Campaigns_Gas_Type B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND B.Fuel_ID = (?) "+
			
			"UNION  "+
			
			"/*Hours_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage  "+
			"FROM Campaigns_hours B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND TIME(NOW()) BETWEEN B.Start_Hour AND B.End_Hour "+
			
			"UNION "+
			
			"/*Customer_Rate_Discount*/ "+
			"SELECT A.Campaign_ID "+
				", A.Start_Campaign "+
				", A.End_Campaign "+
				", A.IS_Active "+
				", A.Campaign_Description "+
			    ", A.Calculation_Method "+
			    ", A.Discount_Percentage "+
			"FROM Campaigns_Rate B "+
			"LEFT OUTER JOIN All_Campaign_On_System A ON A.Campaign_Patterns_ID=B.Campaign_Patterns_ID "+
			"WHERE A.IS_Active='Yes' "+
			"AND B.Customer_Rate <= (?) "+
			") A";
		
		try {
			PreparedStatement ps1=conn.prepareStatement(SqlQuery1);						
			PreparedStatement ps2=conn.prepareStatement(SqlQuery2);
		// Send query to DB  -----------------------------------------------------
			//Get customer rate			
			ps1.setInt(1, Callback.getCustomersID());
			AnswerResult = ps1.executeQuery();
			if(!AnswerResult.next())
				UserRate = AnswerResult.getInt("Carrent_Rate");
			
			//Get best discount
			ps2.setFloat(1, Callback.getPayment());
			ps2.setFloat(2, Callback.getPayment());
			ps2.setFloat(3, Callback.getPayment());
			ps2.setFloat(4, Callback.getFuelAmount());
			ps2.setInt(5, Callback.getGasStationID());
			ps2.setInt(6, Callback.getFuelID());
			ps2.setInt(7, UserRate);
			AnswerResult = ps2.executeQuery();
			
			if(!AnswerResult.next() || AnswerResult.getInt("Campaign_ID")==0)
				return new callbackSuccess("No discount, please pay full price.");
						
			CampaignDiscount.setCampaignID(AnswerResult.getInt("Campaign_ID"));
			CampaignDiscount.setStartCampaignDate(AnswerResult.getString("Start_Campaign"));
			CampaignDiscount.setEndCampaignDate(AnswerResult.getString("End_Campaign"));
			CampaignDiscount.setCampaignDescription(AnswerResult.getString("Campaign_Description"));
			CampaignDiscount.setCalculationMethod(AnswerResult.getString("Calculation_Method"));
			CampaignDiscount.setDiscountPercentage(AnswerResult.getFloat("Discount_Percentage"));
			CampaignDiscount.setPriceAfterDiscount(AnswerResult.getFloat("Price_After_Discount"));
	
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return CampaignDiscount;					// 	Query not succeed
		
	}	
	private CallBack getCarWithNFC(callbackCar Callback){
		// Set variables ---------------------------------------------------------

		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Cars WHERE Car_Number = (?)");
			
		// Send query to DB  -----------------------------------------------------
			
				ps.setString(1, Callback.getCarNumber().trim());
				AnswerResult = ps.executeQuery();
			
				if(!AnswerResult.next()) return new callback_Error("Car not exists.");
				
				Callback.setCarID(AnswerResult.getInt("Car_ID"));
				Callback.setCarNumber(AnswerResult.getString("Car_Number"));
				Callback.setCustomerID(AnswerResult.getInt("Customers_ID"));
				Callback.setFuelID(AnswerResult.getInt("Fuel_ID"));
				Callback.setYesNoNFC(AnswerResult.getString("NFC"));	
	
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible the connection to DB was lost.");					// If query not succeed 
		}
			return Callback;
		
	}
	private CallBack setNewGasStationSale(callbackSale Callback){
		// Set variables ---------------------------------------------------------
			int SaleID;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"INSERT INTO Sales VALUES(null,(?),NOW(),(?),(?),(?))");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT MAX(Sales_ID) AS Sales_ID FROM Sales WHERE Customers_ID = (?)");										
			PreparedStatement ps3=conn.prepareStatement(
					"INSERT INTO Gas_Stations_Sales VALUES((?),(?),(?),(?),(?))");
			PreparedStatement ps4=conn.prepareStatement(
					"UPDATE Fuel_Per_Station SET Current_Amount=Current_Amount-(?) "+
						"WHERE Gas_Station_ID=(?) AND Fuel_ID=(?)");
		// Send query to DB  -----------------------------------------------------
			//Create new sale 			
			ps1.setInt(1, Callback.getFuelID());
			ps1.setFloat(2, Callback.getFuelAmount());
			ps1.setFloat(3, Callback.getPayment());
			ps1.setInt(4, Callback.getCustomersID());
			ps1.executeUpdate();
			//Get SaleID
			ps2.setInt(1, Callback.getCustomersID());
			AnswerResult = ps2.executeQuery();
			
			AnswerResult.next();
			SaleID = AnswerResult.getInt("Sales_ID");
			//Enter new gas station sale
			ps3.setInt(1, SaleID);
			ps3.setString(2,Callback.getDriverName());
			ps3.setInt(3, Callback.getCarID());
			ps3.setInt(4, Callback.getGasStationID());
			if( Callback.getCampaignID() == 0)
				ps3.setNull(5, Types.INTEGER);
			else ps3.setInt(5, Callback.getCampaignID());
			ps3.executeUpdate();
			
			/*Initiate Thread*/
			ThreadSale = Callback;
			ThreadMission=0;
			(new Thread(this)).start();
			/*---------------*/
			
			ps4.setFloat(1, Callback.getFuelAmount());
			ps4.setInt(2, Callback.getGasStationID());
			ps4.setInt(3, Callback.getFuelID());
			ps4.executeUpdate();
			
			//Set user notification
			setNotifications(Callback.getUserID(), "Purchase details: car number "+Callback.getCarNumber()
			+", "+Callback.getFuelAmount()+" liters, cost "+Callback.getPayment()+" shekels.");
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return new callbackSuccess("Add sale to DB.");					
		
	}
	private Vector<?> getFuelPerStation(callbackStationFuels Callback){
		// Set variables ---------------------------------------------------------
		callbackVector LocalVector = new callbackVector();	
		int row;
		// Build query -----------------------------------------------------------
		String SqlQueryGetID = "SELECT Gas_Station_ID FROM Gas_Stations WHERE User_Interface_ID=(?)";
		String SqlQuery = "SELECT * FROM Fuel_For_Gas_Station WHERE Gas_Station_ID=(?)";
		
		// Send query to DB and get result ---------------------------------------
		try {
			PreparedStatement ps1=conn.prepareStatement(SqlQueryGetID);
			ps1.setInt(1, Callback.getGasStationID());
			AnswerResult = ps1.executeQuery();
			AnswerResult.next();
			
			PreparedStatement ps2=conn.prepareStatement(SqlQuery);
			ps2.setInt(1, AnswerResult.getInt("Gas_Station_ID"));			
			AnswerResult = ps2.executeQuery();
			
			/**
			 * Create the report callback structure
			 */
					while (AnswerResult.next()) { 					
						callbackStationFuels callback_Obj = new callbackStationFuels();
						callback_Obj.setGasStationID(AnswerResult.getInt("Gas_Station_ID"));
						callback_Obj.setFuelID(AnswerResult.getInt("Fuel_ID"));
						callback_Obj.setThresholdLimit(AnswerResult.getInt("Threshold_Limit"));
						callback_Obj.setCurrentAmount(AnswerResult.getFloat("Current_Amount"));
						callback_Obj.setCapacity(AnswerResult.getInt("Capacity"));
						callback_Obj.setFuelDescription(AnswerResult.getString("Fuel_Description"));
						callback_Obj.setMaxPrice(AnswerResult.getFloat("Max_Price"));
						callback_Obj.setCurrentPrice(AnswerResult.getFloat("Current_Price"));
						LocalVector.add(callback_Obj);
					}
				
					} catch (SQLException e) {
						LocalVector.add(new callback_Error("Problem has occurred, query not valid or not connection to DB."));					
				e.printStackTrace();
			}
		row = LocalVector.size();
		return LocalVector;
		
	}

/**
 * Station Manager
 */	
	private int getStationIDByUserID(int UserID){
		// Set variables ---------------------------------------------------------
		int GasStationID;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement("SELECT Gas_Station_ID FROM mygas.gas_stations A "+
					"LEFT OUTER JOIN Workers B ON A.Gas_Station_Manager_ID=B.Worker_ID where B.User_Id =(?)");
			
		// Send query to DB  ----------------------------------------------------- 	
			ps1.setInt(1, UserID);
			AnswerResult = ps1.executeQuery();
			AnswerResult.next();
			GasStationID=AnswerResult.getInt("Gas_Station_ID");		

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;					// If query not succeed 
		}
			return GasStationID;					
		
	}
	private CallBack getStationSuppliesOrder(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		int ColNum, GasStationID;
		int RowNum =0;
		Object[] Confirm = {"Waiting","Yes","No"};
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"UPDATE Fuel_Orders SET Showed_To_Manager='Yes' WHERE Gas_Station_ID=(?) AND Order_Confirmation = 'Waiting'");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Order_ID, Gas_Station_ID, Fuel_ID, Fuel_Description "+
					", Amount_To_Order, Current_Amount, Order_Date, Order_Confirmation, Showed_To_Manager "+
					"FROM Fuel_Orders_For_Stations WHERE Gas_Station_ID = (?) AND Order_Confirmation = 'Waiting'");
			
		// Send query to DB  ----------------------------------------------------- 	
			GasStationID=getStationIDByUserID((int) Callback.getVariance()[0]);
			
			ps1.setInt(1, GasStationID);
			ps1.executeUpdate();
			
			ps2.setInt(1, GasStationID);
			AnswerResult = ps2.executeQuery();

			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 				
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setComboBoxStringArray(Confirm);
			Callback.setData(Data);

		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return Callback;					
		
	}
	private CallBack getCurrentThresholdLimit(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		int ColNum;
		Object[] GasStationID = new Object[1];
		int RowNum =0;
		// Build query -----------------------------------------------------------
		
		try {
			
			PreparedStatement ps=conn.prepareStatement(
					"SELECT Fuel_ID ,Fuel_Description ,Capacity ,Threshold_Limit, Current_Amount "+
					"FROM fuel_for_gas_station WHERE Gas_Station_ID=(?)");
			
		// Send query to DB  ----------------------------------------------------- 	
		
			GasStationID[0]=getStationIDByUserID((int) Callback.getVariance()[0]);
			ps.setInt(1, (int)GasStationID[0]);
			AnswerResult = ps.executeQuery();
			Callback.setVariance(GasStationID);
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			/**
			 * Create the report callback structure
			 */
			while (AnswerResult.next()) { 				
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);

		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, NULL pointer back from DB or lost connection with DB.");					// If query not succeed 
		}
			return Callback;							
	}
	private CallBack setCurrentThresholdLimit(callbackVector Callback){
		// Set variables ---------------------------------------------------------
		int i;
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Fuel_Per_Station SET Threshold_Limit=(?) WHERE Gas_Station_ID=(?) AND Fuel_ID=(?)");

		// Send query to DB  -----------------------------------------------------
			for(i=0;i<Callback.size();i++){				
				ps.setInt(1, ((callbackStationFuels)Callback.get(i)).getThresholdLimit());
				ps.setInt(2, ((callbackStationFuels)Callback.get(i)).getGasStationID());
				ps.setInt(3, ((callbackStationFuels)Callback.get(i)).getFuelID());
				ps.executeUpdate();			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, it's possible server lost connection with DB.");					// If query not succeed 
		}
			return (new callbackSuccess());					// 	Query not succeed
	}
	private CallBack getQuarterIncomesReport(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		Object[] YearsComboBox;
		int ColNum;
		int GasStationID;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		
		try {
			
			PreparedStatement ps1=conn.prepareStatement(
					"SELECT YEAR(Sale_Date) AS Sale_Year,QUARTER(Sale_Date) AS Sale_Quarter,Fuel_Description,COUNT(Sales_ID) AS Customer_Number "+
					",SUM(Fuel_Amount) AS Fuel_Amount,SUM(Payment) AS Total_Profit "+
					"FROM All_Gas_Stations_Sales WHERE Gas_Station_ID = (?) "+
					"GROUP BY Gas_Station_ID,YEAR(Sale_Date),QUARTER(Sale_Date),Fuel_Description");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT DISTINCT YEAR(Sale_Date) AS Years_In_System "+
					"FROM mygas.all_gas_stations_sales WHERE Gas_Station_ID=(?)");
		// Send query to DB  ----------------------------------------------------- 	
		
			//Convert user id to stationID, made for station manager
			GasStationID=getStationIDByUserID((int) Callback.getVariance()[0]);
			
			//Get report divide to years and quarters for station
			ps1.setInt(1, GasStationID);
			AnswerResult = ps1.executeQuery();		
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			while (AnswerResult.next()) { 				
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);

			//Get all years in the system to ComboBox
			ps2.setInt(1, GasStationID);
			AnswerResult = ps2.executeQuery();	
			
			AnswerResult.last();
			RowNum = AnswerResult.getRow();
			AnswerResult.beforeFirst();			
			YearsComboBox = new Object[RowNum];
			RowNum=0;
			while (AnswerResult.next()) { 				
				YearsComboBox[RowNum] = AnswerResult.getString("Years_In_System");
				RowNum++;
			}
			Callback.setComboBoxStringArray(YearsComboBox);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, NULL pointer back from DB or lost connection with DB.");					// If query not succeed 
		}
			return Callback;							
	}
	private CallBack getQuarterPurchaseReport(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
		ResultSetMetaData LocalResult;
		Object[][] Data;
		String[] Headers;
		Object[] YearsComboBox;
		int ColNum;
		int GasStationID;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		
		try {
			
			PreparedStatement ps1=conn.prepareStatement(
					"SELECT YEAR(Order_Date) AS Order_Year ,QUARTER(Order_Date) AS Order_Quarter "+
					",Fuel_Description ,SUM(Amount_To_Order) AS Total_Liters_To_Order "+
					",COUNT(*) AS Number_Of_Orders "+
					"FROM fuel_orders_for_stations "+
					"WHERE Order_Confirmation = 'Yes' AND Gas_Station_ID = (?) "+
					"GROUP BY Gas_Station_ID,YEAR(Order_Date),QUARTER(Order_Date),Fuel_Description");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT DISTINCT YEAR(Order_Date) AS Years_In_System  "+
					"FROM mygas.fuel_orders_for_stations WHERE Gas_Station_ID=(?)");
		// Send query to DB  ----------------------------------------------------- 	
		
			//Convert user id to stationID, made for station manager
			GasStationID=getStationIDByUserID((int) Callback.getVariance()[0]);
			
			//Get report divide to years and quarters for station
			ps1.setInt(1, GasStationID);
			AnswerResult = ps1.executeQuery();		
			
			LocalResult = AnswerResult.getMetaData();
			Callback.setColCount(ColNum = LocalResult.getColumnCount());
			
			AnswerResult.last();
			Callback.setRowCount(AnswerResult.getRow());
			AnswerResult.beforeFirst();
			Data = new String[Callback.getRowCount()][ColNum];
			Headers = new String[ColNum];
			
			for(int i=0;i<ColNum;i++)
				Headers[i] = LocalResult.getColumnName(i+1).replace("_", " ");
			Callback.setColHeaders(Headers);
			
			while (AnswerResult.next()) { 				
				for (int i = 0; i < ColNum; i++) 
					Data[RowNum][i] = AnswerResult.getString(i + 1);
				RowNum++;
			}
			Callback.setData(Data);

			//Get all years in the system to ComboBox
			ps2.setInt(1, GasStationID);
			AnswerResult = ps2.executeQuery();	
			
			AnswerResult.last();
			RowNum = AnswerResult.getRow();
			AnswerResult.beforeFirst();			
			YearsComboBox = new Object[RowNum];
			RowNum=0;
			while (AnswerResult.next()) { 				
				YearsComboBox[RowNum] = AnswerResult.getString("Years_In_System");
				RowNum++;
			}
			Callback.setComboBoxStringArray(YearsComboBox);

		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, NULL pointer back from DB or lost connection with DB.");					// If query not succeed 
		}
			return Callback;							
	}
	
/**
 * Variance
 */
	
	/**
	 * Local function for check if the query return results
	 * @param Result - the result from DB
	 */	
	private void printQueryResult(ResultSet Result){
		try {
			while (Result.next()) {
				System.out.println(Result.getString(1) + ":" + 
						Result.getString(2) + ":" +Result.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Logout all users!
	 * IMPORTANT: Only use when server lost connection or stop!
	 */
	public void ServerStopLogoutAllUsers(){						

		// Send query to DB  -----------------------------------------------------
			try {
				st.executeUpdate("UPDATE Users SET Logged_In='No' WHERE User_Type_Id<>2;");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	
		
	}
	
	@Override
	public void run() {
		switch(ThreadMission){
			case 0: SetNewFuelOrder();
				break;
			case 1: SendBillToCustomers();
				break;
		}	
	}
	
	private void SetNewFuelOrder(){
		callbackSale LocalSale = ThreadSale;
		ResultSet NotificationResultSet, LocalResult = AnswerResult;
		int ThresholdLimit,Capacity,StationManagerUserID;
		String FuelDescription;
		float CurrentAmount;
		try {
			Thread.sleep(20);						//Let the result back to the client
			/*Prepare all queries*/
			
			PreparedStatement ps1=conn.prepareStatement(
					"SELECT A.* ,B.Fuel_Description FROM Fuel_Per_Station A "+
					"LEFT OUTER JOIN Fuels B ON A.Fuel_ID = B.Fuel_ID WHERE A.Gas_Station_ID=(?) AND A.Fuel_ID=(?)");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Order_ID, Fuel_ID, Gas_Station_ID, Amount, MAX(Order_Date) AS Order_Date, Order_Confirmation, Showed_To_Manager "+
					"FROM Fuel_Orders WHERE Fuel_ID = (?) AND Gas_Station_ID = (?) AND Order_Confirmation = 'Waiting'");
			PreparedStatement ps3=conn.prepareStatement(
					"INSERT INTO Fuel_Orders VALUES(NULL,(?),(?),(?),NOW(),DEFAULT,DEFAULT)");
			PreparedStatement ps4=conn.prepareStatement(
					"UPDATE Fuel_Orders SET Amount = (?) WHERE Gas_Station_ID=(?) AND Fuel_ID=(?)");
			PreparedStatement ps5=conn.prepareStatement(
					"SELECT B.User_Id FROM Gas_Stations A "+
					"LEFT OUTER JOIN Workers B ON A.Gas_Station_Manager_ID=B.Worker_ID "+
					"WHERE A.Gas_Station_ID = (?)");
			
			/*Check current fuel limit after sale*/
			ps1.setInt(1, LocalSale.getGasStationID());
			ps1.setInt(2, LocalSale.getFuelID());
			LocalResult = ps1.executeQuery();
			LocalResult.next();
			ThresholdLimit = LocalResult.getInt("Threshold_Limit");
			Capacity = LocalResult.getInt("Capacity");
			CurrentAmount = LocalResult.getFloat("Current_Amount");
			FuelDescription = LocalResult.getString("Fuel_Description");
			
			if(CurrentAmount <= ThresholdLimit){
				ps2.setInt(1, LocalSale.getFuelID());
				ps2.setInt(2, LocalSale.getGasStationID());
				LocalResult = ps2.executeQuery();
				/*Set new fuel order*/
				if(!LocalResult.next() || LocalResult.getInt("Order_ID")==Types.NULL) {
					ps3.setInt(1, LocalSale.getFuelID());
					ps3.setInt(2, LocalSale.getGasStationID());
					ps3.setFloat(3, Capacity - CurrentAmount);
					ps3.executeUpdate();
					/*Update new notification*/
					ps5.setInt(1, LocalSale.getGasStationID());
					NotificationResultSet = ps5.executeQuery();
					NotificationResultSet.next();
					setNotifications(NotificationResultSet.getInt("User_Id"),"A new order for "+FuelDescription+" fuel is waiting For approval."); 
				}
				/*Update exists order*/
				else {
					ps4.setFloat(1, Capacity - CurrentAmount);
					ps4.setInt(2, LocalSale.getGasStationID());	
					ps4.setInt(3, LocalSale.getFuelID());
					ps4.executeUpdate();
				}		
			}		
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void SendBillToCustomers(){
		
		// Set variables ---------------------------------------------------------
		int MonthBill;
		ResultSet LocalResult;
		
		if((now.get(Calendar.MONTH)+1) == 1)
			MonthBill = 12;
		else MonthBill = now.get(Calendar.MONTH);
		// Build query -----------------------------------------------------------

			try {
				Thread.sleep(20);						//Let the result back to the client
				PreparedStatement ps1 = conn.prepareStatement(
						"SELECT User_ID "+
						", Customers_ID "+
						", SUM(Fuel_Amount) AS Fuel_Total_Amount "+
						", CASE Costing_Model_ID "+
							"WHEN 3 THEN ROUND(SUM(Costing_Model_Discount) - SUM(Costing_Model_Discount)*0.1,2) "+
							"WHEN 4 THEN ROUND(SUM(Costing_Model_Discount) - SUM(Costing_Model_Discount)*0.13,2) "+
						   " ELSE ROUND(SUM(Costing_Model_Discount),2) "+
						"END AS Price_After_Costing_Model_Discount "+
						", Model_Type_Description "+
						"FROM ( "+
							"SELECT A.User_ID "+
							", A.Customers_ID "+
							", A.Car_Number "+
							", A.Fuel_Description "+
							", A.Sale_Date "+
							", A.Fuel_Amount "+
							", B.Costing_Model_ID "+
							", B.Model_Type_Description, "+
							"CASE Costing_Model_ID	 "+
							"	WHEN 1 THEN A.Payment "+
							"	ELSE A.Payment - A.Payment*0.04 "+
							"END AS Costing_Model_Discount "+
							"FROM all_gas_stations_sales A "+
							"LEFT OUTER JOIN customer_detailes B ON A.User_ID=B.User_ID  "+
						   " WHERE MONTH(A.Sale_Date) = (?) AND YEAR(Sale_Date) = (?) "+
						   " ) A "+
						"WHERE Costing_Model_ID <> 1 "+
						"GROUP BY A.User_ID");
				
		// Send query to DB  -----------------------------------------------------	
				ps1.setInt(1, MonthBill);
				ps1.setInt(2, now.get(Calendar.YEAR));
				LocalResult = ps1.executeQuery();
				
				while(LocalResult.next()){
					setNotifications(LocalResult.getInt("User_ID"),"Monthly bill to date "+MonthBill+"/"+
							now.get(Calendar.YEAR)%100+": price to pay "+LocalResult.getFloat("Price_After_Costing_Model_Discount")+
							" on "+LocalResult.getFloat("Fuel_Total_Amount")+" liters."	);
				}
				
				
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		
		
		
		
	}
}
	

