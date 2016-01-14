package callback;

import common.MessageType;
/**
 * This callback represent Gas station orders 
 * Contain
 *Global : OrderID,GasStationID,FuelID,Amount,Confirmation;
 *
 */
public class callbackStationOrders extends CallBack{

	private static final long serialVersionUID = 1L;
	private int OrderID;
	private int GasStationID;
	private int FuelID;
	private float Amount;
	private String Confirmation;	
	
	public callbackStationOrders(){}
	public callbackStationOrders(MessageType Message){
		super(Message);
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public int getGasStationID() {
		return GasStationID;
	}
	public void setGasStationID(int gasStationID) {
		GasStationID = gasStationID;
	}
	public int getFuelID() {
		return FuelID;
	}
	public void setFuelID(int fuelID) {
		FuelID = fuelID;
	}
	public float getAmount() {
		return Amount;
	}
	public void setAmount(float amount) {
		Amount = amount;
	}
	public String getConfirmation() {
		return Confirmation;
	}
	public void setConfirmation(String confirmation) {
		Confirmation = confirmation;
	}
}
