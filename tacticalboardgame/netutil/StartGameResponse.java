package tacticalboardgame.netutil;

import tacticalboardgame.model.Game;

public class StartGameResponse {
	
	private String responseString;
	private Game game;
	
	private long gameId;

	public StartGameResponse(String responseString){
		this.responseString = responseString;
		if (!responseString.startsWith("failed")){
			gameId = Long.parseLong(responseString);
		} else {
			gameId = -1;
		}
	}
	
	public StartGameResponse(Game game){
		this.game = game;
	}
	
	public String getResponseString(){
		if (responseString == null){
			if (game != null){
				responseString = game.getId() + "";
			} else {
				responseString = "failed";
			}
		}
		return responseString;
	}
	
	/**
	 * 
	 * @return Returns the ID of the new game; -1 if request failed.
	 */
	public long getGameId(){
		return gameId;
	}
}
