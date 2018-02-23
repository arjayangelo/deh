import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
 
public class ChatServer {
	private ChatServerScreen css;
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
 
    public ChatServer(int port) {
        this.port = port;
        this.css = new ChatServerScreen(this);
    }
 
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            print("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                //print("New user connected.");
                
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
 
        } catch (IOException ex) {
            print("Error in the server: " + ex.getMessage()); 
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        ChatServer server = new ChatServer(2000);
        server.execute();
    }
 
    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    /*
     * Broadcasting without excluding sender
     */
    void broadcast(String message) {
        for (UserThread aUser : userThreads) {
            aUser.sendMessage(message);
        }
    }
 
    /**
     * Stores username of the newly connected client.
     */
    void addUserName(String userName) {
        userNames.add(userName);
        //print("#LOGIN:  " + userName);
        System.out.println("LI: USERS ARE NOW: " + userNames.toString());
    }
 
    /**
     * When a client is disconnected, removes the associated username and UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
        	boolean removed2 = userThreads.remove(aUser);
        	/*if(removed2) {
        		print("#LOGOUT: " + userName);
        	}*/
        }
        System.out.println("LO: USERS ARE NOW: " + userNames.toString());
    }
 
    Set<String> getUserNames() {
        return this.userNames;
    }
 
    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
    
	public void print(String text) {
		if(css.getChatHistory().equals("")) {
			css.setChatHistory(css.getChatHistory() + text);
		}
		else {
			css.setChatHistory(css.getChatHistory() + "\n" + text);
		}
	}
}