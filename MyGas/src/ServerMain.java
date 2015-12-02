import GUI.Server_GUI;
import controller.*;
public class ServerMain {

	public static void main(String[] args) {

					Server_GUI Server_GUI=new GUI.Server_GUI(); //Create new GUI windows
					new ServerController(Server_GUI); // Create new Controller 
					Server_GUI.frame.setVisible(true); // set GUI visible

	}
}