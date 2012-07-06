package tacticalboardgame.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import tacticalboardgame.model.Account;
import tacticalboardgame.netutil.GetGridResponse;
import tacticalboardgame.netutil.GetMyCurrentGamesRequest;
import tacticalboardgame.netutil.GetMyCurrentGamesResponse;
import tacticalboardgame.netutil.SubmitTurnRequest;

public class Client {

	private String serverIp;
	private int serverPort;
	private Account account;

	public Client() {
		setServerIp("localhost");
		setServerPort(5003);
	}
	
	public void setAccount(Account account){
		this.account = account;
	}

	public List<CurrentGameInfo> getMyCurrentGames() {
		GetMyCurrentGamesResponse response = null;
		try {
			Socket socket = new Socket(InetAddress.getByName(getServerIp()), getServerPort());
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			GetMyCurrentGamesRequest request = new GetMyCurrentGamesRequest(
					account);
			out.println(request.getRequestString());
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String result = in.readLine();

			response = new GetMyCurrentGamesResponse(result);

			System.out.println(result);

			out.close();

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.getCurrentGames();
	}
	
	public GetGridResponse getGrid(String gameID){
		GetGridResponse ggr = null;
		try {
			Socket socket = new Socket(InetAddress.getByName(getServerIp()), getServerPort());
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			out.println("getgrid:" + account.getName() + ":" + gameID);
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String result = in.readLine();


			ggr = new GetGridResponse(result);

			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ggr;
	}
	
	public void submitTurn(SubmitTurnRequest submitTurn){
		try {
			Socket socket = new Socket(InetAddress.getByName(getServerIp()), getServerPort());
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			out.println(submitTurn.getRequestString());
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String result = in.readLine();

			System.out.println(result);

			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
}
