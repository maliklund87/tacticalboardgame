package tacticalboardgame.swingclient;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class AttemptLoginPanel extends JPanel {

    private final Admin admin;
    private final SwingGui gui;

    public AttemptLoginPanel(Admin admin, SwingGui gui) {
	this.admin = admin;
	this.gui = gui;
	
	setLayout(null);
	
	JLabel lblConnecting = new JLabel("Connecting...");
	lblConnecting.setBounds(180, 130, 235, 14);
	add(lblConnecting);
	
	JButton btnNewButton = new JButton("New button");
	btnNewButton.setBounds(68, 224, 89, 23);
	add(btnNewButton);
	
	JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
	rdbtnNewRadioButton.setBounds(224, 185, 109, 23);
	add(rdbtnNewRadioButton);
	this.setVisible(true);
    }
}
