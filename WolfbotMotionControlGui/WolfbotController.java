// A simple motion controll gui for wolfbots
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/26/13

import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;


public class WolfbotController implements ActionListener {

	/*
	// Sign in window fields
	private	JFrame signInWindow = new JFrame("Sign In to Chat Room");
	private JButton    signIn = new JButton("Sign In");
	private JTextField username = new JTextField(16);
	private JLabel     usernameLabel = new JLabel("User Name: ");
	private JLabel     status = new JLabel("Waiting for one word User Name");
	
	// Chat window fields
	private JFrame chatWindow = new JFrame("Messages");
	private JPanel chatPanel = new JPanel(new BorderLayout(5,5));
	private JTextArea chatTextArea = new JTextArea();
	private JTextField messageField = new JTextField();
	private JButton send = new JButton("Send");
	private JPanel enterPanel = new JPanel(new GridLayout(0,2,5,5));
	private JButton logOut = new JButton("Log Out");
	private JPanel bottomPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	private JScrollPane scrollPane = new JScrollPane(chatTextArea);
	
	//Networking fields
	private Socket socket = null;
	private String serverIPAddress = "localhost";
	private int serverPort = 45000;
	private ObjectInputStream oInStream;
	private ObjectOutputStream oOutStream;
	*/
	
	//Other
	private String newLine = System.getProperty ( "line.separator" );
	private static ImageIcon icon = null;
	
	
	//GUI Fields
	private	JFrame controllerGui = new JFrame("Wolfbot Controller");
	
	private JPanel directionPanel = new JPanel(new GridLayout(3,3));
	private JButton   leftButton = new JButton("Left");
	private JTextArea leftInc = new JTextArea("1");
	private JButton   rightButton = new JButton("Right");
	private JTextArea rightInc = new JTextArea("1");
	private JButton   forwardButton = new JButton("Forward");
	private JTextArea forwardInc = new JTextArea("1");
	private JButton   backButton = new JButton("Backward");
	private JTextArea backInc = new JTextArea("1");
	private JTextArea centerText = new JTextArea("");
	
	private JPanel rotatePanel = new JPanel(new GridLayout(2,2));
	private JButton   leftTurnButton = new JButton("Turn Left");
	private JTextArea leftTurnInc = new JTextArea("1");
	private JButton   rightTurnButton = new JButton("Turn Right");
	private JTextArea rightTurnInc = new JTextArea("1");
	
	private JPanel tiltPanel = new JPanel(new GridLayout(2,2));
	private JButton   tiltUpButton = new JButton("Tilt Up");
	private JTextArea tiltUpInc = new JTextArea("10");
	private JButton   tiltDownButton = new JButton("Tilt Down");
	private JTextArea tiltDownInc = new JTextArea("10");
	
	private JPanel guiPanel = new JPanel(new GridLayout(4,1));
	private JLabel wolbotID = new JLabel("Wolbot ID : unknown");
	
	
	
	
	/*------------------------------------------------------------------------*/
	
	//Constructor to initialize GUIs
	public WolfbotController(){
	
		//row1
	directionPanel.add(  forwardInc );
	directionPanel.add(   forwardButton );                    
	directionPanel.add( rightInc );
	//row2
	directionPanel.add(  leftButton );
	directionPanel.add(  centerText );
	directionPanel.add(   rightButton );
	//row3
	directionPanel.add(  leftInc );
	directionPanel.add(   backButton );
	directionPanel.add( backInc );
	
	
	//row4 & 5
	rotatePanel.add(  leftTurnButton );
	rotatePanel.add( leftTurnInc );
	rotatePanel.add(   rightTurnButton );
	rotatePanel.add( rightTurnInc );
	
	//row 6 & 7
	tiltPanel.add(  tiltUpButton );
	tiltPanel.add( tiltUpInc );
	tiltPanel.add(   tiltDownButton );
	tiltPanel.add( tiltDownInc );
	
	guiPanel.add( directionPanel );
	guiPanel.add( rotatePanel );
	guiPanel.add( tiltPanel );
	guiPanel.add( wolbotID );

	controllerGui.add( guiPanel );	
	
	//Display controller window
	//controllerGui.setIconImage(icon.getImage()); //set custom icon
	controllerGui.setSize(400, 500);
	controllerGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	controllerGui.setVisible(true);
		
	}//end WolbotController constructor
	
	//Program's setup and handles starting events
	public static void main (String[] args) {

		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		//String myname = "Neal O'Hara" + newLine + "ngohara";
		//String programName = "WolfbotController ";
		//System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
		// Set up Wolfbot Controller to run in Event Dispatch Thread
		Runnable task = new Runnable(){
			public void run() {
				new WolfbotController();
			}
		};
		//create Chat Client
		SwingUtilities.invokeLater(task);

	} //end main
	
	public void actionPerformed(ActionEvent e){
		//
		if( (e.getSource() == signIn) || (e.getSource() ==  username) ) {
		}
	}//end action performed
	
	
	
}//end class Wolfbot Controller