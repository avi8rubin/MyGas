package callback;

public class callbackCar extends CallBack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int CarID;
	private String CarNumber;
	private int CustomerID;
	private String NFC;
	private int FuelID;
	private int CostingModelID;
		
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
	public boolean hasNFC() {
		if(NFC.equals("Yes")) return true;
		return false;
	}
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
	public int getCostingModelID() {
		return CostingModelID;
	}
	public void setCostingModelID(int costingModelID) {
		CostingModelID = costingModelID;
	}
}
