/************************************************************************** 
 * Copyright (�) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/

package server;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import GUI.Server_GUI;
import callback.CallBack;
import callback.callbackVector;
import callback.callback_Error;
import common.MessageType;
import ocsf.server.*;

/**
 * This Class Create Server listener that get Query and send callback
 * @author Ohad
 */
public class EchoServer extends AbstractServer 
{
  /**
   * Global variables 
   * DEFAULT_PORT is port to listen
   * URL - Driver to DB
   * User - Username to root mysql DB
   * Password - Password to root mysql DB
   */
  final public static int DEFAULT_PORT = 5555;
  public static  String URL="";
  public static  String User="";
  public static  String Password="";
 
  /**
   * Time for text field.
   */
  private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
  private Calendar cal;
  
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
   * @param port - The port number to connect on.
   * @param QueryAsk - Object of QueryIO
   * @param server_GUI - Gui server
   */
  public EchoServer(int port,QueryIO QueryAsk,Server_GUI server_GUI) 
  {
    super(port);
    this.QueryAsk=QueryAsk;
    this.server_GUI=server_GUI;
  }
  /**
   * This method handles any messages received from the client.
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
	  cal = Calendar.getInstance();								//Current time  
	  Object Answer;
	  if (msg instanceof CallBack){
		  printToConsol(dateFormat.format(cal.getTime())+": Client "+client+", Send: "+((CallBack)msg).getWhatToDo().toString());
		  Answer = QueryAsk.CallbackResolver(msg);
		  
		  /*------ Print to console panel -------*/
		  if (Answer instanceof CallBack)
			  printToConsol(dateFormat.format(cal.getTime())+": Server answer: "+((CallBack)Answer).getClass().toString());
		  if (Answer instanceof Vector)
			  printToConsol(dateFormat.format(cal.getTime())+": Server answer: "+((callbackVector)Answer).getClass().toString());
	  }
	  else if(msg instanceof Vector){
		  Answer = QueryAsk.VectorResolver(msg);
	  }
	  else {
		  Answer = new callback_Error("Can't determine which object was sent to server.");
	  }
	  
	  /*------- Send answer back to client ---------*/
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

 