package tacticalboardgame.client;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Menu {

	private List<Button> buttons;

	public Menu() {
		buttons = new ArrayList<Button>();
	}

	protected void addButton(String name, String text) {

		Button button = new Button(200, 30 * buttons.size() + 100, name, text);
		buttons.add(button);

	}
	
	protected void addButton(int x, int y, String name, String text){
		Button button = new Button(x, y, name, text);
		buttons.add(button);
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		for (Button button : buttons) {
			button.Render(container, g);
		}
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float xMouse = input.getMouseX();
			float yMouse = input.getMouseY();

			for (Button button : buttons) {
				if (button.getArea().contains(xMouse, yMouse)) {
					handleButton(button.getName());
				}
			}
		}
	}
	
	protected void checkButtons(float xMouse, float yMouse){
		for (Button button : buttons) {
			if (button.getArea().contains(xMouse, yMouse)) {
				handleButton(button.getName());
			}
		}
	}

	protected void handleButton(String name) {
		
		
	}

}
