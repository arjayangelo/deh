import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ChatClientScreen extends JFrame implements ActionListener {

	ChatClient cc;
	
	LoginPanel lp;
	GlobalChatPanel gcp;
	
	ArrayList<String> onlineUsersList;
	
	public ChatClientScreen(ChatClient cc) {
		this.cc = cc;
		
		this.onlineUsersList = new ArrayList<String>();

		lp = new LoginPanel(this);
		this.setContentPane(lp);
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CLIENT");
		this.setVisible(true);
	}
	
	@Override //TODO
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(lp.btnLogin)) {
			gcp = new GlobalChatPanel(this);
			cc.setUserName(lp.getUsername());
			this.setContentPane(gcp);

			validate();
			repaint();
			
			cc.execute();
		}
		else if(gcp != null) {
			if(e.getSource().equals(gcp.btnLogout)) {
				cc.notifyLogout();
				cc.setUserName(null);
				onlineUsersList = new ArrayList<String>();
				this.setContentPane(lp);
			}
			else if(e.getSource().equals(gcp.btnSend)) {
				cc.sendMessage(gcp.getMessage());
				gcp.emptyMessageField();
			}			
		}
	}
	
	public void print(String text) {
		if(gcp.getChatHistory().equals("")) {
			gcp.setChatHistory(gcp.getChatHistory() + text);			
		}
		else {
			gcp.setChatHistory(gcp.getChatHistory() + "\n" + text);
		}
	}

	public void setUsernameLabel(String userName) {
		gcp.lblClient.setText("CLIENT: " + userName);
	}
	
	public void addOnlineUser(String userName) {
		this.onlineUsersList.add(userName);
		gcp.updateOnlineUsersListUI();
	}
	
	public void removeOnlineUser(String userName) {
		this.onlineUsersList.remove(userName);
		gcp.updateOnlineUsersListUI();
	}
	
	public ArrayList<String> getOnlineUsersList() {
		return onlineUsersList;
	}
}