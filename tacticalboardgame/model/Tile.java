package tacticalboardgame.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a single tile in the game.
 * @author Malik
 *
 */
public class Tile implements Comparable<Tile>{

    /* id is the index of this tile in the grid */
    private int id;
    
    /* the tiles that this tile is connected to */
    private Set<Tile> adjacentTiles;
    
    /* the unit currently residing on this tile */
    private Unit unit;
    
    public Tile(int id){
	this.id = id;
	adjacentTiles = new HashSet<Tile>();
	unit = null;
    }
    
    
    //----- GETTERS AND SETTERS
    
    public int getId(){
	return id;
    }
    
    
    public List<Tile> getAdjacentTiles(){
	return new ArrayList<Tile>(adjacentTiles);
    }
    
    /**
     * Add a tile that this tile is connected to.
     * @param tile
     */
    public void addAdjacentTile(Tile tile){
	adjacentTiles.add(tile);
    }

    
    public Unit getUnit() {
	return unit;
    }

    /**
     * Place a unit on this tile. If the unit given as a parameter is not
     * null the units position-attribute is set to this tile as well.
     * @param unit A Unit. Use null-value to remove unit.
     */
    public void setUnit(Unit unit) {
	this.unit = unit;
	if (unit != null) {
	    unit.setPosition(this);
	}
    }
    
    
    //----- METHODS FROM OBJECT
    
    public String toString(){
	return "Tile(" + id + ")";
    }

    @Override
    public boolean equals(Object otherObject){
    	if (otherObject == null
    		|| !(otherObject instanceof Tile)){
    		return false;
    	}
    	if (((Tile)otherObject).getId() == id){
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public int compareTo(Tile otherTile) {
	return id - otherTile.getId();
    }   
}
