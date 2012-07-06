package tacticalboardgame.client;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
	
	private static Map<String, Image> images = new HashMap<String, Image>();
	
	private ImageLoader(){
		
	}
	
	/**
	 * Load an image located at the given URL. URL can be relative.
	 * An image will only be loaded once, regardless of how many times
	 * this method is called.
	 * @param url
	 * @return
	 */
	public static Image loadImage(String url){
		Image img = null;
		if (images.containsKey(url)){
			img = images.get(url);
		} else {
			try {
				img = new Image(url);
				images.put(url, img);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		return img;
	}
}
