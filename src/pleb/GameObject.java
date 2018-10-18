package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

// TODO : ADD method docs

public abstract class GameObject {
	
	protected ObjectId id;
	protected Color selfColor;
	
	protected float x, y, initialX, initialY;
	protected float width, height;
	protected float velX = 0, velY = 0;
	protected float acc;
	protected float previousHealth, currentHealth;
	
	protected boolean isOnScreen;
	protected boolean isEntity;
	protected boolean showTargetBorder;
	protected boolean falling = true;
	protected boolean jumping = false;
	
	public GameObject(float x, float y, float w, float h, ObjectId id, Color selfColor, boolean isEntity){
		this.x = initialX = x;
		this.y = initialY = y;
		this.width = w;
		this.height = h;
		this.id = id;
		this.selfColor = selfColor;
		this.isEntity = isEntity;
		if(isEntity) {
			showTargetBorder = false;
		}
		this.isOnScreen = true;
	}
	
	public abstract void update(LinkedList<GameObject> object, float dt, float time);
	public abstract void render(Graphics2D g, float dt);
	
	
	// set
	public void setColor(Color c) 				{	this.selfColor = c;				}
	public void setX(float x) 					{	this.x = x;						}
	public void setY(float y) 					{	this.y = y;						}
	public void setWidth(float w) 				{	this.width = w;					}
	public void setHeight(float h) 				{	this.height = h;				}
	public void setVelX(float velX)				{	this.velX = velX;				}
	public void setVelY(float velY) 			{	this.velY = velY;				}
	public void setFalling(boolean falling) 	{	this.falling = falling;			}
	public void setJumping(boolean jumping) 	{	this.jumping = jumping;			}
	public void setCurrentHealth(float amount) 	{	this.currentHealth = amount;	}
	public void setPreviousHealth(float amount) {	this.previousHealth = amount; 	}
	
	public void setHP(int amount) 
	{
		setCurrentHealth(amount);
		setPreviousHealth(amount);
	}
	
	public void showHP(Graphics2D g) 
	{
		float posX = getX()-10;
		float posY = getY()-15;
		
		g.setColor(Color.WHITE);
		g.fillRect((int)posX, (int)posY, 50, 10);

		float percentage = (getCurrentHealth() * 50 / getPreviousHealth());
		
		g.setColor(Color.RED);		
		g.fillRect((int)posX, (int)posY, (int)(50-(50-percentage)), 10);		
	}
	
	public void showTargetBorder(boolean show) 
	{
		this.showTargetBorder = true;
	}
	
	
	// get
	public abstract Rectangle getBounds();
	public abstract Rectangle getBoundsTop();
	public abstract Rectangle getBoundsBottom();
	public abstract Rectangle getBoundsLeft();
	public abstract Rectangle getBoundsRight();
	
	
	public float getX() 		{	return x;		}
	public float getY() 		{	return y;		}
	public float getVelX() 		{	return velX;	}
	public float getVelY() 		{	return velY;	}
	public float getWidth() 	{	return width;	}
	public float getHeight()	{	return height;	}
	public ObjectId getId() 	{	return id;		}
	
	public Color getSelfColor() {	return this.selfColor;	}
	public boolean isEntity() 	{ 	return this.isEntity; 	}
	public boolean isFalling() 	{ 	return this.falling; 	}
	public boolean isJumping() 	{ 	return this.jumping; 	}
	public boolean isTarget() 	{ 	return this.showTargetBorder; 	}

	
	public float getCurrentHealth() 	{ 	return currentHealth; 	}
	public float getPreviousHealth() 	{ 	return previousHealth; 	}
	
	public int getType() 	{ 	return 0; 	}


	

	
}

