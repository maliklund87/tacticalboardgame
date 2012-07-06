package tacticalboardgame.client;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Button {
	
	private Shape area;
	private String text;
	private String name;
	
	
	
	public Button(int x, int y, String name, String text){
		area = new Rectangle(x,y,200,25);
		this.text = text;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public Shape getArea() {
		return area;
	}
	
	
	public void Render(GameContainer container, Graphics g)
	throws SlickException {
		g.setColor(Color.white);
		g.fillRect(area.getX(), area.getY(), area.getWidth(), area.getHeight());

		g.setColor(Color.black);
		g.drawString(text, area.getX()+10, area.getY()+5);
	}

}
