package pleb;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Block extends GameObject {
	private int type;
	
	Block(float x, float y, float w, float h, Color color, ObjectId id, boolean isEntity){
		super(x, y, w, h, id, color, isEntity);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	

	public void update(LinkedList<GameObject> object, float dt, float time) {
		
	}
	
	public void render(Graphics2D g, float dt) {
			g.setColor(getSelfColor());
			g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
			g.setColor(Config.Colors.borderColor);
			g.drawRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}

	public int getType() { return this.type; }
	
	public Rectangle getBounds() {
		if(type == Config.General.obstacle[0])
			return new Rectangle(
					(int)(x+Config.General.tilesize*0.1), 
					(int)(y+Config.General.tilesize-(Config.General.tilesize*0.2)), 
					(int)(Config.General.tilesize-(Config.General.tilesize*0.1)), 
					(int)(Config.General.tilesize*0.2)
			);
		
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle(
				(int)x, 
				(int)y, 
				(int)(width-(width*0.01f)), 
				(int)(height*0.15f)
		);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(
				(int)x,
				(int)(y+(height-0.15f)-(height*0.15f)),
				(int)(width-(width*0.01f)),
				(int)(height*0.15f)
		);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(
				(int)x,
				(int)y, 
				(int)(width*0.15f), 
				(int)height
		);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(
				(int)(x+(width-(width*0.15f))), 
				(int)y, 
				(int) ((int)width*0.15f), 
				(int)height
		);
	}
	


}

