package tacticalboardgame.server;

public class PseudoGameManager {
	
	private static GameManager gameManager = null;
	
	private PseudoGameManager(){
		
	}

	public static GameManager getInstance() {
		if (gameManager == null){
			gameManager = InpersistentGameManager.getInstance();
		}
		return gameManager;
	}

}
