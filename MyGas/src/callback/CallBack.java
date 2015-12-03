package callback;

import java.io.Serializable;

import common.MessageType;

public abstract class CallBack implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected MessageType WhatToDo;
	public CallBack(){};
}
