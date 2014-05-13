package shared;

import java.io.Serializable;

public class Request implements Serializable {
	
	public Request(char c, String m){
		playerId = c;
		message = m;
	}
	public char playerId;
	public String message;
}
