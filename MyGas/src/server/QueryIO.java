package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import callback.*;
import common.MessageType;


public class QueryIO  {
	static Connection conn = null;
	static Statement st = null;
	private static ResultSet AnswerResult;
	private static Object AnswerVector = null;

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
	
	public Object QueriesResolver(Vector<String> QueryStructure){
		
		switch((String) QueryStructure.get(0)){
		
			case "getCheckExistsUserPass":
				AnswerVector = getCheckExistsUserPass(QueryStructure.get(1), QueryStructure.get(2));
				
				break;
			case "updateChangeUserPassword":
				AnswerVector = updateChangeUserPassword(QueryStructure.get(1), QueryStructure.get(2));
				
				break;	
			
			case "updateUserLogin":
				AnswerVector = updateUserLogin(QueryStructure.get(1));				
				break;
				
		} // END switch

		
		return AnswerVector;
	}

	private Vector<?> getCheckExistsUserPass(String UserName, String Password){
		// Set variables ---------------------------------------------------------
		Vector <CallBack> LocalVector = new Vector <CallBack>();
		
		// Build query -----------------------------------------------------------
		String SqlQuery = "SELECT * FROM All_Users_Detailes "
						+ "WHERE User_Name= '"+UserName+"' ";
		
		// Send query to DB and get result ---------------------------------------
		try {
			AnswerResult = st.executeQuery(SqlQuery);	
		/**
		 * Create the callback structure for user 
		 */
				while (AnswerResult.next()) { 
					callbackUser callback_Obj = new callbackUser();
					callback_Obj.setUserName(AnswerResult.getString("User_Name"));	
					callback_Obj.setPassword(AnswerResult.getString("User_Password"));
					callback_Obj.setEmail(AnswerResult.getString("Email"));
					callback_Obj.setFirstName(AnswerResult.getString("Customer_First_Name"));
					callback_Obj.setLastName(AnswerResult.getString("Customer_Last_Name"));
					callback_Obj.setLoggedIn(AnswerResult.getString("Logged_In"));
					callback_Obj.setUserID(AnswerResult.getInt("User_ID"));
					callback_Obj.setUserTypeId(AnswerResult.getInt("User_Type_Id"));
					callback_Obj.setUserPrivilege(AnswerResult.getString("User_Privilege"));
					LocalVector.add(callback_Obj);
				}
			
				} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(LocalVector.size()==0)
				LocalVector.add(new callback_Error());
		
		return LocalVector;
		
	} // END getCheckExistsUserPass
	
	private Vector<?> updateChangeUserPassword(String UserID, String NewPassword){
		// Set variables ---------------------------------------------------------
				Vector <CallBack> LocalVector = new Vector <CallBack>();
				
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Users SET User_Password=(?) WHERE User_ID=(?);");
			
		// Send query to DB  -----------------------------------------------------
			ps.setString(1, NewPassword);
			ps.setInt(2, Integer.valueOf(UserID));			
			ps.executeUpdate();
		} catch (SQLException e) {
			LocalVector.add(new callback_Error());					// If query not succeed
			e.printStackTrace();
		}
		if(LocalVector.size()==0)
			LocalVector.add(new callbackSuccess());					// 	Query not succeed
		
		return LocalVector;
	}
	
	private Vector<?> updateUserLogin(String UserID){
		// Set variables ---------------------------------------------------------
			Vector <CallBack> LocalVector = new Vector <CallBack>();
				
		// Build query -----------------------------------------------------------
		try {
			PreparedStatement ps=conn.prepareStatement("UPDATE Users SET Logged_In='Yes' WHERE User_ID=(?);");
			
		// Send query to DB  -----------------------------------------------------
			ps.setInt(1, Integer.valueOf(UserID));			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			LocalVector.add(new callback_Error());					// If query not succeed
			e.printStackTrace();
		}
		if(LocalVector.size()==0)
			LocalVector.add(new callbackSuccess());					// 	Query not succeed
		
		return LocalVector;
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
	
	
}
	

