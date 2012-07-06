package tacticalboardgame.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tacticalboardgame.model.Challenge;
import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;

public class ChallengeManager {

	private static ChallengeManager challengeManagerInstance = null;

	private List<Challenge> challenges;

	private ChallengeManager() {
		challenges = new ArrayList<Challenge>();
	}

	public synchronized static ChallengeManager getInstance() {
		if (challengeManagerInstance == null) {
			challengeManagerInstance = new ChallengeManager();
		}
		return challengeManagerInstance;
	}

	public List<Challenge> getChallenges() {
		return new ArrayList<Challenge>(challenges);
	}

	public boolean addChallenge(Challenge newChallenge) {
		if (!challenges.contains(newChallenge)) {
			challenges.add(newChallenge);
			return true;
		}
		return false;
	}

	public Game startGame(long challengeId, Player opponent) {
		Challenge challenge = null;
		Iterator<Challenge> iter = challenges.iterator();
		while (challenge == null && iter.hasNext()) {
			challenge = iter.next();
			if (challenge.getChallengeId() != challengeId) {
				challenge = null;
			}
		}

		Game game = null;

		if (challenge != null && !challenge.getChallenger().getUsername().equals(opponent.getUsername())) {
			List<Player> players = new ArrayList<Player>();
			players.add(challenge.getChallenger());
			players.add(opponent);
			game = new Game(Game.DEFAULT_GRID_MAP, players, System.currentTimeMillis());
		}

		return game;
	}

}
