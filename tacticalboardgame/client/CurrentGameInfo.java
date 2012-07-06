package tacticalboardgame.client;

import java.util.Calendar;

public class CurrentGameInfo {

	private long gameID;
	private String gameName;
	private String opponentName;
	private Calendar startDate;
	
	private String currentPlayerTurn;
	private String otherPlayer;
	private String description;
	
	public long getGameID() {
		return gameID;
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public String getOpponentName() {
		return opponentName;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setGameID(long gameID) {
		this.gameID = gameID;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getCurrentPlayerTurn() {
		return currentPlayerTurn;
	}

	public void setCurrentPlayerTurn(String currentPlayerTurn) {
		this.currentPlayerTurn = currentPlayerTurn;
	}

	public String getOtherPlayer() {
		return otherPlayer;
	}

	public void setOtherPlayer(String otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
