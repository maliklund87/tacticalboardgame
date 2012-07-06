package tacticalboardgame.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import tacticalboardgame.model.Account;
import tacticalboardgame.model.Game;
import tacticalboardgame.model.Tile;
import tacticalboardgame.model.Unit;
import tacticalboardgame.model.UnitTurn;
import tacticalboardgame.netutil.GetGridResponse;
import tacticalboardgame.netutil.SubmitTurnRequest;

/**
 * This class shows the actual game screen. 
 * @author Malik
 *
 */
public class MenuGameScreen extends Menu {

	private Client client;
	private Account account;
	private Game game;

	private Image red, green;

	/* used to calculate the size of the hexagonal vector graphics */
	private float sideSize;
	private float relHeight = (float) Math.sqrt(3);

	/* The vector graphics used to draw the grid */
	private Shape[] tileShapes;

	/* used to determine layout of the map */
	private int[][] idMap;

	private float xMouse, yMouse;

	private Unit selectedUnit;
	private Set<Tile> selectedUnitRange;
	/* when a unit is selected, this tile is the one the mouse hovers above */
	private Tile possibleDestinationTile;
	private List<Tile> possiblePath;

	private Map<Unit, UnitTurnPreview> unitTurns;

	public MenuGameScreen(Client client, Game game, Account account) {
		super();
		this.client = client;
		this.game = game;
		this.account = account;

		init();

		addButton(450, 400, "submit", "Submit");

		addButton(450, 450, "back", "Back");
	}

	/**
	 * Initializes everything for ready use.
	 */
	public void init() {

		/* Setup turn previews. Used to make pseudo-moves before
		 * commiting to moving. Makes it easy to cancel and change
		 * actions. 
		 */
		unitTurns = new TreeMap<Unit, UnitTurnPreview>();
		for (Unit u: game.getUnits()){
			if (u.getPlayer().getUsername().equals(account.getName())){
				UnitTurnPreview turn = new UnitTurnPreview(u);
				List<Tile> path = new ArrayList<Tile>();
				path.add(u.getPosition());
				turn.setPath(path);
				
				unitTurns.put(u, turn);
			}
		}

		sideSize = 20f;

		// TODO: A bit wasteful to load an image and then get a scaled copy.
		red = ImageLoader.loadImage("images/redplayer.png").getScaledCopy(
				(int) (2 * sideSize), (int) (sideSize * relHeight));
		green = ImageLoader.loadImage("images/greenhex.png").getScaledCopy(
				(int) (2 * sideSize), (int) (sideSize * relHeight));

		float halfHeight = (relHeight * sideSize) / 2;

		// define a hexagonal polygon
		float[] array = { sideSize * 0.5f, 0, sideSize * 1.5f, 0,
				sideSize * 2f, halfHeight, sideSize * 1.5f,
				sideSize * relHeight, sideSize * 0.5f, sideSize * relHeight, 0,
				halfHeight };
		Polygon poly = new Polygon(array);

		// place all hex'es
		tileShapes = new Shape[game.getGrid().length];
		idMap = game.getIdMap();
		float xShape, yShape;
		for (int x = 0; x < idMap.length; x++) {
			for (int y = 0; y < idMap[x].length; y++) {
				if (idMap[x][y] != -1) {
					xShape = x * 1.5f * sideSize;
					yShape = (12 * sideSize * relHeight)
							- (y * relHeight * sideSize);
					poly = new Polygon(array);
					if (x % 2 == 1) {
						yShape -= halfHeight;
					}
					poly.setLocation(xShape, yShape);
					tileShapes[idMap[x][y]] = poly;
				}
			}
		}
	}

	/**
	 * Draw everything.
	 */
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
		// draw grid
		g.setColor(Color.blue);

		// variable to the tile the mouse is above.
		int tileContainsMouse = -1;

		// draw the base grid
		// TODO: some tiles are drawn multiple times.
		for (int t = 0; t < tileShapes.length; t++) {
			Shape shape = tileShapes[t];
			g.setColor(Color.blue);
			if (shape.contains(xMouse, yMouse)) {
				tileContainsMouse = t;
			}

			g.draw(shape);
		}

		// draw units
		// TODO: look into a solution where the unit has the information about how
		//       to draw itself.
		for (Unit unit : game.getUnits()) {
			if (unit.getPlayer().getUsername().equals(account.getName())) {
				UnitTurnPreview turn = unitTurns.get(unit);
				Tile tile = turn.getLatest();
				Shape shape = tileShapes[tile.getId()];
				green.draw(shape.getMinX(), shape.getMinY());
				// highlight selected unit
				if (unit == selectedUnit) {
					g.setColor(Color.white);
					g.drawOval(shape.getMinX() + (green.getWidth() / 2) - 5,
							shape.getMinY() + (green.getHeight() / 2) - 5, 10,
							10);
				}
			} else {
				Tile tile = unit.getPosition();
				Shape shape = tileShapes[tile.getId()];
				red.draw(shape.getMinX(), shape.getMinY());
			}
		}

		// draw the distance a unit has moved
		g.setColor(Color.magenta);
		for (UnitTurnPreview turn: unitTurns.values()){
			for (Tile t: turn.getPath()){
				g.draw(tileShapes[t.getId()]);
			}
		}

		// draw the range of selected unit
		if (selectedUnit != null) {
			g.setColor(Color.darkGray);
			for (Tile t : selectedUnitRange) {
				if (t.getUnit() == null) {
					g.draw(tileShapes[t.getId()]);
				}
			}
			if (possiblePath != null && possiblePath.size() > 0) {
				g.setColor(Color.magenta);
				for (Tile t : possiblePath) {
					g.draw(tileShapes[t.getId()]);
				}
			}
		}

		// draw the hex with the mouse over it
		if (tileContainsMouse >= 0) {
			g.setColor(Color.red);
			g.draw(tileShapes[tileContainsMouse]);
		}
	}

	/**
	 * Process input and game logic.
	 */
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		// a call to the method isMousePressed apparently clears it from Input?
		boolean isMouseLeftPressed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		xMouse = input.getMouseX();
		yMouse = input.getMouseY();


		// TODO: look into the option of looking up in a table
		// rather than go through every shape.
		/* find the tile the mouse is above, if any */
		int tileUnderMouse = -1;
		for (int t = 0; t < tileShapes.length; t++) {
			if (tileShapes[t].contains(xMouse, yMouse)) {
				tileUnderMouse = t;
			}
		}

		if (isMouseLeftPressed) {
			// check if any buttons were pressed. Inherited from Menu.
			checkButtons(xMouse, yMouse);

			// check if a unit was selected
			int selectedUnitPosition = -1;
			if (selectedUnit != null){
				selectedUnitPosition = unitTurns.get(selectedUnit).getLatest().getId();	
			}
			
			if (tileUnderMouse >= 0) {
				if (selectedUnit != null
						&& selectedUnitPosition == tileUnderMouse) {
					/* deselect unit */
					selectedUnit = null;
					selectedUnitRange = null;
				} 
				else if (selectedUnit != null 
						&& selectedUnitPosition != tileUnderMouse
						&& selectedUnitRange.contains(game.getTile(tileUnderMouse))){
					/* move unit */
					UnitTurnPreview turn = unitTurns.get(selectedUnit);
					turn.setPath(possiblePath);
					possiblePath = null;
				}
				else {
					/* select unit */
					Unit unit = game.getTile(tileUnderMouse).getUnit();
					for (UnitTurnPreview turn: unitTurns.values()){
						if (turn.getLatest().getId() == tileUnderMouse){
							unit = turn.getUnit();
						}
					}
					if (unit != null
							&& unit.getPlayer().getUsername()
									.equals(account.getName())) {
						selectedUnit = unit;
						// TODO: look for alternative solution. Might be too
						// slow.
						selectedUnitRange = findTilesWithinRange(
								unit.getPosition(), unit.getActionPoints());
					}
				}
			}
		}

		// find the possible path for the selected unit.
		if (selectedUnit != null) {
			if (tileUnderMouse >= 0) {
				Tile newDestinationTile = game.getGrid()[tileUnderMouse];
				if (possibleDestinationTile == null) {
					possibleDestinationTile = newDestinationTile;
				}
				if (possibleDestinationTile.getId() != newDestinationTile
						.getId()
						&& selectedUnitRange.contains(newDestinationTile)) {
					possiblePath = game.shortestPath(selectedUnit.getPosition()
							.getId(), newDestinationTile.getId());
					possibleDestinationTile = newDestinationTile;
				}
			}

			// fix current unitturn
			UnitTurnPreview unitTurnPreview = unitTurns.get(selectedUnit);
			if (unitTurnPreview == null) {
				unitTurnPreview = new UnitTurnPreview(selectedUnit);
				unitTurns.put(selectedUnit, unitTurnPreview);
//				unitTurnPreview.addTile(selectedUnit.getPosition());
			}

		} else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

		}
	}

	/**
	 * recursive function used to determine which tiles are in range.
	 * 
	 * @param start
	 * @param range
	 * @return
	 */
	private Set<Tile> findTilesWithinRange(Tile start, int range) {
		Set<Tile> list = new TreeSet<Tile>();
		if (range == 0) {
			list.add(start);
		} else {
			list.add(start);
			List<Tile> adjacentTiles = start.getAdjacentTiles();
			for (Tile t : adjacentTiles) {
				list.addAll(findTilesWithinRange(t, range - 1));
			}
		}
		return list;
	}

	public void updateGame(String id) {
		GetGridResponse ggr = client.getGrid(id);
		game = ggr.getGame();
		unitTurns = new HashMap<Unit, UnitTurnPreview>();
	}

	private void submitTurn() {
		List<UnitTurn> unitTurnsList = new ArrayList<UnitTurn>();
		for (Unit u : unitTurns.keySet()) {
			unitTurnsList.add(unitTurns.get(u));
		}
		SubmitTurnRequest submitTurnRequest = new SubmitTurnRequest(account,
				game.getId(), unitTurnsList);
		client.submitTurn(submitTurnRequest);
	}

	@Override
	protected void handleButton(String name) {
		if (name.equals("back")) {
			ClientGUI.gui.gotoMenu(new MenuMyCurrentGames());
		} else if (name.equals("submit")) {
			submitTurn();
			updateGame("" + game.getId());
		}
	}
}
