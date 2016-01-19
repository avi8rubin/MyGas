package Fixtures.MyGas;
/**
 * There is a problem when we use in observer method.
 * Query send from one method return in another, that create problem with the answer for fittest check.
 * That class was create to bridge the problem.
 * The check will be in hold until get the answer to common "buffer" from server.
 * @author Ohad
 *
 */
public class ConnectionBridge {
	private boolean WaitUntil = false;
	private Object lockObject = new Object();

	public ConnectionBridge(){
		Restart();
	}	
	/**
	 * Restart parameters
	 */
	public void Restart(){
		WaitUntil=false;
	}
	/**
	 * Hold function until answer back from server.
	 */
	public void Wait(){
		while(!ChangedState()) Sleep(30);
	}
	/**
	 * Wait until notify
	 * @return - false / true
	 */
	private synchronized boolean ChangedState(){
		synchronized(lockObject) {
			return WaitUntil;
		}
	}
	/**
	 * Thread go to sleep.
	 * @param time - the time in milli seconds 
	 */
	private void Sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Release the lock on fittest check function
	 */
	public synchronized void NotifyBridge(){
		synchronized(lockObject){
			WaitUntil=true;
		}
				
	}
}
