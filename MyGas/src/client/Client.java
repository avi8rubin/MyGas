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
public class Client extends AbstractClient
{
//Instance variables **********************************************

	/**
	 * This buffer will allows the transfer of the callback, back to the application
	 */
	private callbackBuffer CommonBuffer = null;
	private volatile Boolean BufferInUse;
	
//Constructors ****************************************************

/**
* Constructs an instance of the chat client.
* @param host The server to connect to.
* @param port The port number to connect on.
* @param CommonBuffer The buffer to transfer callback's
*/

public Client(String host, int port, callbackBuffer CommonBuffer) throws IOException 
{ 
	 super(host, port); 									//Call the superclass constructor
	 this.CommonBuffer=CommonBuffer;
	 BufferInUse = CommonBuffer.getBufferInUse(); 			//Common synchronized send/receive flag
	 
	 
}


//Instance methods ************************************************
 
/**
* This method handles all data that comes in from the server.
* @param msg The message from the server.
*/
public void handleMessageFromServer(Object msg) 
{	  
		if (msg instanceof Vector){
			Vector<?> NewCallBack = (Vector<?>) msg; 							// Casting to Vector object that received from server
			CallBack Message = (CallBack) NewCallBack.get(0);					// Casting to CallBack object
			while (CommonBuffer.setNewCallBack(Message) == false);				// Set the new callback
		}
		if ( msg instanceof CallBack){
			CallBack Message = (CallBack) msg;
			while (CommonBuffer.setNewCallBack(Message) == false);				// Set the new callback
		}
		  BufferInUse = true;													// Release client to read callback
}	

/**
* This method handles all data coming from the application           
* @param message The message from the GUI.    
*/
public void handleMessageFromClient(Object message)
{
		BufferInUse = false;
		int ReleaseCounter = 50;
		try {
			sendToServer(message);
		} catch (IOException e) {
			System.out.println(MessageType.Connection_To_Server_Lost.toString()+" | Class: Client | Function: handleMessageFromClient.");
			e.printStackTrace();
		}
		/*---- Wait until callback arrived from server -----*/
		while(!BufferInUse && ReleaseCounter!=0){	
			if(--ReleaseCounter == 0) 
				System.out.println(MessageType.Connection_To_Server_Lost.toString()+" | Class: Client | Function: handleMessageFromClient.");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}

	/**
	* This method terminates the client.
	*/
	public void quit()
	{
	 try
	 {
	   closeConnection();
	 }
	 catch(IOException e) {}
	 System.exit(0);
	}

}