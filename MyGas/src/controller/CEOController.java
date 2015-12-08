package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import callback.callbackBuffer;
import client.Client;

public class CEOController extends Controller {

	private CEOGUI GuiScreen;	
	
	public CEOController(Client Server, callbackBuffer CommonBuffer, CEOGUI GuiScreen) {
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
