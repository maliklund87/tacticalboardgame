package tacticalboardgame.tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import tacticalboardgame.model.Player;
import tacticalboardgame.model.Tile;

public class BFSGameTest extends BasicGame {

    private List<Player> players;
    private int currentPlayer;
    private Tile[] tiles;
    
    private Tile selectedTile;
    private ArrayList<Tile> selectedPath;

    private float sideSize;
    private float relHeight = (float) Math.sqrt(3);
    
    private float xMouse = 0, yMouse = 0;

    private Shape[] tileShapes;

    public BFSGameTest(String title) {
	super(title);

    }
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
	TileType[][] gridMap = createGridMap();

	// determine number of tiles needed
	int counter = 0;
	int[][] numberMap = new int[gridMap.length][gridMap[0].length];
	for (int i = 0; i < gridMap.length; i++) {
	    for (int j = 0; j < gridMap[i].length; j++) {
		if (gridMap[i][j].equals(TileType.TILE)) {
		    numberMap[i][j] = counter;
		    counter++;
		} else {
		    numberMap[i][j] = -1;
		}
	    }
	}

	// create tiles
	tiles = new Tile[counter];
	for (int i = 0; i < tiles.length; i++) {
	    tiles[i] = new Tile(i);
	}

	// create edges
	for (int i = 0; i < numberMap.length; i++) {
	    for (int j = 0; j < numberMap[i].length; j++) {
		if (numberMap[i][j] != -1) {
		    Tile currentTile = tiles[numberMap[i][j]];

		    // tile above
		    if ((j + 1) < numberMap[i].length
			    && numberMap[i][j + 1] != -1) {
			currentTile.addAdjacentTile(tiles[numberMap[i][j + 1]]);
		    }

		    // tile below
		    if ((j - 1) >= 0 && numberMap[i][j - 1] != -1) {
			currentTile.addAdjacentTile(tiles[numberMap[i][j - 1]]);
		    }

		    // depending on column an offset might be needed
		    int offset = i % 2 == 0 ? -1 : 0;

		    // tiles to the left
		    if ((i - 1) >= 0) {
			// lower left
			int left = j + offset;
			if (left >= 0 && numberMap[i - 1][left] != -1) {
			    currentTile
				    .addAdjacentTile(tiles[numberMap[i - 1][left]]);
			}

			// upper left
			left++;
			if (left < numberMap[i].length
				&& numberMap[i - 1][left] != -1) {
			    currentTile
				    .addAdjacentTile(tiles[numberMap[i - 1][left]]);
			}
		    }

		    // tiles to the right
		    if ((i + 1) < numberMap.length) {
			// lower right
			int right = j + offset;
			if (right >= 0 && numberMap[i + 1][right] != -1) {
			    currentTile
				    .addAdjacentTile(tiles[numberMap[i + 1][right]]);
			}

			// upper right
			right++;
			if (right < numberMap[i].length
				&& numberMap[i + 1][right] != -1) {
			    currentTile
				    .addAdjacentTile(tiles[numberMap[i + 1][right]]);
			}
		    }
		}
	    }
	}

	/* --- setup graphics --- */
	
	sideSize = 20f;
	float halfHeight = (relHeight * sideSize) / 2;

	// define a hexagonal polygon
	float[] array = { sideSize * 0.5f, 0, sideSize * 1.5f, 0,
		sideSize * 2f, halfHeight, sideSize * 1.5f,
		sideSize * relHeight, sideSize * 0.5f, sideSize * relHeight, 0,
		halfHeight };
	Polygon poly = new Polygon(array);

	tileShapes = new Shape[tiles.length];
	float xShape, yShape;
	for (int x = 0; x < numberMap.length; x++) {
	    for (int y = 0; y < numberMap[x].length; y++) {
		if (numberMap[x][y] != -1) {
		    xShape = x * 1.5f * sideSize;
		    yShape = (12 * sideSize * relHeight) - (y * relHeight * sideSize);
		    poly = new Polygon(array);
		    if (x % 2 == 1){
			yShape -= halfHeight;
		    }
		    poly.setLocation(xShape, yShape);
		    tileShapes[numberMap[x][y]] = poly;
		}
	    }
	}
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
	
	int tileContainsMouse = -1;
	
	for (int t = 0; t < tileShapes.length; t++){
	    Shape shape = tileShapes[t];
	    g.setColor(Color.blue);
	    if (shape.contains(xMouse, yMouse)){
		tileContainsMouse = t;
	    }
	    
	    g.draw(shape);
	}
	
	if (tileContainsMouse >= 0){
	    g.setColor(Color.red);
	    g.draw(tileShapes[tileContainsMouse]);
	}
	
	if (selectedTile != null){
	    g.setColor(Color.white);
	    g.draw(tileShapes[selectedTile.getId()]);
	}
	
	if (selectedPath != null && selectedPath.size() > 0){
	    g.setColor(Color.magenta);
	    for (Tile t: selectedPath){
		g.draw(tileShapes[t.getId()]);
	    }
	}
    }


    @Override
    public void update(GameContainer container, int delta) throws SlickException {
	Input input = container.getInput();
	boolean isMouseLeftPressed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
	xMouse = input.getMouseX();
	yMouse = input.getMouseY();
	
	if (isMouseLeftPressed){
	    Tile tile = null;
	    int i = 0;
	    while (tile == null && i < tileShapes.length){
		Shape s = tileShapes[i];
		if (s.contains(xMouse, yMouse)){
		    tile = tiles[i];
		} else {
		    i++;
		}
	    }
	    
	    if (tile != null){
		if (selectedTile == null){
		    selectedTile = tile;  
		    selectedPath = null;
		}
		else if (tile == selectedTile){
		    // cancel selection
		    selectedTile = null;
		} 
		else {
		    // BFS
		    ArrayList<ArrayList<Node>> pathList = new ArrayList<ArrayList<Node>>();
		    boolean[] explored = new boolean[tileShapes.length];
		    
		    ArrayList<Node> startList = new ArrayList<Node>();
		    
		    startList.add(new Node(selectedTile));
		    pathList.add(startList);
		    
		    // mark first tile as explored
		    explored[selectedTile.getId()] = true;
		    
		    Node destination = null;
		    
		    // begin breadth-first-search
		    boolean done = false;
		    int level = 0;
		    while(!done && pathList.get(level).size() > 0){
			pathList.add(level + 1, new ArrayList<Node>()); // create collection L_{level + 1}

			for (Node tempNode: pathList.get(level)){
			    List<Tile> companions = tempNode.getTile().getAdjacentTiles();
			    for (int c = 0; c < companions.size(); c++){
				Tile t = companions.get(c);
				if (t != null && explored[t.getId()] == false && t.getUnit() == null){
				    explored[t.getId()] = true;
				    Node n = new Node(t);
				    n.setPrevious(tempNode);
				    pathList.get(level+1).add(n);
				    if (t.getId() == tile.getId()){
					done = true;
					destination = n;
				    }
				}
			    }
			}
			
			level++;
		    }
		    
		    if (done){
			selectedPath = new ArrayList<Tile>();
			Node n = destination;
			selectedPath.add(n.getTile());
			while (n.getPrevious() != null){
			    n = n.getPrevious();
			    selectedPath.add(n.getTile());
			}
			selectedTile = null;
		    }
		    
		}
	    }
	}
	
    }

    private void printTiles() {
	for (int i = 0; i < tiles.length; i++) {
	    System.out.println(tiles[i] + ": " + tiles[i].getAdjacentTiles());
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	BFSGameTest test = new BFSGameTest("Test");
//	test.printTiles();
	
	try {
	    AppGameContainer container = new AppGameContainer(test);
	    container.setDisplayMode(640, 480, false);
	    container.start();
	} catch (SlickException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private TileType[][] createGridMap() {

	TileType voidTile = TileType.VOID, tileTile = TileType.TILE;

	TileType[][] gridMap = { { voidTile, tileTile, tileTile, voidTile },
		{ tileTile, tileTile, tileTile, tileTile },
		{ voidTile, tileTile, tileTile, tileTile },
		{ voidTile, tileTile, tileTile, voidTile },
		{ voidTile, voidTile, tileTile, voidTile } };
	return gridMap;
    }

    private enum TileType {
	VOID, TILE
    }

    
    private class Node{
	
	private Tile t;
	private boolean explored;
	private Node previous;
	
	public Node(Tile t){
	    this.t = t;
	    setExplored(false);
	}

	public boolean isExplored() {
	    return explored;
	}

	public void setExplored(boolean explored) {
	    this.explored = explored;
	}
	
	public Tile getTile(){
	    return t;
	}

	public Node getPrevious() {
	    return previous;
	}

	public void setPrevious(Node previous) {
	    this.previous = previous;
	}
	
	public String toString(){
	    return t.toString();
	}
    }
}
