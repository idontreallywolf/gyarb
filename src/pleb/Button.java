package pleb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;


public class Button extends JButton implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	private int width, height, x, y, border_radius;
	private String btnText;
	private Font font;
	private Color fontColor, btnColor, hoverColor;
	private boolean mouseEntered, mouseClicked, mouseReleased;

	
	public Button(int x, int y, int width, int height, String btnText, int borderRadius, Color defaultColor, Color hoverColor, Color fontColor) {
		// Default width
		if(width <= 0)
			width = 100;
		
		// Default height		
		if(height <= 0)
			height = 30;
		
		setBounds(x, y, width, height);
		addMouseListener(this);
		setContentAreaFilled(false);
		setBackground(defaultColor);
		setForeground(fontColor);
        
        setBorderPainted(true);
        setFont(new Font("Monospaced", Font.BOLD, 15));
        setText(btnText);
        setVisible(true);
		
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
		
		super.setPreferredSize(new Dimension(this.width, this.height));

	}
	
	
	public void update() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
				
		g2.setPaint(
				new GradientPaint(
						this.width, this.height, new Color(221,221,221), 
						this.width, this.height/(this.mouseEntered ? 2:5), Color.WHITE
				)
		);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.border_radius, this.border_radius);
		
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		
		g2.drawString(getText(), getWidth()/getText().length(), getY()+getHeight()/2);
		g2.dispose();
}

	private void save() 	{ }
	private void close() 	{ Game.killGame = true; }
	private void pause() 	{ Game.pauseGame = true; }
	private void resume() 	{ Game.pauseGame = false; }
	private void editMode() { Game.editorMode = true; }

	@Override
	public void mouseClicked(MouseEvent e) { 
		this.mouseClicked = true;
		
			 if(this.btnText.equals("Quit")) 	close();
		else if(this.btnText.equals("Save")) 	save();
		else if(this.btnText.equals("Pause"))	pause();
		else if(this.btnText.equals("Resume"))	resume();
		else if(this.btnText.equals("edit")) 	editMode();
	}

	@Override
	public void mousePressed(MouseEvent e) { 
		this.mouseReleased = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) { 
		this.mouseReleased = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.mouseEntered = true;
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.mouseEntered = false;
		this.repaint();
	}



	
}