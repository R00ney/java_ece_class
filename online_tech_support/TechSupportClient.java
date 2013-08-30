/**	original code by
 *  @author jtuck
 *	
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
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.net.InetSocketAddress;



public class TechSupportClient {
	public static void main(String[] args) {
		
		
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara" + newLine;
		
		String IPaddress = "localhost";
		int port = 1919;
		
		Socket socket = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
				
		
		if (args.length == 0)
		{
		   System.out.println("Restart. Provide the server computer address "
				    + "in dotted-numeric form (or 'localhost', without the quotes) "
				    + "as a single command line parameter.");
		   return; // terminate so user can restart.
		}
		
		
		//Use passed IP address, port number if given
		else if (args.length > 1) {
			IPaddress = args[0];	//uses mandatory IP address
			port = Integer.valueOf(args[1]);    //overides default port #
			
		} else if (args.length==1) {
			IPaddress = args[0];
		}

		
		//Note, Logger and .PrintnLog() are defined in Logger class
		// They simply handel output and file logging simply and simultaneously
		Logger output = new Logger("TechSupportLog.txt",true);  
		
		
		//Begin Screen and Log
		output.PrintnLog(myname + "TechSupportClient Program");
		output.PrintnLog("This session started " + new Date());
		
		// Set up an object to read in a question from the user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		

		//Test the Server's IP, Port, and Server Socket Connection
		try {
			socket = new Socket(); // connect to server at IPaddress & port
			socket.connect(new InetSocketAddress(IPaddress,port),1000); //create socket timeout errors for bad IP
			output.PrintnLog("Connected to the server at " + args[0] );							
			
			String test = "This is a test connection";
			
			// make a DataOutputStream to format data over the Socket (network stream) 
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF( test ); // this could cause an exception	
			
			//log the question, needs an explicit newline
			output.Log(test + newLine);
			
			//dos.close();
			
			
			//Get Server's Greetings
			dis = new DataInputStream(socket.getInputStream());
	
			String greet1 = dis.readUTF(); // block/stall until the server replies
			String greet2 = dis.readUTF(); // block/stall until the server replies
			String greet3 = dis.readUTF(); // block/stall until the server replies
			output.PrintnLog(greet1 + newLine + greet2 + newLine + greet3);
		
			dis.close();
			socket.close();		//Must close, so later questions sockets work
		
		}
		catch(IOException ioe)
		{
		
			output.PrintnLog("ERROR: Attempt to connect to the TechSupportServer at "
				     +  args[0] + " has failed.");
			output.PrintnLog("This network address may be incorrect for the server,");
			output.PrintnLog("or the server may not be up. The specific failure is:");
			output.PrintnLog( ioe.toString() );
			
					
			
			try{
			dos.close();
			dis.close();
			socket.close();
			} catch (Exception e) {
				//Do nothing, excepstions are just "null pointers" 
				//for closing something that doesn't exist 
			}
			
			return;
			
		}//End Socket Creation
				    
		
				
		
		try {
			while(true) {
						
				// terminal input code
				String line = reader.readLine().trim();
								
				try {
					// This could cause an exception
					socket = new Socket(IPaddress,port); // connect to server at IPaddress & port
		
					// make a DataOutputStream to format data over the Socket (network stream) 
					dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(line); // this could cause an exception	
					
					//log the question
					output.Log("Client: " + line  + "            ");
					
										
					dis = new DataInputStream(socket.getInputStream());
					
					
					String reply = dis.readUTF(); // block/stall until the server replies
					
					output.PrintnLog("Server : "+reply);
					

					
					dos.close(); // hang up on the server
					socket.close();
					
					if( reply.equalsIgnoreCase(" Bye-bye."))  //Client then exits
						break;
					
				} catch (Exception e) {
					output.PrintnLog("Something went wrong! Are you running the server?");
					output.PrintnLog(e.toString() );
				}
				
			} //end while true loop
			
		} catch (Exception e) {
			//Server Error
			output.PrintnLog("Client failed to query or connect");
			output.PrintnLog(e.toString() );
			
		}	    
		
		//End and Date screen and log
		output.PrintnLog( "Session Ended:  " + new Date() );
	
		
		//Close file io's
		// Actually, all file io auto close in Logger class
	}
}


