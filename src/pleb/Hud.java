package pleb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

public class Hud {
	
	private ArrayList<Button> Buttons;
	public JList<ArrayList<Button>> J_list;
	public JPanel hudPanel;
	
	public Hud() {
		System.out.println("Hud created");
		init();
	}
		
	private void init() {
		hudPanel = new JPanel();
		J_list = new JList<ArrayList<Button>>();
		
		Buttons = new ArrayList<Button>();
		Buttons.add(new Button(10, 	10, 100, 40, "Save", 	10, new Color(255,126,126), new Color(255,78,78),Color.WHITE));
		Buttons.add(new Button(115, 10, 100, 40, "Pause", 	10, new Color(255,126,126), new Color(255,78,78),Color.WHITE));
		Buttons.add(new Button(220, 10, 100, 40, "Quit", 	10, new Color(255,126,126), new Color(255,78,78),Color.WHITE));
		Buttons.add(new Button(Config.General.WINDOW_WH[0]-110, 10, 100, 40, "Settings", 10, new Color(255,126,126), new Color(255,78,78),Color.WHITE));
		
		hudPanel.add(J_list);
		
	}
	
	public void update() {
		for(Button b: Buttons)
			b.update();
		
	}
	
	public void render(Graphics2D g) {
		for(Button b: Buttons) {
			b.render(g);
		}
		
	
	}
	
	public JPanel getPanel() {
		return this.hudPanel;
	}
	

	
	/**
	  	Custom Buttons
	 **/
	private class Button extends JComponent implements MouseListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int width, height, x, y, border_radius;
		private String btnText;
		private Font font;
		private Color fontColor;
		/*
		 *  Blue 	= (126,172,255)
		 *  Red 	= (255,126,126)
		 *  Green	= (179,255,126)
		 *  Purple	= (243,126,255) 
		 */
		private Color btnColor;
		private Color hoverColor;
		private boolean mouseEntered, mouseClicked, mouseReleased;

		
		public Button(int x, int y, int width, int height, String btnText, int borderRadius, Color defaultColor, Color hoverColor, Color fontColor) {
			super.setBounds(x, y, width, height);
			super.addMouseListener(this);
			this.width = super.getWidth();
			this.height = super.getHeight();
			this.x = super.getX();
			this.y = super.getY();
			
			this.btnText = btnText;
			this.border_radius = borderRadius;
			this.btnColor = defaultColor;
			this.font = new Font("Monospaced", Font.BOLD, 15);
			this.fontColor = fontColor;			
			
			mouseEntered = mouseClicked = mouseReleased = false;
			
		}
		
		public void update() {
			/*if((Game.lastMouse_clickX > this.x && Game.lastMouse_clickX < this.x + this.width)
			&& (Game.lastMouse_clickY > this.y && Game.lastMouse_clickY < this.y + this.height)) {
				System.out.println("Clicked on button");
			}*/
			
		}
		
		public void render(Graphics2D g) {
			paintComponent(g);
		}
		
		public void paintComponent(Graphics2D g) {
			g.setColor(this.btnColor);
			g.fillRoundRect(this.x,this.y,this.width,this.height, this.border_radius, this.border_radius);
			g.setColor(this.fontColor);
			g.setFont(this.font);
			g.drawString(this.btnText, this.x+(this.width/this.btnText.length()), this.y+25);
		}
		
		
		

		@Override
		public void mouseClicked(MouseEvent e) { this.mouseClicked = true; System.out.println("Clicked");}

		@Override
		public void mousePressed(MouseEvent e) { this.mouseReleased = false; System.out.println("Pressed");}

		@Override
		public void mouseReleased(MouseEvent e) { this.mouseReleased = true; System.out.println("Released");}

		@Override
		public void mouseEntered(MouseEvent e) { this.mouseEntered = true; System.out.println("Mouse entered"); }

		@Override
		public void mouseExited(MouseEvent e) { this.mouseEntered = false; System.out.println("exited");}

		
	}


}

