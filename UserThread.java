import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
 
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
 
    String userName;
    
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
    	
    	String serverMessage;
    	
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            userName = reader.readLine();
            server.addUserName(userName);
 
            serverMessage = "NEW USER: [" + userName + "]";
            server.broadcast(serverMessage, this);
            server.print(serverMessage);
 
            String clientMessage;
 
            while(true) {
                clientMessage = reader.readLine();
                
                if(clientMessage.contains("LOGGED OUT: ")) {
                	//server.broadcast(clientMessage);
                	//server.removeUser(userName, this);
                	break;
                }
                else {
                    serverMessage = "[" + userName + "]: " + clientMessage;
                    server.print(serverMessage);
                    server.broadcast(serverMessage);                	
                }
            }
 
        } catch (IOException ex) {
            //System.out.println("Error in UserThread: " + ex.getMessage());
            //ex.printStackTrace();
        }

        server.removeUser(userName, this);
        
        try {
        	socket.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        serverMessage = "LOGGED OUT: [" + userName + "]";
        server.print(serverMessage);
        server.broadcast(serverMessage, this);
    }
 
    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("ONLINE: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
 
    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}