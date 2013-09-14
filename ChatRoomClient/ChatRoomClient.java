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

	// Sign in window fields
	private	JFrame signInWindow = new JFrame("Sign In to Chat Room");
	private JButton    signIn = new JButton("Sign In");
	private JTextField username = new JTextField(16);
	private JLabel     usernameLabel = new JLabel("User Name: ");
	private JLabel     status = new JLabel("Status: Waiting for User Name");
	
	// Chat window fields
	private JFrame chatWindow = new JFrame("Messages");
	private JPanel chatPanel = new JPanel(new BorderLayout(5,5));
	private JTextArea chatTextArea = new JTextArea();
	private JTextField messageField = new JTextField();
	private JButton send = new JButton("Send");
	private JPanel enterPanel = new JPanel(new GridLayout(0,2,5,5));
	
	//Networking fields
	private Socket socket = null;
	private String serverIPAddress = "localhost";
	private int serverPort = 45000;
	private ObjectInputStream oInStream;
	private ObjectOutputStream oOutStream;
	
	
	//Constructor to initialize GUIs
	public ChatRoomClient(){
		//Initialize Sign in window
		signInWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15) );
		signInWindow.add( usernameLabel );
		signInWindow.add( username );
		signInWindow.add( signIn );
		signInWindow.add( status );
		
		//Display Sign in window
		signInWindow.setSize(250, 180);
		signInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signInWindow.setVisible(true);
		
		//Initialize Chat Window
		//chatPanel.setLayout(new GridLayout(0,2,5,5) );
		enterPanel.add( chatTextArea );
		enterPanel.add(  send );
		chatPanel.add( enterPanel, BorderLayout.NORTH );
		chatPanel.add( messageField, BorderLayout.CENTER );
		chatPanel.setBorder(BorderFactory.createEmptyBorder(5,10,10,10) );
		chatWindow.add( chatPanel );
		
		
		// Not display chat window
		chatWindow.setSize(500, 500);
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatWindow.setVisible(false);
		
		//Action Events to handle
		signIn.addActionListener(this);
		send.addActionListener(this);
	}
	
	
	//Connects to server and waits for messages from server
	class ConnectionWorker extends SwingWorker<Void,Void> {
		@Override
		protected Void doInBackground() throws Exception {
		
			//local fields
			Object r = null;
						
			//create socket, or report error
			try{
				socket = new Socket(serverIPAddress, serverPort);
			} catch (IOException ioe) {
				System.out.println("Could not connect to server @ " + serverIPAddress + " on port " + String.valueOf(serverPort));
				status.setText( "Could not connect to server @ " + serverIPAddress + " on port " + String.valueOf(serverPort) );
				signInWindow.setSize(500, 110);
				return null;
			}
			
			//create object streams (in/out) or report error
			try{
				oOutStream = new ObjectOutputStream(socket.getOutputStream());
				//write the username to server
				oOutStream.writeObject(username.getText().trim());
				
				oInStream = new ObjectInputStream(socket.getInputStream());
				//wait and get reply
				r = oInStream.readObject();
			} catch (IOException e) {
				//connection error
				System.out.println("Could not connect with object streams");
				status.setText( "Could not connect with object streams" );
				signInWindow.setSize(500, 110);
				System.out.println(e );
				return null;
			}
			
			//Check Server response for ACCEPT for name ok,
			//if not, then keep sign in window open, and alert
			// user of duplicate name
			final String r_string = r.toString();
			if( r_string != "ACCEPT" ){
				
				Runnable report_error = new Runnable(){
					public void run() {
						username.setEditable(true);
						status.setText( r_string );
						status.setBackground(Color.RED);
						
					}
				};
				SwingUtilities.invokeLater(report_error);
			}
			
			//with ACCEPT, Chat Window visible, Sign In invisible,
			else {
				Runnable openChat = new Runnable() {
					public void run(){
						chatTextArea.setText(""); //clear!
						signInWindow.setVisible(false);
						chatWindow.setVisible(true);
						username.setEditable(false);
					}
				};
				SwingUtilities.invokeLater(openChat);
			}
			
			
			return null;
		
		}
	};//end class ConnectionWorker
	
	
	
	//Handles GUIs events
	public void actionPerformed(ActionEvent e){
	
		if(e.getSource() == signIn ) {
			System.out.println("Sign In Button Clicked");
			new ConnectionWorker().execute(); 	//launch worker thread
		}
		else if(e.getSource() == send ) {
			System.out.println("Send Button Clicked");
		}
		else {
			System.out.println("Error, unidentified action event");
		}
		

		
	
	}//end actionPerformed
	

	
	//Program's setup and handles starting events
	public static void main (String[] args) {
	
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "ChatRoomClient ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
		// Set up Chat Client to run in Event Dispatch Thread
		Runnable task = new Runnable(){
			public void run() {
				new ChatRoomClient();
			}
		};
		//create Chat Client
		SwingUtilities.invokeLater(task);
		
		
		
		
	} //end main
		
		
		
		
}//end class ChatRoomClient
