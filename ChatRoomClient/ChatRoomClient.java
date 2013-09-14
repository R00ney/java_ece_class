// Lab 3 Chat Room Client
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/11/13

import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class ChatRoomClient implements ActionListener {


	private	JFrame signInWindow = new JFrame("Sign in to chat room");
	private JButton    signIn = new JButton("Sign in");
	private JTextField username = new JTextField(16);
	private JLabel     usernameLabel = new JLabel("User name: ");
	
	public ChatRoomClient(){
		//Initialize Sign in window
		signInWindow.setLayout(new FlowLayout());
		signInWindow.add( usernameLabel );
		signInWindow.add( username );
		signInWindow.add( signIn );
		
		//Display Sign in window
		signInWindow.setSize(200, 150);
		signInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signInWindow.setVisible(true);
	}
	

	public void actionPerformed(ActionEvent e){
	
	
	
	}
	

	
	public static void main (String[] args) {
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "ChatRoomClient ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
		ChatRoomClient test = new ChatRoomClient();
		
	} //end main
		
		
		
		
}
