package tacticalboardgame.netutil;

import java.util.ArrayList;
import java.util.List;

import tacticalboardgame.model.Challenge;
import tacticalboardgame.model.Player;

public class GetChallengesResponse {
	
	private List<Challenge> challenges;
	private String responseString;

	
	/**
	 * Server
	 */
	public GetChallengesResponse(List<Challenge> challenges){
		this.challenges = challenges;
	}
	
	/**
	 * Client
	 * @param responseString
	 */
	public GetChallengesResponse(String responseString){
		this.responseString = responseString;
		challenges = new ArrayList<Challenge>();
		String[] challengesArray = responseString.split(":");
		for (int i = 0; i < challengesArray.length; i++){
			String[] challengeArray = challengesArray[i].split(";");
			long challengeId = Long.parseLong(challengeArray[0]);
			String username = challengeArray[1];
			String description = challengeArray[2];
			challenges.add(new Challenge(new Player(username), description, challengeId));
		}
	}
	
	public String getResponseString(){
		if (responseString == null){
			StringBuffer resultBuffer = new StringBuffer();
			for (Challenge c: challenges){
				resultBuffer.append(c.getChallengeId() + ";");
				resultBuffer.append(c.getChallenger().getUsername() + ";");
				resultBuffer.append(c.getDescription() + ":");
			}
		}
		return responseString;
	}
	
	public List<Challenge> getChallenges(){
		return challenges;
	}
}
