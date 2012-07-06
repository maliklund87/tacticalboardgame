package tacticalboardgame.server;

import java.util.List;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;

public interface GameManager {

	public Game getGame(long gameId);
	public List<Game> getUserGames(String username);
	public void startNewGame(List<Player> players);
	public void endGame(long id);
}
