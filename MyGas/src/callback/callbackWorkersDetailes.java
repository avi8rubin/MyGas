package callback;

public class callbackWorkersDetailes extends CallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int WorkerID;
	private String WorkerFirstName;
	private String WorkerLastName;
	private String Email;
	private String Organization;
	private int UserId;
	
	public int getWorkerID(){
		return WorkerID;
	}
	public void setWorkerID(int WorkerID){
		this.WorkerID = WorkerID;
	}
	public String getWorkerFirstName(){
		return WorkerFirstName;
	}
	public void setWorkerFirstName(String WorkerFirstName){
		this.WorkerFirstName = WorkerFirstName;
	}
	public String getWorkerLastName(){
		return WorkerLastName;
	}
	public void setWorkerLastName(String WorkerLastName){
		this.WorkerLastName = WorkerLastName;
	}
	public String getEmail(){
		return Email;
	}
	public void setEmail(String Email){
		this.Email = Email;
	}
	public String getOrganization(){
		return Organization;
	}
	public void setOrganization(String Organization){
		this.Organization = Organization;
	}
	public int getUserId(){
		return UserId;
	}
	public void setUserId(int UserId){
		this.UserId = UserId;
	}
}
