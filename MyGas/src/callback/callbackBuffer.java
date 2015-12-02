package callback;

public class callbackBuffer extends CallBack{
	private CallBack CallBack = null;
	private boolean HaveNewCallBack = false;
	private boolean BufferInUse = false;
	
	public callbackBuffer(){
		
	}
	
	/**
	 * This function set new callback that receive from server 
	 * @param NewCallBack
	 * @return boolean - if success to set callback
	 */
	public boolean setNewCallBack(CallBack NewCallBack){
		if (BufferInUse) return (false); 	//Check if buffer in use of another class
		BufferInUse = true;					//Taking control on the buffer
		if (HaveNewCallBack == false){ 		//The buffer is empty
			CallBack = NewCallBack;			//Set new callback
			HaveNewCallBack = true;			//Set flag of new callback waiting
			BufferInUse = false;			//Release control on the buffer
			return(true);					//Set new callback successful
		}
		BufferInUse = false;				//Release control on the buffer
		return(false);						//the buffer have already callback
	}
	
	/**
	 * Empty the buffer if there is new callback
	 * @return CallBack element
	 */
	
	public CallBack getBufferCallBack(){
		if (BufferInUse) return (null); 	//Check if buffer in use of another class
		else if (HaveNewCallBack == false) 	//The callback buffer is empty
			return (null);
		HaveNewCallBack = false;			//Update empty buffer & wait for new callback
		return (CallBack);
	}
	
	/**
	 * Check if there is new call back and the buffer not in use of another function
	 * @return
	 */
	public boolean HasNewCallBackAndBufferFree(){
		if (HaveNewCallBack && !BufferInUse)	
			return true;
		return false;
	}
	
}
