package tacticalboardgame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is started with every request. It determines what
 * type of request was sent and lets an appropriate RequestHandler
 * do the work.
 * @author Malik
 * 
 */
public class RequestsManager implements Runnable {

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;

	/**
	 * 
	 * @param socket
	 *            OBS! Must not be null!
	 * @param requestHandlers
	 */
	public RequestsManager(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
		} catch (IOException e) {
			System.err.println("Could not connect to input/output streams.");
			e.printStackTrace();
		}

		try {
			String requestString = in.readLine();
			String[] request = requestString.split(":");
			System.out.println("Request: " + requestString);

			// Get RequestHandler associated with the incoming request-type
			RequestHandler handler = RequestHandlerFactory.getRequestHandler(request[0]);

			String result = null;

			if (handler != null) {
				result = handler.handle(requestString);
			} else {
				result = "Unknown request";
			}
			
			System.out.println("response -> " + result);

			out.println(result);
			out.flush();

			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
