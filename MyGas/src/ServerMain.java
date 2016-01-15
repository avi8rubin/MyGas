/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
import GUI.Server_GUI;
import controller.*;
public class ServerMain {

	/**
	 * Create GUI and start server
	 * @param args
	 */
	public static void main(String[] args) {
					Server_GUI Server_GUI=new GUI.Server_GUI(); //Create new GUI windows
					new ServerController(Server_GUI); // Create new Controller 
					Server_GUI.frame.setVisible(true); // set GUI visible					
	}
}