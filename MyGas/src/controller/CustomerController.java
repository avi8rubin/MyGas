package controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.FeatureDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

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
import callback.callbackSuccess;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;

/**
 * CustomerController
 * @author ליטף
 *
 */
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
		
	//calculate
	private String FuelStr;
	private String dateStr;
	private String timeStr;
	
	//new sale details
	private callbackSale sale;

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
		ErrorAddressLabel=GuiScreen.getErrorAddressLabel();
		
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Buy Home Fuel")){
			ContainerCardCenter.show(CenterCardContainer, "BuyHomeFuel");
		
			GuiScreen.DisablePayButton();
			GuiScreen.setFuelAmount("");
			GuiScreen.setDate();
			GuiScreen.setTime("");
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
		
		sale= new callbackSale(MessageType.setNewHomeFuelSale);
		sale.setFuelID(3);
			
		//check validation of fuel amount
		FuelStr= GuiScreen.getFuelAmount();
		if(Checks.isFloat(FuelStr) && !FuelStr.equals("")){
			ErrorAmount.setText("");
			checkFields++;
			sale.setFuelAmount(Float.parseFloat(FuelStr));
		}
		else
			ErrorAmount.setText("*Incorrect Fuel Amount");
		
		//check validation of delivery date
		dateStr=GuiScreen.getDate();
		if(Checks.isDateValid(dateStr) || !dateStr.equals("")){
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
			ErrorAddressLabel.setText("");
		}
		
		//check time validation
		timeStr=GuiScreen.getTime();
		timeStr=timeStr.substring(0,2)+":"+timeStr.substring(5,7);
		if(Checks.isTimeValid(timeStr, dateStr) && !timeStr.equals("/") ){
			checkFields++;
			sale.setDeliveryTime(timeStr);
			ErrorDeliveryTimeLabel.setText("");
		}
		else
		ErrorDeliveryTimeLabel.setText("*Incorrect Time");

		//check if all fields added successfully
		if(checkFields==4) {
			GuiScreen.EnablePayButton();
			Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
		}
		}
		

			
		private void Server_CalculatePayment(callbackStringArray TariffTable){

			int FuelID=0;
			float FuelPrice=0;
			float SumPrice=0;
			int Differacne=0;
				
			Object[][] arr=TariffTable.getData();
			for(int i=0;i<arr.length;i++){
				if(arr[i][1].toString().equals("Home Fuel"))
					{
						FuelPrice=Float.parseFloat(arr[i][3].toString());
						FuelID=Integer.parseInt(arr[i][0].toString());
					}
				}
			sale.setFuelID(FuelID);
			//Immediate order: (within 6 hours) =Cost + 2% of the price of fuel
				DateFormat TimeFormat = new SimpleDateFormat("HH:mm");
				DateFormat DateFormat = new SimpleDateFormat("dd/MM/yy");
				String currDate=DateFormat.format(new Date()).toString();

				String currTimeHour=TimeFormat.format(new Date()).toString().substring(0,2);	
				String currTimeMin=TimeFormat.format(new Date()).toString().substring(3,5);				
				int DeliveryHour=Integer.parseInt(timeStr.substring(0,2));
				int DeliveryMin=Integer.parseInt(timeStr.substring(3,5));
				
				if(currDate.equals(dateStr)){
					Differacne=(DeliveryHour-Integer.parseInt(currTimeHour))*60+
								(DeliveryMin-Integer.parseInt(currTimeMin));
					if(Differacne<=360){
						SumPrice=FuelPrice*Float.parseFloat(FuelStr);
					//up to 6 hours -cost Shipping is an additional 2%
						GuiScreen.setOrderDetailsLabel("Order Details:");
						GuiScreen.setShowOrderDetailsLabel("<html> Fuel Price:<br>"
															+ "+<br>"
															+ "  Liters:<br>"
															+ "		____________________<br>"
															+ "  Sum:<br><br>"
															+ "+Shipping:</html>");
						
						GuiScreen.setFuelPriceLabel(Float.toString(FuelPrice));
						GuiScreen.setLitersLabel(FuelStr);
						GuiScreen.setsumLabel(Float.toString(SumPrice));
						SumPrice+=SumPrice*0.02;
						GuiScreen.setShippingLabel(Float.toString(SumPrice));
						GuiScreen.setRemaraksLabel("<html>*Immediate order-within 6 hours "
												 + "is fuel cost plus 2% shipping from the"
												 + "fuel price</html>");
						GuiScreen.setCalcPricetextArea(Float.toString(SumPrice));
					}
					
				}
		}
		private void HandleCheckCustomerCreditCard(){
			
			ErrorAmount.setText("");
			ErrorDeliveryTimeLabel.setText("");
			DateLabel.setText("");
			GuiScreen.setTime("");
			
			callbackCustomer customer=new callbackCustomer(MessageType.getCustomer);
			customer.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(customer);
		}
		
		private void Server_HandleCheckCustomerCreditCard(callbackCustomer customer) {
			if(customer.getCreditCard().equals("")){
				JOptionPane.showMessageDialog(null, "Customer "+GuiScreen.getCurrentUserName()+
						"\nCan't buy home fuel because there is no credit card information"+
						"in the system\nPlease contact a service representative to proceed", 
						"", JOptionPane.INFORMATION_MESSAGE);
				GuiScreen.DisableAllComponents();
				}
			GuiScreen.DisablePayButton();
		}
		/**
		 * HandlePayButton- create new sale in DB with sale details
		 * @param callbackSale sale
		 */
		private void HandlePayButton() {
			
			sale.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(sale);
		}	
		
		private void HandleCheckFuelOrder() {
			
			Object[]arr= new Object[1];
			callbackStringArray orders=new callbackStringArray(MessageType.getHomeFuelOrders);
			arr[0]=GuiScreen.getCurrentUserID();
			orders.setVariance(arr);

			Server.handleMessageFromClient(orders);
			
		}
		@Override
		public void update(Observable o, Object arg) {
			if(arg instanceof CallBack){	
				switch(((CallBack) arg).getWhatToDo()){
					case getFuelsDetailes:
						callbackStringArray TariffTable = (callbackStringArray) arg;		
						Server_CalculatePayment(TariffTable);
						break;
					case setWaitingTariff:
						
						break;

					case setNewHomeFuelSale:
						CallBack temp =  (CallBack) arg;
						if(temp instanceof callbackSuccess){
							JOptionPane.showMessageDialog(null, "Your order was added successfully, "
									+ "you can watch your orders in the 'Check Fuel Orders'", 
									"", JOptionPane.INFORMATION_MESSAGE);
							}
						else {
							JOptionPane.showMessageDialog(null, "Error has occurred, no charge was made",
									"", JOptionPane.INFORMATION_MESSAGE);
							}	
						break;
						
					case getCustomer:
						callbackCustomer customer =  (callbackCustomer)arg;
						Server_HandleCheckCustomerCreditCard(customer);
						break;

					case getHomeFuelOrders:
						CallBack temp1 =  (CallBack) arg;
						if(temp1 instanceof callbackSuccess){
							JOptionPane.showMessageDialog(null, "No Orders to show", 
									"", JOptionPane.INFORMATION_MESSAGE);
							}
						else {
							callbackStringArray orders=(callbackStringArray) temp1;
							GuiScreen.setHomeFuelOrdersTable(orders.getDefaultTableModel());
							}	
						break;
				/*Don't change!*/
				default:
					super.update(o, arg);
					break;
				/*-------------*/
				}					
				}
			else if(arg instanceof Vector){
				//

					
			}
		}


}
		
