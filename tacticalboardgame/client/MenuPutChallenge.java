package tacticalboardgame.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

public class MenuPutChallenge extends Menu{
	
	private TextField field;
	
	public MenuPutChallenge(){
		super();
		addButton("dummy", "");
		addButton("put", "Put challenge");
		field = new TextField(ClientGUI.container,
				ClientGUI.container.getDefaultFont(), 200, 100, 200, 25);
	}
	
	@Override
	protected void handleButton(String name) {
		if(name.equals("login")){
			ClientGUI.gui.putChallenge(field.getText());
			ClientGUI.gui.gotoMenu(new MenuMain());
		}
		
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
	throws SlickException {
		super.render(container, g);
		field.render(container, g);
	}

}
