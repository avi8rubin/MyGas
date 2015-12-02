package server;
import java.sql.Connection;
import java.sql.DriverManager;
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
	private static Vector<?> AnswerVector = null;

	/**
	 * This function create Driver connection
	 * @return MessageType
	 */
	public String SetDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e1) {
			return MessageType.No_Driver.toString();
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
		return MessageType.No_Connection_To_Database.toString();
		}	
		return MessageType.Connection_To_Database_Succeded.toString();
	}
	public String setStatement(){
	
		try {
			st =  conn.createStatement();
		} catch (SQLException e) {
			return MessageType.Statement_Not_succeded.toString();
		}
		return MessageType.statement_Succeded.toString();
	}
	
	public Vector<?> QueriesResolver(Vector<String> QueryStructure){
		
		switch((String) QueryStructure.get(0)){
		
			case "getCheckExistsUserPass":
				AnswerVector = getCheckExistsUserPass(QueryStructure.get(1), QueryStructure.get(2));
				
				break;
			
		} // END switch

		
		return AnswerVector;
	}

	private Vector<?> getCheckExistsUserPass(String UserName, String Password){

		/*String SqlQuery = "SELECT A.User_ID,A.User_Type_Id,B.User_Privilege FROM Users A "
						+ "LEFT OUTER JOIN User_Type B ON A.User_Type_Id=B.User_Type_Id "
						+ "WHERE A.User_Name= '"+UserName+"' "
						+ "AND A.User_Password= '"+Password+"' "
						+ "AND A.Logged_In = 'No'";*/
		String SqlQuery = "SELECT * FROM All_Users_Detailes "
						+ "WHERE User_Name= '"+UserName+"' ";
	 
		try {
			AnswerResult = st.executeQuery(SqlQuery);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Vector <CallBack> LocalVector = new Vector <CallBack>();
		System.out.println("resolver");
		/**
		 * Create the callback structure for user 
		 */
		try {
				
			
				while (AnswerResult.next()) { 
					callbackUser callback_Obj = new callbackUser();
					callback_Obj.setUserName(UserName);	
					callback_Obj.setPassword(Password);
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
		{
			callback_Error callback_Error = new callback_Error();
			LocalVector.add(callback_Error);
		}
		return LocalVector;
		
	} // END getCheckExistsUserPass
	
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
	

