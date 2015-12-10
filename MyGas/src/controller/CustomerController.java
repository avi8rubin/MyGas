package controller;

import java.awt.event.ActionEvent;

import GUI.CEOGUI;
import GUI.CustomerGUI;
import callback.callbackBuffer;
import client.Client;

public class CustomerController extends Controller{

	private CustomerGUI GuiScreen;
	
	public CustomerController(Client Server, callbackBuffer CommonBuffer, CustomerGUI GuiScreen) {
		super(Server, CommonBuffer,GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
