package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import GUI.StationsGUI;
import callback.callbackBuffer;
import client.Client;

public class StationsController extends Controller {

	private StationsGUI GuiScreen;
	
	public StationsController(Client Server, callbackBuffer CommonBuffer, StationsGUI GuiScreen) {
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
