package callback;

import java.io.Serializable;

import common.MessageType;
/**
 * An abstract callback class, implements Serializable
 * @author Ohad
 *
 */
public abstract class CallBack implements Serializable{

	private static final long serialVersionUID = 1L;
	protected MessageType WhatToDo;
	
	/**
	 * Empty constructor
	 */
	public CallBack(){};
	/**
	 * Constructor with MessageType
	 * @param WhatToDo - MessageType
	 */
	public CallBack(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	/**
	 * The callback mission, will be translate to query in DB.
	 * @param WhatToDo - The query mission.
	 */
	public void setWhatToDo(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	public MessageType getWhatToDo(){
		return WhatToDo;
	}
}
