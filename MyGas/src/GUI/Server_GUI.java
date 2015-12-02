package GUI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Scrollable;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import controller.*;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
public class Server_GUI {

	public JFrame frame;
	public Scrollable scrollBar;
	public JTextField textURL;
	public JTextField txtUser;
	public JTextField textPassword;
	public JTextField textPort;
	public ServerController Control;
	public JTextArea textPane = new JTextArea();
	public int Word_Counter=0;
	public JButton btnStart;
	public JButton btnStop; 
	private JLabel lblNewLabel_2;
	private JScrollPane Constol;
	private JScrollPane scrollPane;
public Server_GUI(){
	initialize();
}
	
private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(240, 240, 240));
		frame.setBounds(350, 300, 750, 434);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Url:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(12, 13, 28, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUser.setBounds(253, 13, 40, 16);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(388, 13, 78, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPort.setBounds(579, 13, 46, 16);
		frame.getContentPane().add(lblPort);
		
		txtUser = new JTextField();
		txtUser.setText("root");
		txtUser.setColumns(10);
		txtUser.setBounds(301, 11, 84, 22);
		frame.getContentPane().add(txtUser);
		
		textURL = new JTextField();
		textURL.setText("jdbc:mysql://localhost/mygas");
		textURL.setBounds(52, 11, 189, 22);
		frame.getContentPane().add(textURL);
		textURL.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 76, 720, 296);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textPane);
		
		textPassword = new JTextField();
		textPassword.setText("1234");
		textPassword.setColumns(10);
		textPassword.setBounds(476, 11, 84, 22);
		frame.getContentPane().add(textPassword);
		
		textPort = new JTextField();
		textPort.setText("5555");
		textPort.setColumns(10);
		textPort.setBounds(626, 11, 84, 22);
		frame.getContentPane().add(textPort);
		
		
		btnStart = new JButton("Start");

		btnStart.setBounds(12, 46, 97, 25);
		frame.getContentPane().add(btnStart);
		
		 btnStop = new JButton("Stop");

		btnStop.setBounds(589, 46, 97, 25);
		frame.getContentPane().add(btnStop);
		
		JLabel lblNewLabel_1 = null;
		try {
			lblNewLabel_2 = new JLabel("My IP: "+Inet4Address.getLocalHost().getHostAddress());
			lblNewLabel_2.setFont(new Font("David", Font.PLAIN, 17));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lblNewLabel_2.setBounds(263, 47, 181, 24);
		frame.getContentPane().add(lblNewLabel_2);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(12, 76, 720, 296);
		//frame.getContentPane().add(scrollBar);
		//frame.getContentPane().add(new JScrollPane(textPane));
	}

public int GetPort(){
	return Integer.parseInt(textPort.getText()); 
}
public String GetUser(){
	return txtUser.getText();
}
public String GetPassword(){
	return textPassword.getText();
}
public String GetURL(){
	return textURL.getText();
}
public void setToPane(String str){
	try {
		//str=str+"\n";
		Word_Counter+=str.length();
		textPane.getDocument().insertString(Word_Counter, str, null);
	} catch (BadLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}