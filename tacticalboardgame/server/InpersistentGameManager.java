package tacticalboardgame.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;

public class InpersistentGameManager implements GameManager{

	private static GameManager gameManagerInstance = null;
	
	private List<Game> games;
	private List<Game> finishedGames;

	private InpersistentGameManager() {
		games = new ArrayList<Game>();
		finishedGames = new ArrayList<Game>();
	}

	public synchronized static GameManager getInstance() {
		if (gameManagerInstance == null) {
			gameManagerInstance = new InpersistentGameManager();
		}
		return gameManagerInstance;
	}
	
	public Game getGame(long gameId){
		Game game = null;
		Iterator<Game> iter = games.iterator();
		while (game == null && iter.hasNext()){
			game = iter.next();
			if (game.getId() != gameId){
				game = null;
			}
		}
		return game;
	}

	/**
	 * TODO: This method should probably be replaced with a lookup
	 *       in a database, rather than searching through a list.
	 * @param username
	 * @return
	 */
	public List<Game> getUserGames(String username) {
		List<Game> usersGames = new ArrayList<Game>();
		for (Game g: games){
			if (g.hasPlayer(username)){
				usersGames.add(g);
			}
		}
		return usersGames;
	}

	public void startNewGame(List<Player> players) {
		Game newGame = new Game(Game.DEFAULT_GRID_MAP, players, System.currentTimeMillis());
		// TODO: remove!!!!
		newGame.init();
		games.add(newGame);
	}

	public void endGame(long id) {
		Game finishedGame = getGame(id);
		if (finishedGame != null){
			games.remove(finishedGame);
			finishedGames.add(finishedGame);
		}
	}
}
