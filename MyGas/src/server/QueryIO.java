package server;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import callback.*;
import common.MessageType;


public class QueryIO implements Runnable {
	static Connection conn = null;
	static Statement st = null;
	private static ResultSet AnswerResult;
	private static Object AnswerObject = null;
	
/**
 * Thread values
 */
	callbackSale ThreadSale;

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
				if(SwitchCallback instanceof callbackCar)
					AnswerObject = getCarDetailes((callbackCar)SwitchCallback);	
				if(SwitchCallback instanceof callbackStringArray)
					AnswerObject = getCarDetailes((callbackStringArray)SwitchCallback);	
				break;
				
			case getCustomerDetailes:
				AnswerObject = getCustomerDetailes((callbackCustomer)SwitchCallback);				
				break;
				
/*Customer*/
			case setNewHomeFuelSale:
				AnswerObject = setNewHomeFuelSale((callbackSale)SwitchCallback);				
				break;
				
			case getCarDetailes:
				if(SwitchCallback instanceof callbackSale)
					AnswerObject = getHomeFuelOrders((callbackSale)SwitchCallback);	
				if(SwitchCallback instanceof callbackStringArray)
					AnswerObject = getHomeFuelOrders((callbackStringArray)SwitchCallback);	
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
				
				
		default:
			AnswerObject = new callback_Error("Not a callback object, send legal callback or you don't fill 'WhatToDo'.");
			break;
				
		} // END switch

		
		return AnswerObject;
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
				
		default:
			AnswerObject = new callback_Error("Not a callbackVector object, send legal callbackVector or you don't fill 'WhatToDo'.");
			break;
		}
		
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
					//else Data[RowNum][i] = new JComboBox<String[]>(Confirm);
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

		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM Customer_Detailes "+
														"WHERE User_ID = (?)");
			
		// Send query to DB  -----------------------------------------------------
						
			ps.setInt(1, Callback.getUserID());
			AnswerResult = ps.executeQuery();
			
			if (AnswerResult.isLast()) return new callback_Error("Customer not exists in DB.");
			
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
			AnswerResult = ps.executeQuery(SqlQuery2);
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
		
		// Build query -----------------------------------------------------------
		String SqlQuery1 = "SELECT * FROM Customers WHERE Email=(?)";
		String SqlQuery2 = "INSERT INTO Customers VALUES((?),(?),(?),(?),(?),(?),(?),(?),(?),0,1)";
		try {
			PreparedStatement ps1 = conn.prepareStatement(SqlQuery1);
			PreparedStatement ps2=conn.prepareStatement(SqlQuery2);
			
		// Send query to DB  -----------------------------------------------------
			
			Ucallback = getIsUserNameExists(NewUser);
			if(Ucallback instanceof callback_Error) return Ucallback;
			Callback = (callbackCustomer) Ucallback;
			
			//Check if email already exists	
			ps1.setString(1, Callback.getEmail().trim());
			AnswerResult = ps1.executeQuery();			
			if (AnswerResult.isLast()) return new callback_Error("Email belong to another customer.");
			
			//Add new customer to DB
			ps2.setInt(1, Callback.getUserID());
			ps2.setString(2, Callback.getCustomerFirstName().trim());
			ps2.setString(3, Callback.getCustomerLastName().trim());
			ps2.setString(4, Callback.getCustomerType().trim());
			ps2.setInt(5, Callback.getPlanID());
			ps2.setInt(6, Callback.getUserID());
			ps2.setString(7, Callback.getPhoneNumber().trim());
			ps2.setString(8, Callback.getCreditCard().trim());
			ps2.setString(9, Callback.getEmail().trim());			
			ps2.executeUpdate();
			
			
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
			PreparedStatement ps=conn.prepareStatement("INSERT INTO Cars VALUES(null,(?),(?),(?),(?),(?))");
			
		// Send query to DB  -----------------------------------------------------
			
				ps.setString(1, Callback.getCarNumber().trim());
				ps.setInt(2, Callback.getCustomerID());
				ps.setString(3, Callback.getYesNoNFC().trim());
				ps.setInt(4, Callback.getFuelID());
				ps.setInt(5, Callback.getCostingModelID());
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
				callback_Obj.setCostingModelID(AnswerResult.getInt("Costing_Model_ID"));
				callback_Obj.setCustomerID(AnswerResult.getInt("Customers_ID"));
				callback_Obj.setFuelID(AnswerResult.getInt("Fuel_ID"));
				callback_Obj.setYesNoNFC(AnswerResult.getString("NFC"));				
				LocalVector.add(callback_Obj);
			}
			if (LocalVector.size() == 0) 
				return new callback_Error("No records of cars for this customer.");
			
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
			ps2.setInt(6, UserRate);
			AnswerResult = ps2.executeQuery();
			
			if(!AnswerResult.next())
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
				Callback.setCostingModelID(AnswerResult.getInt("Costing_Model_ID"));
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
					"SELECT Sales_ID, MAX(Sale_Date) AS Sale_Date FROM Sales WHERE Customers_ID = (?)");										
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
			ps3.setInt(5, Callback.getCampaignID());
			ps3.executeUpdate();
			
			ps4.setFloat(1, Callback.getFuelAmount());
			ps4.setInt(2, Callback.getGasStationID());
			ps4.setInt(3, Callback.getFuelID());
			ps4.executeUpdate();
			
			/*Initiate Thread*/
			ThreadSale = Callback;
			new Thread(this);
			/*---------------*/
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return new callbackSuccess("Add sale to DB.");					
		
	}
	private Vector<?> getFuelPerStation(callbackStationFuels Callback){
		// Set variables ---------------------------------------------------------
		callbackVector LocalVector = new callbackVector();	
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
		return LocalVector;
		
	}

/**
 * Station Manager
 */
	/*
	private CallBack getStationSuppliesOrder(callbackStringArray Callback){
		// Set variables ---------------------------------------------------------
			int SaleID;
		// Build query -----------------------------------------------------------
		
		try {
			PreparedStatement ps1=conn.prepareStatement(
					"SELECT Order_ID, Fuel_ID, Gas_Station_ID, Amount, MAX(Order_Date) AS Order_Date, Order_Confirmation, Showed_To_Manager "+
					"FROM Fuel_Orders WHERE Fuel_ID = (?) AND Gas_Station_ID = (?) AND Order_Confirmation = 'Waiting'");
			
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
			ps3.setInt(5, Callback.getCampaignID());
			ps3.executeUpdate();
			
			ps4.setFloat(1, Callback.getFuelAmount());
			ps4.setInt(2, Callback.getGasStationID());
			ps4.setInt(3, Callback.getFuelID());
			ps4.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new callback_Error("Problem has occurred, user id not existe or not connection to DB.");					// If query not succeed 
		}
			return new callbackSuccess("Add sale to DB.");					
		
	}*/
	
	
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
		
		callbackSale LocalSale = ThreadSale;
		ResultSet LocalResult = AnswerResult;
		int ThresholdLimit,Capacity;
		float CurrentAmount;
		try {
			Thread.sleep(20);						//Let the result back to the client
			/*Prepare all queries*/
			
			PreparedStatement ps1=conn.prepareStatement(
					"SELECT * FROM Fuel_Per_Station WHERE Gas_Station_ID=(?) AND Fuel_ID=(?)");
			PreparedStatement ps2=conn.prepareStatement(
					"SELECT Order_ID, Fuel_ID, Gas_Station_ID, Amount, MAX(Order_Date) AS Order_Date, Order_Confirmation, Showed_To_Manager "+
					"FROM Fuel_Orders WHERE Fuel_ID = (?) AND Gas_Station_ID = (?) AND Order_Confirmation = 'Waiting'");
			PreparedStatement ps3=conn.prepareStatement(
					"INSERT INTO Fuel_Orders VALUES(NULL,(?),(?),(?),NOW(),DEFAULT,DEFAULT)");
			PreparedStatement ps4=conn.prepareStatement(
					"UPDATE Fuel_Orders SET Amount = (?) WHERE Gas_Station_ID=(?) AND Fuel_ID=(?)");
			
			
			/*Check current fuel limit after sale*/
			ps1.setInt(1, LocalSale.getGasStationID());
			ps1.setInt(2, LocalSale.getFuelID());
			LocalResult = ps1.executeQuery();
			LocalResult.next();
			ThresholdLimit = LocalResult.getInt("Threshold_Limit");
			Capacity = LocalResult.getInt("Capacity");
			CurrentAmount = LocalResult.getFloat("Current_Amount");
			if(CurrentAmount <= ThresholdLimit){
				ps2.setInt(1, LocalSale.getFuelID());
				ps2.setInt(2, LocalSale.getGasStationID());
				LocalResult = ps2.executeQuery();
				/*Set new fuel order*/
				if(!LocalResult.next()) {
					ps3.setInt(1, LocalSale.getFuelID());
					ps3.setInt(2, LocalSale.getGasStationID());
					ps3.setFloat(3, Capacity - CurrentAmount);
					ps3.executeUpdate();
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
	
}
	

