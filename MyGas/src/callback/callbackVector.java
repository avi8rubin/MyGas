package callback;

import java.util.Vector;

import common.MessageType;

public class callbackVector extends Vector<CallBack>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MessageType WhatToDo;
	
	public callbackVector(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	
	public void setWhatToDo(MessageType WhatToDo){
		this.WhatToDo = WhatToDo;
	}
	public MessageType getWhatToDo(){
		return WhatToDo;
	}
}
