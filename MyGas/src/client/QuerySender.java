package client;
import java.util.Arrays;
import java.util.Vector;


public class QuerySender  {
	private final Client Server;

	public QuerySender(Client Server) {
		 this.Server=Server;
	}

	public void SendMessageToServer(Vector<String> Message){
		Server.handleMessageFromClient(Message);
	}
	
	public void getCheckExistsUserPass(String UserName, String Password){
		Vector<String> Query = new Vector<String>(
				Arrays.asList("getCheckExistsUserPass", UserName, Password));
		SendMessageToServer(Query);
	} 
	
	public void updateChangeUserPassword(int UserID, String NewPassword){
		Vector<String> Query = new Vector<String>(
				Arrays.asList("updateChangeUserPassword", Integer.toString(UserID), NewPassword));
		SendMessageToServer(Query);
	}
	
	public void updateUserLogin(int UserID){
		Vector<String> Query = new Vector<String>(
				Arrays.asList("updateUserLogin", Integer.toString(UserID)));
		SendMessageToServer(Query);
	}
	
	
}
	

