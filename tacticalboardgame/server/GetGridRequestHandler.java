package tacticalboardgame.server;

import tacticalboardgame.model.Game;
import tacticalboardgame.netutil.GetGridRequest;
import tacticalboardgame.netutil.GetGridResponse;

/**
 * 
 * @author malik
 *
 */
public class GetGridRequestHandler extends RequestHandler {

	public GetGridRequestHandler(String request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String handle(String params) {
		GetGridRequest request = new GetGridRequest(params);
//		String playerName = request.getPlayerName();
		long gameId = request.getGameId();
		GameManager gameManager = PseudoGameManager.getInstance();
		Game game = gameManager.getGame(gameId);
		
		String result = "Game does not exist.";
		
		if (game != null){
			GetGridResponse responseCreator = new GetGridResponse(game);
			result = responseCreator.generateResponseString();
		}
		return result;
	}

}
