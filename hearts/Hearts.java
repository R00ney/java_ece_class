//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

import java.util.*;


public class Hearts {
	public static void main(String[] args) throws InterruptedException {
	
			//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "HeartsGame program ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		//end name output
		
		
		List<String> players = new ArrayList<String>();
		
		players.add("James"); 
		players.add("Ben");
		players.add("David"); 
		players.add("Elizabeth");

		HeartsGame game = new HeartsGame(players.toArray(new String[0]));

		List<Thread> playerThreads = new ArrayList<Thread>();
		for(String s: players) {
			Thread t;
			playerThreads.add(
t=new Thread(new NaiveHeartsPlayer(s,game)));
			t.start();
		}
		for(Thread t: playerThreads)
			t.join();
		game.printGameScores();
	}
}

