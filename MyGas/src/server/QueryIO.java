package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JComboBox;

import callback.*;
import common.MessageType;


public class QueryIO  {
	static Connection conn = null;
	static Statement st = null;
	private static ResultSet AnswerResult;
	private static Object AnswerObject = null;

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
				
		default:
			AnswerObject = new callback_Error("Not a callback object, send legal callback or you don't fill 'WhatToDo'.");
			break;
				
		} // END switch

		
		return AnswerObject;
	}

	public Object VectorResolver(Object SwitchCallback){
		Vector AnswerVector = new Vector();
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
						+ "WHERE User_Name= '"+User.getUserName()+"' ";
		 	
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
		String[] Combo;
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
		int ColNum;
		int RowNum =0;
		// Build query -----------------------------------------------------------
		//Campaign Patterns
		String SqlQuery1 = "SELECT Campaign_Patterns_ID, Campaign_Description FROM Campaign_patterns";
		//Active Campaign
		String SqlQuery2 = 	"SELECT Campaign_ID "+
							",DATE_FORMAT(Start_Campaign,'%d/%m/%Y') AS Start_Campaign "+
							",DATE_FORMAT(End_Campaign,'%d/%m/%Y') AS End_Campaign "+
							",CONCAT(Campaign_Description,' (',CAST(DATE_FORMAT(Start_Campaign,'%d/%m/%Y') AS CHAR),' - ',CAST(DATE_FORMAT(End_Campaign,'%d/%m/%Y') AS CHAR),')') AS Campaign_Description "+
							"FROM All_Campaign_On_System "+
							"WHERE IS_Active = 'Yes'";
		
		
		
		// Send query to DB and get result ---------------------------------------
		try {
			
			//Campaign Patterns			
			AnswerResult = st.executeQuery(SqlQuery1);					//Send query to DB
			AnswerResult.last();										//--------------------------
			RowNum = AnswerResult.getRow();								//Get the number of rows   |
			AnswerResult.beforeFirst();									//--------------------------
			Combo = new String[RowNum];									//Set values for combobox object
			RowNum=0;
			while (AnswerResult.next()) { 	
				Combo[RowNum] = AnswerResult.getString("Campaign_Description");
			}
			RowNum=0;
			Callback.setComboBoxStringArray(Combo);
			
			
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
				
				ps1.setString(1, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getCEOConfirmation());
				ps1.setInt(2, ((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getTariffUpdateID());
				ps1.executeUpdate();
				if(((callbackWaitingTariff)UpdateWaitingTariff.get(i)).getCEOConfirmation().equals("Yes")){	
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
				st.executeUpdate("UPDATE Users SET Logged_In='No' where User_Type_Id<>2;");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	
		
	}
	
}
	

