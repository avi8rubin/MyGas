package callback;

import common.MessageType;
/**
 * Class that contain all values of cars objects
 * @author Ohad
 */
public class callbackCar extends CallBack{

	private static final long serialVersionUID = 1L;
	private int CarID;
	private String CarNumber;
	private int CustomerID;
	private String NFC = "No";
	private int FuelID;
	private String ActiveCar;
	
		
	public callbackCar(){}
	public callbackCar(MessageType Message){
		super(Message);
	}
	
	public int getCarID() {
		return CarID;
	}
	public void setCarID(int carID) {
		CarID = carID;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	/**
	 * Return if the car have NFC
	 * @return - true/false
	 */
	public boolean hasNFC() {
		if(NFC.equals("Yes")) return true;
		return false;
	}
	/**
	 * Set NFC of remove NFC
	 * @param NFC - true/false
	 */
	public void setNFC(boolean NFC) {
		if(NFC) this.NFC = "Yes";
		else this.NFC = "No";
	}
	public String getYesNoNFC() {
		return NFC;
	}
	public void setYesNoNFC(String NFC) {
		this.NFC = NFC;
	}
	public int getFuelID() {
		return FuelID;
	}
	public void setFuelID(int fuelID) {
		FuelID = fuelID;
	}
	public String getYesNoActiveCar() {
		return ActiveCar;
	}
	public void setActiveCar(String activeCar) {
		ActiveCar = activeCar;
	}
	public boolean getActiveCar() {
		if(ActiveCar.equals("Yes")) return true;
		return false;
	}
	public void setActiveCar(boolean NFC) {
		if(NFC) this.ActiveCar = "Yes";
		else this.ActiveCar = "No";
	}
}
