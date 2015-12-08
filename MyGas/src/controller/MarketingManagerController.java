package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import GUI.CustomerGUI;
import GUI.MarketingManagerGUI;
import callback.callbackBuffer;
import client.Client;

public class MarketingManagerController extends Controller {

	private MarketingManagerGUI GuiScreen;
	
	public MarketingManagerController(Client Server, callbackBuffer CommonBuffer, MarketingManagerGUI GuiScreen) {
		super(Server, CommonBuffer);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
