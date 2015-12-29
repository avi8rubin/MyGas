package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.CEOGUI;
import GUI.CustomerGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackStringArray;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;

public class CustomerController extends Controller{

	private CustomerGUI GuiScreen;
	private CardLayout ContainerCardCenter;

	
	private JButton BuyHomeFuelButton;
	private JButton CheckFuelOrdersButton;
	private JButton PayButton;
	private JButton CalculateButton;
	
	private JLabel ErrorAmount;
	private JLabel DateLabel;
	private JLabel ErrorAddressLabel;
	private JLabel ErrorDeliveryTimeLabel;

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
		
		CalculateButton=GuiScreen.getCalcButton();
		CalculateButton.addActionListener(this);
		CalculateButton.setActionCommand("Calc Button");
		
		ErrorAmount=GuiScreen.ErrorAmoutLabel();
		DateLabel=GuiScreen.DateLabel();
		ErrorDeliveryTimeLabel=GuiScreen.getErrorDeliveryTimeLabel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Buy Home Fuel")){
			ContainerCardCenter.show(CenterCardContainer, "BuyHomeFuel");
			HandleCheckCustomerCreditCard();
		}		
		
		else if(e.getActionCommand().equals("Pay Button")){
			HandlePayButton();
		}
		else if(e.getActionCommand().equals("Calc Button")){
			HandleCalcButtonPressed();
		}
		else if(e.getActionCommand().equals("Check Fuel Orders")){
			ContainerCardCenter.show(CenterCardContainer, "HomeFuelOrdersCenterLayer");
			HandleCheckFuelOrder();
		}
			
	}
	
	
	private void HandleCalcButtonPressed(){
			
		int checkFields=0;
		ErrorAmount.setText("");
		ErrorDeliveryTimeLabel.setText("");
		DateLabel.setText("");
			
		callbackSale sale= new callbackSale(MessageType.setNewHomeFuelSale);
		sale.setFuelID(3);
			
		//check validation of fuel amount
		String str= GuiScreen.getFuelAmount();
		if(Checks.isFloat(str)){
			ErrorAmount.setText("");
			checkFields++;
			sale.setFuelAmount(Float.parseFloat(str));
		}
		else
			ErrorAmount.setText("*Incorrect Fuel Amount");
		//check validation of delivery date
		String dateStr=GuiScreen.getDate();
		if(Checks.isDateValid(dateStr))
		{
			checkFields++;
			DateLabel.setText("");
			sale.setDeliveryDate(dateStr);
		}
		else
			DateLabel.setText("*Incorrect Date");
		//check Address exist
		if(GuiScreen.getAddrres().equals(""))
			ErrorAddressLabel.setText("*Address Missing");
		else{
			checkFields++;
			sale.setAddress(GuiScreen.getAddrres());
		}
		//check time validation
		String timeStr=GuiScreen.getTime();
		timeStr=timeStr.substring(0,2)+"/"+timeStr.substring(5,7);
		if(timeStr.equals("")|| !Checks.isTimeValid(timeStr, dateStr))
			ErrorDeliveryTimeLabel.setText("*Incorrect Time");
		else
			checkFields++;
			
		if(checkFields==4) 
			GuiScreen.EnablePayButton();

		}
		
		
		private void HandleCheckCustomerCreditCard(){
			
			ErrorAmount.setText("");
			ErrorDeliveryTimeLabel.setText("");
			DateLabel.setText("");
			GuiScreen.setTime();
			
			callbackCustomer customer=new callbackCustomer(MessageType.getCustomer);
			customer.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(customer);
			customer =  (callbackCustomer) getCallBackFromBuffer();
			if(customer.getCreditCard().equals("")){
				
				JOptionPane.showMessageDialog(null, "Customer "+GuiScreen.getCurrentUserName()+
						"\nCan't buy home fuel because there is no credit card information"+
						"in the system\nPlease contact a service representative to proceed", 
						"", JOptionPane.INFORMATION_MESSAGE);
				GuiScreen.DisableAllComponents();
				}
			GuiScreen.DisablePayButton();
		}
		
		private void HandlePayButton() {
			
		}	
		
		private void HandleCheckFuelOrder() {
			
			
		}
}
		
