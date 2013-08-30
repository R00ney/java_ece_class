
/**
 *  @author jtuck
 */

// Modified by Neal O'Hara
// ngohara
// 8/28/13

import java.io.*;
import java.util.Date;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;


public class TechSupportServer {	
	
	public static void main(String[] args) {
		
		FileWriter fw = null;
		BufferedWriter log_writer = null;
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara" + newLine;
		int drop_conn_count = 0;
		ServerSocket ss = null;
		Socket socket = null;
		DataOutputStream server_dos = null;
		DataInputStream server_dis = null;
		
		String test = "This is a test connection";
		Boolean first_time = true;
		Boolean bye_occured = false;
		
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
			ss = new ServerSocket(1919);// specify port #
	
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
				
				//Make and Report Client connections or connection issues
				try {
					socket = ss.accept(); // wait for a client to connect
					
					if ( first_time ) {
						System.out.println("Connection made from a calling client!");
						try {
							log_writer.write("Connection made from a calling client!" + newLine);
						} catch (Exception e35) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(e35);
						}
					}
				} catch (Exception ioe) {
					drop_conn_count++; 	//keep track of total connections lost
					System.out.println("Connection problem with a client: " + ioe + newLine + "Total Connections lost = " + String.valueOf(drop_conn_count));
					try {
						log_writer.write("Connection problem with a client: " + ioe + newLine + "Total Connections lost = " + String.valueOf(drop_conn_count) + newLine);
					} catch (Exception e36) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e36);
					}
				}
				
				//open data input on
				server_dis = new DataInputStream(socket.getInputStream());
				//System.out.println("Debug:  Stream opened");
				String question = server_dis.readUTF();  // exception could happen here
				
				
				
				if (question.equalsIgnoreCase( test )) {
					// Don't respond or worry about this test connections
					//clean up current connections
					try {
						first_time = false;  //disable first time message
						server_dis.close();
						socket.close();
					} catch (Exception close_error) { 
						System.out.println( close_error);
						try {
							log_writer.write(close_error  + newLine);
						} catch (Exception log_error) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(log_error);
						}
					}
				} else {		
				
					//show and log cleint question
					System.out.println("Client: " + question);
					try {
						log_writer.write("Client: " + question + newLine);
					} catch (Exception e34) {
						System.out.println("Error: Could not write to TechSupportLog.txt") ;
						System.out.println(e34);
					}
					
					// if the line has no characters...
					if (question.length() > 0) {
						// check if string matches bye
						if (question.equalsIgnoreCase("bye")){
							bye_occured = true;
							
							//Server Exits
							System.out.println("Support: Bye-bye.");
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF(" Bye-bye.");
							try {
								log_writer.write("Support: Bye-bye."  + newLine + "Session Ended:  " + new Date() + newLine);
							} catch (Exception e) {
								System.out.println("Error: Could not write to TechSupportLog.txt") ;
								System.out.println(e);
							}
							//no break, server keeps running
						
						//} else 
							
						}
						else {	
													
							// Get an answer randomly from the responses array
							String answer = responses[(int)(Math.random()*responses.length)];
		
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF(answer);
							System.out.println("Support: " + answer);
							try {
								log_writer.write("Support: " + answer + newLine);
							} catch (Exception e23) {
									System.out.println("Error: Could not write to TechSupportLog.txt") ;
									System.out.println(e23);
							}
							
							//close data_out_stream
							try {
								server_dos.close();
							} catch (Exception close_error) { 
								System.out.println( close_error);
								try {
									log_writer.write(close_error  + newLine);
								} catch (Exception log_error) {
									System.out.println("Error: Could not write to TechSupportLog.txt") ;
									System.out.println(log_error);
								}
							}
						}
					
					} else {
						
						if(bye_occured==false){
							server_dos = new DataOutputStream(socket.getOutputStream());
							server_dos.writeUTF("Support: You there?");
							System.out.println("Support: You there?");
							try {
								log_writer.write("Support: You there?"  + newLine);
							} catch (Exception e) {
									System.out.println("Error: Could not write to TechSupportLog.txt") ;
									System.out.println(e);
							}
						} else { bye_occured = false; }
						
					}//End if lenght >0
					
					//clean up current connections
					try {
						server_dis.close();
						socket.close();
						
					} catch (Exception close_error) { 
						System.out.println( close_error);
						try {
							log_writer.write(close_error  + newLine);
						} catch (Exception log_error) {
							System.out.println("Error: Could not write to TechSupportLog.txt") ;
							System.out.println(log_error);
						}
					}
					
				} //end if first time else
			}//End while(true)
			
		} catch (Exception e) {
			//Server Error
			//server_dos = new DataOutputStream(socket.getOutputStream());
			//server_dos.writeUTF("Support: I can't help you!");
			System.out.println("Support: I can't help you!");
			
			try {
				log_writer.write("Support: I can't help you!"  + newLine);
			} catch (Exception e2) {
				System.out.println("Error: Could not write to TechSupportLog.txt") ;
				System.out.println(e2);
			}
			
			//clean up current connections
			try {
				server_dis.close();
				socket.close();
			} catch (Exception close_error) { 
				System.out.println( close_error);
				try {
					log_writer.write(close_error  + newLine);
				} catch (Exception log_error) {
					System.out.println("Error: Could not write to TechSupportLog.txt") ;
					System.out.println(log_error);
				}
			}
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
