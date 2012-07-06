package tacticalboardgame.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import tacticalboardgame.model.Account;


public class MenuLogin extends Menu{
	
	private TextField field;
	
	public MenuLogin() {
		super();
		addButton("dummy", "");
		addButton("login", "Login");
		field = new TextField(ClientGUI.container,
				ClientGUI.container.getDefaultFont(), 200, 100, 200, 25);
	}
	
	
	@Override
	protected void handleButton(String name) {
		if(name.equals("login")){
			ClientGUI.gui.setAccount(new Account(field.getText()));
			ClientGUI.gui.gotoMenu(new MenuMain());
			field.deactivate();

		}
		
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
	throws SlickException {
		super.render(container, g);
		field.render(container, g);
	}
	
	

}
