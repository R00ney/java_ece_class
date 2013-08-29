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
		
		if (args.length != 1)
		{
		   System.out.println("Restart. Provide the server computer address "
				    + "in dotted-numeric form (or 'localhost', without the quotes) "
				    + "as a single command line parameter.");
		   return; // terminate so user can restart.
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
		
		
		try {
			// Provide a greeting
			System.out.println("Support: Hello! I'm available to answer your questions.");
			log_writer.write("Support: Hello! I'm available to answer your questions." + newLine);
			System.out.println("Support: If at any time you no longer my help, just type");
			log_writer.write("Support: If at any time you no longer my help, just type" + newLine);
			System.out.println("         'Bye' to leave.");
			log_writer.write("         'Bye' to leave." + newLine);
					
		} catch (Exception e) {
			System.out.println("Error: Could not write to TechSupportLog.txt or to output line") ;
			System.out.println(e);
		}
		
		try {
			while(true) {
						//Attempt to open socket
				try {
					Socket socket = new Socket(args[0],1919);
					System.out.println("Connected to the server at " + args[0]);
					try {
						log_writer.write("Connected to the server at " + args[0]  + newLine);							
					} catch (Exception e_line) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e_line);
					}
					break;
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
							
					} catch (Exception e_line) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e_line);
					}
					break;
				}
			
				
				//Old terminal input code
				//String line = reader.readLine().trim();
				//log_writer.write("Client:   " + line + "         ");
				
				
				//Old Server Code
				/* 
				// if the line has no characters...
				if (line.length() > 0) {
					// check if string matches bye
					if (line.equalsIgnoreCase("bye"))
						break;
					
					// Get an answer randomly from the responses array
					String answer = "reply";

					System.out.println("Support: " + answer);
					log_writer.write("Support: " + answer  + newLine);
					
				} else {
					
					System.out.println("Support: You there?");
					try {
						log_writer.write("Support: You there?"  + newLine);
					} catch (Exception e) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(e);
					}
				}*/
			}
		} catch (Exception e) {
			//Server Error
			System.out.println("Support: I can't help you!");
			
			try {
				log_writer.write("Support: I can't help you!"  + newLine);
			} catch (Exception e2) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e2);
			}
		}	    
		
		/* Old Server Code
		//Server Exits
		System.out.println("Support: Bye-bye.");
		try {
			log_writer.write("Support: Bye-bye."  + newLine + "Session Ended:  " + new Date() + newLine);
		} catch (Exception e) {
			System.out.println("Error: Could not write to TechSupportLog.txt") ;
			System.out.println(e);
		}
		*/
		
		
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
