package callback;

import java.io.Serializable;

import common.MessageType;
/**
 * Error callback return from server with the error description.
 * @author Ohad
 *
 */
public class callback_Error extends CallBack implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ErrorMassage;
	
	public callback_Error(){}
	public callback_Error(MessageType WhatToDo){
		super(WhatToDo);
	}
	public callback_Error(String ErrorMassage){
		this.ErrorMassage = ErrorMassage;
	}
	
	public String getErrorMassage(){
		return ErrorMassage;
	}
	public void setWhatToDo(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	public MessageType getWhatToDo(){
		return WhatToDo;
	}
}
