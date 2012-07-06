package tacticalboardgame.netutil;

import tacticalboardgame.model.Account;

public class GetMyCurrentGamesRequest{

	private Account player;
	private String requestString;
	
	public GetMyCurrentGamesRequest(Account player){
		this.player = player; 
	}
	
	public GetMyCurrentGamesRequest(String requestString){
		this.requestString = requestString;
	}


	public String getRequestString() {
		if (player == null){
			return requestString;
		}
		return "getmycurrentgames:" + player.getName();
	}

	public String getUsername() {
		String username = requestString.split(":")[1];
		return username;
	}
	
	
	
	

}
