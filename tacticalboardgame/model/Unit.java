package tacticalboardgame.model;


public class Unit implements Comparable<Unit>{

	private int unitId;
	private Tile position;
	private Player player;
	
	private int hitpoints;
	private int actionPoints;

	public Unit(int id) {
		this.unitId = id;
		hitpoints = 10;
		actionPoints = 7;
	}

	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile position) {
		this.position = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;		
	}

	public int getId() {
		return unitId;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}
	
	public void dealDamage(int damage){
		hitpoints = hitpoints - damage;
		if (hitpoints < 0){
			hitpoints = 0;
		}
	}
	
	public int getActionPoints(){
		return actionPoints;
	}
	
	/**
	 * Adds action points to this unit, but action points
	 * can never be higher than 7.
	 * @param ap
	 */
	public void addActionPoints(int ap){
		actionPoints = actionPoints + ap;
		if (actionPoints > 7){
			actionPoints = 7;
		}
	}
	
	/**
	 * 
	 * @param ap Must not be larger than remaining actionPoints.
	 */
	public void subtractActionPoints(int ap){
		actionPoints = actionPoints - ap;
	}

	public void setHp(int hp) {
		this.hitpoints = hp;		
	}

	public void setActionPoints(int ap) {
		this.actionPoints = ap;
	}

	@Override
	public int compareTo(Unit otherUnit) {
		return unitId - otherUnit.getId();
	}

}
