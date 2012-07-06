package tacticalboardgame.netutil;

import tacticalboardgame.model.Account;

public class PutChallengeRequest {

	private String requestString = null;
	
	private Account account;
	private String description;
	
	private String username;

	/**
	 * A client uses this constructor to create a request-string from a 
	 * series of objects.
	 * @param account
	 * @param description
	 */
	public PutChallengeRequest(Account account, String description){
		this.account = account;
		this.description = description;
	}
	
	/**
	 * The server uses this constructor to create a collection of objects from
	 * a request string.
	 * @param requestString
	 */
	public PutChallengeRequest(String requestString){
		String[] params = requestString.split(":");
		username = params[1];
		description = params[2];
	}
	
	/**
	 * If this object was initialized with the client-side constructor, it will
	 * return a generated request string. Otherwise it just returns the string
	 * given to the server side constructor.
	 * @return
	 */
	public String getRequestString(){
		if (requestString == null){
			StringBuffer requestStringBuffer = new StringBuffer();
			requestStringBuffer.append("putchallenge:");
			requestStringBuffer.append(account.getName() + ":");
			requestStringBuffer.append(description);
			requestString = requestStringBuffer.toString();
		}
		return requestString;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getDescription(){
		return description;
	}
}
