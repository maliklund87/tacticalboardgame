package tacticalboardgame.client;

public class MenuMain extends Menu {

	public MenuMain() {
		super();
		addButton("getMyCurrentGames", "Display current games");
		addButton("challenges", "Put a new Challenge");
	}
	
	@Override
	protected void handleButton(String name) {
		if(name.equals("getMyCurrentGames")){
			ClientGUI.gui.gotoMenu(new MenuMyCurrentGames());
		}else if(name.equals("challenges")){
			ClientGUI.gui.gotoMenu(new MenuPutChallenge());
		}
		
	}

}
