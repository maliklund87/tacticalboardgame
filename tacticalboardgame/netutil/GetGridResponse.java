package tacticalboardgame.netutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Player;
import tacticalboardgame.model.Unit;

/**
 * Responsible for converting a Game object into a response-string
 * and converting a responseString into objects
 * @author malik
 *
 */
public class GetGridResponse{
	
	private String responseString;
	private long gameId;
	private String currentPlayer;
	private String[] playersNames;
	private String[] units;
	private Game game;
	
	private String mainSeperator = ":";
	private String subSeperator = ";";
	
	public GetGridResponse(){
		
	}
	
	public GetGridResponse(Game game){
		this.game = game;
	}

	/**
	 * Generates a response-string based on the game given in the constructor.
	 */
	public String getResponseString() {
		return responseString;
	}
	
	public String generateResponseString(){
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(game.getId() + mainSeperator);
		resultBuffer.append(game.getCurrentPlayerTurn().getUsername());
		List<Player> players = game.getPlayers();
		for (Player p: players){
			if (p != game.getCurrentPlayerTurn()){
				resultBuffer.append(subSeperator + p.getUsername());
			}
		}
		resultBuffer.append(mainSeperator);
		
		List<Unit> units = game.getUnits();
		for(Unit u: units){
			resultBuffer.append(u.getPlayer().getUsername() + subSeperator);
			resultBuffer.append(u.getId() + subSeperator);
			resultBuffer.append(u.getPosition().getId() + subSeperator);
			resultBuffer.append(u.getHitpoints() + subSeperator);
			resultBuffer.append(u.getActionPoints() + subSeperator);
			resultBuffer.append(mainSeperator);
		}
		
		responseString = resultBuffer.toString();
		return responseString;
	}
	
	/**
	 * should maybe be moved to a constructor?
	 */
	public GetGridResponse(String responseString) {
		this.responseString = responseString;
		String[] responseArray = responseString.split(mainSeperator);
		gameId = Long.parseLong(responseArray[0]);
		playersNames = responseArray[1].split(subSeperator);
		currentPlayer = playersNames[0];
		units = new String[responseArray.length - 2];
		for (int i = 2; i < responseArray.length; i++){
			units[i-2] = responseArray[i];
		}
		
		
		Map<String, Player> playersMap = new HashMap<String, Player>();
		for (String s: playersNames){
			playersMap.put(s, new Player(s));
		}
		game = new Game(Game.DEFAULT_GRID_MAP, new ArrayList<Player>(playersMap.values()), gameId);
		
		List<Player> players = game.getPlayers();
		for (Player player: players){
			if (player.getUsername().equals(currentPlayer)){
				game.setCurrentPlayer(player);
			}
		}
		
		
		for(String s : units){
			String[] unitInfoArray = s.split(subSeperator);
			Player p = playersMap.get(unitInfoArray[0]);
			Unit unit = new Unit(Integer.parseInt(unitInfoArray[1]));
			
			int hp = Integer.parseInt(unitInfoArray[3]);
			unit.setHp(hp);
			
			int ap = Integer.parseInt(unitInfoArray[4]);
			unit.setActionPoints(ap);
			
			p.addUnit(unit);
			int position = Integer.parseInt(unitInfoArray[2]);
			game.putUnit(unit, position);
		}
	}
	
	public long getGameId(){
		return gameId;
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	public String[] getUnits() {
		return units;
	}
	
	public Game getGame(){
		return game;
	}

}
