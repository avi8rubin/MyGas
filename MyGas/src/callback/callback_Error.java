package callback;

import java.io.Serializable;

public class callback_Error extends CallBack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ErrorMassage;
	
	public callback_Error(){}
	public callback_Error(String ErrorMassage){
		this.ErrorMassage = ErrorMassage;
	}
	
	public String getErrorMassage(){
		return ErrorMassage;
	}
}
