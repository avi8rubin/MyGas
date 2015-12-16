package GUI;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class MarketingRepresentativeGUI extends abstractPanel_GUI{
	
	
	private static final long serialVersionUID = 1L;

	public MarketingRepresentativeGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);
		// TODO Auto-generated constructor stub
		LoginScreen=LoginScreen;
	}

}
