// Modified by Neal O'Hara
// ngohara
// ngohara@ncsu.edu
// 8/28/13

import java.io.FileWriter;
import java.io.BufferedWriter;

public class Logger {	
	
	String log;
	Boolean q;	

	// Keep log file string and boolean available for 
	// multiple opens and close of log
	// This allows every write to be save, so minimal data is lost
	// even if client or server crashes
	Logger (String log, Boolean q) {	
		this.log = log;
		this.q = q;
	}
	
	
	// Print message to output and write to log, with newline
	public void PrintnLog ( String message ) {
				
		String newLine = System.getProperty ( "line.separator" );
		FileWriter logfile = null;
				
		//Open filewriter for the log
		try {
			logfile = new FileWriter(log,q);
		} catch (Exception e) {
			System.out.println("Error, could not open log file");
			System.out.println( e.toString() );
		}
		//Open DataOutStream for file write
		BufferedWriter log_writer = new BufferedWriter( logfile );
		
		System.out.println( message );
		try {
			//logfile.write(message  + newLine);
			log_writer.write(message  + newLine);
		} catch (Exception log_error) {
			System.out.println("Error: Could not write to TechSupportLog.txt") ;
			System.out.println(log_error);
		}
		try {
			//Close and save log file
			log_writer.close();
		} catch (Exception e) {System.out.println(e.toString()); }
	}

	// Print client messages to output and write to log with no newline
	public void ClientPrintnLog ( String message ) {
		
		FileWriter logfile = null;
			
		//Open filewriter for the log
		try {
			logfile = new FileWriter(log,q);
		} catch (Exception e) {
			System.out.println("Error, could not open log file");
			System.out.println( e.toString() );
		}
		//Open DataOutStream for file write
		BufferedWriter log_writer = new BufferedWriter( logfile );
		
		System.out.println( message );
		try {
			//logfile.write(message  + newLine);
			log_writer.write(message);
		} catch (Exception log_error) {
			System.out.println("Error: Could not write to TechSupportLog.txt") ;
			System.out.println(log_error);
		}
		try {
			//Close and save log file
			log_writer.close();
		} catch (Exception e) {System.out.println(e.toString()); }
	}	
	
	// Only write message to the log file with no newline
	public void Log (String message) {

		FileWriter logfile = null;
		
		//Open filewriter for the log
		try {
			logfile = new FileWriter(log,q);
		} catch (Exception e) {
			System.out.println("Error, could not open log file");
			System.out.println( e.toString() );
		}
		//Open DataOutStream for file write
		BufferedWriter log_writer = new BufferedWriter( logfile );
		
		try {
			//logfile.write(message  + newLine);
			log_writer.write(message);
		} catch (Exception log_error) {
			System.out.println("Error: Could not write to TechSupportLog.txt") ;
			System.out.println(log_error);
		}
		try {
			//Close and save log file
			log_writer.close();
		} catch (Exception e) {System.out.println(e.toString()); }
	}
	
	
}
