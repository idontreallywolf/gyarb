package pleb;

import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;


public class GameFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private ArrayList<Button> Btns_PanelMain, Btns_PanelPause;
	private Panel panelMain, panelPause;
	private JPanel panelMainHud, panelPauseHud, panelGame;
	private String currentPanel;

	
	/**
	 * Create the frame.
	 */
	
	
	public GameFrame(int w, int h, String title, Game game) {

		gamePanelInit(game);
		
		panelMainInit();
		panelPauseInit();
		
		setContentPane(panelMain);
		game.start();
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 576);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		currentPanel = "Main";
		
	}
	
	private void gamePanelInit(Game game) {
		this.game = game;
		this.game.setSize(Config.WINDOW_WH[0], Config.WINDOW_WH[1]);
		
		panelGame = new JPanel();
		panelGame.add(this.game);
		
	}
	
	private void panelMainInit() {
		panelMain = new Panel();
		panelMainHud = new JPanel();
		
		Btns_PanelMain = new ArrayList<Button>();
		Btns_PanelMain.add(new Button(0, 10, 0, 0, "Save", 		10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelMain.add(new Button(0, 10, 0, 0, "Pause", 	10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelMain.add(new Button(0, 10, 0, 0, "Quit", 		10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelMain.add(new Button(0, 10, 0, 0, "Settings", 	10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));

		for(int i = 0;i < Btns_PanelMain.size();i++)
			panelMainHud.add(Btns_PanelMain.get(i));

		panelMain.setPreferredSize(new Dimension(Config.WINDOW_WH[0], Config.WINDOW_WH[1]));
		panelMain.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelMainHud, BorderLayout.NORTH);
		panelMain.add(panelGame, BorderLayout.SOUTH);
	}
	
	private void panelPauseInit() {
		panelPause = new Panel();
		panelPauseHud = new JPanel();
		
		Btns_PanelPause = new ArrayList<Button>();
		Btns_PanelPause.add(new Button(0, 10, 0, 0, "Save", 	10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelPause.add(new Button(0, 10, 0, 0, "Resume", 	10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelPause.add(new Button(0, 10, 0, 0, "Quit", 	10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		Btns_PanelPause.add(new Button(0, 10, 0, 0, "Settings", 10, new Color(255,126,126), new Color(255,78,78),Color.BLACK));
		
		for(int i = 0;i < Btns_PanelPause.size();i++)
			panelPauseHud.add(Btns_PanelPause.get(i));
		
		panelPause.setPreferredSize(new Dimension(Config.WINDOW_WH[0], Config.WINDOW_WH[1]));
		panelPause.setLayout(new BorderLayout(0, 0));
		panelPause.add(panelPauseHud, BorderLayout.NORTH);
		//panelPause.add(panelGame, BorderLayout.SOUTH);
		
	}
	
	public void update() {
		if(Game.pauseGame) {
			if(getCurrentPanel() != "Pause") 
				changePanelTo("Pause");
		} else {
			if(getCurrentPanel() != "Main") 
				changePanelTo("Main");
		}
		
		
		
	
	}
	
	public String getCurrentPanel() {
		return this.currentPanel;
	}
	
	public void changePanelTo(String panelID) {
		/*Panel contentPane = (Panel) getContentPane();
		*/
		
		switch(panelID) {
			case "Pause": 
				this.currentPanel = "Pause"; 
				setContentPane(panelPause);
				
				break;
			case "Main": 
				this.currentPanel = "Main"; 
				setContentPane(panelMain); 
			break;
		}

		//contentPane.revalidate(); 
		//contentPane.repaint();
		validate();
		repaint();
	}

	protected void dispatchEvent(WindowEvent windowEvent) {
		// TODO Auto-generated method stub
		
	}
}
