package tacticalboardgame.netutil;

import tacticalboardgame.model.Account;

public class GetChallengesRequest {

	private Account account;
	private String requestString;
	
	private String username;

	/**
	 * Client
	 * @param account
	 */
	public GetChallengesRequest(Account account){
		this.account = account;
	}
	
	/**
	 * Server
	 * @param requestString
	 */
	public GetChallengesRequest(String requestString){
		this.requestString = requestString;
		this.username = requestString.split(":")[1];
	}
	
	/**
	 * Client: Generates request string.
	 * Server: Returns the string given to the constructor
	 * @return
	 */
	public String getRequestString(){
		if (requestString == null){
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("getchallenges:");
			requestBuffer.append(account.getName());
		}
		return requestString;
	}
	
	public String getUsername(){
		return username;
	}
}
