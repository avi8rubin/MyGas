package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import GUI.StationManagerGUI;
import callback.callbackBuffer;
import client.Client;

public class StationManagerController extends Controller{

	private StationManagerGUI GuiScreen;
	
	public StationManagerController(Client Server, callbackBuffer CommonBuffer, StationManagerGUI GuiScreen) {
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
