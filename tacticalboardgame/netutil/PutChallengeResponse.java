package tacticalboardgame.netutil;

import tacticalboardgame.model.Challenge;

public class PutChallengeResponse {
	
	private boolean success;
	private Challenge challenge;
	
	private String responseString = null;
	
	private long challengeId;

	public PutChallengeResponse(boolean success, Challenge challenge){
		this.success = success;
		this.challenge = challenge;
	}
	
	public PutChallengeResponse(String responseString){
		this.responseString = responseString;
		if (responseString.startsWith("success:")){
			success = false;
			challengeId = Long.parseLong(responseString.split(":")[1]);
		} else {
			success = true;
		}
	}
	
	public String getResponseString(){
		if (responseString == null){
			StringBuffer responseBuffer = new StringBuffer();
			if (success){
				responseBuffer.append("success:");
				responseBuffer.append(challenge.getChallengeId());
			} else {
				responseBuffer.append("failure:");
			}
		}
		return responseString;
	}
	
	public boolean isSuccess(){
		return success;
	}
	
	public long challengeId(){
		return challengeId;
	}
}
