package tacticalboardgame.netutil;

import tacticalboardgame.model.Account;

public class StartGameRequest {
	
	private String requestString;
	
	private Account account;
	private String username;
	private long challengeId;

	public StartGameRequest(String requestString){
		this.requestString = requestString;
		String[] requestArray = requestString.split(":");
		username = requestArray[1];
		challengeId = Long.parseLong(requestArray[2]);
	}
	
	public StartGameRequest(Account account, long challengeId){
		this.account = account;
		this.challengeId = challengeId;
	}
	
	public String getRequestString(){
		if (requestString == null){
			StringBuffer requestBuffer = new StringBuffer();
			requestBuffer.append("startgame:");
			requestBuffer.append(account.getName() + ":");
			requestBuffer.append(challengeId);
			requestString = requestBuffer.toString();
		}
		return requestString;
	}

	public String getUsername() {
		return username;
	}

	public long getChallengeId() {
		return challengeId;
	}
}
