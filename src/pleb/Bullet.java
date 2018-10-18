package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Bullet extends GameObject {

	public boolean collided = false;
	private double originX, originY, targetX, targetY;
	
	public Bullet(float x, float y, ObjectId id, double tX, double tY) {
		super(x, y, Config.Bullet.size, Config.Bullet.size, id, new Color(255,0,255), false);
		setX(x);
		setY(y);
		setWidth(Config.Bullet.size);
		setHeight(Config.Bullet.size);
		
		this.originX = getX();
		this.originY = getY();
		this.targetX = tX;
		this.targetY = tY;
		
	}
	

	public void update(LinkedList<GameObject> object, float dt, float time) {
		double angle;

		angle = Math.atan2(
				(this.targetX+Config.General.tilesize/2-(getWidth()/2))-this.originX, 
				(this.targetY+Config.General.tilesize/2-(getHeight()/2))-this.originY
		);
	
		setX(getX()+(float)(Math.sin(angle))*Config.Bullet.speed);
		setY(getY()+(float)(Math.cos(angle))*Config.Bullet.speed);
	
		
		checkCollision(object);
	
		if(getX() > this.originX+Config.Bullet.destroyDist
		|| getY() > this.originY+Config.Bullet.destroyDist) {
			collided = true;
		}
		
	}

	private void checkCollision(LinkedList<GameObject> object) 
	{
		for(int i = 0; i < Game.objects.size(); i++) 
		{
			GameObject tempObject = Game.objects.get(i);
			
			if(tempObject.getId() == ObjectId.Block) 
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					collided = true;
				}
			} 
			else if(getId() != ObjectId.Player && tempObject.getId() == ObjectId.Player) 
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					tempObject.setCurrentHealth(tempObject.getCurrentHealth()-5);
					collided = true;
				}
			}
			
			else if(getId() != ObjectId.Enemy && tempObject.getId() == ObjectId.Enemy) 
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					tempObject.setCurrentHealth(tempObject.getCurrentHealth()-5);
					collided = true;
				}
			}
			
		}
	}
	
	public void render(Graphics2D g, float dt) 
	{
		g.setColor(getSelfColor());
		g.fillOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}

	public Rectangle getBounds() { return new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight()); }
	
	public Rectangle getBoundsTop() 	{ return null; }
	public Rectangle getBoundsBottom() 	{ return null; }
	public Rectangle getBoundsLeft() 	{ return null; }
	public Rectangle getBoundsRight() 	{ return null; }

}
