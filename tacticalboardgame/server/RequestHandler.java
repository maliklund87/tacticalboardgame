package tacticalboardgame.server;

public abstract class RequestHandler {

	private String request = null;

	public RequestHandler(String request) {
		this.request = request;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public abstract String handle(String request);

}
