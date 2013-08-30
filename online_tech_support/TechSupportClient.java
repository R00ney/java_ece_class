/**	original code by
 *  @author jtuck
 *	
 */

// Modified by Neal O'Hara
// ngohara
// 8/28/13


import java.io.*;
//import java.io.DataOutputStream;
//import java.io.DataInputStream;
import java.net.Socket;
import java.util.Date;



public class TechSupportClient {
	public static void main(String[] args) {
		
		FileWriter fw = null;
		BufferedWriter log_writer = null;
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara" + newLine;
		
		String IPaddress = "localhost";
		int port = 1919;
		
		Socket socket = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		
		String test = "This is a test connection";
		
		
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
			port = Integer.valueOf(args[1]);		//overides default port #
			
		} else if (args.length==1) {
			IPaddress = args[0];
		}

		try {
			//Create a log of interactions
			fw = new FileWriter("TechSupportLog.txt",true); 
			//Open DataOutStream for file write
			log_writer = new BufferedWriter( fw );
			
			//Start new session in log file
			System.out.println(myname + newLine + "TechSupportClient Program");
			log_writer.write(newLine + myname);
			log_writer.write("This session started " + new Date() + newLine);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		

		
		// Set up an object to read in a question from the user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		

		//Test the Server's IP, Port, and Server Socket Connection
		try {
			socket = new Socket(IPaddress,port); // connect to server at IPaddress & port
			System.out.println("Connected to the server at " + args[0]);
			
			try {
				log_writer.write("Connected to the server at " + args[0]  + newLine);							
			} catch (Exception e_line) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e_line);
			}
			
			// make a DataOutputStream to format data over the Socket (network stream) 
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF( test ); // this could cause an exception	
			System.out.println(test);
			//log the question
			try {
				log_writer.write(test + newLine);
			} catch (Exception e) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e);
			}
			dos.close();
			socket.close();		//Must close, so later questions sockets work
		}
		catch(IOException ioe)
		{
				System.out.println("ERROR: Attempt to connect to the TechSupportServer at "
					     +  args[0] + " has failed.");
				System.out.println("This network address may be incorrect for the server,");
				System.out.println("or the server may not be up. The specific failure is:");
				System.out.println(ioe);
			try {
				log_writer.write("ERROR: Attempt to connect to the TechSupportServer at "
				     +  args[0] + " has failed."  + newLine);
				log_writer.write("This network address may be incorrect for the server,"+ newLine);
				log_writer.write("or the server may not be up. The specific failure is:"+ newLine);
				log_writer.write(ioe+ newLine);
				return;
					
			} catch (Exception e_line) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e_line);
			}
			
			try{
			dos.close();
			socket.close();
			} catch (Exception e) {
				System.out.println( e );
			}
			 
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
					try {
						log_writer.write("Client: " + line  + "            ");
					} catch (Exception e) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e);
					}
					
					dis = new DataInputStream(socket.getInputStream());
					
					
					String reply = dis.readUTF(); // block/stall until the server replies
					System.out.println("Server: "+reply);
					//log the server's answer
					try {
						log_writer.write("Server : "+reply + newLine);
					} catch (Exception e) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e);
					}

					
					dos.close(); // hang up on the server
					socket.close();
					
					if( reply.equalsIgnoreCase(" Bye-bye."))  //Client then exits
						break;
					
				} catch (Exception e) {
					System.out.println("Something went wrong! Are you running the server?");
					e.printStackTrace();
				}
				
			} //end while true loop
			
		} catch (Exception e) {
			//Server Error
			System.out.println("Error: Socket System completely failed");
			System.out.println(e);
			
			try {
				log_writer.write("Support: I can't help you!"  + newLine);
				log_writer.write(e + newLine);
			} catch (Exception e2) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e2);
			}
		}	    
		

		try {
			log_writer.write( "Session Ended:  " + new Date() + newLine);
		} catch (Exception e) {
			System.out.println("Error: Could not write to TechSupportLog.txt") ;
			System.out.println(e);
		}
		
		
		
		//Close file io's
		try {
			log_writer.close();
			fw.close();
		} catch (Exception e) {
			System.out.println("Error: could not close log_writer or fw");
			System.out.println(e);
		}
	}
}
