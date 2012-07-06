package tacticalboardgame.netutil;

import java.util.ArrayList;
import java.util.List;

import tacticalboardgame.model.Account;
import tacticalboardgame.model.UnitTurn;

/**
 * Converts a request string into objects and objects into a request string.
 * @author Malik
 *
 */
public class SubmitTurnRequest {
	
	private String requestString;
	private String username;
	private long gameId;
	private List<UnitTurn> unitTurns;
	
	public SubmitTurnRequest(Account account, long gameId, List<UnitTurn> unitTurns){
		StringBuffer requestBuffer = new StringBuffer();
		requestBuffer.append("submitturn:" + account.getName() + ":" + gameId);
		for (UnitTurn ut: unitTurns){
			requestBuffer.append(":" + ut.getUnitId() + ";");
			
			requestBuffer.append("move");
			requestBuffer.append("," + ut.getDestination());
			
			requestBuffer.append(";attack," + ut.getAttackDirection());
		}
		
		requestString = requestBuffer.toString();
	}
	
	public SubmitTurnRequest(String requestString){
		this.requestString = requestString;
		
		// Format: submitturn:username:gameId:unitId;move,d;attack,d:unitId...etc
		String[] requestArray = requestString.split(":");
		username = requestArray[1];
		gameId = Long.parseLong(requestArray[2]);
//		String[] unitActions = new String[requestArray.length - 3];
		unitTurns = new ArrayList<UnitTurn>();
		for (int i = 3; i < requestArray.length; i++){
			String[] unitTurnArray = requestArray[i].split(";");
			int unitId = Integer.parseInt(unitTurnArray[0]);
			
			String[] moveArray = unitTurnArray[1].split(",");
			int move = Integer.parseInt(moveArray[1]);

			int attackDirection = Integer.parseInt(unitTurnArray[2].split(",")[1]);
			
			unitTurns.add(new UnitTurn(unitId, move, attackDirection));
		}

	}

	public String getUsername() {
		return username;
	}

	public long getGameId() {
		return gameId;
	}

	public List<UnitTurn> getUnitTurns() {
		return unitTurns;
	}
	
	public String getRequestString(){
		return requestString;
	}
	
	

}
