package tacticalboardgame.model;

public class UnitTurn {
	
	protected int unitId;
	private int destination;
	private int attackDirection;
	
	public UnitTurn(int unitId, int destination, int attackDirection){
		this.unitId = unitId;
		this.destination = destination;
		this.attackDirection = attackDirection;
	}
	
	public UnitTurn(){
		this.attackDirection = -1;
	}

	public int getUnitId() {
		return unitId;
	}

	public int getDestination() {
		return destination;
	}

	public int getAttackDirection() {
		return attackDirection;
	}
	
	public void setAttackDirection(int attackDirection) {
		this.attackDirection = attackDirection;
	}
}
