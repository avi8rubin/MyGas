/**
 * This Class Create Server listener that get Query and send callback
 */
package server;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import GUI.Server_GUI;
import common.MessageType;
import ocsf.server.*;

public class EchoServer extends AbstractServer 
{
  /**
   * Global variables 
   * DEFAULT_PORT is port to listen
   * @param URL - is the path to DB
   * @param User - is Username to DB
   * @param Password - password to DB
   */
  final public static int DEFAULT_PORT = 5555;
  public static  String URL="";
  public static  String User="";
  public static  String Password="";
  
  /**
   * The Global QueryIO - all the query sent by this variable.
   */
  private  QueryIO QueryAsk;
  /**
   * The Global Server GUI
   */
  private Server_GUI server_GUI;
  
  /**
   * Constructs an instance of the echo server.
   * @param port The port number to connect on.
   */
  public EchoServer(int port,QueryIO QueryAsk,Server_GUI server_GUI) 
  {
    super(port);
    this.QueryAsk=QueryAsk;
    this.server_GUI=server_GUI;
  }
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
	  Answer = (Vector<?>) QueryAsk.QueriesResolver(NewQuery);
	  
	  /*This part print to console panel*/
	  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  Calendar cal = Calendar.getInstance();
	  printToConsol(dateFormat.format(cal.getTime())+": Client send: "+msg.toString()+" Client: "+client);
	  printToConsol(dateFormat.format(cal.getTime())+": Server send: "+Answer.toString());
	  
	  /*This part send to client*/
	  try {
			client.sendToClient(Answer);
		} catch (IOException e) {
			printToConsol(MessageType.Lost_Connection_With_Client.toString()+" | Class: EchoServer | Function: handleMessageFromClient.");
			e.printStackTrace();
		} 	
	}

    /**
     * This function print to console panel
     * @param str what to print
     */
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

 