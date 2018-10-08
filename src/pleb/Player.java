package pleb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

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
	private static double timeElapsed;

	public Rectangle bounds, boundsTop, boundsBottom, boundsLeft, boundsRight;
	public static int maxHP = 150;


	Texture tex = Game.getInstance();

	Player(float x, float y, float w, float h, ObjectId id, Handler obj_handler, Color color, boolean isEntity){
		super(x, y, w, h, id, obj_handler, color, isEntity);

		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		setHP(Config.playerHealth);

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
		setY(getY()+ (getVelY()*dt));
		if(falling || jumping) {
			setVelY(getVelY()+gravity*dt);
			if(getVelY() > Y_MAX_SPEED)
				setVelY(Y_MAX_SPEED);
		}


		CollisionAndCondition(object);

		if(isBouncing ) {
			timeElapsed += time;
			if (timeElapsed >= 0.01 ) {
				timeElapsed = 0;
				particleMgr.genParticle("tail",(int)getX(), (int)getY(), 10, Config.particleSpeed*2, Config.Colors.purple);
			}
		}


		particleMgr.update(time);

/*
		 Direction is used to determine
		 which way the bullet is supposed to go
*/
		handleBullets(this.direction, object, dt, time);
	}





	public void render(Graphics2D g, float dt) {
		for(Bullet b: Bullets)
			b.render(g, dt);

		particleMgr.render(g, dt);

		g.setColor(getSelfColor());
		g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());

		g.setColor(Color.RED);
		g.draw(getBoundsTop());
		g.draw(getBoundsBottom());
		g.draw(getBoundsLeft());
		g.draw(getBoundsRight());

		showHP(g);



	}


	private void handleKeys(float dt) {
		if(Game.keyDownMap.containsKey(KeyEvent.VK_SPACE))
		{
			for(GameObject e: Handler.entities)
			{
				if(Game.getObjectDistance(this, e) <= Config.EnemyVision)
				{
					if((System.currentTimeMillis() - lastBullet >= bulletDelay) && (Bullets.size() < Config.BulletLimit))
					{
						Bullets.add( new Bullet(
							getX()+(getWidth()/2),
							getY()+(getHeight()/2-(getHeight()/4)),
							getId(), handler, e.getX(), e.getY()
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
				particleMgr.genParticle("explosion",(int)b.getX(), (int)b.getY(), 30, Config.particleSpeed*1, Config.Colors.purple);
			}

		}
		Bullets.removeAll(removeBullets);
	}


	private void CollisionAndCondition(LinkedList<GameObject> object) {

		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

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

				if(getBoundsRight().intersects(tempObject.getBoundsLeft())){
					intersectsRight = true;
					x = tempObject.getX() - this.width;
					setVelX(0);

				} else { intersectsRight = false; }


				if(getBoundsLeft().intersects(tempObject.getBoundsRight())){
					intersectsLeft = true;
					x = tempObject.getX() + this.width;
					setVelX(0);
				} else { intersectsLeft = false; }

			} else if (tempObject.getId() == ObjectId.Obstacle) {

				// Spikes/Nails
				//if(tempObject.getType() == Config.obstacle[0]) {
				if(tempObject.getId() == ObjectId.Obstacle) {
					if(getBounds().intersects(tempObject.getBounds())){
						this.kill();
						System.out.println("hit obstacle!");
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
					particleMgr.genParticle("tail",(int)getX(), (int)getY(), 10, Config.particleSpeed*2, Config.Colors.yellow);
				}
			}
		}

		// Player dies if it keeps falling into the void.
		if(getY() >= 700) this.kill();

	}


	public int getCoinsCollected() {
		return this.coins_collected;
	}

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
				(int)((int)width*0.15f),
				(int)(height-(height*0.3f))
		);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(
				(int)(x+(width-(width*0.15f))),
				(int)(y+height*0.15f),
				(int)((int)width*0.15f),
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
