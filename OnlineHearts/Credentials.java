// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13

import java.io.Serializable;

//User's credentials,
//only ever sent to Game Server.
public class Credentials implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	public Credentials(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public String getName(){ return name; }
	public String getPassword() {return password;}
}
