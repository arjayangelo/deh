import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class ChatClient {
	private String hostname = "localhost";
	private int port = 2000;

	private ChatClientScreen ccs;
	private String userName;
	private boolean loggedIn;
    
    private ReadThread readThread;
    private WriteThread writeThread;
 
    private Socket socket;
    
    public ChatClient() {
        this.ccs = new ChatClientScreen(this);
    }
 
    public void execute() {
    	socket = null;
    	while(socket == null) {
	        try {
	            socket = new Socket(hostname, port);
	            
	            ccs.print("Connected to the chat server");
	            
	            readThread = new ReadThread(socket, this);
	            writeThread = new WriteThread(socket, this);
	            readThread.start();
	            writeThread.start();
	 
	        } catch (UnknownHostException ex) {
	        	//System.out.println("Server not found: " + ex.getMessage());
	        	//ccs.print("Server not found: " + ex.getMessage());
	        } catch (IOException ex) {
	        	//System.out.println("I/O Error: " + ex.getMessage());
	        	//ccs.print("I/O Error: " + ex.getMessage());
	        }
    	}
    }
 
    public void setUserName(String userName) {
    	this.userName = userName;
        ccs.setUsernameLabel(userName);
    }
 
    public String getUserName() {
        return this.userName;
    }
    
    public ChatClientScreen getChatClientScreen() {
    	return ccs;
    }
	
	public void print(String text) {
		ccs.print(text);
	}
	
	public void closeSocket() {
		try {
			socket.close();
			//System.out.println("SOCKET NOW CLOSED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		writeThread.send(message);
	}
	
	public void reconnect() {
		closeSocket();
		execute();
	}
	
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
    }

	public void notifyLogout() {
		writeThread.send("LOGGED OUT: " + this.userName);
	}
}