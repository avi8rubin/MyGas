package Fixtures.MyGas;

import java.util.Observable;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JOptionPane;

import GUI.CustomerGUI;
import GUI.Login_GUI;
import callback.CallBack;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackUser;
import common.MessageType;
import controller.CustomerController;

public class HomeSale extends ServerConnection{

	private ConnectionBridge Bridge = new ConnectionBridge();

	/**
	 * global callbacks
	 */
	callbackUser user;
	callbackCustomer customer;
	callbackSale sale;
	CallBack ReturnCallBack=null;
	callbackCustomer ReturnCustomer=null;
	callbackStringArray ReturnCallBackString=new callbackStringArray(MessageType.getHomeFuelOrders);

	/**
	 * controller and gui
	 */
	Login_GUI LGui;
	CustomerController CCon;
	CustomerGUI CGui;
	/**
	 * global var
	 */
	float price;
	boolean bool=false;
	boolean pay=true;
	String Date;
	String ReturnOrders[][];
	int flag=0;
	int SaleID;
	
	
	public HomeSale(){
		customer=new callbackCustomer(MessageType.getCustomer);
	}
	
	public void enterUser(){
		user=new callbackUser();
	}
	public void userID(int id){
		user.setUserID(id);
		customer.setUserID(id);
		creatController(id);
	}
	public void creatController(int userID){
		
		user.setUserID(userID);
		LGui = new Login_GUI();
		LGui.setVisible(false);							//Set gui invisible
		CGui = new CustomerGUI(user, Server, LGui);		//Create new customer gui
		CGui.setVisible(false);							//Set gui invisible
		CCon = new CustomerController(Server, CGui);	//Create new customer controller
		Server.deleteObserver(CCon);
		Server.deleteObserver(CGui.getNotificationThread());
		CGui.getNotificationThread().setNotificationFlag(false);
	}
	
	public void customerID(int id){
		customer.setCustomersID(id);
	}
	
	public boolean checkCreditCard(){
		Bridge.Restart();
		Server.handleMessageFromClient(customer);
		Bridge.Wait();
		if(CCon.CheckForCreditCard(ReturnCustomer)==true)
			return false;
		return true;
	}
	public void enterSaleDetails(){
		sale=new callbackSale(MessageType.setNewHomeFuelSale);
	}
	public void fuelAmount(String fuel){
		CGui.setFuelAmount(fuel);
		sale.setFuelAmount(Float.parseFloat(fuel));
	}
	public void address(String add){
		CGui.setAddress(add);
		sale.setAddress(add);
		
	}
	public void deliveryDate(String date){
		Date=date;
		CGui.setDate(date);
		sale.setDeliveryDate(date);
	}
	public void deliveryTime(String time){
		CGui.setTime(time);
		sale.setDeliveryTime(time);
		sale.setDeliveryDateAndTime(Date.substring(6,8),Date.substring(3,5),
				Date.substring(0,2),time.substring(0,2), time.substring(3,5));
	}
	public void fuelPrice(float m_price){
		price= m_price;
	}
	public boolean checkValidFields(){
		if(CCon.CheckAllValidFileds()==4)
			return true;
		return false;
	}
	//discount
	public float checkValidDiscount(){
		CCon.CheckAllValidFileds();
		sale.setPayment(CCon.setSale(3, price));
		return(CCon.setSale(3, price));
	}
	//orders check
	public void payForOrder(){
		sale.setCustomersID(customer.getCustomersID());
		sale.setUserID(customer.getUserID());
		sale.setFuelID(3);
		Bridge.Restart();
		Server.handleMessageFromClient(sale);
		Bridge.Wait();

	}
	public boolean checkOrderInDB(){
		Bridge.Restart();
		CCon.HandleCheckFuelOrder();
		Bridge.Wait();
		
		if(flag==1)
			return false;
		for(int i=0;i<ReturnCallBackString.getData().length;i++)
		{
			String temp=ReturnOrders[i][4];
			String dateSub=temp.substring(0, 6)+temp.substring(8, 10);
			String a=(ReturnOrders[i][5].substring(0, 5));
			if(ReturnOrders[i][1].equals(sale.getAddress())&& 
					dateSub.equals(sale.getDeliveryDate())&& 
					(ReturnOrders[i][5].substring(0, 5)).equals(sale.getDeliveryTime()))
					return true;
		}
		return false;
	}
	
	public void cancleOrder(){
		CCon.HandleCancelButton2();
	}
	

	@Override
	public void update(Observable arg0, Object arg) {
		if(arg instanceof CallBack){		
			switch(((CallBack)arg).getWhatToDo()){
				case getCustomer:						
						ReturnCustomer = (callbackCustomer)arg;
					break;
				case getHomeFuelOrders:
					CallBack temp1 =  (CallBack) arg;
					if(temp1 instanceof callbackSuccess)
						flag=1;
					else{
					//	ReturnOrders= (callbackStringArray) arg;
						ReturnCallBackString=(callbackStringArray) arg;
						ReturnOrders=(String[][])((callbackStringArray)arg).getData();
						//CheckOrder((String[][])((callbackStringArray)arg).getData(),
							//((callbackStringArray)arg).getRowCount());
					}
					break;
				case setNewHomeFuelSale:
					CallBack temp =  (CallBack) arg;
					if(temp instanceof callbackSuccess)
						pay=true;
					else
						pay=false;	
					break;
			default:
				break;
			}
			Bridge.NotifyBridge();		
	}

}
}
