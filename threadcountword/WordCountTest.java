// Lab 6: MultiThread Word Count
// Neal O'Hara

import java.util.Date;

public class WordCountTest {

	//reference, list of filenames, and numThreads
	public static long averageTime(AbstractWordCount wc, String[] args, int numThreads) {
		wc.doWorkInParallel(args, numThreads);

		// measure the average time
		long startTime = System.currentTimeMillis();
		for(int i=0; i<3; i++) {
			wc.doWorkInParallel(args, numThreads);			
		}
		long stopTime = System.currentTimeMillis();
		
		return (stopTime-startTime)/3;
	}
	
	public static void evaluate(AbstractWordCount wc, String[] args) {
	// try different thread counts and compare performance
		for(int i=1; i<=16; i=i*2) {
			System.out.println( wc.getClass().getName()+ " on " + i + 
		" threads is: " + averageTime(wc,args,i)+"ms");
		}
	}
	
	//Pass list of filenames
	public static void main(String[] args) {
	
	//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "WordCountTest ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		//Program
		AbstractWordCount wc = new WordCountSync();
		evaluate(wc,args);
		AbstractWordCount wc2 = new WordCountConcurrent();
		evaluate(wc2,args);

	}

	


}

