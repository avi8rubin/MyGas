/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import GUI.MarketingRepresentativeGUI;
import callback.CallBack;
import callback.callbackCampaignPattern;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackVector;
import callback.callback_Error;
import client.Client;
import common.BooleanTableModel;
import common.Checks;
import common.JTableToExcel;
import common.MessageType;

public class MarketingRepresentativeController extends Controller{
	/**
	 * Marketing Representative GUI components 
	 */
	
	private CardLayout ContainerCardCenter;
	private CardLayout ContainerCardLeft;
	private MarketingRepresentativeGUI GuiScreen;
	
	/**
	 * Top Layer Components	
	 */
	private JButton CreateNewCustomerAccountButton;
	private JButton CustomerDetailsButton;
	private JButton CreateNewCampignPatternButton;
	private JButton ReportsButton;

	/**
	 * CreateNewCustomerAccountLayer Left Layer Components
	 */
	private JLayeredPane CreateNewCustomerAccountLayer;
	private JButton CreateUserButton;
	private JButton AddCarDetailsButton;
	
	/**
	 * CreateUserLayer Center Layer Components
	 */
	private JLabel PasswordValidationFailedMessageLabel;
	private JLabel MissedFieldsMessageLabel;
	private JLabel UserNameExistMesaageLabel;
	private JButton NextButton;	
	
	/**
	 * AddPersonalDetails Center Layer Components	
	 */
	private JButton CreateButton;
	private JButton EditButton;
	private JButton SaveButton;	
	private JLabel InvalidCustomerIDMesaageLabel;
	private JLabel InvalidEmailMesaageLabel;
	private JLabel EmailExistMesaageLabel;
	private JTextField EmailTextField;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;
	private JLabel MissedFieldsMessage;
	private JLabel CustomerIDExistMesaageLabel;

	/**
	 * AddCars Center Layer Components		
	 */
	private JButton AddButton;
	private JLabel ExistCarNumberLabel;
	private JFormattedTextField CarNumberFormattedTextField;
	private JLabel MissedFieldMessageAddCarsLabel;
	private BooleanTableModel CarsViewTableModelAfterChanges;
	private JButton CarsViewSaveButton;
	
	/**
	 * CustomerDetails Left Layer Components	
	 */
	private JLayeredPane CustomerDetailsLayer;
	private JButton SearchButton;
	private JTextField EnterCustomerIdTextField;
	private JLabel InvalidcustomerIDMessageLabel;
	private JLabel CustomerNotExistMessageLabel;
	private callbackCustomer fullCallbackCustomer;
	
	/**
	 * RatesReport Left Layer Components	
	 */
	private JButton GenerateReportButton;
	private JButton UpdateRateButton;
	
	/**
	 * CampignPattern Center Layer Components	
	 */
	private JComboBox<?> PatternKindComboBox;
	private JButton CreatePatternButton;
	private JTextField InsertFuelAmountTextField;
	private JTextArea DescriptionTextArea;
	
	/**
	 * CampignPattern Left Layer Components	
	 */
	private JComboBox<?> ExistPatternsComboBox;
	
	/**
	 * MarketingRepresentative controller, control the MarketingRepresentative gui.
	 * First adding listeners to all relevant buttons, then send query to DB for getting all values for ComboBox -
	 * only one time when the controller created.
	 * @param Server - Connection to server
	 * @param CommonBuffer - Not in use
	 * @param GuiScreen - The gui screen use in some methods
	 * @author Adir
	 */
	public MarketingRepresentativeController(Client Server, MarketingRepresentativeGUI GuiScreen) {
		super(Server, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);

		/* ------ Add action listener to buttons ------ */
		
		//CreateNewCustomerAccount Button
		CreateNewCustomerAccountButton = GuiScreen.getCreateNewCustomerAccountButton();
		CreateNewCustomerAccountButton.addActionListener(this);
		CreateNewCustomerAccountButton.setActionCommand("CreateNewCustomerAccount"); //Add action command
		
		//CustomerDetails Button
		CustomerDetailsButton = GuiScreen.getCustomerDetailsButton();
		CustomerDetailsButton.addActionListener(this);
		CustomerDetailsButton.setActionCommand("CustomerDetails"); //Add action command

		//CreateUser Button
		CreateUserButton = GuiScreen.getCreateUserButton();
		CreateUserButton.addActionListener(this);
		CreateUserButton.setActionCommand("CreateUser"); //Add action command
		
		//AddCarDetails Button
		AddCarDetailsButton = GuiScreen.getAddCarDetailsButton();
		AddCarDetailsButton.addActionListener(this);
		AddCarDetailsButton.setActionCommand("AddCarDetails"); //Add action command	
		
		//Search Button
		SearchButton = GuiScreen.getSearchButton();
		SearchButton.addActionListener(this);
		SearchButton.setActionCommand("Search"); //Add action command
		
		//CampignPattern Button
		CreateNewCampignPatternButton = GuiScreen.getCreateNewCampignPatternButton();
		CreateNewCampignPatternButton.addActionListener(this);
		CreateNewCampignPatternButton.setActionCommand("CampignPattern"); //Add action command	
		
		ReportsButton = GuiScreen.getReportsButton();
		ReportsButton.addActionListener(this);
		ReportsButton.setActionCommand("Reports"); //Add action command	
				
		// get values for comboBoxs
		Server.handleMessageFromClient(new callbackVector(MessageType.getMarketingRepresentativeComboBox));

	}

	/**
	 * actionPerformed- attach the buttons to their action event handlers and 
	 * Adding the appropriate window layer according to the selected action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		/* -------- Check the source of the event ---------*/
		
		if(e.getActionCommand().equals("CreateNewCustomerAccount")){
			GuiScreen.setNewCustomerFlag(true);
			CreateNewCustomerAccountLayer = GuiScreen.getCreateNewCustomerAccountLayer();
			AddCarDetailsButton.setVisible(false);
			CreateUserButton.setText("<html>Create User</html>");
			AddCarDetailsButton.setText("<html>Add Car Details</html>");
			ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft");
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");				//The CreateNewCustomerAccount layer will be display											
		}

		if(e.getActionCommand().equals("CustomerDetails")){
			GuiScreen.setNewCustomerFlag(false);
			CustomerDetailsLayer = GuiScreen.getCustomerDetailsLayer();
			CreateUserButton.setText("<html>Edit User</html>");
			AddCarDetailsButton.setText("<html>Edit Cars Details</html>");
			AddCarDetailsButton.setVisible(true);
			ContainerCardLeft.show(LeftCardContainer, "CustomerDetailsLayerLeft");
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");				//The CustomerDetails layer will be display											
		}		
		
		
		if(e.getActionCommand().equals("CreateUser")){
			HandleCreateUserPressed();
		}		

		if(e.getActionCommand().equals("Next")){
			HandleNextPressed();
		}		

		if(e.getActionCommand().equals("Create")){
			HandleCreatePressed();										
		}			

		if(e.getActionCommand().equals("AddCarDetails")){
			HandleAddCarPressed();										
		}		

		if(e.getActionCommand().equals("Add")){
			HandleAddPressed();										
		}	
		
		if(e.getActionCommand().equals("SaveCarsDetails")){
			HandleSaveCarsDetailsPressed();										
		}	
		
		if(e.getActionCommand().equals("Search")){
			HandleSearchPressed();										
		}			
		
		if(e.getActionCommand().equals("Edit")){
			GuiScreen.EnableRelevantButtons(true);
			ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");	 //The CreateUser layer will be display
		}	
		
		if(e.getActionCommand().equals("Save"))
			HandleCreatePressed();
		
		if(e.getActionCommand().equals("CampignPattern"))
			HandleCampignPatternPressed();									
		
		if(e.getActionCommand().equals("ChangePatternKindSelection"))
			 GuiScreen.setVisibleByPattenKindComboBox(PatternKindComboBox.getSelectedIndex());
		
		if(e.getActionCommand().equals("ExistPatternSelection"))
			 GuiScreen.displaySelectionExistPattern();
		
		
		if(e.getActionCommand().equals("CreatePattern"))
			HandleCreatePatternPressed();
		
		if(e.getActionCommand().equals("Reports"))
			HandleReportPressed();
	
		if(e.getActionCommand().equals("GenerateReport"))
			HandleGenerateReportPressed();
		
		if(e.getActionCommand().equals("UpdateRate"))
			HandleUpdateRatePressed();		
		
		}	

	/* Create/Edit User */
	
	/**
	 * HandleCreateUserPressed()- initialize CreateUserLayerCenter for new customer insertion.
	 * In addition, adding action listeners to relevant buttons.
	 */
	private void HandleCreateUserPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		GuiScreen.CreateNewUserCenterLayer();
		// HideRelevantButtons()- let us show relevant button- depend Create/Edit case.
		GuiScreen.HideRelevantButtons();
		if ( GuiScreen.getNewCustomerFlag() ) GuiScreen.EnableRelevantButtons(true);
		else GuiScreen.EnableRelevantButtons(false);
		
		ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");				//The CreateUser layer will be display											
		
		//Next Button
		NextButton = GuiScreen.getNextButton();
		NextButton.addActionListener(this);
		NextButton.setActionCommand("Next"); //Add action command
		
		//Create Button
		CreateButton = GuiScreen.getCreateButton();
		CreateButton.addActionListener(this);
		CreateButton.setActionCommand("Create"); //Add action command
		
		//Edit Button
		EditButton = GuiScreen.getEditButton();
		EditButton.addActionListener(this);
		EditButton.setActionCommand("Edit"); //Add action command
		
		//Save Button
		SaveButton = GuiScreen.getSaveButton();
		SaveButton.addActionListener(this);
		SaveButton.setActionCommand("Save"); //Add action command
		
	}
	/**
	 * HandleNextPressed() - Checking validation of inserted password and that all fields are full -
	 * display relevant message else. If all fields inserted OK - display the next insertion layer - AddPersonalDetailsCenter.
	 */
	private void HandleNextPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		PasswordValidationFailedMessageLabel = GuiScreen.getPasswordValidationFailedMessageLabel();
		MissedFieldsMessageLabel = GuiScreen.getMissedFieldsMessageLabel();
		UserNameExistMesaageLabel = GuiScreen.getUserNameExistMesaageLabel();
		
		// Show/ don't show PasswordValidationFailedMessageLabel after isValidPassword check
		if (!Checks.isValidPassword(GuiScreen.getPasswordPasswordField().getText(), GuiScreen.getPasswordValidatePasswordField().getText()))
			PasswordValidationFailedMessageLabel.setVisible(true);
		else PasswordValidationFailedMessageLabel.setVisible(false);
		
		// Show/ don't show MissedFieldsMessageLabel after isAllFieldsFilled check
		if (!Checks.isAllFieldsFilled(GuiScreen.getPasswordPasswordField().getText(), GuiScreen.getPasswordValidatePasswordField().getText(), GuiScreen.getUserNametextField().getText()))
				MissedFieldsMessageLabel.setVisible(true);
		else MissedFieldsMessageLabel.setVisible(false);
		
		// If the input OK - display the next layer - AddPersonalDetailsCenter
		if (!PasswordValidationFailedMessageLabel.isVisible()&&!MissedFieldsMessageLabel.isVisible())
			ContainerCardCenter.show(CenterCardContainer, "AddPersonalDetailsCenter");					
			
	}
	/**
	 * HandleCreatePressed() - Checking validation of inserted fields -display relevant message else.
	 * If all fields inserted OK - send query to DB for add new customer.
	 */
	private void HandleCreatePressed(){
		
		InvalidEmailMesaageLabel = GuiScreen.getInvalidEmailMesaageLabel();
		EmailTextField = GuiScreen.getEmailTextField();
		InvalidPhoneMesaageLabel= GuiScreen.getInvalidPhoneMesaageLabel();
		InvalidCreditCardMesaageLabel =  GuiScreen.getInvalidCreditCardMesaageLabel();
		MissedFieldsMessage = GuiScreen.getMissedFieldsMessage();
		InvalidCustomerIDMesaageLabel = GuiScreen.getInvalidCustomerIDMesaageLabel();
		
		// Show/ don't show InvalidCustomerIDMesaageLabel after isNumeric check		
		if ( !Checks.isNumeric(GuiScreen.getCustomerIDTextField().getText()) )
			InvalidCustomerIDMesaageLabel.setVisible(true);
		else InvalidCustomerIDMesaageLabel.setVisible(false);		
		
		// Show/ don't show InvalidEmailMesaageLabel after isValidEmailAddress check		
		if (!Checks.isValidEmailAddress(GuiScreen.getEmailTextField().getText()))
			InvalidEmailMesaageLabel.setVisible(true);
		else InvalidEmailMesaageLabel.setVisible(false);
		
		// Show/ don't show InvalidPhoneMesaageLabel after phone number check	
		if (String.valueOf(GuiScreen.getPhoneFormattedTextField().getText().trim().length()).equals("1")) InvalidPhoneMesaageLabel.setVisible(true);
		else InvalidPhoneMesaageLabel.setVisible(false);
		
		// Show/ don't show InvalidCreditCardMesaageLabel after credit card number check		
		if (!String.valueOf(GuiScreen.getCreditCardFormattedTextField().getText().trim().length()).equals("19") && !GuiScreen.getCreditCardFormattedTextField().getText().equals("    -    -    -    ") ) 
			InvalidCreditCardMesaageLabel.setVisible(true);
		else InvalidCreditCardMesaageLabel.setVisible(false);		
		
		// Show/ don't show MissedFieldsMessage after checking that all fields filled
		if ( !checks.isAllFieldsFilled(GuiScreen.getCustomerIDTextField().getText(), GuiScreen.getFirstNameTextField().getText(), GuiScreen.getLastNameTextField().getText()) ||
				InvalidEmailMesaageLabel.isVisible() || InvalidPhoneMesaageLabel.isVisible() || InvalidCreditCardMesaageLabel.isVisible() ||
				( !GuiScreen.getPrivateCustomerRadioButton().isSelected() && !GuiScreen.getCommercialCustomerRadioButton().isSelected() )   )
			MissedFieldsMessage.setVisible(true);
		else MissedFieldsMessage.setVisible(false);
			
		// If all input OK- start checking valid input with DB
		if ( !MissedFieldsMessage.isVisible() && !InvalidCreditCardMesaageLabel.isVisible() && !InvalidCreditCardMesaageLabel.isVisible()
				&& !InvalidPhoneMesaageLabel.isVisible() && !InvalidEmailMesaageLabel.isVisible() && !InvalidCustomerIDMesaageLabel.isVisible() )
		{
			CallBack LocalUserCallBack;
			callbackCustomer fullCallbackCustomer = GuiScreen.getFullCallbackCustomer(); // Fill CallbackCustomer with GUI data
			if (GuiScreen.getNewCustomerFlag() ) fullCallbackCustomer.setWhatToDo(MessageType.setCreateNewCustomer);
			else fullCallbackCustomer.setWhatToDo(MessageType.setUpdateCustomer);
				
			//  show error message in no_changes case of update customer details
			if ( !GuiScreen.getNewCustomerFlag() && GuiScreen.isCustomerChanged(fullCallbackCustomer) ) {
				UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
				UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));
					JOptionPane.showMessageDialog(GuiScreen,	
						"There are no changes in the existing records.",
							"Error Message",
				    			JOptionPane.ERROR_MESSAGE);					
			}
			else  //Send CallbackCustomer to DB
			{
				this.fullCallbackCustomer = fullCallbackCustomer;
				Server.handleMessageFromClient( fullCallbackCustomer);
			}
		}	
	}
	/**
	 * HandleCreatePressedBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleCreatePressedBackFromServer(CallBack LocalUserCallBack){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		
		// Success case
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
			GuiScreen.setFullCallbackCustomer(fullCallbackCustomer);
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");
			AddCarDetailsButton.setVisible(true);
		}
		else 
		{
			callback_Error LocalErrorCallBack;
			LocalErrorCallBack= (callback_Error)LocalUserCallBack;
			
			// UserNameExist error case
			
			if (LocalErrorCallBack.getErrorMassage().equals("User name already exists in DB."))
			{
				UserNameExistMesaageLabel.setVisible(true);
				ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");	 //The CreateUser layer will be display	
			}
			else UserNameExistMesaageLabel.setVisible(false);
			
			
			// CustomerID error case
			CustomerIDExistMesaageLabel = GuiScreen.getCustomerIDExistMesaageLabel();
			if (LocalErrorCallBack.getErrorMassage().equals("Customer already registered in system."))
				CustomerIDExistMesaageLabel.setVisible(true);
			else CustomerIDExistMesaageLabel.setVisible(false);	
			
			// EmailExist error case
			EmailExistMesaageLabel = GuiScreen.getEmailExistMesaageLabel();
			if (LocalErrorCallBack.getErrorMassage().equals("Email belong to another customer."))
				EmailExistMesaageLabel.setVisible(true);
			else EmailExistMesaageLabel.setVisible(false);				
				
		}			
	
	}
	public callbackCustomer getFullCallbackCustomer() {
		return fullCallbackCustomer;
	}
	
	/* Add/Edit User's Cars */
	
	/**
	 * HandleAddCarPressed()- initialize AddCarsCenterPanelLayer for new cars insertion.
	 * In addition, adding action listeners to relevant buttons, then send query to DB for get cars details of current customer.
	 */
	private void HandleAddCarPressed() {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		Object[] idAraay = {GuiScreen.getCallbackCustomerUpdated().getCustomersID()};
		GuiScreen.AddCarsCenterPanelLayer();
		
		//Add Button
		AddButton = GuiScreen.getAddButton();
		AddButton.addActionListener(this);
		AddButton.setActionCommand("Add"); //Add action command
		
		//SaveCarsDetails Button
		CarsViewSaveButton = GuiScreen.getCarsViewSaveButton();
		CarsViewSaveButton.addActionListener(this);
		CarsViewSaveButton.setActionCommand("SaveCarsDetails"); //Add action command
		
		callbackStringArray CarsViewTable = new callbackStringArray(MessageType.getCarDetailes);
		CarsViewTable.setVariance(idAraay);
		Server.handleMessageFromClient(CarsViewTable);			
	}
	/**
	 * HandleAddCarPressedBackFromServer(callbackStringArray CarsViewTable) - getting cars details table from server in callbackStringArray,
	 * then display cars table in GUI.
	 * @param CarsViewTable - callbackStringArray containing cars details table.
	 */
	private void HandleAddCarPressedBackFromServer(callbackStringArray CarsViewTable) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		
		GuiScreen.setCarsViewTable(CarsViewTable.getBooleanTableModel());
		ContainerCardCenter.show(CenterCardContainer, "AddCarLayerCenter");				//The AddCar layer will be display			
	}
	/**
	 * HandleAddPressed() - Checking validation of inserted fields -display relevant message else.
	 * If all fields inserted OK - send query to DB for add new car.
	 */
	private void HandleAddPressed() {
		
		CarNumberFormattedTextField = GuiScreen.getCarNumberFormattedTextField();
		MissedFieldMessageAddCarsLabel = GuiScreen.getMissedFieldMessageAddCarsLabel();
		
		// Show/ don't show MissedFieldsMessage after checking that all fields filled		
		if (CarNumberFormattedTextField.getText().equals("  -   -  "))
			MissedFieldMessageAddCarsLabel.setVisible(true);
		else MissedFieldMessageAddCarsLabel.setVisible(false);
		
		// send CarCallback to server check
		if ( !MissedFieldMessageAddCarsLabel.isVisible() )
		{
			callbackCar fullCallbackCar = GuiScreen.getFullCallbackCar(); // Fill CallbackCustomer with GUI data
			fullCallbackCar.setWhatToDo(MessageType.setNewCar);
			
			//Send CallbackCar to DB
			Server.handleMessageFromClient(fullCallbackCar);					
		}
				
	}
	/**
	 * HandleAddPressedBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * If return callback from server is instance of callbackSuccess - refresh cars table by send query for getting cars details table from server and then 
	 * display cars table in GUI.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleAddPressedBackFromServer(CallBack LocalUserCallBack) {
		
		ExistCarNumberLabel = GuiScreen.getExistCarNumberLabel();
		// Success case
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
			GuiScreen.setSuccessAddingCarMessageLabel();
			ExistCarNumberLabel.setVisible(false);
			
			Object[] idAraay = {GuiScreen.getCallbackCustomerUpdated().getCustomersID()};
			ExistCarNumberLabel.setVisible(false);
			
			callbackStringArray CarsViewTable = new callbackStringArray(MessageType.getCarDetailes);
			CarsViewTable.setVariance(idAraay);
			Server.handleMessageFromClient(CarsViewTable);	
		}
		// Error case - car exist in DB
		else 
			{
			GuiScreen.getSuccessAddingCarMessageLabel().setVisible(false);
			ExistCarNumberLabel.setVisible(true);
			}
	}
	/**
	 * HandleSaveCarsDetailsPressed() - scanning cars table in GUI and update these cars in car table in DB by query that get 
	 * vector of Car Callbacks.
	 */
	private void HandleSaveCarsDetailsPressed(){
		CarsViewTableModelAfterChanges = (BooleanTableModel) GuiScreen.getCarsViewTable().getModel();
		callbackVector CarsChangesVector = new callbackVector(MessageType.updateCustomerCar);
		// Scan CarsViewTableAfterChange and update CarsChangesVector
		for (int i=0; i<CarsViewTableModelAfterChanges.getRowCount(); i++)
		{
			callbackCar callback_Obj = new callbackCar();
			if (CarsViewTableModelAfterChanges.getValueAt(i,3).equals(Boolean.TRUE)) callback_Obj.setYesNoNFC("Yes");
			else callback_Obj.setYesNoNFC("No");
			if (CarsViewTableModelAfterChanges.getValueAt(i,5).equals(Boolean.TRUE)) callback_Obj.setActiveCar("Yes");
			else callback_Obj.setActiveCar("No");
			callback_Obj.setCarID( Integer.parseInt((String)CarsViewTableModelAfterChanges.getValueAt(i,0)) );
			CarsChangesVector.add(callback_Obj);	
		} 
		if (CarsChangesVector.size()>0)
			Server.handleMessageFromClient(CarsChangesVector);
	}
	/**
	 * HandleSaveCarsDetailsPressedBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * If return callback from server is instance of callbackSuccess - refresh cars table by send query for getting cars details table from server and then 
	 * display cars table in GUI.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleSaveCarsDetailsPressedBackFromServer(CallBack LocalUserCallBack){
		
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
		Object[] idAraay = {GuiScreen.getCallbackCustomerUpdated().getCustomersID()};
		GuiScreen.getExistCarNumberLabel().setVisible(false);
		
		callbackStringArray CarsViewTable = new callbackStringArray(MessageType.getCarDetailes);
		CarsViewTable.setVariance(idAraay);
		Server.handleMessageFromClient(CarsViewTable);	
		GuiScreen.getSuccessAddingCarMessageLabel().setVisible(false);
		GuiScreen.getExistCarNumberLabel().setVisible(false);
		}
	}
	
	/* CustomerDetails Left Layer - Search Customer */	
	
	/**
	 * HandleSearchPressed()- Checking input customer Id from user - if not valid display relevant message.
	 * If valid - sent query to server to get customer details.
	 */
	private void HandleSearchPressed() {
		
		EnterCustomerIdTextField = GuiScreen.getEnterCustomerIdTextField();
		InvalidcustomerIDMessageLabel = GuiScreen.getInvalidcustomerIDMessageLabel();
		CustomerNotExistMessageLabel = GuiScreen.getCustomerNotExistMessageLabel();
		CustomerNotExistMessageLabel.setVisible(false);
	
		// Show/ don't show InvalidcustomerIDMessageLabel after checking CustomerId is valid
		if ( !checks.isNumeric(EnterCustomerIdTextField.getText() ) ) InvalidcustomerIDMessageLabel.setVisible(true);
		else InvalidcustomerIDMessageLabel.setVisible(false);
		
		// send CustomerCallback to server check
		if (!InvalidcustomerIDMessageLabel.isVisible())
		{
			CallBack LocalUserCallBack;
			fullCallbackCustomer = GuiScreen.getCustomerIdCallbackCustomer(); // Fill CallbackCustomer with GUI data
			fullCallbackCustomer.setWhatToDo(MessageType.getCustomer);	
			
			//Send CallbackCustomer to DB
			Server.handleMessageFromClient( fullCallbackCustomer);				
		}	
	}
	/**
	 * HandleSearchPressedBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * If return callback from server is instance of callbackSuccess - set customer details on relevant gui layers, then display CreateNewCustomerAccountLeft 
	 * for choose action button.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleSearchPressedBackFromServer(CallBack LocalUserCallBack) {
		
		ContainerCardLeft = (CardLayout)(LeftCardContainer.getLayout());

		// Success case
		if  (LocalUserCallBack instanceof callbackCustomer)
		{
			fullCallbackCustomer = (callbackCustomer) LocalUserCallBack;
			GuiScreen.setFullCallbackCustomer(fullCallbackCustomer);
			ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft"); //The CustomerDetailsLayer layer will be display
		}
		// Error case - customer ID not exist in DB
		else CustomerNotExistMessageLabel.setVisible(true);		
	}
	
	/* Campaign Patterns */

	/**
	 * HandleCampignPatternPressed()- initialize CampighnPatternLayerCenter for new pattern insertion.
	 * In addition, adding action listeners to relevant buttons.
	 * Then send query to server to get Exist Campaign Patterns for Exist Campaign Patterns comboBox.
	 */
	private void HandleCampignPatternPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		GuiScreen.CampighnPatternLayer();
		GuiScreen.setNotVisibleAllPatternsKinds();
		ContainerCardCenter.show(CenterCardContainer, "CampighnPatternLayerCenter");	//The CampighnPattern layer will be display												
		ContainerCardLeft.show(LeftCardContainer, "CampighnPatternLeft");
		
		//ExistPatterns ComboBox
		ExistPatternsComboBox = GuiScreen.getExistPatternsComboBox();
		ExistPatternsComboBox.addActionListener(this);
		ExistPatternsComboBox.setActionCommand("ExistPatternSelection"); //Add action command
		
		//PatternKind ComboBox
		PatternKindComboBox=GuiScreen.getPatternKindComboBox();
		PatternKindComboBox.addActionListener(this);
		PatternKindComboBox.setActionCommand("ChangePatternKindSelection"); //Add action command
	
		//Description TextArea 
		DescriptionTextArea = GuiScreen.getDescriptionTextArea();
		//Add FocusListener to TextArea to get automatic Description
		DescriptionTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (DescriptionTextArea.getText().equals(""))
					GuiScreen.setAutoTextToDescriptionTextArea(PatternKindComboBox.getSelectedIndex());
			}
		});		
		
		//Create Button
		CreatePatternButton = GuiScreen.getCreatePatternButton();
		CreatePatternButton.addActionListener(this);
		CreatePatternButton.setActionCommand("CreatePattern"); //Add action command
		
		//get existPatternRefresh from DB
		callbackStringArray existPatternRefresh = new callbackStringArray(MessageType.getExistCampaignPatterns);
		Server.handleMessageFromClient(existPatternRefresh);
		
	}
	/**
	 * HandlegetExistCampaignPatternsBackFromServer(callbackStringArray callBack) - update Gui components with patterns data that 
	 * came from DB.
	 * @param callBack - callbackStringArray containing Exist Patterns description values for Exist Patterns comboBox.
	 */
	private void HandlegetExistCampaignPatternsBackFromServer(callbackStringArray callBack){
		
		GuiScreen.SetExistPatternsSelection((String[])callBack.getComboBoxStringArray());
		GuiScreen.setExistPatternsCallbackArray((callbackCampaignPattern[])callBack.getVariance());
	}
	/**
	 * HandleCreatePatternPressed() - Checking validation of inserted fields -display relevant message else.
	 * If all fields inserted OK - send query to DB for adding new campaign pattern.
	 */
	private void HandleCreatePatternPressed(){
		
		InsertFuelAmountTextField = GuiScreen.getInsertFuelAmountTextField();
		DescriptionTextArea = GuiScreen.getDescriptionTextArea();
		
		// checking Inserted Fuel Amount in FuelAmount chosen case
		if (PatternKindComboBox.getSelectedIndex()==1)
			if ( !InsertFuelAmountTextField.getText().equals("")  && Checks.isNumeric(InsertFuelAmountTextField.getText() )  )
				if (Integer.parseInt(InsertFuelAmountTextField.getText()) > 0)
					GuiScreen.getInvalidFuelAmountLabel().setVisible(false);
				else GuiScreen.getInvalidFuelAmountLabel().setVisible(true);
			else GuiScreen.getInvalidFuelAmountLabel().setVisible(true);
		
		// checking if DescriptionTextArea is empty
		if (DescriptionTextArea.getText().equals(""))
			GuiScreen.getEmptyDescriptionTextAreaMessage().setVisible(true);
		else GuiScreen.getEmptyDescriptionTextAreaMessage().setVisible(false);
		
		// if we don't have invalid messages - send new pattern to server
		if ( !GuiScreen.getEmptyDescriptionTextAreaMessage().isVisible() && !GuiScreen.getInvalidFuelAmountLabel().isVisible())
		{
			callbackCampaignPattern newCampaignPattern = GuiScreen.getNewCampaignPatternCallback();
			Server.handleMessageFromClient( newCampaignPattern);
		}	
	}
	/**
	 * HandleCreatePatternPressedBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * If return callback from server is instance of callbackSuccess - send query to server to get Exist Campaign Patterns for Exist Campaign Patterns comboBox.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleCreatePatternPressedBackFromServer(CallBack LocalUserCallBack){		
		// Success case
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
			GuiScreen.getSuccessAddPatternMessageLabel().setVisible(true);
			GuiScreen.getPatternExistInDbMessageLabel().setVisible(false);
			//get existPatternRefresh from DB - add the new pattern to existPattern comboBox
			callbackStringArray existPatternRefresh = new callbackStringArray(MessageType.getExistCampaignPatterns);
			Server.handleMessageFromClient(existPatternRefresh);
		}
		else  // Error case
		{
			GuiScreen.getSuccessAddPatternMessageLabel().setVisible(false);
			GuiScreen.getPatternExistInDbMessageLabel().setVisible(true);
		}
	}
	
	/* Rates Report */
	
	/**
	 * HandleGenerateReportPressed()- initialize RatesReportlLayer.
	 * In addition, adding action listeners to relevant buttons.
	 */
	private void HandleReportPressed(){
		GuiScreen.RatesReportlLayer();
		GuiScreen.SetVisibleRelevantComponents(false);
		
		ContainerCardLeft.show(LeftCardContainer, "EmptyLeftPanel");
		ContainerCardCenter.show(CenterCardContainer, "RatesReportLayerCenter");	//The RatesReport layer will be display	
		
		// GenerateReport Button
		GenerateReportButton = GuiScreen.getGenerateReportButton();
		GenerateReportButton.addActionListener(this);
		GenerateReportButton.setActionCommand("GenerateReport"); //Add action command	
		
		// UpdateRate Button
		UpdateRateButton = GuiScreen.getUpdateRateButton();
		UpdateRateButton.addActionListener(this);
		UpdateRateButton.setActionCommand("UpdateRate"); //Add action command	
	}	
	/**
	 * HandleGenerateReportPressed() - Checking GenerateDateChooser validation and display relevant message.
	 * If GenerateDateChooser inserted OK - send query to DB for getting rate report by inserted date.
	 */
	private void HandleGenerateReportPressed(){
		JDateChooser GenerateDateChooser = GuiScreen.getGenerateDateChooser();
		// show/ don't show InvalidInsertedDateLabel
		if (!checks.isPassedDate(((JTextField)GenerateDateChooser.getDateEditor().getUiComponent()).getText())) GuiScreen.getInvalidInsertedDateLabel().setVisible(true);
		else { //get RatesReportTable from DB
			GuiScreen.getInvalidInsertedDateLabel().setVisible(false);
			String[] dateArr={((JTextField)GenerateDateChooser.getDateEditor().getUiComponent()).getText()};
			callbackStringArray RatesReportTable = new callbackStringArray(MessageType.getAnalyticSystemRatingCalculation);
			RatesReportTable.setVariance(dateArr);
			Server.handleMessageFromClient(RatesReportTable);
		}		
	}
	/**
	 * HandleGenerateReportBackFromServer(callbackStringArray RatesReportTable) - getting report details from DB and display it on GUI.
	 * In addition, display relevant components.
	 * @param RatesReportTable - callbackStringArray contain report details.
	 */
	private void HandleGenerateReportBackFromServer(callbackStringArray RatesReportTable){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		// set report table on GUI table
		GuiScreen.setRatesReportTable(RatesReportTable.getDefaultTableModel());
		// define hander to Export Button
		new JTableToExcel(GuiScreen.getRatesReportExportButton(), GuiScreen.getRatesReportTable());
		GuiScreen.setRatesReportLabel();
		GuiScreen.SetVisibleRelevantComponents(true);
		ContainerCardCenter.show(CenterCardContainer, "RatesReportLayerCenter");				//The RatesReport layer will be display											
	}
	
	/**
	 * HandleUpdateRatePressed() -  send query to DB for update rate field in customer table according to new rate in Rates Report table that generated.
	 */
	private void HandleUpdateRatePressed(){
		JDateChooser GenerateDateChooser = GuiScreen.getGenerateDateChooser();
		//updates Rates to DB
		callbackStringArray UpdateRates = new callbackStringArray(MessageType.updateAnalyticSystemRatingCalculation);
		String[] dateArr={((JTextField)GenerateDateChooser.getDateEditor().getUiComponent()).getText()};
		UpdateRates.setVariance(dateArr);
		Server.handleMessageFromClient(UpdateRates);			
	}
	/**
	 * HandleUpdateRateBackFromServer(CallBack LocalUserCallBack) - Checking instance of return callback from server and show relevant message.
	 * @param LocalUserCallBack - Callback answer that return from server.
	 */
	private void HandleUpdateRateBackFromServer(CallBack LocalUserCallBack){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		
		if  (LocalUserCallBack instanceof callbackSuccess){
			GuiScreen.getUpdateSuccessMessageLabel().setVisible(true);
		}	
	}
	
	/* Controller */
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){
		
			switch(((CallBack) arg).getWhatToDo()){
				case setCreateNewCustomer:
					HandleCreatePressedBackFromServer((CallBack) arg);
					break;
				case setUpdateCustomer:
					HandleCreatePressedBackFromServer((CallBack) arg);
					break;
				case setNewCar:
					HandleAddPressedBackFromServer((CallBack) arg);
					break;
				case getCustomer:
					HandleSearchPressedBackFromServer((CallBack) arg);
					break;
				case getCarDetailes:
					HandleAddCarPressedBackFromServer((callbackStringArray) arg);
					break;					
				case getAnalyticSystemRatingCalculation:
					HandleGenerateReportBackFromServer((callbackStringArray) arg);
					break;				
				case updateAnalyticSystemRatingCalculation:
					HandleUpdateRateBackFromServer((CallBack) arg);
					break;	
				case setNewCampignPattern:
					HandleCreatePatternPressedBackFromServer((CallBack) arg);
					break;
				case getExistCampaignPatterns:
					HandlegetExistCampaignPatternsBackFromServer((callbackStringArray) arg);
					break;	
				case updateCustomerCar:
					HandleSaveCarsDetailsPressedBackFromServer((CallBack) arg);
					break;						
			default:
				super.update(o, arg);
				break;
			}
		}
		
		
		// getMarketingRepresentativeComboBox  only case
		else if(arg instanceof Vector<?>){
			Vector<String[]> ComboBoxs = (Vector<String[]>) arg;
			GuiScreen.SetComboBoxSelection(ComboBoxs.get(0));  // purcase plan combo box
			GuiScreen.SetFuelIDComboBoxSelection(ComboBoxs.get(1));
			GuiScreen.SetCostingModelComboBoxSelection(ComboBoxs.get(2));
			GuiScreen.setGasStationsComboBox(ComboBoxs.get(3));
		}
			
		
		
	}

}