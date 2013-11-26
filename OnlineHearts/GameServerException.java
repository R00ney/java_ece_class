// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13


public class GameServerException extends Exception {
	protected String message;
	public GameServerException(String m) {
		message = m;
	}
	public String getMessage() {
		return message;
	}
}

