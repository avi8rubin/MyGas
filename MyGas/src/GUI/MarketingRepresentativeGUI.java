package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class MarketingRepresentativeGUI extends abstractPanel_GUI{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gui variables
	 */
	
	//top buttons
	private JButton CreateNewCustomerAccountButton;
	private JButton CustomerDetailsButton;
	private JButton CreateNewCampignPatternButton;
	private JButton ReportsButton;
	
	//CreateNewCustomerAccountLayer buttons
	private JLayeredPane CreateNewCustomerAccountLayer;
	private JButton CreateUserButton;
	private JButton AddCarDetailsButton;
	
	//CreateUserLayer buttons
	private JLabel UserNameLabel;
	private JLabel PasswordLabel;
	private JLabel PasswordValidateLabel;
	private JLabel UserNameExistMesaageLabel;
	private JLabel PasswordValidationFailedMessageLabel;
	private JTextField PasswordValidateTextField;
	private JTextField PasswordTextField;
	private JTextField UserNametextField;
	private JButton NextButton;

	public MarketingRepresentativeGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/* ------- Adding new button to Top Panel -------- */	
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
		ReportsButton = new JButton("Reports");											//Global variable
		ReportsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportsButton.setBounds(947, 101, 125, 38);
		TopPanel.add(ReportsButton);		
		
		
		/* ------- Adding new layer to CreateNewCustomerAccount Panel -------- */
		

		/* ------- Adding Left layer to CreateNewCustomerAccount Panel -------- */
		CreateNewCustomerAccountLayer = new JLayeredPane();
		CreateNewCustomerAccountLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateNewCustomerAccountLayer.setBackground(new Color(169, 169, 169));

		//add to left container
		LeftCardContainer.add(CreateNewCustomerAccountLayer, "CreateNewCustomerAccountLeft");
		CreateNewCustomerAccountLayer.setOpaque(true);
		CreateNewCustomerAccountLayer.setName("CreateNewCustomerAccountLeft");
		
		//buttons left layer
		CreateUserButton = new JButton("<html>Create User</html>");
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CreateUserButton.setBounds(27, 33, 212, 46);
		
		AddCarDetailsButton = new JButton("<html>Add Car Details</html>");
		AddCarDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		AddCarDetailsButton.setBounds(27, 99, 212, 46);
		
		CreateNewCustomerAccountLayer.add(CreateUserButton);
		CreateNewCustomerAccountLayer.add(AddCarDetailsButton);	
		
		/* ------- Adding CreateUser Center Layer -------- */
		JLayeredPane CreateUserLayer = new JLayeredPane();
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
		
		PasswordLabel = new JLabel("Password");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordLabel.setEnabled(false);
		PasswordLabel.setBounds(27, 104, 100, 34);
		CreateUserLayer.add(PasswordLabel);
	
		PasswordValidateLabel = new JLabel("Password Validate");
		PasswordValidateLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidateLabel.setEnabled(false);
		PasswordValidateLabel.setBounds(27, 151, 163, 46);
		CreateUserLayer.add(PasswordValidateLabel);

		// Message to marketing representative - display only in User name existance case
		UserNameExistMesaageLabel = new JLabel("User name already exist in system");
		UserNameExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		UserNameExistMesaageLabel.setEnabled(false);
		UserNameExistMesaageLabel.setBounds(403, 41, 309, 46);
		CreateUserLayer.add(UserNameExistMesaageLabel);		

		// Message to marketing representative - display only in Password Validation Failure case
		PasswordValidationFailedMessageLabel = new JLabel("Password Validation Failed");
		PasswordValidationFailedMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		PasswordValidationFailedMessageLabel.setEnabled(false);
		PasswordValidationFailedMessageLabel.setBounds(403, 122, 268, 46);
		CreateUserLayer.add(PasswordValidationFailedMessageLabel);	
		
		PasswordValidateTextField = new JTextField();
		PasswordValidateTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidateTextField.setBounds(179, 161, 201, 27);
		CreateUserLayer.add(PasswordValidateTextField);
		
		PasswordTextField = new JTextField();
		PasswordTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordTextField.setColumns(10);
		PasswordTextField.setBounds(179, 108, 201, 27);
		CreateUserLayer.add(PasswordTextField);
		
		UserNametextField = new JTextField();
		UserNametextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		UserNametextField.setColumns(10);
		UserNametextField.setBounds(179, 51, 201, 27);
		CreateUserLayer.add(UserNametextField);
		
		NextButton = new JButton("Next");
		NextButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NextButton.setBounds(607, 248, 125, 38);	
		CreateUserLayer.add(NextButton);
		
		// display changes till the controller will build- please don't delete (Adir)
		
		
//		CardLayout ContainerCardCenter;
//		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
//		ContainerCardCenter.show(CenterCardContainer,"CreateUserLayerCenter");
		
	//	CardLayout ContainerCardLeft;
	//	ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
	//	ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft");
		
		
		
		
		
	}

}
