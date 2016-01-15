package callback;

import common.MessageType;

/**
 * This callback will send to client when connection to server lost
 * @author Ohad
 *
 */
public class callbackLostConnection extends CallBack {
	private static final long serialVersionUID = 1L;
	public callbackLostConnection(){
		super(MessageType.Lost_Connection_With_Client);
	}
}
