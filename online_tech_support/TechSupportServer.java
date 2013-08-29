import java.io.*;
import java.util.Date;
import java.net.ServerSocket;
import java.net.InetAddress;

/**
 *  @author jtuck
 */

// Modified by Neal O'Hara
// ngohara
// 8/28/13


public class TechSupportServer {
	
	public static void main(String[] args) {
		
		
		FileWriter fw = null;
		BufferedWriter log_writer = null;
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara" + newLine;
		
		try {
			//Create a log of interactions
			fw = new FileWriter("SERVER_TechSupportLog.txt",true); 
			//Open DataOutStream for file write
			log_writer = new BufferedWriter( fw );
			
			//Start new session in log file
			System.out.println(myname + newLine + "TechSupportServer Program");
			log_writer.write(newLine + myname);
			log_writer.write("This session started " + new Date() + newLine);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
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

		
		// Set up an object to read in a question from the user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try{ //Setup SocketServer and IP Address
			ServerSocket ss = new ServerSocket(1919);// specify port #
	
			System.out.println("TechSupportServer is up at "
				+ InetAddress.getLocalHost().getHostAddress()
				+" on port " 
				+ ss.getLocalPort());
	
			
			try {
				log_writer.write("TechSupportServer is up at "
				+ InetAddress.getLocalHost().getHostAddress()
				+" on port " 
				+ ss.getLocalPort() + newLine);
			} catch (Exception e34) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e34);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
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
				
				
				
				String line = reader.readLine().trim();
				try {
					log_writer.write(line + newLine);
				} catch (Exception e34) {
					System.out.println("Error: Could not write to TechSupportLog.txt") ;
					System.out.println(e34);
				}
					
				// if the line has no characters...
				if (line.length() > 0) {
					// check if string matches bye
					if (line.equalsIgnoreCase("bye"))
						break;
					
					// Get an answer randomly from the responses array
					String answer = responses[(int)(Math.random()*responses.length)];

					System.out.println("Support: " + answer);
					try {
						log_writer.write("Support: " + answer + newLine);
					} catch (Exception e23) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(e23);
					}
				} else {
					
					System.out.println("Support: You there?");
					try {
						log_writer.write("Support: You there?"  + newLine);
					} catch (Exception e) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(e);
					}
				}
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
		
		//Server Exits
		System.out.println("Support: Bye-bye.");
		try {
			log_writer.write("Support: Bye-bye."  + newLine + "Session Ended:  " + new Date() + newLine);
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
