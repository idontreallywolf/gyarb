package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class Player extends GameObject {

	private float gravity = 2f;
	private boolean falling = true;
	private final float X_MAX_SPEED, Y_MAX_SPEED;
	private int direction;
	private long lastBullet;
	private boolean intersectsLeft, intersectsRight, isBouncing = false;
	private int decreaseHealth = 20;

	private int bulletDelay = 500; // ms
	private int coins_collected;

	private ParticleManager particleMgr;
	private ArrayList<Bullet> Bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();
	private Vector<Float> rayv = new Vector<Float>(2);
	private static double timeElapsed;

	public Rectangle bounds, boundsTop, boundsBottom, boundsLeft, boundsRight;
	public static int maxHP = 150;


	Player(float x, float y, float w, float h, ObjectId id, Color color, boolean isEntity){
		super(x, y, w, h, id, color, isEntity);

		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		setHP(Config.Entity.Player.health);

		coins_collected = 0;
		lastBullet = 0;


		acc = 1.5f;
		X_MAX_SPEED = 6;
		Y_MAX_SPEED = 15;
		direction = 1;

		particleMgr = new ParticleManager();
		bounds = new Rectangle( (int)x, (int)y, (int)width, (int)height );

	}



	public void update(LinkedList<GameObject> object, float dt, float time) {
		handleKeys(dt);
		setX(getX()+getVelX() * dt);

		// Gravitation
		setY(getY() + (getVelY()*dt));
		if(falling || jumping) {
			setVelY(getVelY()+gravity*dt);
			if(getVelY() > Y_MAX_SPEED)
				setVelY(Y_MAX_SPEED);
		}

		
		
		CollisionAndCondition(object);

		if(isBouncing) {
			timeElapsed += time;
			if (timeElapsed >= 0.01 ) {
				timeElapsed = 0;
				particleMgr.genParticle("tail",(int)getX(), (int)getY(), 10, Config.Particle.speed*2, Config.Colors.purple);
			}
		}


		particleMgr.update(time);

		handleBullets(this.direction, object, dt, time);
		
		
	}

	public void render(Graphics2D g, float dt) {
		for(Bullet b: Bullets)
			b.render(g, dt);

		particleMgr.render(g, dt);

		g.setColor(getSelfColor());
		g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
		
		raycast(g);
		/*
		g.setColor(Color.WHITE);
		g.draw(getBounds());
		g.draw(getBoundsTop());
		g.draw(getBoundsBottom());
		g.draw(getBoundsLeft());
		g.draw(getBoundsRight());
		*/
		showHP(g);

	}
	
	boolean raycast(GameObject ent, float startx, float starty, float directionx, float directiony, float length, LinkedList<GameObject> mask, Graphics2D g) {
	    return raycast(ent, startx, starty, startx + normalized(directionx, directiony)[0] * length, starty + normalized(directionx, directiony)[1] * length, mask, 1.0f, g);
	}
	 
	boolean raycast(GameObject ent, float startx, float starty, float directionx, float directiony, float length, LinkedList<GameObject> mask, float precision, Graphics2D g) {
	    return raycast(ent, startx, starty, startx + normalized(directionx, directiony)[0] * length, starty + normalized(directionx, directiony)[1] * length, mask, precision, g);
	}
	 
	boolean raycast(GameObject ent, float startx, float starty, float endx, float endy, LinkedList<GameObject> mask, Graphics2D g) {
	    return raycast(ent, startx, starty, endx, endy, mask, 1.0f, g);
	}
	 
	boolean raycast(GameObject ent, float startx, float starty, float endx, float endy, LinkedList<GameObject> mask, float precision, Graphics2D g) {
	    float currentx = startx;
	    float currenty = starty;
	    
	    while (Math.abs(endx - currentx) + Math.abs(endy - currenty) >= precision) {
	        currentx -= normalized(startx - endx, starty - endy)[0] * precision;
	        currenty -= normalized(startx - endx, starty - endy)[1] * precision;

	        Rectangle c = new Rectangle((int)currentx, (int)currenty, (int)precision, (int)precision);
	        
	        //g.setColor(Color.PINK);
	        //g.drawRect((int)currentx, (int)currenty, (int)Math.ceil(precision), (int)Math.ceil(precision));
	        
	        for (GameObject go : mask) {
	        	
	        	if (go == ent)
	        		continue;
	        	
	        	if (c.getBounds().intersects(go.getBounds())) {
	        		this.rayv.add(0, go.getX());
	        		this.rayv.add(1, go.getY());
	        		return true;
	        	}
			}
	    }
	   
	    return false;
	}
	
	private float mag(float x, float y) {
		return (float) Math.sqrt((double)(x * x + y * y));
	} 
	
	private float[] normalized(float x, float y) {
		float m = mag(x, y);
		float[] f = {x, y};
		if (m > 0) {
			f[0] = f[0] / m;
			f[1] = f[1] / m;
		}
	    return f;
	}
	
	private void raycast(Graphics2D g) {		
		// Left
		if (raycast(this, getX(), getY(), -1, 0f, 100, Game.objects, 1.0f, g)) 
		{  
			//System.out.println(getX()+":"+this.rayv.get(0));
			if(getX() <= this.rayv.get(0)+Config.General.tilesize) {
				setX(this.rayv.get(0)+Config.General.tilesize);
				setVelX(0);	
			}
		}
		
		// Right
		if (raycast(this, getX(), getY(), 1, 0f, 132, Game.objects, 1.0f, g)) 
		{ 
			//System.out.println(getX()+":"+this.rayv.get(0));
			if(getX()+Config.General.tilesize >= this.rayv.get(0)) {
				setX(this.rayv.get(0)-Config.General.tilesize);
				setVelX(0);
				
			}
		}
		
		
		// Down
		/*if (raycast(this, getX(), getY(), 0, 1f, 100, Game.objects, 1.0f, g)) 
		{ }*/
	}


	private void handleKeys(float dt) {
		if(Game.keyDownMap.containsKey(KeyEvent.VK_SPACE))
		{
			for(GameObject e: Game.entities)
			{
				if(Game.getObjectDistance(this, e) <= Config.Entity.Enemy.vision)
				{
					if((System.currentTimeMillis() - lastBullet >= bulletDelay) && (Bullets.size() < Config.Bullet.limit))
					{
						Bullets.add( new Bullet(
							getX()+(getWidth()/2),
							getY()+(getHeight()/2-(getHeight()/4)),
							getId(), e.getX(), e.getY()
						) );
						e.showTargetBorder(true);
						lastBullet = System.currentTimeMillis();
					}
				}
			}
		}


		if(Game.keyDownMap.containsKey(KeyEvent.VK_A)){
			direction = 0;

			if(!intersectsLeft) {
				setVelX(getVelX() - acc);
				if(getVelX() < -X_MAX_SPEED)
					setVelX(-X_MAX_SPEED);
			}

		} else if(Game.keyDownMap.containsKey(KeyEvent.VK_D)){
			direction = 1;

			if(!intersectsRight) {
				setVelX(getVelX() + acc);
				if(getVelX() > X_MAX_SPEED)
					setVelX(X_MAX_SPEED);
			}

		} else { setVelX(getVelX() * 0.8f); }


		if(!Game.keyDownMap.containsKey(KeyEvent.VK_A)) intersectsLeft = false;
		if(!Game.keyDownMap.containsKey(KeyEvent.VK_D)) intersectsRight = false;

		if(Game.keyDownMap.containsKey(KeyEvent.VK_W) && !isJumping()){
			setJumping(true);
			setVelY(-16);
		}


	}

	private void bounce() {
		setJumping(true);
		setVelY(-24);
		isBouncing = true;
	}


	private void handleBullets(int direction, LinkedList<GameObject> object, float dt, float time) {
		for(Bullet b: Bullets) {

			b.update(object, dt, time);
			if(b.collided) {
				removeBullets.add(b);
				particleMgr.genParticle("explosion",(int)b.getX(), (int)b.getY(), 30, Config.Particle.speed*1, Config.Colors.purple);
			}

		}
		Bullets.removeAll(removeBullets);
		removeBullets.clear();
	}


	private void CollisionAndCondition(LinkedList<GameObject> object) {

		for(int i = 0; i < Game.objects.size(); i++) {
			
			GameObject tempObject = Game.objects.get(i);

			// avoid looking for collisions on objects outside of screen
			if(!tempObject.isOnScreen)
				continue;
			
			
			
			
			if(tempObject.getId() == ObjectId.Block) {

				if(getBoundsTop().intersects(tempObject.getBoundsBottom())){
					setY(tempObject.getY() + height);
					setVelY(0);
				}

				if(getBoundsBottom().intersects(tempObject.getBoundsTop())){
					setY(tempObject.getY() - height);
					setVelY(0);
					falling = false;
					setJumping(false);
					isBouncing = false;
				} else { falling = true; }

				
				/*if(getBoundsRight().intersects(tempObject.getBoundsLeft())){
					intersectsRight = true;
					x = tempObject.getX() - this.width;
					setVelX(0);

				} else { intersectsRight = false; }*/


				/*if(getBoundsLeft().intersects(tempObject.getBoundsRight())){
					intersectsLeft = true;
					x = tempObject.getX() + this.width;
					setVelX(0);
				} else { intersectsLeft = false; }*/

			} else if (tempObject.getId() == ObjectId.Obstacle) {

				if(tempObject.getId() == ObjectId.Obstacle) {
					if(getBounds().intersects(tempObject.getBounds())){
						this.kill();
					}
				}


			} else if(tempObject.getId() == ObjectId.Trampoline) {
				if(getBoundsBottom().intersects(tempObject.getBoundsTop())){
					setY(tempObject.getY() - height);
					bounce();
					falling = false;
					setJumping(false);
				} else {
					falling = true;
				}

			} else if(tempObject.getId() == ObjectId.Coin) {
				if(getBounds().intersects(tempObject.getBounds())) {
					coins_collected++;
					object.remove(tempObject);
					particleMgr.genParticle("tail",(int)getX(), (int)getY(), 10, Config.Particle.speed*2, Config.Colors.yellow);
				}
			}
		}

		// Player dies if it keeps falling into the void.
		if(getY() >= 700) this.kill();

	}


	public int getCoinsCollected() { return this.coins_collected; }

	public void kill() {
		setX(initialX);
		setY(initialY);

		setCurrentHealth(getCurrentHealth()-decreaseHealth);
		setVelX(0);
		setVelY(0);
	}


	public Rectangle getBounds() { return new Rectangle( (int)x, (int)y, (int)width, (int)height ); }

	public Rectangle getBoundsTop() {
		return new Rectangle(
				(int)(x+(width*0.2f)),
				(int)y,
				(int)(width-(width*0.45f)),
				(int)(height/2)
		);
	}
	public Rectangle getBoundsBottom() {
		return new Rectangle(
				(int)(x+(width*0.2f)),
				(int)(y+(height/2)),
				(int)(width-(width*0.45f)),
				(int)(height/2)
		);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(
				(int)x,
				(int)(y+height*0.15f),
				(int)(width*0.15f),
				(int)(height-(height*0.3f))
		);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(
				(int)(x+(width-(width*0.15f))),
				(int)(y+height*0.15f),
				(int)(width*0.15f),
				(int)(height-(height*0.3f))
		);
	}

	public Rectangle getBoundsPepe() {
		return new Rectangle(
				(int)(x),
				(int)(y+height*1.15f),
				(int)(width*0.15f),
				(int)(height-(height*0.3f))
		);
	}
}
