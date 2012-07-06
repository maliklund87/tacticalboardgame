package tacticalboardgame.model;


public class Challenge {
	
	private long challengeId;
	private Player challenger;
	private String description;
	
	public Challenge(Player challenger, String description){
		this.challenger = challenger;
		this.description = description;
		challengeId = System.currentTimeMillis();
	}	
	
	public Challenge(Player challenger, String description, long id){
		this.challenger = challenger;
		this.description = description;
		challengeId = System.currentTimeMillis();
	}
	
	public Player getChallenger() {
		return challenger;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean equals(Challenge otherChallenge){
		if (challengeId == otherChallenge.getChallengeId()){
			return true;
		}
		return false;
	}
	
	public long getChallengeId() {
		return challengeId;
	}
}
