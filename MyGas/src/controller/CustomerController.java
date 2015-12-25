package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import GUI.CEOGUI;
import GUI.CustomerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCustomer;
import callback.callbackStringArray;
import callback.callback_Error;
import client.Client;
import common.MessageType;

public class CustomerController extends Controller{

	private CustomerGUI GuiScreen;
	private CardLayout ContainerCardCenter;

	
	private JButton BuyHomeFuelButton;
	private JButton CheckFuelOrdersButton;
	
	private JButton PayButton;

	public CustomerController(Client Server, callbackBuffer CommonBuffer, CustomerGUI GuiScreen) {
		super(Server, CommonBuffer,GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
			
		BuyHomeFuelButton = GuiScreen.getBuyHomeFuelButton();
		BuyHomeFuelButton.addActionListener(this);
		BuyHomeFuelButton.setActionCommand("Buy Home Fuel");							
		
		CheckFuelOrdersButton = GuiScreen.getCheckFuelOrdersButton();
		CheckFuelOrdersButton.addActionListener(this);
		CheckFuelOrdersButton.setActionCommand("Check Fuel Orders");
		
		PayButton=GuiScreen.getPayButton();
		PayButton.addActionListener(this);
		PayButton.setActionCommand("Pay Button");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Buy Home Fuel")){
			ContainerCardCenter.show(CenterCardContainer, "BuyHomeFuel");
			HandleCheckCustomerCreditCard();
		}		
	}
	
	
	//	private void HandleCalcButtonPressed(){}
		
		
		private void HandleCheckCustomerCreditCard(){
			callbackCustomer customer=new callbackCustomer(MessageType.setNewCampaign);
			customer.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(customer);
			CallBack temp =  (CallBack) getCallBackFromBuffer();
			if(temp instanceof callback_Error){
				
				JOptionPane.showMessageDialog(null, "Customer "+GuiScreen.getCurrentUserName()+
						"\nCan't buy home fuel because there is no credit card information"+
						"in the system\nPlease contact a service representative to proceed", 
						"", JOptionPane.INFORMATION_MESSAGE);
				GuiScreen.DisableAllComponents();
				}

		}
		
}
