package tacticalboardgame.netutil;

public class GetGridRequest{
	
	private String playerName;
	private long gameId;
	private boolean success = false;

	public GetGridRequest(String playerName, long gameId){
		this.playerName = playerName;
		this.gameId = gameId;
		if (playerName != null && gameId >= 0){
			success = true;	
		}
	}
	
	public GetGridRequest(String requestString){
		String[] requestArray = requestString.split(":");
		try {
			playerName = requestArray[1];
			gameId = Long.parseLong(requestArray[2]);
			success = true;
		} catch (IndexOutOfBoundsException outOfBoundsException){
			success = false;
		} catch (NumberFormatException numberFormatException){
			success = false;
		}
	}


	public String getRequestString() {
		return "getgrid:" + playerName + ":" + gameId;
	}
	
	public boolean successFullyInitialized() {
		return success;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

}
