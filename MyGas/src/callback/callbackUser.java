package callback;
import java.io.Serializable;

import common.MessageType;
/**
 * This callback represent User 
 * Contain
 * Global : UserID, UserName, Password, UserTypeId, LoggedIn, UserPrivilege, FirstName, LastName, Email;
 *
 */
public class callbackUser extends CallBack implements Serializable {

	private static final long serialVersionUID = 1L;
	private int UserID;
	private String UserName;
	private String Password;
	private int UserTypeId;
	private String LoggedIn;
	private String UserPrivilege;
	private String FirstName;
	private String LastName;
	private String Email;
	
	public callbackUser(){}    		//Empty constructor
	
	public callbackUser(String UserName, String Password){
		this.Password = Password;
		this.UserName = UserName;
	}
	
	public callbackUser(MessageType WhatToDo, String UserName, String Password){
		this.Password = Password;
		this.UserName = UserName;
		this.WhatToDo = WhatToDo;
	}
	
	public void setFirstName(String FirstName){
		this.FirstName = FirstName;
	}
	public void setLastName(String LastName){
		this.LastName = LastName;
	}
	public void setEmail(String Email){
		this.Email = Email;
	}
	public void setLoggedIn(String LoggedIn){
		this.LoggedIn = LoggedIn;
	}
	public void setUserName(String UserName){
		this.UserName = UserName;
	}
	public void setUserTypeId(int UserTypeId){
		this.UserTypeId = UserTypeId;
	}
	public void setPassword(String Password){
		this.Password = Password;
	}
	public void setUserID(int UserID){
		this.UserID=UserID;
	}
	public void setUserPrivilege(String UserPrivilege){
		this.UserPrivilege = UserPrivilege;
	}	
	public String getFirstName(){
		return(FirstName);
	}
	public String getLastName(){
		return(LastName);
	}
	public String getEmail(){
		return(Email);
	}
	public String getLoggedIn(){
		return(LoggedIn);
	}
	public String getUserName(){
		return(UserName);
	}	
	public int getUserTypeId(){
		return(UserTypeId);
	}
	public String getPassword(){
		return(Password);
	}
	public int getUserID(){
		return(UserID);
	}
	public String getUserPrivilege(){
		return(UserPrivilege);
	}
	
}
