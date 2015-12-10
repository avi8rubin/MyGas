package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import GUI.MarketingRepresentativeGUI;
import callback.callbackBuffer;
import client.Client;

public class MarketingRepresentativeController extends Controller{

	private MarketingRepresentativeGUI GuiScreen;
	
	public MarketingRepresentativeController(Client Server, callbackBuffer CommonBuffer, MarketingRepresentativeGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
