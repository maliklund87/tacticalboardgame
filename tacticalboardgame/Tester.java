package tacticalboardgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import tacticalboardgame.server.Server;

public class Tester {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws java.io.IOException
	 */
	
	
	public static void main(String[] args) throws InterruptedException,
			IOException {
		Server server = new Server(5003);

		Thread serverThread = new Thread(server);
		serverThread.start();
		
		Scanner scanner = new Scanner(System.in);
		
		String input = "";
		boolean dontStop = true;
		while (dontStop){
			input = scanner.nextLine();
			if (input.equals("exit")){
				dontStop = false;
			} else {
				System.out.println(sendRequest(input));
			}
		}
	}
	


	private static String sendRequest(String request) {
		String result = null;
		try {
			Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 5003);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			out.println(request);
			out.flush();
			
			result = in.readLine();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
