import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
 
/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author www.codejava.net
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
 
    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            //System.out.println("Error getting input stream: " + ex.getMessage());
            client.print("Error getting input stream:" + ex.getMessage());
        	ex.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                
                if(response.contains("ONLINE: ")) {
                	int listStartIndex = response.indexOf("[") + 1;
                	for(String username : response.substring(listStartIndex, response.length() - 1).split("[\\, ]")) {
                		client.getChatClientScreen().addOnlineUser(username);
                	}
                }
                else if(response.contains("NEW USER: ")) {
                	int usernameStartIndex = response.indexOf("[") + 1;
                	String username = response.substring(usernameStartIndex, response.length() - 1);
                	client.getChatClientScreen().addOnlineUser(username);
                }
                else if(response.contains("LOGGED OUT: ")) {
                	int usernameStartIndex = response.indexOf("[") + 1;
                	String username = response.substring(usernameStartIndex, response.length() - 1);
                	client.getChatClientScreen().removeOnlineUser(username);
                }
                
                client.print(response);
 
            } catch (IOException ex) {
            	client.print("Error reading from server: " + ex.getMessage());
            	client.reconnect();
            	
            	ex.printStackTrace();
                break;
            }
        }
    }
}