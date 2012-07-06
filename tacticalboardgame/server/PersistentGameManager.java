package tacticalboardgame.server;

import java.util.List;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;

public class PersistentGameManager implements GameManager{

	private static PersistentGameManager gameManagerInstance = null;
	
//	private List<Game> games;
//	private List<Game> finishedGames;

	private PersistentGameManager() {
//		games = new ArrayList<Game>();
//		finishedGames = new ArrayList<Game>();
	}

	public synchronized static PersistentGameManager getInstance() {
		if (gameManagerInstance == null) {
			gameManagerInstance = new PersistentGameManager();
		}
		return gameManagerInstance;
	}
	
	public Game getGame(long gameId){
		Dao dao = Dao.getInstance();
		Game game = dao.getGame(gameId);
		return game;
	}

	/**
	 * TODO: This method should probably be replaced with a lookup
	 *       in a database, rather than searching through a list.
	 * @param username
	 * @return
	 */
	public List<Game> getUserGames(String username) {
		Dao dao = Dao.getInstance();
		List<Game> usersGames = dao.getUserGames(username);
		return usersGames;
	}

	public void startNewGame(List<Player> players) {
		Game newGame = new Game(Game.DEFAULT_GRID_MAP, players, System.currentTimeMillis());
		// TODO: remove!!!!
		newGame.init();
		Dao dao = Dao.getInstance();
		dao.saveGame(newGame);
	}

	public void endGame(long id) {
		Game finishedGame = getGame(id);
		if (finishedGame != null){
//			games.remove(finishedGame);
//			finishedGames.add(finishedGame);
			Dao dao = Dao.getInstance();
			dao.deleteGame(finishedGame);
		}
	}
}
