package tacticalboardgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tacticalboardgame.model.Player;

/**
 * A server that continually listen for incoming requests.
 * As soon as a request is recieved it is handed to a new instance
 * of the RequestsManager-class. This class is started in a new 
 * thread.
 * @author malik
 *
 */
public class Server implements Runnable {
	
	private int port;

	public Server(int port) {
		this.port = port;
		GameManager gameManager = PseudoGameManager.getInstance();
		Player p1 = new Player("malik");
		Player p2 = new Player("simon");
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		gameManager.startNewGame(players);
	}
	
	public Server(){
	    this(5003);
	}

	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println();
				Thread t = new Thread(new RequestsManager(socket));
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param args First argument is the port to use. 
	 *             If no argument is given port 5003 is used.
	 */
	public static void main(String[] args){
		System.out.println(Arrays.toString(args));
		Server server;
		if (args.length > 0) {
			server = new Server(Integer.parseInt(args[0]));	
		} else {
			server = new Server(5003);
		}
		server.run();
	}

}
