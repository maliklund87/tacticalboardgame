package tacticalboardgame.server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

public class ServerController {

    private int port;
    private Server server;
    private boolean keepRunning;

    public ServerController() {
	this(5002);
    }

    public ServerController(int port) {
	this.port = port;
    }

    public void start() {
	ClassLoader loader;
	try {
	    loader = URLClassLoader.newInstance(new URL[]{new URL("file:server.jar")
	    }, getClass().getClassLoader());
	    Class<Server> serverClass = (Class<Server>) Class.forName("tacticalboardgame.server.Server", false, loader);
	    Thread serverThread = new Thread(serverClass.newInstance());
	    serverThread.start();
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    	catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ServerController c = new ServerController();
	c.start();
    }

}
