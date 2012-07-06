package tacticalboardgame.swingclient;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import tacticalboardgame.client.Client;
import tacticalboardgame.model.Account;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class ConnectPanel extends JPanel {
	private JTextField txfServerAddress;
	private JTextField txfPort;
	private JTextField txfUsername;
	private JButton btnConnect;
	
	private Admin admin;
	private SwingGui gui;
	
	private Controller controller;
	private JPasswordField passwordField;
	
	public ConnectPanel(Admin admin, SwingGui gui){
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.admin = admin;
		this.gui = gui;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel connectPanel = new JPanel();
		tabbedPane.addTab("connect", null, connectPanel, null);
		
		JLabel lblConnectToServer = new JLabel("Connect to server");
		
		JLabel lblUsername = new JLabel("Username:");
		
		txfUsername = new JTextField();
		txfUsername.setColumns(10);
		
		btnConnect = new JButton("Connect");
		
		JLabel lblPassword = new JLabel("Password:");
		
		passwordField = new JPasswordField();
		
		GroupLayout gl_connectPanel = new GroupLayout(connectPanel);
		gl_connectPanel.setHorizontalGroup(
			gl_connectPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_connectPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_connectPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnConnect)
						.addGroup(gl_connectPanel.createSequentialGroup()
							.addGroup(gl_connectPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPassword)
								.addComponent(lblUsername)
								.addComponent(lblConnectToServer))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_connectPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordField)
								.addComponent(txfUsername, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))))
					.addContainerGap(206, Short.MAX_VALUE))
		);
		gl_connectPanel.setVerticalGroup(
			gl_connectPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_connectPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConnectToServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_connectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(txfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_connectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(btnConnect)
					.addContainerGap(147, Short.MAX_VALUE))
		);
		connectPanel.setLayout(gl_connectPanel);
		
		JPanel settingsPanel = new JPanel();
		tabbedPane.addTab("server settings", null, settingsPanel, null);
		
		JLabel lblAdjustServerSetting = new JLabel("Adjust server settings");
		
		JLabel lblServerAddress = new JLabel("Server address:");
		
		txfServerAddress = new JTextField();
		txfServerAddress.setText("localhost");
		txfServerAddress.setColumns(10);
		
		JLabel lblServerPort = new JLabel("Server port:");
		
		txfPort = new JTextField();
		txfPort.setText("5003");
		txfPort.setColumns(10);
		GroupLayout gl_settingsPanel = new GroupLayout(settingsPanel);
		gl_settingsPanel.setHorizontalGroup(
			gl_settingsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_settingsPanel.createSequentialGroup()
					.addGroup(gl_settingsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_settingsPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAdjustServerSetting))
						.addGroup(gl_settingsPanel.createSequentialGroup()
							.addGap(43)
							.addGroup(gl_settingsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblServerAddress)
								.addComponent(lblServerPort))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_settingsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txfPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txfServerAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(215, Short.MAX_VALUE))
		);
		gl_settingsPanel.setVerticalGroup(
			gl_settingsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_settingsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAdjustServerSetting)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_settingsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerAddress)
						.addComponent(txfServerAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_settingsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerPort)
						.addComponent(txfPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(179, Short.MAX_VALUE))
		);
		settingsPanel.setLayout(gl_settingsPanel);

		controller = new Controller();
		btnConnect.addActionListener(controller);
	}
	
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnConnect){
				String username = txfUsername.getText();
				char[] password = passwordField.getPassword();
				String serverAddress = txfServerAddress.getText();
				int port = Integer.parseInt(txfPort.getText());
				
				admin.setUsername(username);
				admin.setPassword(password);
				admin.setServerAddress(serverAddress);
				admin.setServerPort(port);
				
				// attempt login
				
				gui.switchMenu(new AttemptLoginPanel(admin, gui));
			}
		}
		
	}
}
