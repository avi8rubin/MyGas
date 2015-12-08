package GUI;

import java.awt.Font;

import javax.swing.JButton;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class CEOGUI extends abstractPanel_GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton TariffApprovalButton = new JButton("Tariff Approval");
	
	public CEOGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		
		TariffApprovalButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TariffApprovalButton.setBounds(24, 128, 198, 47);		
		TopPanel.add(TariffApprovalButton);
		CenterPanel.setVisible(false);
		
	}
	
	public JButton getTariffApprovalButton(){
		return TariffApprovalButton;
	}
}
