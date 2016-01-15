/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/

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
