/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package controller;
import GUI.*;
import callback.callbackLostConnection;
import common.MessageType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import server.EchoServer;
import server.QueryIO;
public class ServerController {
	/**
	 * This represent if server is up or not
	 */
	public boolean ServerStatus=false;
	/**
	 * Global server GUI
	 */
	public Server_GUI server_GUI;

	/**
	 * Global ocsf server
	 */
	public EchoServer Server;
	
	/**
	 * Global jdbc server
	 */
	public QueryIO QueryServer;
	
	/**
	 * Constructor function
	 * @param SuperGUI was Created in main function
	 */
	public ServerController(Server_GUI SuperGUI) {
		server_GUI=SuperGUI;
		StartButtonHandelr();
		StopButtonHandler();	
	}
	
	/**
	 * This function create start button listener
	 * If start button pushed it call initServer
	 */
	public void  StartButtonHandelr(){
		server_GUI.btnStart.addActionListener(new ActionListener() { //add Handler for Start button	
		public void actionPerformed(ActionEvent arg0) {
		    	ClearConsol();
		    	ServerStatus=true;
				printToConsol("Server listening for connections on port: "+server_GUI.GetPort());
				//Create Server Handler//
				initServer(server_GUI.GetPort());
				initMySqlConnection(server_GUI.GetURL(), server_GUI.GetUser(), server_GUI.GetPassword());
				QueryServer.ServerStopLogoutAllUsers();
		}});
	}
	/**
	 * This function create stop button listener
	 * If pushed it stop server from listen
	 */
	public void StopButtonHandler(){
		server_GUI.btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ServerStatus==true){
					ServerStatus=false;
					printToConsol("Server is Terminate connction.");
					QueryServer.ServerStopLogoutAllUsers();
					Server.sendToAllClients(new callbackLostConnection());
					
					try {
						Thread.sleep(3000); //Wait while message send to all clients
						Server.close();
					} catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
/**
 * Create the connection to DB
 * @param Port for jdbc connection
 * @return Message Error if server connection failed
 */
	public MessageType initServer(int Port){
		QueryServer = new QueryIO();
		 Server=new EchoServer(Port,QueryServer,this.server_GUI);
		 try {
			Server.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return null;
	}//public CreateSever
	
	/**
	 * This function create connection to sql server
	 * @param Url - Drivet path
	 * @param User - User for DB
	 * @param Password - Password for DB
	 */
	public void initMySqlConnection(String Url,String User,String Password){
		String msg=null;
		msg=QueryServer.SetDriver();
		printToConsol(msg);
		msg=QueryServer.SetConnectionToDB(Url, User, Password);
		printToConsol(msg);
		msg=QueryServer.setStatement();
		printToConsol(msg);
	}
	
	/**
	 * print to console 
	 * @param str -What to print
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
	/**
	 * Clear Console screen
	 */
	public void ClearConsol(){
		this.server_GUI.textPane.setText("");
		server_GUI.Word_Counter=0;
	}
	}



