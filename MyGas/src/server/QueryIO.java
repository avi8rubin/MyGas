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
		//Vector <CallBack> LocalVector = new Vector <CallBack>();
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
	

