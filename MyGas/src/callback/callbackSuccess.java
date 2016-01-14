package callback;

import common.MessageType;
/**
 * This callback represent Success by the DB
 * Contain
 * Global : SuccessMassage
 *
 */
public class callbackSuccess extends CallBack{

	private static final long serialVersionUID = 1L;
	private String SuccessMassage;
	public callbackSuccess(){}

	public callbackSuccess(MessageType WhatToDo){
		super(WhatToDo);
	}
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
