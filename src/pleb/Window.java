package pleb;



import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Window {
	private Game game;
	//private Hud hud;
	private JPanel /*hudPanel,*/ gamePanel;
	
	public Window(int w, int h, String title, Game game/*, Hud hud*/) {

		this.game = game;
		
		// COMPONENT DIMENSIONS
		setDimensions();
		game.start();
		///*GameFrame frame = */new GameFrame(game);
		

	}
	
	public void newButton(JPanel j_panel, ImageIcon btnImg) {
		
		JButton plebBtn = new JButton(btnImg);
		plebBtn.setBorderPainted(false); 
		plebBtn.setContentAreaFilled(false); 
		plebBtn.setFocusPainted(false); 
		plebBtn.setOpaque(false);
		plebBtn.setBorder(null);

		
		j_panel.add(plebBtn);
		
	}
	
	public void setSubPanels() {
		gamePanel.add(game); 
		gamePanel.setBorder(null);
	}
	
	public void setPanelButtons() {

	}
	
	public void setDimensions() {
		game.setSize(Config.WINDOW_WH[0], Config.WINDOW_WH[1]);
	}
}
