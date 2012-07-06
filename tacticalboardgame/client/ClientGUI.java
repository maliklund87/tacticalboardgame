package tacticalboardgame.client;

import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import tacticalboardgame.model.Account;
import tacticalboardgame.model.Game;
import tacticalboardgame.netutil.GetGridResponse;

public class ClientGUI extends BasicGame {

	public static ClientGUI gui;
	public static GameContainer container;

	private Client client;

	private Menu menu;

	private Account account;

	public ClientGUI(String title) {
		super(title);

	}

	public void setAccount(Account account) {
		this.account = account;
		client.setAccount(account);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		menu.render(container, g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ClientGUI.gui = this;
		ClientGUI.container = container;
		this.client = new Client();

		menu = new MenuLogin();

	}

	public List<CurrentGameInfo> getCurrentGames() {
		return client.getMyCurrentGames();
	}

	public void gotoMenu(Menu newMenu) {
		menu = newMenu;
	}

	public void kill() {
		container.exit();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		menu.update(container, delta);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(new ClientGUI("tac"));
			agc.setDisplayMode(640, 480, false);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void putChallenge(String text) {

	}

	public void startGame(String name) {
		GetGridResponse gridResponse = client.getGrid(name);
		Game game = gridResponse.getGame();
		Menu gameMenu = new MenuGameScreen(client, game, account);
		gotoMenu(gameMenu);
	}

}
