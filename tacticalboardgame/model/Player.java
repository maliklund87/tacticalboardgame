package tacticalboardgame.model;

import java.util.ArrayList;
import java.util.List;


/**
 * The player associated with a single game (i.e. it is
 * not the same as a "user"). This object only exists within
 * a single game.
 * @author Malik
 *
 */
public class Player {

	private String username;
	private List<Unit> units;

	public Player(String username) {
		this.username = username;
		units = new ArrayList<Unit>();
	}

	public String getUsername() {
		return username;
	}
	
	public void addUnit(Unit unit){
		units.add(unit);
		unit.setPlayer(this);
	}

	public List<Unit> getUnits() {
		return new ArrayList<Unit>(units);
	}

}
