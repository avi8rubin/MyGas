package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.BadLocationException;

import GUI.Server_GUI;
import callback.*;
import ocsf.server.*;
import GUI.*;
/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  public static  String URL="";
  public static  String User="";
   public static  String Password="";
  /**
   * The Global QueryIO - all the query sent by this variable.
   */
  private  QueryIO QueryAsk;
  private Server_GUI server_GUI;
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port,QueryIO QueryAsk,Server_GUI server_GUI) 
  {
    super(port);
    this.QueryAsk=QueryAsk;
    this.server_GUI=server_GUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  @SuppressWarnings("unchecked")
public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
	
		 	 

	  Vector<?> Answer = new Vector<>()  ;
	  Vector<String> NewQuery =(Vector<String>) msg;
	  
	  /*This part send query to DB and return to client the result*/
	  Answer = QueryAsk.QueriesResolver(NewQuery);
	  
	  printToConsol("Client send: "+msg.toString()+" Client: "+client);
	  printToConsol("Server send: "+Answer.toString());
	  try {
			client.sendToClient(Answer);
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  public void printToConsol(String str){
		str=str+"\n";
		try {
			this.server_GUI.textPane.getDocument().insertString(server_GUI.Word_Counter, str, null);
			server_GUI.Word_Counter+=str.length();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  }
}
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
 