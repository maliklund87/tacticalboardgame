package tacticalboardgame.client;

import java.util.List;

public class MenuMyCurrentGames extends Menu{

	public MenuMyCurrentGames() {
		super();
		List<CurrentGameInfo> currentGames = ClientGUI.gui.getCurrentGames();
		
		for(CurrentGameInfo cgi : currentGames){
			addButton("" + cgi.getGameID(), "" +cgi.getGameID());
		}
		
		addButton("back", "back");
	}
	
	@Override
	protected void handleButton(String name) {
		if(name.equals("back")){
			ClientGUI.gui.gotoMenu(new MenuMain());
		}else{
			ClientGUI.gui.startGame(name);
		}
	}
	
}
