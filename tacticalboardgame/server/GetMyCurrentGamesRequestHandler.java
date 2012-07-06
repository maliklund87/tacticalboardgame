package tacticalboardgame.server;

import java.util.List;

import tacticalboardgame.model.Game;
import tacticalboardgame.netutil.GetMyCurrentGamesRequest;
import tacticalboardgame.netutil.GetMyCurrentGamesResponse;

/**
 * Handles request for fetching all games a user is playing at
 * the moment.
 * @author Malik
 *
 */
public class GetMyCurrentGamesRequestHandler extends RequestHandler {

	public GetMyCurrentGamesRequestHandler(String request) {
		super(request);
	}

	@Override
	public String handle(String requestString) {
		GetMyCurrentGamesRequest request = new GetMyCurrentGamesRequest(requestString);
		String username = request.getUsername();
		
		GameManager gameManager = PseudoGameManager.getInstance();
		List<Game> games = gameManager.getUserGames(username);
		
		GetMyCurrentGamesResponse response = new GetMyCurrentGamesResponse(games);
		
		return response.getResponseString();
	}

}
