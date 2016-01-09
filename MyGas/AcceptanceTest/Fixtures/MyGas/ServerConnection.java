package Fixtures.MyGas;

import java.io.IOException;
import java.util.Observer;

import client.Client;
import fit.ActionFixture;
/**
 * An abstract class that was made to create connection to server 
 * for all FitTests 
 * @author Ohad
 *
 */
public abstract class ServerConnection extends ActionFixture implements Observer{
	protected Client Server;
	/**
	 * Create default connection
	 */
	public ServerConnection(){
		try {
			Server = new Client("127.0.0.1", 5555);
			Server.openConnection();	//Try open connection to server
			Server.addObserver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Create specific connection to server
	 * @param host - Server IP
	 * @param port - Connection port (example: 5555)
	 */
	public ServerConnection(String host,int port){
		try {
			Server = new Client(host, port);
			Server.openConnection();	//Try open connection to server
			Server.addObserver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void Sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
