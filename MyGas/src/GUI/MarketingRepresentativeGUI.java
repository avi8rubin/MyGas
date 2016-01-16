/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCampaignPattern;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackUser;
import callback.callbackVector;
import client.Client;
import common.BooleanTableModel;
import common.Checks;
import common.MessageType;

public class MarketingRepresentativeGUI extends abstractPanel_GUI{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * newCustomerFlag is system flag that let us know which process use in the same layers- 
	 * CreateNewCustomerAccountLayer or AddPersonalDetails.
	 * Both using CreateUserLayer (center layer) & AddCars ( center layer).
	 * If newCustomerFlag=1 , CreateNewCustomerAccountLayer process using those common layers to create new customer account.
	 * If newCustomerFlag=0 , AddPersonalDetails process using those common layers to update customer account's details.
	 */
	private boolean newCustomerFlag=true;
	
	/**
	 * Gui variables
	 */
	
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
	private JLabel LogoImage;
	
/**
 * CustomerDetailsLayer Left Layer Components
 */
	private JLayeredPane CustomerDetailsLayer;
	private JTextField EnterCustomerIdTextField;
	private JButton SearchButton;
	private JLabel InvalidcustomerIDMessageLabel;
	private JLabel CustomerNotExistMessageLabel;
	/**
	 * fullCallbackCustomer - CallbackCustomer that return from DB with all details about sent customer.
	 */
	private callbackCustomer fullCallbackCustomer ;
	
/**
 * CampighnPattern Left Layer Components
 */
	private JLayeredPane CampighnPatternLayer;
	/**
	 * ExistPatternsComboBox - contain description of all exist campaign patterns 
	 */
	private JComboBox ExistPatternsComboBox;
	private JLabel ExistPatternsLabel;
	/** 
	 * ExistPatternsCallbackArray -callbackCampaignPattern array that contain all details of exist campaign patterns-
	 * using for get relevant data from callbackCampaignPattern when his description chosen in ExistPatternsComboBox.
	 */
	private callbackCampaignPattern [] ExistPatternsCallbackArray;
	
/**
 * CreateUserLayer Center Layer Components
 */
	private JLayeredPane CreateUserLayer;
	private JLabel UserNameLabel;
	private JPasswordField PasswordPasswordField;
	private JPasswordField PasswordValidatePasswordField;
	private JLabel UserNameExistMesaageLabel;
	private JLabel PasswordValidationFailedMessageLabel;
	private JTextField UserNametextField;
	private JLabel IsActiveLabel;
	private JCheckBox IsActiveCheckBox;	
	private JButton NextButton;
	private JLabel MissedFieldsMessageLabel;
	
/**
 * AddPersonalDetails Center Layer Components
 */
	private JLayeredPane AddPersonalDetails;
	private JLabel CustimerIDLabel;
	private JLabel FirstNameLabel;
	private JLabel LastNameLabel;
	private JLabel EmailLabel;
	private JLabel PhoneLabel;
	private JLabel CreditCardLabel;
	private JLabel CustomerTypeLabel;
	private JLabel PurchasePlanLabel;
	private JLabel InvalidCustomerIDMesaageLabel;
	private JLabel CustomerIDExistMesaageLabel;
	private JLabel EmailExistMesaageLabel;
	private JLabel InvalidEmailMesaageLabel;
	private JTextField CustomerIDTextField;
	private JTextField FirstNameTextField;
	private JTextField LastNameTextField;
	private JTextField EmailTextField;
	private MaskFormatter PhoneMask = null;
	private JFormattedTextField PhoneFormattedTextField;
	private MaskFormatter CreditCardMask = null;
	private JFormattedTextField CreditCardFormattedTextField;
	private ButtonGroup PurchasePlanButtonGroup;
	private JRadioButton PrivateCustomerRadioButton;
	private JRadioButton CommercialCustomerRadioButton;
	private JComboBox PurchasePlanComboBox;
	private JComboBox CostingModelComboBox;
	private JButton CreateButton;
	private JButton EditButton;
	private JButton SaveButton;
	private JLabel MissedFieldsMessage;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;

/**
 * AddCars Center Layer Components
 */
	private JLayeredPane AddCarPanel;
	private JLabel CarNumberLabel;
	private JLabel FuelIDLabel;
	private JLabel NFCLabel;
	private JLabel CostingModelLabel;
	private JFormattedTextField CarNumberFormattedTextField;
	private JComboBox FuelIDComboBox;
	private JCheckBox NFCNewCheckBox;
	private JButton AddButton;
	private JLabel ExistCarNumberLabel;
	private MaskFormatter CarNumberMask;
	private JLabel MissedFieldMessageAddCarsLabel;
	private JLabel SuccessAddingCarMessageLabel;
	/**
	 * Create JTable - that the 2 column is editable.
	 */
	private JTable CarsViewTable = new JTable(){
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) { 
        	if(column==1 || column==3)
        		return true;
        	else return false;
        };       
	};
	private JLabel CarsViewLabel;
	private JButton CarsViewSaveButton;
	
/**
 * CampighnPattern Center Layer Components	
 */
	private JLayeredPane CampighnPatternPanel;
	private JLabel PatternKindLabel;
	private JComboBox PatternKindComboBox;
	private JLabel ChooseRateLabel;
	private JComboBox ChooseRateComboBox;
	private JLabel InsertFuelAmountLabel;
	private JTextField InsertFuelAmountTextField;
	private JLabel InvalidFuelAmountLabel;
	private JComboBox FuelTypeComboBox;
	private JLabel ChooseGasStationLabel;
	private JComboBox GasStationsComboBox;
	private JLabel ChooseTimeSpanLabel;
	private JLabel TimeSpanToLabel;
	private JSpinner FromHourSpinner;
	private JSpinner ToHourSpinner;
	private JLabel ChooseFuelTypeLabel;
	private JLabel DiscountLabel;
	private JSpinner DiscountSpinner;
	private JLabel DiscountPercentLabel;
	private JLabel DescriptionLabel;
	private JTextArea DescriptionTextArea;
	private JButton CreatePatternButton;
	private JLabel EmptyDescriptionTextAreaMessage;
	private JLabel PatternExistInDbMessageLabel;
	private JLabel SuccessAddPatternMessageLabel;

/**
 *  RatesReport Center Layer Components
 */
	
	private JLayeredPane RatesReportLayer;
	private JLayeredPane RatesReportPanel;
	private JLabel InsertDateLabel;
	private JDateChooser GenerateDateChooser;
	private JButton GenerateReportButton;
	private JLabel InvalidInsertedDateLabel;
	private JLabel RatesReportLabel;
	private JScrollPane RatesReportScroll;
	/**
	 * Create JTable - no column is editable.
	 */
	private JTable RatesReportTable = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) { 
	                return false;  
	        };
		};
	private JButton RatesReportExportButton;
	private JButton UpdateRateButton;
	private JLabel UpdateSuccessMessageLabel;
	
	/**
	 * MarketingRepresentativeGUI constructor - build top panel, left panels  and relevant combo-boxes from center panels
	 * for load combo-boxes values only once when the MarketingRepresentativeGUI created.
	 */
	public MarketingRepresentativeGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/** 
		 * Create top panel gui.
		 */
		CreateNewCustomerAccountButton = new JButton("Create New Customer Account");
		CreateNewCustomerAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateNewCustomerAccountButton.setBounds(12, 101, 329, 38);	
		TopPanel.add(CreateNewCustomerAccountButton);					
		
		/* ------- Adding new button to Top Panel -------- */				
		CustomerDetailsButton = new JButton("Customer Details");						//Global variable
		CustomerDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerDetailsButton.setBounds(351, 101, 222, 38);
		TopPanel.add(CustomerDetailsButton);
			
		/* ------- Adding new button to Top Panel -------- */		
		CreateNewCampignPatternButton = new JButton("Create New Campign Pattern");		//Global variable
		CreateNewCampignPatternButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateNewCampignPatternButton.setBounds(583, 101, 354, 38);
		TopPanel.add(CreateNewCampignPatternButton);		
		
		/* ------- Adding new button to Top Panel -------- */
		ReportsButton = new JButton("Rates Report");											//Global variable
		ReportsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportsButton.setBounds(947, 101, 168, 38);
		TopPanel.add(ReportsButton);		
		
		
		/* ------- Adding new layer to CreateNewCustomerAccount Panel -------- */
		

		/**
		 * Create and adding CreateNewCustomer Left layer to CreateNewCustomerAccount Panel
		 */
		CreateNewCustomerAccountLayer = new JLayeredPane();
		CreateNewCustomerAccountLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateNewCustomerAccountLayer.setBackground(new Color(169, 169, 169));

		/**
		 * Create and adding left CreateNewCustomer Layer
		 */
		LeftCardContainer.add(CreateNewCustomerAccountLayer, "CreateNewCustomerAccountLeft");
		CreateNewCustomerAccountLayer.setOpaque(true);
		CreateNewCustomerAccountLayer.setName("CreateNewCustomerAccountLeft");
		
		//buttons left layer
		CreateUserButton = new JButton("<html>Create User</html>");
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateUserButton.setBounds(27, 33, 212, 46);
		
		AddCarDetailsButton = new JButton("<html>Add Car Details</html>");
		AddCarDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddCarDetailsButton.setBounds(27, 99, 212, 46);
		AddCarDetailsButton.setVisible(false);
		
		// Logo on left panel
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		
		CreateNewCustomerAccountLayer.add(CreateUserButton);
		CreateNewCustomerAccountLayer.add(AddCarDetailsButton);
		CreateNewCustomerAccountLayer.add(LogoImage);
		
		
		/**
		 * Create and adding CustomerDetails Left layer to CreateNewCustomerAccount Panel
		 */
		CustomerDetailsLayer = new JLayeredPane();
		CustomerDetailsLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CustomerDetailsLayer.setBackground(new Color(169, 169, 169));

		//add left CustomerDetails Layer 
		LeftCardContainer.add(CustomerDetailsLayer, "CustomerDetailsLayerLeft");
		CustomerDetailsLayer.setOpaque(true);
		CustomerDetailsLayer.setName("CustomerDetailsLayerLeft");
		
		/**
		 * Create and adding CustomerDetails left layer
		 */
		JLabel EnterCustomerIdLabel = new JLabel("Enter customer ID :");
		EnterCustomerIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EnterCustomerIdLabel.setBounds(54, 11, 187, 31);
		CustomerDetailsLayer.add(EnterCustomerIdLabel);
		
		EnterCustomerIdTextField = new JTextField();
		EnterCustomerIdTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EnterCustomerIdTextField.setBounds(38, 44, 222, 31);
		CustomerDetailsLayer.add(EnterCustomerIdTextField);
		EnterCustomerIdTextField.setColumns(10);
		
		SearchButton = new JButton("Search");
		SearchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SearchButton.setBounds(75, 86, 125, 38);
		CustomerDetailsLayer.add(SearchButton);
		
		InvalidcustomerIDMessageLabel = new JLabel("Invalid customer ID inserted");
		InvalidcustomerIDMessageLabel.setForeground(Color.WHITE);
		InvalidcustomerIDMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidcustomerIDMessageLabel.setVisible(false);
		InvalidcustomerIDMessageLabel.setBounds(10, 126, 265, 46);
		CustomerDetailsLayer.add(InvalidcustomerIDMessageLabel);
		
		CustomerNotExistMessageLabel = new JLabel("Customer is not exist");
		CustomerNotExistMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		CustomerNotExistMessageLabel.setEnabled(false);
		CustomerNotExistMessageLabel.setVisible(false);
		CustomerNotExistMessageLabel.setBounds(54, 126, 210, 46);
		CustomerDetailsLayer.add(CustomerNotExistMessageLabel);
		
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		CustomerDetailsLayer.add(LogoImage);
		
		/**
		 * Define relevant comboBoxs to get values one time from DB- only when the GUI loading.		
		 */
		PurchasePlanComboBox = new JComboBox();
		PurchasePlanComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PurchasePlanComboBox.setBounds(179, 438, 460, 27);
		
		CostingModelComboBox = new JComboBox();
		CostingModelComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CostingModelComboBox.setBounds(179, 484, 233, 27);
		
		FuelIDComboBox = new JComboBox();
		FuelIDComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FuelIDComboBox.setBounds(154, 74, 158, 27);
		
		CreateNewUserCenterLayer();
		
		/* ------- Adding new layer to CampighnPatterns Panel -------- */
		
		/**
		 * Create and adding CampighnPatterns Left layer to CampighnPatterns Panel
		 */
		CampighnPatternLayer = new JLayeredPane();
		CampighnPatternLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CampighnPatternLayer.setBackground(new Color(169, 169, 169));

		//add left CampighnPatterns Layer
		LeftCardContainer.add(CampighnPatternLayer, "CampighnPatternLeft");
		CampighnPatternLayer.setOpaque(true);
		CampighnPatternLayer.setName("CampighnPatternLeft");
		
		//components left layer
		ExistPatternsLabel = new JLabel("Exist Patterns :");
		ExistPatternsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ExistPatternsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ExistPatternsLabel.setBounds(64, 28, 164, 34);
		CampighnPatternLayer.add(ExistPatternsLabel);
		
		ExistPatternsComboBox = new JComboBox();
		ExistPatternsComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ExistPatternsComboBox.setBounds(10, 73, 280, 36);
		CampighnPatternLayer.add(ExistPatternsComboBox);
		
		// Logo on left panel
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		CampighnPatternLayer.add(LogoImage);
		
		/* ------- Adding CampighnPatterns Center components to CampighnPatterns Panel -------- */
		/**
		 * Define relevant comboBoxs to get values one time from DB- only when the GUI loading.		
		 */
		FuelTypeComboBox = new JComboBox();
		FuelTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FuelTypeComboBox.setBounds(213, 67, 155, 31);
		
		GasStationsComboBox = new JComboBox();
		GasStationsComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GasStationsComboBox.setBounds(213, 67, 201, 31);
		
		/* ------- Adding new layer to RatesReport Panel -------- */
	
		/**
		 * Create and adding RatesReport Left layer to RatesReport Panel
		 */
		RatesReportLayer = new JLayeredPane();
		RatesReportLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		RatesReportLayer.setBackground(new Color(169, 169, 169));

		//add left RatesReport Layer
		LeftCardContainer.add(RatesReportLayer, "RatesReportLeft");
		RatesReportLayer.setOpaque(true);
		RatesReportLayer.setName("RatesReportLeft");
		
		//buttons left layer
		GenerateReportButton = new JButton("<html>Generate Report</html>");
		GenerateReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GenerateReportButton.setBounds(27, 33, 212, 46);
		
		UpdateRateButton = new JButton("<html>Update</html>");
		UpdateRateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		UpdateRateButton.setBounds(27, 99, 212, 46);
		UpdateRateButton.setVisible(false);
		
		UpdateSuccessMessageLabel = new JLabel("<html>Customers rates<br>updated successfully</html>", SwingConstants.CENTER);
		UpdateSuccessMessageLabel.setForeground(Color.WHITE);
		UpdateSuccessMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));		
		UpdateSuccessMessageLabel.setBounds(10, 146, 231, 66);
		UpdateSuccessMessageLabel.setVisible(false);
		
		// Logo on left panel
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		
		RatesReportLayer.add(GenerateReportButton);
		RatesReportLayer.add(UpdateRateButton);
		RatesReportLayer.add(UpdateSuccessMessageLabel);
		RatesReportLayer.add(LogoImage);
		
	} 
	
	/* Top Panel layer  */
	
	public JButton getCreateNewCustomerAccountButton(){
		return CreateNewCustomerAccountButton;
	}
	public JButton getCustomerDetailsButton(){
		return CustomerDetailsButton;
	}
	public JButton getCreateNewCampignPatternButton() {
		return CreateNewCampignPatternButton;
	}
	public JButton getReportsButton() {
		return ReportsButton;
	}
	
	/* CreateNewCustomerAccount Left Layer */
	
	public JButton getCreateUserButton(){
		return CreateUserButton;
	}
	public JButton getAddCarDetailsButton(){
		return AddCarDetailsButton;
	}
	
	/* CustomerDetails Left Layer */
	
	public JTextField getEnterCustomerIdTextField() {
		return EnterCustomerIdTextField;
	}
	public JButton getSearchButton() {
		return SearchButton;
	}
	/**
	 * Get inserted customer ID from EnterCustomerIdTextField.
	 * @return callbackCustomer filled by customer ID for asking customer data from server.
	 */
	public callbackCustomer getCustomerIdCallbackCustomer(){
		callbackCustomer customerCallback = new callbackCustomer();
		
		customerCallback.setCustomersID( Integer.valueOf(EnterCustomerIdTextField.getText().trim()) );
	
		return customerCallback;
	}
	public JLabel getInvalidcustomerIDMessageLabel() {
		return InvalidcustomerIDMessageLabel;
	}
	public JLabel getCustomerNotExistMessageLabel() {
		return CustomerNotExistMessageLabel;
	}

	/* Create New User Center Layer */
	
	/**
	 * Build CreateUser & AddPersonalDetails Center Layers and their components.
	 * Fill these components by customer data in edit customer case.
	 */
	public void CreateNewUserCenterLayer(){
		// show AddCarDetailsButton only in new customer case
		if (newCustomerFlag) AddCarDetailsButton.setVisible(false);
		/* ------- Adding CreateUser Center Layer -------- */
		CreateUserLayer = new JLayeredPane();
		CreateUserLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateUserLayer,"CreateUserLayerCenter");
		CreateUserLayer.setOpaque(true);
		CreateUserLayer.setName("CreateUserLayerCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to CreateUserLayer --------*/		
		
		UserNameLabel = new JLabel("User Name");
		UserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		UserNameLabel.setEnabled(false);
		UserNameLabel.setBounds(27, 47, 115, 34);
		CreateUserLayer.add(UserNameLabel);		
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordLabel.setEnabled(false);
		PasswordLabel.setBounds(27, 104, 100, 34);
		CreateUserLayer.add(PasswordLabel);
	
		JLabel PasswordValidateLabel = new JLabel("Password Validate");
		PasswordValidateLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidateLabel.setEnabled(false);
		PasswordValidateLabel.setBounds(27, 151, 163, 46);
		CreateUserLayer.add(PasswordValidateLabel);		
				
		PasswordPasswordField = new JPasswordField();
		PasswordPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordPasswordField.setBounds(179, 111, 201, 27);
		CreateUserLayer.add(PasswordPasswordField);
		
		PasswordValidatePasswordField = new JPasswordField();
		PasswordValidatePasswordField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidatePasswordField.setBounds(179, 161, 201, 27);
		CreateUserLayer.add(PasswordValidatePasswordField);
		
		IsActiveLabel = new JLabel("Is Active ");
		IsActiveLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		IsActiveLabel.setEnabled(false);
		IsActiveLabel.setBounds(27, 193, 132, 46);
		CreateUserLayer.add(IsActiveLabel);
		
		IsActiveCheckBox = new JCheckBox("");
		IsActiveCheckBox.setBounds(179, 204, 97, 23);
		IsActiveCheckBox.setSelected(true);
		CreateUserLayer.add(IsActiveCheckBox);
	
		// Message to marketing representative - display only in User name existance case
		UserNameExistMesaageLabel = new JLabel("User name already exist in system");
		UserNameExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		UserNameExistMesaageLabel.setEnabled(false);
		UserNameExistMesaageLabel.setBounds(403, 41, 309, 46);	
		UserNameExistMesaageLabel.setVisible(false);
		CreateUserLayer.add(UserNameExistMesaageLabel);
		
		// Message to marketing representative - display only in Password Validation Failure case
		PasswordValidationFailedMessageLabel = new JLabel("Password Validation Failed");
		PasswordValidationFailedMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		PasswordValidationFailedMessageLabel.setEnabled(false);
		PasswordValidationFailedMessageLabel.setBounds(403, 122, 268, 46);
		PasswordValidationFailedMessageLabel.setVisible(false);
		CreateUserLayer.add(PasswordValidationFailedMessageLabel);
		
		UserNametextField = new JTextField();
		UserNametextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		UserNametextField.setColumns(10);
		UserNametextField.setBounds(179, 51, 201, 27);
		CreateUserLayer.add(UserNametextField);
		
		NextButton = new JButton("Next");
		NextButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NextButton.setBounds(607, 248, 125, 38);
		CreateUserLayer.add(NextButton);
		
		// Message to marketing representative - display only in Missed Fields case
		MissedFieldsMessageLabel = new JLabel("There is one or more missed fields");
		MissedFieldsMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldsMessageLabel.setEnabled(false);
		MissedFieldsMessageLabel.setBounds(249, 240, 348, 46);
		MissedFieldsMessageLabel.setVisible(false);
		CreateUserLayer.add(MissedFieldsMessageLabel);
		
		/* ------- AddPersonalDetails Center Layer -------- */
		
		AddPersonalDetails = new JLayeredPane();
		AddPersonalDetails.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(AddPersonalDetails,"AddPersonalDetailsCenter");
		AddPersonalDetails.setOpaque(true);
		AddPersonalDetails.setName("AddPersonalDetailsCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to CreateUserLayer --------*/		
		CustimerIDLabel = new JLabel("Customer ID :");
		CustimerIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustimerIDLabel.setEnabled(false);
		CustimerIDLabel.setBounds(39, 50, 115, 34);
		AddPersonalDetails.add(CustimerIDLabel);		
		
		FirstNameLabel = new JLabel("First Name :");
		FirstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FirstNameLabel.setEnabled(false);
		FirstNameLabel.setBounds(39, 107, 100, 34);
		AddPersonalDetails.add(FirstNameLabel);
	
		LastNameLabel = new JLabel("Last Name :");
		LastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		LastNameLabel.setEnabled(false);
		LastNameLabel.setBounds(39, 154, 163, 46);
		AddPersonalDetails.add(LastNameLabel);
		
		EmailLabel = new JLabel("Email :");
		EmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		EmailLabel.setEnabled(false);
		EmailLabel.setBounds(39, 211, 163, 46);
		AddPersonalDetails.add(EmailLabel);
		
		PhoneLabel = new JLabel("Phone :");
		PhoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PhoneLabel.setEnabled(false);
		PhoneLabel.setBounds(39, 267, 163, 46);
		AddPersonalDetails.add(PhoneLabel);
		
		CreditCardLabel = new JLabel("Credit Card :");
		CreditCardLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CreditCardLabel.setEnabled(false);
		CreditCardLabel.setBounds(39, 324, 163, 46);
		AddPersonalDetails.add(CreditCardLabel);
		
		CustomerTypeLabel = new JLabel("Customer Type :");
		CustomerTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustomerTypeLabel.setEnabled(false);
		CustomerTypeLabel.setBounds(39, 381, 163, 46);
		AddPersonalDetails.add(CustomerTypeLabel);
		
		PurchasePlanLabel = new JLabel("Purchase Plan :");
		PurchasePlanLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PurchasePlanLabel.setEnabled(false);
		PurchasePlanLabel.setBounds(39, 427, 163, 46);
		AddPersonalDetails.add(PurchasePlanLabel);
	
		CostingModelLabel = new JLabel("Costing Model :");
		CostingModelLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CostingModelLabel.setEnabled(false);
		CostingModelLabel.setBounds(39, 480, 115, 34);
		AddPersonalDetails.add(CostingModelLabel);
		
		InvalidCustomerIDMesaageLabel = new JLabel("Invalid Customer ID ");
		InvalidCustomerIDMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidCustomerIDMesaageLabel.setEnabled(false);
		InvalidCustomerIDMesaageLabel.setBounds(403, 44, 184, 46);
		InvalidCustomerIDMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidCustomerIDMesaageLabel);		
		
		CustomerIDExistMesaageLabel = new JLabel("Customer ID already exist in system");
		CustomerIDExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		CustomerIDExistMesaageLabel.setEnabled(false);
		CustomerIDExistMesaageLabel.setBounds(403, 41, 340, 46);
		CustomerIDExistMesaageLabel.setVisible(false);
		AddPersonalDetails.add(CustomerIDExistMesaageLabel);
	
		EmailExistMesaageLabel = new JLabel("Email already exist in system");
		EmailExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		EmailExistMesaageLabel.setEnabled(false);
		EmailExistMesaageLabel.setBounds(497, 211, 278, 46);
		EmailExistMesaageLabel.setVisible(false);
		AddPersonalDetails.add(EmailExistMesaageLabel);
		
		InvalidEmailMesaageLabel = new JLabel("Invalid email");
		InvalidEmailMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidEmailMesaageLabel.setEnabled(false);
		InvalidEmailMesaageLabel.setBounds(497, 211, 278, 46);
		InvalidEmailMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidEmailMesaageLabel);
		
		CustomerIDTextField = new JTextField();
		CustomerIDTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustomerIDTextField.setColumns(10);
		CustomerIDTextField.setBounds(179, 51, 201, 27);
		CustomerIDTextField.setToolTipText("Customer ID have to include only numbers.\r\nFor example: 123456789");
		AddPersonalDetails.add(CustomerIDTextField);
		
		FirstNameTextField = new JTextField();
		FirstNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FirstNameTextField.setColumns(10);
		FirstNameTextField.setBounds(179, 108, 201, 27);
		AddPersonalDetails.add(FirstNameTextField);
		
		LastNameTextField = new JTextField();
		LastNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		LastNameTextField.setBounds(179, 161, 201, 27);
		AddPersonalDetails.add(LastNameTextField);
		
		EmailTextField = new JTextField();
		EmailTextField.setToolTipText("Email have to include @.\r\nFor example: david@gmail.com");
		EmailTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		EmailTextField.setBounds(179, 227, 295, 27);
		AddPersonalDetails.add(EmailTextField);
		
	    try {
	        //
	        // Create a MaskFormatter for accepting phone number, the # symbol accept
	        // only a number. We can also set the empty value with a place holder
	        // character.
	        //
	    	PhoneMask = new MaskFormatter("###-#######");
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		
	    PhoneFormattedTextField = new JFormattedTextField(PhoneMask);
		PhoneFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PhoneFormattedTextField.setToolTipText("Phone have to written with 10 digits. For example: 050-1234567");
		PhoneFormattedTextField.setBounds(179, 280, 125, 27);
		AddPersonalDetails.add(PhoneFormattedTextField);
		
	    try {
	        //
	        // Create a MaskFormatter for accepting CreditCard number, the # symbol accept
	        // only a number. We can also set the empty value with a place holder
	        // character.
	        //
	    	CreditCardMask = new MaskFormatter("####-####-####-####");
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	
	    CreditCardFormattedTextField = new JFormattedTextField(CreditCardMask);
		CreditCardFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CreditCardFormattedTextField.setToolTipText("Credit Card have to written with 16 digits. For example: 1234-5678-9876-4321");
		CreditCardFormattedTextField.setBounds(179, 334, 241, 27);
		AddPersonalDetails.add(CreditCardFormattedTextField);       
	    
		PrivateCustomerRadioButton = new JRadioButton("Private");
		PrivateCustomerRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PrivateCustomerRadioButton.setBounds(179, 393, 100, 23);
		AddPersonalDetails.add(PrivateCustomerRadioButton);
		
		CommercialCustomerRadioButton = new JRadioButton("Commercial");
		CommercialCustomerRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CommercialCustomerRadioButton.setBounds(279, 393, 141, 23);
		AddPersonalDetails.add(CommercialCustomerRadioButton);
		
		PurchasePlanButtonGroup = new ButtonGroup();
		PurchasePlanButtonGroup.add(PrivateCustomerRadioButton);
		PurchasePlanButtonGroup.add(CommercialCustomerRadioButton);
		
		AddPersonalDetails.add(PurchasePlanComboBox);
		AddPersonalDetails.add(CostingModelComboBox);
		
		CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateButton.setBounds(678, 496, 125, 38);	
		AddPersonalDetails.add(CreateButton);
		
		EditButton = new JButton("Edit");
		EditButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EditButton.setBounds(678, 438, 125, 38);
		AddPersonalDetails.add(EditButton);
		
		SaveButton = new JButton("Save");
		SaveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SaveButton.setBounds(678, 496, 125, 38);
		AddPersonalDetails.add(SaveButton);
		
		MissedFieldsMessage = new JLabel("There is one or more missed fields");
		MissedFieldsMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldsMessage.setEnabled(false);
		MissedFieldsMessage.setBounds(320, 505, 348, 46);
		MissedFieldsMessage.setVisible(false);
		AddPersonalDetails.add(MissedFieldsMessage);
		
		InvalidPhoneMesaageLabel = new JLabel("Invalid phone number inserted");
		InvalidPhoneMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidPhoneMesaageLabel.setEnabled(false);
		InvalidPhoneMesaageLabel.setBounds(340, 267, 278, 46);
		InvalidPhoneMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidPhoneMesaageLabel);
		
		InvalidCreditCardMesaageLabel = new JLabel("Invalid credit card number inserted");
		InvalidCreditCardMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidCreditCardMesaageLabel.setEnabled(false);
		InvalidCreditCardMesaageLabel.setBounds(444, 324, 331, 46);
		InvalidCreditCardMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidCreditCardMesaageLabel);
		
		/**
		 * Show customer details on GUI components - only if it's edit customer case.
		 */
		if (!newCustomerFlag)
		{			
			UserNametextField.setText( fullCallbackCustomer.getUserName() );
			PasswordPasswordField.setText( fullCallbackCustomer.getUserPassword() );
			PasswordValidatePasswordField.setText( fullCallbackCustomer.getUserPassword() );
			if ( fullCallbackCustomer.getISActive().equals("Yes") ) IsActiveCheckBox.setSelected(true);
			else IsActiveCheckBox.setSelected(false);	
			CustomerIDTextField.setText( String.valueOf(fullCallbackCustomer.getCustomersID()) );
			FirstNameTextField.setText( fullCallbackCustomer.getCustomerFirstName() );
			LastNameTextField.setText( fullCallbackCustomer.getCustomerLastName() );
			EmailTextField.setText( fullCallbackCustomer.getEmail() );	
			PhoneFormattedTextField.setText( fullCallbackCustomer.getPhoneNumber() );
			CreditCardFormattedTextField.setText( fullCallbackCustomer.getCreditCard() );	
			if ( fullCallbackCustomer.getCustomerType().equals("Private") ) PrivateCustomerRadioButton.setSelected(true);
			else CommercialCustomerRadioButton.setSelected(true);
			PurchasePlanComboBox.setSelectedIndex(fullCallbackCustomer.getPlanID()-1);
			CostingModelComboBox.setSelectedIndex(fullCallbackCustomer.getCostingModelID()-1);
			
		}
		
		
	}
	public JLayeredPane getCreateNewCustomerAccountLayer(){
		return CreateNewCustomerAccountLayer;}
	public JButton getNextButton(){
		return NextButton;
	}
	public JLabel getPasswordValidationFailedMessageLabel() {
		return PasswordValidationFailedMessageLabel;
	}
	public JPasswordField getPasswordPasswordField() {
		return PasswordPasswordField;
	}
	public JPasswordField getPasswordValidatePasswordField() {
		return PasswordValidatePasswordField;
	}
	public JTextField getUserNametextField() {
		return UserNametextField;
	}
	public JCheckBox getIsActiveCheckBox() {
		return IsActiveCheckBox;
	}
	public JLabel getMissedFieldsMessageLabel() {
		return MissedFieldsMessageLabel;
	}		
	public JLabel getUserNameExistMesaageLabel() {
		return UserNameExistMesaageLabel;
	}	
	
	/* Customer Details Center Layer */
	
	public JLayeredPane getCustomerDetailsLayer() {
		return CustomerDetailsLayer;
	}
	public JTextField getCustomerIDTextField(){
		return CustomerIDTextField;
	}
	public JTextField getFirstNameTextField(){
		return FirstNameTextField;
	}
	public JTextField getLastNameTextField(){
		return LastNameTextField;
	}
	public JTextField getEmailTextField(){
		return EmailTextField;
	}
	public JLabel getInvalidEmailMesaageLabel() {
		return InvalidEmailMesaageLabel;
	}	
	public JFormattedTextField getPhoneFormattedTextField(){
		return PhoneFormattedTextField;
	}
	public JFormattedTextField getCreditCardFormattedTextField(){
		return CreditCardFormattedTextField;
	}
	/**
	 * Get the index selected PurchasePlanComboBox
	 * @return index selected PurchasePlanComboBox
	 */
	public int getComboBoxSelection(){
		return PurchasePlanComboBox.getSelectedIndex();
	}
	/**
	 * Set String values of  Purchase Plans in PurchasePlanComboBox
	 * @param PurchasePlan String values of  Purchase Plans
	 */
	public void SetComboBoxSelection(String[] PurchasePlan){
		Object[]pattern=PurchasePlan;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		PurchasePlanComboBox.setModel(combopatternModel);	
	}	
	/**
	 * Get PurchasePlanComboBox
	 * @return PurchasePlanComboBox
	 */
	public JComboBox<?> getComboBox(){
		return PurchasePlanComboBox;
	}
	public int getCostingModelComboBoxSelection(){
		return CostingModelComboBox.getSelectedIndex();
	}
	/**
	 * Set String values of  Costing Models in CostingModelComboBox
	 * @param CostingModels String values of  Costing Models
	 */
	public void SetCostingModelComboBoxSelection(String[] CostingModels){
		Object[]pattern=CostingModels;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		CostingModelComboBox.setModel(combopatternModel);	
	}
	public JLabel getMissedFieldsMessage() {
		return MissedFieldsMessage;
	}
	public JLabel getInvalidPhoneMesaageLabel() {
		return InvalidPhoneMesaageLabel;
	}	
	public JLabel getInvalidCreditCardMesaageLabel() {
		return InvalidCreditCardMesaageLabel;
	}	
	public JRadioButton getPrivateCustomerRadioButton() {
		return PrivateCustomerRadioButton;
	}	
	public JRadioButton getCommercialCustomerRadioButton() {
		return CommercialCustomerRadioButton;
	}	
	public JLabel getEmailExistMesaageLabel() {
		return EmailExistMesaageLabel;
	}
	public JLabel getCustomerIDExistMesaageLabel() {
		return CustomerIDExistMesaageLabel;
	}
	public JLabel getInvalidCustomerIDMesaageLabel() {
		return InvalidCustomerIDMesaageLabel;
	}
	public JButton getCreateButton(){
		return CreateButton;
	}
	public JButton getSaveButton() {
		return SaveButton;
	}
	public JButton getEditButton() {
		return EditButton;
	}
	/**
	 * isCustomerChanged method compare between 2 objects of callbackCustomer and return false 
	 * if there is any change at those relevant fields, else return true.
	 * @param beforeChange callbackCustomer before user changes.
	 * @param afterChange callbackCustomer after user changes.
	 * @return return false if there is any change at those relevant fields, else return true.
	 */
	public boolean isCustomerChanged(callbackCustomer afterChange ){
		callbackCustomer beforeChange=fullCallbackCustomer;
		if ( !beforeChange.getUserName().equals(afterChange.getUserName() ) ) return false;
		if ( !beforeChange.getUserPassword().equals(afterChange.getUserPassword() ) ) return false;
		if ( !beforeChange.getISActive().equals(afterChange.getISActive() ) ) return false;
		if ( !beforeChange.getCustomerFirstName().equals(afterChange.getCustomerFirstName() ) ) return false;
		if ( !beforeChange.getCustomerLastName().equals(afterChange.getCustomerLastName() ) ) return false;
		if ( !beforeChange.getEmail().equals(afterChange.getEmail() ) ) return false;
		if ( !beforeChange.getPhoneNumber().equals(afterChange.getPhoneNumber() ) ) return false;
		if ( !beforeChange.getCreditCard().equals(afterChange.getCreditCard() ) ) return false;
		if ( !beforeChange.getCustomerType().equals(afterChange.getCustomerType() ) ) return false;
		if ( beforeChange.getPlanID() != afterChange.getPlanID()  ) return false;
		if ( beforeChange.getCostingModelID() != afterChange.getCostingModelID()  ) return false;
		return true;
	}
	/**
	 * Create new callbackCustomer object, fill it with GUI componenet's data and return full callbackCustomer object.
	 * @return return full callbackCustomer object.
	 */
	public callbackCustomer getFullCallbackCustomer(){
		callbackCustomer customerCallback = new callbackCustomer();
		
		customerCallback.setUserName(UserNametextField.getText().trim());
		customerCallback.setUserPassword(PasswordPasswordField.getText().trim());
		customerCallback.setCustomersID( Integer.valueOf(CustomerIDTextField.getText().trim()) );
		customerCallback.setCustomerFirstName(FirstNameTextField.getText().trim());
		customerCallback.setCustomerLastName(LastNameTextField.getText().trim());
		customerCallback.setEmail(EmailTextField.getText().trim());
		customerCallback.setPhoneNumber(PhoneFormattedTextField.getText().trim());
		customerCallback.setCreditCard(CreditCardFormattedTextField.getText().trim());
		if (PrivateCustomerRadioButton.isSelected()) customerCallback.setCustomerType("Private");
		else customerCallback.setCustomerType("Commercial");
		customerCallback.setPlanID(getComboBoxSelection()+1);
		customerCallback.setCostingModelID(getCostingModelComboBoxSelection()+1);
		
		//Update user data case
		if (!getNewCustomerFlag()) 
			if (getIsActiveCheckBox().isSelected()) customerCallback.setISActive("Yes"); 
			else customerCallback.setISActive("No"); 
	
		return customerCallback;
	}
	public callbackCustomer getCallbackCustomerUpdated() {
		return fullCallbackCustomer;
	}
	public void setFullCallbackCustomer(callbackCustomer fullCallbackCustomer) {
		this.fullCallbackCustomer = fullCallbackCustomer;
	}

	/* Common layers control */
	
	/**
	 * Hide relevant components - using for common layers.
	 */
	public void HideRelevantButtons(){
		if(newCustomerFlag)
		{
			IsActiveLabel.setVisible(false);
			IsActiveCheckBox.setVisible(false);
			CustomerIDTextField.setEnabled(true);
			CreateButton.setVisible(true);
			EditButton.setVisible(false);
			SaveButton.setVisible(false);
		}
		else
		{
			IsActiveLabel.setVisible(true);
			IsActiveCheckBox.setVisible(true);
			CustomerIDTextField.setEnabled(false);
			CreateButton.setVisible(false);
			EditButton.setVisible(true);
			SaveButton.setVisible(true);			
		}
		
	}
	/**
	 * Make relevant components to be enable/disable.
	 * In CreateUserLayer we want all components enable for get input of new customer.
	 * In AddPersonalDetails we want only watch customer details until press on edit button.
	 * @param res true / false : enable/disable the components.
	 */
	public void EnableRelevantButtons(boolean res){
			PasswordPasswordField.setEnabled(res);
			PasswordValidatePasswordField.setEnabled(res);
			UserNametextField.setEnabled(res);
			IsActiveCheckBox.setEnabled(res);
			FirstNameTextField.setEnabled(res);
			LastNameTextField.setEnabled(res);
			EmailTextField.setEnabled(res);
			PhoneFormattedTextField.setEnabled(res);
			CreditCardFormattedTextField.setEnabled(res);
			PrivateCustomerRadioButton.setEnabled(res);
			CommercialCustomerRadioButton.setEnabled(res);
			PurchasePlanComboBox.setEnabled(res);
			CostingModelComboBox.setEnabled(res);
	}
	public boolean getNewCustomerFlag() {
		return newCustomerFlag;
	}
	public void setNewCustomerFlag(boolean newCustomerFlag1) {
		newCustomerFlag = newCustomerFlag1;
	}

	/* AddCars */
	/**
	 * Build AddCars Center Layer and it's components.
	 */
	public void AddCarsCenterPanelLayer(){
		
		/* ------- Adding AddCar Center Layer -------- */

		AddCarPanel = new JLayeredPane();
		AddCarPanel.setLayout(null);
		AddCarPanel.setOpaque(true);
		AddCarPanel.setName("EmptyCenterPanel");
		AddCarPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(AddCarPanel, "AddCarLayerCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to AddCarLayer --------*/
		
		JLabel AddNewCarLabel = new JLabel("Add new car :");
		AddNewCarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		AddNewCarLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddNewCarLabel.setBounds(51, 0, 164, 34);
		AddCarPanel.add(AddNewCarLabel);
		
		CarNumberLabel = new JLabel("Car Number : ");
		CarNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CarNumberLabel.setEnabled(false);
		CarNumberLabel.setBounds(21, 32, 115, 34);
		AddCarPanel.add(CarNumberLabel);
		
		FuelIDLabel = new JLabel("Fuel ID : ");
		FuelIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FuelIDLabel.setEnabled(false);
		FuelIDLabel.setBounds(21, 67, 115, 34);
		AddCarPanel.add(FuelIDLabel);
		
		NFCLabel = new JLabel("NFC :");
		NFCLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		NFCLabel.setEnabled(false);
		NFCLabel.setBounds(21, 105, 115, 34);
		AddCarPanel.add(NFCLabel);
				
        try {
            //
            // Create a MaskFormatter for accepting car number, the # symbol accept
            // only a number. We can also set the empty value with a place holder
            // character.
            //
        	 CarNumberMask = new MaskFormatter("##-###-##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		CarNumberFormattedTextField = new JFormattedTextField(CarNumberMask);
		CarNumberFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CarNumberFormattedTextField.setToolTipText("Car number have to written with 7 digits. For example: 11-222-33");
		CarNumberFormattedTextField.setBounds(155, 36, 103, 27);
		AddCarPanel.add(CarNumberFormattedTextField);
		
		AddCarPanel.add(FuelIDComboBox);
		
		NFCNewCheckBox = new JCheckBox("");
		NFCNewCheckBox.setBounds(155, 116, 97, 23);
		AddCarPanel.add(NFCNewCheckBox);
		
		AddButton = new JButton("Add");
		AddButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddButton.setBounds(21, 150, 97, 38);
		AddCarPanel.add(AddButton);
		
		ExistCarNumberLabel = new JLabel("This car already in system");
		ExistCarNumberLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		ExistCarNumberLabel.setEnabled(false);
		ExistCarNumberLabel.setBounds(264, 26, 244, 46);
		ExistCarNumberLabel.setVisible(false);
		AddCarPanel.add(ExistCarNumberLabel);

	    MissedFieldMessageAddCarsLabel = new JLabel("There is one or more missed fields");
		MissedFieldMessageAddCarsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldMessageAddCarsLabel.setEnabled(false);
		MissedFieldMessageAddCarsLabel.setBounds(137, 147, 329, 46);
		MissedFieldMessageAddCarsLabel.setVisible(false);
		AddCarPanel.add(MissedFieldMessageAddCarsLabel);
		
		SuccessAddingCarMessageLabel = new JLabel("lol gadol");
		SuccessAddingCarMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		SuccessAddingCarMessageLabel.setEnabled(false);
		SuccessAddingCarMessageLabel.setBounds(10, 183, 532, 46);
		SuccessAddingCarMessageLabel.setVisible(false);
		AddCarPanel.add(SuccessAddingCarMessageLabel);
		
		
		
		/*------- Create JTable surround with scroll pane and add it to AddCarPanel --------*/
		JScrollPane CarsViewScroll = new JScrollPane();
		CarsViewScroll.setBounds(43, 264, 764, 287);
		AddCarPanel.add(CarsViewScroll);		
		
		CarsViewScroll.setViewportView(CarsViewTable);		
		CarsViewTable.setRowHeight(23);
		CarsViewTable.setFillsViewportHeight(true);
		CarsViewTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CarsViewTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		CarsViewTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		/*------- Create new label on the new table --------*/
		CarsViewLabel = new JLabel("Cars table of customer :");				
		CarsViewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CarsViewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CarsViewLabel.setBounds(241, 228, 329, 42);
		AddCarPanel.add(CarsViewLabel);
		
		/* ------- Adding new button to carsView layer -------- */
		CarsViewSaveButton = new JButton("Save");													//Global variable
		CarsViewSaveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CarsViewSaveButton.setBounds(839, 360, 112, 38);
		AddCarPanel.add(CarsViewSaveButton);
		
	}
	public int getFuelIDComboBoxSelection(){
		return FuelIDComboBox.getSelectedIndex();
	}
	/**
	 * Set String values of  Fuel ID in FuelIDComboBox
	 * @param FuelID String values of  Fuel names
	 */
	public void SetFuelIDComboBoxSelection(String[] FuelID){
		Object[]pattern=FuelID;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		FuelIDComboBox.setModel(combopatternModel);	
		FuelTypeComboBox.setModel(combopatternModel);	
	}		
	public JLabel getExistCarNumberLabel() {
		return ExistCarNumberLabel;
	}	
	public JLabel getMissedFieldMessageAddCarsLabel() {
		return MissedFieldMessageAddCarsLabel;
	}		
	public JFormattedTextField getCarNumberFormattedTextField() {
		return CarNumberFormattedTextField;
	}	
	public JButton getAddButton(){
		return AddButton;
	}			
	public JButton getCarsViewSaveButton(){
		return CarsViewSaveButton;
	}
	/**
	 * Create new callbackCar object, fill it with GUI componenet's data and return full callbackCar object.
	 * @return return full callbackCar object.
	 */
	public callbackCar getFullCallbackCar() {
		
		callbackCar carCallback = new callbackCar();
		
		carCallback.setCarNumber(CarNumberFormattedTextField.getText().trim());
		if (FuelIDComboBox.getSelectedIndex()!=2) carCallback.setFuelID(FuelIDComboBox.getSelectedIndex()+1);
		else carCallback.setFuelID(FuelIDComboBox.getSelectedIndex()+2);
		carCallback.setNFC(NFCNewCheckBox.isSelected());
		
		// Get CustomerID from the right field - depend which situation.
		if (!newCustomerFlag) carCallback.setCustomerID(Integer.parseInt(EnterCustomerIdTextField.getText().trim()));
		else carCallback.setCustomerID(Integer.parseInt(CustomerIDTextField.getText().trim()));

		return carCallback;		

	}
	public void setSuccessAddingCarMessageLabel(){
		SuccessAddingCarMessageLabel.setText("Car number " + CarNumberFormattedTextField.getText().trim() + " added successfully to system");
		SuccessAddingCarMessageLabel.setVisible(true);
	}
	public JLabel getSuccessAddingCarMessageLabel() {
		return SuccessAddingCarMessageLabel;
	}
	public JTable getCarsViewTable(){
		return CarsViewTable;
	}
	/**
	 * Get NewTableModel of customer's cars and set this data in CarsViewTable - JTable of cars by customer.
	 * @param NewTableModel cars by customer table model, came from DB.
	 */
	public void setCarsViewTable(BooleanTableModel NewTableModel){		
		CarsViewTable.setModel(NewTableModel);
		CarsViewTable.getModel().addTableModelListener(new ModelListener());
		CarsViewTable.getColumnModel().getColumn(3).setCellEditor(new CheckBoxCellEditor());
		CarsViewTable.getColumnModel().getColumn(5).setCellEditor(new CheckBoxCellEditor());
		//Hide columns
		CarsViewTable.removeColumn(CarsViewTable.getColumnModel().getColumn( 0 ));
		CarsViewTable.removeColumn(CarsViewTable.getColumnModel().getColumn( 1 ));
		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		CarsViewTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	/**
	 * ModelListener is a TableModelListener that let is listen to BooleanTableModel.
	 * We need to use this model for update NFC $IsActive field when checkBox pressed.
	 * @author Ohad Zino
	 */
	private class ModelListener implements TableModelListener{
		@Override
		public void tableChanged(TableModelEvent e) {
			 int row = e.getFirstRow();
		        int column = e.getColumn();
		        BooleanTableModel model = (BooleanTableModel)e.getSource();
		       // String columnName = model.getColumnName(column);
//		        Object data = model.getValueAt(row, column);
//		        model.setValueAt(data, row, column);
		        CarsViewTable.setModel(model);
//		        model.fireTableCellUpdated(row, column);	
		}
		
	}
	/**
	 * CheckBoxCellEditor class let us use checkBox as cell in JTable.
	 * @author Ohad Zino
	 */
	private class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		protected JCheckBox checkBox;
         
        public CheckBoxCellEditor() {
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setBackground( Color.white);
        }
         
        public Component getTableCellEditorComponent(
                JTable table, 
                Object value, 
                boolean isSelected, 
                int row, 
                int column) {
  
            checkBox.setSelected(((Boolean) value).booleanValue());
             
//            Component c = table.getDefaultRenderer(String.class).getTableCellRendererComponent(table, value, isSelected, false, row, column);
//            if (c != null) {
//                checkBox.setBackground(c.getBackground());
//            }
             
            return checkBox;
        }
        public Object getCellEditorValue() {
            return Boolean.valueOf(checkBox.isSelected());
        }
    }
	
	/* CampighnPattern */
	
	/**
	 * Build CampighnPattern Center Layer and it's components.
	 */
	public void CampighnPatternLayer(){
		
		/* ------- Adding Rates Report Center Layer -------- */

		CampighnPatternPanel = new JLayeredPane();
		CampighnPatternPanel.setLayout(null);
		CampighnPatternPanel.setOpaque(true);
		CampighnPatternPanel.setName("CampighnPatternCenterPanel");
		CampighnPatternPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(CampighnPatternPanel, "CampighnPatternLayerCenter");
		
		// PatternKind 
		PatternKindLabel = new JLabel("Pattern Kind By :");
		PatternKindLabel.setHorizontalAlignment(SwingConstants.LEFT);
		PatternKindLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PatternKindLabel.setBounds(10, 19, 200, 34);
		CampighnPatternPanel.add(PatternKindLabel);
		
		PatternKindComboBox = new JComboBox();
		PatternKindComboBox.setBounds(213, 22, 251, 31);
		PatternKindComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CampighnPatternPanel.add(PatternKindComboBox);
		Object[] PatternKindComboValue = {"Select Pattern Kind", "Fuel Amount", "Fuel Type", "Gas Station", "Hours", "Rate" };
		DefaultComboBoxModel<?> PatternKindComboModel = new DefaultComboBoxModel( PatternKindComboValue );
		PatternKindComboBox.setModel( PatternKindComboModel );
		
		// Rates 
		ChooseRateLabel = new JLabel("Choose Rate :");
		ChooseRateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ChooseRateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChooseRateLabel.setBounds(10, 64, 200, 34);
		CampighnPatternPanel.add(ChooseRateLabel);
		
		ChooseRateComboBox = new JComboBox();
		ChooseRateComboBox.setToolTipText("The pattern select the customers that have greater or equal rate than your selection.");
		ChooseRateComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ChooseRateComboBox.setBounds(213, 67, 68, 31);
		CampighnPatternPanel.add(ChooseRateComboBox);
		Object[] RatesValue = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		DefaultComboBoxModel<?> RatescomboModel = new DefaultComboBoxModel( RatesValue );
		ChooseRateComboBox.setModel( RatescomboModel );
		
		// Fuel Amount
		InsertFuelAmountLabel = new JLabel("Insert Fuel Amount :");
		InsertFuelAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		InsertFuelAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		InsertFuelAmountLabel.setBounds(10, 64, 200, 34);
		CampighnPatternPanel.add(InsertFuelAmountLabel);
		
		InsertFuelAmountTextField = new JTextField();
		InsertFuelAmountTextField.setToolTipText("The pattern select the customers that have greater or equal rate than your insertion. Please insert integer value.");
		InsertFuelAmountTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		InsertFuelAmountTextField.setColumns(10);
		InsertFuelAmountTextField.setBounds(213, 69, 90, 27);
		CampighnPatternPanel.add(InsertFuelAmountTextField);
		
		InvalidFuelAmountLabel = new JLabel("Invalid fuel amount inserted");
		InvalidFuelAmountLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidFuelAmountLabel.setEnabled(false);
		InvalidFuelAmountLabel.setBounds(424, 59, 278, 46);
		CampighnPatternPanel.add(InvalidFuelAmountLabel);
		
		// Fuel Type		
		ChooseFuelTypeLabel = new JLabel("Choose Fuel Type :");
		ChooseFuelTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ChooseFuelTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChooseFuelTypeLabel.setBounds(10, 64, 200, 34);
		CampighnPatternPanel.add(ChooseFuelTypeLabel);
	
		CampighnPatternPanel.add(FuelTypeComboBox);
		
		// Gas Station
		ChooseGasStationLabel = new JLabel("Choose Gas Station :");
		ChooseGasStationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ChooseGasStationLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChooseGasStationLabel.setBounds(10, 64, 211, 34);
		CampighnPatternPanel.add(ChooseGasStationLabel);
		
		CampighnPatternPanel.add(GasStationsComboBox);
		
		// Hours - time span
		ChooseTimeSpanLabel = new JLabel("Choose Time Span :");
		ChooseTimeSpanLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ChooseTimeSpanLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ChooseTimeSpanLabel.setBounds(10, 64, 200, 34);
		CampighnPatternPanel.add(ChooseTimeSpanLabel);
		
		TimeSpanToLabel = new JLabel("to");
		TimeSpanToLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TimeSpanToLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TimeSpanToLabel.setBounds(280, 61, 46, 34);
		CampighnPatternPanel.add(TimeSpanToLabel);
		
		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		FromHourSpinner = new JSpinner(sm);
		FromHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 17));
		JSpinner.DateEditor de = new JSpinner.DateEditor(FromHourSpinner,"HH:mm");
		FromHourSpinner.setEditor(de);
		FromHourSpinner.setBounds(213, 63, 68, 34);
		CampighnPatternPanel.add(FromHourSpinner);

		Date date2 = new Date();
		SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		ToHourSpinner = new JSpinner(sm2);
		ToHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 17));
		JSpinner.DateEditor de2 = new JSpinner.DateEditor(ToHourSpinner,"HH:mm");
		ToHourSpinner.setEditor(de2);
		ToHourSpinner.setBounds(326, 63, 68, 34);
		CampighnPatternPanel.add(ToHourSpinner);	
		
		// Discount 
		DiscountLabel = new JLabel("Discount :");
		DiscountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		DiscountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DiscountLabel.setBounds(10, 99, 200, 34);
		CampighnPatternPanel.add(DiscountLabel);
		
		DiscountSpinner = new JSpinner();
		DiscountSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		DiscountSpinner.setFont(new Font("Tahoma", Font.PLAIN, 17));
		DiscountSpinner.setBounds(213, 104, 68, 27);
		CampighnPatternPanel.add(DiscountSpinner);
		
		DiscountPercentLabel = new JLabel("%");
		DiscountPercentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DiscountPercentLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		DiscountPercentLabel.setBounds(280, 100, 37, 34);
		CampighnPatternPanel.add(DiscountPercentLabel);
		
		// Description 
		DescriptionLabel = new JLabel("Description :");
		DescriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		DescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DescriptionLabel.setBounds(10, 132, 200, 34);
		CampighnPatternPanel.add(DescriptionLabel);
		
		DescriptionTextArea = new JTextArea();
		DescriptionTextArea.setBackground(new Color(176, 196, 222));
		DescriptionTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), null));
		DescriptionTextArea.setLineWrap(true);
		DescriptionTextArea.setRows(8);
		DescriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 17));
		DescriptionTextArea.setBounds(213, 165, 747, 112);
		CampighnPatternPanel.add(DescriptionTextArea);
		
		EmptyDescriptionTextAreaMessage = new JLabel("<html>Empty Description Text Area.<br>Please enter description for this pattern.</html>", SwingConstants.CENTER);
		EmptyDescriptionTextAreaMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		EmptyDescriptionTextAreaMessage.setEnabled(false);
		EmptyDescriptionTextAreaMessage.setBounds(202, 288, 366, 63);
		CampighnPatternPanel.add(EmptyDescriptionTextAreaMessage);
		
		// CreatePattern Button 
		CreatePatternButton = new JButton("Create Pattern");
		CreatePatternButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreatePatternButton.setBounds(783, 288, 173, 38);
		CampighnPatternPanel.add(CreatePatternButton);
		
		// Messages labels
		PatternExistInDbMessageLabel = new JLabel("Pattern already exist in system");
		PatternExistInDbMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		PatternExistInDbMessageLabel.setEnabled(false);
		PatternExistInDbMessageLabel.setBounds(680, 325, 294, 46);
		CampighnPatternPanel.add(PatternExistInDbMessageLabel);
		
		SuccessAddPatternMessageLabel = new JLabel("New Pattern created Successfully");
		SuccessAddPatternMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		SuccessAddPatternMessageLabel.setEnabled(false);
		SuccessAddPatternMessageLabel.setBounds(644, 325, 316, 46);
		CampighnPatternPanel.add(SuccessAddPatternMessageLabel);
		
	}
	/**
	 * Hide all components of patterns kinds
	 */
	public void setNotVisibleAllPatternsKinds(){
		// Rates 
		ChooseRateLabel.setVisible(false);
		ChooseRateComboBox.setVisible(false);
		// Fuel Amount
		InsertFuelAmountLabel.setVisible(false);
		InsertFuelAmountTextField.setVisible(false);
		InvalidFuelAmountLabel.setVisible(false);
		// Fuel Type		
		ChooseFuelTypeLabel.setVisible(false);
		FuelTypeComboBox.setVisible(false);
		// Gas Station
		ChooseGasStationLabel.setVisible(false);
		GasStationsComboBox.setVisible(false);
		// Hours - time span
		ChooseTimeSpanLabel.setVisible(false);
		TimeSpanToLabel.setVisible(false);
		FromHourSpinner.setVisible(false);
		ToHourSpinner.setVisible(false);
		// Discount 
		DiscountLabel.setVisible(false);
		DiscountSpinner.setVisible(false);
		DiscountPercentLabel.setVisible(false);
		// Description 
		DescriptionTextArea.setEnabled(false);
		EmptyDescriptionTextAreaMessage.setVisible(false);
		// CreatePattern Button 
		CreatePatternButton.setEnabled(false);
		// Messages labels
		PatternExistInDbMessageLabel.setVisible(false);
		SuccessAddPatternMessageLabel.setVisible(false);
	}
	/**
	 * Show only components of relevant pattern kind
	 * @param choice - chosen pattern kind
	 */
	public void setVisibleByPattenKindComboBox(int choice) {
		
		DescriptionTextArea.setText("");
		setNotVisibleAllPatternsKinds();
		if (choice>0)
		{
			DiscountLabel.setVisible(true);
			DiscountSpinner.setVisible(true);
			DiscountSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
			DiscountPercentLabel.setVisible(true);
			DescriptionTextArea.setEnabled(true);
			CreatePatternButton.setEnabled(true);			
		}
		switch	(choice){
		
		case 1: // Fuel Amount
			InsertFuelAmountLabel.setVisible(true);
			InsertFuelAmountTextField.setVisible(true);
			break;
		case 2: // Fuel Type
			ChooseFuelTypeLabel.setVisible(true);
			FuelTypeComboBox.setVisible(true);
			break;
		case 3: //Gas Station
			ChooseGasStationLabel.setVisible(true);
			GasStationsComboBox.setVisible(true);
			break;
		case 4: //Hours
			ChooseTimeSpanLabel.setVisible(true);
			TimeSpanToLabel.setVisible(true);
			FromHourSpinner.setVisible(true);
			ToHourSpinner.setVisible(true);
			break;
		case 5: //Rate
			ChooseRateLabel.setVisible(true);
			ChooseRateComboBox.setVisible(true);
			break;											
	default:
		break;
	}
	}
	/**
	 * Set auto text completion by pattern kind - help us to avoid incorrect text in pattern description field.
	 * @param choice - chosen pattern kind
	 */
	public void setAutoTextToDescriptionTextArea(int choice) {
		
		switch	(choice){
		
		case 1: // Fuel Amount
			// set auto text only if fuel amount is valid
			if ( !InsertFuelAmountTextField.getText().equals("")  && Checks.isNumeric(InsertFuelAmountTextField.getText() )  )
				if (Integer.parseInt(InsertFuelAmountTextField.getText()) > 0)
					DescriptionTextArea.setText("Discount of " + String.valueOf((Integer)DiscountSpinner.getValue()) + "% on refueling over " + 
					InsertFuelAmountTextField.getText() + " liters.");
			break;
		case 2: // Fuel Type
			DescriptionTextArea.setText("Discount of " + String.valueOf((Integer)DiscountSpinner.getValue()) + "% on " + 
										(String)FuelTypeComboBox.getSelectedItem() );
			break;
		case 3: //Gas Station
			DescriptionTextArea.setText("Discount of " + String.valueOf((Integer)DiscountSpinner.getValue()) + "% on refueling in " + 
										(String)GasStationsComboBox.getSelectedItem() + " gas station.");
			break;
		case 4: //Hours
			String fromHour = new String();
			String fromMinute = new String();
			String toHour = new String();
			String toMinute = new String();
			if (Integer.parseInt(String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours()))>9)
				fromHour = String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours());
			else fromHour = "0" + String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours());
			
			if (Integer.parseInt(String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes()))>9)
				fromMinute = String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes());
			else fromMinute = "0" + String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes());
			
			if (Integer.parseInt(String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours()))>9)
				toHour = String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours());
			else toHour = "0" + String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours());
			
			if (Integer.parseInt(String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes()))>9)
				toMinute = String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes());
			else toMinute = "0" + String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes());
			
			DescriptionTextArea.setText("Discount of " + String.valueOf((Integer)DiscountSpinner.getValue()) + "% between " + 
											fromHour + ":" + fromMinute +" - " + toHour + ":" + toMinute  + ".");
			break;
		case 5: //Rate
			DescriptionTextArea.setText("Discount of " + String.valueOf((Integer)DiscountSpinner.getValue()) + "% for customers that have " + 
										"rating above " + (String)ChooseRateComboBox.getSelectedItem() + ".");
			break;											
	default:
		break;
	}
	}
	/**
	 * Show chosen exist pattern in layer's components.
	 * exist pattern is save in ExistPatternsCallbackArray in index "choice".
	 */
	public void displaySelectionExistPattern() {
		
		callbackCampaignPattern LocalCallback = ExistPatternsCallbackArray[ExistPatternsComboBox.getSelectedIndex()];
		PatternKindComboBox.setSelectedIndex(LocalCallback.getCampaignTypeIdClient());
		DescriptionTextArea.setText(LocalCallback.getCampaignDescription());
		int discount = (int)(LocalCallback.getDiscountPercentage()*100);
		DiscountSpinner.setModel(new SpinnerNumberModel(discount, 1, 100, 1));
	
		switch	(LocalCallback.getCampaignTypeIdClient()){
			case 1: // Fuel Amount
				InsertFuelAmountTextField.setText(String.valueOf(LocalCallback.getFuelAmount()));
				break;
			case 2: // Fuel Type
				if (LocalCallback.getFuelId()<3)
					FuelTypeComboBox.setSelectedIndex(LocalCallback.getFuelId()-1);
				else FuelTypeComboBox.setSelectedIndex(LocalCallback.getFuelId()-2);
				break;
			case 3: //Gas Station
				GasStationsComboBox.setSelectedIndex(LocalCallback.getGasStationId()-1);
				break;
			case 4: //Hours
				String fromString = new String(LocalCallback.getStartHour());
				String fromString2 = new String(LocalCallback.getStartHour());
				String toString = new String(LocalCallback.getEndHour());
				String toString2 = new String(LocalCallback.getEndHour());
	
				int fromHour = Integer.valueOf( fromString.substring(0, 2) );	
				int fromMinute = Integer.valueOf( fromString2.substring(3, 5) );	
				int toHour = Integer.valueOf( toString.substring(0, 2) );	
				int toMinute = Integer.valueOf( toString2.substring(3, 5) );	
	
				CampighnPatternPanel.remove(FromHourSpinner);
				Date fromDate = new Date();
				fromDate.setHours(fromHour);
				fromDate.setMinutes(fromMinute);
				FromHourSpinner= new JSpinner(new SpinnerDateModel(fromDate, null, null, Calendar.HOUR_OF_DAY));
				FromHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 17));
				JSpinner.DateEditor de = new JSpinner.DateEditor(FromHourSpinner,"HH:mm");
				FromHourSpinner.setEditor(de);
				FromHourSpinner.setBounds(213, 63, 68, 34);
				CampighnPatternPanel.add(FromHourSpinner);
				
				CampighnPatternPanel.remove(ToHourSpinner);
				Date toDate = new Date();
				toDate.setHours(toHour);
				toDate.setMinutes(toMinute);
				ToHourSpinner= new JSpinner(new SpinnerDateModel(toDate, null, null, Calendar.HOUR_OF_DAY));
				ToHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 17));
				JSpinner.DateEditor de2 = new JSpinner.DateEditor(ToHourSpinner,"HH:mm");
				ToHourSpinner.setEditor(de2);
				ToHourSpinner.setBounds(326, 63, 68, 34);
				CampighnPatternPanel.add(ToHourSpinner);
				break;
			case 5: //Rate
				ChooseRateComboBox.setSelectedIndex(LocalCallback.getCustomerRate()-1);
				break;
		default:
			break;
		}
				
	}
	public void setExistPatternsCallbackArray(callbackCampaignPattern[] existPatternsCallbackArray) {
		ExistPatternsCallbackArray = existPatternsCallbackArray;
	}
	public JComboBox<?> getExistPatternsComboBox(){
		return ExistPatternsComboBox;
	}
	/**
	 * Set String values of  Exist Patterns in ExistPatternsComboBox
	 * @param ExistPatterns String values of  Exist Patterns names
	 */
	public void SetExistPatternsSelection(String[] ExistPatterns){
		Object[]pattern=ExistPatterns;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		ExistPatternsComboBox.setModel(combopatternModel);	
	}
	/**
	 * convert pattern kind from client ID to server ID.
	 * @param CampaignTypeIdClient - pattern kind ID on client.
	 * @return pattern kind ID on server.
	 */
	public int convertCampaignTypeIdClientToServer(int CampaignTypeIdClient) {
		
		switch	(CampaignTypeIdClient){
		case 1: // Fuel Amount
			return 4;
		case 2: // Fuel Type
			return 3;
		case 3: //Gas Station
			return 5;
		case 4: //Hours
			return 1;
		case 5: //Rate
			return 6;
	default:
		return 0;
	}
	}
	public JLabel getPatternExistInDbMessageLabel() {
		return PatternExistInDbMessageLabel;
	}
	public JLabel getSuccessAddPatternMessageLabel() {
		return SuccessAddPatternMessageLabel;
	}
	public JTextArea getDescriptionTextArea() {
		return DescriptionTextArea;
	}
	public JLabel getEmptyDescriptionTextAreaMessage() {
		return EmptyDescriptionTextAreaMessage;
	}
	public int getGasStationsComboBoxSelection(){
		return GasStationsComboBox.getSelectedIndex();
	}
	/**
	 * Set String values of  Gas Stations in GasStationsComboBox
	 * @param GasStations String values of  Gas Stations names
	 */
	public void setGasStationsComboBox(String[] GasStations){
		Object[]pattern=GasStations;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		GasStationsComboBox.setModel(combopatternModel);	
	}	
	/**
	 * Create new callbackCampaignPattern object, fill it with GUI componenet's data and return full callbackCampaignPattern object.
	 * @return return full callbackCampaignPattern object.
	 */
	public callbackCampaignPattern getNewCampaignPatternCallback(){
		callbackCampaignPattern newCampaignPattern = new callbackCampaignPattern(MessageType.setNewCampignPattern);
		int PatternKindComboBoxIndex = PatternKindComboBox.getSelectedIndex();
		float discount;
		
		newCampaignPattern.setCampaignDescription(DescriptionTextArea.getText());
		newCampaignPattern.setCampaignTypeIdClient(PatternKindComboBoxIndex);
		newCampaignPattern.setCampaignTypeIdServer(convertCampaignTypeIdClientToServer(PatternKindComboBoxIndex));
		discount = Float.parseFloat(String.valueOf((Integer)DiscountSpinner.getValue()))/100;  
		newCampaignPattern.setDiscountPercentage(discount);
		
		switch	(PatternKindComboBoxIndex){
			case 1: // Fuel Amount
				newCampaignPattern.setFuelAmount( Integer.parseInt(InsertFuelAmountTextField.getText()) );
				break;
			case 2: // Fuel Type
				if (FuelTypeComboBox.getSelectedIndex()<2) newCampaignPattern.setFuelId(FuelTypeComboBox.getSelectedIndex()+1);
				else newCampaignPattern.setFuelId(FuelTypeComboBox.getSelectedIndex()+2);
				break;
			case 3: //Gas Station
				newCampaignPattern.setGasStationId(GasStationsComboBox.getSelectedIndex()+1);
				break;
			case 4: //Hours
				String fromHour = new String();
				String fromMinute = new String();
				String toHour = new String();
				String toMinute = new String();
				if (Integer.parseInt(String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours()))>9)
					fromHour = String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours());
				else fromHour = "0" + String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getHours());
				
				if (Integer.parseInt(String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes()))>9)
					fromMinute = String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes());
				else fromMinute = "0" + String.valueOf( ((Date)FromHourSpinner.getModel().getValue()).getMinutes());
				
				if (Integer.parseInt(String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours()))>9)
					toHour = String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours());
				else toHour = "0" + String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getHours());
				
				if (Integer.parseInt(String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes()))>9)
					toMinute = String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes());
				else toMinute = "0" + String.valueOf( ((Date)ToHourSpinner.getModel().getValue()).getMinutes());
				
				newCampaignPattern.setStartHour(fromHour+":"+fromMinute+":"+"00");
				newCampaignPattern.setEndHour(toHour+":"+toMinute+":"+"00");
				break;
			case 5: //Rate
				newCampaignPattern.setCustomerRate(ChooseRateComboBox.getSelectedIndex()+1);
				break;
		default:
			break;
		}
		return newCampaignPattern;
	}
	public JButton getCreatePatternButton() {
		return CreatePatternButton;
	}
	public JComboBox getPatternKindComboBox() {
		return PatternKindComboBox;
	}
	public JLabel getChooseRateLabel() {
		return ChooseRateLabel;
	}
	public JComboBox getChooseRateComboBox() {
		return ChooseRateComboBox;
	}
	public JLabel getInsertFuelAmountLabel() {
		return InsertFuelAmountLabel;
	}
	public JTextField getInsertFuelAmountTextField() {
		return InsertFuelAmountTextField;
	}
	public JLabel getInvalidFuelAmountLabel() {
		return InvalidFuelAmountLabel;
	}
	public JComboBox getFuelTypeComboBox() {
		return FuelTypeComboBox;
	}
	public JLabel getChooseGasStationLabel() {
		return ChooseGasStationLabel;
	}
	public JComboBox getGasStationsComboBox() {
		return GasStationsComboBox;
	}
	public JLabel getChooseTimeSpanLabel() {
		return ChooseTimeSpanLabel;
	}
	public JLabel getTimeSpanToLabel() {
		return TimeSpanToLabel;
	}
	public JSpinner getFromHourSpinner() {
		return FromHourSpinner;
	}
	public JSpinner getToHourSpinner() {
		return ToHourSpinner;
	}
	public JLabel getChooseFuelTypeLabel() {
		return ChooseFuelTypeLabel;
	}
	public JLabel getDiscountLabel() {
		return DiscountLabel;
	}
	public JSpinner getDiscountSpinner() {
		return DiscountSpinner;
	}
	public JLabel getDiscountPercentLabel() {
		return DiscountPercentLabel;
	}
	
	/* RatesReport */
	
	/**
	 * Build RatesReport Center Layer and it's components.
	 */
	public void RatesReportlLayer(){
		
		/* ------- Adding Rates Report Center Layer -------- */

		RatesReportPanel = new JLayeredPane();
		RatesReportPanel.setLayout(null);
		RatesReportPanel.setOpaque(true);
		RatesReportPanel.setName("RatesReportCenterPanel");
		RatesReportPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(RatesReportPanel, "RatesReportLayerCenter");
		
		InsertDateLabel = new JLabel("Insert Date:");
		InsertDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		InsertDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		InsertDateLabel.setEnabled(false);
		InsertDateLabel.setBounds(10, 11, 115, 34);
		RatesReportPanel.add(InsertDateLabel);
		
		GenerateDateChooser = new JDateChooser();
		GenerateDateChooser.setDateFormatString("yyyy/MM/dd");
		GenerateDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GenerateDateChooser.setBounds(126, 9, 140, 36);
		RatesReportPanel.add(GenerateDateChooser);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		GenerateDateChooser.getDateEditor().setDate(date);

		GenerateReportButton = new JButton("Generate");
		GenerateReportButton.setBounds(276, 7, 129, 38);
		RatesReportPanel.add(GenerateReportButton);
		GenerateReportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		/*------- Create new label on the new table --------*/
		RatesReportLabel = new JLabel("Customer rates for the last week :");				
		RatesReportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RatesReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		RatesReportLabel.setBounds(236, 49, 463, 42);
		RatesReportPanel.add(RatesReportLabel);
		
		RatesReportExportButton = new JButton("Export");													//Global variable
		RatesReportExportButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		RatesReportExportButton.setBounds(846, 38, 112, 38);
		RatesReportPanel.add(RatesReportExportButton);
		
		/*------- Create JTable surround with scroll pane and add it to AddCarPanel --------*/
		RatesReportScroll = new JScrollPane();
		RatesReportScroll.setBounds(12, 87, 964, 402);
		RatesReportPanel.add(RatesReportScroll);
		
		RatesReportScroll.setViewportView(RatesReportTable);
		RatesReportTable.setRowHeight(23);
		RatesReportTable.setFillsViewportHeight(true);
		RatesReportTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		RatesReportTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		RatesReportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		UpdateRateButton = new JButton("<html>Update</html>");
		UpdateRateButton.setBounds(846, 500, 97, 36);
		RatesReportPanel.add(UpdateRateButton);
		UpdateRateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		UpdateSuccessMessageLabel = new JLabel("<html>Customers rates<br>updated successfully</html>", SwingConstants.CENTER);
		UpdateSuccessMessageLabel.setBounds(605, 502, 231, 57);
		RatesReportPanel.add(UpdateSuccessMessageLabel);
		UpdateSuccessMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		UpdateSuccessMessageLabel.setEnabled(false);
		
		InvalidInsertedDateLabel = new JLabel("<html>Invalid inserted date</html>", SwingConstants.CENTER);
		InvalidInsertedDateLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidInsertedDateLabel.setForeground(Color.black);
		InvalidInsertedDateLabel.setBounds(401, 9, 216, 34);
		RatesReportPanel.add(InvalidInsertedDateLabel);
		UpdateRateButton.setVisible(false);
	}
	/**
	 * SetVisibleRelevantComponents(boolean res) -show/don't show relevant components in rate report center layer.
	 * @param res - boolean true/false
	 */
	public void SetVisibleRelevantComponents(boolean res){
		InvalidInsertedDateLabel.setVisible(false);
		RatesReportLabel.setVisible(res);
		RatesReportScroll.setVisible(res);
		RatesReportTable.setVisible(res);
		RatesReportExportButton.setVisible(res);
		UpdateRateButton.setVisible(res);
		UpdateSuccessMessageLabel.setVisible(false);
	}
	public void setRatesReportLabel() {
		RatesReportLabel.setText("Customer rates for week before " + 
				((JTextField)GenerateDateChooser.getDateEditor().getUiComponent()).getText() );
	}
	/**
	 * Get NewTableModel of RatesReport and set this data in RatesReportTable - JTable of RatesReport.
	 * @param NewTableModel RatesReport table model, came from DB.
	 */
	public void setRatesReportTable(DefaultTableModel NewTableModel){
		RatesReportTable.setModel(NewTableModel);
		//All values are in the center of the cell		
		DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
		CenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		RatesReportTable.setDefaultRenderer(Object.class, CenterRenderer);
	}
	public JLabel getInvalidInsertedDateLabel() {
		return InvalidInsertedDateLabel;
	}
	public JTable getRatesReportTable() {
		return RatesReportTable;
	}
	public JButton getRatesReportExportButton() {
		return RatesReportExportButton;
	}
	public JLabel getUpdateSuccessMessageLabel() {
		return UpdateSuccessMessageLabel;
	}
	public JButton getGenerateReportButton() {
		return GenerateReportButton;
	}
	public JButton getUpdateRateButton() {
		return UpdateRateButton;
	}

	public JDateChooser getGenerateDateChooser() {
		return GenerateDateChooser;
	}

}