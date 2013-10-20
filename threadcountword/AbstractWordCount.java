// Lab 6: MultiThread Word Count
// Neal O'Hara

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;


public abstract class AbstractWordCount implements Runnable {
	
	private String[] filenames = null;
	private int position=0;

	public void run(){
	
		while(true) {
			String filename = getFilename();
			if(filename==null) break;
			try {
				// do file processing on filename
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String line;
				while( (line=br.readLine())!=null) {
					// while loop over all lines in file
					String[] words = line.split(" ");
					for( String w: words ) {
						if( w.trim().length() > 1 ) {
							increment(w.trim());
						}
					}
				}
				// close the file
				br.close();
			} catch (Exception e) { // do nothing
			}
		}


	}
	
	protected abstract void increment(String word);
	
	public void doWorkInParallel(String[] args, int numThreads) {
		// to make sure these updates are visible before creating threads
		// surround them with synchronized block
		synchronized (this) { 
			filenames = args; // initialize filenames
			position = 0;     // reset position to 0
		}  
		
		// create threads
		Collection<Thread> threads = new ArrayList<Thread>();
		for(int i=0; i<numThreads; i++) {
			// Make a new thread using this Runnable object
			Thread t= new Thread(this); 
			// Remember it in the list so we can wait for it to finish later
			threads.add(t);
			// Start the thread
			t.start();
		}

		// wait for threads
		for(Thread t: threads) {
			try {
						t.join(); // wait for thread to finish
			} catch (Exception e) { 
			/*This will let us provide an approximate answer for the Word Counts.  In such a scenario, we may want to notify the user of such approximation, but we’ll skip this for now.
			*/}
		}

	}


	public synchronized String getFilename() {
		if (position == filenames.length)
			return null;

		return filenames[position++];
	}

	public abstract void print(OutputStream stream) throws IOException;

	
}