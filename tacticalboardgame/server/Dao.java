package tacticalboardgame.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tacticalboardgame.model.Game;
import tacticalboardgame.netutil.GetGridResponse;

public class Dao {
	
	private static Dao dao;

	private Dao(){
		
	}
	
	public static Dao getInstance(){
		if (dao == null){
			dao = new Dao();
		}
		return dao;
	}
	
	public void saveGame(Game game){
		File file = new File("data/" + game.getId() + ".game");
		try {
			FileWriter writer = new FileWriter(file);
			GetGridResponse gameInfo = new GetGridResponse(game);
			String responseString = gameInfo.generateResponseString();
			System.out.println(responseString);
			writer.write(responseString);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Game getGame(long gameId) {
		File file = new File ("data/" + gameId + ".game");
		Game game = null;
		try {
			Scanner scanner = new Scanner(file);
			GetGridResponse response = new GetGridResponse(scanner.nextLine());
			game = response.getGame();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}

	public List<Game> getUserGames(String username) {
		File file = new File("data");
		File[] files = file.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith(".game");
			}
		});
		
		ArrayList<Game> userGames = new ArrayList<Game>();
		GetGridResponse response = null;
		Scanner scanner = null;
		Game game = null;
		for (File f: files){
			try {
				scanner = new Scanner(f);
				response = new GetGridResponse(scanner.nextLine());
				game = response.getGame();
				if (game.hasPlayer(username)){
					userGames.add(game);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userGames;
	}

	public void deleteGame(Game finishedGame) {
		File file = new File("data/" + finishedGame.getId() + ".game");
		file.delete();
	}
	
	
}
