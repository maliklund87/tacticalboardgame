package tacticalboardgame.client;

import java.util.ArrayList;
import java.util.List;

import tacticalboardgame.model.Tile;
import tacticalboardgame.model.Unit;
import tacticalboardgame.model.UnitTurn;

public class UnitTurnPreview extends UnitTurn{

	
	public UnitTurnPreview(Unit unit){
		super();
		this.unitId = unit.getId();
		this.unit = unit;
		path = new ArrayList<Tile>();
	}

	private List<Tile> path;
	private Unit unit;
	private boolean isAttacking = false;
	
	
	public List<Tile> getPath() {
		return path;
	}
	
	public void setPath(List<Tile> path){
		this.path = path;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	
	public Tile getLatest(){
		Tile t;
		if (path.size() > 0){
			t = path.get(0);
		} else {
			t = null;
		}
		return t;
	}
	
	@Override
	public int getDestination() {
		System.out.println(path.size()-1);
		int destination = path.get(path.size() - 1).getId();
		
		return destination;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
}
