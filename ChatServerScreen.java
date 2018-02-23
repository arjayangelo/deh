import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ChatServerScreen extends JFrame implements ActionListener{

	ChatServer cs;

	JLabel lblServer = new JLabel("SERVER");
	JScrollPane scrollPane = new JScrollPane();
	JTextArea taChatHistory = new JTextArea();
	JTextField tfMessage = new JTextField();
	JButton btnSend = new JButton("Send");
	private final JButton btnClear = new JButton("Clear");
	
	public ChatServerScreen(ChatServer cs) {
		this.cs = cs;
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SERVER");
		taChatHistory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		scrollPane.setViewportView(taChatHistory);
		tfMessage.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfMessage.setColumns(10);

		getContentPane().setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		lblServer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		getContentPane().add(lblServer, "flowx,cell 0 0");
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnClear.setBackground(new Color(220, 220, 220));
		
		getContentPane().add(btnClear, "cell 1 0");
		getContentPane().add(scrollPane, "cell 0 1 2 1,grow");
		getContentPane().add(tfMessage, "flowx,cell 0 2,grow");
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSend.setBackground(new Color(0, 128, 0));
		btnSend.setForeground(new Color(255, 255, 255));
		getContentPane().add(btnSend, "cell 1 2,growy");
		
		btnClear.addActionListener(this);
		btnSend.addActionListener(this);

		this.setVisible(true);
	}
	
	public String getChatHistory() {
		return taChatHistory.getText();
	}
	
	public void setChatHistory(String s) {
		taChatHistory.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnClear)) {
			setChatHistory("");
		}
		else if(e.getSource().equals(btnSend)) {
			String message = "SERVER: " + tfMessage.getText(); 
			cs.broadcast(message);
			print(message);
			
			tfMessage.setText("");
		}
	}
	
	public void print(String text) {
		if(getChatHistory().equals("")) {
			setChatHistory(getChatHistory() + text);			
		}
		else {
			setChatHistory(getChatHistory() + "\n" + text);
		}
	}
}