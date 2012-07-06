package tacticalboardgame.server;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;
import tacticalboardgame.netutil.StartGameRequest;
import tacticalboardgame.netutil.StartGameResponse;

public class StartGameRequestHandler extends RequestHandler {

	public StartGameRequestHandler(String request) {
		super(request);
	}

	@Override
	public String handle(String requestString) {
		StartGameRequest request = new StartGameRequest(requestString);
		String username = request.getUsername();
		long challengeId = request.getChallengeId();
		
		ChallengeManager challengeManager = ChallengeManager.getInstance();
		Game game = challengeManager.startGame(challengeId, new Player(username));
		
		StartGameResponse response = new StartGameResponse(game);
		
		String result = response.getResponseString();

		return result;
	}

}
