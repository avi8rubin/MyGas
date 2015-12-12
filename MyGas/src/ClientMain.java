import GUI.*;
import controller.*;
import callback.callbackBuffer;


public class ClientMain {

	
  
	public static void main(String[] args) {
	    callbackBuffer CommonBuffer = new callbackBuffer();
		Login_GUI frame = new Login_GUI();
		frame.setVisible(true);

	     /*----- Create the controller -----*/
	     
		new LoginController(frame,CommonBuffer);

		
	}

}
