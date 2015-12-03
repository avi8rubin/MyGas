import java.io.IOException;

import GUI.*;
import client.Client;
import controller.*;
import callback.callbackBuffer;


public class ClientMain {
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
	
  
	public static void main(String[] args) {
		String host = "";

	    try {
	      host = args[0]; }
	   
	    catch(ArrayIndexOutOfBoundsException e){
	      host = "localhost"; }
		
	    
	    callbackBuffer CommonBuffer = new callbackBuffer();
		Login_GUI frame = new Login_GUI();
		frame.setVisible(true);
	    /**
	     * Create the Client connection
	     */
		try {
			new LoginController(frame, new Client (host,DEFAULT_PORT,CommonBuffer),CommonBuffer);
		} catch (IOException e) {
			frame.NoConnectionToServer(); 								//Set label on gui
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
