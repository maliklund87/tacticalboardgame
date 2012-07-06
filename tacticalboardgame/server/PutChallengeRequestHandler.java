package tacticalboardgame.server;

import tacticalboardgame.model.Challenge;
import tacticalboardgame.model.Player;
import tacticalboardgame.netutil.PutChallengeRequest;
import tacticalboardgame.netutil.PutChallengeResponse;

public class PutChallengeRequestHandler extends RequestHandler {

	public PutChallengeRequestHandler(String request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handle(String requestString) {
		PutChallengeRequest request = new PutChallengeRequest(requestString);
		String username = request.getUsername();
		String description = request.getDescription();
		
		Challenge challenge = new Challenge(new Player(username), description);
		
		ChallengeManager challengeManager = ChallengeManager.getInstance();
		
		boolean success = challengeManager.addChallenge(challenge);
		PutChallengeResponse response = new PutChallengeResponse(success, challenge);
		 
		String result = response.getResponseString();
		
		return result;
	}

}
