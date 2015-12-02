package controller;
import GUI.*;
import common.MessageType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.text.BadLocationException;

import org.omg.CORBA.INTERNAL;

import ocsf.server.*;
import server.EchoServer;
import server.QueryIO;
public class ServerController {
	
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
				printToConsol("Server listening for connections on port: "+server_GUI.GetPort());
				//Create Server Handler//
				initServer(server_GUI.GetPort());
				initMySqlConnection(server_GUI.GetURL(), server_GUI.GetUser(), server_GUI.GetPassword());
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
			printToConsol("Server is Terminate connction");
			Server.stopListening();
			//QueryServer.s
			}
		});
	}
	
/**
 * 
 * @param Port for mysql
 * @param Url for mysql
 * @param User for mysql
 * @param Password for mysql 
 * @return Message Error if server connection failed
 */
	public MessageType initServer(int Port){
		QueryServer = new QueryIO();
		 Server=new EchoServer(Port,QueryServer,this.server_GUI);
		 try {
			Server.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		 
		return null;
	}//public CreateSever
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
	 * print to consol 
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
	 * Clear Consol screen
	 */
	public void ClearConsol(){
		this.server_GUI.textPane.setText("");
		server_GUI.Word_Counter=0;
	}
	}



