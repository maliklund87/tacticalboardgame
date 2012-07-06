package tacticalboardgame.server;

public class RequestHandlerFactory {
	
	public static RequestHandler getRequestHandler(String requestType){
		if (requestType.equals("submitturn")){
			return new SubmitTurnRequestHandler(requestType);
		} 
		else if (requestType.equals("getmycurrentgames")){
			return new GetMyCurrentGamesRequestHandler(requestType);
		} 
		else if (requestType.equals("getgrid")){
			return new GetGridRequestHandler(requestType);
		} 
		else if (requestType.equals("putchallenge")){
			return new PutChallengeRequestHandler(requestType);
		} 
		else if (requestType.equals("getchallenges")){
			return new GetChallengesRequestHandler(requestType);
		} 
		else if (requestType.equals("startgame")){
			return new StartGameRequestHandler(requestType);
		}
		return null;
	}
}
