package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Bullet extends GameObject {

	public boolean collided = false;
	private double originX, originY, targetX, targetY;
	
	public Bullet(float x, float y, ObjectId id, Handler handler, double tX, double tY) {
		super(x, y, Config.bulletSize, Config.bulletSize, id, handler, new Color(255,0,255), false);
		setX(x);
		setY(y);
		setWidth(Config.bulletSize);
		setHeight(Config.bulletSize);
		this.handler = handler;
		this.originX = getX();
		this.originY = getY();
		this.targetX = tX;//Game.getPlayerX();
		this.targetY = tY;//Game.getPlayerY();
		
	}
	

	public void update(LinkedList<GameObject> object, float dt, float time) {
		double angle;

		angle = Math.atan2(
				(this.targetX+Config.tilesize/2-(getWidth()/2))-this.originX, 
				(this.targetY+Config.tilesize/2-(getHeight()/2))-this.originY
		);
	
		setX(getX()+(float)(Math.sin(angle))*Config.BulletSpeed);
		setY(getY()+(float)(Math.cos(angle))*Config.BulletSpeed);
	
		
		checkCollision(object);
	
		if(getX() > this.originX+Config.bulletDestroyDist
		|| getY() > this.originY+Config.bulletDestroyDist) {
			collided = true;
		}
		
	}

	private void checkCollision(LinkedList<GameObject> object) 
	{
		for(int i = 0; i < handler.object.size(); i++) 
		{
			GameObject tempObject = handler.object.get(i);
			
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
