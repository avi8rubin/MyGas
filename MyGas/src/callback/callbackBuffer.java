package callback;

import java.util.Vector;

public class callbackBuffer extends CallBack{
	
	private static final long serialVersionUID = 1L;
	private Object CallBack = null;
	private boolean HaveNewCallBack = false;
	public boolean BufferInUse;
	
	public callbackBuffer(){
		
	}
	
	/**
	 * This function set new callback that receive from server 
	 * @param NewCallBack
	 * @return boolean - if success to set callback
	 */
	public boolean setNewCallBack(Object NewCallBack){
		if (HaveNewCallBack == false){ 		//The buffer is empty
			CallBack = NewCallBack;			//Set new callback
			HaveNewCallBack = true;			//Set flag of new callback waiting
			return(true);					//Set new callback successful
		}
		return(false);						//the buffer have already callback
	}
	
	/**
	 * Empty the buffer if there is new callback
	 * @return CallBack element
	 */
	
	public CallBack getBufferCallBack(){
		if (HaveNewCallBack == false) 		//The callback buffer is empty
			return (null);
		HaveNewCallBack = false;			//Update empty buffer & wait for new callback
		return ((CallBack) CallBack);
	}
	
	public Vector<?> getBufferCallBackVector(){
		if (HaveNewCallBack == false) 		//The callback buffer is empty
			return (null);
		HaveNewCallBack = false;			//Update empty buffer & wait for new callback vector
		return ((Vector<?>) CallBack);
	}
	
	public boolean getHaveNewCallBack(){
		return (HaveNewCallBack);
	}
	
	public boolean getBufferInUse(){
		return (BufferInUse);
	}

	
}
