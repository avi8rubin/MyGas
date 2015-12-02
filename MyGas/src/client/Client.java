package client;
import ocsf.client.*;
import java.io.*;
import java.util.Vector;

import callback.*;

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
	private long ThreadNum;
	private Object ThreadController;
//Constructors ****************************************************

/**
* Constructs an instance of the chat client.
* @param host The server to connect to.
* @param port The port number to connect on.
* @param CommonBuffer The buffer to transfer callback's
*/

public Client(String host, int port, callbackBuffer CommonBuffer) throws IOException 
{ 
	 super(host, port); //Call the superclass constructor
	 this.CommonBuffer=CommonBuffer;
	 openConnection();
}


//Instance methods ************************************************
 
/**
* This method handles all data that comes in from the server.
* @param msg The message from the server.
*/
public synchronized void handleMessageFromServer(Object msg) 
{	  
	System.out.println(Thread.currentThread().getId());
		  Vector<?> NewCallBack = (Vector<?>) msg; 							// Casting to Vector object that received from server
		  CallBack Message = (CallBack) NewCallBack.get(0);					// Casting to CallBack object
		 // while (CommonBuffer.HasNewCallBackAndBufferFree() == false); 	// Waits for new callback
		  while(CommonBuffer.setNewCallBack(Message) == false);				// Set the new callback
		  ThreadController.notify();
}	

/**
* This method handles all data coming from the application           
* @param message The message from the GUI.    
*/
public synchronized void handleMessageFromClient(Object message, Object ThreadController)
{
		this.ThreadController=ThreadController;
		try {
			sendToServer(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ThreadController.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

