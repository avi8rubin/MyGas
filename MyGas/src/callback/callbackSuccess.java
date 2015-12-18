package callback;

public class callbackSuccess extends CallBack{

	private static final long serialVersionUID = 1L;
	private String SuccessMassage;
	public callbackSuccess(){}
	public callbackSuccess(String successMassage){
		this.SuccessMassage = successMassage;
	}
	
	public String getSuccessMassage() {
		return SuccessMassage;
	}
	public void setSuccessMassage(String successMassage) {
		SuccessMassage = successMassage;
	}
}
