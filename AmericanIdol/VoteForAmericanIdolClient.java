// Lab 2 American Idol Server
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/7/13

//import javax.servlet.*;
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
	JPanel panel = new JPanel();
	int numberOfContestants = 0;
	Random rand = new Random();
	int port = 2222;
	
	
	
	//builds GUI
	public void init() { //the main for web apps
		
		//Set GUI parameters from html tags
		for(int x=1; true; x++){	//will break at # limit
		
			//initialize jradiobuttons
			contestant_i[x-1] = new JRadioButton();
			
			//set text for buttons
			contestant_i[x-1].setText(getParameter(new String("name"+x)));
			
			//determine number of buttons
			if(contestant_i[x-1].getText() == null){
				numberOfContestants = x-1;
				break;
			}
			
			//randomize the button colors
			int rand_color = rand.nextInt(16777216); //generate a random color
			contestant_i[x-1].setBackground(new Color(rand_color));
			contestant_i[x-1].setForeground(this.getContrastTextColor(new Color(rand_color)));
			contestant_i[x-1].setOpaque(true);
			
			//Stack and center buttons
			//contestant_i[x-1].setAlignmentX(Component.CENTER_ALIGNMENT);

		}//end for(true) break
		
		
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		System.out.println(myname + newLine + "VoteForAmericanIdolsServer Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
			// Build the GUI
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
	
	
	
	
	//Code that services GUI here
	public void actionPerformed(ActionEvent ae) {
	
		System.out.println("Debug: Submit Pushed");
		
		String vote = null;
		for(int i=1; i <= numberOfContestants; i++){
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
		
		if ((webServerAddress == null) || (webServerAddress.toString().startsWith("file:"))) {
			// We're running locally on the AppletViewer. Do nothing.
		} else {
			// We're running on a web server.
			serverAddress = webServerAddress.getHost();
		}
		
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
	
	
	// pick black or white to contrast with background color
	public static Color getContrastTextColor(Color color){
		double y = (299*color.getRed() + 587*color.getGreen() + 114*color.getBlue())/1000;
		return (y >= 128) ? Color.black : Color.white;
	}

//No Main, because browser starts code

} // end class Vote...Client
