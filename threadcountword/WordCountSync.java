// Lab 6: MultiThread Word Count
// Neal O'Hara

import java.util.Map;
import java.util.HashMap;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

public class WordCountSync extends AbstractWordCount {

	Map<String,Integer> map = new HashMap<String,Integer>();

	protected void increment(String word) {
		synchronized(this) {
			if (map.containsKey(word)) {
				// action if present
				map.put(word, map.get(word)+1);
			} else {
				// action if not present
				map.put(word, 1);
			}
		}
		
	}
	
	public void print(OutputStream stream) throws IOException 
	{
		// create a new PrintStream
		PrintStream out = new PrintStream(stream); 

		for (String word: map.keySet()) {
			out.println(word+":"+map.get(word));
		}
		
		out.flush();
		
	}

	public static void main(String[] args) {
	
	//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "WordCountSync ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		
		//program
		AbstractWordCount wc = new WordCountSync();
		
		
		//test and verify the command line args are valid
		try{
			if(args.length < 2){
				System.out.println("Please enter number of threads and at least one filename");
				return;
			}
			int temp = Integer.parseInt(args[0]);
			if(temp < 1){
				System.out.println("Please enter a valid positive number of threads");
				return;
			}
		}
		catch (Exception e){
			System.out.println("Error with command line arguments");
			System.out.println("Please enter number of threads and at least one filename");
			e.printStackTrace();
			return;
		}
		
		//zeroth args element is num of threads, so length -1
		String[] files = new String[(args.length-1)];
		//from 0 to filenames.length-1, since index of 0
		for(int k=0; k < (files.length); k++){
			files[k] = args[k+1];
			System.out.println("filename #" +k+" : " + files[k]);
		}
	
		int numThreads = Integer.parseInt(args[0]);
		
		System.out.println("numThreads: " + numThreads);
		System.out.println("Words Counted: ");
		
		
		wc.doWorkInParallel(files, numThreads);
		try {
			wc.print((OutputStream)System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}//end class
