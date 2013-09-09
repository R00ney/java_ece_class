// Lab 2 American Idol Server
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/7/13


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;


public class VoteForAmericanIdolClient extends JApplet implements ActionListener
{
	// class variables
	
	//can handle arbritrary up to 99 of contestants
	JRadioButton[] contestant_i = new JRadioButton[99];
	ButtonGroup contestants = new ButtonGroup();
	JButton submitButton = new JButton("Submit");
	JLabel msgLabel = new JLabel("Select a contestant and press Submit");
	
	//Create layout and panel for applet
	GridLayout voteApp = new GridLayout();
	JPanel panel = new JPanel();
	
	int numberOfContestants = 0;
	
	int port = 2222;
	
	
	/*------------------------------------------------------------------------*/
	//builds GUI
	public void init() { //the main for web apps
		
		//Set GUI parameters from html tags
		// This expects all contestants to be listed in parms,
		// starting with name1 , to name#, where # is last contestant 
		// (# must be less than 100)
		Color rand_color = null;
		Color prev_rand_color = null;
		for(int x=1; true; x++){	//will break at # limit
		
			//initialize jradiobuttons
			contestant_i[x-1] = new JRadioButton();
			
			//set text for buttons
			contestant_i[x-1].setText(getParameter(new String("name"+x)));
			
			//determine number of buttons
			if(contestant_i[x-1].getText() == null){
				
				if(x==1){ // if no contestants, Sorry and disable submit
					msgLabel.setText("Sorry, there are no contestants to vote for now.");
					 submitButton.setEnabled(false);// disable button!
				}
				numberOfContestants = x-1;
				break; //leave loop b/c no more contestants
			}
			
			//randomize the button colors
			rand_color = this.newRandColor(prev_rand_color);
			contestant_i[x-1].setBackground( rand_color );
			contestant_i[x-1].setForeground(this.getContrastTextColor(rand_color));
			contestant_i[x-1].setOpaque(true);
			prev_rand_color = rand_color;
			

		}//end for(true) break
		
		
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		System.out.println(myname + newLine + "VoteForAmericanIdolsServer Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
			// Build the GUI
		voteApp = new GridLayout(numberOfContestants+2,1,1,2); //stack all buttons and text
		panel = new JPanel(voteApp);
		panel.add(msgLabel);
		for(int i=1; i <= numberOfContestants; i++){
			panel.add(contestant_i[i-1]);
		}
		panel.add(submitButton);
		
		add(panel); // call JApplet to add to browser's window

		for(int i=1; i <= numberOfContestants; i++){
			contestants.add(contestant_i[i-1]);
		}

		
		// test port value is non-null and pos-ints and connect
		String pnum = getParameter("port");
		Boolean test = (( pnum!= null) & (pnum.matches("\\d+(\\.\\d+)?") ));
		if( test ){
			port = Integer.parseInt( pnum ); //get port# from html
			System.out.println("Debug, using port :"+pnum);
		} else {
		
			msgLabel.setText("Sorry, unable to connect to Server. Please Try again later. ");
			System.out.println("Debug fail, port :"+pnum);
			submitButton.setEnabled(false);// disable button!
		}	


		
		//Set up actionlistener for required event
		submitButton.addActionListener(this); //get submnit button clicked

	} //end init()
	
	
	
	/*------------------------------------------------------------------------*/	
	//Code that services GUI here
	public void actionPerformed(ActionEvent ae) {
	
		//System.out.println("Debug: Submit Pushed");
		
		String vote = null;
		for(int i=1; i <= numberOfContestants; i++){
			//identify contestant voted for
			if(contestant_i[i-1].isSelected()) vote = String.valueOf(i);
		}
		if( vote == null) { // no radio button was selected...
				msgLabel.setText("Select a contestant before SUBMIT");
				msgLabel.setForeground(Color.red);
				return; 
				}       
		System.out.println("Contestant #" + vote + " was selected.");
	
		
				// Get IP address
		String serverAddress = "localhost"; // default address
		URL webServerAddress = getDocumentBase(); // get from browser
		
		//pick appropriate url/ip
		if ((webServerAddress == null) || (webServerAddress.toString().startsWith("file:"))) {
			// We're running locally on the AppletViewer. Do nothing.
		} else {
			// We're running on a web server.
			serverAddress = webServerAddress.getHost();
		}
		
		// create socket
		System.out.println("Server address is " + serverAddress); 
		InetSocketAddress isa = new InetSocketAddress(serverAddress,port);
		byte[] sendBuffer = vote.getBytes();
		
		
		//Send packet with vote
		try {
			DatagramPacket sendPacket = new    DatagramPacket(sendBuffer,sendBuffer.length,isa);
			DatagramSocket sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
			msgLabel.setText("Your vote has been submitted!");
			msgLabel.setForeground(Color.blue);
		}
		catch(IOException ioe)
		{
			msgLabel.setText("Error sending your vote! Please try again.");  
			msgLabel.setForeground(Color.red);
			return;
		}
		

		// Disable GUI objects so user
		 // doesn't try to reselect or send again.
		 submitButton.setEnabled(false);// disable button!
		for(int i=1; i <= numberOfContestants; i++){
			(contestant_i[i-1]).setEnabled(false); // disable button!
		}
		
//		 msgLabel.setText(" "); //clear
		 
	} //end actionPerformed
	
	
	/*------------------------------------------------------------------------*/
	// pick black or white to contrast with background color
	public static Color getContrastTextColor(Color color){
		double y = (299*color.getRed() + 587*color.getGreen() + 114*color.getBlue())/1000;
		return (y >= 128) ? Color.black : Color.white;
	}

	
	/*------------------------------------------------------------------------*/
	// Generates a new random color, and makes sure it's not in the
	// same color majority as prev color
	public static Color newRandColor(Color prev_color){
		Random rand = new Random();	
		Color rand_color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		Boolean test1 = true;
		Boolean test2 = true;
		Boolean test3 = true;
		int diff = 100;
		
		//if null, this is first instance, return color immediately
		if( prev_color!=null ){

			//if rand_color too close to prev_color, re-randomize
			while( test1 & test2 & test3 ){
				rand_color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
			
				test1 = ( ((rand_color.getRed()+diff)>prev_color.getRed()) & ((rand_color.getRed()-diff)<prev_color.getRed()));	
				test2 = ( ((rand_color.getGreen()+diff)>prev_color.getGreen()) & ((rand_color.getGreen()-diff)<prev_color.getGreen()));
				test3 = ( ((rand_color.getBlue()+diff)>prev_color.getBlue()) & ((rand_color.getBlue()-diff)<prev_color.getBlue()));
			}
			
		}
		return rand_color;
	}
	
	
	
	
//No Main, because browser starts code

} // end class Vote...Client
