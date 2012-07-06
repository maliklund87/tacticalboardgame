package tacticalboardgame.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
	/* A unique id */
	private long gameId;

	private List<Player> players;
	private int currentPlayer;

	private TileType[][] gridMap;
	private int[][] idMap;
	private Tile[] grid;

	/* A default grid map that is used for now */
	public static TileType[][] DEFAULT_GRID_MAP = {
			// 0 1 2 3 4 5 6 7 8 9 10 11
			/* 0 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 1 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 2 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 3 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 4 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 5 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 6 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 7 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 8 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 9 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 10 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 11 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 12 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE },
			/* 13 */{ TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE, TileType.TILE, TileType.TILE, TileType.TILE,
					TileType.TILE } };

	public Game(TileType[][] gridMap, List<Player> players, long id) {
		this.players = players;
		currentPlayer = 0;
		gameId = id;

		this.gridMap = gridMap;

		// determine number of tiles needed
		int counter = 0;
		idMap = new int[gridMap.length][gridMap[0].length];
		for (int i = 0; i < gridMap.length; i++) {
			for (int j = 0; j < gridMap[i].length; j++) {
				if (gridMap[i][j].equals(TileType.TILE)) {
					idMap[i][j] = counter;
					counter++;
				} else {
					idMap[i][j] = -1;
				}
			}
		}

		// create tiles
		grid = new Tile[counter];
		for (int i = 0; i < grid.length; i++) {
			grid[i] = new Tile(i);
		}

		// create edges
		for (int i = 0; i < idMap.length; i++) {
			for (int j = 0; j < idMap[i].length; j++) {
				if (idMap[i][j] != -1) {
					Tile currentTile = grid[idMap[i][j]];

					// tile above
					if ((j + 1) < idMap[i].length && idMap[i][j + 1] != -1) {
						currentTile.addAdjacentTile(grid[idMap[i][j + 1]]);
					}

					// tile below
					if ((j - 1) >= 0 && idMap[i][j - 1] != -1) {
						currentTile.addAdjacentTile(grid[idMap[i][j - 1]]);
					}

					// depending on column an offset might be needed
					int offset = i % 2 == 0 ? -1 : 0;

					// tiles to the left
					if ((i - 1) >= 0) {
						// lower left
						int left = j + offset;
						if (left >= 0 && idMap[i - 1][left] != -1) {
							currentTile
									.addAdjacentTile(grid[idMap[i - 1][left]]);
						}

						// upper left
						left++;
						if (left < idMap[i].length && idMap[i - 1][left] != -1) {
							currentTile
									.addAdjacentTile(grid[idMap[i - 1][left]]);
						}
					}

					// tiles to the right
					if ((i + 1) < idMap.length) {
						// lower right
						int right = j + offset;
						if (right >= 0 && idMap[i + 1][right] != -1) {
							currentTile
									.addAdjacentTile(grid[idMap[i + 1][right]]);
						}

						// upper right
						right++;
						if (right < idMap[i].length
								&& idMap[i + 1][right] != -1) {
							currentTile
									.addAdjacentTile(grid[idMap[i + 1][right]]);
						}
					}
				}
			}
		}
	}

	public void init() {
		Unit unit1 = new Unit(1);
		grid[0].setUnit(unit1);
		players.get(0).addUnit(unit1);

		Unit unit2 = new Unit(2);
		grid[7].setUnit(unit2);
		players.get(0).addUnit(unit2);

		Unit unit3 = new Unit(3);
		grid[6].setUnit(unit3);
		players.get(1).addUnit(unit3);

		Unit unit4 = new Unit(4);
		grid[13].setUnit(unit4);
		players.get(1).addUnit(unit4);
	}

	public void update(List<Unit> units) {

		// for(Unit unit : units){
		//
		// }
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Validate that a move is correct.
	 * 
	 * @param unit
	 * @param destination
	 * @return
	 */
	public boolean validateMove(Unit unit, List<Tile> path) {
		if (unit.getPlayer() != players.get(currentPlayer)) {
			return false;
		}

		if (unit.getHitpoints() <= 0) {
			return false;
		}

		if ((path.size() - 1) > unit.getActionPoints()) {
			return false;
		}

		return true;

	}

	/**
	 * Uses a breadth-first search to find the shortest path between a start and
	 * a destination tile.
	 * 
	 * TODO: insert checks to validate start and destination
	 * 
	 * @param start
	 *            Id of the tile to start from.
	 * @param destination
	 *            Id of the destination tile.
	 * @return
	 */
	public List<Tile> shortestPath(int start, int destination) {
		// BFS
		ArrayList<ArrayList<Node>> pathList = new ArrayList<ArrayList<Node>>();
		boolean[] explored = new boolean[grid.length];

		ArrayList<Node> startList = new ArrayList<Node>();

		startList.add(new Node(grid[start]));
		pathList.add(startList);

		// mark first tile as explored
		explored[start] = true;

		Node destinationNode = null;

		// begin breadth-first-search
		boolean done = false;
		if (start == destination){
			done = true;
			destinationNode = new Node(grid[destination]);
		}
		int level = 0;
		while (!done && pathList.get(level).size() > 0) {
			pathList.add(level + 1, new ArrayList<Node>()); // create collection
															// L_{level + 1}

			for (Node tempNode : pathList.get(level)) {
				List<Tile> companions = tempNode.getTile().getAdjacentTiles();
				for (int c = 0; c < companions.size(); c++) {
					Tile t = companions.get(c);
					if (t != null && explored[t.getId()] == false
							&& t.getUnit() == null) {
						explored[t.getId()] = true;
						Node n = new Node(t);
						n.setPrevious(tempNode);
						pathList.get(level + 1).add(n);
						if (t.getId() == destination) {
							done = true;
							destinationNode = n;
						}
					}
				}
			}

			level++;
		}

		ArrayList<Tile> path = new ArrayList<Tile>();

		if (done) {
			Node n = destinationNode;
			path.add(n.getTile());
			while (n.getPrevious() != null) {
				n = n.getPrevious();
				path.add(n.getTile());
			}
		}

		return path;
	}

	/**
	 * TODO: evaluate weather to assume a move is valid or not.
	 * 
	 * @param unit
	 * @param destination
	 * @return
	 */
	public String moveUnit(Unit unit, int start, int destination) {

		String result = null;

		List<Tile> path = shortestPath(start, destination);
		if (validateMove(unit, path)) {
			// Move unit to destination tile if no errors occurred.
			// Subtracts AP from the unit according to the distance moved.
			unit.getPosition().setUnit(null);
			grid[destination].setUnit(unit);
			unit.subtractActionPoints(path.size() - 1);
			result = "Unit " + unit.getPlayer().getUsername() + ":"
					+ unit.getId() + " succesfully moved to " + "("
					+ destination + ")";
		} else {
			// error messages
			// TODO: these conditions are checked multiple times.
			if (unit.getPlayer() != players.get(currentPlayer)) {
				result = unit.getId() + "is not the current players unit.";
			} else if (unit.getHitpoints() <= 0) {
				result = unit.getId() + " is KO'ed.";
			} else {
				result = unit.getPlayer().getUsername() + ":" + unit.getId()
						+ " does not have enough AP to complete the move";
			}
		}
		return result;
	}

	/**
	 * TODO: implement error checking on the attack id.
	 * 
	 * @param unit
	 * @param attack
	 *            The id of the tile being attacked
	 * @return
	 */
	public String attack(Unit unit, int attack) {
		String result = null;
		if (unit.getPlayer() == players.get(currentPlayer)) {
			if (unit.getActionPoints() >= 2 && unit.getHitpoints() > 0) {
				Tile attackedTile = grid[attack];
				if (attackedTile != null) {
					Unit attackedUnit = attackedTile.getUnit();
					if (attackedUnit != null) {
						// Ok, everything checks out, now attack.
						String hpDrop = "";
						Unit defender = attackedTile.getUnit();
						hpDrop += defender.getHitpoints();
						defender.dealDamage(3);
						hpDrop += " -> " + defender.getHitpoints();
						unit.subtractActionPoints(2);
						result = "Succesfully attacked "
								+ defender.getPlayer().getUsername() + ":"
								+ defender.getId() + ": " + hpDrop;
					} else {
						result = "There is no unit at (" + attack + ")";
					}
				} else {
					result = "There is no tile in that direction";
				}
			} else {
				if (unit.getActionPoints() < 2) {
					result = unit.getPlayer().getUsername() + ":"
							+ unit.getId()
							+ " does not have enough AP to attack.";
				} else {
					result = unit.getId() + " is KO'ed.";
				}
			}
		} else {
			result = unit.getId() + "is not the current players unit.";
		}
		return result;
	}

	/**
	 * Checks if this game has a player with the given username.
	 * 
	 * @param username
	 * @return
	 */
	public boolean hasPlayer(String username) {
		for (Player p : players) {
			if (p.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Forwards to next turn.
	 * 
	 * Increments AP by one for all units and sets current player to the next
	 * one in the list (wrapping around if nescessary).
	 */
	public void nextTurn() {

		// increment AP by one for all units
		List<Unit> units = getUnits();
		for (Unit u : units) {
			int apBefore = u.getActionPoints();
			u.addActionPoints(1);
			System.out.println("Unit " + u.getId() + "'s AP: " + apBefore
					+ " -> " + u.getActionPoints());
		}

		// set next player as current player
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	/**
	 * Place a unit on the tile with the given id.
	 * 
	 * @param unit
	 * @param tileId
	 */
	public void putUnit(Unit unit, int tileId) {
		grid[tileId].setUnit(unit);
	}

	/**
	 * Checks if the game is over, i.e. if a player has all of his units KO'd.
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		List<Player> players = getPlayers();
		Player player = null;
		boolean lost = false;
		Iterator<Player> iter = players.iterator();
		while (!lost && iter.hasNext()) {
			player = iter.next();
			List<Unit> units = player.getUnits();
			int unitsKOd = 0;
			for (Unit u : units) {
				if (u.getHitpoints() <= 0) {
					unitsKOd++;
				}
			}
			if (unitsKOd == units.size()) {
				lost = true;
			}
		}
		return lost;
	}

	// ----- GETTERS AND SETTERS

	/**
	 * Get the id of this game.
	 * 
	 * @return
	 */
	public long getId() {
		return gameId;
	}

	public Tile[] getGrid() {
		return grid;
	}

	public int[][] getIdMap() {
		return idMap;
	}

	/**
	 * Get a list of units for all players in the game.
	 * 
	 * @return
	 */
	public List<Unit> getUnits() {
		List<Unit> allUnits = new ArrayList<Unit>();
		for (Player p : players) {
			allUnits.addAll(p.getUnits());
		}
		return allUnits;
	}

	/**
	 * Returns a unit with the given ID, if one exists.
	 * 
	 * @param unitId
	 * @return
	 */
	public Unit getUnit(int unitId) {

		List<Unit> allUnits = getUnits();

		Unit unit = null;
		Iterator<Unit> iter = allUnits.iterator();
		while (unit == null && iter.hasNext()) {
			unit = iter.next();
			if (!(unit.getId() == unitId)) {
				unit = null;
			}
		}

		return unit;
	}

	/**
	 * Get a tile with the given id.
	 * 
	 * @param id
	 * @return
	 */
	public Tile getTile(int id) {
		return grid[id];
	}

	/**
	 * Set the player who has the turn.
	 * 
	 * @param player
	 */
	public void setCurrentPlayer(Player player) {
		for (Player p : players) {
			if (p == player) {
				currentPlayer = players.indexOf(p);
			}
		}
	}

	/**
	 * Get the player who has the turn.
	 * 
	 * @return
	 */
	public Player getCurrentPlayerTurn() {
		return players.get(currentPlayer);
	}

	/**
	 * Get a description of this game.
	 * 
	 * @return
	 */
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	// ----- PRIVATE CLASSES
	/**
	 * Effectively 'decorates' a Tile with a link to the previous tile in a
	 * breadth-first search
	 * 
	 * @author Malik
	 * 
	 */
	private class Node {

		private Tile t;
		private boolean explored;
		private Node previous;

		public Node(Tile t) {
			this.t = t;
			setExplored(false);
		}

		public boolean isExplored() {
			return explored;
		}

		public void setExplored(boolean explored) {
			this.explored = explored;
		}

		public Tile getTile() {
			return t;
		}

		public Node getPrevious() {
			return previous;
		}

		public void setPrevious(Node previous) {
			this.previous = previous;
		}

		public String toString() {
			return t.toString();
		}
	}
}
