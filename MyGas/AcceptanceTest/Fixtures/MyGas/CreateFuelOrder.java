package Fixtures.MyGas;

import java.sql.Types;
import java.util.Observable;

import callback.CallBack;
import callback.callbackSale;
import callback.callbackStationFuels;
import callback.callbackStringArray;
import callback.callbackSuccess;
import common.MessageType;

public class CreateFuelOrder extends ServerConnection{
	//Callback Objects
	callbackStationFuels Station1 = new callbackStationFuels();
	callbackSale Sale;
	callbackStringArray SuppliesOrder;
	callbackStringArray SuppliesOrder2 = new callbackStringArray(MessageType.getStationSuppliesOrder);
	
	//CallbackAnswer
	private boolean SaleAddToDB;
	private boolean StationOrderWasSet;
	private boolean WhileFlag;
	private CallBack ReturnCallback;
	private int FirstOrderID;
	private int SecondOrderID;
	
	
	public CreateFuelOrder(){
		//Duplicate of the station fuel level in the DB
		Station1.setGasStationID(1);
		Station1.setFuelID(2);
		Station1.setThresholdLimit(200);
		Station1.setCapacity(1700);
		Station1.setCurrentAmount(205);		
	}
	public void startSale(){
		SaleAddToDB = false;
		Sale = new callbackSale(MessageType.setNewGasStationSale);
	}	
	public void saleUser(int UserID){
		Sale.setUserID(UserID);
	}
	public void saleFuel(int FuelID){
		Sale.setFuelID(FuelID);
	}
	public void saleFuelAmount(float FuelAmount){
		Sale.setFuelAmount(FuelAmount);
	}
	public void salePayment(float Payment){
		Sale.setPayment(Payment);
	}
	public void saleCarId(int CarID){
		Sale.setCarID(CarID);
	}
	public void saleCustomer(int CustomerID){
		Sale.setCustomersID(CustomerID);
	}
	public void saleGasStation(int GasStationID){
		Sale.setGasStationID(GasStationID);
	}
	public boolean addSale(){
		Station1.setCurrentAmount(Station1.getCurrentAmount()-Sale.getFuelAmount()); //Update current fuel amount
		Server.handleMessageFromClient(Sale);		
		while (!CheckSaleState(1)) Sleep(30);	//Waiting an answer from server
		if(ReturnCallback instanceof callbackSuccess)
			return true;
		return false;		
	}
	
	public void startOrder(){
		WhileFlag = false;
		StationOrderWasSet = false;
		SuppliesOrder = new callbackStringArray(MessageType.getStationSuppliesOrder);
	}
	public void stationManagerId(int StationManagerID){
		Object[] UserID = {StationManagerID};
		SuppliesOrder.setVariance(UserID);
	}
	public boolean currentStationOrder(){
		Server.handleMessageFromClient(SuppliesOrder);
		while (!CheckOrderState(1)) Sleep(30);	//Waiting an answer from server
		if(SecondOrderID != Types.NULL){
			if(FirstOrderID == SecondOrderID) return true;	//Order was update
			return false;									//New order create
		}
		return StationOrderWasSet;
	}
	
	private synchronized boolean CheckSaleState(int state){
		if(state==1) return SaleAddToDB;	
		else SaleAddToDB=true;
		return true;
	}
	
	private synchronized boolean CheckOrderState(int state){
		if(state==1) return WhileFlag;	
		else WhileFlag=true;
		return true;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){		
			switch(((CallBack) arg).getWhatToDo()){
				case setNewGasStationSale:
						ReturnCallback = (CallBack) arg;
						CheckSaleState(2);
					break;
				case getStationSuppliesOrder:
					CheckNewOrder((String[][])((callbackStringArray)arg).getData(),
							((callbackStringArray)arg).getRowCount());
					break;
			}
		}
		
	}
	
	private void CheckNewOrder(String[][] Data, int Rows){
		for(int i =0 ; i<Rows ; i++){
			if(Integer.parseInt(Data[i][1])==1 && Integer.parseInt(Data[i][2])== 2)
				if(Float.parseFloat(Data[i][4])==Station1.getCapacity()-Station1.getCurrentAmount()){
					StationOrderWasSet = true;
					if(FirstOrderID==Types.NULL) FirstOrderID = Integer.parseInt(Data[i][0]);
					else SecondOrderID = Integer.parseInt(Data[i][0]);
					CheckOrderState(2);
				}	
		}
		StationOrderWasSet = false;
	}
}
