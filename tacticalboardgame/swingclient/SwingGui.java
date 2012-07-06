package tacticalboardgame.swingclient;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tacticalboardgame.client.Client;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class SwingGui extends JFrame {
	
	private Admin admin;
	
	public SwingGui(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setLocation(100, 100);

		admin = new Admin();
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		switchMenu( new ConnectPanel(admin, this));
		
		pack();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingGui gui = new SwingGui();
		gui.setVisible(true);
	}

	public void switchMenu(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().invalidate();
		
		this.getContentPane().add(panel);
		this.getContentPane().validate();
//		pack();
		this.repaint();
		panel.setVisible(true);
	}

}
