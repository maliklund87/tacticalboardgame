package tacticalboardgame.server;

import java.util.List;

import tacticalboardgame.model.Challenge;
import tacticalboardgame.netutil.GetChallengesResponse;

public class GetChallengesRequestHandler extends RequestHandler{

	public GetChallengesRequestHandler(String request) {
		super(request);
	}

	@Override
	public String handle(String requestString) {
//		GetChallengesRequest request = new GetChallengesRequest(requestString);
		// TODO: perform some kind of user validation
//		String username = request.getUsername();
		
		ChallengeManager challengeManager = ChallengeManager.getInstance();
		List<Challenge> challenges = challengeManager.getChallenges();
		
		GetChallengesResponse response = new GetChallengesResponse(challenges);

		String result = response.getResponseString();
		
		return result;
	}

}
