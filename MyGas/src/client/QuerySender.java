package client;
import java.util.Arrays;
import java.util.Vector;


public class QuerySender  {
	private final Client Server;

	public QuerySender(Client Server) {
		 this.Server=Server;
	}

	public void SendMessageToServer(Vector<String> Message){
		
		
		
		Server.handleMessageFromClient(Message,Thread.currentThread() );
		
		//	Thread.currentThread().wait();
	
		// System.out.println(Thread.currentThread().getId());
		/*
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
	public void getCheckExistsUserPass(String UserName, String Password){
		Vector<String> Query = new Vector<String>(
				Arrays.asList("getCheckExistsUserPass", UserName, Password));
		SendMessageToServer(Query);
	} 
	
	
}
	

