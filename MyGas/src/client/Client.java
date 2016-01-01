package client;
import ocsf.client.*;
import java.io.*;
import java.util.Vector;

import callback.*;
import common.MessageType;

/**
* This class overrides some of the methods defined in the abstract
* superclass in order to give more functionality to the client.
*/
public class Client extends ObservableClient
{
//Instance variables **********************************************

	/**
	 * This buffer will allows the transfer of the callback, back to the application
	 */
	
//Constructors ****************************************************

/**
* Constructs an instance of the chat client.
* @param host The server to connect to.
* @param port The port number to connect on.
* @param CommonBuffer The buffer to transfer callback's
*/

public Client(String host, int port) throws IOException 
{ 
	 super(host, port); 									//Call the superclass constructor 
}


//Instance methods ************************************************
 
/**
* This method handles all data that comes in from the server.
* @param msg The message from the server.
*/
public void handleMessageFromServer(Object msg) 
{	  
		/************************/
		super.setChanged();
		super.notifyObservers(msg);
		/************************/	
}	

/**
* This method handles all data coming from the application           
* @param message The message from the GUI.    
*/
public void handleMessageFromClient(Object message)
{

		try {
			sendToServer(message);
		} catch (IOException e) {
			System.out.println(MessageType.Connection_To_Server_Lost.toString()
					+" | Class: Client | Function: handleMessageFromClient.");			
			//LostConnection();														//Close connection
			e.printStackTrace();
		}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

}

	/**
	* This method terminates the client.
	*/
	public void LostConnection()
	{
		try{
			closeConnection();
		}
		catch(IOException e) {}
	}
	
	public void quit()
	{
		try{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}

}