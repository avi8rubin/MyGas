package callback;

import java.util.Vector;

import common.MessageType;
/**
 * callbackVector class extends Vector class and create to send and receive vector of callbacks.
 * @author Ohad
 *
 */
public class callbackVector extends Vector<CallBack>{

	private static final long serialVersionUID = 1L;
	private MessageType WhatToDo;
	private Object[][] Data;
	private String[] Headers;
	
	public callbackVector(){}
	public callbackVector(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	
	public void setWhatToDo(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	public MessageType getWhatToDo(){
		return WhatToDo;
	}
	public Object[][] getData(){
		return Data;
	}
	public void setData(Object[][] Data){
		this.Data = Data;
	}
	public String[] getColHeaders(){
		return Headers;
	}
	public void setColHeaders(String[] colHeaders){
		this.Headers = colHeaders;
	}
	public callbackStringArray getcallbackStringArray(){
		return new callbackStringArray(Data,Headers);
	}
}
