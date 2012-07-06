package tacticalboardgame.netutil;

import java.util.ArrayList;
import java.util.List;

import tacticalboardgame.client.CurrentGameInfo;
import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;

/**
 * gameId0;playerTurn;notPlayerTurn;description : gameId1;playerTurn;notPlayerTurn;description
 * @author Malik
 *
 */
public class GetMyCurrentGamesResponse{

	private String responseString;
	private List<CurrentGameInfo> currentGames;
	
	public GetMyCurrentGamesResponse(){
		
	}
	
	public GetMyCurrentGamesResponse(List<Game> games){
		StringBuffer resultBuffer = new StringBuffer();
		for(Game g: games){
			// gameId
			resultBuffer.append(g.getId() + ";");
			
			// playerTurn
			Player currentPlayer = g.getCurrentPlayerTurn();
			resultBuffer.append(currentPlayer.getUsername() + ";");
			
			// notPlayerTurn
			List<Player> players = g.getPlayers();
			for (Player p: players){
				if (p != currentPlayer){
					resultBuffer.append(p.getUsername() + ";");
				}
			}
			
			// description
			resultBuffer.append(g.getDescription());
			
			resultBuffer.append(":");
		}
		responseString = resultBuffer.toString();
	}

	public String getResponseString() {
		return responseString;
	}

	public GetMyCurrentGamesResponse(String responseString) {
		this.responseString = responseString;
		String[] games = responseString.split(":");
		currentGames = new ArrayList<CurrentGameInfo>(games.length);
		for (int i = 0; i < games.length; i++) {
			String[] gameInfo = games[i].split(";");
			CurrentGameInfo cgi = new CurrentGameInfo();
			
			// gameId
			cgi.setGameID(Long.parseLong(gameInfo[0]));
			
			// playerTurn
			cgi.setCurrentPlayerTurn(gameInfo[1]);
			
			// notPlayerTurn
			cgi.setOtherPlayer(gameInfo[2]);
			
			// description
			cgi.setDescription(gameInfo[3]);
			
			currentGames.add(cgi);
		}

	}
	
	public List<CurrentGameInfo> getCurrentGames(){
		return currentGames;
	}

}
