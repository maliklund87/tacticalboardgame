package tacticalboardgame.server;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import tacticalboardgame.model.Game;
import tacticalboardgame.model.Tile;
import tacticalboardgame.model.Unit;
import tacticalboardgame.model.UnitTurn;
import tacticalboardgame.netutil.SubmitTurnRequest;

/**
 * TODO: 
 * @author Malik
 *
 */
public class SubmitTurnRequestHandler extends RequestHandler {

	public SubmitTurnRequestHandler(String request) {
		super(request);
	}

	@Override
	public String handle(String requestString) {
		SubmitTurnRequest request = new SubmitTurnRequest(requestString);
		
		String username = request.getUsername();
		long gameId = request.getGameId();
		
		Game game = PseudoGameManager.getInstance().getGame(gameId);
		
		String result = null;
		
		
		if (game == null){
			result = "Game does not exist.";
		}
		else if (!game.hasPlayer(username)){
			// The player isn't playing this game
			result = "Player \"" + username + "\" not found.";
		}
		else if (!game.getCurrentPlayerTurn().getUsername().equals(username)){
			// It is not this players turn
			result = "It is not " + username + "'s turn.";
		} 
		else{
			result = "";
			List<UnitTurn> unitTurns = request.getUnitTurns();
			
			// Validate each move before commiting
			boolean error = false;
			Iterator<UnitTurn> iter = unitTurns.iterator();
			String errorMessage = null;
			while (!error && iter.hasNext()){
				UnitTurn unitTurn = iter.next();
				Unit unit = game.getUnit(unitTurn.getUnitId());
				
				List<Tile> path = game.shortestPath(unit.getPosition().getId(), unitTurn.getDestination());
				if (!game.validateMove(unit, path)){
					error = true;
					errorMessage = "Failed to move " + unit.getId() + 
							" according to " + game.shortestPath(unit.getPosition().getId(), unitTurn.getDestination());
					System.out.println(errorMessage);
				}
			}
			
			
			// return error message if a move failed.
			if (error){
				result = errorMessage;
			}
			// Commit to moving each unit.
			else {
				for (int i = 0; i < unitTurns.size(); i++){
					UnitTurn unitTurn = unitTurns.get(i);
					int unitId = unitTurn.getUnitId();
					
					// move
					int move = unitTurn.getDestination();
					
					Unit unit = game.getUnit(unitId);
					result += game.moveUnit(unit, unit.getPosition().getId(), move) + ", ";
					
					// Attack
					int attack = unitTurn.getAttackDirection();
					if (attack >= 0){
						result += game.attack(unit, attack) + "; ";
					}
				}
			}
			
			// check if game is over, else advance to next turn.
			if (game.isGameOver()){
				GameManager gameManager = PseudoGameManager.getInstance();
				gameManager.endGame(game.getId());
				result += "; ===> " + " lost!";
			} 
			else if (!error) {
				// Advance to next turn if no error occurred.
				game.nextTurn();
				
				// quick and dirty
				Dao dao = Dao.getInstance();
				dao.saveGame(game);
			}
		}
		return result;
	}
}
