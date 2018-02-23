import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class LoginPanel extends JPanel implements MouseListener {
	ChatClientScreen parent;
	JTextField txtUsername;
	JLabel lblWelcomeToMessenger = new JLabel("Messenger");
	JButton btnLogin = new JButton("Login");
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component verticalStrut_1 = Box.createVerticalStrut(70);

	public LoginPanel(ChatClientScreen ccs) {
		setBackground(Color.WHITE);
		this.parent = ccs;
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtUsername.setText("username");
		txtUsername.setColumns(20);
		txtUsername.addMouseListener(this);
		txtUsername.setForeground(Color.gray);
		
		setLayout(new MigLayout("", "[grow]", "[][][][][]"));
		
		add(verticalStrut_1, "cell 0 0");
		lblWelcomeToMessenger.setFont(new Font("Segoe UI Light", Font.PLAIN, 40));
		add(lblWelcomeToMessenger, "cell 0 1,alignx center");
		
		add(verticalStrut, "cell 0 2");
		add(txtUsername, "flowx,cell 0 3,alignx center,growy");
						btnLogin.setForeground(new Color(255, 255, 255));
						btnLogin.setBackground(new Color(0, 128, 0));
						btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 18));
						
								btnLogin.addActionListener(parent);
								add(btnLogin, "cell 0 4,alignx center,growy");
	}
	
	public String getUsername() {
		return txtUsername.getText();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		txtUsername.setText("");
		txtUsername.setForeground(Color.black);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}