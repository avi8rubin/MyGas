package Fixtures.MyGas;

import java.awt.Component;
import java.util.Observable;

import GUI.Login_GUI;
import GUI.StationsGUI;
import callback.CallBack;
import callback.callbackCampaign;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackSuccess;
import callback.callbackUser;
import common.MessageType;
import controller.LoginController;
import controller.StationsController;

public class GasStationSale extends ServerConnection{
	
	private ConnectionBridge Bridge = new ConnectionBridge();
	
	/*Login Controller and Gui*/
	private Login_GUI LGui = new Login_GUI();
	private LoginController LCon = new LoginController(LGui);
	
	/*Station Controller and Gui*/
	private StationsGUI SGui;	
	private StationsController SCon;
	
	/*Callback for the tests*/
	private callbackUser User;
	private callbackCustomer Customer;
	private callbackCar Car;
	private callbackSale Sale;
	private CallBack ReturnCallBack;
	
	/*Variable*/
	private Component FuelType;
	private boolean SaleAddToDB;
	private int GasStationID = 1;
	
	
	/*Constructor*/
	public GasStationSale(){
		User = new callbackUser();	
		LGui.setVisible(false);							//Set gui invisible
		SGui = new StationsGUI(User, Server, LGui);		//Create new station gui
		SGui.setVisible(false);							//Set gui invisible
		SCon = new StationsController(Server, SGui);	//Create new station controller
		Server.deleteObserver(SCon);
		Server.deleteObserver(SGui.getNotificationThread());
		CreateStation();
	}	
	private void CreateStation(){		
		SCon.setFuel95CurrentAmount(500);
		SCon.setFuel95IsExist(true);
		SCon.setNFCIsExist(false);
		SCon.setFuelDieselCurrentAmount(700);
		SCon.setFuelDieselIsExist(true);
		SCon.setFuelScooterCurrentAmount(700);
		SCon.setFuelScooterIsExist(true);
		SCon.setGasStationID(GasStationID);
	}	
	
	//Check credit card
	public void startCustomer(){
		Customer = new callbackCustomer();	
	}
	public void createCustomer(int CustomerNumber){
		if(CustomerNumber == 1){
			Customer.setUserID(34);
			Customer.setCustomersID(306216168);
			Customer.setCustomerFirstName("Nir");
			Customer.setCustomerLastName("Lod");
			Customer.setCreditCard("2356-6589-7644-7852");	
		}
		else {
			Customer.setUserID(33);
			Customer.setCustomersID(302561566);
			Customer.setCustomerFirstName("Sali");
			Customer.setCustomerLastName("Funny");
			Customer.setCreditCard("");	
		}
		CreateCar(CustomerNumber);
	}
	private void CreateCar(int CustomerNumber){
		Car = new callbackCar();
		if(CustomerNumber == 1){
			Car.setCarID(17);
			Car.setCarNumber("12-985-44");
			Car.setCustomerID(Customer.getCustomersID());
			Car.setFuelID(2);
		}
		else {
			Car.setCarID(16);
			Car.setCarNumber("21-236-50");
			Car.setCustomerID(Customer.getCustomersID());
			Car.setFuelID(4);
		}
	}
	public boolean checkCreditCard(){
		return SCon.CheckCreditCard(Customer);
	}
	
	//Check pump
	public void setPump(String FuelType){
		switch (FuelType){
		case "Diesel": 
			this.FuelType = SGui.getBlueHand();
			break;
		case "Scooter": 
			this.FuelType = SGui.getGreenHand();
			break;
		case "95": 
			this.FuelType = SGui.getRedHand();
			break;
		}
	}
	public boolean checkPump(){
		SCon.setCarTest(Car);
		return SCon.mouseClickedTest(FuelType);
	}
	
	
	//Create Sale
	public void startSale(){
		Sale = new callbackSale();
		Sale.setCustomersID(Customer.getCustomersID());
		Sale.setCarID(Car.getCarID());
		Sale.setGasStationID(GasStationID);
		Sale.setFuelID(Car.getFuelID());
		Sale.setUserID(Customer.getUserID());
	}	
	public void saleFuelAmount(float FuelAmount){
		Sale.setFuelAmount(FuelAmount);
	}
	public void salePayment(float Payment){
		Sale.setPayment(Payment);
	}
	public boolean addSale(){
		Sale.setWhatToDo(MessageType.setNewGasStationSale);
		Bridge.Wait();
		Server.handleMessageFromClient(Sale);				
		if(ReturnCallBack instanceof callbackSuccess)
			return true;
		return false;		
	}
	
	//Check Discount
	public boolean checkDiscount(){
		Bridge.Restart();
		SCon.DiscountCalulation(Sale.getPayment(), Sale.getFuelAmount(), Sale.getFuelID(), Sale.getCustomersID(), Car.getCarNumber());
		Bridge.Wait();
		if (ReturnCallBack instanceof callbackSuccess) return false;
		else if (ReturnCallBack instanceof callbackCampaign) {
			Sale.setPayment(((callbackCampaign)ReturnCallBack).getPriceAfterDiscount());
			Sale.setCampaignID(((callbackCampaign)ReturnCallBack).getCampaignID());
			return true;
		}
		System.out.println("callback Error");
		return false;		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg) {
		if(arg instanceof CallBack){		
			switch(((CallBack) arg).getWhatToDo()){
				case getSaleDiscount:						
						ReturnCallBack = (CallBack) arg;
					break;
				case setNewGasStationSale:
						ReturnCallBack = (CallBack) arg;
					break;
			}
			Bridge.NotifyBridge();
		}
		
	}

}
