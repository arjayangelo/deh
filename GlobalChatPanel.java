import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class GlobalChatPanel extends JPanel {

	ChatClientScreen parent;
	
	JLabel lblClient = new JLabel("Client");
	JScrollPane scrollPane = new JScrollPane();
	JTextArea taChatHistory = new JTextArea();
	JTextField tfMessage = new JTextField();
	JButton btnSend = new JButton("Send");
	JScrollPane scrollPane_1 = new JScrollPane();
	JList list = new JList();
	JButton btnLogout = new JButton("Logout");
	
	
	public GlobalChatPanel(ChatClientScreen ccs) {
		setBackground(Color.WHITE);
		this.parent = ccs;
		taChatHistory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		scrollPane.setViewportView(taChatHistory);
		tfMessage.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		tfMessage.setColumns(10);
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.setBackground(new Color(0, 128, 0));
		
		btnSend.addActionListener(parent);
		btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnLogout.setBackground(new Color(220, 220, 220));
		
		btnLogout.addActionListener(parent);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		scrollPane_1.setViewportView(list);

		this.setLayout(new MigLayout("", "[:100px:100px][grow]", "[][grow][]"));
		lblClient.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		this.add(lblClient, "flowx,cell 0 0");
		this.add(scrollPane_1, "cell 0 1 1 2,grow");
		this.add(scrollPane, "cell 1 1,grow");
		this.add(tfMessage, "flowx,cell 1 2,grow");
		this.add(btnSend, "cell 1 2,growy");
		this.add(btnLogout, "cell 1 0,alignx right");
	}
	
	public String getChatHistory() {
		return taChatHistory.getText();
	}
	
	public void setChatHistory(String s) {
		taChatHistory.setText(s);
	}
	
	public String getMessage() {
		return tfMessage.getText();
	}
	
	public void emptyMessageField() {
		tfMessage.setText("");
	}
	
	public void setUsername(String username) {
		lblClient.setText("CLIENT: " + username);
	}

	public void updateOnlineUsersListUI() {
		ArrayList<String> onlineUsersList = parent.getOnlineUsersList();
		DefaultListModel listModel = new DefaultListModel();
		for(String s : onlineUsersList) {
			listModel.addElement(s);
		}
		list.setModel(listModel);
	}
}