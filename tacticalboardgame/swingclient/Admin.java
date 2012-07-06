package tacticalboardgame.swingclient;

public class Admin {
    
    private String username;
    private char[] password;
    private String serverAddress;
    private int port;

    public void setUsername(String username) {
	this.username = username;
    }

    public void setPassword(char[] password) {
	this.password = password;
    }

    public void setServerAddress(String serverAddress) {
	this.serverAddress = serverAddress;
    }

    public void setServerPort(int port) {
	this.port = port;
    }

}
