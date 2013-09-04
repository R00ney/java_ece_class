
/**
 *  @author jtuck
 */

// Modified by Neal O'Hara
// ngohara
// ngohara@ncsu.edu
// 8/28/13


import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.Date;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;

//import Logger;


public class TechSupportServer {	
	
	public static void main(String[] args) {
		

		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara" + newLine;
		int drop_conn_count = 0;
		ServerSocket ss = null;
		Socket socket = null;
		DataOutputStream server_dos = null;
		DataInputStream server_dis = null;
		
		
		Boolean first_time = true;
		Boolean bye_occured = false;
		
		// An array of responses that our excellent tech support will provide
		String[] responses = {
				"Reboot your computer.",
				"Try banging on it a little.",
				"Just wait. It will fix itself.",
				"You may want to re-install the OS.",
				"Try it on a different computer.",
				"Make sure your computer is turned on. That will help.",
				"Your computer is just too old. Buy a new one."
		};
						
					//  ^ Most Objects ^
		/**************************************************************************************/
					//   Initialize Server Code
		
		
		//Note, Logger and .PrintnLog(), .Log(), and .ClientPrintnLog() are defined in Logger class
		// They simply handel output and file logging simply and simultaneously
		Logger output = new Logger("SERVER_TechSupportLog.txt",true);  
		
		
		//Start new session in log file
		output.PrintnLog(myname + newLine + "TechSupportServer Program");
		output.PrintnLog("This session started " + new Date());
			
		
		// Set up an object to read in a question from the user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		
		//Setup SocketServer and IP Address
		Integer port = 1919; //Default
		if (args.length > 0) {
			
			port = Integer.valueOf(args[0]);		//overides default port #
		}
		
		try{ 
			ss = new ServerSocket(port);// specify port #
						
			output.PrintnLog("TechSupportServer is up at "
				+ InetAddress.getLocalHost().getHostAddress()
				+" on port " 
				+ ss.getLocalPort());
			
			
		} catch (Exception e) {
			output.PrintnLog("The Server Socket failed: " + newLine + e.toString());
		}
		
				
		/**************************************************************************************/
					//   Server Responses Code
		
		
		
		try {
			while(true) {
				
				//Make and Report Client connections or connection issues
				try {
					socket = ss.accept(); // wait for a client to connect
					
					if ( first_time ) {
						output.PrintnLog("Connection made from a calling client!");
					}
				} catch (Exception ioe) {
					drop_conn_count++; 	//keep track of total connections lost
					output.PrintnLog("Connection problem with a client: " + ioe + newLine + "Total Connections lost = " + String.valueOf(drop_conn_count));
				}
				
				//open data input on
				server_dis = new DataInputStream(socket.getInputStream());

				String question = server_dis.readUTF();  // exception could happen here
				
				
				String test = "This is a test connection";
				if (question.equalsIgnoreCase( test )) {
					// Don't respond or worry about this test connections
					//clean up current connections
	
					
					try {
						first_time = false;  //disable first time message
						//server_dis.close();
					
						
						// Startup Helpfull Message
						String greet1 = "Server:  Hello! I'm available to answer your questions.";
						String greet2 = "         If at any time you no longer my help, just type";
						String greet3 = "        'Bye' to leave.";
					
						DataOutputStream temp_dos = new DataOutputStream(socket.getOutputStream());
	
						temp_dos.writeUTF( greet1 );
						temp_dos.writeUTF( greet2 );
						temp_dos.writeUTF( greet3 );
	
						output.PrintnLog(greet1 + newLine + greet2 + newLine + greet3);
						
						socket.close(); // close so safe future re-open
					} catch (Exception close_error) { 
						output.PrintnLog("Greeting or socke.close failed: " + newLine + close_error.toString());
					}
				} else {		
				
					//show and log cleint question
					output.ClientPrintnLog("Client: " + question + "          " );
					
					
					// if the line has no characters...
					if (question.length() > 0) {
						
						
						// check if string matches bye
						if (question.equalsIgnoreCase("bye")){
							bye_occured = true;
							first_time = true;
							
							//Server Exits
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF(" Bye-bye.");
							output.PrintnLog("Server: Bye-bye."  + newLine + "Session Ended:  " + new Date());
							
							//no break, server keeps running
							
						}
						else {	//Answer normally
													
							// Get an answer randomly from the responses array
							String answer = responses[(int)(Math.random()*responses.length)];
		
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF(answer);
							output.PrintnLog("Server: " + answer);
							
							
							//close data_out_stream
							try {
								server_dos.close();
							} catch (Exception close_error) { 
								output.PrintnLog(close_error.toString());
							}
						}
					
					} else { //blank question
						
						if(bye_occured==false){
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF("Server: You there?");
							output.PrintnLog("Server: You there?");
							
						} else { bye_occured = false; }
						
					}//End if lenght >0
					
					//clean up current connections
					try {
						server_dis.close();
						socket.close();
						
					} catch (Exception close_error) { 
						output.PrintnLog(close_error.toString());
					}
					
				} //end if first time else
			}//End while(true)
			
		} catch (Exception e) {
			//Server Error
			try{
				server_dos = new DataOutputStream(socket.getOutputStream());
				server_dos.writeUTF("Server: I can't help you!");
			} catch (Exception open_error) {
				output.PrintnLog("Open dos error: " + newLine + open_error.toString());	
			}
			
			output.PrintnLog("Server: I can't help you!");
			
			
			//clean up current connections
			try {
				server_dis.close();
				server_dos.close();
				socket.close();
			} catch (Exception close_error) { 
				//Do nothing, errors will be null references to already closed objects
			}
		}	    
		

		
		//Close file io's
		// Actually, all file io auto close in Logger class
	}
	
}
