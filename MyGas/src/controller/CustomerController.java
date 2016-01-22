/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.CustomerGUI;
import callback.CallBack;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackStringArray;
import callback.callbackSuccess;
import client.Client;
import common.Checks;
import common.MessageType;

/**
 * CustomerController-  control all the components of the Customer Gui
 * handle the orders table
 * handle the option of buying home fuel
 * @author litaf
 */
public class CustomerController extends Controller{

	int checkFields=0;
 
	private CustomerGUI GuiScreen;
	private CardLayout ContainerCardCenter;
	
	private JButton BuyHomeFuelButton;
	private JButton CheckFuelOrdersButton;
	private JButton PayButton;
	private JButton CancelButton;
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

	/**
	 * CustomerController- control all the components of the Customer Gui
	 * handle the Buy home fuel features: insert the sale details, calculate price and right the sale
	 * details in the DB
	 * handle the information of all the customer past purchases 
	 * @param Server - Connection to server
	 * @param GuiScreen - The gui screen use in some methods
	 * @author Litaf
	 */
	public CustomerController(Client Server, CustomerGUI GuiScreen) {
		super(Server,GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		/**
		 * Add action listener to buttons
		 */
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
		
		CancelButton=GuiScreen.getCancelButton();
		CancelButton.addActionListener(this);
		CancelButton.setActionCommand("Cancel Button");
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Buy Home Fuel")){
			ContainerCardCenter.show(CenterCardContainer, "BuyHomeFuel");
			GuiScreen.cleanScreen();
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
		else if(e.getActionCommand().equals("Cancel Button")){
			HandleCancelButton();
		}
			
	}
	
	/**
	 * HandleCalcButtonPressed- check if all relevant fields are correctly filled and print
	 * to the Gui the description of the sale price.
	 * @author Litaf
	 */
	private void HandleCalcButtonPressed(){
		
		checkFields=CheckAllValidFileds();
		//check if all fields added successfully
		if(checkFields==4) {
			GuiScreen.EnablePayButton();
			GuiScreen.EnableCancleButton();
			Server.handleMessageFromClient(new callbackStringArray(MessageType.getFuelsDetailes));
		}
		else{
			GuiScreen.DisablePayButton();
			GuiScreen.DisableCancleButton();
		}
			
		}
	/**
	 * CheckAllValidFileds- check if all relevant fields are correctly filled 
	 * @return status about the fields
	 * @author Litaf
	 */
	public int CheckAllValidFileds() {
		
	checkFields=0;
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
	if(Checks.isDateValid(dateStr) && !dateStr.equals("")){
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
	 return checkFields;
		
	}
	
	
	/**
	* HandleCancelButton- remove all information from customer sale that was cancled
	*@author Litaf
	*/
		public void HandleCancelButton(){
			JOptionPane.showMessageDialog(GuiScreen,
					"Purchase has been cancelled, no charge was made", 
					"Information", JOptionPane.INFORMATION_MESSAGE);
			HandleCancelButton2();
		}			
			public void HandleCancelButton2(){

			GuiScreen.cleanScreen();
			GuiScreen.DisablePayButton();
			GuiScreen.DisableCancleButton();
		}
		
			/**
			 * Server_CalculatePayment- check if customer order fit one of the 
			 * rate discount in the home fuel rate mechanism and calculate the 
			 * price after discount/Increase price. print to Gui screen the order details
			 * @param TariffTable - Table from server
			 * @author Litaf
			 * 
			 */
		private void Server_CalculatePayment(callbackStringArray TariffTable){

			int FuelID=0;
			float FuelPrice=0;

			
			Object[][] arr=TariffTable.getData();
			for(int i=0;i<arr.length;i++){
				if(arr[i][1].toString().equals("Home Fuel"))
					{
						FuelPrice=Float.parseFloat(arr[i][3].toString());
						FuelID=Integer.parseInt(arr[i][0].toString());
					}
				}
			setSale(FuelID,FuelPrice);
			
		}
		public float setSale(int FuelID, float FuelPrice){
			float SumPrice=0;
			int Differacne=0;
			int ShippingAndDiscount=0;
			int flag=0;
			String RemarksString=null;
			
			sale.setFuelID(FuelID);
			
			//Immediate order: (within 6 hours) =Cost + 2% of the price of fuel
				DateFormat TimeFormat = new SimpleDateFormat("HH:mm");
				DateFormat DateFormat = new SimpleDateFormat("dd/MM/yy");
				String currDate=DateFormat.format(new Date()).toString();

				String currTimeHour=TimeFormat.format(new Date()).toString().substring(0,2);	
				String currTimeMin=TimeFormat.format(new Date()).toString().substring(3,5);				
				String DeliveryHour=timeStr.substring(0,2);
				String DeliveryMin=timeStr.substring(3,5);

				sale.setDeliveryDateAndTime(dateStr.substring(6,8),dateStr.substring(3,5),
											dateStr.substring(0,2),DeliveryHour, DeliveryMin);
				SumPrice=FuelPrice*Float.parseFloat(FuelStr);
				if(currDate.equals(dateStr)){
					Differacne=(Integer.parseInt(DeliveryHour)-Integer.parseInt(currTimeHour))*60+
								(Integer.parseInt(DeliveryMin)-Integer.parseInt(currTimeMin));
					if(Differacne<=360){
						flag++;
					//up to 6 hours -cost Shipping is an additional 2%
						sale.setPayment((float)(SumPrice*1.02));
						ShippingAndDiscount++;
						RemarksString="<html>*Immediate order-within 6 hours "
											+ "is fuel cost plus 2% shipping from the "
											+ "fuel price</html>";
						PrintSaleTemplate(SumPrice,RemarksString,0.02, FuelPrice,"+Shipping");		
					}
				}
				 if(Float.parseFloat(FuelStr)>=600 && Float.parseFloat(FuelStr)<=800){
					 flag++;
					 if(ShippingAndDiscount==1){
							sale.setPayment((float)(SumPrice*0.99));
							RemarksString="<html>*Shipping is 2% from fuel price and reservation of 600-800 "
												+ "liters in 3% discount. Overall 1% discount</html>";
							PrintSaleTemplate(SumPrice,RemarksString,-0.01, FuelPrice, "+Discount");	
					 }
					else{
					sale.setPayment((float)(SumPrice*0.97));
					RemarksString="<html>*Discount of 3% for reservation of  600-800 liters</html>";
					PrintSaleTemplate(SumPrice,RemarksString,-0.03, FuelPrice, "+Discount");	
					 }
				 }
				 if(Float.parseFloat(FuelStr)>800){
					 flag++;
					 if(ShippingAndDiscount==1){
							sale.setPayment((float)(SumPrice*0.98));
							RemarksString="<html>*Shipping is 2% from fuel price and reservation of over 800 "
												+ "liters in 4% discount. Overall 2% discount</html>";
							PrintSaleTemplate(SumPrice,RemarksString,-0.02, FuelPrice, "+Discount");	
					 }
					 else{
					sale.setPayment((float)(SumPrice*0.96));
					RemarksString="<html>*Discount of 4% for reservation of over 800 liters</html>";
					PrintSaleTemplate(SumPrice,RemarksString,-0.04, FuelPrice, "+Discount");	
					 }
				}
				if(flag==0) {
					sale.setPayment((float)(SumPrice));
					PrintSaleTemplate(SumPrice,RemarksString,0, FuelPrice, "<br>");	
				}
				return sale.getPayment();
		}
		/**
		 * PrintSaleTemplate- print to gui the order information
		 * @param SumPrice - Total price to pay
		 * @param RemarksString - Remarks String
		 * @param AfterDiscount - price After Discount
		 * @param FuelPrice - Current Fuel Price
		 * @param Discount - Discount
		 * @author Litaf
		 */
		private void PrintSaleTemplate(float SumPrice, String RemarksString, double AfterDiscount,
									   float FuelPrice, String Discount){
			GuiScreen.setOrderDetailsLabel("Order Details:");
			GuiScreen.setShowOrderDetailsLabel("<html> Fuel Price:<br>"
												+ "+<br>"
												+ "  Liters:<br>"
												+ "		____________________<br>"
												+ "  Sum:<br><br>"
												+ Discount+"</html>");
			GuiScreen.setLitersLabel(FuelStr);
			GuiScreen.setsumLabel(Float.toString(SumPrice));
			SumPrice+=SumPrice*AfterDiscount;
			if(AfterDiscount!=0)
				GuiScreen.setShippingLabel(Float.toString(SumPrice));
			else GuiScreen.setShippingLabel("");

			GuiScreen.setRemaraksLabel(RemarksString);
			GuiScreen.setFuelPriceLabel(Float.toString(FuelPrice));
			GuiScreen.setCalcPricetextArea(Float.toString(SumPrice));

		}
		/**
		 * HandleCheckCustomerCreditCard- send query to check if current customer 
		 * has creditcard information in the system
		 * @author Litaf
		 */
		public void HandleCheckCustomerCreditCard(){
			
			ErrorAmount.setText("");
			ErrorDeliveryTimeLabel.setText("");
			DateLabel.setText("");
			GuiScreen.setTime("");
			
			callbackCustomer customer=new callbackCustomer(MessageType.getCustomer);
			customer.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(customer);
		}
		
		/**
		 * Server_HandleCheckCustomerCreditCard- after receiving answer from the server-
		 * check if current customer has creditcard information in the system
		 * if not- let hin know that he can't purchase
		 * if yes- open the order fileds for edit
		 * @param customer - Customer
		 * @return true/false if that customer has credit card
		 * @author Litaf
		 */
		public boolean CheckForCreditCard(callbackCustomer customer){
			if(customer.getCreditCard().equals(""))
				return true;
			return false;

		}
		public void Server_HandleCheckCustomerCreditCard(callbackCustomer customer) {
			
			if(CheckForCreditCard(customer)){
				JOptionPane.showMessageDialog(GuiScreen, "Customer "+GuiScreen.getCurrentUserName()+
						"\nCan't buy home fuel because there is no credit card information"+
						"in the system\nPlease contact a service representative to proceed", 
						"Error", JOptionPane.INFORMATION_MESSAGE);
				GuiScreen.DisableAllComponents();
				}
			GuiScreen.DisablePayButton();
			GuiScreen.DisableCancleButton();
		}
		/**
		 * HandlePayButton- create new sale in DB with sale details
		 */
		public void HandlePayButton() {
			GuiScreen.cleanScreen();
			sale.setUserID(GuiScreen.getCurrentUserID());
			Server.handleMessageFromClient(sale);
		}	
		/**
		 * HandleCheckFuelOrder- send query for all the customer past purchases
		 */
		public void HandleCheckFuelOrder() {
			
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
							JOptionPane.showMessageDialog(GuiScreen,
									"Your order was added successfully, "
									+ "you can watch your orders in the 'Check Fuel Orders'", 
									"Information", JOptionPane.INFORMATION_MESSAGE);
							}
						else{
							JOptionPane.showMessageDialog(GuiScreen,
									"Error has occurred, no charge was made",
									"Error", JOptionPane.INFORMATION_MESSAGE);
							}	
						break;
					case getCustomer:
						callbackCustomer customer =  (callbackCustomer)arg;
						Server_HandleCheckCustomerCreditCard(customer);
						break;

					case getHomeFuelOrders:
						CallBack temp1 =  (CallBack) arg;
						if(temp1 instanceof callbackSuccess){
							JOptionPane.showMessageDialog(GuiScreen, "No Orders to show", 
									"Information", JOptionPane.INFORMATION_MESSAGE);
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
					
			}
		}
}